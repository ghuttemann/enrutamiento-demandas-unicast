/*
 * @(#)Cromosoma.java
 */
package enrut.ag;

import java.util.Hashtable;
import java.util.Random;
import enrut.grafo.Arista;
import enrut.grafo.Camino;
import enrut.grafo.GrupoCaminos;

public class Cromosoma {
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
	double costo;
	
	
	/**
	 * Construye un nuevo cromosoma con alelos
	 * randómicos para sus genes.
	 * @param demandas Las demandas solicitadas
	 */
	public Cromosoma(Demanda[] demandas) {
		this.demandas = demandas;
		genes = new int[demandas.length];
	}
	
	/**
	 * Setea los genes del cromosoma con 
	 * alelos randómicos.
	 */
	public void generarGenes() {
		Random rand = new Random(System.currentTimeMillis());
		for (int i=0; i < genes.length; i++) {
			int cantCaminos = demandas[i].getGrupoCaminos().getCantCaminos();
			genes[i] = rand.nextInt(cantCaminos);
		}
	}
	
	public void setGen(int pos, int valor) {
		this.genes[pos] = valor;
	}
	
	public int getGen(int pos) {
		return this.genes[pos];
	}
	
	public GrupoCaminos getGrupoCaminos(int i) {
		return this.demandas[i].getGrupoCaminos();
	}
	
	public Demanda[] getDemandas() {
		return this.demandas;
	}
	
	public int getCantGenes() {
		return this.genes.length;
	}
	
	public double evaluar() {
		
		if (!this.esValido())
			return 0.0;
		
		double total=0.0;
		for(int i=0; i<this.getCantGenes(); i++){
			total += getGrupoCaminos(i).getCamino(getGen(i)).getCosto();
		}
		
		this.costo = 1/total;
		return this.costo;
	}
	
	private boolean esValido() {
		
		Hashtable<String, Capacidad> aristasRepetidas;
		
		aristasRepetidas = new Hashtable<String, Capacidad>();
		
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
							aristasRepetidas.put(arista.toString(), capa);
						}
						
						if (capa.esNegativo())
							return false;
					}
				}
				
				if (repetida) {
					Capacidad capa = aristasRepetidas.get(arista.toString());
					Demanda demanda = getDemandas()[i];
					double auxiliar = demanda.getAnchoDeBanda();
					capa.descontar(auxiliar);
					
					if (capa.esNegativo())
						return false;
				}
			}
		}
		
		return true;
	}
	
	public double getCosto() {
		return this.costo;
	}
	
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
	
	public void imprimir(){
		for (int i=0; i<this.getCantGenes();i++){
			String c = getGrupoCaminos(i).getCamino(getGen(i)).toString();
			System.out.println("Gen "+i+"= "+c);
		}
	}
	
	private class Capacidad {
		private double valor;
		
		public Capacidad(double valor) {
			this.valor = valor;
		}
		
		public Capacidad() {
			this(0.0);
		}
		
		public void descontar(double valor){
			this.valor -= valor;
		}
		
		public double getValor() {
			return this.valor;
		}
		
		public boolean esNegativo() {
			return this.valor < 0.0;
		}
	}
}
