/*
 * @(#)MovimientoDiversidad.java
 */
package enrut.pso.oper.impl;

import enrut.pso.Particula;
import enrut.pso.oper.Movimiento;

public class MovimientoDiversidad implements Movimiento {

	//@Override
	public int[] mover(Particula p, Particula mejorGlobal, int[] factores) {
		int size = p.getPosActual().length;
		int[] nuevaPos = new int[size];
		
		for (int i=0; i < size; i++) {
			int gbest  = mejorGlobal.getPosActual(i);
			int pbest  = p.getMejorPosicionLocal(i);
			int actual = p.getPosActual(i);
			
			int tmp = (factores[2] * gbest) + (factores[1] * pbest) + (factores[0] * actual);
			nuevaPos[i] = Math.round(tmp / 100.0f);
		}
		
		return nuevaPos;
	}
}
