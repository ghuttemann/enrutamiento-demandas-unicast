/*
 * @(#)MutacionUnGen.java
 */
package enrut.ag.oper.impl.mutacion;

import java.util.Random;

import enrut.ag.Cromosoma;
import enrut.ag.oper.OperadorMutacion;

public class MutacionUnGen implements OperadorMutacion {
	@Override
	public void mutar(Cromosoma a) {
		Random rand     = new Random(System.currentTimeMillis());
		int cantGenes   = a.getCantGenes();
		int elegido     = rand.nextInt(cantGenes);
		int cantCaminos = a.getDemandas()[elegido].getCaminos().getCantCaminos();
		int nuevoValor  = rand.nextInt(cantCaminos);
		a.setGen(elegido, nuevoValor);
	}
}
