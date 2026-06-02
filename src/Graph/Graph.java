/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Graph;

import LinkedList.LinkedList;
import LinkedList.ListNode;
import Classes.Neurona;
import Classes.Sinapsis;

/** Clase Graph: representa el grafo dirigido de la red sinaptica Implementado mediante lista de adyacencia Cada neurona es un nodo y cada sinapsis es una arista dirigida entre dos neuronas
 * @author Diego Di Lima
 */
public class Graph {

    /** Arreglo de neuronas (nodos del grafo) */
    private Neurona[] neuronas;

    /** Arreglo de listas de adyacencia, Cada posicion corresponde a una neurona y su lista contiene todas las sinapsis que salen de ella */
    private LinkedList<Sinapsis>[] adyacencia;

    /** Cantidad actual de neuronas en el grafo */
    private int cantNeuronas;

    /** Capacidad maxima de neuronas que puede almacenar el grafo */
    private final int capacidad;

    /** Constructor que inicializa el grafo con una capacidad determinada */
    public Graph(int capacidad) {
        this.capacidad = capacidad;
        this.cantNeuronas = 0;
        // Se inicializa el arreglo de neuronas
        this.neuronas = new Neurona[capacidad];
        // Se inicializa el arreglo de listas de adyacencia
        this.adyacencia = new LinkedList[capacidad];
        for (int i = 0; i < capacidad; i++) {
            this.adyacencia[i] = new LinkedList<>();
        }
    }

    /** Constructor por defecto con capacidad de 100 neuronas */
    public Graph() {
        this(100);
    }

    /** Busca el indice de una neurona en el arreglo por su ID, Retorna -1 si no se encuentra */
    private int getIndice(String idNeurona) {
        for (int i = 0; i < cantNeuronas; i++) {
            if (neuronas[i].getId().equals(idNeurona)) {
                return i;
            }
        }
        return -1;
    }

    /** Agrega una neurona al grafo, Si ya existe una neurona con ese ID o si se alcanzo la capacidad maxima, no realiza ninguna operacion */
    public void agregarNeurona(Neurona neurona) {
        // Se verifica que no se haya alcanzado la capacidad maxima
        if (cantNeuronas >= capacidad) {
            return;
        }
        // Se verifica que no exista ya una neurona con ese ID
        if (getIndice(neurona.getId()) != -1) {
            return;
        }
        // Se agrega la neurona al arreglo
        neuronas[cantNeuronas] = neurona;
        cantNeuronas++;
    }

    /** Elimina una neurona del grafo por su ID junto con todas sus sinapsis entrantes y salientes, Si no existe, no realiza ninguna operacion */
    public void eliminarNeurona(String idNeurona) {
        // Se obtiene el indice de la neurona a eliminar
        int indice = getIndice(idNeurona);
        if (indice == -1) {
            return;
        }
        // Se eliminan todas las sinapsis salientes de la neurona
        adyacencia[indice] = new LinkedList<>();
        // Se eliminan todas las sinapsis entrantes desde otras neuronas
        for (int i = 0; i < cantNeuronas; i++) {
            if (i != indice) {
                eliminarSinapsisDeBucket(i, idNeurona);
            }
        }
        // Se desplaza el arreglo para llenar el hueco dejado por la neurona eliminada
        for (int i = indice; i < cantNeuronas - 1; i++) {
            neuronas[i] = neuronas[i + 1];
            adyacencia[i] = adyacencia[i + 1];
        }
        // Se limpia la ultima posicion
        neuronas[cantNeuronas - 1] = null;
        adyacencia[cantNeuronas - 1] = new LinkedList<>();
        cantNeuronas--;
    }

    /** Recorre la lista de adyacencia de un bucket y elimina todas las sinapsis que tengan como destino la neurona con el ID dado */
    private void eliminarSinapsisDeBucket(int indice, String idDestino) {
        // Se crea una nueva lista sin las sinapsis que apuntan al destino eliminado
        LinkedList<Sinapsis> nueva = new LinkedList<>();
        ListNode<Sinapsis> nodo = adyacencia[indice].getpFirst();
        while (nodo != null) {
            if (!nodo.getElement().getDestino().getId().equals(idDestino)) {
                nueva.append(nodo.getElement());
            }
            nodo = nodo.getpNext();
        }
        adyacencia[indice] = nueva;
    }

    /** Agrega una sinapsis dirigida entre dos neuronas existentes en el grafo, Si alguna de las neuronas no existe, no realiza ninguna operacion */
    public void agregarSinapsis(Sinapsis sinapsis) {
        // Se verifica que la neurona origen exista en el grafo
        int indiceOrigen = getIndice(sinapsis.getOrigen().getId());
        if (indiceOrigen == -1) {
            return;
        }
        // Se verifica que la neurona destino exista en el grafo
        int indiceDestino = getIndice(sinapsis.getDestino().getId());
        if (indiceDestino == -1) {
            return;
        }
        // Se agrega la sinapsis a la lista de adyacencia de la neurona origen
        adyacencia[indiceOrigen].append(sinapsis);
    }

    /** Retorna la neurona con el ID dado, o null si no existe */
    public Neurona getNeurona(String idNeurona) {
        int indice = getIndice(idNeurona);
        if (indice == -1) {
            return null;
        }
        return neuronas[indice];
    }

    /** Retorna la lista de sinapsis salientes de una neurona por su ID, Retorna null si la neurona no existe */
    public LinkedList<Sinapsis> getAdyacentes(String idNeurona) {
        int indice = getIndice(idNeurona);
        if (indice == -1) {
            return null;
        }
        return adyacencia[indice];
    }

    /** Retorna el arreglo completo de neuronas del grafo */
    public Neurona[] getNeuronas() {
        return neuronas;
    }

    /** Retorna la cantidad actual de neuronas en el grafo */
    public int getCantNeuronas() {
        return cantNeuronas;
    }

    /** Retorna la capacidad maxima del grafo */
    public int getCapacidad() {
        return capacidad;
    }

    /** Reinicia el estado visitada y aislada de todas las neuronas del grafo, Debe llamarse antes de cada nuevo recorrido BFS o DFS */
    public void resetEstados() {
        for (int i = 0; i < cantNeuronas; i++) {
            neuronas[i].setVisitada(false);
            neuronas[i].setAislada(false);
        }
    }
}