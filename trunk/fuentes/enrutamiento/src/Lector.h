#ifndef LECTOR_H_
#define LECTOR_H_

#include "Grafo.h"

class Lector {
public:
    Lector() {};
    ~Lector() {};
    void leerArchivo(const char*, Grafo);
};

#endif /*LECTOR_H_*/
