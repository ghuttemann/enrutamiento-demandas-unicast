/*
 * @(#)GrupoCaminos.java
 */
package enrut.grafo;

import java.util.Random;
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
	
	public Camino eliminar(int i) {
		return caminos.remove(i);		
	}
	
	public boolean estaVacio() {
		return caminos.isEmpty();
	}
	
	public void vaciar() {
		caminos.clear();
	}
	
	/**
	 * Elige al azar max caminos, los cuales son
	 * conservados. El resto (si hubiere) son
	 * desechados.
	 * 
	 * @param max La máxima cantidad de caminos a conservar.
	 */
	public void conservar(int max) {
		int cantCaminos = this.getCantCaminos();
		
		/*
		 * Si hay "max" o menos caminos,
		 * o si el grupo está vacío, no 
		 * hacemos nada.
		 */
		if (max >= cantCaminos || cantCaminos == 0)
			return;
		
		/*
		 * Si "max" es cero o negativo, debemos
		 * vaciar el grupo.
		 */
		if (max <= 0) {
			this.vaciar();
			return;
		}
		
		/* 
		 * En cualquier otro caso, eliminamos al 
		 * azar max caminos y los guardamos en una
		 * lista temporal, que finalmente reemplaza
		 * a la lista actual.
		 */
		Vector<Camino> temp = new Vector<Camino>();
		Random rand = new Random(System.currentTimeMillis());
		for (int i=0; i < max; i++) {
			int cantidad = this.getCantCaminos();
			int elegido = rand.nextInt(cantidad);
			Camino cam = this.eliminar(elegido);
			temp.add(cam);
		}
		
		/*
		 * Finalmente reemplazamos las listas
		 */
		this.caminos = temp;
	}
}
