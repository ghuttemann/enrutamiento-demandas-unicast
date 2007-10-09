#ifndef LECTOR_H_
#define LECTOR_H_

using namespace std;
class Lector 
{
	struct enlaces {
		int origen, destino;
		double costo, capacidad;
	};

private:
	struct enlaces enlace; //Solo muestra
	
public:
	Lector();
	~Lector();
	void printenlace(struct enlaces);
	void Lector::leerArchivo(const char*);
};

#endif /*LECTOR_H_*/
