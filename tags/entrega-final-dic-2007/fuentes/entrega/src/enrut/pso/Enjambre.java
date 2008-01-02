/*
 * @(#)Enjambre.java
 */
package enrut.pso;

import java.util.Random;
import enrut.Demanda;
import enrut.pso.oper.Movimiento;

/**
 * Representa enjambre de partículas
 * para el algoritmo basado en PSO.
 */
public class Enjambre {
	/*
	 * Partículas del enjambre
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
	 * Factores Alpha y Beta para el calculo de
	 * la nueva posición
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
	
	/*
	 * Porcentaje de reinicialización
	 */
	private double porcentajeReinicializacion;

	/**
	 * Crea un nuevo enjambre
	 * @param demandas Las demandas solicitadas
	 * @param cantParticulas La cantidad de particulas a generar
	 * @param cantArist La cantidad de aristas del grafo a evaluar
	 */
	public Enjambre(Demanda[] demandas, int cantParticulas, int cantAristas) {
		particulas = new Particula[cantParticulas];
		fitness = new double[cantParticulas];
		this.cantAristas = cantAristas;
		factores = new int[2];
		
		// Factores por defecto
		factores[0] = 100;
		factores[1] = 0;
		
		// Porcentaje por defecto
		this.porcentajeReinicializacion = 0.8;
		
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
	 * Compara cada partícula del enjambre con
	 * las demás y modifica las particulas duplicados
	 * "mutándolos".
	 * Se realiza una sola pasada, lo que implica que
	 * si al mutar vuelve a duplicarse una partícula,
	 * esto ya no se elimina.
	 */
	public void descartarIguales() {
		for (int i=0; i<this.getTamaño()-1; i++)
			for (int j=i+1; j<this.getTamaño(); j++)
				if (particulas[i].equals(particulas[j]))
					mutar(particulas[j]);
	}
	
	/*
	 * Muta una partícula, idem GA.
	 * En realidad, lo más apropiado sería decir que 
	 * se cambia la posición randómicamente.
	 */
	private void mutar(Particula a) {
		Random rand = new Random();
		rand.nextInt();
		
		// Cantidad de caminos de la particula
		int cantCaminos = a.getCantDimensiones();
		
		for (int i=0; i<cantCaminos; i++) {
			int cantCam = a.getGrupoCaminos(i).getCantCaminos();
			int valorNuevo = rand.nextInt(cantCam);
			a.setPosActual(i, valorNuevo);
		}
	}

	/**
	 * Realiza el calculo de fitness para todas las particulas,
	 * además de actualizar el mejor global.
	 */
	public void evaluar() {
		for (int i=0; i < this.getTamaño(); i++)
			fitness[i] = cantAristas + particulas[i].evaluar();

		elegirMejor();
	}
	
	/**
	 * Obtiene el fitness de la i-ésima partícula.
	 * @param ind Indice de la particula
	 * @return fitness
	 */
	public double getFitness(int ind) {
		return fitness[ind];
	}
	
	/**
	 * Obtiene la pos-ésima particula del enjambre
	 */
	public Particula getParticula(int pos) {
		return particulas[pos];
	}
	
	/**
	 * Obtiene la cantidad de aristas del grafo asociado.
	 */
	public int getCantAristas(){
		return this.cantAristas;
	}
	
	/**
	 * Setea la cantidad de aristas del grafo asociado.
	 */
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
					particulas[0].getCantDimensiones());
			mejorParticula.clonar(particulas[0]);
		}
		
		double mejorFitness = cantAristas + mejorParticula.getFitness();
		for (int i=0; i < this.getTamaño(); i++) {
			if (fitness[i] > mejorFitness) {
				mejorParticula.clonar(particulas[i]);
				mejorFitness = cantAristas + mejorParticula.getFitness();
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
	public boolean reinicializar() {
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
		if (contador > this.getTamaño() * getPorcentajeReinicializacion())
			return true;
		
		// Si no, retornamos false
		return false;
	}

	/**
	 * Obtiene la mejor partícula de la historia del enjambre
	 */
	public Particula getMejorParticula() {
		return this.mejorParticula;
	}
	
	/**
	 * Setea la mejor partícula de la historia del enjambre
	 */
	public void setMejorParticula(Particula x) {
		this.mejorParticula = x; 
	}
	
	/**
	 * Obtiene el fitness de la mejor partícula de la
	 * historia del enjambre.
	 */
	public double getMejorFitness(){
		return this.cantAristas + this.mejorParticula.getFitness();
	}

	/**
	 * Obtiene el costo de la mejor partícula de la
	 * historia del enjambre.
	 */
	public double getMejorCosto(){
		return this.mejorParticula.getCosto();
	}	
	
	/**
	 * Calcula las nuevas posiciones de las
	 * particulas y se mueven (actualizan) a ellas. 
	 */
	public void nuevasPosiciones() {
		Random rand = new Random();
		rand.nextInt(101);
		
		for (int i=0; i < this.getTamaño(); i++ ) {
			int[] nuevaPos;
			
			// Factores de movimiento
			factores[0] = rand.nextInt(101);
			factores[1] = 100 - factores[0];
			
			// Obtenemos la nueva posición
			nuevaPos = operadorMovimiento.mover(particulas[i], mejorParticula, factores);
			
			// Actualizamos la nueva posición
			particulas[i].setPosActual(nuevaPos);
		}
	}
	
	/**
	 * Imprime en salida standard toda el enjambre.
	 */
	public void imprimir(){
		for (int i=0; i<this.getTamaño(); i++){
			System.out.println("Particula: "+i+" ");
			System.out.println("Fitness  : "+fitness[i]);
			particulas[i].imprimir();
			System.out.println();
		}
	}

	/**
	 * Obtiene el operador de movimiento del enjambre
	 */
	public Movimiento getOperadorMovimiento() {
		return operadorMovimiento;
	}

	/**
	 * Setea el operador de movimiento del enjambre
	 */
	public void setOperadorMovimiento(Movimiento operadorMovimiento) {
		this.operadorMovimiento = operadorMovimiento;
	}
	
	/**
	 * Obtiene el porcentaje de reinicialización.
	 */
	public double getPorcentajeReinicializacion() {
		return porcentajeReinicializacion;
	}

	/**
	 * Setea el porcentaje de reinicialización.
	 */
	public void setPorcentajeReinicializacion(double porcentajeReinicializacion) {
		this.porcentajeReinicializacion = porcentajeReinicializacion;
	}
}
