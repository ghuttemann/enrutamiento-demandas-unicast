//============================================================================
// Name        : Enrutamiento.cpp
// Author      : Marcelo Rodas
// Version     : 1.0
// Copyright   : copyright
// Description : Lector del archivo que representa un grafo, Ansi-style
//============================================================================
#include "Lector.h"
#include<string>
#include<cctype>
#include<fstream>
#include<iostream>

namespace br_stl
{

	Lector::Lector(){
		this->enlace.origen = 0; //solo muestra
		this->enlace.destino = 0; //solo muestra
		this->enlace.capacidad = 0; //solo muestra
		this->enlace.costo = 0; //solo muestra
	}

	Lector::~Lector(){
		//destructor
	}

	void Lector::leerArchivo(const char *Filename){
		ifstream Source;
		string linea;
		int cantnodos;
		int cantvertices;
		
		Source.open(Filename);
		if (!Source) {
			std::cerr << "No se puede abrir "<< Filename << "!\n";
			exit(-1);
		}
		
		// se lee cantidad de nodos
		Source >> cantnodos;
		// se lee cantidad de enlaces
		Source >> cantvertices;
		struct enlaces vertAux[cantvertices];
		int i=0;
		
		cout << "     Origen | Destino | Costo | Capacidad"<<endl;
		while (Source && i<cantvertices){
			Source >> vertAux[i].origen;
			Source >> vertAux[i].destino;
			Source >> vertAux[i].capacidad;
			Source >> vertAux[i].costo;
			Source >> linea;
			vertAux[i].capacidad *=1000;
			vertAux[i].costo *=1000;
			this->printenlace(vertAux[i]);
			i++;
		}
	}

	void Lector::printenlace(struct enlaces e){
		cout <<"\t"<<e.origen<<"\t"<<e.destino<<"\t";
		cout <<e.costo<<"\t"<<e.capacidad<<endl;
	}

} // namespace br_stl


int main(){
	Lector x;
	cout << "!!!LECTOR DE ARCHIVO GRAFO!!!" << endl;
	x.Lector::leerArchivo("C:\\eclipsecpp\\workspace\\Enrutamiento\\grafo.m");
	cout << "ENDOFFILE";
	return 0;
};
