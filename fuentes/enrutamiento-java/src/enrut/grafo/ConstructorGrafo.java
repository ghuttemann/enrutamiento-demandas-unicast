/*
 * @(#)ConstructorGrafo.java
 */
package enrut.grafo;

import enrut.utils.Lector;

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
		String linea; // variable auxiliar
		int contador=0; // contador de lineas leidas
		int cantAristas;
		int cantVertices;
		int origen, destino;
		double costo, capacidad;

		// Abrimos el archivo
		Lector lector = new Lector(archivo);
		
		try {
			// Leemos cantidad de vertices y aristas
			cantVertices = Integer.parseInt(lector.leerLinea());
			cantAristas  = Integer.parseInt(lector.leerLinea());
			
			// Construimos el grafo
			grafo = new Grafo(cantVertices);
	
			linea = lector.leerLinea();
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
				linea = lector.leerLinea();
			}
			
			if (contador != cantAristas) {
				String str = "Error en el archivo \"" + archivo + "\"\n";
				str += "La cantidad de vertices leidos no coincide con ";
				str += "la cantidad especificada";
				throw new Error(str);
			}
		}
		catch (NumberFormatException e) {
			System.out.println("Error de conversión numérica");
			e.printStackTrace();
			System.exit(0);
		}
		
		lector.cerrar();
	}
	
	public Grafo getGrafo() {
		return grafo;
	}
}
