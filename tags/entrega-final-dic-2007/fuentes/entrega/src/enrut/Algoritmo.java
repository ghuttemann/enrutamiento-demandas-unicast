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
	/**
	 * Inicializa la población/enjambre con
	 * la configuración recibida como argumento.
	 */
	public void inicializar(Config conf);
	
	/**
	 * Verifica si es necesario reinicializar
	 * la población/enjambre.
	 */
	public boolean reinicializar();
	
	/**
	 * Realiza el proceso de descartar
	 * cromosomas/partículas iguales.
	 */
	public void descartarIguales();
	
	/**
	 * Realiza la evaluación, asignación de
	 * costos y fitness de cromosomas/particulas.
	 */
	public void evaluar();
	
	/**
	 * Realiza las operaciones principales
	 * del algoritmo en cuestión.
	 */
	public void ejecutar();
	
	/**
	 * Obtiene la mejor solución de la
	 * historia de la población/enjambre.
	 */
	public Solucion getMejorSolucion();
	
	/**
	 * Setea la mejor solución de la
	 * historia de la población/enjambre.
	 */
	public void setMejorSolucion(Solucion s);
	
	/**
	 * Obtiene el costo de la mejor solución
	 * de la historia de la población/enjambre.
	 */
	public double getMejorCosto();
	
	/**
	 * Obtiene el fitness de la mejor solución
	 * de la historia de la población/enjambre.
	 */
	public double getMejorFitness();
}
