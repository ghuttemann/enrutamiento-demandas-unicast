#include "Arista.h"

Arista::Arista(int orig, int dest, double cost, double capa, Arista *s) {
	origen    = orig;
	destino   = dest;
	costo     = cost;
	capacidad = capa;
	sgte      = s;
}

Arista::~Arista() {
}

bool Arista::igual(const Arista& a) {
	if (origen == a.origen && destino == a.destino)
		return true;
	else
		return false;
}

bool Arista::operator <(const Arista& a) {
	return !igual(a);
}

void Arista::setOrigen(int orig) {
	origen = orig;
}

void Arista::setDestino(int dest) {
	destino = dest;
}

void Arista::setCapacidad(double capa) {
	capacidad = capa;
}

void Arista::setCosto(double cost) {
	costo = cost;
}

void Arista::setSgte(Arista *s) {
	sgte = s;
}

int Arista::getOrigen() {
	return origen;
}

int Arista::getDestino() {
	return destino;
}

double Arista::getCapacidad() {
	return capacidad;
}

double Arista::getCosto() {
	return costo;
}

Arista* Arista::getSgte() {
	return sgte;
}
