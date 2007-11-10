/*
 * @(#)PrincipalAG.java
 */
package enrut.test;

import enrut.ag.Cromosoma;
import enrut.ag.Demanda;
import enrut.ag.Poblacion;
import enrut.ag.oper.OperadorCruce;
import enrut.ag.oper.OperadorMutacion;
import enrut.ag.oper.OperadorSeleccion;
import enrut.ag.oper.impl.cruce.CruceUniforme;
import enrut.ag.oper.impl.mutacion.MutacionUnGen;
import enrut.ag.oper.impl.seleccion.TorneoBinario;
import enrut.grafo.ConstructorCaminos;
import enrut.utils.Config;

public class PrincipalAG {
	
	private PrincipalAG() {
		
	}
	
	/*
	 * Prueba principal del algoritmo genetico 
	 * para la optimización de demandas unicast.
	 */
	public static void main(String[] args) {
		// -----------------------| Variables |--------------------------
		ConstructorCaminos builder = new ConstructorCaminos();
		Poblacion poblacion;
		OperadorSeleccion seleccion;
		OperadorCruce cruce;
		OperadorMutacion mutacion;
		Config conf;
		
		
		imprimirTitulo();
		inicializarOperadores(seleccion, cruce, mutacion);
		cargarConfiguracion(conf);
			
		// -----------------------| Cargar Rutas |-----------------------
		if (args.length != 1)
			throw new Error("Falta nombre de Carpeta de Rutas");
		
		builder.leerCaminos(d, 5, args[0]);
		// -----------------------| Algoritmo Genético |-----------------
		pob.setOperadorSeleccion(oSeleccion);
		pob.setOperadorCruce(oCruce);
		pob.setOperadorMutacion(oMutacion);
		Cromosoma [] selectos;
		
		selectos = pob.seleccionar();
		pob.cruzar(selectos);
		pob.mutar();
		
		// -----------------------| Finalización |-----------------------

		System.out.println("¡¡¡END OF PROGRAM!!!");
	}
	
	private static void imprimirTitulo() {
		System.out.println("      ..................................");
		System.out.println("-----| Optimización de Demandas Unicast |------");
		System.out.println("      ..................................");
	}
	
	private static void inicializarOperadores(OperadorSeleccion seleccion, 
			OperadorCruce cruce, OperadorMutacion mutacion) {
		seleccion = new TorneoBinario();
		cruce = new CruceUniforme();
		mutacion = new MutacionUnGen();
	}
	
	private static void cargarConfiguracion(Config conf) {
				 
	}
}
