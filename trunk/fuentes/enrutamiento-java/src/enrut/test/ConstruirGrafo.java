/*
 * @(#)ConstruirGrafo.java
 */
package enrut.test;

import enrut.Demanda;
import enrut.grafo.Camino;
import enrut.grafo.ConstructorGrafo;
import enrut.grafo.Grafo;
import enrut.grafo.PrintTo;

public class ConstruirGrafo {
	public static void main(String[] args) {
		ConstructorGrafo cg;
		Grafo grafo;
		
		System.out.println("¡¡¡LECTOR DE ARCHIVO GRAFO!!!");
		
		if (args.length != 1)
			throw new Error("Falta nombre de archivo");
		
		cg = new ConstructorGrafo();
		cg.leerGrafo(args[0]);
		grafo = cg.getGrafo();
		grafo.imprimir(PrintTo.STDOUT);
		
		//--------------| Calculo de Rutas|--------------------
		Demanda[] d = {new Demanda(), new Demanda(), new Demanda()};
		d[0].setOrigen(1);
		d[0].setDestino(9);
		d[0].setAnchoDeBanda(1.1);
		d[1].setOrigen(3);
		d[1].setDestino(7);
		d[1].setAnchoDeBanda(1.5);
		d[2].setOrigen(11);
		d[2].setDestino(9);
		d[2].setAnchoDeBanda(2.5);
		
		Camino[] rutas = {new Camino(),
						  new Camino(),
						  new Camino(),
						  new Camino(),
						  new Camino()};
		
		System.out.println("¡¡¡END OF PROGRAM!!!"+rutas);
	}

}
