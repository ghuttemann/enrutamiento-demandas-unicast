/*
 * @(#)MovimientoTradicional.java
 */
package enrut.pso.oper.impl;

import enrut.pso.Particula;
import enrut.pso.oper.Movimiento;

/*
 * Implementación del movimiento de las partículas, basado
 * en el enfoque tradicional de PSO, pero sin la variable
 * de velocidad.
 * En base a la distancia de la posición actual respecto de
 * la mejor posición global y de la mejor posición personal
 * se calcula un desplazamiento para la partícula, el cual
 * se utiliza para actualizar su posición actual.
 */
public class MovimientoTradicional implements Movimiento {

	//@Override
	public int[] mover(Particula p, Particula mejorGlobal, int[] factores) {
		int size = p.getPosActual().length;
		int[] nuevaPos = new int[size];
		
		for (int i=0; i < size; i++) {
			int gbest  = mejorGlobal.getPosActual(i);
			int pbest  = p.getMejorPosicionPersonal(i);
			int actual = p.getPosActual(i);
			int limite = p.getGrupoCaminos(i).getCantCaminos();
			
			// Distancia de la posición actual al mejor posición personal
			int a = Math.abs(pbest - actual) * factores[0];
			
			// Distancia de la posición actual al mejor posición global
			int b = Math.abs(gbest - actual) * factores[1];
			
			// Suma de las distancias
			int c = a + b;
			
			/*
			 * Dividimos entre 100.0f para ajustar. Esto se
			 * debe a que los factores en vez de ir de 0 a 1,
			 * van de 0 a 100.
			 */
			int d = Math.round(c / 100.0f);
			
			// Desplazamos a la posición actual según el desplazamiento resultante
			int e = Math.abs(actual + d);
			
			/*
			 * Establecemos el nuevo valor. Aplicamos la operación
			 * módulo en caso de que el valor esté fuera del dominio
			 * permitido para la dimensión en cuestión.
			 */
			nuevaPos[i] = e % limite;
		}
		
		return nuevaPos;
	}
}
