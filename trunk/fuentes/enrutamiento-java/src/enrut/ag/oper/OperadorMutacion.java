/*
 * @(#)OperadorMutacion.java
 */
package enrut.ag.oper;

import enrut.ag.Cromosoma;

public interface OperadorMutacion {
	/**
	 * Realiza una operación de mutación
	 * sobre un cromosoma dado.
	 * 
	 * @param a El cromosoma a ser mutado
	 * @return Un nuevo cromosoma
	 */
	public void mutar(Cromosoma a);
}
