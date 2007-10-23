#ifndef LISTA_H_
#define LISTA_H_

#include "Arista.h"

class Lista {
private:
	Arista *inicio;
	Arista *actual;
	int longitud;
public:
	Lista();
	virtual ~Lista();
	
	void agregar(Arista *);
	void iniciarIteracion();
	void siguiente();
	bool hayMas();
	bool adestino(int);
	Arista *aristaActual();
	int getLongitud();
private:
	void vaciar();
};

#endif /*LISTA_H_*/
