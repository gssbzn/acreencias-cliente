package com.example.acreencias.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import com.example.dao.ClienteDAO;
import com.example.dao.CuentaDAO;
import com.example.factory.DAOFactory;
import com.example.factory.DAOFactory.DAOTYPE;
import com.example.model.Cliente;
import com.example.model.Cuenta;
import com.example.model.TipoCuenta;

/**
 * 
 * @author Gustavo Bazan
 *
 */
public class Cuentas {
	
	public static void createCuenta(WebTarget target, Cliente cliente){
		Cuenta cuenta = new Cuenta();
		cuenta.setTipo(TipoCuenta.ACREENCIA.getValue());
		cuenta = (Cuenta) target.path("clientes/"+cliente.getId()+"/cuentas").request("application/xml").post(Entity.xml(cuenta), Cuenta.class);
	}
	
	public static boolean executeCuentaMenu(BufferedReader in) throws IOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOTYPE.RESTFULFACTORY);
		ClienteDAO clienteDao = daoFactory.getClienteDAO();
		CuentaDAO cuentaDao = daoFactory.getCuentaDAO();
		String action;
    	
    	System.out.println("\n\n[C]reate | [R]ead | [L]ist | [B]ack: ");
        action = in.readLine();
        if ((action.length() == 0) || action.toUpperCase().charAt(0) == 'B') {
            return true;
        }
        int cliente_id;
        Cuenta cuenta;
        switch (action.toUpperCase().charAt(0)) {
        case 'C':
        	System.out.println("Id del cliente: ");
        	cliente_id = new Integer(in.readLine().trim());
        	Cliente cli = clienteDao.find(cliente_id);
        	if (cli == null) {
	            System.out.println("\n\nCliente " + cliente_id + " not found");
	            break;
	        }
        	cuenta = cuentaDao.create(inputCuenta(in, cli));
            System.out.println("Successfully added Cuenta Record: " + cuenta.getId());
            System.out.println("\n\nCreated " + cuenta);
        	break;
        case 'L':
        	System.out.println("Id del cliente: ");
        	cliente_id = new Integer(in.readLine().trim());
            List<Cuenta> cuentas = cuentaDao.findCuentasCliente(cliente_id);
            for (Cuenta cta : cuentas) {
                System.out.println(cta + "\n");
                System.out.println("Saldo: " + cta.getSaldo());
            }
            break;
        }
	    
        return false;
	}
    
    private static Cuenta inputCuenta(BufferedReader in, Cliente cli) {
		Cuenta cuenta = new Cuenta(0, BigDecimal.ZERO, TipoCuenta.ACREENCIA.getValue());
		return cuenta;
	}
}
