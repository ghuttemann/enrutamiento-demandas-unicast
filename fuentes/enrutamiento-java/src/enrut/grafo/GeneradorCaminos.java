/*
 * @(#)GeneradorCaminos.java
 */
package enrut.grafo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

/**
 * Esta clase se encarga de generar todos los caminos
 * posibles dentro del grafo. Esto es, todos los caminos
 * entre cada par de vertices.
 */
public class GeneradorCaminos {
	
	private GeneradorCaminos() {
	}
	
	public static void generar(Grafo grafo, String path) {
		int numVert = grafo.cantVertices();
		GrupoCaminos[][] caminos = new GrupoCaminos[numVert][numVert];
		
		// Inicializamos la matriz
		System.out.println("Inicializando matriz...");
		for (int i=0; i < numVert; i++)
		for (int j=0; j < numVert; j++)
			if (i != j)
			caminos[i][j] = new GrupoCaminos();
		
		// Generamos todos los caminos del grafo
		generar(grafo, caminos);
		
		/*
		 * Una vez que hemos generado todos los caminos
		 * del grafo, procedemos a guardarlos en archivos.
		 * Los nombres de los archivos generados seguiran 
		 * el patron: "caminos-[a]-[b].txt", donde [a] y 
		 * [b] son numeros enteros indicando el origen y 
		 * destino, respectivamente, de los caminos 
		 * contenidos en el archivo.
		 */		
		for (int i=0; i < numVert; i++) {
			for (int j=0; j < numVert; j++) {
				if (i != j) {
					String archivo = path + "caminos-"+i+"-"+j+".txt";
					try {
						PrintWriter writer = new PrintWriter(new FileWriter(archivo));
						GrupoCaminos grupo = caminos[i][j];
						
						writer.println(i);
						writer.println(j);
						
						for (int k=0; k < grupo.getCantCaminos(); k++) {
							Camino cam = grupo.getCamino(k);
							writer.println(cam);
						}
						
						writer.flush();
						writer.close();
					} 
					catch (IOException e) {
						System.out.println("Error escribiendo en \"" + archivo + "\"");
						e.printStackTrace();
						System.exit(0);
					}
				}
			}
		}
	}
	
	/**
	 * Genera todos los caminos del grafo
	 * 
	 * @param grafo El grafo del cual deben generarse los caminos
	 * @param caminos Matriz en la que se guardan los caminos
	 */
	private static void generar(Grafo grafo, GrupoCaminos[][] caminos) {
		Camino camino = new Camino();
		for (int i=0; i < grafo.cantVertices(); i++) {
			System.out.printf("Generando caminos vertice %d...\n", i);
			generarGrupo(grafo, i, i, camino, caminos);
			camino.vaciar();
		}
	}
	
	/**
	 * Genera todos los caminos para un vertice
	 * en particular
	 * 
	 * @param grafo El grafo del cual deben generarse los caminos
	 * @param origen El vertice origen para los caminos a generarse
	 * @param actual El vertice actual en el recorrido
	 * @param camino El camino contruido hasta el vertice actual
	 * @param caminos Todos los caminos del grafo
	 */
	private static void generarGrupo(Grafo grafo, final int origen, int actual, 
			Camino camino, GrupoCaminos[][] caminos) {
		
		/*
		 * Si no estamos en el vertice actual, entonces
		 * guardamos el camino construido hasta el momento
		 * al grupo de caminos entre el par de vertices
		 * origen y actual.
		 */
		if (origen != actual) {
			Camino cam = camino.clonar();
			caminos[origen][actual].agregarCamino(cam);
		}
		
		// Obtenemos el vertice en cuestion
		Vertice vert = grafo.getVertice(actual);
		
		// Marcamos al vertice como visitado
		vert.setVisitado(true);
		
		// Obtenemos un iterador sobre las aristas del vertice
		Iterator<Arista> it = vert.getAdyacentes().iterator();
		
		// Comenzamos el recorrido
		while (it.hasNext()) {
			Arista a = it.next();
			int destino = a.getDestino();
			Vertice v = grafo.getVertice(destino);
			
			if (!v.getVisitado()) {
				camino.agregarArista(a);
				generarGrupo(grafo, origen, destino, camino, caminos);
				camino.quitarArista();
			}
		}
		
		vert.setVisitado(false);
	}
}
