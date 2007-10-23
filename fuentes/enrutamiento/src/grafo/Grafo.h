#ifndef GRAFO_H_
#define GRAFO_H_

#include "Vertice.h"
#include "../ag/Demanda.h"

class Grafo {
private:
	Vertice *tabla;
	int longitud;
public:
	Grafo();
	Grafo(int = 20);
	virtual ~Grafo();
	
	void agregarArista(Arista *);
	Vertice *obtenerVertice(int);
	int cantVertices();
	void generarRutas(Demanda); 
	void imprimir();
};

#endif /*GRAFO_H_*/
