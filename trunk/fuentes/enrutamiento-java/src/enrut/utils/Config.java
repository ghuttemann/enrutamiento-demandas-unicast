/*
 * @(#)Config.java
 */
package enrut.utils;

import enrut.Demanda;
import enrut.grafo.ConstructorCaminos;

/**
 * Clase en la que se leen todos los parámetros 
 * de configuración de los algoritmos.
 */
public class Config {
	/*
	 * Id del algoritmo
	 */
	private String id;

	/*
	 * Directorio de los archivos de rutas
	 */
	private String pathRutas;

	/*
	 * Demandas solicitadas
	 */
	private Demanda[] demandas;

	/*
	 * Máxima cantidad de caminos a leer
	 */
	private int maxCaminos;

	/*
	 * Tiempo que durará la ejecución del algoritmo
	 */
	private int maxTiempo;

	/*
	 * Tamaño de la población (GA).
	 */
	private int tamPoblacionAG;

	/*
	 * Tamaño del enjambre (PSO).
	 */
	private int tamPoblacionPSO;

	/*
	 * Cantidad de aristas del grafo asociado
	 */
	private int cantAristas;

	/*
	 * Cantidad de ejecuciones a realizar
	 */
	private int cantEjecuciones;

	/*
	 * Intervalo de toma de muestra
	 */
	private int intervaloMuestra;

	/*
	 * Porcentaje de reinicialización (GA).
	 */
	private int porcReinicioAG;

	/*
	 * Porcentaje de reinicialización (PSO).
	 */
	private int porcReinicioPSO;

	/*
	 * Probabilidad de mutación (GA).
	 */
	private int probMutacionAG;
	
	/**
	 * Construye un nuevo objeto de configuración
	 */
	public Config(String id) {
		this.id = id;
	}

	/**
	 * Obtiene las demandas solicitadas
	 */
	public Demanda[] getDemandas() {
		return demandas;
	}

	/**
	 * Setea las demandas solicitadas
	 */
	public void setDemandas(Demanda[] demandas) {
		this.demandas = demandas;
	}

	/**
	 * Obtiene la cantidad máxima de caminos
	 */
	public int getMaxCaminos() {
		return maxCaminos;
	}

	/**
	 * Obtiene el tiempo de ejecución
	 */
	public int getMaxTiempo() {
		return maxTiempo;
	}

	/**
	 * Obtiene el tamaño de la población (GA).
	 */
	public int getTamPoblacionAG() {
		return tamPoblacionAG;
	}
	
	/**
	 * Obtiene el tamaño del enjambre (PSO).
	 */
	public int getTamPoblacionPSO() {
		return tamPoblacionPSO;
	}
	
	/**
	 * Obtiene la cantidad de aristas del grafo asociado
	 */
	public int getCantAristas() {
		return cantAristas;
	}

	/**
	 * Obtiene la cantidad de ejecuciones
	 */
	public int getCantEjecuciones() {
		return cantEjecuciones;
	}

	/**
	 * Obtiene el intervalo de muestreo
	 */
	public int getIntervaloMuestra() {
		return intervaloMuestra;
	}
	
	/**
	 * Obtiene el porcentaje de reinicialización de GA
	 */
	public int getPorcReinicioAG() {
		return porcReinicioAG;
	}
	
	/**
	 * Obtiene el porcentaje de reinicialización de PSO
	 */
	public int getPorcReinicioPSO() {
		return porcReinicioPSO;
	}

	/**
	 * Obtiene el porcentaje de mutación de GA.
	 */
	public int getProbMutacionAG() {
		return probMutacionAG;
	}
	
	/**
	 * Carga los parámetros desde el archivo de configuración
	 */
	public void cargarParametros(String path) {
		Lector lector = new Lector(path + "config.txt");
		
		// Indica si ya se leyó la cantidad de caminos
		boolean maxCaminosLeido = false;

		String linea = lector.leerLinea();
		while (linea != null) {
			
			/*
			 * Si la línea comienza con # es un comentario y
			 * si está vacía es una línea en blanco. En cualquier
			 * caso la omitimos.
			 */
			if (!linea.startsWith("#") && !linea.equals("")) {
				// Separamos clave y valor
				String[] partes = linea.split("=");
				
				// Quitamos espacios en blanco
				partes[0] = partes[0].trim();
				partes[1] = partes[1].trim();
	
				// Leemos los parámetros de configuración
				try {
					if (partes[0].equalsIgnoreCase("MAX_TIEMPO")) {
						int valor = Integer.parseInt(partes[1]);
						this.setMaxTiempo(valor);
					}
					else if (partes[0].equalsIgnoreCase("MAX_CAMINOS")) {
						int valor = Integer.parseInt(partes[1]);
						this.setMaxCaminos(valor);
						
						// Ya se leyó la cantidad de caminos
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
					else if (partes[0].equalsIgnoreCase("CANT_EJECUCIONES")) {
						int valor = Integer.parseInt(partes[1]);
						this.setCantEjecuciones(valor);
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
						 * MAX_CAMINOS, ya que se utiliza para
						 * leer/cargar los caminos.
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
	
	/**
	 * Carga las rutas para las demandas solicitadas
	 */
	public void cargarRutas(String path) {
		if (demandas == null)
			throw new Error("Todavia no se inicializaron las demandas");
		
		ConstructorCaminos builder = new ConstructorCaminos();
		builder.leerCaminos(demandas, this.getMaxCaminos(), path + pathRutas);
		this.setDemandas(demandas);
	}

	/*
	 * Setea la cantidad máxima de caminos
	 */
	private void setMaxCaminos(int maxCaminos) {
		this.maxCaminos = maxCaminos;
	}

	/*
	 * Setea el tiempo de ejecución.
	 */
	private void setMaxTiempo(int maxTiempo) {
		this.maxTiempo = maxTiempo;
	}

	/*
	 * Setea el tamaño de la población (GA).
	 */
	private void setTamPoblacionAG(int tamPoblacionAG) {
		this.tamPoblacionAG = tamPoblacionAG;
	}
	
	/*
	 * Setea el tamaño del enjambre (PSO).
	 */
	private void setTamPoblacionPSO(int tamPoblacionPSO) {
		this.tamPoblacionPSO = tamPoblacionPSO;
	}

	/*
	 * Setea la cantidad de aristas del grafo
	 */
	private void setCantAristas(int cantAristas) {
		this.cantAristas = cantAristas;
	}

	/*
	 * Setea la cantidad de ejecuciones
	 */
	private void setCantEjecuciones(int cantEjecuciones) {
		this.cantEjecuciones = cantEjecuciones;
	}

	/*
	 * Setea el intervalo de muestreo
	 */
	private void setIntervaloMuestra(int intervaloMuestra) {
		this.intervaloMuestra = intervaloMuestra;
	}
	
	/*
	 * Setea el porcentaje de reinicialización de GA
	 */
	private void setPorcReinicioAG(int porcReinicioAG) {
		this.porcReinicioAG = porcReinicioAG;
	}

	/*
	 * Setea el porcentaje de reinicialización de PSO
	 */
	private void setPorcReinicioPSO(int porcReinicioPSO) {
		this.porcReinicioPSO = porcReinicioPSO;
	}

	/*
	 * Setea el porcentaje de mutación de GA.
	 */
	private void setProbMutacionAG(int probMutacionAG) {
		this.probMutacionAG = probMutacionAG;
	}
	
	/*
	 * Obtiene la cantidad de aristas del grafo.
	 */
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

	/*
	 * Lee las demandas solicitadas desde el archivo de demandas
	 */
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
	
	/**
	 * Imprime los parámetros de configuración leídos desde el archivo
	 */
	public void imprimir() {
		System.out.println("Max. Cant. de Caminos     : " + (maxCaminos<=0 ? "ilimitado" : maxCaminos));
		System.out.println("Tiempo de la Corrida      : " + maxTiempo + " seg.");
		
		if (this.id.equalsIgnoreCase("AlgoritmoAG"))
			System.out.println("Tamaño de la Población    : " + tamPoblacionAG + " individuos");
		else if (this.id.equalsIgnoreCase("AlgoritmoPSO"))
			System.out.println("Tamaño del Enjambre       : " + tamPoblacionPSO + " particulas");
		
		System.out.println("Cant. de Aristas del Grafo: " + cantAristas);
		System.out.println("Cantidad de Corridas      : " + cantEjecuciones);
		System.out.println("Intervalo de la Muestra   : " + intervaloMuestra + " ms.");
		System.out.println("Prob. de Mutación AG      : " + probMutacionAG);
		
		if (this.id.equalsIgnoreCase("AlgoritmoAG"))
			System.out.println("Porc. Reinicialización AG : " + porcReinicioAG);
		else if (this.id.equalsIgnoreCase("AlgoritmoPSO"))
			System.out.println("Porc. Reinicialización PSO: " + porcReinicioPSO);
	}
}
