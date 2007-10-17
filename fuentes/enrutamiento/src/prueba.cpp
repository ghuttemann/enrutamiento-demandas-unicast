#include <iostream>
#include "GrafoLista.h"
#include "Lector.h"

int main() {
	GrafoLista grafo;
	Lector lector;

	cout << "¡¡¡LECTOR DE ARCHIVO GRAFO!!!" << endl;
	lector.leerArchivo("grafo.m", grafo);

	grafo.imprimir();

	return 0;
}
