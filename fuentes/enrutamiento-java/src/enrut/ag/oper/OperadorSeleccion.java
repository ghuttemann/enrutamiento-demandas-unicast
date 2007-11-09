/*
 * @(#)OperadorSelecci�n.java
 */
package enrut.ag.oper;

import enrut.ag.Cromosoma;
import enrut.ag.Poblacion;

public interface OperadorSeleccion {
	/**
	 * Realiza una operaci�n de selecci�n
	 * sobre una poblaci�n de individuos.
	 * 
	 * @param p La poblaci�n sobre la cual realizar la selecci�n
	 * @return Cromosomas seleccionados
	 */
	public Cromosoma[] seleccionar(Poblacion p);
}
