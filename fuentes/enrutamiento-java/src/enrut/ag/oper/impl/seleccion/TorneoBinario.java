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
		if (p==null)
			System.exit(0);
		int cantMejores = p.getTama�o(); //tama�o de poblaci�n seleccionada
		Cromosoma Mejores[] = new Cromosoma[cantMejores];
		Random rand = new Random();
		rand.nextInt();
		
		for (int i=0; i<cantMejores; i++) {
			
			int ind1 = rand.nextInt(p.getTama�o()); // se elige un individuo
			int ind2 = rand.nextInt(p.getTama�o()); // se elige un individuo
			while (ind2==ind1) {
				ind2 = rand.nextInt(p.getTama�o()); // se reelige un individuo
			}
			
			// Se extrae los fitness de los correspondientes individuos 
			double costo1 = p.getFitness(ind1);
			double costo2 = p.getFitness(ind2);
			
			if (costo1==0.0 && costo2==0.0) {
				i -= 1;
				continue;
			}
			
			// Competencia
			if (costo1>=costo2) { // Gan� individuo 1
				Mejores[i]=p.getIndividuo(ind1);
			}
			else { // Gan� individuo 2
				Mejores[i]=p.getIndividuo(ind2);
			}
		}

		return Mejores;
	}
}
