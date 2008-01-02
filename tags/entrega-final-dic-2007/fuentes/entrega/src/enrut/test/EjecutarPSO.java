/*
 * @(#)EjecutarAPSO.java
 */
package enrut.test;

/*
 * Clase que realiza la ejecución del
 * algoritmo basado en PSO.
 */
public class EjecutarPSO {
	public static void main(String[] args) {
		Principal principal = new Principal(new AlgoritmoPSO());
		principal.ejecutar(args);
	}
}
