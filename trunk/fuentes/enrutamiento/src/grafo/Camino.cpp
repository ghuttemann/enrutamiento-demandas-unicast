#include "Camino.h"
#include <iostream>

using namespace std;

Camino::Camino() {
	secuencia = new int[INIT_LEN];
	ocupado = -1;
	longitud = INIT_LEN;
	factor = 0.25;
}

Camino::~Camino() {
	delete[] secuencia;
}

void Camino::agregarVertice(int valor) {
	ocupado++;
	secuencia[ocupado] = valor;

	if (ocupado == longitud - 1)
		redimensionar();
}

void Camino::quitarVertice() {
	if (!getVacio())
		ocupado--;
}

bool Camino::getVacio() {
	return (ocupado == -1);
}

int Camino::getLongitud() {
	return (ocupado + 1);
}

int Camino::getPrimero(){
	if (!getVacio())
		return secuencia[0];
	return -1;
}

int Camino::getUltimo(){
	if (!getVacio())
		return secuencia[ocupado];
	return -1;
}

void Camino::redimensionar() {
	int nuevoTam, *temporal;
	nuevoTam = (int) (longitud * (1 + factor));
	temporal = new int[nuevoTam];
	
	for (int i=0; i < longitud; i++)
		temporal[i] = secuencia[i];

	delete[] secuencia;
	secuencia = temporal;
	
	sgteFactor();
}

void Camino::sgteFactor() {
	if (factor <= 0.5)
		factor *= 2;
}

void Camino::imprimir(){
	int i = 0;
	while (ocupado >= i){
		cout <<"-"<< secuencia[i] <<"-";
		i++;
	}
}
