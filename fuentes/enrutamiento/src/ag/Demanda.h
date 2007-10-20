#ifndef DEMANDA_H_
#define DEMANDA_H_

class Demanda {
private:
	int origen, destino;
	double ancho; // ancho de banda requerido
public:
	Demanda(int = -1, int = -1, double = -1.0);
	virtual ~Demanda();
	
	// setters
	void setOrigen(int);
	void setDestino(int);
	void setAncho(double);
	
	// getters
	int getOrigen();
	int getDestino();
	double getAncho();
};

#endif /*DEMANDA_H_*/
