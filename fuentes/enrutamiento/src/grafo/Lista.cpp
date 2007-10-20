#include "Lista.h"
#include <cstdlib>

Lista::Lista() {
	inicio = NULL;
	actual = NULL;
	longitud = 0;
}

Lista::~Lista() {
	vaciar();
}

void Lista::vaciar() {
	while (inicio != NULL) {
		Arista *aux = inicio;
		inicio = inicio->getSgte();
		delete aux;
	}
}

void Lista::agregar(Arista *arist) {
	arist->setSgte(inicio);
	inicio = arist;
	longitud++;
}

void Lista::iniciarIteracion() {
	actual = inicio;
}

void Lista::siguiente() {
	actual = actual->getSgte();
}

Arista* Lista::aristaActual() {
	return actual;
}

bool Lista::hayMas() {
	return (actual != NULL);
}
