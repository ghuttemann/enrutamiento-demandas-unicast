/*
 * @(#)GenerarCaminos.java
 */
package enrut.test;

import enrut.grafo.ConstructorGrafo;
import enrut.grafo.GeneradorCaminos;
import enrut.grafo.Grafo;

public class GenerarCaminos {
	public static void main(String[] args) {
		ConstructorGrafo constGrafo;
		Grafo grafo;
		
		if (args.length != 2)
			throw new Error("Falta nombre de archivo");
		
		constGrafo = new ConstructorGrafo();
		constGrafo.leerGrafo(args[0]);
		grafo = constGrafo.getGrafo();
		GeneradorCaminos.generar(grafo, args[1]);
	}
}
