/*
 * @(#)LectorTeclado.java
 */
package enrut.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Clase usada para la lectura de datos desde
 * teclado y para el borrado de pantalla.
 *
 * @author German Huttemann Arza
 */
public class LectorTeclado extends Lector {
	
	/**
	 * Construimos un nuevo lector
	 */
	public LectorTeclado() {
		in = new BufferedReader( new InputStreamReader(System.in) );
	}
	
	/**
	 * Lee una linea de la entreda estandar, desplegando 
	 * el mensaje que se le pasa como parametro.
	 */
	public String leerLinea(String mensaje) {
		System.out.print(mensaje);
		String linea = null;
		
		try {
			linea = in.readLine();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return linea;
	}
	
	/**
	 * Lee una linea de la entreda estandar.
	 */
	public String leerLinea() {
		return leerLinea("");
	}
	
	/** 
	 * Simula el borrado de pantalla
	 * imprimiendo lineas en blanco.
	 */
	public void clear() {
		for(int i=0; i < 100; ++i)
			System.out.println("\t\t\t\t\t");
	}	
}