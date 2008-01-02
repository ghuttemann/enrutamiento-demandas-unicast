/*
 * @(#)Cromosoma.java
 */
package enrut.ag;

import java.util.Hashtable;
import java.util.Random;
import enrut.Demanda;
import enrut.Solucion;
import enrut.grafo.Arista;
import enrut.grafo.Camino;
import enrut.grafo.GrupoCaminos;

/*
 * Representa un cromosoma (solución para
 * el algoritmo GA).
 */
public class Cromosoma extends Solucion {
	/*
	 * Genes del cromosoma
	 */
	private int[] genes;
	
	/*
	 * Demandas realizadas
	 */
	private Demanda[] demandas;
	
	/*
	 * Costo de la solucion
	 */
	private double costo;
	
	/*
	 * Fitness de la solución
	 */
	private double fitness;


	/**
	 * Construye un nuevo cromosomas con alelos igual a 0.
	 * @param demandas Las demandas solicitadas
	 */
	public Cromosoma(Demanda[] demandas) {
		/*
		 * El motivo por el que se pasa las demandas
		 * solicitadas es que éstas almacenan a su vez
		 * su grupo de caminos relacionado, el cual es
		 * utilizado para definir el dominio de cada gen
		 * del cromosoma.
		 */
		this.demandas = demandas;
		genes = new int[demandas.length];
	}
	
	/**
	 * Setea los genes del cromosoma con 
	 * alelos randómicos.
	 */
	public void generarGenes() {
		Random rand = new Random();
		rand.nextInt();
		for (int i=0; i < genes.length; i++) {
			int cantCaminos = demandas[i].getGrupoCaminos().getCantCaminos();
			genes[i] = rand.nextInt(cantCaminos);
		}
	}
	
	/**
	 * Setea un determinado gen del cromosoma.
	 */
	public void setGen(int pos, int valor) {
		this.genes[pos] = valor;
	}
	
	/**
	 * Obtiene el valor de un determinado 
	 * gen del cromosoma.
	 */
	public int getGen(int pos) {
		return this.genes[pos];
	}
	
	/**
	 * Obtiene el grupo de caminos relacionado
	 * al gen i del cromosoma.
	 */
	public GrupoCaminos getGrupoCaminos(int i) {
		return this.demandas[i].getGrupoCaminos();
	}
	
	/**
	 * Obtiene las demandas relacionadas a
	 * este cromosoma.
	 */
	public Demanda[] getDemandas() {
		return this.demandas;
	}
	
	/**
	 * Obtiene la longitud (cantidad de genes)
	 * de este cromosoma.
	 */
	public int getCantGenes() {
		return this.genes.length;
	}
	
	/**
	 * Realiza la operación de evaluación
	 * del cromosoma, asignando fitness y costo.
	 */
	public double evaluar() {
		// Calculamos el costo de la solución
		double total=0.0;
		for(int i=0; i<this.getCantGenes(); i++){
			total += getGrupoCaminos(i).getCamino(getGen(i)).getCosto();
		}
		this.costo = total;
		
		// Verificamos si la solución es válida
		int repetidos = this.esValido();
		if (repetidos > 0)
			this.fitness = -repetidos;
		else
			this.fitness = 1/this.costo;
		
		return this.fitness;
	}
	
	/*
	 * Verifica si el cromosoma es una solución
	 * válida. Si lo es, esta función retorna cero.
	 * En caso contrario, retorna la cantidad de
	 * aristas en las que se viola la restricción
	 * de ancho de banda.
	 */
	private int esValido() {
		
		Hashtable<String, Capacidad> aristasRepetidas;
		
		aristasRepetidas = new Hashtable<String, Capacidad>();
		int enlacesRepetidos = 0;		
		
		/*
		 * Iteramos sobre cada gen del cromosoma
		 * excepto el último.
		 */
		for (int i=0; i < this.getCantGenes()-1; i++) {
			// Gen actual
			int genActual = this.getGen(i);
			
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
				for (int k=i+1; k < this.getCantGenes(); k++) {
					int genDestino = this.getGen(k);
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
					
					if (capa.getCantAristas() > 0 && capa.esNegativo())
						++enlacesRepetidos;
				}
			}
		}
		
		return enlacesRepetidos;
	}
	
	/**
	 * Obtiene el costo de la solución
	 * representada por este cromosoma.
	 */
	public double getCosto() {
		return this.costo;
	}
	
	/**
	 * Obtiene el fitness de este cromosoma.
	 */
	public double getFitness() {
		return fitness;
	}
	
	/**
	 * Función de comparación de igualdad.
	 */
	@Override
	public boolean equals(Object obj) {
		String thisClass = this.getClass().getName();
		String objClass  = obj.getClass().getName();
		
		if (!thisClass.equalsIgnoreCase(objClass))
			return false;
			
		Cromosoma c = (Cromosoma) obj;
		if (this.getCantGenes() != c.getCantGenes())
			return false;
		
		for (int i=0; i < this.getCantGenes(); i++){
			if (this.getGen(i) != c.getGen(i))
				return false;
		}
		
		return true;
	}
	
	/**
	 * Imprime al cromosoma en la salida estándar.
	 */
	public void imprimir(){
		for (int i=0; i<this.getCantGenes();i++){
			String c = getGrupoCaminos(i).getCamino(getGen(i)).toString2();
			System.out.println("Demanda "+i+"= "+c);
		}
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
