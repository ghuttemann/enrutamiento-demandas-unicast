/*
 * @(#)EjecutarAG.java
 */
package enrut.test;

/*
 * Clase que realiza la ejecución del
 * algoritmo basado en GA.
 */
public class EjecutarAG {
	public static void main(String[] args) {
		Principal principal = new Principal(new AlgoritmoAG());
		principal.ejecutar(args);
	}
}
