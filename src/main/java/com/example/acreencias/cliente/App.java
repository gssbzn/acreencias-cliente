package com.example.acreencias.cliente;

import java.util.ArrayList;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import com.example.model.Cliente;

/**
 * 
 * @author Gustavo Bazan
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Client client = ClientBuilder.newClient();
    	WebTarget target = client.target("http://acreencias.herokuapp.com/");
    	ArrayList<Cliente> clientes = Clientes.getClientes(target);
    	for(Cliente cliente : clientes){
    		System.out.println(cliente.toString());
    	}
    	if(clientes.size() == 0)
    		Clientes.init(target);
        
    }
    
    
    
}
