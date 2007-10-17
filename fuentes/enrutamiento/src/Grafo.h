#ifndef GRAFO_H_
#define GRAFO_H_

#include "Enlace.h"
#include "ListaAdyacencia.h"

class Grafo {
public:
    Grafo();
    virtual ~Grafo();
    virtual int cantidadNodos();
    virtual ListaAdyacencia adyacentes(int);
    virtual void agregarEnlace(int, Enlace);
    virtual void imprimir();
};

#endif /*GRAFO_H_*/
