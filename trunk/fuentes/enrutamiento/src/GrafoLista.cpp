#include "GrafoLista.h"

GrafoLista::GrafoLista() {
}

GrafoLista::~GrafoLista() {
}

void GrafoLista::imprimir() {
    for (int i=0; i < this->cantidadNodos(); i++) {
        ListaAdyacencia a = this->adyacentes(i);
        for (int j=0; j < a.cantidadEnlaces(); j++) {
            Enlace e = a.obtenerEnlace(j);
            cout << "{";
            cout << "Orig:" << e.getOrigen()     << ", ";
            cout << "Dest:" << e.getDestino()    << ", ";
            cout << "Cost:" << e.getCosto()      << ", ";
            cout << "Capa:" << e.getCapaciadad() << "}";
            cout << endl;
        }
    }
}
