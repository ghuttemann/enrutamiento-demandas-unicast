/*
 * @(#)Poblacion.java
 */
package enrut.ag;

import java.util.Random;
import enrut.Demanda;
import enrut.ag.oper.OperadorCruce;
import enrut.ag.oper.OperadorMutacion;
import enrut.ag.oper.OperadorSeleccion;

public class Poblacion {
	/*
	 * Individuos de la población
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
	 * Operador de mutación
	 */
	private OperadorMutacion operadorMutacion;
	
	/*
	 * Operador de selección
	 */
	private OperadorSeleccion operadorSeleccion;
	
	/*
	 * Mejor cromosoma de toda la historia
	 */
	private Cromosoma mejorIndividuo = null;
	
	/*
	 * Cantidad total de aristas 
	 */
	private int cantAristas = 0;
	
	/*
	 * Probabilidad de mutación (0 a 100)
	 */
	private int probabilidadMutacion;
	
	/*
	 * Porcentaje de reinicialización
	 */
	private double porcentajeReinicializacion;

	/**
	 * Crea una nueva población cant individuos
	 * @param demandas Las demandas solicitadas
	 * @param cant La cantidad de individuos a generar
	 * @param cantArist La cantidad de aristas del grafo a evaluar
	 */
	public Poblacion(Demanda[] demandas, int cantIndividuos, int cantAristas,
						int probabilidadMutacion) {
		individuos = new Cromosoma[cantIndividuos];
		hijos = new Cromosoma[cantIndividuos];
		fitness = new double[cantIndividuos];
		this.cantAristas = cantAristas;
		this.probabilidadMutacion = probabilidadMutacion;
		this.porcentajeReinicializacion = 0.8;
		
		for (int i=0; i < individuos.length; i++) {
			individuos[i] = new Cromosoma(demandas);
			individuos[i].generarGenes();
		}
	}
	
	/**
	 * Obtiene la cantidad de individuos de la poblacion
	 * @return int tamaño de poblacion
	 */
	public int getTamaño() {
		return individuos.length;
	}
	
	/**
	 * Compara cada individuo de la población con
	 * los demás y modifica los cromosomas duplicados
	 * mutandolos.
	 */
	public void descartarIguales() {
		for (int i=0; i<this.getTamaño()-1; i++) {
			for (int j=i+1; j<this.getTamaño(); j++) {
				if (individuos[i].equals(individuos[j])) {
					operadorMutacion.mutar(individuos[j]);
				}
			}
		}
	}

	/**
	 * Selecciona nuevos individuos de esta población
	 * @return Cromosoma[] nuevos individuos seleccionados
	 */
	public Cromosoma[] seleccionar() {
		return operadorSeleccion.seleccionar(this);
	}
	
	/**
	 * Cruza los individuos seleccionados eliminando
	 * la población vieja. 
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
	 * Muta cromosomas de la población
	 */
	public void mutar() {
		Random rand = new Random();
		rand.nextInt();
		
		for (int i=0; i < this.getTamaño(); i++){
			if (rand.nextInt(101) < this.getProbabilidadMutacion())
				operadorMutacion.mutar(hijos[i]);
		}
	}
	
	/**
	 * Realiza el reemplazo de individuos de
	 * la población.
	 */
	public void reemplazar() {
		for (int i =0; i<this.getTamaño(); i++)
			individuos[i] = hijos[i];
	}

	/**
	 * Realiza el calculo de fitness para todos los individuos
	 */
	public void evaluar() {
		
		for (int i=0; i<this.getTamaño();i++) {
			fitness[i] = cantAristas + individuos[i].evaluar();
		}
		elegirMejor();
	}
	
	/**
	 * Obtiene el fitness de un individuo
	 * @param ind indice de un individuo
	 * @return fitness
	 */
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
	
	public int getCantAristas(){
		return this.cantAristas;
	}
	
	public void setCantAristas(int n){
		this.cantAristas = n;
	}
	
	/**
	 * Elige el mejor cromosoma de 
	 * toda la historia.
	 */
	private void elegirMejor() {
		/*
		 * Si todavia no se seleccionó
		 * a ninguno, guardamos al primero.
		 */
		if (mejorIndividuo == null) {
			mejorIndividuo = individuos[0];
		}
		
		double mejorFitness = cantAristas + mejorIndividuo.getCosto();
		for (int i=0; i < this.getTamaño(); i++) {
			if (fitness[i]> mejorFitness) {
				mejorIndividuo = individuos[i];
				mejorFitness = cantAristas + mejorIndividuo.getCosto();
			}
		}
	}
	
	/**
	 * Realiza el control de la población, y si la cantidad
	 * de cromosomas inválidos es mayor al factor, retorna
	 * true y en caso contrario, retorna false.
	 * @param factor valor entre 0 y 1 que indica el porcentaje permitido.
	 * @return true si la cantidad de invalidos supera el factor.
	 */
	public boolean reinicializar() {
		int contador = 0; // cuenta los cromosomas invalidos
		
		for (int i=1; i < this.getTamaño(); i++) {
			// Contamos si el fitness es inválido
			if (fitness[i] < cantAristas)
				contador++;
		}
		
		/*
		 * Si el porcentaje de inválidos calculado es mayor 
		 * al permitido, retornamos true
		 */
		if (contador > this.getTamaño() * getPorcentajeReinicializacion())
			return true;
		
		// Si no, retornamos false
		return false;
	}

	public Cromosoma getMejorIndividuo() {
		return this.mejorIndividuo;
	}
	
	public void setMejorIndividuo(Cromosoma x) {
		this.mejorIndividuo = x; 
	}
	
	public double getMejorFitness(){
		return this.cantAristas + this.mejorIndividuo.getCosto();
	}

	public double getMejorCosto(){
		return 1/this.mejorIndividuo.getCosto();
	}
	
	/**
	 * Imprime en salida standard toda la población
	 */
	public void imprimir(){
		for (int i=0; i<this.getTamaño(); i++){
			System.out.println("Cromosoma: "+i+" ");
			System.out.println("Fitness  : "+fitness[i]);
			individuos[i].imprimir();
			System.out.println();
		}
	}

	public int getProbabilidadMutacion() {
		return probabilidadMutacion;
	}

	public void setProbabilidadMutacion(int probabilidadMutacion) {
		this.probabilidadMutacion = probabilidadMutacion;
	}

	public double getPorcentajeReinicializacion() {
		return porcentajeReinicializacion;
	}

	public void setPorcentajeReinicializacion(double porcentajeReinicializacion) {
		this.porcentajeReinicializacion = porcentajeReinicializacion;
	}
}
