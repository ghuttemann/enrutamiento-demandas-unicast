/*
 * @(#)EjecutarAPSO.java
 */
package enrut.test;

public class EjecutarPSO {
	public static void main(String[] args) {
		Principal principal = new Principal(new AlgoritmoPSO());
		principal.ejecutar(args);
	}
}
