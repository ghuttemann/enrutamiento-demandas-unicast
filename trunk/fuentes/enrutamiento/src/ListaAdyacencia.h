#ifndef LISTAADYACENCIA_H_
#define LISTAADYACENCIA_H_

using namespace std;

#include "Enlace.h"
#include <vector>

class ListaAdyacencia
{
private:
	vector<Enlace> enlaces;
public:
	void agregarEnlace(Enlace enc) { enlaces.push_back(enc); }
	int cantidadEnlaces() { return enlaces.size(); }
	Enlace obtenerEnlace(int pos) { return enlaces.at(pos); }
	ListaAdyacencia() {};
	~ListaAdyacencia() { enlaces.clear(); };
};

#endif /*LISTAADYACENCIA_H_*/
