package com.example.acreencias.cliente;

import java.util.ArrayList;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;

import com.example.model.Cliente;
import com.example.model.Cuenta;
import com.example.model.TipoCuenta;

/**
 * 
 * @author Gustavo Bazan
 *
 */
public class Cuentas {
	
	public static ArrayList<Cuenta> getCuentas(WebTarget target, Cliente cliente){
		GenericType<ArrayList<Cuenta>> genericRootElement = new GenericType<ArrayList<Cuenta>>() {};
		ArrayList<Cuenta> cuentas = target.path("clientes/"+cliente.getId()+"/cuentas").request().get(genericRootElement);
		return cuentas;
	}
	
	public static void createCuenta(WebTarget target, Cliente cliente){
		Cuenta cuenta = new Cuenta();
		cuenta.setTipo(TipoCuenta.ACREENCIA.getValue());
		cuenta = (Cuenta) target.path("clientes/"+cliente.getId()+"/cuentas").request("application/xml").post(Entity.xml(cuenta), Cuenta.class);
	}
}
