package com.example.acreencias.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import com.example.dao.ClienteDAO;
import com.example.dao.CuentaDAO;
import com.example.dao.DAOFactory;
import com.example.dao.MovimientoDAO;
import com.example.model.Cliente;
import com.example.model.Cuenta;
import com.example.model.Movimiento;
import com.example.model.TipoMovimiento;

public class Movimientos {
	private static final Logger logger = Logger.getLogger(Movimientos.class.getCanonicalName());
	
	public static boolean executeMovimientoMenu(BufferedReader in) throws IOException {
		ClienteDAO clienteDao = DAOFactory.getClienteDAO();
		CuentaDAO cuentaDao = DAOFactory.getCuentaDAO();
		MovimientoDAO movimientoDao = DAOFactory.getMovimientoDAO();
    	String action;
    	
    	System.out.println("\n\n[C]reate | [R]ead | [L]ist | [B]ack: ");
        action = in.readLine();
        if ((action.length() == 0) || action.toUpperCase().charAt(0) == 'B') {
            return true;
        }
        int cliente_id, id;
        Cliente cli;
        Cuenta cta;
        Movimiento mov;
        switch (action.toUpperCase().charAt(0)) {
        case 'C':
        	System.out.println("Id del cliente: ");
        	cliente_id = new Integer(in.readLine().trim());
        	cli = clienteDao.find(cliente_id);
        	if (cli == null) {
	            System.out.println("\n\nCliente " + cliente_id + " not found");
	            break;
	        }
        	cta = cuentaDao.findCuentasCliente(cliente_id).get(0);
        	mov = movimientoDao.create(inputMovimiento(in, cta));
            if(mov.getId()!=0){
	        	System.out.println("Successfully added Movimiento Record: " + mov.getId());
	            System.out.println("\n\nCreated " + mov);
            }
        	break;
        case 'R':
        	System.out.println("Id del movimiento: ");
            id = new Integer(in.readLine().trim());
            mov = movimientoDao.find(id);
            if (mov != null) {
                System.out.println(mov + "\n");
            } else {
                System.out.println("\n\nMovimiento " + id + " not found");
                break;
            }

            break;
        case 'L':
        	System.out.println("Id del cliente: ");
        	cliente_id = new Integer(in.readLine().trim());
        	cli = clienteDao.find(cliente_id);
        	if (cli == null) {
	            System.out.println("\n\nCliente " + cliente_id + " not found");
	            break;
	        }
        	cta = cuentaDao.findCuentasCliente(cliente_id).get(0);
        	List<Movimiento> movimientos = movimientoDao.findMovimientosCuenta(cta.getId());
            for (Movimiento movimiento : movimientos) {
                System.out.println("Id: " + movimiento.getId() + ", Tipo="+ movimiento.getTipo() +", Monto=" + movimiento.getMonto()+ "\n");
            }
            break;
        }
	    
        return false;
	}

	private static Movimiento inputMovimiento(BufferedReader in, Cuenta cta) throws IOException {
		BigDecimal monto = BigDecimal.ZERO;
        String tipo = null;
        Movimiento mov;
        
        String prompt = "Tipo";
        do {
            System.out.println(prompt + " : ");
            tipo = in.readLine().trim();
            if (!TipoMovimiento.isValid(tipo)) {
                System.out.println("Tipo invalido, + o -");                    
            }
        } while (!TipoMovimiento.isValid(tipo));
        
        prompt = "Monto";
        do {
            System.out.println(prompt + " : ");
            try {
                String amt = in.readLine().trim();
                if (!amt.equals("")) {
                	monto = new BigDecimal(amt);
                }                
                if (monto.signum() <= 0) {
                    System.out.println("debe ser mayor a cero (0)");
                    monto = BigDecimal.ZERO;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please retry with a valid float salary: " + e.getMessage());
            }
        } while (monto.signum() <= 0);
        
        mov = new Movimiento(0, tipo, monto);
        mov.setCuenta(cta);
		return mov;
	}

	public static void daemon(Integer cliente_id){
		ClienteDAO clienteDao = DAOFactory.getClienteDAO();
		CuentaDAO cuentaDao = DAOFactory.getCuentaDAO();
		MovimientoDAO movimientoDao = DAOFactory.getMovimientoDAO();
		
    	Cliente cli = clienteDao.find(cliente_id);
    	logger.info(cli.toString());
    	Cuenta cta = cuentaDao.findCuentasCliente(cliente_id).get(0);
    	logger.info(cta.toString());
    	
    	Random rand = new Random(System.currentTimeMillis());
    	do{
    		BigDecimal monto = new BigDecimal(rand.nextDouble());
    		monto = monto.multiply(new BigDecimal(1000));
    		monto = monto.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	    	Movimiento mov = new Movimiento(0, "+", monto);
			mov.setCuenta(cta);
			mov = movimientoDao.create(mov);
	    	logger.info(mov.toString());
    	}while(true);
		
	}
}
