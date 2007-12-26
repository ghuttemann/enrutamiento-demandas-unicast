/*
 * @(#)Camino.java
 */
package enrut.grafo;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/**
 * Implementa un camino de un grafo, representado
 * por una secuencia de las aristas que lo componen.
 *
 * @see Arista
 * @see Vertice
 * @see Grafo
 * @author Germán Hüttemann Arza
 */
public class Camino {
	/*
	 * Costo del camino
	 */
	private double costo;
	
	/*
	 * Capacidad mínima del camino
	 */
	private double capacidadMinima;
	
	/*
	 * Incremento actual del arreglo
	 */
	private int incremento;
	
	/*
	 * Mantenemos las aristas de este camino
	 * en estas dos estructuras.
	 * El Vector sirve para buscarlas por posición (secuencia).
	 * El Hashtable sirve para realizar búsquedas O(1).
	 */
	private Vector<Arista> secuencia;
	private Hashtable<String, Arista> hash;
	
	/*
	 * Incremento inicial del arreglo
	 */
	private final int INIT_INCR  = 5;
	
	/*
	 * Primer incremento del arreglo
	 */
	private final int FIRST_INCR = 10;
	
	/*
	 * Incremento por defecto del arreglo
	 */
	private final int NEXT_INCR  = 15;
 	
 	/**
 	 * Construye un nuevo camino
 	 */
 	public Camino() {
 		costo = 0;
 		capacidadMinima = 0;
 		incremento = INIT_INCR;
 		secuencia = new Vector<Arista>(10);
 		hash = new Hashtable<String, Arista>();
 	}
 	
 	/**
 	 * Agrega una nueva arista al camino
 	 */
 	public void agregarArista(Arista a) {
 		if (secuencia.capacity() == secuencia.size()) {
 			secuencia.ensureCapacity(secuencia.size() + incremento);
 			incremento = getSgteIncremento();
 		}
 		
 		/*
 		 * Verificamos que el nodo origen de la
 		 * nueva arista sea igual al nodo destino
 		 * de la última arista del camino.
 		 */
 		if (!this.estaVacio() && this.getUltimaArista().getDestino() != a.getOrigen())
 			throw new Error("El origen de la nueva arista no coincide con el destino de" +
 					" la última arista del camino");
 		
 		secuencia.add(a);
 		costo += a.getCosto();
 		
 		// Actualizamos la capacidad mínima del camino
 		double capacidadArista = a.getCapacidad();
 		if (capacidadMinima == 0 || capacidadArista < capacidadMinima)
 			capacidadMinima = capacidadArista;
 		
 		// Agregamos la arista también al hash
 		hash.put(a.toString(), a);
 	}
 	
 	/**
 	 * Quita la última arista del camino
 	 */
 	public Arista quitarArista() {
 		Arista a = secuencia.remove(secuencia.size()-1);
 		costo -= a.getCosto();
 		
 		// Actualizamos la capacidad mínima del camino
 		if (a.getCapacidad() == capacidadMinima)
 			capacidadMinima = getMenorCapacidad();
 		
 		// Quitamos la arista también del hash
 		hash.remove(a.toString());
 		
 		return a;
 	}
 	
 	/*
 	 * Actualiza la capacidad minima del camino.
 	 */
 	private double getMenorCapacidad() {
 		/*
 		 * Si el camino se ha vaciado,
 		 * entonces su capacidad es cero.
 		 */
 		if (this.estaVacio())
 			return 0;
 		
 		// Calculamos la menor capacidad
 		Iterator<Arista> it = secuencia.iterator();
 		double menor = it.next().getCapacidad();
 		while (it.hasNext()) {
 			double tmp = it.next().getCapacidad();
 			if (tmp < menor)
 				menor = tmp;
 		}
 		return menor;
 	}
 	
 	/**
 	 * Verifica si el camino está vacío.
 	 */
 	public boolean estaVacio() {
 		return secuencia.isEmpty();
 	}
 	
 	/**
 	 * Obtiene la longitud de este camino
 	 */
 	public int getLongitud() {
 		return secuencia.size();
 	}
 	
 	/**
 	 * Obtiene la i-ésima arista del camino
 	 */
 	public Arista getArista(int i) {
 		return secuencia.get(i);
 	}
 	
 	/**
 	 * Verifica si una arista dada está en el camino
 	 */
 	public boolean contiene(Arista a) {
 		return hash.containsKey(a.toString());
 	}
 	
 	/**
 	 * Obtiene la primera arista del camino
 	 */
 	public Arista getPrimeraArista() {
 		return secuencia.firstElement();
 	}
 	
 	/**
 	 * Obtiene la última arista del camino
 	 */
 	public Arista getUltimaArista() {
 		return secuencia.lastElement();
 	}
 	
 	/**
 	 * Obtiene el costo del camino
 	 */
 	public double getCosto() {
 		return costo;
 	}
 	
 	/**
 	 * Obtiene la capacidad mínima del camino
 	 */
 	public double getCapacidadMinima() {
 		return capacidadMinima;
 	}
 	
 	/*
 	 * Actualiza el incremento del arreglo.
 	 */
 	private int getSgteIncremento() {
 		if (incremento == INIT_INCR)
 			return FIRST_INCR;
 		else if (incremento == FIRST_INCR)
 			return NEXT_INCR;
 		else
 			return 0;
 	}
 	
 	/**
 	 * Imprime el camino en la salida estándar.
 	 */
 	public void imprimir() {
 		System.out.print(" | ");
 		for (int i=0; i < secuencia.size()-1; i++)
 			System.out.print(getArista(i).getOrigen() + "-");
 		
 		System.out.println(getUltimaArista().getDestino() + " |");
 	}
 	
 	@Override
 	public boolean equals(Object obj) {
 		String thisClass = this.getClass().getName();
 		String objClass  = this.getClass().getName();
 		
 		// Resultado de la opearción
 		boolean resultado = false;
 		
 		// Realizamos la comparación
 		if (thisClass.equalsIgnoreCase(objClass)) {
 			Camino cam = (Camino) obj;
 			if (this.secuencia.size() == cam.secuencia.size()) {
 				resultado = true;
 				for (int i=0; i < this.secuencia.size(); i++) {
 					Arista a = this.secuencia.get(i);
 					Arista b = cam.secuencia.get(i);
 					if (!a.equals(b)) {
 						resultado = false;
 						break;
 					}
 				}
 			}
 		}
 		return resultado; 
 	}
 	
 	/**
 	 * Retorna una copia de este camino
 	 */
 	public Camino clonar() {
 		Camino cam = new Camino();
 		
 		for (int i=0; i < this.getLongitud(); i++)
 			cam.agregarArista(this.getArista(i));
 		
 		return cam;
 	}
 	
 	/* 
 	 * Convierte este camino a cadena.
 	 */
 	public String toString() {
 		StringBuffer buffer = new StringBuffer();
 		buffer.append(this.getLongitud());
 		buffer.append("-" + this.getCosto());
 		buffer.append("-" + this.getCapacidadMinima());
 		
 		for (int i=0; i < this.getLongitud(); i++) {
 			Arista a = this.getArista(i);
 			buffer.append("-" + a);
 		}
 		
 		return buffer.toString();
 	}
 	
 	public String toString2() {
 		String retorno;
 		retorno = "[De:" + getArista(0).getOrigen();
 		retorno += " A:" + getUltimaArista().getDestino();
 		retorno += "  |  Costo: " + getCosto() + "] \n";
 		retorno += "\t";//"         "; // El vacio a Demanda n = 
 		retorno += "  [Ruta: ";
 		for (int i=0; i < secuencia.size(); i++)
 			retorno += getArista(i).getOrigen() + "-";
 		
 		retorno += "" + getUltimaArista().getDestino() + "]";
 		return retorno;
 	}
 	
 	/**
 	 * Quita todas las aristas de este camino.
 	 */
 	public void vaciar() {
 		while (!this.estaVacio())
 			this.quitarArista();
 	}
}