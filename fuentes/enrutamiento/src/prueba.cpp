#include "utils/utils.h"

#include "grafo/ConstructorGrafo.h"

int main(int argc, char *argv[]) {
	ConstructorGrafo cg;
	Grafo *grafo;

	cout << "¡¡¡LECTOR DE ARCHIVO GRAFO!!!" << endl;
	
	if (argc < 2)
		terminar("Falta nombre de archivo");
	
	grafo = cg.leerGrafo(argv[1]);

	grafo->imprimir();
	delete grafo;
	
	return 0;
}
