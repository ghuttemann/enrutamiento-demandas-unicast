/*
 * @(#)MovimientoDiversidad.java
 */
package enrut.pso.oper.impl;

import enrut.pso.Particula;
import enrut.pso.oper.Movimiento;

/*
 * Implementación del movimiento de partículas, en la
 * que el movimiento de las partículas está calculada
 * como un valor ponderado de la mejor posición global,
 * mejor posición personal y posición actual.
 */
public class MovimientoDiversidad implements Movimiento {

	//@Override
	public int[] mover(Particula p, Particula mejorGlobal, int[] factores) {
		int size = p.getPosActual().length;
		int[] nuevaPos = new int[size];
		
		for (int i=0; i < size; i++) {
			int gbest  = mejorGlobal.getPosActual(i);
			int pbest  = p.getMejorPosicionPersonal(i);
			int actual = p.getPosActual(i);
			
			// Suma ponderada
			int tmp = (factores[2] * gbest) + (factores[1] * pbest) + (factores[0] * actual);
			nuevaPos[i] = Math.round(tmp / 100.0f);
		}
		
		return nuevaPos;
	}
}
