#ifndef GRAFOMATRIZ_H_
#define GRAFOMATRIZ_H_

#include "Grafo.h"
#include "ListaAdyacencia.h"

class GrafoMatriz : public Grafo
{
private:
	Enlace **matriz;
	int tam;
public:
	void agregarEnlace(int nodoOrig, int nodoDest);
	int cantidadNodos() { return tam; }
	ListaAdyacencia adyacentes(int nodo);
	GrafoMatriz();
	virtual ~GrafoMatriz();
};

#endif /*GRAFOMATRIZ_H_*/
