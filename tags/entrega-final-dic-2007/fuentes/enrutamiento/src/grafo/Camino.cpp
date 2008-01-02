#include "Camino.h"
#include <iostream>

using namespace std;

Camino::Camino() {
	secuencia = new Arista[INIT_LEN];
	ocupado = -1;
	costo = 0.0;
	longitud = INIT_LEN;
	factor = 0.25;
}

Camino::~Camino() {
	delete[] secuencia;
}

void Camino::setCosto(double cost){
	costo = cost;
}

void Camino::agregarCamino(Arista *valor) {
	ocupado++;
	//secuencia[ocupado] = valor;
	clonar(valor);

	if (ocupado == longitud - 1)
		redimensionar();
}

void Camino::quitarCamino() {
	if (!getVacio()){
		ocupado--;
	}
}

bool Camino::getVacio() {
	return (ocupado == -1);
}

int Camino::getLongitud() {
	return (ocupado + 1);
}

double Camino::getCosto(){
	return costo;
}

int Camino::getPrimero(){
	if (!getVacio())
		return secuencia[0].getOrigen();
	return -1;
}

int Camino::getUltimo(){
	if (!getVacio())
		return secuencia[ocupado].getDestino();
	return -1;
}

void Camino::redimensionar() {
	int nuevoTam;
	nuevoTam = (int) (longitud * (1 + factor));
	Arista temporal[nuevoTam];
	
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
int Camino::getNodo(int i){
	return secuencia[i].getOrigen();
}

void Camino::imprimir(){
	int i = 0;
	cout << " | ";
	while (i <= ocupado){
		cout <<secuencia[i].getOrigen()<<"-";
		i++;
	}
	cout << secuencia[ocupado].getDestino() <<" | "<<endl;
}

void Camino::clonar(Arista *a){
	secuencia[ocupado].setOrigen(a->getOrigen());
	secuencia[ocupado].setDestino(a->getDestino());
	secuencia[ocupado].setCosto(a->getCosto());
	secuencia[ocupado].setCapacidad(a->getCapacidad());
	secuencia[ocupado].setSgte(NULL);
}
