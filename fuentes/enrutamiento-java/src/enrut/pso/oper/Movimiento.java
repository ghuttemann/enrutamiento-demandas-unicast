/*
 * @(#)Movimiento.java
 */
package enrut.pso.oper;

import enrut.pso.Particula;

public interface Movimiento {
	public int[] mover(Particula p, Particula mejor, int[] factores);
}
