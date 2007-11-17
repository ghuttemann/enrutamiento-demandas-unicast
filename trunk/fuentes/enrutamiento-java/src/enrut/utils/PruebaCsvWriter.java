package enrut.utils;

import java.io.FileWriter;
import java.io.IOException;
import au.com.bytecode.opencsv.CSVWriter;;

public class PruebaCsvWriter {
	public static void main(String[] args) {
		
		/*
		 * Archivo de salida
		 */
		String archivo = ".\\data\\prueba.csv";
		
		
		/*
		 * Creamos el escritor CSV
		 */
		CSVWriter writer;
		try {
			writer = new CSVWriter(new FileWriter(archivo), ',', '"');
			
			/*
			 * Filas a escribir en el archivo
			 */
			String[][] filas = {{"1089", "josé", "23/12/1980"},
								{"1203", "pedro", "13/02/1987"},
								{"8321", "maría", "13/11/1982"}};
			
			/*
			 * Escribimos las filas en el archivo
			 */
			for (int i=0; i < filas.length; i++)
				writer.writeNext(filas[i]);
					
			
			/*
			 * Cerramos el escritor
			 */
			writer.flush();
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
}
