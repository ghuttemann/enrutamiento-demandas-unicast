/*
 * @(#)PrincipalAG.java
 */
package enrut.test;

import java.util.LinkedList;
import enrut.ag.Cromosoma;
import enrut.ag.Poblacion;
import enrut.ag.oper.impl.cruce.CruceUniforme;
import enrut.ag.oper.impl.mutacion.MutacionGenes;
import enrut.ag.oper.impl.seleccion.TorneoBinario;
import enrut.utils.Config;
import enrut.utils.ImpresionSalida;

public class PrincipalAG {
	
	private PrincipalAG() {
	}
	
	/*
	 * Prueba principal del algoritmo genetico 
	 * para la optimización de demandas unicast.
	 */
	public static void main(String[] args) {
		
		// Controlamos si recibimos el directorio de configuracion
		if (args.length != 1)
			throw new Error("Falta nombre de Carpeta de Configuración");
		
		// Cargamos la configuracion
		Config conf = new Config();
		conf.cargar(args[0]);
		
		// El tiempo máximo está en minutos
		long maxTiempo = 1000L * conf.getMaxTiempo();
		
		// El intervalo de muestreo está en segundos
		long intervaloMuestra = conf.getIntervaloMuestra(); // el valor leido está en segundos
		
		// Resultados historicos
		LinkedList<String[]> historico = new LinkedList<String[]>();
		
		// Guardamos los titulos de las columnas
		historico.add(new String[]{"Tiempo", "Costo"});

		
		/*
		 * Comenzamos las corridas
		 */
		for (int k = 1; k <= conf.getCantCorridas(); k++) {
			ImpresionSalida.imprimirTituloAG();
			long iteradorTiempo = intervaloMuestra; // se evalua de a 5 segundos
			
			// Variables contadoras
			int iteraciones = 0;
			int reinicios   = 0;

			// Comenzamos a medir el tiempo
			long tiempoActual;
			long tiempoInicio = System.currentTimeMillis();
		
//-----------------------------| A G |----------| I N I C I O |-----------------------------------//
			
			// Inicializamos la población
			Poblacion poblacion = inicializarPoblacion(conf);
			poblacion.descartarIguales();
			poblacion.evaluar();

			// -----------------------| Ciclo principal |-----------------
			while (true) {
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
				if (poblacion.reinicializar(0.8)) {
					// se guarda el mejor antes de reinicializar
					Cromosoma best = poblacion.getMejorIndividuo();
					poblacion = inicializarPoblacion(conf);

					// se actualiza el mejor historico
					poblacion.setMejorIndividuo(best);
					
					poblacion.descartarIguales();
					poblacion.evaluar();
					reinicios++;
				}

				// Contamos las iteraciones
				iteraciones++;
				
				// Medimos el tiempo actual
				tiempoActual = System.currentTimeMillis();

				// Si transcurrió el intervalo de muestra...
				if (tiempoActual - tiempoInicio >= iteradorTiempo) {

					// Imprimimos la salida, y...
					ImpresionSalida.traza((tiempoActual-tiempoInicio), 
										  iteraciones, reinicios,
										  poblacion.getMejorCosto(),
										  poblacion.getMejorFitness());
					
					// ...registramos datos estadísticos
					ImpresionSalida.registrarDatosHistoricos(historico, 
											 tiempoActual - tiempoInicio,
											 poblacion.getMejorCosto());
					
					// Incrementamos el muestreo pero en tiempo
					iteradorTiempo += intervaloMuestra;
				}
				
				// Si llegamos al tiempo, terminamos
				if (tiempoActual - tiempoInicio >= maxTiempo)
					break;
			}

//-----------------------------| P S O |----------| F I N |-----------------------------------//
			
			// -----------------------| Finalización |-----------------------
			
			// Registramos datos estadísticos finales
			ImpresionSalida.registrarDatosHistoricos(historico, maxTiempo, 
								poblacion.getMejorCosto());
			
			// Registramos la cantidad de reinicios
			historico.addFirst(new String[]{"Reinicios", String.valueOf(reinicios)});
			
			// Escribimos el historico
			ImpresionSalida.escribirHistorico(k, args[0], reinicios, historico);

			System.out.println();
			System.out.println();
			System.out.println("RESULTADO FINAL:");
			ImpresionSalida.traza((tiempoActual-tiempoInicio),
								  iteraciones, reinicios,
								  poblacion.getMejorCosto(),
								  poblacion.getMejorFitness());
			
			System.out.println();
			System.out.println("SOLUCION:");
			poblacion.getMejorIndividuo().imprimir();
			
			/*
			 * En caso de que no exista solución válida, imprimimos
			 * dicho mensaje para notificarlo.
			 */
			if (poblacion.getMejorFitness() < conf.getCantAristas()) {
				System.out.println("\n");
				System.out.println("NO EXISTE SOLUCION VALIDA.");
			}
		}
		
		System.out.println();
		System.out.println("PARAMETROS INICIALES:");
		conf.imprimir();
	}
		
	private static Poblacion inicializarPoblacion(Config conf) {
		Poblacion p = new Poblacion(conf.getDemandas(), 
									conf.getTamPoblacion(),
									conf.getCantAristas());
	
		p.setOperadorCruce(new CruceUniforme());
		p.setOperadorMutacion(new MutacionGenes());
		p.setOperadorSeleccion(new TorneoBinario());	
		return p;
	}
}
