#ifndef CONSTRUCTORGRAFO_H_
#define CONSTRUCTORGRAFO_H_

#include "Grafo.h"

class ConstructorGrafo {
public:
	ConstructorGrafo();
	virtual ~ConstructorGrafo();
	
	Grafo *leerGrafo(const char *);
};

#endif /*CONSTRUCTORGRAFO_H_*/
