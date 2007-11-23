/**
 * 
 */
package enrut.pso;

import enrut.ag.Demanda;
/**
 * @author Administrador
 *
 */
public class Enjambre {
	/*
	 * Individuos de la poblaci�n
	 */
	private Particula[] particulas;

	/*
	 * Valor de calidad de un cromosoma 
	 */
	private double[] fitness;
	
	/*
	 * Mejor cromosoma de toda la historia
	 */
	private Particula mejorParticula = null;
	
	/*
	 * Mejor Posici�n Global del Enjambre 
	 */
	private int[] mejorGlobal;
	
	/*
	 * Factores K1, K2, K3, para el calculo de
	 * la nueva ruta. 
	 */
	private double[] factores;
	
	/*
	 * Cantidad total de aristas 
	 */
	private int cantAristas = 0;
	
	/**
	 * Crea una nueva poblaci�n cant particulas
	 * @param demandas Las demandas solicitadas
	 * @param cant La cantidad de particulas a generar
	 * @param cantArist La cantidad de aristas del grafo a evaluar
	 */
	public Enjambre(Demanda[] demandas, int cant, int cantArists) {
		particulas = new Particula[cant];
		fitness = new double[cant];
		cantAristas = cantArists;
		factores = new double [3];
		factores[0] = 0.1;
		factores[1] = 0.1;
		factores[2] = 0.8;
		
		for (int i=0; i < particulas.length; i++) {
			particulas[i] = new Particula(demandas);
			particulas[i].inicializarPosicion();
		}
	}
	
	/**
	 * Obtiene la cantidad de particulas de la poblacion
	 * @return int tama�o de poblacion
	 */
	public int getTama�o() {
		return particulas.length;
	}
	
	/**
	 * Compara cada individuo de la poblaci�n con
	 * los dem�s y modifica los cromosomas duplicados
	 * mutandolos.
	 */
	public void descartarIguales() {
		for (int i=0; i<this.getTama�o()-1; i++) {
			for (int j=i+1; j<this.getTama�o(); j++) {
				if (particulas[i].equals(particulas[j])) {
					// TODO --> Modificar la particula igual
				}
			}
		}
	}

	/**
	 * Realiza el calculo de fitness para todos los particulas
	 */
	public void evaluar() {
		
		for (int i=0; i<this.getTama�o();i++) {
			fitness[i] = cantAristas + particulas[i].evaluar();
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
	
	public Particula getParticula(int pos) {
		return particulas[pos];
	}
	
	public int getCantAristas(){
		return this.cantAristas;
	}
	
	public void setCantAristas(int N){
		this.cantAristas = N;
	}
	
	/**
	 * Elige el mejor cromosoma de 
	 * toda la historia.
	 */
	private void elegirMejor() {
		/*
		 * Si todavia no se seleccion�
		 * a ninguno, guardamos al primero.
		 */
		if (mejorParticula == null) {
			mejorParticula = particulas[0];
		}
		
		double mejorFitness = cantAristas + mejorParticula.getCosto();
		for (int i=0; i < this.getTama�o(); i++) {
			if (fitness[i]> mejorFitness) {
				mejorParticula = particulas[i];
				mejorFitness = cantAristas + mejorParticula.getCosto();
			}
		}
	}
	
	/**
	 * Realiza el control de la poblaci�n, y si la cantidad
	 * de cromosomas inv�lidos es mayor al factor, se reinicializa
	 * la poblaci�n.
	 * @param factor valor entre 0 y 1 que indica el porcentaje permitido.
	 * @return boolean si se reinicializa o no.
	 */
	public boolean reinicializar(double factor){
		int contador = 0; // cuenta los cromosomas invalidos
		for (int i=1; i < this.getTama�o(); i++) {
			// si es invalido contar
			if (fitness[i] < cantAristas) {
				contador++;
			}
		}
		if (contador > this.getTama�o()*factor) // Reinicializar
			return true;
		else // No Reinicializar
			return false;
	}

	public Particula getMejorParticula() {
		return this.mejorParticula;
	}
	public void setMejorParticula(Particula x) {
		this.mejorParticula = x; 
	}
	
	public double getMejorFitness(){
		return this.cantAristas + this.mejorParticula.getCosto();
	}

	public double getMejorCosto(){
		return 1/this.mejorParticula.getCosto();
	}	
	
	/**
	 * Funci�n donde se calcula las nuevas posiciones de las
	 * particulas y se mueven (actualizan)a ellas. 
	 *
	 */
	public void NuevasPosiciones() {
		for (int i=0; i< this.getTama�o(); i++ ) {
			int[] nuevaPos;
			int[] posLocal = getMejorVecindad(i);
			nuevaPos = particulas[i].getNuevaPosicion(posLocal, mejorGlobal,
					factores);
			particulas[i].setPosActual(nuevaPos);
		}
	}
	
	private int[] getMejorVecindad (int indice) {
		int [] mejorLocal = particulas[indice].getPosActual();
		int V1 = indice - 1;
		int V2 = indice + 1;
		if(indice == 0)
			V1 = this.getTama�o()-1;
		else if (indice == this.getTama�o()-1)
			V2 = 0;
		
		double costoV1 = particulas[V1].getCosto();
		double costo = particulas[indice].getCosto();
		double costoV2 = particulas[V2].getCosto();
		
		if (costoV1 > costo && costoV1 > costoV2)
			mejorLocal = particulas[V1].getPosActual();
		else if (costoV2 > costo && costoV2 > costoV1)
			mejorLocal = particulas[V2].getPosActual();
		
		return mejorLocal;
	}
	
	/**
	 * Imprime en salida standard toda la poblaci�n
	 */
	public void imprimir(){
		for (int i=0; i<this.getTama�o(); i++){
			System.out.println("Particula: "+i+" ");
			System.out.println("Fitness  : "+fitness[i]);
			particulas[i].imprimir();
			System.out.println();
		}
	}

}
