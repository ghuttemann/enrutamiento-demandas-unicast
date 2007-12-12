/*
 * @(#)MutacionGenes.java
 */
package enrut.ag.oper.impl.mutacion;

import java.util.Random;
import enrut.ag.Cromosoma;
import enrut.ag.oper.OperadorMutacion;

/*
 * Implementación de la operación de mutación que muta
 * todos los genes del cromosoma.
 */
public class MutacionGenes implements OperadorMutacion {
	//@Override
	public void mutar(Cromosoma a) {
		Random rand = new Random();
		rand.nextInt();
		
		// Cantidad de genes del cromosoma
		int cantGenes = a.getCantGenes();
		
		for (int i=0; i<cantGenes; i++) {
			
			/*
			 * Dominio sobre el cual puede cambiarse
			 * el gen, representado por la cantidad
			 * de caminos disponibles.
			 */
			int cantCaminos = a.getGrupoCaminos(i).getCantCaminos();
			
			/*
			 * Obtenemos el nuevo valor para el gen. Si es el 
			 * mismo, no hacemos nada. Es muy improbable que 
			 * la gran mayoria de los genes quede con el mismo
			 * valor, por lo que esta decisión hace ahorrar un 
			 * poco de tiempo a la hora de realizar la operación 
			 * de mutación, haciendo que sea O(1) por cada gen.
			 */
			int valorNuevo = rand.nextInt(cantCaminos);
				
			// Cambiamos el valor del gen
			a.setGen(i, valorNuevo);
		}
	}
}
