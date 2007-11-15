/*
 * @(#)ConstruirCaminos.java
 */
package enrut.test;

import enrut.ag.Demanda;
import enrut.grafo.Arista;
import enrut.grafo.Camino;
import enrut.grafo.ConstructorCaminos;
import enrut.grafo.GrupoCaminos;

public class ConstruirCaminos {
	public static void main(String[] args) {
		ConstructorCaminos cc = new ConstructorCaminos();
		Demanda[] demandas    = {new Demanda(0, 1, 1)};
		
		if (args.length != 1)
			throw new Error("Falta nombre de archivo");
		
		cc.leerCaminos(demandas, 0, args[0]);
		
		for (int i=0; i < demandas.length; i++) {
			GrupoCaminos gc = demandas[i].getGrupoCaminos();
			System.out.print("Demanda ");
			System.out.print(demandas[i].getOrigen() + " a ");
			System.out.print(demandas[i].getDestino());
			System.out.println();
			for (int j=0; j < gc.getCantCaminos(); j++) {
				Camino cam = gc.getCamino(j);
				System.out.print("Camino " + j + " -> ");
				for (int k=0; k < cam.getLongitud(); k++) {
					Arista a = cam.getArista(k);
					System.out.print(a.getOrigen() + ":");
					System.out.print(a.getDestino());
					System.out.print("/");
				}
				System.out.println();
			}
			System.out.println();
		}
	}
}
