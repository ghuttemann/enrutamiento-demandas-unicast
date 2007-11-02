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
 * @author Germ�n H�ttemann Arza
 */
public class Vertice {
 	
 	/*
 	 * Lista de Aristas adyacentes al vertice.
 	 */
 	private Vector<Arista> adyacentes;
 	
 	/** Construye un nuevo Vertice */
 	public Vertice() {
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
 }