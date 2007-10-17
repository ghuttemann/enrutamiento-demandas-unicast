//============================================================================
// Name        : Enrutamiento.cpp
// Author      : Marcelo Rodas
// Version     : 1.0
// Copyright   : copyright
// Description : Lector del archivo que representa un grafo, Ansi-style
//============================================================================
#include "Lector.h"
#include "Enlace.h"
#include <string>
#include <cctype>
#include <fstream>
#include <iostream>

void Lector::leerArchivo(const char *filename, Grafo grafo) {
    ifstream source;
    string linea;
    int cantEnlaces; // contador de lineas leidas
    int cantNodos;
    int cantVertices;
    int origen, destino;
    double costo, capacidad;
    
    // Abrimos el archivo
    source.open(filename);
    if (!source) {
        std::cerr << "No se puede abrir "<< filename << "!" << endl;
        exit(-1);
    }
    
    source >> cantNodos;    // Se lee cantidad de nodos
    source >> cantVertices; // se lee cantidad de enlace
    
    cantEnlaces = 0;
    while (source || cantEnlaces < cantVertices) {
        // Cargamos una linea del archivo
        source >> origen;
        source >> destino;
        source >> capacidad;
        source >> costo;
        source >> linea;
        
        // Creamos el enlace y guardamos en el grafo
        grafo.agregarEnlace(origen, Enlace(origen, destino, costo, capacidad));
        
        cantEnlaces++;
    }
    
    if (cantEnlaces != cantVertices) {
        std::cerr << "Error en el archivo "<< filename << "!" << endl;
        std::cerr << "La cantidad de vertices leidos no coincide con la cantidad especificada" << endl;
        exit(-1);
    }
}
