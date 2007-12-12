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
			
			// Distancia del actual al mejor personal
			int a = Math.abs(pbest - actual) * factores[0];
			
			// Distancia del actual al mejor global
			int b = Math.abs(gbest - actual) * factores[1];
			
			// Suma de las distancias
			int c = a + b;
			
			/*
			 * Dividimos entre 100.0f para ajustar. Esto se
			 * debe a que los factores en vez de ir de 0 a 1,
			 * va de 0 a 100.
			 */
			int d = Math.round(c / 100.0f);
			
			// Desplazamos al actual según la resultante
			int e = Math.abs(actual + d);
			
			// Establecemos el nuevo valor
			nuevaPos[i] = e % limite;
		}
		
		return nuevaPos;
	}
}
