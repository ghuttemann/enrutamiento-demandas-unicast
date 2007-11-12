/*
 * @(#)Config.java
 */
package enrut.utils;

import enrut.ag.Demanda;

public class Config {
	private Demanda[] demandas;
	private int maxCaminos;
	private int maxIteraciones;
	private int tamPoblacion;
	
	public Config() {
		
	}

	public Demanda[] getDemandas() {
		return demandas;
	}

	public void setDemandas(Demanda[] demandas) {
		this.demandas = demandas;
	}

	public int getMaxCaminos() {
		return maxCaminos;
	}

	public void setMaxCaminos(int maxCaminos) {
		this.maxCaminos = maxCaminos;
	}

	public int getMaxIteraciones() {
		return maxIteraciones;
	}

	public void setMaxIteraciones(int maxIteraciones) {
		this.maxIteraciones = maxIteraciones;
	}

	public int getTamPoblacion() {
		return tamPoblacion;
	}

	public void setTamPoblacion(int tamPoblacion) {
		this.tamPoblacion = tamPoblacion;
	}
}
