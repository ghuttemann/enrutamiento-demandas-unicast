/*
 * @(#)MutacionUnGen.java
 */
package enrut.ag.oper.impl.mutacion;

import java.util.Random;

import enrut.ag.Cromosoma;
import enrut.ag.oper.OperadorMutacion;

/*
 * Implementación de la operación de mutación que muta
 * un gen elegido al azar, cambiándolo de valor hasta
 * que sea realmente distinto al valor actual.
 */
public class MutacionUnGen implements OperadorMutacion {
	//@Override
	public void mutar(Cromosoma a) {
		Random rand = new Random(System.currentTimeMillis());
		rand.nextInt();
		
		// Cantidad de genes del cromosoma
		int cantGenes = a.getCantGenes();
		
		// Gen elegido aleatoriamente para mutar
		int elegido = rand.nextInt(cantGenes);
		
		/*
		 * Dominio sobre el cual puede cambiarse
		 * el gen, representado por la cantidad
		 * de caminos disponibles.
		 */
		int cantCaminos = a.getGrupoCaminos(elegido).getCantCaminos();
		
		/*
		 * Obtenemos el nuevo valor para el gen.
		 * Mientras el valor calculado sea el
		 * mismo al actual, volvemos a generarlo.
		 */
		int valorActual = a.getGen(elegido);
		int valorNuevo  = valorActual;
		while (valorNuevo == valorActual)
			valorNuevo = rand.nextInt(cantCaminos);
			
		// Cambiamos el valor del gen
		a.setGen(elegido, valorNuevo);
	}
}
