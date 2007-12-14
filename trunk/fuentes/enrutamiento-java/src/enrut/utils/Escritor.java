/*
 * @(#)Escritor.java
 */
package enrut.utils;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Escritor {
	protected PrintWriter output;
	private String archivo;
	
	/**
	 * Construimos un nuevo Escritor
	 * y abrimos el archivo asociado.
	 */
	public Escritor(String archivo) {
		abrir(archivo);
	}

	/**
	 * Construimos un nuevo Escritor
	 */
	public Escritor() {
		
	}
	
	/**
	 * Abre el archivo que recibe como parámetro
	 * @param archivo Nombre del archivo a abrir.
	 */
	public void abrir(String archivo) {
		try {
			output = new PrintWriter( new FileWriter(archivo) );
		}
		catch (FileNotFoundException e) {
			System.out.println("Error encontrando \"" + archivo + "\"");
			e.printStackTrace();
			System.exit(0);
		}
		catch (IOException e) {
			System.out.println("Error abriendo \"" + archivo + "\"");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * Imprime el objeto que recibe como parámetro
	 */
	public Escritor imprimir(Object arg) {
		output.print(arg);
		this.chequearError();
		return this;
	}
	
	public Escritor nuevaLinea() {
		output.println();
		this.chequearError();
		return this;
	}

	public void cerrar() {
		output.flush();
		output.close();
	}
	
	private void chequearError() {
		if (output.checkError())
			throw new Error("Error escribiendo en \"" + archivo + "\"");
	}
}
