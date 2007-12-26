/*
 * @(#)Algoritmo.java
 */
package enrut;

import enrut.utils.Config;

/*
 * Interfaz utilizada como abstracción para
 * manejar ambos algoritmos, GA y PSO, de 
 * igual manera.
 */
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
