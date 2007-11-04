/*
 * @(#)Lector.java
 */
package enrut.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase usada para la lectura de datos desde
 * teclado y para el borrado de pantalla.
 *
 * @author German Huttemann Arza
 */
public class Lector {
	protected BufferedReader in;
	private String archivo;
	
	/**
	 * Construimos un nuevo lector
	 */
	public Lector(String archivo) {
		try {
			in = new BufferedReader( new FileReader(archivo) );
		} catch (FileNotFoundException e) {
			System.out.println("Error abriendo \"" + archivo + "\"");
			e.printStackTrace();
			System.exit(0);
		}
	}

	protected Lector() {
		// necesaria para clases hijas
	}
	
	/**
	 * Lee una linea de la entreda estandar, desplegando 
	 * el mensaje que se le pasa como parametro.
	 */
	public String leerLinea() {
		String linea = null;
		
		try {
			linea = in.readLine();
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return linea;
	}

	public void cerrar() {
		try {
			in.close();
		}
		catch (IOException e) {
		}
	}
}