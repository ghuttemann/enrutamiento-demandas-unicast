/*
 * @(#)Enjambre.java
 */
package enrut.pso;

import java.util.Random;
import enrut.ag.Demanda;
import enrut.pso.oper.Movimiento;
/**
 * @author Administrador
 *
 */
public class Enjambre {
	/*
	 * Individuos de la población
	 */
	private Particula[] particulas;

	/*
	 * Valor de calidad de una Particula
	 */
	private double[] fitness;
	
	/*
	 * Mejor Particula de toda la historia
	 */
	private Particula mejorParticula = null;
	
	/*
	 * Factores K1, K2, K3, para el calculo de
	 * la nueva ruta. 
	 */
	private int[] factores;
	
	/*
	 * Cantidad total de aristas 
	 */
	private int cantAristas = 0;
	
	/*
	 * Operador de Movimiento
	 */
	private Movimiento operadorMovimiento;
	
	/**
	 * Crea una nueva población cant particulas
	 * @param demandas Las demandas solicitadas
	 * @param cant La cantidad de particulas a generar
	 * @param cantArist La cantidad de aristas del grafo a evaluar
	 */
	public Enjambre(Demanda[] demandas, int cantParticulas, int cantAristas) {
		particulas = new Particula[cantParticulas];
		fitness = new double[cantParticulas];
		this.cantAristas = cantAristas;
		factores = new int[2];
		factores[0] = 100;
		factores[1] = 0;
		
		for (int i=0; i < particulas.length; i++) {
			particulas[i] = new Particula(demandas, this.cantAristas);
			particulas[i].inicializarPosicion();
		}
	}
	
	/**
	 * Obtiene la cantidad de particulas del enjambre
	 * @return int tamaño del enjambre
	 */
	public int getTamaño() {
		return particulas.length;
	}
	
	/**
	 * Compara cada individuo de la población con
	 * los demás y modifica las particulas duplicados
	 * mutandolos.
	 */
	public void descartarIguales() {
		//int k=0;
		for (int i=0; i<this.getTamaño()-1; i++) {
			for (int j=i+1; j<this.getTamaño(); j++) {
				if (particulas[i].equals(particulas[j])) {
					mutar(particulas[j]);
					//k++;
				}
			}
		}
		//System.out.println("Mutaciones: "+k);
	}
	
	public void mutar(Particula a) {
		Random rand = new Random();
		rand.nextInt();
		
		// Cantidad de caminos de la particula
		int cantCaminos = a.getCantCaminos();
		
		for (int i=0; i<cantCaminos; i++) {
			int cantCam = a.getGrupoCaminos(i).getCantCaminos();
			int valorNuevo = rand.nextInt(cantCam);
			a.setPosActual(i, valorNuevo);
		}
	}

	/**
	 * Realiza el calculo de fitness para todos los particulas
	 */
	public void evaluar() {
		
		for (int i=0; i<this.getTamaño();i++) {
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
	
	public void setCantAristas(int n){
		this.cantAristas = n;
	}
	
	/**
	 * Elige la mejor particula de 
	 * toda la historia.
	 */
	private void elegirMejor() {
		/*
		 * Si todavia no se seleccionó
		 * a ninguno, guardamos al primero.
		 */
		if (mejorParticula == null) {
			mejorParticula = new Particula(particulas[0].getDemandas(),
					particulas[0].getCantCaminos());
			mejorParticula.clonar(particulas[0]);
		}
		
		double mejorFitness = cantAristas + mejorParticula.getCosto();
		for (int i=0; i < this.getTamaño(); i++) {
			if (fitness[i]> mejorFitness) {
				mejorParticula.clonar(particulas[i]);
				mejorFitness = cantAristas + mejorParticula.getCosto();
			}
		}
	}
	
	/**
	 * Realiza el control del enjambre, y si la cantidad
	 * de particulas inválidos es mayor al factor, retorna
	 * true y en caso contrario, retorna false.
	 * @param factor valor entre 0 y 1 que indica el porcentaje permitido.
	 * @return true si la cantidad de invalidos supera el factor.
	 */
	public boolean reinicializar(double factor){
		int contador = 0; // cuenta las particulas invalidas
		for (int i=1; i < this.getTamaño(); i++) {
			// si es invalido contar
			if (fitness[i] < cantAristas) {
				contador++;
			}
		}
		
		/*
		 * Si el porcentaje de inválidos calculado es mayor 
		 * al permitido, retornamos true
		 */
		if (contador > this.getTamaño()*factor)
			return true;
		
		// Si no, retornamos false
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
	 * Función donde se calcula las nuevas posiciones de las
	 * particulas y se mueven (actualizan) a ellas. 
	 *
	 */
	public void nuevasPosiciones() {
		
		Random rand = new Random();
		rand.nextInt(101);
		for (int i=0; i< this.getTamaño(); i++ ) {
			int[] nuevaPos;
			factores[0] = rand.nextInt(101);
			factores[1] = 100-factores[0];
			nuevaPos = operadorMovimiento.mover(particulas[i], mejorParticula, factores);
			particulas[i].setPosActual(nuevaPos);
		}
	}
	
	/*private int[] getMejorVecindad(int indice) {
		int [] mejorLocal = particulas[indice].getPosActual();
		int V1 = indice - 1;
		int V2 = indice + 1;
		if(indice == 0)
			V1 = this.getTamaño()-1;
		else if (indice == this.getTamaño()-1)
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
	*/
	/**
	 * Imprime en salida standard toda la población
	 */
	public void imprimir(){
		for (int i=0; i<this.getTamaño(); i++){
			System.out.println("Particula: "+i+" ");
			System.out.println("Fitness  : "+fitness[i]);
			particulas[i].imprimir();
			System.out.println();
		}
	}

	public Movimiento getOperadorMovimiento() {
		return operadorMovimiento;
	}

	public void setOperadorMovimiento(Movimiento operadorMovimiento) {
		this.operadorMovimiento = operadorMovimiento;
	}
}
