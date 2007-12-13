/*
 * @(#)AlgoritmoAG.java
 */
package enrut.test;

import enrut.Algoritmo;
import enrut.Solucion;
import enrut.ag.Poblacion;
import enrut.ag.Cromosoma;
import enrut.ag.oper.impl.cruce.CruceUniforme;
import enrut.ag.oper.impl.mutacion.MutacionGenes;
import enrut.ag.oper.impl.seleccion.TorneoBinario;
import enrut.utils.Config;

public class AlgoritmoAG implements Algoritmo {
	private Poblacion poblacion;
	
	public AlgoritmoAG() {
	}

	@Override
	public void descartarIguales() {
		poblacion.descartarIguales();
	}

	@Override
	public void ejecutar() {
		Cromosoma[] selectos = poblacion.seleccionar();
		poblacion.cruzar(selectos);
		poblacion.mutar();
		poblacion.reemplazar();
	}

	@Override
	public void evaluar() {
		poblacion.evaluar();
	}

	@Override
	public double getMejorCosto() {
		return poblacion.getMejorCosto();
	}

	@Override
	public double getMejorFitness() {
		return poblacion.getMejorFitness();
	}

	@Override
	public Solucion getMejorSolucion() {
		return poblacion.getMejorIndividuo();
	}

	@Override
	public void inicializar(Config conf) {
		poblacion = inicializarPoblacion(conf);		
	}

	@Override
	public void setMejorSolucion(Solucion s) {
		Cromosoma x = (Cromosoma) s;
		poblacion.setMejorIndividuo(x);
	}
	
	@Override
	public boolean reinicializar() {
		return poblacion.reinicializar();
	}

	private static Poblacion inicializarPoblacion(Config conf) {
		Poblacion p = new Poblacion(conf.getDemandas(), 
									conf.getTamPoblacionAG(),
									conf.getCantAristas(),
									conf.getProbMutacionAG());
		
		p.setPorcentajeReinicializacion(conf.getPorcReinicioAG() / 100.0);
	
		p.setOperadorCruce(new CruceUniforme());
		p.setOperadorMutacion(new MutacionGenes());
		p.setOperadorSeleccion(new TorneoBinario());	
		return p;
	}
}
