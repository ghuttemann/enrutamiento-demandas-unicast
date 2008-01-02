#include "ConstructorGrafo.h"
#include "../utils/utils.h"

#include <fstream>

using namespace std;

ConstructorGrafo::ConstructorGrafo() {
}

ConstructorGrafo::~ConstructorGrafo() {
}

Grafo* ConstructorGrafo::leerGrafo(const char *archivo) {
	ifstream stream;
	double aux; // variable auxiliar
	int contador; // contador de lineas leidas
	int cantAristas;
	int cantVertices;
	int origen, destino;
	double costo, capacidad;
	Grafo *grafo;

	// Abrimos el archivo
	stream.open(archivo);
	if (stream.bad()) {
		string str = string("No se puede abrir \"") + archivo + "\"";
		terminar(str);
	}

	stream >> cantVertices; // Se lee cantidad de vertices
	stream >> cantAristas;  // se lee cantidad de aristas
	
	// Construimos el grafo
	grafo = new Grafo(cantVertices); 

	contador = 0;
	while (stream.good()) {
		// Cargamos una linea del archivo
		stream >> origen;
		stream >> destino;
		stream >> capacidad;
		stream >> costo;
		stream >> aux;

		// Creamos la arista y la guardamos en el grafo
		Arista *a = new Arista(origen, destino, costo, capacidad);
		
		grafo->agregarArista(a);

		contador++;
	}
	
	// Cerramos el stream
	stream.close();
	
	if (contador != cantAristas) {
		string str = string("Error en el archivo \"") + archivo + "\"\n";
		str += "La cantidad de vertices leidos no coincide con ";
		str += "la cantidad especificada";
		terminar(str);
	}
	
	return grafo;
}
