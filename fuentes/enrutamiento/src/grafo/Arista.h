#ifndef ARISTA_H_
#define ARISTA_H_

#include <cstdlib>

class Arista {
private:
	int origen, destino;
	double costo, capacidad;
	Arista *sgte;
	
public:
	Arista(int = -1, int = -1, double = -1.0, double = -1.0, Arista* = NULL);
	virtual ~Arista();
	bool igual(const Arista&);
	
	// setters
	void setOrigen(int);
	void setDestino(int);
	void setCosto(double);
	void setCapacidad(double);
	void setSgte(Arista *);
	
	// getters
	int getOrigen();
	int getDestino();
	double getCosto();
	double getCapacidad();
	Arista *getSgte();
};

#endif /*ARISTA_H_*/
