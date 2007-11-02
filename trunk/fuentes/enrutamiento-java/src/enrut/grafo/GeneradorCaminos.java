/*
 * @(#)GeneradorCaminos.java
 */
package enrut.grafo;

import java.util.Random;

public class GeneradorCaminos {
	private Random rand;
	
	/**
	 * Funci�n que genera las R rutas (o menos si no hay suficientes) para
	 * las correspondientes N demandas.
	 * @param index1 indica el indice de la a leer demanda
	 * @param index2 indica el indice de la ruta a ser insertada
	 * @param d[] demandas de conexiones
	 * @param Camino* Caminos encontrados (R*N caminos)
	 */
	void generarRutas(int index1, int index2, Demanda d[], Camino route[]) {
		int origen = d[index1].getOrigen(); // Origen
		int destino = d[index1].getDestino(); // Destino
		cout<<"Origen:"<<origen<<" Destino:"<<destino<<endl<<endl;
		
		Lista *lista = obtenerVertice(origen)->getAdyacentes(); //adyacentes
		int cantady = lista->getLongitud();
		int sigte = random(cantady); // id del camino a seguir
		cout<<"Cantady = "<<cantady<<"; sigte = "<<sigte<<endl;
		
		lista->iniciarIteracion();
		obtenerVertice(origen)->setVisitado(true);
		
		// sigte esta limitado por el tama�o de la lista
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
				route[index2].setCosto(route[index2].getCosto() + a->getCosto());
				// se agrega el camino
				route[index2].agregarCamino(a);
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
			// Selecci�n de una arista
			lista->iniciarIteracion();
			for (int i = 0; i<sigte ; i++)
				lista->siguiente();
		}
		
		
		if (route[index2].getPrimero()!=origen || route[index2].getUltimo()!=destino) {
			cout << "No hay camino entre los nodos"<<endl;
			cout <<"De:"<<origen<<" A:"<<destino<<endl;
		} else {
			route[index2].imprimir();
		}
	}

	/**
	 * Funcion principal para generar todas las rutas 
	 */
	void generarRutas(Demanda d[], Camino route[]) {
		srand ( time(NULL) );
		// i indica el indice de la a leer demanda
		// j indica el indice de la ruta a ser insertada
		for (int i = 0; i<1 ; i++){
			for (int j = 0; j<5; j++){
				generarRutas(i,j,d,route);
				reset(route[j]);
			}
		}
	}

	void reset(Camino route){
		for (int i = 0; i<5; i++){
			int n = route.getNodo(i);
			tabla[n].setVisitado(false);
		}
		tabla[route.getUltimo()].setVisitado(false);
	}
	
	/*
	 * Determine if there is a route between from and to
	 * by using depth-first searching.
	 */
	void findroute(Lista * list, int from, int to) {
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
	 * Funci�n que busca un camino de from a to en la lista de artistas
	 * Retorna verdadero si existe alg�n camino, sino, retorna falso.
	 */
	boolean match(Lista *lista, int from, int to, double &cost) 
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
	boolean find(Lista *lista, int from, Arista *arist) 
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
	
	private int random(int n) {
		if (rand == null)
			rand = new Random(System.currentTimeMillis());
		
		return rand.nextInt(n);
	}
}
