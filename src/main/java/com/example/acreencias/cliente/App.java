package com.example.acreencias.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.client.ClientBuilder;

import com.example.dao.ClienteDAO;
import com.example.dao.DAOFactory;
import com.example.model.Cliente;

/**
 * 
 * @author Gustavo Bazan
 *
 */
public class App {
	private static final Logger logger = Logger.getLogger(App.class.getCanonicalName());
	private static final String SERVER = "http://acreencias.herokuapp.com/";
	
	public static void main(String[] args) throws Exception {
		ClienteDAO clienteDao = DAOFactory.getClienteDAO();
    	List<Cliente> clientes = (ArrayList<Cliente>) clienteDao.findAll();
    	
    	if(clientes.size() == 0){
    		logger.config("Inizializando Clientes y Cuentas");
    		Clientes.init(ClientBuilder.newClient().target(SERVER));
    	}    		
    	
    	boolean interactive = true;
    	boolean daemon = false;
    	Integer cliente_id = null;
    	if(args.length > 0){
    		interactive = args[0].equals("-i");
    		if(!interactive){
    			daemon = args[0].equals("-c");
    			cliente_id = Integer.parseInt(args[1]);
    		}
    	}
    	
    	if(interactive){
	    	boolean timeToQuit = false;
	        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	        do {
	            timeToQuit = executeMenu(in);
	        } while (!timeToQuit);
    	}
    	if(daemon && cliente_id != null){
    		Movimientos.daemon(cliente_id);
    	}
        
    }
    
    public static boolean executeMenu(BufferedReader in) throws IOException {
    	String action;
    	
    	System.out.println("\n\n[C]lientes | C[u]entas | [M]ovimientos | [Q]uit: ");
        action = in.readLine();
        if ((action.length() == 0) || action.toUpperCase().charAt(0) == 'Q') {
            return true;
        }
        boolean back = false;
        switch (action.toUpperCase().charAt(0)) {        
        case 'C':        	
        	do {
        		back = Clientes.executeClienteMenu(in);
            } while (!back);
        	break;
        case 'U':
        	do {
        		back = Cuentas.executeCuentaMenu(in);
            } while (!back);
        	break;
        case 'M':
        	do {
        		back = Movimientos.executeMovimientoMenu(in);
            } while (!back);
        	break;
        }
    	return false;
    }
	
}
