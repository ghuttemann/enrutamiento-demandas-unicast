/*
 * @(#)AlgoritmoAPSO.java
 */
package enrut.test;

import enrut.Algoritmo;
import enrut.Solucion;
import enrut.pso.Enjambre;
import enrut.pso.Particula;
import enrut.pso.oper.impl.MovimientoTradicional;
import enrut.utils.Config;

public class AlgoritmoPSO implements Algoritmo {
	private Enjambre enjambre;
	
	public AlgoritmoPSO() {
	}

	@Override
	public void descartarIguales() {
		enjambre.descartarIguales();
	}

	@Override
	public void ejecutar() {
		enjambre.nuevasPosiciones();
	}

	@Override
	public void evaluar() {
		enjambre.evaluar();
	}

	@Override
	public double getMejorCosto() {
		return enjambre.getMejorCosto();
	}

	@Override
	public double getMejorFitness() {
		return enjambre.getMejorFitness();
	}

	@Override
	public Solucion getMejorSolucion() {
		return enjambre.getMejorParticula();
	}

	@Override
	public void inicializar(Config conf) {
		enjambre = inicializarEnjambre(conf);		
	}

	@Override
	public void setMejorSolucion(Solucion s) {
		Particula x = (Particula) s;
		enjambre.setMejorParticula(x);
	}
	
	@Override
	public boolean reinicializar() {
		return enjambre.reinicializar();
	}

	private static Enjambre inicializarEnjambre(Config conf) {
		Enjambre p = new Enjambre(conf.getDemandas(), 
								  conf.getTamPoblacionPSO(),
								  conf.getCantAristas());
		
		p.setPorcentajeReinicializacion(conf.getPorcReinicioPSO() / 100.0);
		p.setOperadorMovimiento(new MovimientoTradicional());
		return p;
	}
}
