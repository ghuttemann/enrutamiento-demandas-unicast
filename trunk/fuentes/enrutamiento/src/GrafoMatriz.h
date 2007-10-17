#ifndef GRAFOMATRIZ_H_
#define GRAFOMATRIZ_H_

#include "Grafo.h"
#include "ListaAdyacencia.h"

class GrafoMatriz : public Grafo {
private:
    Enlace **matriz;
    int tam;
public:
    GrafoMatriz();
    ~GrafoMatriz();
    int cantidadNodos() { return tam; }
    ListaAdyacencia adyacentes(int nodo);
    void agregarEnlace(int origen, Enlace enc);
};

#endif /*GRAFOMATRIZ_H_*/
