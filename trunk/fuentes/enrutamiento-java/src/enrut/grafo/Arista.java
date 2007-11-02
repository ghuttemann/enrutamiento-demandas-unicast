/*
 * @(#)Arista.java
 */
package enrut.grafo;

/**
 * Implementa la arista de un grafo, representada
 * por el vertice destino y por el costo de la
 * arista. El vertice origen es aquel que la contiene
 * en su lista de adyacencias.
 *
 * @see Vertice
 * @see Grafo
 * @author Germán Hüttemann Arza
 */
public class Arista {
 	
 	/*
 	 * Un entero que representa el numero interno
 	 * asignado por el grafo al vertice origen de
 	 * la arista.
 	 */
	private int origen;
	
	/*
 	 * Un entero que representa el numero interno
 	 * asignado por el grafo al vertice destino de
 	 * la arista.
 	 */
	private int destino;
 	
 	/*
 	 * Un entero que representa el costo/peso
 	 * de la arista.
 	 */
	private double costo;
 	
 	/*
 	 * Un double que representa la capacidad
 	 * que soporta esta arista.
 	 */
	private double capacidad;
 	
 	/**
 	 * Construye una nueva arista
 	 *
 	 * @param destino El vertice destino de la arista.
 	 * @param costo El costo/peso de la arista.
 	 */
 	public Arista(int origen, int destino, double costo, double capacidad) {
 		this.origen    = origen;
 		this.destino   = destino;
 		this.costo     = costo;
 		this.capacidad = capacidad;
 	}
 	
 	@Override
 	public boolean equals(Object obj) {
 		String thisClass = this.getClass().getName();
 		String objClass  = this.getClass().getName();
 		
 		if (thisClass.equalsIgnoreCase(objClass)) {
 			Arista a = (Arista) obj;
 			return (this.origen == a.origen) && (this.destino == a.destino);
 		}
 		return false;
 	}
 	
 	public Arista clonar() {
 		return new Arista(origen, destino, costo, capacidad);
 	}

	public int getOrigen() {
		return origen;
	}

	public void setOrigen(int origen) {
		this.origen = origen;
	}

	public int getDestino() {
		return destino;
	}

	public void setDestino(int destino) {
		this.destino = destino;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public double getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(double capacidad) {
		this.capacidad = capacidad;
	}
 }