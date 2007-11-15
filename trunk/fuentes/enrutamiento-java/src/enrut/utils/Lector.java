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
	protected BufferedReader input;
	private String archivo;
	
	/**
	 * Construimos un nuevo Lector
	 * y abrimos el archivo asociado.
	 */
	public Lector(String archivo) {
		abrir(archivo);
	}

	/**
	 * Construimos un nuevo Lector
	 */
	public Lector() {
		
	}
	
	/**
	 * Abre el archivo que recibe como argumento
	 * @param archivo Nombre del archivo a abrir.
	 */
	public void abrir(String archivo) {
		try {
			if (input != null)
				this.cerrar();
			
			input = new BufferedReader( new FileReader(archivo) );
		}
		catch (FileNotFoundException e) {
			System.out.println("Error abriendo \"" + archivo + "\"");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * Lee una linea de la entreda estandar, desplegando 
	 * el mensaje que se le pasa como parametro.
	 */
	public String leerLinea() {
		String linea = null;
		
		try {
			linea = input.readLine();
		}
		catch(Exception e) {
			System.out.println("Error al leer de \"" + archivo + "\"");
			e.printStackTrace();
			System.exit(0);
		}
		return linea;
	}

	public void cerrar() {
		try {
			input.close();
		}
		catch (IOException e) {
		}
	}
}