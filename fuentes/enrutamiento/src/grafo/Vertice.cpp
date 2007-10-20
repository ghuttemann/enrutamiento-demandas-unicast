#include "Vertice.h"

Vertice::Vertice() {
}

Vertice::~Vertice() {
}

void Vertice::agregarAdyacencia(Arista *arist) {
	adyacentes.agregar(arist);
}

Lista* Vertice::getAdyacentes() {
	return &adyacentes;
}

bool Vertice::getVisitado() {
	return visitado;
}

void Vertice::setVisitado(bool visit) {
	visitado = visit;
}
