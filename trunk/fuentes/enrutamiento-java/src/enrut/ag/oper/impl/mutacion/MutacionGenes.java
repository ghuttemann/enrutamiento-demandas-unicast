/*
 * @(#)MutacionGenes.java
 */
package enrut.ag.oper.impl.mutacion;

import java.util.Random;

import enrut.ag.Cromosoma;
import enrut.ag.oper.OperadorMutacion;

public class MutacionGenes implements OperadorMutacion {
	//@Override
	public void mutar(Cromosoma a) {
		Random rand = new Random();
		rand.nextInt();
		
		// Cantidad de genes del cromosoma
		int cantGenes = a.getCantGenes();
		
		for (int i=0; i<cantGenes; i++) {
			
			/*if (rand.nextInt(99)>99)
				continue; // si es el 80% no se muta el gen
			*/
			/*
			 * Dominio sobre el cual puede cambiarse
			 * el gen, representado por la cantidad
			 * de caminos disponibles.
			 */
			int cantCaminos = a.getGrupoCaminos(i).getCantCaminos();
			
			/*
			 * Obtenemos el nuevo valor para el gen.
			 * Mientras el valor calculado sea el
			 * mismo al actual, volvemos a generarlo.
			 */
			int valorActual = a.getGen(i);
			int valorNuevo  = valorActual;
			valorNuevo = rand.nextInt(cantCaminos);
				
			// Cambiamos el valor del gen
			a.setGen(i, valorNuevo);
		}
	}
}

