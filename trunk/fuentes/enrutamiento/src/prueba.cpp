#include <iostream>
#include "GrafoLista.h"

int main()
{
	struct enlace enc[12] = {{0, 1, 1.5, 10.0},
							 {0, 2, 1.5, 10.0},
							 {0, 3, 1.5, 10.0},
							 {1, 2, 1.5, 10.0},
							 {1, 3, 1.5, 10.0},
							 {2, 3, 1.5, 10.0},
							 {1, 0, 1.5, 10.0},
							 {2, 0, 1.5, 10.0},
							 {3, 0, 1.5, 10.0},
							 {2, 1, 1.5, 10.0},
							 {3, 1, 1.5, 10.0},
							 {3, 2, 1.5, 10.0}};
	
	GrafoLista grafo(enc, 12, 4);
	
	for (int i=0; i < grafo.cantidadNodos(); i++) {
		cout << "Vecinos del nodo " << (i) << endl;
		ListaAdyacencia lista = grafo.adyacentes(i);
		for (int j=0; j < lista.cantidadEnlaces(); j++) {
			cout << "{Nodo:" << lista.obtenerEnlace(j).getDestino();
			cout << ", Costo:" << lista.obtenerEnlace(j).getCosto();
			cout << ", Capacidad:" << lista.obtenerEnlace(j).getCapaciadad() << "}";
			cout << endl;
		}
	}
	
	return 0;
}
