/*
 * @(#)Grafo.java
 */
package enrut.grafo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import java.util.Iterator;

 
/**
 * Implementacion del TDA Grafo. Utiliza lista de adyacencias
 * para almacenar las aristas entre cada par de vertices.
 *
 * @see Vertice
 * @see Arista
 * @author Germán Hüttemann Arza
 */
public class Grafo {
 	/*
 	 * Lista de vertices del grafo.
 	 */
	private Vector<Vertice> vertices;
 	
 	/**
 	 * Construye un nuevo grafo vacio con una cantidad
 	 * de vertices especificada por cantidad.
 	 *
 	 * @param cantidad La cantidad de vertices del Grafo.
 	 */
 	public Grafo(int cantidad) {
 		vertices = new Vector<Vertice>(cantidad);
 		for (int i=0; i < cantidad; i++) {
 			vertices.add(new Vertice());
 		}
 	}
 	
 	/**
 	 * Construye un nuevo grafo vacio con una cantidad
 	 * inicial de 50 vertices.
 	 */
 	public Grafo() {
 		this(50);
 	}
 	
 	/** Agrega un nuevo vertice al Grafo. */
 	public void agregarVertice() {
 		vertices.setSize(vertices.size()+1);
 	}
 	
 	/**
 	 * Agrega una arista a un determinado vertice indicado
 	 * por su representacion numerica dentro del Grafo.
 	 * @param a La Arista a ser agregada.
 	 */
 	public void agregarArista(Arista a) {
 		int origen = a.getOrigen(); 
 		vertices.get(origen).agregarAdyacencia(a);
 	}
 	
 	/**
 	 * Retorna el Vertice especificado por indice, teniendo
 	 * en cuenta su representacion numerica dentro del Grafo.
 	 * @param indice El numero interno del Vertice buscado.
 	 */
 	public Vertice getVertice(int indice) {
 		return vertices.get(indice);
 	}
 	
 	/**
 	 * Retorna la cantidad de Vertices del Grafo.
 	 * @return La cantidad de Vertices del grafo.
 	 */
 	public int cantVertices() {
 		return vertices.size();
 	}
 	
 	/**
 	 * Retorna la cantidad de Aristas del Grafo.
 	 * @return La cantidad de Aristas del Grafo.
 	 */
 	public int cantAristas() {
 		int cant = 0;
 		for (int i=0; i < vertices.size(); i++)
 			cant += vertices.get(i).getCantAristas();

 		return cant;
 	}
 	
 	/**
 	 * Imprime el Grafo, en funcion de sus Vertices, cada uno
 	 * de los cuales con sus respectivas aristas.
 	 *
 	 * @param destino Si es PrintTo.STDOUT imprime la tabla en la
 	 *				  salida estandar (generalmente la pantalla); si
 	 *				  es PrintTo.FILE imprime en un archivo de texto; 
 	 *				  si es PrintTo.ALL, imprime en ambos y si es 
 	 *				  otro valor, no imprime.
 	 */
 	public void imprimir(PrintTo destino) {
 		
 		if ((destino == PrintTo.STDOUT) || (destino == PrintTo.ALL))
 			priv_imprimir();
 		
 		if ((destino == PrintTo.FILE) || (destino == PrintTo.ALL))
 			try {
 				priv_imprimir( new PrintWriter(new FileWriter("grafo.txt")) );
 			}
 			catch(IOException e) {
 				System.out.println(e);
 			}
 	}
 	
 	/*
 	 * Usado por la rutina imprimir para imprimir el grafo
 	 * en pantalla.
 	 */
 	private void priv_imprimir() {
 		System.out.println("Grafo:\n");
 		for (int i=0; i < vertices.size(); i++) {
 			Iterator<Arista> it = this.getVertice(i).getAdyacentes().iterator();
 			while (it.hasNext()) {
 				Arista a = it.next();
 				System.out.print("{");
 				System.out.print("Orig:" + a.getOrigen()    + ", ");
 				System.out.print("Dest:" + a.getDestino()   + ", ");
 				System.out.print("Cost:" + a.getCosto()     + ", ");
 				System.out.print("Capa:" + a.getCapacidad() + "}");
 				System.out.println();
 			}
 		}
 	}
 	
 	/*
 	 * Usado por la rutina imprimir para imprimir el grafo
 	 * en un archivo de texto.
 	 */
 	private void priv_imprimir(PrintWriter output) {
 		output.println("Grafo:");
 		output.println("Grafo:\n");
 		for (int i=0; i < vertices.size(); i++) {
 			Iterator<Arista> it = this.getVertice(i).getAdyacentes().iterator();
 			while (it.hasNext()) {
 				Arista a = it.next();
 				output.print("{");
 				output.print("Orig:" + a.getOrigen()    + ", ");
 				output.print("Dest:" + a.getDestino()   + ", ");
 				output.print("Cost:" + a.getCosto()     + ", ");
 				output.print("Capa:" + a.getCapacidad() + "}");
 				output.println();
 			}
 		}
 		output.close();
 	}
 }