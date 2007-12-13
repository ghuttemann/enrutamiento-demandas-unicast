/*
 * @(#)EjecutarAG.java
 */
package enrut.test;

public class EjecutarAG {
	public static void main(String[] args) {
		Principal principal = new Principal(new AlgoritmoAG());
		principal.ejecutar(args);
	}
}
