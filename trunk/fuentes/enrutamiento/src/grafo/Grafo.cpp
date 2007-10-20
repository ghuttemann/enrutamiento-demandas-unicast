#include "Grafo.h"
#include <iostream>

using namespace std;

Grafo::Grafo(int cantVert) {
	tabla = new Vertice[cantVert];
	longitud = cantVert;
}

Grafo::~Grafo() {
}

void Grafo::agregarArista(Arista *a) {
	int origen = a->getOrigen();

	tabla[origen].agregarAdyacencia(a);
}

Vertice* Grafo::obtenerVertice(int vertice) {
	return &(tabla[vertice]);
}

int Grafo::cantVertices() {
	return longitud;
}

void Grafo::imprimir() {
	for (int i=0; i < longitud; i++) {
		Lista *lista = obtenerVertice(i)->getAdyacentes();
		for (lista->iniciarIteracion(); lista->hayMas(); lista->siguiente()) {
			Arista *a = lista->aristaActual();
			cout << "{";
			cout << "Orig:" << a->getOrigen()     << ", ";
			cout << "Dest:" << a->getDestino()    << ", ";
			cout << "Cost:" << a->getCosto()      << ", ";
			cout << "Capa:" << a->getCapacidad() << "}";
			cout << endl;
		}
	}
}
