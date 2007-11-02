/*
 * @(#)Camino.java
 */
package enrut.grafo;

import java.util.Vector;

/**
 * Implementa un camino de un grafo, representado
 * por una secuencia de los vertices que lo componen.
 *
 * @see Arista
 * @see Vertice
 * @see Grafo
 * @author Germán Hüttemann Arza
 */
public class Camino {
 	private Vector<Arista> secuencia;
	private double costo;
	private int incremento;
	
	private final int INIT_INCR  = 5;
	private final int FIRST_INCR = 10;
	private final int NEXT_INCR  = 15;
 	
 	/**
 	 * Construye un nuevo camino
 	 */
 	public Camino() {
 		costo = 0;
 		incremento = INIT_INCR;
 		secuencia = new Vector<Arista>(10);
 	}
 	
 	public void agregarArista(Arista a) {
 		if (secuencia.capacity() == secuencia.size()) {
 			secuencia.ensureCapacity(secuencia.size() + incremento);
 			incremento = getSgteIncremento();
 		}
 		secuencia.add(a);
 		costo += a.getCosto();
 	}
 	
 	public Arista quitarArista() {
 		Arista a = secuencia.remove(secuencia.size()-1);
 		costo -= a.getCosto();
 		return a;
 	}
 	
 	public boolean getVacio() {
 		return secuencia.isEmpty();
 	}
 	
 	public int getLongitud() {
 		return secuencia.size();
 	}
 	
 	public Arista getArista(int i) {
 		return secuencia.get(i);
 	}
 	
 	public Arista getPrimeraArista() {
 		return secuencia.firstElement();
 	}
 	
 	public Arista getUltimaArista() {
 		return secuencia.lastElement();
 	}
 	
 	public double getCosto() {
 		return costo;
 	}
 	
 	private int getSgteIncremento() {
 		if (incremento == INIT_INCR)
 			return FIRST_INCR;
 		else if (incremento == FIRST_INCR)
 			return NEXT_INCR;
 		else
 			return 0;
 	}
 	
 	public void imprimir() {
 		System.out.print(" | ");
 		for (int i=0; i < secuencia.size()-1; i++)
 			System.out.print(getArista(i).getOrigen() + "-");
 		
 		System.out.println(getUltimaArista().getDestino() + " |");
 	}
 	
 	@Override
 	public boolean equals(Object obj) {
 		String thisClass = this.getClass().getName();
 		String objClass  = this.getClass().getName();
 		
 		if (thisClass.equalsIgnoreCase(objClass)) {
 			Camino cam = (Camino) obj;
 			if (this.secuencia.size() != cam.secuencia.size())
 				return false;
 			
 			for (int i=0; i < this.secuencia.size(); i++) {
 				Arista a = this.secuencia.get(i);
 				Arista b = cam.secuencia.get(i);
 				if (!a.equals(b))
 					return false;
 			}
 			return true;
 		}
 		return false;
 	}
 }