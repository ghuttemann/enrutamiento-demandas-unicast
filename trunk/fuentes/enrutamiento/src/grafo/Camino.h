#ifndef CAMINO_H_
#define CAMINO_H_

class Camino {
private:
	int *secuencia;
	int longitud;
	int ocupado;
	double factor;
	static const int INIT_LEN = 20;
public:
	Camino();
	virtual ~Camino();
	
	void agregarVertice(int);
	void quitarVertice();
	bool getVacio();
	int getLongitud();
private:
	void redimensionar();
	void sgteFactor();
};

#endif /*CAMINO_H_*/
