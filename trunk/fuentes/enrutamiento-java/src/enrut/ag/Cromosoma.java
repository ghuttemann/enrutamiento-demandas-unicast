/*
 * @(#)Cromosoma.java
 */
package enrut.ag;

import java.util.Random;

public class Cromosoma {
	/*
	 * Genes del cromosoma
	 */
	private int[] genes;
	
	
	/*
	 * Demandas realizadas
	 */
	private Demanda[] demandas;
	
	
	/**
	 * Construye un nuevo cromosoma con alelos
	 * randómicos para sus genes.
	 * @param demandas Las demandas solicitadas
	 */
	public Cromosoma(Demanda[] demandas) {
		this.demandas = demandas;
		genes = new int[demandas.length];
		
		/*
		 * Seteamos los genes del cromosoma con
		 * alelos randómicos.
		 */
		Random rand = new Random(System.currentTimeMillis());
		for (int i=0; i < genes.length; i++) {
			int cantCaminos = demandas[i].getCaminos().getCantCaminos();
			genes[i] = rand.nextInt(cantCaminos);
		}
	}
}
