/*
 * @(#)PrincipalAG.java
 */
package enrut.test;

import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVWriter;
import enrut.ag.Cromosoma;
import enrut.ag.Demanda;
import enrut.ag.Poblacion;
import enrut.ag.oper.impl.cruce.CruceUniforme;
import enrut.ag.oper.impl.mutacion.MutacionGenes;
//import enrut.ag.oper.impl.mutacion.MutacionUnGen;
import enrut.ag.oper.impl.seleccion.TorneoBinario;
import enrut.grafo.ConstructorCaminos;
import enrut.utils.Config;
import enrut.utils.Lector;

public class PrincipalAG {
	
	private PrincipalAG() {
	}
	
	/*
	 * Prueba principal del algoritmo genetico 
	 * para la optimización de demandas unicast.
	 */
	public static void main(String[] args) {
		for (int k =1; k<=10; k++){
		// -----------------------| Variables |--------------------------
		Poblacion poblacion;
		Config conf;
		/*
		 * Variables para calculo de Resultados.
		 *  Correr por: 5 min (300 seg)
		 *  Tomar muestra cada: 5 seg.
		 */
		long muestra = 5000; 
		String[] tiempos = new String [61];
		String[] fitness = new String [61];
		tiempos[0] = "Tiempo";
		fitness[0] = "Costo";
		int indiceTiempo = 1;
		long iteradorTiempo = muestra; // se evalua de a 5 segundos

		// -----------------------| Control |-----------------------
		if (args.length != 1)
			throw new Error("Falta nombre de Carpeta de Configuración");
		
		// -----------------------| Inicialización |-----------------
		imprimirTitulo();
		conf = cargarConfiguracion(args[0]);
		poblacion = inicializarPoblacion(conf);
			
		// -----------------------| Algoritmo Genético |-----------------
		poblacion.descartarIguales();
		poblacion.evaluar();
		
		int iteraciones = 0;
		int reinicios = 0;
		boolean parada = false;
		
		long maxTiempo = 60000L * conf.getMaxTiempo();
		long tiempoActual;
		long inicio = System.currentTimeMillis();
		//System.out.println("Generacion: 0");
		while (!parada) {
			Cromosoma[] selectos = poblacion.seleccionar();
			poblacion.cruzar(selectos);
			poblacion.mutar();
			poblacion.reemplazar();
			poblacion.descartarIguales();
			poblacion.evaluar();
			
			/*
			 *  Se reinicializa si mas del 80% de 
			 *  la población actual es inválida.
			 */
			if (poblacion.reinicializar(0.8)){
				// se guarda el mejor antes de reinicializar
				Cromosoma best = poblacion.getMejorIndividuo();
				poblacion = inicializarPoblacion(conf);
				
				// se actualiza el mejor historico
				poblacion.setMejorIndividuo(best);
				poblacion.descartarIguales();
				poblacion.evaluar();
				reinicios++;
			}
			
			iteraciones++;
			tiempoActual = System.currentTimeMillis();
			
			
			if (tiempoActual - inicio >= maxTiempo)
				parada = true;
			else if (tiempoActual - inicio >= iteradorTiempo){
				//System.out.println("Generacion: "+iteraciones);
				//imprimirMejor(poblacion);
				tiempos[indiceTiempo]= ""+(tiempoActual - inicio);
				fitness[indiceTiempo]= ""+poblacion.getMejorCosto();
				indiceTiempo++;
				iteradorTiempo += muestra; 		
			}
		}
		
		tiempos[indiceTiempo]= ""+maxTiempo;
		fitness[indiceTiempo]= ""+poblacion.getMejorCosto();
		
		
		if (poblacion.getMejorFitness()< conf.getCantAristas()){
			System.out.println("\n");
			System.out.println("NO EXISTE SOLUCION VALIDA.");
		}
		
		System.out.println("\n");
		System.out.println("ULTIMA POBLACION GENERADA");
		System.out.println("Numero de Reinicios = "+reinicios);
		imprimirMejor(poblacion);
		System.out.println();
		poblacion.imprimir();
		// ----------------------| Escribir el Historico |-----------------

		CSVWriter writer = null;
		try {
			String path = args[0]+"historico"+k+".csv";
			writer = new CSVWriter(new FileWriter(path));
		} catch (IOException e){
			System.out.println("Error de escritura del archivo historico");
			e.printStackTrace();
			System.exit(0);			
		}
	
		writer.writeNext(tiempos);
		writer.writeNext(fitness);
		String [] re = {"Reinicios",(""+reinicios)};
		writer.writeNext(re);
		
		try {
			writer.close();	
		} catch (IOException e){
			System.out.println("Error para cerrar archivo historico");
			e.printStackTrace();
			System.exit(0);			
		}		
		// -----------------------| Finalización |-----------------------
		System.out.println("¡¡¡END OF PROGRAM!!!");
		}
	}
	
	
	/* ------------------------------------------
	 * --Funciones de Inicialización y Formateo--
	 * ------------------------------------------
	 */
	private static void imprimirTitulo() {
		System.out.println("      ..................................");
		System.out.println("-----| Optimización de Demandas Unicast |------");
		System.out.println("      ..................................");
	}
	
	private static void imprimirMejor(Poblacion poblacion){
		System.out.println();
		System.out.println("El mejor es:");
		double fitness = poblacion.getMejorFitness();
		System.out.println("Fitness : "+fitness);
		poblacion.getMejorIndividuo().imprimir();
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
				}
				else if (partes[0].equalsIgnoreCase("MAX_CAMINOS")) {
					int valor = Integer.parseInt(partes[1]);
					config.setMaxCaminos(valor);
				}
				else if (partes[0].equalsIgnoreCase("MAX_POBLACION")) {
					int valor = Integer.parseInt(partes[1]);
					config.setTamPoblacion(valor);
				}
				else if (partes[0].equalsIgnoreCase("PATH_ARCHIVOS")) {
					Demanda[] demandas = null;
					demandas = getDemandas(path+"demandas.txt");
					
					ConstructorCaminos builder = new ConstructorCaminos();
					builder.leerCaminos(demandas, config.getMaxCaminos(), partes[1]);
					config.setDemandas(demandas);
				}
				else {
					throw new Error("Valor de configuración incorrecto: " + partes[0]);
				}
			}
			catch (NumberFormatException e) {
				System.out.println("Error de conversión numérica");
				e.printStackTrace();
				System.exit(0);
			}
			linea = lector.leerLinea();
		}
		
		// Al Final escribimos al cantidad de aristas del grafo
		int cantAristas = getCantAristas(path+"grafo.txt");
		config.setCantAristas(cantAristas);
		
		lector.cerrar();
		return config;
	}
	
	private static int getCantAristas(String path){

		Lector lector = new Lector(path);

		String linea = lector.leerLinea(); // Cantidad de Nodos
		linea = lector.leerLinea(); // Cantidad de Aristas
		if (linea == null) {
			System.out.println("Cantidad_Aristas: Archivo de grafo No valido");
			System.exit(0);
		}
		
		// se lee la cantidad de Aristas
		int cantidad=0;
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
	
	private static Demanda[] getDemandas(String path){
		
		Lector lector = new Lector(path);

		String linea = lector.leerLinea();
		if (linea == null) {
			System.out.println("Archivo de demandas no valido");
			System.exit(0);
		}
		
		// se lee la cantidad de demandas
		int cantidad=0;
		try {
			cantidad = Integer.parseInt(linea);
		} catch (NumberFormatException e) {
			System.out.println("Error de conversión numérica");
			e.printStackTrace();
			System.exit(0);
		}
		
		// se lee todas las demandas
		Demanda[] d = new Demanda[cantidad];
		for (int i=0; i<cantidad; i++) {
			linea = lector.leerLinea();
			String[] partes = linea.split(" ");
			
			int origen=0;
			int destino=0;
			double capacidad=0.0;
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
			d[i]= new Demanda(origen, destino, capacidad);
		}
		
		lector.cerrar();
		return d;
	}
	
	private static Poblacion inicializarPoblacion(Config conf) {
		Poblacion p = new Poblacion(conf.getDemandas(), conf.getTamPoblacion(),
				conf.getCantAristas());
	
		p.setOperadorCruce(new CruceUniforme());
		p.setOperadorMutacion(new MutacionGenes());
		p.setOperadorSeleccion(new TorneoBinario());
		
		return p;
	}
}
