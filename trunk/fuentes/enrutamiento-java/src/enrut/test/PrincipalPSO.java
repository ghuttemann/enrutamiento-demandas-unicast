/*
 * @(#)PrincipalPSO.java
 */
package enrut.test;

import java.util.LinkedList;
import enrut.pso.Enjambre;
import enrut.pso.Particula;
import enrut.pso.oper.impl.MovimientoTradicional;
import enrut.utils.Config;
import enrut.utils.ImpresionSalida;

public class PrincipalPSO {
	
	private PrincipalPSO() {
	}

	/*
	 * Prueba principal del algoritmo PSO para la 
	 * optimización de demandas unicast.
	 */
	public static void main(String[] args) {
		
		// Controlamos si recibimos el directorio de configuracion
		if (args.length != 1)
			throw new Error("Falta nombre de Carpeta de Configuración");
		
		// Id del algoritmo
		String id = "PSO";
		
		// Cargamos la configuracion
		Config conf = new Config(id);
		conf.cargarParametros(args[0]);
		
		// El tiempo máximo está en minutos
		long maxTiempo = 1000L * conf.getMaxTiempo();
		
		// El intervalo de muestreo está en segundos
		long intervaloMuestra = conf.getIntervaloMuestra(); // el valor leido está en segundos
		
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
			
			salida.imprimirTituloPSO();
			long iteradorTiempo = intervaloMuestra; // se evalua de a 5 segundos
			
			// Variables contadoras
			int iteraciones = 0;
			int reinicios   = 0;

			// Comenzamos a medir el tiempo
			long tiempoActual;
			long tiempoInicio = System.currentTimeMillis();
			

//-----------------------------| P S O |----------| I N I C I O |-----------------------------------//
			
			// Inicializamos el enjambre
			Enjambre enjambre = inicializarEnjambre(conf);
			enjambre.descartarIguales();
			enjambre.evaluar();

			// -----------------------| Ciclo principal |-----------------
			while (true) {
				enjambre.nuevasPosiciones(); // Proceso Principal
				enjambre.descartarIguales();
				enjambre.evaluar();
				
				/*
				 * Se reinicializa si mas del 50% de las 
				 * particulas actuales son inválidas.
				 */
				if (enjambre.reinicializar()) {
					// se guarda el mejor antes de reinicializar
					Particula best = enjambre.getMejorParticula();
					enjambre = inicializarEnjambre(conf);
					
					// se actualiza el mejor historico
					enjambre.setMejorParticula(best);
					
					enjambre.descartarIguales();
					enjambre.evaluar();
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
										  enjambre.getMejorCosto(), 
										  enjambre.getMejorFitness());
					
					// ...registramos datos estadísticos
					salida.registrarDatosHistoricos(historico, 
											 tiempoActual - tiempoInicio,
											 enjambre.getMejorCosto());
					
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
								enjambre.getMejorCosto());
			
			// Registramos la cantidad de reinicios
			historico.addFirst(new String[]{"Reinicios", String.valueOf(reinicios)});
			
			// Escribimos el historico
			salida.escribirHistorico(k, args[0], reinicios, historico);

			System.out.println();
			System.out.println();
			System.out.println("RESULTADO FINAL:");
			salida.traza((tiempoActual-tiempoInicio), 
								  iteraciones, reinicios,
								  enjambre.getMejorCosto(),
								  enjambre.getMejorFitness());
			
			System.out.println();
			System.out.println("SOLUCION:");
			enjambre.getMejorParticula().imprimir();
			
			/*
			 * En caso de que no exista solución válida, imprimimos
			 * dicho mensaje para notificarlo.
			 */
			if (enjambre.getMejorFitness() < conf.getCantAristas()) {
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

	private static Enjambre inicializarEnjambre(Config conf) {
		Enjambre p = new Enjambre(conf.getDemandas(), 
								  conf.getTamPoblacionPSO(),
								  conf.getCantAristas());
		
		p.setPorcentajeReinicializacion(conf.getPorcReinicioPSO() / 100.0);
		p.setOperadorMovimiento(new MovimientoTradicional());
		return p;
	}
}
