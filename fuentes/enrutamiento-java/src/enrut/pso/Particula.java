/*
 * @(#)Particula.java
 */
package enrut.pso;

import java.util.Hashtable;
import java.util.Random;

import enrut.Demanda;
import enrut.Solucion;
import enrut.grafo.Arista;
import enrut.grafo.Camino;
import enrut.grafo.GrupoCaminos;

/**
 * Clase que representa una particula en el esquema de
 * un AlgoritmoPSO.
 * @author marcelo rodas
 */
public class Particula extends Solucion {
	/*
	 * Posicion Actual = Conjunto de Caminos para
	 * satisfacer las demandas.
	 */
	private int[] posActual;
	
	/*
	 * Demandas realizadas
	 */
	private Demanda[] demandas;
	
	/*
	 * Costo de la solucion actual
	 */
	private double costoActual;
	
	/*
	 * Fitness de la solución
	 */
	private double fitness;
	
	/*
	 * Mejor posición personal
	 */
	private int[] mejorPosicionLocal;
	
	/*
	 * Costo de la mejor solucion personal
	 */
	private double mejorCostoLocal;
	
	/*
	 * Costo de la mejor solucion personal
	 */
	private double mejorFitnessLocal;
	
	
	/**
	 * Construye un nuevo cromosoma con alelos
	 * randómicos para sus genes.
	 * @param demandas Las demandas solicitadas
	 */
	public Particula(Demanda[] demandas, int cantAristas) {
		this.demandas = demandas;
		posActual = new int[demandas.length];
		
		mejorPosicionLocal = new int[posActual.length];
		mejorCostoLocal    = -cantAristas;
		mejorFitnessLocal  = -cantAristas;
	}
	
	/**
	 * Setea la posición actual con  
	 * valores aleatorios.
	 */
	public void inicializarPosicion() {
		Random rand = new Random();
		rand.nextInt();
		for (int i=0; i < posActual.length; i++) {
			int cantCaminos = demandas[i].getGrupoCaminos().getCantCaminos();
			posActual[i] = rand.nextInt(cantCaminos);
		}
	}
	
	public void setPosActual(int pos, int valor) {
		this.posActual[pos] = valor;
	}
	
	public void setPosActual(int[] pos) {
		for(int i=0; i< posActual.length; i++) {
			setPosActual(i, pos[i]);
		}
	}
	
	public int getPosActual(int pos) {
		return this.posActual[pos];
	}
	
	public int[] getPosActual() {
		return this.posActual;
	}
	
	public int[] getMejorPosicionLocal() {
		return this.mejorPosicionLocal;
	}
	
	public int getMejorPosicionLocal(int pos) {
		return this.mejorPosicionLocal[pos];
	}
	
	public double getMejorCostoLocal() {
		return this.mejorCostoLocal;
	}
	
	public GrupoCaminos getGrupoCaminos(int i) {
		return this.demandas[i].getGrupoCaminos();
	}
	
	public Demanda[] getDemandas() {
		return this.demandas;
	}
	
	public int getCantDimensiones() {
		return this.posActual.length;
	}
	
	public double evaluar() {
		// Calculamos el costo de la solución
		double total=0.0;
		for(int i=0; i<this.getCantDimensiones(); i++){
			total += getGrupoCaminos(i).getCamino(getPosActual(i)).getCosto();
		}
		this.costoActual = total;
		
		// Verificamos si la solución es válida
		int repetidos = this.esValido();
		if (repetidos>0)
			this.fitness = -repetidos;
		else
			this.fitness = 1/this.costoActual;
		
		/*
		 * Debemos verificar si la posición actual 
		 * de la particula es mejor que la mejor
		 * calculada hasta el momento y en caso de
		 * serlo lo reemplazamos.
		 */
		if (fitness > mejorFitnessLocal) {
			System.arraycopy(posActual, 0, mejorPosicionLocal, 0, posActual.length);
			mejorFitnessLocal = fitness;
			mejorCostoLocal   = costoActual;
		}
		
		return this.fitness;
	}
	
	private int esValido() {
		
		Hashtable<String, Capacidad> aristasRepetidas;
		
		aristasRepetidas = new Hashtable<String, Capacidad>();
		int enlacesRepetidos = 0;		
		/*
		 * Iteramos sobre cada gen del cromosoma
		 * excepto el último.
		 */
		for (int i=0; i < this.getCantDimensiones()-1; i++) {
			// Gen actual
			int genActual = this.getPosActual(i);
			
			/*
			 * Camino actual, cuyas aristas serán
			 * buscadas en los subsecuentes caminos.
			 */
			Camino camActual = this.getGrupoCaminos(i).getCamino(genActual);
			
			/*
			 * Para cada gen del cromosoma, debemos buscar
			 * sus aristas en los genes subsecuentes.
			 */
			for (int j=0; j < camActual.getLongitud(); j++) {
				/*
				 * Arista del camino actual que será
				 * buscada en los subsecuentes caminos.
				 */
				Arista arista = camActual.getArista(j);
				boolean repetida = false;
				
				/*
				 * Para cada arista del gen actual debemos
				 * buscar en el gen destino.
				 */
				for (int k=i+1; k < this.getCantDimensiones(); k++) {
					int genDestino = this.getPosActual(k);
					Camino camDestino = this.getGrupoCaminos(k).getCamino(genDestino);
					
					/*
					 * Si la arista actual está contenida
					 * en el camino, debemos agregar a la
					 * lista de aristas repetidas.
					 */
					if (camDestino.contiene(arista)) {
						repetida = true;
						
						/*
						 * Si la arista ya existe en la lista 
						 * de aristas repetidas, debemos obtener 
						 * dicha arista y agregar el camino al
						 * grupo de caminos en los que aparece
						 * dicha arista.
						 */
						Capacidad capa;
						if (aristasRepetidas.containsKey(arista.toString())) {
							capa = aristasRepetidas.get(arista.toString());
							Demanda demanda = getDemandas()[k];
							double auxiliar = demanda.getAnchoDeBanda();
							capa.descontar(auxiliar);
						}
						else {
							capa = new Capacidad(arista.getCapacidad());
							Demanda demanda = getDemandas()[k];
							double auxiliar = demanda.getAnchoDeBanda();
							capa.descontar(auxiliar);
							capa.aumentarCantAristas();
							aristasRepetidas.put(arista.toString(), capa);
						}
					}
				}
				
				/*
				 * Si hay solapamientos entonces existen problemas
				 */
				if (repetida) {
					Capacidad capa = aristasRepetidas.get(arista.toString());
					Demanda demanda = getDemandas()[i];
					double auxiliar = demanda.getAnchoDeBanda();
					capa.descontar(auxiliar);
					
					if (capa.esNegativo()){
						enlacesRepetidos += capa.getCantAristas();
					}
				}
			}
		}
		
		return enlacesRepetidos;
	}
	
	public double getCosto() {
		return this.costoActual;
	}
	
	public double getFitness() {
		return fitness;
	}
	
	@Override
	public boolean equals(Object obj) {
		String thisClass = this.getClass().getName();
		String objClass  = obj.getClass().getName();
		
		if (!thisClass.equalsIgnoreCase(objClass))
			return false;
			
		Particula c = (Particula) obj;
		if (this.getCantDimensiones() != c.getCantDimensiones())
			return false;
		
		for (int i=0; i < this.getCantDimensiones(); i++){
			if (this.getPosActual(i) != c.getPosActual(i))
				return false;
		}
		
		return true;
	}
	
	public void imprimir(){
		for (int i=0; i<this.getCantDimensiones();i++){
			String c = getGrupoCaminos(i).getCamino(getPosActual(i)).toString2();
			System.out.println("Demanda "+i+"= "+c);
		}
	}
	
	/**
	 * Calcula una nueva posición para la particula dadas:
	 * la posicionActual, la mejor posicionLocal, la mejor
	 * posicionGlobal y los factores.
	 * @param mejorGlobal
	 * @param factores
	 * @return int [] nueva posicion calculada
	 */
	public int[] getNuevaPosicion(Particula mejorGlobal, int[] factores) {
		
		Random rand = new Random();
		rand.nextInt();
		int size = this.posActual.length;
		int [] nuevaPos = new int[size];
		
		for (int i=0; i < size; i++) {
			
			int r = rand.nextInt(100)+1; // valor entre 1 y 100
			// Seleccionar Mejor Global
			if (r <= factores[2]) { 
				nuevaPos[i] = mejorGlobal.getPosActual(i);
			}
			// Seleccionar Mejor Personal
			else if (r <= factores[2]+factores[1]) { 
				nuevaPos[i] = mejorPosicionLocal[i];
			}
			// Seleccionar de la posicion actual
			else {
				nuevaPos[i] = posActual[i]; 
			}
		}
		return nuevaPos;
	}

	public void clonar(Particula p) {
		
		this.demandas = new Demanda[p.demandas.length]; // vector de demandas
		this.posActual = new int[p.demandas.length]; // vector de ints
		this.mejorPosicionLocal = new int[posActual.length]; // vector de ints
		
		for (int i =0; i< p.demandas.length; i++) {
			int origen = p.getDemandas()[i].getOrigen();
			int destino = p.getDemandas()[i].getDestino();
			double anchoDeBanda = p.getDemandas()[i].getAnchoDeBanda();
			this.demandas[i] = new Demanda(origen, destino, anchoDeBanda);
			this.demandas[i].setCaminos(p.getGrupoCaminos(i));
			this.posActual[i] = p.getPosActual(i);
			this.mejorPosicionLocal[i] = p.getMejorPosicionLocal(i);
		}
		
		this.costoActual = p.costoActual;
		this.mejorCostoLocal = p.mejorCostoLocal;
		this.fitness = p.fitness;
		this.mejorFitnessLocal = p.mejorFitnessLocal;
	}
	
	
	private class Capacidad {
		private double valor;
		private int cantAristas = 0;
		
		public Capacidad(double valor) {
			this.valor = valor;
		}
		
		public Capacidad() {
			this(0.0);
		}
		
		public void descontar(double valor){
			this.valor -= valor;
		}

		public void aumentarCantAristas() {
			this.cantAristas++;
		}
		
		public double getValor() {
			return this.valor;
		}
		
		public int getCantAristas(){
			return this.cantAristas;
		}
		
		public boolean esNegativo() {
			return this.valor < 0.0;
		}
	}
}
