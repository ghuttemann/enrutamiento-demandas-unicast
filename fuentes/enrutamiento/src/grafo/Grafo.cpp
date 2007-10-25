#include "Grafo.h"

#include <iostream>
#include <cstdlib>
#include <stack>
#include <ctime>
//#include <stdlib.h>
using namespace std;

/**
 * Constructor
 */
Grafo::Grafo(int cantVert) {
	tabla = new Vertice[cantVert];
	longitud = cantVert;
}
/**
 * Destructor
 */
Grafo::~Grafo() {
	delete[] tabla;
}

/**
 * Función para agregar una Arista al grafo.
 */
void Grafo::agregarArista(Arista *a) {
	int origen = a->getOrigen();
	tabla[origen].agregarAdyacencia(a);
}

/**
 * Función para obtener un vertice del grafo dado el numero
 * correspondiente al vertice en la tabla del grafo.
 */
Vertice* Grafo::obtenerVertice(int vertice) {
	return &(tabla[vertice]);
}

/**
 * Función para obtener la cantida de vertices en el Grafo.
 */
int Grafo::cantVertices() {
	return longitud;
}
/**
 * Función que genera las R rutas (o menos si no hay suficientes) para
 * las correspondientes N demandas.
 * @param index1 indica el indice de la a leer demanda
 * @param index2 indica el indice de la ruta a ser insertada
 * @param d[] demandas de conexiones
 * @param Camino* Caminos encontrados (R*N caminos)
 */
void Grafo::generarRutas(int index1, int index2, Demanda d[], Camino route[][Rmax]) {
	
	int origen = d[index1].getOrigen(); // Origen
	int destino = d[index1].getDestino(); // Destino
	cout<<"Origen:"<<origen<<" Destino:"<<destino<<endl<<endl;
	
	Lista *lista = obtenerVertice(origen)->getAdyacentes(); //adyacentes
	int cantady = lista->getLongitud();
	int sigte = random(cantady); // id del camino a seguir
	cout<<"Cantady = "<<cantady<<"; sigte = "<<sigte<<endl;
	
	lista->iniciarIteracion();
	obtenerVertice(origen)->setVisitado(true);
	
	// sigte esta limitado por el tamaño de la lista
	for (int i = 0; i<sigte; i++)
		lista->siguiente();
	
	int contador=0;
	while(!lista->endestino(destino) && contador<10*cantady) {
		
		Arista *a = lista->aristaActual(); // arista actual
		cout << "Estoy en:"<<a->getOrigen()<<endl;
		Vertice *v = obtenerVertice(a->getDestino()); // vertice subdestino
	
		if (! v->getVisitado()) {
			cout<<endl<<"VISITADO"<<endl;
			// se pone como visitado
			v->setVisitado(true);
			// se agrega el costo del camino
			route[index1][index2].setCosto(route[index1][index2].getCosto() + a->getCosto());
			// se agrega el camino
			route[index1][index2].agregarCamino(a);
			// se eligen los siguentes adyacentes
			cout << "Me fui a:"<<a->getDestino()<<endl;
			lista = v->getAdyacentes();
			cantady = lista->getLongitud();
			contador = 0;
		} else {
			cout<<endl<<"NO VISITADO"<<endl;
			contador++;
		}
				
		sigte = random(cantady); // id del camino a seguir
		cout<<" Cantady = "<<cantady<<"; sigte = "<<sigte<<endl;
		// Selección de una arista
		lista->iniciarIteracion();
		for (int i = 0; i<sigte ; i++)
			lista->siguiente();
	}
	
	
	if (route[index1][index2].getPrimero()!=origen || route[index1][index2].getUltimo()!=destino) {
		cout << "No hay camino entre los nodos"<<endl;
		cout <<"De:"<<origen<<" A:"<<destino<<endl;
	} else {
		route[index1][index2].imprimir();
	}
}
/**
 * Funcion principal para generar todas las rutas 
 */
void Grafo::generarRutas(Demanda d[], Camino route[][Rmax]) {
	srand ( time(NULL) );
	// i indica el indice de la a leer demanda
	// j indica el indice de la ruta a ser insertada
	for (int i = 0; i<3 ; i++){
		for (int j = 0; j<Rmax; j++){
			generarRutas(i,j,d,route);
			reset(route[i][j]);
		}
	}
}

void Grafo::reset(Camino route){
	for (int i = 0; i<Rmax; i++){
		int n = route.getNodo(i);
		tabla[n].setVisitado(false);
	}
	tabla[route.getUltimo()].setVisitado(false);
}


int Grafo::random(int cant){
	int retorno = (double(rand())/RAND_MAX)*cant;
	return retorno;
}

/**
 * Función para imprimir todos los nodos del grafo
 */
void Grafo::imprimir() {
	for (int i=0; i < longitud; i++) {
		Lista *lista = obtenerVertice(i)->getAdyacentes();
		for (lista->iniciarIteracion(); lista->hayMas(); lista->siguiente()) {
			Arista *a = lista->aristaActual();
			cout << "{";
			cout << "Orig:" << a->getOrigen()    << ", ";
			cout << "Dest:" << a->getDestino()   << ", ";
			cout << "Cost:" << a->getCosto()     << ", ";
			cout << "Capa:" << a->getCapacidad() << "}";
			cout << endl;
		}
	}
}




/*
 * Determine if there is a route between from and to
 * by using depth-first searching.
 */
void Grafo::findroute(Lista * list, int from, int to) {
	double cost;
	//Vertice *v;
	Arista *a;
	Demanda c;
	stack<Demanda> btStack;

	// See if at destination. 
	if (match(list, from, to, cost)) {
		btStack.push(Demanda(from, to, cost)); 
		return;
	}

	//Try another connection. 
	if (find(list, from, a)) {
		btStack.push(Demanda(from,to,a->getCosto()));
		findroute(obtenerVertice(a->getDestino())->getAdyacentes(), 
				a->getDestino(), to);
	} else if (!btStack.empty()) {
		//Backtrack and try another connection.
		c = btStack.top();
		btStack.pop();
		findroute(list, c.getOrigen(), c.getDestino());
	}
}

/**
 * Función que busca un camino de from a to en la lista de artistas
 * Retorna verdadero si existe algún camino, sino, retorna falso.
 */
bool Grafo::match(Lista *lista, int from, int to, double &cost) 
{ 
	for (lista->iniciarIteracion(); lista->hayMas(); lista->siguiente()) {
		Arista *a = lista->aristaActual();
		Vertice *v = obtenerVertice(a->getDestino());
		if (a->getOrigen()==from && a->getDestino()==to && !v->getVisitado()){
			v->setVisitado(true);
			cost = a->getCosto();
			return true;
		}
	}
	
	return false; // no encontrado  
} 
   
// Given from, find any connection. 
// Return true if a connection is found,
// and false otherwise.
bool Grafo::find(Lista *lista, int from, Arista *arist) 
{ 
	for (lista->iniciarIteracion(); lista->hayMas(); lista->siguiente()) {
		Arista *a = lista->aristaActual();
		Vertice *v = obtenerVertice(a->getDestino());
		if (a->getOrigen()==from && !v->getVisitado()){
			v->setVisitado(true);
			arist= a; // OJO Controlar!!!
			return true;
		}
	}

  return false; 
}
