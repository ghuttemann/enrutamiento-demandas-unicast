/**
 * 
 */
package enrut.pso;

import java.util.Hashtable;
import java.util.Random;

import enrut.ag.Demanda;
import enrut.grafo.Arista;
import enrut.grafo.Camino;
import enrut.grafo.GrupoCaminos;

/**
 * Clase que representa una particula en el esquema de
 * un PSO.
 * @author marcelo rodas
 */
public class Particula {
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
	 * Costo de la solucion
	 */
	double costo;
	
	
	/**
	 * Construye un nuevo cromosoma con alelos
	 * randómicos para sus genes.
	 * @param demandas Las demandas solicitadas
	 */
	public Particula(Demanda[] demandas) {
		this.demandas = demandas;
		posActual = new int[demandas.length];
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
	
	public void setPosActual(int [] pos) {
		for(int i=0; i< posActual.length; i++) {
			setPosActual(i,pos[i]);
		}
	}
	
	public int getPosActual(int pos) {
		return this.posActual[pos];
	}
	
	public int[] getPosActual() {
		return this.posActual;
	}
	
	public GrupoCaminos getGrupoCaminos(int i) {
		return this.demandas[i].getGrupoCaminos();
	}
	
	public Demanda[] getDemandas() {
		return this.demandas;
	}
	
	public int getCantCaminos() {
		return this.posActual.length;
	}
	
	public double evaluar() {
		
		int repetidos = this.esValido();
		if (repetidos>0) {
			this.costo = -repetidos;
			return -repetidos;
		}
		
		double total=0.0;
		for(int i=0; i<this.getCantCaminos(); i++){
			total += getGrupoCaminos(i).getCamino(getPosActual(i)).getCosto();
		}
		
		this.costo = 1/total;
		return this.costo;
	}
	
	private int esValido() {
		
		Hashtable<String, Capacidad> aristasRepetidas;
		
		aristasRepetidas = new Hashtable<String, Capacidad>();
		int enlacesRepetidos = 0;		
		/*
		 * Iteramos sobre cada gen del cromosoma
		 * excepto el último.
		 */
		for (int i=0; i < this.getCantCaminos()-1; i++) {
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
				for (int k=i+1; k < this.getCantCaminos(); k++) {
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
		return this.costo;
	}
	
	@Override
	public boolean equals(Object obj) {
		String thisClass = this.getClass().getName();
		String objClass  = obj.getClass().getName();
		
		if (!thisClass.equalsIgnoreCase(objClass))
			return false;
			
		Particula c = (Particula) obj;
		if (this.getCantCaminos() != c.getCantCaminos())
			return false;
		
		for (int i=0; i < this.getCantCaminos(); i++){
			if (this.getPosActual(i) != c.getPosActual(i))
				return false;
		}
		
		return true;
	}
	
	public void imprimir(){
		for (int i=0; i<this.getCantCaminos();i++){
			String c = getGrupoCaminos(i).getCamino(getPosActual(i)).toString();
			System.out.println("Gen "+i+"= "+c);
		}
	}
	
	/**
	 * Calcula una nueva Posición para la particula dadas:
	 * la posicionActual, la mejor posicionLocal, la mejor
	 * posicionGlobal y los factores.
	 * @param mejorLocal
	 * @param mejorGlobal
	 * @param factores
	 * @return int [] nueva posicion calculada
	 */
	public int[] getNuevaPosicion(int [] mejorLocal, int [] mejorGlobal, double[] factores) {
		
		Random rand = new Random();
		rand.nextInt();
		int size = this.posActual.length;
		int [] nuevaPos = new int[size];
		nuevaPos[0] = posActual[size-1];
		int j = 0;
		
		for (int i = 1; i< size; i++) {
			int r = rand.nextInt(99)+1; // valor entre 1 y 100
			double dr = 1/r;
			if (dr <= factores[2]){
				j = mejorGlobal[i-1];
				if (j != 0) { // Corregir
					
				}
			}
			/*
			 * Falta seguir implementando el comentario de mas abajo
			 */
		
		}
		/*
		8. FOR i := 2 TO nro_ciudades - 1 DO
		9.   r := rand(0, 1); // numero aleatorio entre 0 y 1 
		10.  ciudad_elegida = false;
		11.  IF(r <= K3) THEN
		12.    Elegir la ciudad j que aparece luego de la ciudad
			   indicada por nueva_pos[i-1] en mejor_global;
		13.    IF(ciudad j no está en nueva_pos) THEN
		14.      nueva_pos[i] := ciudad j;
		15.      ciudad_elegida = true;
		16.    END_IF
		17.  END_IF
		18.  IF(r <= (K2 + K3) OR ciudad_elegida = false) THEN
		19.    Elegir la ciudad j que aparece luego de la ciudad indicada por
		       nueva_pos[i-1] en mejor_local;
		20.    IF(ciudad j no está en nueva_pos) THEN
		21.      nueva_pos[i] := ciudad j;
		22.      ciudad_elegida = true;
		23.    END_IF
		24.  END_IF
		25.  IF(ciudad_elegida = false) THEN
		26.    nueva_pos[i] := ultima ciudad en la pos_actual que no aparece
		       en nueva_pos;
		27.  END_IF
		28. END_FOR
		29. nueva_pos[nro_ciudades] := ciudad restante;
		30. retornar(nueva_pos);
		*/
		
		return nuevaPos;
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
