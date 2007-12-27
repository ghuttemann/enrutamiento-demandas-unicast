/*
 * @(#)GrupoCaminos.java
 */
package enrut.grafo;

import java.util.Random;
import java.util.Vector;

/**
 * Representa un grupo de caminos. Esta clase es
 * utilizada para agrupar todos los caminos asociados
 * a una determinada demanda.
 */
public class GrupoCaminos {
	/*
	 * Vector de caminos
	 */
	private Vector<Camino> caminos;
	
	/** Construye un nuevo grupo de caminos */
	public GrupoCaminos() {
		caminos = new Vector<Camino>();
	}
	
	/** Agrega un nuevo camino al grupo */
	public void agregarCamino(Camino cam) {
		caminos.add(cam);
	}
	
	/** Obtiene el i-ésimo camino del grupo */
	public Camino getCamino(int i) {
		return caminos.get(i);
	}
	
	/** Obtiene la cantidad de caminos del grupo */
	public int getCantCaminos() {
		return caminos.size();
	}
	
	/** Elimina el i-ésimo camino del grupo */
	public Camino eliminar(int i) {
		return caminos.remove(i);		
	}
	
	/** Verifica si el grupo de caminos está vacío */
	public boolean estaVacio() {
		return caminos.isEmpty();
	}
	
	/** Vacia el grupo de caminos */
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
		rand.nextInt();
		
		for (int i=0; i < max; i++) {
			// Cantidad de caminos
			int cantidad = this.getCantCaminos();
			
			// i-ésimo Camino elegido para conserverse
			int elegido = rand.nextInt(cantidad);
			
			// Eliminamos del grupo y lo retenemos temporalmente
			Camino cam = this.eliminar(elegido);
			temp.add(cam);
		}
		
		/*
		 * Finalmente reemplazamos las listas
		 */
		this.caminos = temp;
	}
}
