/*
 * @(#)MovimientoTradicional.java
 */
package enrut.pso.oper.impl;

import enrut.pso.Particula;
import enrut.pso.oper.Movimiento;

public class MovimientoTradicional implements Movimiento {

	//@Override
	public int[] mover(Particula p, Particula mejorGlobal, int[] factores) {
		int size = p.getPosActual().length;
		int[] nuevaPos = new int[size];
		
		for (int i=0; i < size; i++) {
			int gbest  = mejorGlobal.getPosActual(i);
			int pbest  = p.getMejorPosicionLocal(i);
			int actual = p.getPosActual(i);
			int limite = p.getGrupoCaminos(i).getCantCaminos();
			
			int a = Math.abs(pbest - actual) * factores[0];
			int b = Math.abs(gbest - actual) * factores[1];
			int c = a + b;
			int d = Math.round(c / 100.0f);
			
			nuevaPos[i] = d % limite;
		}
		
		return nuevaPos;
	}
}
