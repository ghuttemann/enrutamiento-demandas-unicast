/*
 * @(#)Config.java
 */
package enrut.utils;

import enrut.ag.Demanda;

public class Config {
	private Demanda[] demandas;
	private int maxCaminos;
	private int maxTiempo;
	private int tamPoblacion;
	private int cantAristas;
	
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

	public int getMaxTiempo() {
		return maxTiempo;
	}

	public void setMaxTiempo(int maxTiempo) {
		this.maxTiempo = maxTiempo;
	}

	public int getTamPoblacion() {
		return tamPoblacion;
	}

	public void setTamPoblacion(int tamPoblacion) {
		this.tamPoblacion = tamPoblacion;
	}
	
	public int getCantAristas() {
		return cantAristas;
	}

	public void setCantAristas(int cantAristas) {
		this.cantAristas = cantAristas;
	}
}
