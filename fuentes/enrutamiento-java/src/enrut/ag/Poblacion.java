/*
 * @(#)Poblacion.java
 */
package enrut.ag;

import enrut.ag.oper.OperadorCruce;
import enrut.ag.oper.OperadorMutacion;
import enrut.ag.oper.OperadorSeleccion;

public class Poblacion {
	/*
	 * Individuos de la poblaci�n
	 */
	private Cromosoma[] individuos;
	
	/*
	 * Operador de cruce
	 */
	private OperadorCruce operadorCruce;
	
	/*
	 * Operador de mutaci�n
	 */
	private OperadorMutacion operadorMutacion;
	
	/*
	 * Operador de selecci�n
	 */
	private OperadorSeleccion operadorSeleccion;
	
	/**
	 * Crea una nueva poblaci�n cant individuos
	 * @param demandas Las demandas solicitadas
	 * @param cant La cantidad de individuos a generar
	 */
	public Poblacion(Demanda[] demandas, int cant) {
		individuos = new Cromosoma[cant];
		
		for (int i=0; i < individuos.length; i++) {
			individuos[i] = new Cromosoma(demandas);
			individuos[i].generarGenes();
		}
	}
	
	/**
	 * Crea una poblaci�n con 32 individuos
	 * @param demandas Las demandas solicitadas
	 */
	public Poblacion(Demanda[] demandas) {
		this(demandas, 32);
	}
	
	public int getTama�o() {
		return individuos.length;
	}
	
	/**
	 * Compara cada individuo de la poblaci�n con
	 * los dem�s y reemplaza los cromosomas duplicados
	 * por nuevos, generados aleatoriamente.
	 */
	public void descartarIguales() {
		
	}
	
	public void mutar() {
		
	}
	
	public void cruzar() {
		
	}
	
	public void seleccionar() {
		
	}
	
	public void reemplazar() {
		
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
}
