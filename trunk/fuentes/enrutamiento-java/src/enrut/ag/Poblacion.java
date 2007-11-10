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
	 * Individuos de la población
	 */
	private Cromosoma[] individuos;
	
	/*
	 * Valor de calidad de un cromosoma 
	 */
	private Double[] fitness;
	/*
	 * Operador de cruce
	 */
	private OperadorCruce operadorCruce;
	
	/*
	 * Operador de mutación
	 */
	private OperadorMutacion operadorMutacion;
	
	/*
	 * Operador de selección
	 */
	private OperadorSeleccion operadorSeleccion;
	
	/**
	 * Crea una nueva población cant individuos
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
	 * Crea una población con 32 individuos
	 * @param demandas Las demandas solicitadas
	 */
	public Poblacion(Demanda[] demandas) {
		this(demandas, 32);
	}
	
	public int getTamaño() {
		return individuos.length;
	}
	
	/**
	 * Compara cada individuo de la población con
	 * los demás y reemplaza los cromosomas duplicados
	 * por nuevos, generados aleatoriamente.
	 */
	public void descartarIguales() {
		
	}

	/**
	 * Selecciona nuevos individuos de esta población
	 * @return Cromosoma[] nuevos individuos seleccionados
	 */
	public Cromosoma[] seleccionar() {
		return operadorSeleccion.seleccionar(this);
	}
	
	/**
	 * Cruza los individuos seleccionados eliminando la población
	 * vieja. 
	 * @param selectos
	 */
	public void cruzar(Cromosoma []selectos) {
		for (int i = 0; i<=selectos.length-2; i=i+2){
			Cromosoma nuevos[];
			nuevos = operadorCruce.cruzar(selectos[i], selectos[i+1]);
			individuos[i]=nuevos[i];
			individuos[i+1]=nuevos[i+1];
		}
	}

	/**
	 * Muta cromosomas de la población con una problabilidad de
	 * mutar de p = 0.1
	 */
	public void mutar() {
		Random rand = new Random(System.currentTimeMillis());
		
		for (int i =0; i<this.getTamaño(); i++){
			if (rand.nextInt(10)>8)
				operadorMutacion.mutar(individuos[i]);
		}
	}
	
	public void reemplazar(Cromosoma nuevo, int pos) {
		individuos[pos]=nuevo;
	}

	public void evaluar(){
		for (int i=0; i<=this.getTamaño();i++){
			fitness[i] = evaluar(i);
		}
	}
	
	private double evaluar(int ind){
		double costo=0;
		double retorno=0;
		
		Cromosoma x = this.getIndividuo(ind);
		
		for (int i=0; i<x.getCantGenes(); i++){
			double max = x.getDemandas()[i].getAnchoDeBanda();
			costo = x.getDemandas()[i].getCaminos().getCamino(ind).getCosto();
			retorno +=costo; 
			if (max < costo){
				retorno +=costo;
			}
		}
 
		return (1/retorno);
	}
	
	public double getFitness(int ind){
		return fitness[ind];
	}
	
	public Cromosoma getIndividuo(int pos){
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
