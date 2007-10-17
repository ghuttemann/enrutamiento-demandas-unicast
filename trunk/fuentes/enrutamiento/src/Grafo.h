#ifndef GRAFO_H_
#define GRAFO_H_

#include "Enlace.h"

class Grafo
{
public:
	Grafo();
	virtual ~Grafo();
};

#endif /*GRAFO_H_*/

struct enlace {
	int origen, destino;
	double costo, capacidad;
};
