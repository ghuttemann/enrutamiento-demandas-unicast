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
import enrut.utils.CSVWriter;

/**
 * Clase que se encarga de toda la impresión de 
 * la salida de los algoritmos GA y PSO.
 */
public class ImpresionSalida {
	/*
	 * Formateador de numeros de punto flotante
	 */
	private NumberFormat nf;
	
	/*
	 * Id del algoritmo que lo utiliza
	 */
	private String id;
	
	/**
	 * Crea un nuevo objeto de esta clase.
	 */
	public ImpresionSalida(String id) {
		this.id = id;
		
		// Separador decimal = punto
		this.nf = NumberFormat.getInstance(Locale.US);
		
		// Digitos decimales = seis
		this.nf.setMaximumFractionDigits(6);
		this.nf.setMinimumFractionDigits(6);
		
		// Se redondean los dígitos decimales
		this.nf.setRoundingMode(RoundingMode.HALF_UP);
	}
	
	/**
	 * Imprime el estado de ejecución de un algoritmo
	 * 
	 * @param tiempo Tiempo transcurrido
	 * @param iteraciones Iteración del algoritmo
	 * @param reinicios Cantidad de reinicios de la población/enjambre
	 * @param costo Mejor costo alcanzado hasta el momento
	 * @param fitness Mejor fitness alcanzado hasta el momento
	 */
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

	/**
	 * Imprime un archivo CSV con los datos de cada ejecución, para que
	 * luego pueda ser abierto en Excel o Calc para sacar estadísticas y
	 * realizar gráficos.
	 * 
	 * @param id Ejecución del algoritmo
	 * @param path Directorio (terminado en barra) donde se tiene que imprimir el archivo
	 * @param historico El listado de toda la salida a escribir
	 */
	public void escribirHistorico(int id, String path, List<String[]> historico) {

		CSVWriter writer = null;
		try {
			// Creamos el archivo
			path = path + "historico" + id + ".csv";
			writer = new CSVWriter(new FileWriter(path), ';');
		} 
		catch (IOException e) {
			System.out.println("Error de escritura del archivo historico");
			e.printStackTrace();
			System.exit(0);
		}

		// Escribimos la salida
		writer.writeAll(historico);

		try {
			// Cerramos el archivo
			writer.close();
		}
		catch (IOException e) {
			System.out.println("Error para cerrar archivo historico");
			e.printStackTrace();
			System.exit(0);
		}
	}

	/**
	 * Agrega un registro (muestra) al histórico de ejecución.
	 * 
	 * @param historico El listado de toda la salida a escribir
	 * @param tiempo Tiempo actual de ejecución
	 * @param costo Mejor costo alcanzado hasta el momento
	 * @param fitness Mejor fitness alcanzado hasta el momento
	 */
	public void registrarDatosHistoricos(List<String[]> historico, 
			long tiempo, double costo, double fitness) {

		// Construimos el registro (muestra)
		String[] muestra = new String[3];
		muestra[0] = String.valueOf(tiempo); 
		muestra[1] = String.valueOf(costo).replace('.', ',');
		muestra[2] = String.valueOf(fitness).replace('.', ',');

		// Imprimimos el registro (muestra)
		historico.add(muestra);
	}
	
	/**
	 * Imprime el titulo de la ejecución.
	 */
	public void imprimirTitulo(String id) {
		if (id.equalsIgnoreCase("AlgoritmoAG")) {
			System.out.println("      .........................................");
			System.out.println("-----| Optimización de Demandas Unicast con AG |------");
			System.out.println("      .........................................");
		}
		else if (id.equalsIgnoreCase("AlgoritmoPSO")) {
			System.out.println("      ..........................................");
			System.out.println("-----| Optimización de Demandas Unicast con PSO |------");
			System.out.println("      ..........................................");
		}
	}
}
