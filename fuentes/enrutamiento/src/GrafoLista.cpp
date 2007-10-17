#include "GrafoLista.h"

GrafoLista::GrafoLista(struct enlace e[], int n, int r)
{
	nodos = vector<ListaAdyacencia>(r);
	
	for (int i = 0; i < n; i++) {
		struct enlace aux = e[i];
		int pos = aux.origen;
		nodos[pos].agregarEnlace(Enlace(aux.destino, aux.costo, aux.capacidad)); 
	}
}

GrafoLista::~GrafoLista()
{
}
