/*
 * @(#)ConstructorCaminos.java
 */
package enrut.grafo;

import enrut.ag.Demanda;
import enrut.utils.Lector;

public class ConstructorCaminos {
	
	/**
	 * Lee los caminos para una demanda dada.
	 * 
	 * @param demandas La lista de demandas
	 * @param max Máxima cantidad de caminos a recuperar
	 * @param path Ruta donde se encuentrar los archivos a leer
	 */
	public void leerCaminos(Demanda[] demandas, int max, String path) {
		Lector lector = new Lector();
		
		for (int i=0; i < demandas.length; i++) {
			// Origen de la demanda i
			int origen  = demandas[i].getOrigen();
			
			// Destino de la demanda i
			int destino = demandas[i].getDestino();
			
			// Nombre del archivo a leer
			String archivo = "caminos-" + origen + "-" + destino + ".txt";
			
			// Abrimos el archivo
			lector.abrir(path + archivo);
			
			// Leemos el origen y destino del archivo
			int origEnArch=0, destEnArch=0;
			try {
				origEnArch = Integer.parseInt(lector.leerLinea());
				destEnArch = Integer.parseInt(lector.leerLinea());
			}
			catch (NumberFormatException e) {
				System.out.println("Error de conversión numérica");
				e.printStackTrace();
				System.exit(0);
			}
			
			// Verificamos que origen y destino coincidan
			if (origen != origEnArch || destino != destEnArch) {
				throw new Error("Origen y Destino no coinciden");
			}
			
			// Grupo de caminos a leer
			GrupoCaminos grupo = new GrupoCaminos();
			
			// Leemos el grupo de caminos
			leerGrupoCaminos(grupo, lector);
			
			// Guardamos el grupo de caminos para la demanda
			demandas[i].setCaminos(grupo);
			
			// Cerramos el lector
			lector.cerrar();
		}
		
		/*
		 * Ya tenemos todos los caminos para cada
		 * demanda.
		 * En caso de que "max" sea mayor a cero,
		 * conservamos solo dicha cantidad (como 
		 * máximo) de caminos para cada demanda.
		 */
		if (max > 0)
		for (int i=0; i < demandas.length; i++)
			demandas[i].getCaminos().conservar(max);
	}
	
	/**
	 * Lee los caminos para una determinada demanda y
	 * los guarda en un objeto del tipo GrupoCaminos.
	 * 
	 * @param grupo El grupo de caminos.
	 * @param lector El manejador del archivo del cual leer.
	 */
	private void leerGrupoCaminos(GrupoCaminos grupo, Lector lector) {
		// Comenzamos a leer los caminos
		String linea = lector.leerLinea();
		while (linea != null) {
			String[] partes = linea.split("-");
			Camino cam = new Camino();
			
			// Leemos cada arista del camino
			for (int i=3; i < partes.length-3; i++) {
				String[] arista = partes[i].split(":");
				
				// Obtenemos los datos de la arista
				int origen=0, destino=0;
				double capacidad=0, costo=0;
				try {
					origen    = Integer.parseInt(arista[0]);
					destino   = Integer.parseInt(arista[1]);
					capacidad = Double.parseDouble(arista[2]);
					costo     = Double.parseDouble(arista[3]);
				}
				catch (NumberFormatException e) {
					System.out.println("Error de conversión numérica");
					e.printStackTrace();
					System.exit(0);
				}
				
				// Agregamos la arista al camino
				Arista a = new Arista(origen, destino, capacidad, costo);
				cam.agregarArista(a);
			}
			
			// Agregamos el camino al grupo de caminos
			grupo.agregarCamino(cam);
			
			// Leemos el siguiente camino
			linea = lector.leerLinea();
		}
	}
}
