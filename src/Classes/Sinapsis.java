/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

/** Clase Sinapsis: representa una arista dirigida del grafo de red sinaptica 
 * @author Diego Di Lima
 */
public class Sinapsis {

    /** Referencia a la neurona de origen (neurona presináptica) */
    private Neurona origen;

    /** Referencia a la neurona de destino (neurona postsináptica) */
    private Neurona destino;

    /** Distancia sinaptica entre las dos neuronas, obtenida del CSV */
    private double distancia;

    /** ID del neurotransmisor que utiliza esta conexion (ej: "GLU", "GABA") */
    private String idNeurotransmisor;

    /** Coeficiente de eficiencia sinaptica (k). Indica si la neurona esta fatigada.
     * Se multiplica por 1.2 al simular deterioro cognitivo por fatiga.
     */
    private double coheficienteEficienciaSinaptica;

    /** Constructor que inicializa todos los atributos de la sinapsis */
    public Sinapsis(Neurona origen, Neurona destino, double distancia, String idNeurotransmisor, double coheficienteEficienciaSinaptica) {
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
        this.idNeurotransmisor = idNeurotransmisor;
        this.coheficienteEficienciaSinaptica = coheficienteEficienciaSinaptica;
    }

    /** Retorna la neurona de origen de esta sinapsis */
    public Neurona getOrigen() {
        return origen;
    }

    /** Establece la neurona de origen */
    public void setOrigen(Neurona origen) {
        this.origen = origen;
    }

    /** Retorna la neurona de destino de esta sinapsis */
    public Neurona getDestino() {
        return destino;
    }

    /** Establece la neurona de destino */
    public void setDestino(Neurona destino) {
        this.destino = destino;
    }

    /** Retorna la distancia sinaptica entre las dos neuronas */
    public double getDistancia() {
        return distancia;
    }

    /** Establece la distancia sinaptica */
    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    /** Retorna el ID del neurotransmisor que utiliza esta sinapsis */
    public String getIdNeurotransmisor() {
        return idNeurotransmisor;
    }

    /** Establece el ID del neurotransmisor */
    public void setIdNeurotransmisor(String idNeurotransmisor) {
        this.idNeurotransmisor = idNeurotransmisor;
    }

    /** Retorna el coeficiente de eficiencia sinaptica (k) */
    public double getCoheficienteEficienciaSinaptica() {
        return coheficienteEficienciaSinaptica;
    }

    /** Establece el coeficiente de eficiencia sinaptica (k) */
    public void setCoheficienteEficienciaSinaptica(double coheficienteEficienciaSinaptica) {
        this.coheficienteEficienciaSinaptica = coheficienteEficienciaSinaptica;
    }

    /** Calculamos y retornamos el peso de esta arista para los algoritmos de Dijkstra
     * Utilizamos la formula W = d / (v * k), donde v es la velocidad del neurotransmisor
     * recuperada de la TablaHash y k es el coeficiente de eficiencia sinaptica
     */
    public double calcularPeso(double velocidad) {
        // Se aplica la formula W = d / (v * k)
        return distancia / (velocidad * coheficienteEficienciaSinaptica);
    }
}