#include "Grafo.h"
#include "Camino.h"
#include <iostream>
#include <cstdlib>

using namespace std;

Grafo::Grafo(int cantVert) {
	tabla = new Vertice[cantVert];
	longitud = cantVert;
}

Grafo::~Grafo() {
	delete[] tabla;
}

void Grafo::agregarArista(Arista *a) {
	int origen = a->getOrigen();
	tabla[origen].agregarAdyacencia(a);
}

Vertice* Grafo::obtenerVertice(int vertice) {
	return &(tabla[vertice]);
}

int Grafo::cantVertices() {
	return longitud;
}

void Grafo::generarRutas(Demanda d) {
	int origen = d.getOrigen();
	int destino = d.getDestino();
	//double ancho = d.getAncho();
	Lista *lista = obtenerVertice(origen)->getAdyacentes();
	//int cantady = lista->getLongitud();
	Camino route;
	route.agregarVertice(origen);
	obtenerVertice(origen)->setVisitado(true);
	
	lista->iniciarIteracion();
	while(lista->hayMas() && !lista->adestino(destino)) {
		
		Arista *a = lista->aristaActual();
		Vertice *v = obtenerVertice(a->getDestino());
		if (! v->getVisitado()) {
			v->setVisitado(true);
			route.agregarVertice(a->getDestino());
			lista = v->getAdyacentes();
			lista->iniciarIteracion();
		} else {
			lista->siguiente();
		}
	}
	if (!lista->hayMas())
		cout << "No hay camino entre los nodos"<<endl;
	else
		route.imprimir();
}

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

/*/ Determine if there is a route between from and to
// by using depth-first searching.
void Search::findroute(string from, string to) 
{ 
  int dist; 
  FlightInfo f; 
 
  // See if at destination. 
  if(match(from, to, dist)) {
    btStack.push(FlightInfo(from, to, dist)); 
    return; 
  } 
 
  // Try another connection. 
  if(find(from, f)) {
    btStack.push(FlightInfo(from, to, f.distance)); 
    findroute(f.to, to); 
  } 
  else if(!btStack.empty()) { 
    // Backtrack and try another connection. 
    f = btStack.top();
    btStack.pop(); 
    findroute(f.from, f.to); 
  } 
}*/
