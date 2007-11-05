/*
 * @(#)Poblacion.java
 */
package enrut.ag;

public class Poblacion {
	/*
	 * Individuos de la poblaci�n
	 */
	private Cromosoma[] individuos;
	
	/**
	 * Crea una nueva poblaci�n cant individuos
	 * @param demandas Las demandas solicitadas
	 * @param cant La cantidad de individuos a generar
	 */
	public Poblacion(Demanda[] demandas, int cant) {
		individuos = new Cromosoma[cant];
		
		for (int i=0; i < individuos.length; i++) {
			individuos[i] = new Cromosoma(demandas);
		}
	}
	
	/**
	 * Crea una poblaci�n con 32 individuos
	 * @param demandas Las demandas solicitadas
	 */
	public Poblacion(Demanda[] demandas) {
		this(demandas, 32);
	}
	
	public void mutar() {
		
	}
	
	public void cruzar() {
		
	}
	
	public void seleccionar() {
		
	}
	
	public void reemplazar() {
		
	}
}
