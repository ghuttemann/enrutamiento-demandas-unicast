/*
 * @(#)PrincipalAG.java
 */
package enrut.test;

import java.util.LinkedList;

import enrut.Algoritmo;
import enrut.Solucion;
import enrut.utils.Config;
import enrut.utils.ImpresionSalida;

public class Principal {
	
	private Algoritmo algoritmo;
	
	public Principal(Algoritmo alg) {
		this.algoritmo = alg;
	}

	public void ejecutar(String[] args) {
		
		// Controlamos si recibimos el directorio de configuracion
		if (args.length != 1)
			throw new Error("Falta nombre de Carpeta de Configuración");
		
		// Id del algoritmo
		String clase = algoritmo.getClass().getName();
		String id = clase.substring(clase.lastIndexOf(".")+1);
		
		// Cargamos la configuracion
		Config conf = new Config(id);
		conf.cargarParametros(args[0]);
		
		// El tiempo máximo está en segundos
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
			long iteradorTiempo = intervaloMuestra;
			
			// Variables contadoras
			int iteraciones = 0;
			int reinicios   = 0;

			// Comenzamos a medir el tiempo
			long tiempoActual;
			long tiempoInicio = System.currentTimeMillis();
	

//----------------------------------------------| I N I C I O |-----------------------------------//
			
			// Inicializamos la solucion
			algoritmo.inicializar(conf);
			
			// Eliminamos iguales y evaluamos
			algoritmo.descartarIguales();
			algoritmo.evaluar();

			// -----------------------| Ciclo principal |-----------------
			while (true) {
				algoritmo.ejecutar();
				
				// Eliminamos iguales y evaluamos
				algoritmo.descartarIguales();
				algoritmo.evaluar();

				/*
				 *  Se reinicializa si hay un porcentaje
				 *  alto de inválidos.
				 */
				if (algoritmo.reinicializar()) {
					// se guarda el mejor antes de reinicializar
					Solucion best = algoritmo.getMejorSolucion();
					
					// Inicializamos la solucion
					algoritmo.inicializar(conf);

					// se actualiza el mejor historico
					algoritmo.setMejorSolucion(best);
					
					// Eliminamos iguales y evaluamos
					algoritmo.descartarIguales();
					algoritmo.evaluar();
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
										  algoritmo.getMejorCosto(),
										  algoritmo.getMejorFitness());
					
					// ...registramos datos estadísticos
					salida.registrarDatosHistoricos(historico, 
											 tiempoActual - tiempoInicio,
											 algoritmo.getMejorCosto());
					
					// Incrementamos el muestreo pero en tiempo
					iteradorTiempo += intervaloMuestra;
				}
				
				// Si llegamos al tiempo, terminamos
				if (tiempoActual - tiempoInicio >= maxTiempo)
					break;
			}

//------------------------------------------------| F I N |-----------------------------------//

			// -----------------------| Finalización |-----------------------
			
			// Registramos datos estadísticos finales
			salida.registrarDatosHistoricos(historico, maxTiempo, 
					algoritmo.getMejorCosto());
			
			// Registramos la cantidad de reinicios
			historico.addFirst(new String[]{"Reinicios", String.valueOf(reinicios)});
			
			// Escribimos el historico
			salida.escribirHistorico(k, args[0], reinicios, historico);

			System.out.println();
			System.out.println();
			System.out.println("RESULTADO FINAL:");
			salida.traza((tiempoActual-tiempoInicio),
								  iteraciones, reinicios,
								  algoritmo.getMejorCosto(),
								  algoritmo.getMejorFitness());
			
			System.out.println();
			System.out.println("SOLUCION:");
			algoritmo.getMejorSolucion().imprimir();
			
			/*
			 * En caso de que no exista solución válida, imprimimos
			 * dicho mensaje para notificarlo.
			 */
			if (algoritmo.getMejorFitness() < conf.getCantAristas()) {
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
}
