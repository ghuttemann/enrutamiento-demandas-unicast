#ifndef GRAFO_H_
#define GRAFO_H_

#include "Vertice.h"
#include "../ag/Demanda.h"
#include "Camino.h"

class Grafo {
private:
	Vertice *tabla;
	int longitud;
	static const int Rmax = 5; // Nro de caminos maximo por demanda 
public:
	Grafo();
	Grafo(int = 20);
	virtual ~Grafo();
	
	void agregarArista(Arista *);
	Vertice *obtenerVertice(int);
	int cantVertices();
	void generarRutas(int index, int index2, Demanda d[], Camino route[]); 
	void generarRutas(Demanda d[], Camino route[][Rmax]); 
	void reset(Camino);
	void imprimir();
	
	void findroute(Lista *,int from, int to);
	bool match(Lista *, int, int, double&);
	bool find(Lista *, int, Arista *);
private:
	int random(int);
};

#endif /*GRAFO_H_*/
