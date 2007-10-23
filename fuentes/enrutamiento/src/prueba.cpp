#include "utils/utils.h"

#include "grafo/ConstructorGrafo.h"
#include "ag/Demanda.h"

int main(int argc, char *argv[]) {
	ConstructorGrafo cg;
	Grafo *grafo;
	Demanda d;
	d.setOrigen(1);
	d.setDestino(5);
	d.setAncho(1.1);
	cout << "¡¡¡LECTOR DE ARCHIVO GRAFO!!!" << endl;
	
	if (argc < 2)
		terminar("Falta nombre de archivo");
	
	grafo = cg.leerGrafo(argv[1]);

	grafo->imprimir();
	grafo->generarRutas(d);
	delete grafo;
	
	return 0;
}
