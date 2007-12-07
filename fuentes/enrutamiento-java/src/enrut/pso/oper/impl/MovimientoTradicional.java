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
			
			int tmp = (factores[1]*(gbest-actual) + factores[0]*(pbest-actual));
			int auxiliar = Math.abs(Math.round(tmp / 100.0f));
			nuevaPos[i] = auxiliar % limite;
		}
		
		return nuevaPos;
	}
}
