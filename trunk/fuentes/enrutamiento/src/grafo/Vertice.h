#ifndef VERTICE_H_
#define VERTICE_H_

#include "Lista.h"

class Vertice {
private:
	Lista adyacentes;
	bool visitado;
public:
	Vertice();
	virtual ~Vertice();
	
	void agregarAdyacencia(Arista *);
	Lista *getAdyacentes();
	bool getVisitado();
	void setVisitado(bool);
};

#endif /*VERTICE_H_*/
