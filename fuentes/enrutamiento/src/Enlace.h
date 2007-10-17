#ifndef ENLACE_H_
#define ENLACE_H_

class Enlace {
private:
    int origen;
    int destino;
    double costo;
    double capacidad;
public:
    // Getters
    int getOrigen() { return origen; }
    int getDestino() { return destino; }
    double getCosto() { return costo; }
    double getCapaciadad() { return capacidad; }
    
    // Setters
    void setOrigen(int origen) { this->origen = origen; }
    void setDestino(int destino) { this->destino = destino; }
    void setCosto(double costo) { this->costo = costo; }
    void setCapacidada(double capacidad) {this->capacidad = capacidad; }
    
    // Constructor
    Enlace(int origen, int destino, double costo, double capacidad) {
        this->origen    = origen;
        this->destino   = destino;
        this->costo     = costo;
        this->capacidad = capacidad;
    }
};

#endif /*ENLACE_H_*/
