package enrut.utils;

import java.io.File;

/**
 * Clase para formatear directorios
 */
public class FormatDir {
	
	/**
	 * Si el path no termina con barra, le agrega.
	 * Funciona para Unix y/o Windows.
	 */
	public static String format(String path) {
		String barra = File.separator;
		
		if ( path.endsWith(barra) )
			return path;
		
		return (path + barra);
	}
}
