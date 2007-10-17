#ifndef GRAFOLISTA_H_
#define GRAFOLISTA_H_

#include "Grafo.h"
#include "ListaAdyacencia.h"

class GrafoLista : public Grafo
{
private:
	vector<ListaAdyacencia> nodos;
public:
	int cantidadNodos() { return nodos.size(); }
	ListaAdyacencia adyacentes(int nodo) { return nodos.at(nodo); }
	GrafoLista(struct enlace e[], int n, int r);
	~GrafoLista();
};

#endif /*GRAFOLISTA_H_*/
