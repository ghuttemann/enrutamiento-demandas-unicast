/*
 * @(#)GenerarCaminos.java
 */
package enrut.test;

import enrut.grafo.ConstructorGrafo;
import enrut.grafo.GeneradorCaminos;
import enrut.grafo.Grafo;
import enrut.utils.FormatDir;

/*
 * Dado un grafo, genera los caminos entre sus vertices
 */
public class GenerarCaminos {
	public static void main(String[] args) {
		ConstructorGrafo constGrafo;
		Grafo grafo;
		String path;
		
		if (args.length != 1)
			throw new Error("Directorio de configuración");
		
		
		path = FormatDir.format(args[0]);
		constGrafo = new ConstructorGrafo();
		constGrafo.leerGrafo(path + "grafo.txt");
		grafo = constGrafo.getGrafo();
		GeneradorCaminos.generar(grafo, path);
	}
}
