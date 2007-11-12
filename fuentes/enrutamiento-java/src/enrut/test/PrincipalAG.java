/*
 * @(#)PrincipalAG.java
 */
package enrut.test;

import enrut.ag.Cromosoma;
import enrut.ag.Demanda;
import enrut.ag.Poblacion;
import enrut.ag.oper.impl.cruce.CruceUniforme;
import enrut.ag.oper.impl.mutacion.MutacionUnGen;
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
		// -----------------------| Variables |--------------------------
		Poblacion poblacion;
		Config conf;

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
		int maxIteraciones = conf.getMaxIteraciones();
		while (iteraciones < maxIteraciones) {
			Cromosoma[] selectos = poblacion.seleccionar();
			poblacion.cruzar(selectos);
			poblacion.mutar();
			poblacion.reemplazar();
			poblacion.descartarIguales();
			poblacion.evaluar();
		}
		
		// -----------------------| Finalización |-----------------------
		System.out.println("¡¡¡END OF PROGRAM!!!");
	}
	
	private static void imprimirTitulo() {
		System.out.println("      ..................................");
		System.out.println("-----| Optimización de Demandas Unicast |------");
		System.out.println("      ..................................");
	}
	
	private static Config cargarConfiguracion(String path) {
		Config config = new Config();
		Lector lector = new Lector(path + "config.txt");
		
		String linea = lector.leerLinea();
		while (linea != null) {
			String[] partes = linea.split("=");
			
			try {
				if (partes[0].equalsIgnoreCase("MAX_ITERACIONES")) {
					int valor = Integer.parseInt(partes[1]);
					config.setMaxIteraciones(valor);
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
					/*
					 * Del archivo demandas.txt deben leerse las
					 * demandas y crear el arreglo correspondiente
					 */
					Demanda[] demandas = null;
					
					ConstructorCaminos builder = new ConstructorCaminos();
					builder.leerCaminos(demandas, config.getMaxCaminos(), partes[1]);
					config.setDemandas(demandas);
				}
				else {
					throw new Error("Valor de configuración incorrecto");
				}
			}
			catch (NumberFormatException e) {
				System.out.println("Error de conversión numérica");
				e.printStackTrace();
				System.exit(0);
			}
		}
		
		return config;
	}
	
	private static Poblacion inicializarPoblacion(Config conf) {
		Poblacion p = new Poblacion(conf.getDemandas(), conf.getTamPoblacion());
		
		p.setOperadorCruce(new CruceUniforme());
		p.setOperadorMutacion(new MutacionUnGen());
		p.setOperadorSeleccion(new TorneoBinario());
		
		return p;
	}
}
