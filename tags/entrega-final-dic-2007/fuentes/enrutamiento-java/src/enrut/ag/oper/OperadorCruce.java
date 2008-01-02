/*
 * @(#)OperadorCruce.java
 */
package enrut.ag.oper;

import enrut.ag.Cromosoma;

public interface OperadorCruce {
	/**
	 * Realiza una operación de cruce entre dos
	 * cromosomas, produciendo un arreglo de 
	 * cromosomas hijos.
	 * 
	 * @param a Primer cromosoma a cruzar
	 * @param b Segundo cromosoma a cruzar
	 * @return Cromosomas hijos
	 */
	public Cromosoma[] cruzar(Cromosoma a, Cromosoma b);
}
