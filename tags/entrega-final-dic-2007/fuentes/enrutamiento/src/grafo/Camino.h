#ifndef CAMINO_H_
#define CAMINO_H_

#include "Arista.h"

class Camino {
private:
	Arista *secuencia;
	double costo;
	int longitud;
	int ocupado;
	double factor;
	static const int INIT_LEN = 20;
public:
	Camino();
	virtual ~Camino();
	
	void setCosto(double);
	void agregarCamino(Arista*);
	void quitarCamino();
	bool getVacio();
	int getLongitud();
	double getCosto();
	int getNodo(int i);
	int getPrimero();
	int getUltimo();
	void imprimir();
private:
	void clonar(Arista *);
	void redimensionar();
	void sgteFactor();
};

#endif /*CAMINO_H_*/
