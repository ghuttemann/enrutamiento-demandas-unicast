/*
 * @(#)Vertice.java
 */
package enrut.grafo;

import java.util.List;
import java.util.Vector;
 
/**
 * Implementa un vertice de un grafo. Las Aristas
 * asociadas al vertice, estan representadas en 
 * una lista de adyacencias.
 *	
 * @see Arista
 * @see Grafo
 * @author Germán Hüttemann Arza
 */
public class Vertice {
 	/*
 	 * Lista de Aristas adyacentes al vertice.
 	 */
 	private Vector<Arista> adyacentes;
 	
 	/*
 	 * Para saber si fue visitado durante algún
 	 * recorrido sobre el grafo.
 	 */
 	private boolean visitado;
 	
 	/*
 	 * Identificador numerico
 	 */
 	private int id;
 	

	/** Construye un nuevo Vertice */
 	public Vertice(int id) {
 		this.id = id;
 		adyacentes = new Vector<Arista>();
 	}
 	
 	/**
 	 * Agrega una nueva Arista al Vertice.
 	 * @param a La Arista a ser agregada.
 	 */
 	public void agregarAdyacencia(Arista a) {
 		adyacentes.add(a);
 	}
 	
	/** Retorna la primera Arista adyacente al vertice. */
 	public List<Arista> getAdyacentes() {
 		return adyacentes;
 	}
 	
 	public int getCantAristas() {
 		return adyacentes.size();
 	}
 	
 	public Arista getArista(int i) {
 		return adyacentes.get(i);
 	}
 	
 	public boolean getVisitado() {
		return visitado;
	}

	public void setVisitado(boolean visitado) {
		this.visitado = visitado;
	}
	
	public int getId() {
		return id;
	}
	
	public String toString() {
		return String.valueOf(id);
	}
 }