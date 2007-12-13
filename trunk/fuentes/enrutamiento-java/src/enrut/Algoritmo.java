/*
 * @(#)Algoritmo.java
 */
package enrut;

import enrut.utils.Config;

public interface Algoritmo {
	public void inicializar(Config conf);
	public boolean reinicializar();
	public void descartarIguales();
	public void evaluar();
	public void ejecutar();
	public Solucion getMejorSolucion();
	public void setMejorSolucion(Solucion s);
	public double getMejorCosto();
	public double getMejorFitness();
}
