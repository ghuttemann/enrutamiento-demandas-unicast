/*
 * @(#)CruceUniforme.java
 */
package enrut.ag.oper.impl.cruce;

import java.util.Random;

import enrut.ag.Cromosoma;
import enrut.ag.oper.OperadorCruce;

/**
 * Implementación del operador
 * de cruzamiento uniforme.
 * 
 * @author ghuttemann
 */
public class CruceUniforme implements OperadorCruce {
	@Override
	public Cromosoma[] cruzar(Cromosoma a, Cromosoma b) {
		Cromosoma[] hijos = new Cromosoma[2];
		
		// Comprobamos que la demanda sea la misma
		if (a.getDemandas() != b.getDemandas())
			throw new Error("Las demandas no coinciden");
		
		/*
		 * Construimos los dos nuevos cromosomas.
		 * Utilizamos solamente el cromosoma "a" 
		 * para obtener las demandas y la 
		 * cantidad de genes ya que se supone que
		 * es igual a "b" en ese sentido.
		 */
		hijos[0] = new Cromosoma(a.getDemandas());
		hijos[1] = new Cromosoma(a.getDemandas());
		
		/*
		 * Aquí iremos guardando el gen "i" de
		 * cada uno de los cromosomas a cruzar,
		 * "a" y "b", en este caso, para luego
		 * realizar la selección de destinos.
		 */
		int[] genes = new int[2];
		
		Random rand = new Random(System.currentTimeMillis());
		for (int i=0; i < a.getCantGenes(); i++) {
			/*
			 * Tiramos la moneda para obtener el
			 * gen que incluiremos en el primer
			 * hijo. El otro, irá al segundo hijo.
			 */
			boolean moneda = rand.nextBoolean();
			
			/* 
			 * Realizamos la selección de destinos
			 */
			if (moneda) {
				genes[0] = a.getGen(i); // Gen de "a" al primer hijo
				genes[1] = b.getGen(i); // Gen de "b" al segundo hijo
			}
			else {
				genes[0] = b.getGen(i); // Gen de "b" al primer hijo
				genes[1] = a.getGen(i); // Gen de "a" al segundo hijo
			}
			
			/*
			 * Seteamos el gen "i" de cada hijo
			 */
			hijos[0].setGen(i, genes[0]);
			hijos[1].setGen(i, genes[1]);
		}
		
		return hijos;
	}
}
