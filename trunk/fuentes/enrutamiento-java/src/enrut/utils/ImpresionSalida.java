/*
 * @(#)ImpresionSalida.java
 */
package enrut.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import au.com.bytecode.opencsv.CSVWriter;

public class ImpresionSalida {
	
	private NumberFormat nf;
	private String id;
	
	public ImpresionSalida(String id) {
		this.id = id;
		
		this.nf = NumberFormat.getInstance(Locale.US);
		this.nf.setMaximumFractionDigits(6);
		this.nf.setMinimumFractionDigits(6);
		this.nf.setRoundingMode(RoundingMode.HALF_UP);
	}
	
	public void traza(long tiempo, int iteraciones, int reinicios,
							double costo, double fitness) {
		
		System.out.print(this.id + "=");
		System.out.print("[");
		System.out.print("Tiempo: " + tiempo + " ms." + "\t");
		System.out.print("Iteracion: " + iteraciones + "\t");
		System.out.print("Fitness: " + nf.format(fitness) + "\t");
		System.out.print("Costo: " + nf.format(costo) + "\t");
		System.out.print("Reinicios: " + reinicios);
		System.out.print("]");
		System.out.println();
	}

	public void escribirHistorico(int id, String path, int reinicios, 
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

	public void registrarDatosHistoricos(List<String[]> historico, 
			long tiempo, double costo) {

		String[] muestra = new String[2];
		muestra[0] = String.valueOf(tiempo); 
		muestra[1] = String.valueOf(costo);

		historico.add(muestra);
	}
	
	public void imprimirTituloPSO() {
		System.out.println("      ..........................................");
		System.out.println("-----| Optimización de Demandas Unicast con PSO |------");
		System.out.println("      ..........................................");
	}
	
	public void imprimirTituloAG() {
		System.out.println("      .........................................");
		System.out.println("-----| Optimización de Demandas Unicast con AG |------");
		System.out.println("      .........................................");
	}
}
