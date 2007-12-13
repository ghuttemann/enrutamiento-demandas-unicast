/*
 * @(#)Config.java
 */
package enrut.utils;

import enrut.Demanda;
import enrut.grafo.ConstructorCaminos;

public class Config {
	private String id;
	private String pathRutas;
	private Demanda[] demandas;
	private int maxCaminos;
	private int maxTiempo;
	private int tamPoblacionAG;
	private int tamPoblacionPSO;
	private int cantAristas;
	private int cantCorridas;
	private int intervaloMuestra;
	private int porcReinicioAG;
	private int porcReinicioPSO;
	private int probMutacionAG;
	
	public Config(String id) {
		this.id = id;
	}

	public Demanda[] getDemandas() {
		return demandas;
	}

	public void setDemandas(Demanda[] demandas) {
		this.demandas = demandas;
	}

	public int getMaxCaminos() {
		return maxCaminos;
	}

	public int getMaxTiempo() {
		return maxTiempo;
	}

	public int getTamPoblacionAG() {
		return tamPoblacionAG;
	}
	
	public int getTamPoblacionPSO() {
		return tamPoblacionPSO;
	}
	
	public int getCantAristas() {
		return cantAristas;
	}

	public int getCantCorridas() {
		return cantCorridas;
	}

	public int getIntervaloMuestra() {
		return intervaloMuestra;
	}
	
	public int getPorcReinicioAG() {
		return porcReinicioAG;
	}

	public int getPorcReinicioPSO() {
		return porcReinicioPSO;
	}

	public int getProbMutacionAG() {
		return probMutacionAG;
	}
	
	public void cargarParametros(String path) {
		Lector lector = new Lector(path + "config.txt");
		
		// Indica si ya se leyó la cantidad de caminos
		boolean maxCaminosLeido = false;

		String linea = lector.leerLinea();
		while (linea != null) {
			
			if (!linea.startsWith("#") && !linea.equals("")) {
				String[] partes = linea.split("=");
				
				partes[0] = partes[0].trim();
				partes[1] = partes[1].trim();
	
				try {
					if (partes[0].equalsIgnoreCase("MAX_TIEMPO")) {
						int valor = Integer.parseInt(partes[1]);
						this.setMaxTiempo(valor);
					}
					else if (partes[0].equalsIgnoreCase("MAX_CAMINOS")) {
						int valor = Integer.parseInt(partes[1]);
						this.setMaxCaminos(valor);
						maxCaminosLeido = true;
					}
					else if (partes[0].equalsIgnoreCase("TAM_POBLACION_AG")) {
						int valor = Integer.parseInt(partes[1]);
						this.setTamPoblacionAG(valor);
					}
					else if (partes[0].equalsIgnoreCase("TAM_POBLACION_PSO")) {
						int valor = Integer.parseInt(partes[1]);
						this.setTamPoblacionPSO(valor);
					}
					else if (partes[0].equalsIgnoreCase("CANT_CORRIDAS")) {
						int valor = Integer.parseInt(partes[1]);
						this.setCantCorridas(valor);
					}
					else if (partes[0].equalsIgnoreCase("INTERVALO_MUESTRA")) {
						int valor = Integer.parseInt(partes[1]);
						this.setIntervaloMuestra(valor);
					}
					else if (partes[0].equalsIgnoreCase("PROB_MUTACION_AG")) {
						int valor = Integer.parseInt(partes[1]);
						this.setProbMutacionAG(valor);
					}
					else if (partes[0].equalsIgnoreCase("PORC_REINICIO_AG")) {
						int valor = Integer.parseInt(partes[1]);
						this.setPorcReinicioAG(valor);
					}
					else if (partes[0].equalsIgnoreCase("PORC_REINICIO_PSO")) {
						int valor = Integer.parseInt(partes[1]);
						this.setPorcReinicioPSO(valor);
					}
					else if (partes[0].equalsIgnoreCase("TABLA_RUTAS")) {
						/*
						 * Comprobamos si ya se leyó la variable
						 * MAX_CAMINOS, ya que se utiliza para leer
						 * cargar los caminos.
						 */
						if (!maxCaminosLeido) {
							throw new Error("Antes de la lectura de la variable " +
											"TABLA_RUTAS, debe leerse la variable MAX_CAMINOS");
						}
						
						// Cargamos las demandas
						demandas = getDemandas(path + "demandas.txt");
						
						// Guardamos el directorio de las rutas
						pathRutas = partes[1];
					}
					else {
						throw new Error("Valor de configuración incorrecto: \"" + partes[0] + "\"");
					}
				}
				catch (NumberFormatException e) {
					System.out.println("Error de conversión numérica");
					e.printStackTrace();
					System.exit(0);
				}
			}
			linea = lector.leerLinea();
		}

		// Al Final escribimos al cantidad de aristas del grafo
		int cantAristas = getCantAristas(path + "grafo.txt");
		this.setCantAristas(cantAristas);

		lector.cerrar();
	}
	
	public void cargarRutas(String path) {
		if (demandas == null)
			throw new Error("Todavia no se inicializaron las demandas");
		
		ConstructorCaminos builder = new ConstructorCaminos();
		builder.leerCaminos(demandas, this.getMaxCaminos(), path + pathRutas);
		this.setDemandas(demandas);
	}

	private void setMaxCaminos(int maxCaminos) {
		this.maxCaminos = maxCaminos;
	}

	private void setMaxTiempo(int maxTiempo) {
		this.maxTiempo = maxTiempo;
	}

	private void setTamPoblacionAG(int tamPoblacionAG) {
		this.tamPoblacionAG = tamPoblacionAG;
	}
	
	private void setTamPoblacionPSO(int tamPoblacionPSO) {
		this.tamPoblacionPSO = tamPoblacionPSO;
	}

	private void setCantAristas(int cantAristas) {
		this.cantAristas = cantAristas;
	}

	private void setCantCorridas(int cantCorridas) {
		this.cantCorridas = cantCorridas;
	}

	private void setIntervaloMuestra(int intervaloMuestra) {
		this.intervaloMuestra = intervaloMuestra;
	}
	
	private void setPorcReinicioAG(int porcReinicioAG) {
		this.porcReinicioAG = porcReinicioAG;
	}

	private void setPorcReinicioPSO(int porcReinicioPSO) {
		this.porcReinicioPSO = porcReinicioPSO;
	}

	private void setProbMutacionAG(int probMutacionAG) {
		this.probMutacionAG = probMutacionAG;
	}
	
	private int getCantAristas(String path) {
		Lector lector = new Lector(path);

		String linea = lector.leerLinea(); // Cantidad de Nodos
		linea = lector.leerLinea(); // Cantidad de Aristas
		if (linea == null) {
			System.out.println("Cantidad_Aristas: Archivo de grafo No valido");
			System.exit(0);
		}

		// se lee la cantidad de Aristas
		int cantidad = 0;
		try {
			cantidad = Integer.parseInt(linea);
		}
		catch (NumberFormatException e) {
			System.out.println("Error de conversión numérica");
			e.printStackTrace();
			System.exit(0);
		}

		lector.cerrar();
		return cantidad;
	}

	private Demanda[] getDemandas(String path) {
		Lector lector = new Lector(path);

		String linea = lector.leerLinea();
		if (linea == null) {
			System.out.println("Archivo de demandas no valido");
			System.exit(0);
		}

		// se lee la cantidad de demandas
		int cantidad = 0;
		try {
			cantidad = Integer.parseInt(linea);
		}
		catch (NumberFormatException e) {
			System.out.println("Error de conversión numérica");
			e.printStackTrace();
			System.exit(0);
		}

		// se lee todas las demandas
		Demanda[] d = new Demanda[cantidad];
		for (int i = 0; i < cantidad; i++) {
			linea = lector.leerLinea();
			String[] partes = linea.split(" ");

			int origen = 0;
			int destino = 0;
			double capacidad = 0.0;
			try {
				origen = Integer.parseInt(partes[0]);
				destino = Integer.parseInt(partes[1]);
				capacidad = Double.parseDouble(partes[2]);
			}
			catch (NumberFormatException e) {
				System.out.println("Error de conversión numérica");
				e.printStackTrace();
				System.exit(0);
			}
			d[i] = new Demanda(origen, destino, capacidad);
		}

		lector.cerrar();
		return d;
	}
	
	public void imprimir() {
		System.out.println("Max. Cant. de Caminos     : " + (maxCaminos<=0 ? "ilimitado" : maxCaminos));
		System.out.println("Tiempo de la Corrida      : " + maxTiempo + " seg.");
		
		if (this.id.equalsIgnoreCase("AlgoritmoAG"))
			System.out.println("Tamaño de la Población    : " + tamPoblacionAG + " individuos");
		else if (this.id.equalsIgnoreCase("AlgoritmoPSO"))
			System.out.println("Tamaño del Enjambre       : " + tamPoblacionPSO + " particulas");
		
		System.out.println("Cant. de Aristas del Grafo: " + cantAristas);
		System.out.println("Cantidad de Corridas      : " + cantCorridas);
		System.out.println("Intervalo de la Muestra   : " + intervaloMuestra + " ms.");
		System.out.println("Prob. de Mutación AG      : " + probMutacionAG);
		
		if (this.id.equalsIgnoreCase("AlgoritmoAG"))
			System.out.println("Porc. Reinicialización AG : " + porcReinicioAG);
		else if (this.id.equalsIgnoreCase("AlgoritmoPSO"))
			System.out.println("Porc. Reinicialización PSO: " + porcReinicioPSO);
	}
}
