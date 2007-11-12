/*
 * @(#)Poblacion.java
 */
package enrut.ag;

import java.util.Random;

import enrut.ag.oper.OperadorCruce;
import enrut.ag.oper.OperadorMutacion;
import enrut.ag.oper.OperadorSeleccion;

public class Poblacion {
	/*
	 * Individuos de la poblaci�n
	 */
	private Cromosoma[] individuos;
	
	/*
	 * Valor de calidad de un cromosoma 
	 */
	private double[] fitness;
	
	/*
	 * Operador de cruce
	 */
	private OperadorCruce operadorCruce;
	
	/*
	 * Operador de mutaci�n
	 */
	private OperadorMutacion operadorMutacion;
	
	/*
	 * Operador de selecci�n
	 */
	private OperadorSeleccion operadorSeleccion;
	
	/**
	 * Crea una nueva poblaci�n cant individuos
	 * @param demandas Las demandas solicitadas
	 * @param cant La cantidad de individuos a generar
	 */
	public Poblacion(Demanda[] demandas, int cant) {
		individuos = new Cromosoma[cant];
		
		for (int i=0; i < individuos.length; i++) {
			individuos[i] = new Cromosoma(demandas);
			individuos[i].generarGenes();
		}
	}
	
	/**
	 * Crea una poblaci�n con 32 individuos
	 * @param demandas Las demandas solicitadas
	 */
	public Poblacion(Demanda[] demandas) {
		this(demandas, 32);
	}
	
	public int getTama�o() {
		return individuos.length;
	}
	
	/**
	 * Compara cada individuo de la poblaci�n con
	 * los dem�s y reemplaza los cromosomas duplicados
	 * por nuevos, generados aleatoriamente.
	 */
	public void descartarIguales() {
		/*
		 * Falta implementar
		 */
	}

	/**
	 * Selecciona nuevos individuos de esta poblaci�n
	 * @return Cromosoma[] nuevos individuos seleccionados
	 */
	public Cromosoma[] seleccionar() {
		return operadorSeleccion.seleccionar(this);
	}
	
	/**
	 * Cruza los individuos seleccionados eliminando
	 * la poblaci�n vieja. 
	 * @param selectos
	 */
	public void cruzar(Cromosoma []selectos) {
		for (int i=0; i <= selectos.length-2; i = i+2){
			Cromosoma nuevos[];
			nuevos = operadorCruce.cruzar(selectos[i], selectos[i+1]);
			/*
			 * Los cromosomas que se van obteniendo
			 * tienen que ir siendo guardados en alguna
			 * estructura auxiliar, que sirva a la hora
			 * de realizar el reemplazo de poblaci�n.
			 */
		}
	}

	/**
	 * Muta cromosomas de la poblaci�n con una 
	 * problabilidad de mutar de 0.01
	 */
	public void mutar() {
		Random rand = new Random(System.currentTimeMillis());
		
		for (int i=0; i < this.getTama�o(); i++){
			if (rand.nextInt(100) > 98)
				operadorMutacion.mutar(individuos[i]);
		}
	}
	
	/**
	 * Realiza el reemplazo de individuos de
	 * la poblaci�n.
	 */
	public void reemplazar() {
		/*
		 * Falta implementar
		 */
	}

	public void evaluar() {
		for (int i=0; i<=this.getTama�o();i++) {
			fitness[i] = individuos[i].evaluar();
		}
	}
	
	public double getFitness(int ind) {
		return fitness[ind];
	}
	
	public Cromosoma getIndividuo(int pos) {
		return individuos[pos];
	}
	
	public OperadorCruce getOperadorCruce() {
		return operadorCruce;
	}

	public void setOperadorCruce(OperadorCruce operadorCruce) {
		this.operadorCruce = operadorCruce;
	}

	public OperadorMutacion getOperadorMutacion() {
		return operadorMutacion;
	}

	public void setOperadorMutacion(OperadorMutacion operadorMutacion) {
		this.operadorMutacion = operadorMutacion;
	}

	public OperadorSeleccion getOperadorSeleccion() {
		return operadorSeleccion;
	}

	public void setOperadorSeleccion(OperadorSeleccion operadorSeleccion) {
		this.operadorSeleccion = operadorSeleccion;
	}
}
