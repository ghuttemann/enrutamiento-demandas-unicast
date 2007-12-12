/*
 * @(#)ImpresionSalida.java
 */
package enrut.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import au.com.bytecode.opencsv.CSVWriter;

public class ImpresionSalida {
	
	public static void traza(long tiempo, int iteraciones, int reinicios,
							double costo, double fitness) {
		System.out.print("[");
		System.out.print("Tiempo: " + tiempo + " ms." + "\t");
		System.out.print("Iteracion: " + iteraciones + "\t");
		System.out.print("Fitness: " + fitness + "\t");
		System.out.print("Costo: " + costo + "\t");
		System.out.print("Reinicios: " + reinicios);
		System.out.print("]");
		System.out.println();
	}

	public static void escribirHistorico(int id, String path, int reinicios, 
			List<String[]> historico) {

		CSVWriter writer = null;
		try {
			path = path + "historico" + id + ".csv";
			writer = new CSVWriter(new FileWriter(path), ';');
		} 
		catch (IOException e) {
			System.out.println("Error de escritura del archivo historico");
			e.printStackTrace();
			System.exit(0);
		}

		writer.writeAll(historico);

		try {
			writer.close();
		}
		catch (IOException e) {
			System.out.println("Error para cerrar archivo historico");
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static void registrarDatosHistoricos(List<String[]> historico, 
			long tiempo, double costo) {

		String[] muestra = new String[2];
		muestra[0] = String.valueOf(tiempo); 
		muestra[1] = String.valueOf(costo);

		historico.add(muestra);
	}
	
	public static void imprimirTituloPSO() {
		System.out.println("      ..........................................");
		System.out.println("-----| Optimización de Demandas Unicast con PSO |------");
		System.out.println("      ..........................................");
	}
	
	public static void imprimirTituloAG() {
		System.out.println("      .........................................");
		System.out.println("-----| Optimización de Demandas Unicast con AG |------");
		System.out.println("      .........................................");
	}
}
