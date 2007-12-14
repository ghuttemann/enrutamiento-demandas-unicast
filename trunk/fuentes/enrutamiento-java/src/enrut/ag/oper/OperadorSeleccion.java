/*
 * @(#)OperadorSeleccion.java
 */
package enrut.ag.oper;

import enrut.ag.Cromosoma;
import enrut.ag.Poblacion;

public interface OperadorSeleccion {
	/**
	 * Realiza una operación de selección
	 * sobre una población de individuos.
	 * 
	 * @param p La población sobre la cual realizar la selección
	 * @return Cromosomas seleccionados
	 */
	public Cromosoma[] seleccionar(Poblacion p);
}
