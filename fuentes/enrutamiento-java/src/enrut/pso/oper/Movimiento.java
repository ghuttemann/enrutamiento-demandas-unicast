/*
 * @(#)Movimiento.java
 */
package enrut.pso.oper;

import enrut.pso.Particula;

public interface Movimiento {	
	/**
	 * Calcula la nueva posición de una partícula dada.
	 * 
	 * @param p Partícula cuya nueva posición se calculará
	 * @param mejor Mejor partícula de la historia del enjambre
	 * @param factores Factores de movimiento
	 */
	public int[] mover(Particula p, Particula mejor, int[] factores);
}
