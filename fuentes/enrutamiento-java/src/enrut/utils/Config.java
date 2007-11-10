/*
 * @(#)Config.java
 */
package enrut.utils;

import enrut.ag.Demanda;
import enrut.ag.oper.OperadorCruce;
import enrut.ag.oper.OperadorMutacion;
import enrut.ag.oper.OperadorSeleccion;

public class Config {
	private OperadorSeleccion seleccion;
	private OperadorCruce cruce;
	private OperadorMutacion mutacion;
	private Demanda[] demandas;
	private String path;
	
	public Config(String file) {
		try {
			Class c = Class.forName("TorneoBinario");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
