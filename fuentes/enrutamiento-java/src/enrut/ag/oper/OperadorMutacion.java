/*
 * @(#)OperadorMutacion.java
 */
package enrut.ag.oper;

import enrut.ag.Cromosoma;

public interface OperadorMutacion {
	/**
	 * Realiza una operaci�n de mutaci�n
	 * sobre un cromosoma dado.
	 * 
	 * @param a El cromosoma a ser mutado
	 * @return Un nuevo cromosoma
	 */
	public void mutar(Cromosoma a);
}
