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
		
		// Id del algoritmo
		String id = "AlgoritmoAG";
		
		// Cargamos la configuracion
		Config conf = new Config(id);
		conf.cargarParametros(args[0]);
		
		// El tiempo máximo está en minutos
		long maxTiempo = 1000L * conf.getMaxTiempo();
		
		// El intervalo de muestreo está en milisegundos
		long intervaloMuestra = conf.getIntervaloMuestra();
		
		// Para imprimir la salida
		ImpresionSalida salida = new ImpresionSalida(id);
		
		
		/*
		 * Comenzamos las corridas
		 */
		for (int k = 1; k <= conf.getCantCorridas(); k++) {
			// Resultados historicos
			LinkedList<String[]> historico = new LinkedList<String[]>();
			
			// Guardamos los titulos de las columnas
			historico.add(new String[]{"Tiempo", "Costo"});
			
			// Cargamos las rutas
			conf.cargarRutas(args[0]);
			
			salida.imprimirTitulo(id);
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
				 *  Se reinicializa si hay un porcentaje
				 *  alto de inválidos.
				 */
				if (poblacion.reinicializar()) {
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
					salida.traza((tiempoActual-tiempoInicio),
										  iteraciones, reinicios,
										  poblacion.getMejorCosto(),
										  poblacion.getMejorFitness());
					
					// ...registramos datos estadísticos
					salida.registrarDatosHistoricos(historico, 
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
			salida.registrarDatosHistoricos(historico, maxTiempo, 
					poblacion.getMejorCosto());
			
			// Registramos la cantidad de reinicios
			historico.addFirst(new String[]{"Reinicios", String.valueOf(reinicios)});
			
			// Escribimos el historico
			salida.escribirHistorico(k, args[0], reinicios, historico);

			System.out.println();
			System.out.println();
			System.out.println("RESULTADO FINAL:");
			salida.traza((tiempoActual-tiempoInicio),
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
			
			// Liberamos memoria
			System.gc();
		}
		
		System.out.println();
		System.out.println("PARAMETROS INICIALES:");
		conf.imprimir();
	}
		
	private static Poblacion inicializarPoblacion(Config conf) {
		Poblacion p = new Poblacion(conf.getDemandas(), 
									conf.getTamPoblacionAG(),
									conf.getCantAristas(),
									conf.getProbMutacionAG());
		
		p.setPorcentajeReinicializacion(conf.getPorcReinicioAG() / 100.0);
	
		p.setOperadorCruce(new CruceUniforme());
		p.setOperadorMutacion(new MutacionGenes());
		p.setOperadorSeleccion(new TorneoBinario());	
		return p;
	}
}
