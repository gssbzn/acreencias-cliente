package com.example.acreencias.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import com.example.dao.ClienteDAO;
import com.example.factory.DAOFactory;
import com.example.factory.DAOFactory.DAOTYPE;
import com.example.model.Cliente;

/**
 * 
 * @author Gustavo Bazan
 *
 */
public class Clientes {
	private static final Logger logger = Logger.getLogger(Clientes.class.toString());	
		
	public static void init(WebTarget target){
		for(int i=1; i<=10; ++i){
			Cliente cli = new Cliente();
			cli.setCedula("V-"+i);
			cli.setNombre("Prueba "+i);		
			cli = (Cliente) target.path("clientes").request("application/xml").post(Entity.xml(cli), Cliente.class);
			logger.config(cli.toString());
			Cuentas.createCuenta(target, cli);
		}
	}
	
	public static boolean executeClienteMenu(BufferedReader in) throws IOException {
		DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOTYPE.RESTFULFACTORY);
		ClienteDAO clienteDao = daoFactory.getClienteDAO();
		String action;
    	
    	System.out.println("\n\n[C]reate | [R]ead | [U]pdate | [D]elete | [L]ist | [B]ack: ");
        action = in.readLine();
        if ((action.length() == 0) || action.toUpperCase().charAt(0) == 'B') {
            return true;
        }
        int id;
        Cliente cli;
        switch (action.toUpperCase().charAt(0)) {
        case 'C':
        	cli = clienteDao.create(inputCliente(in));
            System.out.println("Successfully added Cliente Record: " + cli.getId());
            System.out.println("\n\nCreated " + cli);
        	break;
        case 'R':
        	System.out.println("Id del cliente: ");
            id = new Integer(in.readLine().trim());
            cli = clienteDao.find(id);
            if (cli != null) {
                System.out.println(cli + "\n");
            } else {
                System.out.println("\n\nCliente " + id + " not found");
                break;
            }

            break;
        case 'U':
        	System.out.println("Id del cliente: ");
	        id = new Integer(in.readLine().trim());
	        cli = null;
	        cli = clienteDao.find(id);
	        if (cli == null) {
	            System.out.println("\n\nCliente " + id + " not found");
	            break;
	        }
	        cli = inputEmployee(in, cli);
	        clienteDao.update(cli);
	        System.out.println("Successfully updated Employee Record: " + cli.getId());
	        break;
        case 'D':
        	System.out.println("Id del cliente: ");
            id = new Integer(in.readLine().trim());
            cli = clienteDao.find(id);
	        if (cli == null) {
	            System.out.println("\n\nCliente " + id + " not found");
	            break;
	        }
            clienteDao.remove(cli);
            System.out.println("Deleted Employee " + id);
            break;
        case 'L':
            List<Cliente> clientes = clienteDao.findAll();
            for (Cliente cliente : clientes) {
                System.out.println(cliente + "\n");
            }
            break;
        }
	    
        return false;
    }
    
    private static Cliente inputCliente(BufferedReader in) throws IOException {
        return inputCliente(in, null, true);
    }

    private static Cliente inputEmployee(BufferedReader in, Cliente cliDefaults) throws IOException {
        return inputCliente(in, cliDefaults, false);
    }

    private static Cliente inputCliente(BufferedReader in, Cliente cliDefaults, boolean newCliente) throws IOException {
        
        String nombre;
        String cedula;
        Cliente cli;
        int id = 0;
        if (!newCliente) {            
            id = cliDefaults.getId();
            System.out.println("Modifique los campos del cliente con el id: " + id
                    + ". Press return to accept current value.");
        }

        String prompt = "Nombre" + ((cliDefaults == null) ? "" : " [" + cliDefaults.getNombre() + "]");

        do {
            System.out.println(prompt + " : ");
            nombre = in.readLine().trim();
            if (nombre.equals("") && cliDefaults != null) {
                nombre = cliDefaults.getNombre();
            }
            if (nombre.length() < 1) {
                System.out.println("Reintente con un nombre valido");
            }
        } while (nombre.length() < 1);


        prompt = "Cedula" + ((cliDefaults == null) ? "" : " [" + cliDefaults.getCedula() + "]");
        do {
            System.out.println(prompt + " : ");
            cedula = in.readLine().trim();
            if (cedula.equals("") && cliDefaults != null) {
            	cedula = cliDefaults.getCedula();
            }
            if (cedula.length() < 1) {
                System.out.println("Reintente con una cedula valida");
            }
        } while (cedula.length() < 1);
    
        cli = new Cliente(id, nombre, cedula);
        return cli;
    }

}
