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
	 * hijos de los individuos selectos
	 */
	private Cromosoma[] hijos;
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
	
	/*
	 * Mejor cromosoma de toda la historia
	 */
	Cromosoma mejorIndividuo = null;
	
	/**
	 * Crea una nueva poblaci�n cant individuos
	 * @param demandas Las demandas solicitadas
	 * @param cant La cantidad de individuos a generar
	 */
	public Poblacion(Demanda[] demandas, int cant) {
		individuos = new Cromosoma[cant];
		hijos = new Cromosoma[cant];
		fitness = new double[cant];
		
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
	 * los dem�s y modifica los cromosomas duplicados
	 * mutandolos.
	 */
	public void descartarIguales() {
		for (int i=0; i<this.getTama�o()-1; i++) {
			for (int j=i+1; j<this.getTama�o(); j++) {
				if (individuos[i].equals(individuos[j])) {
					operadorMutacion.mutar(individuos[j]);
				}
			}
		}
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
			
			this.hijos[i] = nuevos[0];
			this.hijos[i+1] = nuevos[1];
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
		for (int i =0; i<this.getTama�o(); i++)
			individuos[i] = hijos[i];
	}

	public void evaluar() {
		for (int i=0; i<this.getTama�o();i++) {
			fitness[i] = individuos[i].evaluar();
		}
		elegirMejor();
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
	
	private void elegirMejor() {
		if (mejorIndividuo == null)
			mejorIndividuo = individuos[0];
		
		for (int i=0; i < this.getTama�o(); i++) {
			if (individuos[i].getCosto() < mejorIndividuo.getCosto())
				mejorIndividuo = individuos[i];
		}
	}
	
	public Cromosoma getMejorIndividuo() {
		return this.mejorIndividuo;
	}
	
	public void imprimir(){
		for (int i=0; i<this.getTama�o(); i++){
			System.out.println("Cromosoma: "+i+" ");
			System.out.println("Fitness  : "+fitness[i]);
			individuos[i].imprimir();
			System.out.println();
		}
	}
}
