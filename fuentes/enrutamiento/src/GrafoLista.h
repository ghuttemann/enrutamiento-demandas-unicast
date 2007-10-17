#ifndef GRAFOLISTA_H_
#define GRAFOLISTA_H_

#include "Grafo.h"
#include "ListaAdyacencia.h"

class GrafoLista : public Grafo {
private:
    vector<ListaAdyacencia> nodos;
public:
    GrafoLista();
    ~GrafoLista();
    int cantidadNodos() { return nodos.size(); }
    ListaAdyacencia adyacentes(int nodo) { return nodos.at(nodo); }
    void agregarEnlace(int origen, Enlace enc) { ((ListaAdyacencia)nodos.at(origen)).agregarEnlace(enc); }
    void imprimir();
};

#endif /*GRAFOLISTA_H_*/
