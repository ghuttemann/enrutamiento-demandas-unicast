/*
 * @(#)TorneoBinario.java
 */
package enrut.ag.oper.impl.seleccion;

import java.util.Random;

import enrut.ag.Cromosoma;
import enrut.ag.Poblacion;
import enrut.ag.oper.OperadorSeleccion;

public class TorneoBinario implements OperadorSeleccion {
	//@Override
	public Cromosoma[] seleccionar(Poblacion p) {
		if (p == null)
			throw new Error("La población es nula (POB=NULL)");
		
		// Tamaño de población seleccionada
		int cantMejores = p.getTamaño();
		
		// Cromosomas seleccionados
		Cromosoma[] mejores = new Cromosoma[cantMejores];
		
		Random rand = new Random();
		rand.nextInt();
		
		for (int i=0; i<cantMejores; i++) {
			// Se eligen a dos individuos (torneo "binario")
			int ind1 = rand.nextInt(p.getTamaño());
			int ind2 = rand.nextInt(p.getTamaño());
			
			/*
			 * Nos aseguramos que los individuos
			 * seleccionados sean distintos.
			 */ 
			while (ind2 == ind1) {
				ind2 = rand.nextInt(p.getTamaño());
			}
			
			// Se extrae los fitness de los correspondientes individuos 
			double costo1 = p.getFitness(ind1);
			double costo2 = p.getFitness(ind2);
				
			// Competencia
			if (costo1 >= costo2) {
				// Ganó individuo 1
				mejores[i] = p.getIndividuo(ind1);
			}
			else {
				// Ganó individuo 2
				mejores[i] = p.getIndividuo(ind2);
			}
		}

		return mejores;
	}
}
