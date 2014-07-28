package com.example.acreencias.cliente;

import java.util.ArrayList;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import com.example.model.Cliente;

/**
 * 
 * @author Gustavo Bazan
 *
 */
public class Clientes {
	
	public static ArrayList<Cliente> getClientes(WebTarget target){
		GenericType<ArrayList<Cliente>> genericRootElement = new GenericType<ArrayList<Cliente>>() {};
		ArrayList<Cliente> clientes = target.path("clientes").request().get(genericRootElement);
		return clientes;
	}
	
	public static void init(WebTarget target){
		for(int i=1; i<=10; ++i){
			Cliente cli = new Cliente();
			cli.setCedula("V-"+i);
			cli.setNombre("Prueba "+i);		
			cli = (Cliente) target.path("clientes").request("application/xml").post(Entity.xml(cli), Cliente.class);
			System.out.println(cli.toString());
			Cuentas.createCuenta(target, cli);
		}
	}

}
