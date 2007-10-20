#include "Demanda.h"

Demanda::Demanda(int orig, int dest, double anch) {
	setOrigen(orig);
	setDestino(dest);
	setAncho(anch);
}

Demanda::~Demanda() {
}

void Demanda::setOrigen(int orig) {
	origen = orig;
}

void Demanda::setDestino(int dest) {
	destino = dest;
}

void Demanda::setAncho(double anch) {
	ancho = anch;
}


int Demanda::getOrigen() {
	return origen;
}

int Demanda::getDestino() {
	return destino;
}

double Demanda::getAncho() {
	return ancho;
}
