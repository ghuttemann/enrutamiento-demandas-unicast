/*
 * @(#)ConstructorGrafo.java
 */
package enrut.grafo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Clase que realiza la construccion de un Grafo, leyendo
 * la definicion del mismo, en terminos de sus vertices y 
 * aristas, de un archivo de texto.
 * 
 * @see Grafo
 * @author Germán Hüttemann Arza
 */
public class ConstructorGrafo {
	private Grafo grafo;
	
	public void leerGrafo(String archivo) {
		BufferedReader input;
		String linea; // variable auxiliar
		int contador=0; // contador de lineas leidas
		int cantAristas;
		int cantVertices;
		int origen, destino;
		double costo, capacidad;

		// Abrimos el archivo
		input = getConexionArchivo(archivo);
		
		try {
			// Leemos cantidad de vertices y aristas
			cantVertices = Integer.parseInt(input.readLine());
			cantAristas  = Integer.parseInt(input.readLine());
			
			// Construimos el grafo
			grafo = new Grafo(cantVertices);
	
			linea = input.readLine();
			while (linea != null) {
				String[] partes = linea.split("\\s");
				
				origen    = Integer.parseInt(partes[0]);
				destino   = Integer.parseInt(partes[1]);
				capacidad = Double.parseDouble(partes[2]);
				costo     = Double.parseDouble(partes[3]);
	
				// Creamos la arista y la guardamos en el grafo
				Arista a = new Arista(origen, destino, costo, capacidad);
				grafo.agregarArista(a);
	
				contador++;
				linea = input.readLine();
			}
			
			if (contador != cantAristas) {
				String str = "Error en el archivo \"" + archivo + "\"\n";
				str += "La cantidad de vertices leidos no coincide con ";
				str += "la cantidad especificada";
				throw new Error(str);
			}
		}
		catch (NumberFormatException e) {
		}
		catch (IOException e) {
		}
		
		try {
			input.close();
		} 
		catch (IOException e) {
		}
	}
	
	public Grafo getGrafo() {
		return grafo;
	}
	
	private BufferedReader getConexionArchivo(String archivo) {
 		BufferedReader input = null;

 		try {
 			input = new BufferedReader( new FileReader(archivo) );
 		}
 		catch(IOException e) {
 			throw new Error("No se puede abrir el archivo \"" + archivo + "\"");
 		}
 		
 		return input;
 	}
}
