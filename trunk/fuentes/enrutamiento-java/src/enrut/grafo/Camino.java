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
	
	/*
	 * Clave que representa univocamente a este
	 * camino, en funcion de las aristas que lo
	 * componen.
	 */
	private StringBuffer clave;
	
	private final int INIT_INCR  = 5;
	private final int FIRST_INCR = 10;
	private final int NEXT_INCR  = 15;
 	
 	/**
 	 * Construye un nuevo camino
 	 */
 	public Camino() {
 		clave = new StringBuffer();
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
 		
 		// Agregamos la clave de la Arista
 		clave.append(a.getClave());
 	}
 	
 	public Arista quitarArista() {
 		Arista a = secuencia.remove(secuencia.size()-1);
 		costo -= a.getCosto(); 		
 		
 		// Removemos la clave de la Arista
 		int posIni = clave.indexOf(a.getClave());
 		int posFin = clave.indexOf("]", posIni);
 		clave.delete(posIni, posFin+1);
 		
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
 		
 		/* 
 		 * Proveemos una implementacion "O(1)" de la
 		 * comparacion entre los Caminos.
 		 */
 		if (thisClass.equalsIgnoreCase(objClass)) {
 			Camino cam = (Camino) obj;
 			return getClave().equals(cam.getClave());
 		}
 		else {
 			return false;
 		}
 	}
 	
 	public String getClave() {
 		return clave.toString();
 	}
 	
 	public Camino clonar() {
 		Camino cam = new Camino();
 		
 		for (int i=0; i < this.getLongitud(); i++)
 			cam.agregarArista(this.getArista(i));
 		
 		return cam;
 	}
 	
 	public String toString() {
 		StringBuffer buffer = new StringBuffer();
 		buffer.append(1 + this.getLongitud() + ":");
 		buffer.append(this.getCosto() + ":");
 		
 		for (int i=0; i < this.getLongitud(); i++) {
 			Arista a = this.getArista(i);
 			buffer.append(a.getOrigen());
 			buffer.append("-");
 		}
 		
 		buffer.append(this.getUltimaArista().getDestino());
 		return buffer.toString();
 	}
 	
 	public void vaciar() {
 		while (!this.getVacio())
 			this.quitarArista();
 	}
 }