#ifndef ENLACE_H_
#define ENLACE_H_

class Enlace
{
private:
	int destino;
	double costo;
	double capacidad;
public:
	int getDestino() { return destino; }
	double getCosto() { return costo; }
	double getCapaciadad() { return capacidad; }
	Enlace(int destino, double costo, double capacidad) {
		this->destino   = destino;
		this->costo     = costo;
		this->capacidad = capacidad;
	}
};

#endif /*ENLACE_H_*/
