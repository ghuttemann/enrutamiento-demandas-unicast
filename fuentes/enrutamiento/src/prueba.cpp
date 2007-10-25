#include "utils/utils.h"

#include "grafo/ConstructorGrafo.h"
#include "grafo/Camino.h"
#include "ag/Demanda.h"

int main(int argc, char *argv[]) {
	//--------------| Lectura del Grafo |------------------
	ConstructorGrafo cg;
	Grafo *grafo;
	cout << "¡¡¡LECTOR DE ARCHIVO GRAFO!!!" << endl;
	
	if (argc < 2)
		terminar("Falta nombre de archivo");
	
	grafo = cg.leerGrafo(argv[1]);
	grafo->imprimir();
	//--------------| Grafo LEIDO |------------------------
	
	//--------------| Calculo de Rutas|--------------------
	Demanda d[3]; // N = 3
	d[0].setOrigen(1);
	d[0].setDestino(9);
	d[0].setAncho(1.1);
	d[1].setOrigen(3);
	d[1].setDestino(7);
	d[1].setAncho(1.5);
	d[2].setOrigen(11);
	d[2].setDestino(9);
	d[2].setAncho(2.5);
	
	Camino rutas [5]; //R = 5;
	
	grafo->generarRutas(d, rutas);
	//--------------| Rutas CALCULADAS |-------------------
	
	for (int i=0; i<5; i++){
		cout <<"------------------RESULTADO-----------------------"<<endl;
		rutas[i].imprimir();
		cout << endl<< (rutas[i].getPrimero())<<"·"<<rutas[i].getUltimo()<<endl;
		cout << rutas[i].getCosto()<<endl;
		cout <<"--------------------------------------------------"<<endl;
	}
	
	//--------------| Calculo de AG |----------------------
	
	//--------------| AG Terminado (FIN) |-----------------
	delete grafo;
	cout <<endl<< "¡¡¡END OF PROGRAM!!!" << endl;
	return 0;
}
