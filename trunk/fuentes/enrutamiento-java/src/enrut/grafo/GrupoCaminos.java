/*
 * @(#)GrupoCaminos.java
 */
package enrut.grafo;

import java.util.Vector;


public class GrupoCaminos {
	private Vector<Camino> caminos;
	
	public GrupoCaminos() {
		caminos = new Vector<Camino>();
	}
	
	public void agregarCamino(Camino cam) {
		caminos.add(cam);
	}
	
	public Camino getCamino(int i) {
		return caminos.get(i);
	}
	
	public int getCantCaminos() {
		return caminos.size();
	}
}
