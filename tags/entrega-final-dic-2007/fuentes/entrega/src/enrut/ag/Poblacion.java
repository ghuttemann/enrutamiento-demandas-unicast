/*
 * @(#)Poblacion.java
 */
package enrut.ag;

import java.util.Random;
import enrut.Demanda;
import enrut.ag.oper.OperadorCruce;
import enrut.ag.oper.OperadorMutacion;
import enrut.ag.oper.OperadorSeleccion;

/**
 * Representa la población de individuos
 * para el algoritmo basado en GA.
 */
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
	 * Probabilidad de mutaci�n (0 a 100)
	 */
	private int probabilidadMutacion;
	
	/*
	 * Porcentaje de reinicializaci�n
	 */
	private double porcentajeReinicializacion;

	
	/**
	 * Crea una población
	 * 
	 * @param demandas Las demandas asociadas al problema
	 * @param cantIndividuos Tamaño de la población
	 * @param cantAristas Cantidad de aristas del grafo en cuestión
	 * @param probabilidadMutacion Probabilidad de mutación de los individuos
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
	 * Obtiene la cantidad de individuos de la población
	 * @return int tamaño de población
	 */
	public int getTamaño() {
		return individuos.length;
	}
	
	/**
	 * Compara cada individuo de la población con
	 * los demás y modifica los cromosomas duplicados
	 * mutandolos.
	 * Se realiza una sola pasada, lo que implica que
	 * si al mutar vuelve a duplicarse un individuo,
	 * esto ya no se elimina.
	 */
	public void descartarIguales() {
		for (int i=0; i<this.getTamaño()-1; i++)
			for (int j=i+1; j<this.getTamaño(); j++)
				if (individuos[i].equals(individuos[j]))
					operadorMutacion.mutar(individuos[j]);
	}

	/**
	 * Selecciona a los individuos de esta población para
	 * realizar la operación de cruce.
	 * @return Cromosoma[] Individuos seleccionados
	 */
	public Cromosoma[] seleccionar() {
		return operadorSeleccion.seleccionar(this);
	}
	
	/**
	 * Cruza los individuos seleccionados eliminando
	 * la población vieja. 
	 * @param selectos
	 */
	public void cruzar(Cromosoma[] selectos) {
		for (int i=0; i <= selectos.length-2; i = i+2) {
			Cromosoma nuevos[];
			nuevos = operadorCruce.cruzar(selectos[i], selectos[i+1]);
			
			this.hijos[i]   = nuevos[0];
			this.hijos[i+1] = nuevos[1];
		}
	}

	/**
	 * Muta a los cromosomas de la población
	 */
	public void mutar() {
		Random rand = new Random();
		rand.nextInt();
		
		for (int i=0; i < this.getTamaño(); i++)
			if (rand.nextInt(101) < this.getProbabilidadMutacion())
				operadorMutacion.mutar(hijos[i]);
	}
	
	/**
	 * Realiza el reemplazo de individuos de
	 * la población.
	 */
	public void reemplazar() {
		for (int i =0; i < this.getTamaño(); i++)
			individuos[i] = hijos[i];
	}

	/**
	 * Realiza el cálculo de fitness para todos los individuos
	 * y actualiza el mejor global de la población.
	 */
	public void evaluar() {		
		for (int i=0; i < this.getTamaño(); i++)
			fitness[i] = cantAristas + individuos[i].evaluar();

		elegirMejor();
	}
	
	/**
	 * Obtiene el fitness de un individuo
	 * @param ind Indice de un individuo
	 * @return El fitness del individuo elegido
	 */
	public double getFitness(int ind) {
		return fitness[ind];
	}
	
	/**
	 * Obtiene el pos-ésimo individuo de la población
	 * @param pos Indice del individuo
	 * @return El individuo elegido
	 */
	public Cromosoma getIndividuo(int pos) {
		return individuos[pos];
	}
	
	/**
	 * Obtiene el operador de cruce de la población
	 */
	public OperadorCruce getOperadorCruce() {
		return operadorCruce;
	}

	/**
	 * Setea el operador de cruce de la población
	 */
	public void setOperadorCruce(OperadorCruce operadorCruce) {
		this.operadorCruce = operadorCruce;
	}

	/**
	 * Obtiene el operador de mutación de la población
	 */
	public OperadorMutacion getOperadorMutacion() {
		return operadorMutacion;
	}

	/**
	 * Setea el operador de mutación de la población
	 */
	public void setOperadorMutacion(OperadorMutacion operadorMutacion) {
		this.operadorMutacion = operadorMutacion;
	}

	/**
	 * Obtiene el operador de selección de la población
	 */
	public OperadorSeleccion getOperadorSeleccion() {
		return operadorSeleccion;
	}

	/**
	 * Setea el operador de selección de la población
	 */
	public void setOperadorSeleccion(OperadorSeleccion operadorSeleccion) {
		this.operadorSeleccion = operadorSeleccion;
	}
	
	/**
	 * Obtiene la cantidad de aristas del grafo asociado.
	 */
	public int getCantAristas() {
		return this.cantAristas;
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
		
		double mejorFitness = cantAristas + mejorIndividuo.getFitness();
		for (int i=0; i < this.getTamaño(); i++) {
			if (fitness[i]> mejorFitness) {
				mejorIndividuo = individuos[i];
				mejorFitness = cantAristas + mejorIndividuo.getFitness();
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
		int contador = 0; // cuenta los cromosomas inválidos
		
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

	/**
	 * Obtiene el mejor individuo de la población
	 */
	public Cromosoma getMejorIndividuo() {
		return this.mejorIndividuo;
	}
	
	/**
	 * Setea el mejor individuo de la población
	 */
	public void setMejorIndividuo(Cromosoma x) {
		this.mejorIndividuo = x; 
	}
	
	/**
	 * Obtiene el fitness del mejor individuo de la población
	 */
	public double getMejorFitness(){
		return this.cantAristas + this.mejorIndividuo.getFitness();
	}

	/**
	 * Obtiene el costo del mejor individuo de la población
	 */
	public double getMejorCosto(){
		return this.mejorIndividuo.getCosto();
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

	/**
	 * Obtiene la probabilidad de mutación de la población
	 */
	public int getProbabilidadMutacion() {
		return probabilidadMutacion;
	}

	/**
	 * Setea la probabilidad de mutación de la población
	 */
	public void setProbabilidadMutacion(int probabilidadMutacion) {
		this.probabilidadMutacion = probabilidadMutacion;
	}

	/**
	 * Obtiene el porcentaje de reinicialización de la población
	 */
	public double getPorcentajeReinicializacion() {
		return porcentajeReinicializacion;
	}

	/**
	 * Setea el porcentaje de reinicialización de la población
	 */
	public void setPorcentajeReinicializacion(double porcentajeReinicializacion) {
		this.porcentajeReinicializacion = porcentajeReinicializacion;
	}
}
