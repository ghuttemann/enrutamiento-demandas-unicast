/*
 * @(#)MovimientoLima.java
 */
package enrut.pso.oper.impl;

import java.util.Random;

import enrut.pso.Particula;
import enrut.pso.oper.Movimiento;

public class MovimientoLima implements Movimiento {

	@Override
	public int[] mover(Particula p, Particula mejorGlobal, int[] factores) {
		Random rand = new Random();
		rand.nextInt();
		
		int size = p.getPosActual().length;
		int [] nuevaPos = new int[size];
		
		for (int i=0; i < size; i++) {
			
			int r = rand.nextInt(100)+1; // valor entre 1 y 100
			
			// Seleccionar Mejor Global
			if (r <= factores[2]) { 
				nuevaPos[i] = mejorGlobal.getPosActual(i);
			}
			// Seleccionar Mejor Personal
			else if (r <= factores[2]+factores[1]) { 
				nuevaPos[i] = p.getMejorPosicion(i);
			}
			// Seleccionar de la posicion actual
			else {
				nuevaPos[i] = p.getPosActual(i);
			}
		}
		return nuevaPos;
	}
}
