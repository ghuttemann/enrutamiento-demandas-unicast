/*
 * @(#)Demanda.java
 */
package enrut.ag;

import enrut.grafo.GrupoCaminos;

public class Demanda {
	/*
	 * El vertice origen de la demanda.
	 */
	private int origen;
	
	/*
	 * El vertice destino de la demanda.
	 */
	private int destino;
	
	/*
	 * El ancho de banda requerido por la
	 * Demanda. Medido en KB/s.
	 */
	private double anchoDeBanda;
	
	/*
	 * Caminos para la demanda
	 */
	private GrupoCaminos caminos;
	
	/**
	 * Construye una nueva Demanda.
	 * 
	 * @param origen El Vertice origen de la Demanda.
	 * @param destino El Vertice destino de la Demanda.
	 * @param anchoDeBanda El ancho de banda requerido por la Demanda.
	 */
	public Demanda(int origen, int destino, double anchoDeBanda) {
		this.origen       = origen;
		this.destino      = destino;
		this.anchoDeBanda = anchoDeBanda;
	}
	
	public Demanda() {
		this(-1, -1, -1);
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

	public double getAnchoDeBanda() {
		return anchoDeBanda;
	}

	public void setAnchoDeBanda(double anchoDeBanda) {
		this.anchoDeBanda = anchoDeBanda;
	}

	public GrupoCaminos getCaminos() {
		return caminos;
	}

	public void setCaminos(GrupoCaminos caminos) {
		this.caminos = caminos;
	}
}