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
	}
	
	/**
	 * Setea los genes del cromosoma con 
	 * alelos randómicos.
	 */
	public void generarGenes() {
		Random rand = new Random(System.currentTimeMillis());
		for (int i=0; i < genes.length; i++) {
			int cantCaminos = demandas[i].getCaminos().getCantCaminos();
			genes[i] = rand.nextInt(cantCaminos);
		}
	}
	
	public void setGen(int pos, int valor) {
		this.genes[pos] = valor;
	}
	
	public int getGen(int pos) {
		return this.genes[pos];
	}
	
	public Demanda[] getDemandas() {
		return this.demandas;
	}
	
	public int getCantGenes() {
		return this.genes.length;
	}
}
