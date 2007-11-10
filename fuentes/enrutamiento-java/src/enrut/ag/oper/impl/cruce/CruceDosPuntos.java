/**
 * 
 */
package enrut.ag.oper.impl.cruce;

import enrut.ag.Cromosoma;
import enrut.ag.oper.OperadorCruce;

/**
 * @author Administrador
 *
 */
public class CruceDosPuntos implements OperadorCruce{

	public Cromosoma[] cruzar(Cromosoma a, Cromosoma b){
		
		Cromosoma hijos[] = new Cromosoma[2];
		hijos[0] = new Cromosoma(a.getDemandas());
		hijos[1] = new Cromosoma(a.getDemandas());
		
		// se calculan los puntos de cruce
		int size =hijos[0].getCantGenes();
		int punto1 = 0;
		int punto2 = 0;
		if (size >=3){
			punto1= size/3;
			punto2 = size-punto1;
		}

		// se generan los dos hijos
		for (int i=0; i<size;i++){
			if (i<punto1){
				hijos[0].setGen(i, a.getGen(i));
				hijos[1].setGen(i, b.getGen(i));
			} else if (i>=punto1 && i<punto2){
				hijos[0].setGen(i, b.getGen(i));
				hijos[1].setGen(i, a.getGen(i));				
			} else { // i>=punto2
				hijos[0].setGen(i, a.getGen(i));
				hijos[1].setGen(i, b.getGen(i));
			}
		}
		return hijos;
	}
	
}
