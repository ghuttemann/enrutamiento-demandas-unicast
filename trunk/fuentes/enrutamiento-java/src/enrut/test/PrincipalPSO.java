/**
 * PrincipalPSO.java
 * Pagina Principal del algoritmo PSO para conjunto de 
 * demandas unicast.
 */
package enrut.test;

import java.io.FileWriter;
import java.io.IOException;
import au.com.bytecode.opencsv.CSVWriter;
import enrut.ag.Demanda;
import enrut.grafo.ConstructorCaminos;
import enrut.pso.Enjambre;
import enrut.pso.Particula;
//import enrut.pso.oper.impl.MovimientoDiversidad;
import enrut.pso.oper.impl.MovimientoTradicional;
import enrut.utils.Config;
import enrut.utils.Lector;

public class PrincipalPSO {

	/*
	 * Prueba principal del algoritmo PSO para la optimización de demandas
	 * unicast.
	 */
	public static void main(String[] args) {
		for (int k = 1; k <= 1; k++) {
			// -----------------------| Variables |--------------------------
			Enjambre enjambre;
			Config conf;
			
			// -----------------------| Control |-----------------------
			if (args.length != 1)
				throw new Error("Falta nombre de Carpeta de Configuración");

			// -----------------------| Inicialización |-----------------
			imprimirTitulo();
			conf = cargarConfiguracion(args[0]);
			enjambre = inicializarEnjambre(conf);

			/*
			 * Variables para calculo de Resultados.
			 * Correr por: 5 min (300 seg)
			 * Tomar muestra cada: 5 seg.
			 */
			long maxTiempo = 60000L * conf.getMaxTiempo();
			long muestra = 5000;
			int size = ((int) (maxTiempo/muestra)) + 1;
			String[] tiempos = new String[size];
			String[] fitness = new String[size];
			tiempos[0] = "Tiempo";
			fitness[0] = "Costo";
			int indiceTiempo = 1;
			long iteradorTiempo = muestra; // se evalua de a 5 segundos
			
			// -----------------------| Algoritmo PSO |------------------
			enjambre.descartarIguales(); // Opcional
			enjambre.evaluar();

			int iteraciones = 0;
			int reinicios = 0;
			boolean parada = false;

			long tiempoActual;
			long inicio = System.currentTimeMillis();

			while (!parada) {
				enjambre.nuevasPosiciones(); // Proceso Principal
				enjambre.descartarIguales(); // Opcional
				enjambre.evaluar();
				
				/*
				 * Se reinicializa si mas del 80% de las 
				 * particulas actuales son inválidas.
				 */
				if (enjambre.reinicializar(1.1)) {
					// se guarda el mejor antes de reinicializar
					Particula best = enjambre.getMejorParticula();
					enjambre = inicializarEnjambre(conf);
					
					// se actualiza el mejor historico
					enjambre.setMejorParticula(best);
					
					enjambre.descartarIguales(); //Opcional
					enjambre.evaluar();
					reinicios++;
				}

				iteraciones++;
				tiempoActual = System.currentTimeMillis();

				if (tiempoActual - inicio >= maxTiempo)
					parada = true;
				else if (tiempoActual - inicio >= iteradorTiempo) {
					System.out.println("Generacion: "+iteraciones);
					imprimirMejor(enjambre);
					tiempos[indiceTiempo] = "" + (tiempoActual - inicio);
					fitness[indiceTiempo] = "" + enjambre.getMejorCosto();
					indiceTiempo++;
					iteradorTiempo += muestra;
				}
			}

			tiempos[indiceTiempo] = "" + maxTiempo;
			fitness[indiceTiempo] = "" + enjambre.getMejorCosto();

			if (enjambre.getMejorFitness() < conf.getCantAristas()) {
				System.out.println("\n");
				System.out.println("NO EXISTE SOLUCION VALIDA.");
			}

			System.out.println("\n");
			System.out.println("ULTIMA POBLACION GENERADA");
			System.out.println("Numero de Reinicios = " + reinicios);
			imprimirMejor(enjambre);
			System.out.println();
			//enjambre.imprimir();
			// ----------------------| Escribir el Historico |-----------------

			CSVWriter writer = null;
			try {
				String path = args[0] + "historico" + k + ".csv";
				writer = new CSVWriter(new FileWriter(path));
			} catch (IOException e) {
				System.out.println("Error de escritura del archivo historico");
				e.printStackTrace();
				System.exit(0);
			}

			writer.writeNext(tiempos);
			writer.writeNext(fitness);
			String[] re = { "Reinicios", ("" + reinicios) };
			writer.writeNext(re);

			try {
				writer.close();
			}
			catch (IOException e) {
				System.out.println("Error para cerrar archivo historico");
				e.printStackTrace();
				System.exit(0);
			}
			// -----------------------| Finalización |-----------------------
			System.out.println("¡¡¡END OF PROGRAM!!!");
		}
	}

	/*
	 * ------------------------------------------
	 * --Funciones de Inicialización y Formateo--
	 * ------------------------------------------
	 */
	private static void imprimirTitulo() {
		System.out.println("      ..................................");
		System.out.println("-----| Optimización de Demandas Unicast |------");
		System.out.println("      ..................................");
	}

	private static void imprimirMejor(Enjambre enjambre) {
		System.out.println();
		System.out.println("El mejor es:");
		double fitness = enjambre.getMejorFitness();
		System.out.println("Fitness : " + fitness);
		enjambre.getMejorParticula().imprimir();
	}

	private static Config cargarConfiguracion(String path) {
		Config config = new Config();
		Lector lector = new Lector(path + "config.txt");

		String linea = lector.leerLinea();
		while (linea != null) {
			String[] partes = linea.split("=");

			try {
				if (partes[0].equalsIgnoreCase("MAX_TIEMPO")) {
					int valor = Integer.parseInt(partes[1]);
					config.setMaxTiempo(valor);
				} else if (partes[0].equalsIgnoreCase("MAX_CAMINOS")) {
					int valor = Integer.parseInt(partes[1]);
					config.setMaxCaminos(valor);
				} else if (partes[0].equalsIgnoreCase("MAX_POBLACION")) {
					int valor = Integer.parseInt(partes[1]);
					config.setTamPoblacion(valor);
				} else if (partes[0].equalsIgnoreCase("PATH_ARCHIVOS")) {
					Demanda[] demandas = null;
					demandas = getDemandas(path + "demandas.txt");

					ConstructorCaminos builder = new ConstructorCaminos();
					builder.leerCaminos(demandas, config.getMaxCaminos(),
							partes[1]);
					config.setDemandas(demandas);
				} else {
					throw new Error("Valor de configuración incorrecto: "
							+ partes[0]);
				}
			} catch (NumberFormatException e) {
				System.out.println("Error de conversión numérica");
				e.printStackTrace();
				System.exit(0);
			}
			linea = lector.leerLinea();
		}

		// Al Final escribimos al cantidad de aristas del grafo
		int cantAristas = getCantAristas(path + "grafo.txt");
		config.setCantAristas(cantAristas);

		lector.cerrar();
		return config;
	}

	private static int getCantAristas(String path) {

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
		} catch (NumberFormatException e) {
			System.out.println("Error de conversión numérica");
			e.printStackTrace();
			System.exit(0);
		}

		lector.cerrar();

		return cantidad;
	}

	private static Demanda[] getDemandas(String path) {

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
		} catch (NumberFormatException e) {
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
			} catch (NumberFormatException e) {
				System.out.println("Error de conversión numérica");
				e.printStackTrace();
				System.exit(0);
			}
			d[i] = new Demanda(origen, destino, capacidad);
		}

		lector.cerrar();
		return d;
	}

	private static Enjambre inicializarEnjambre(Config conf) {
		Enjambre p = new Enjambre(conf.getDemandas(), conf.getTamPoblacion(),
				conf.getCantAristas());
		
		p.setOperadorMovimiento(new MovimientoTradicional());
		
		return p;
	}
}
