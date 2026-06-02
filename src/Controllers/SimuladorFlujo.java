/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Graph.Graph;
import HashTable.HashTable;
import Classes.Neurona;
import Classes.Sinapsis;
import LinkedList.LinkedList;
import LinkedList.ListNode;

/** . Controlador que calcula la ruta de menor tiempo entre dos neuronas usando Dijkstra
 * @author Ariadna Lechin
 */
public class SimuladorFlujo {

    /** Grafo de la red sinaptica */
    private Graph graph;

    /** Tabla hash del diccionario de neurotransmisores */
    private HashTable hashTable;

    /** Arreglo de distancias minimas desde la fuente a cada neurona */
    private double[] distancias;

    /** Arreglo de predecesores para reconstruir la ruta */
    private String[] predecesores;

    /** Constructor que recibe el grafo y la tabla hash */
    public SimuladorFlujo(Graph graph, HashTable hashTable) {
        this.graph = graph;
        this.hashTable = hashTable;
    }

    /** Ejecuta Dijkstra desde la neurona fuente y calcula distancias minimas a todas las demas */
    public void dijkstra(String idFuente) {
        int n = graph.getCantNeuronas();
        distancias = new double[n];
        predecesores = new String[n];

        // Se inicializan todas las distancias en infinito y predecesores en null
        for (int i = 0; i < n; i++) {
            distancias[i] = Double.MAX_VALUE;
            predecesores[i] = null;
        }

        // Se obtiene el indice de la neurona fuente y se inicializa su distancia en 0
        int indiceFuente = getIndice(idFuente);
        if (indiceFuente == -1) {
            return;
        }
        distancias[indiceFuente] = 0;

        // Se usa LinkedList como lista de nodos pendientes por visitar
        // Cada elemento es el ID de la neurona pendiente
        LinkedList<String> pendientes = new LinkedList<>();
        pendientes.insertAtHead(idFuente);

        while (!pendientes.isEmpty()) {
            // Se busca el nodo pendiente con menor distancia
            String idActual = extraerMinimo(pendientes);
            int indiceActual = getIndice(idActual);

            // Se recorren las sinapsis salientes de la neurona actual
            LinkedList<Sinapsis> adyacentes = graph.getAdyacentes(idActual);
            if (adyacentes == null) {
                continue;
            }
            ListNode<Sinapsis> nodo = adyacentes.getpFirst();
            while (nodo != null) {
                Sinapsis sinapsis = nodo.getElement();
                String idVecino = sinapsis.getDestino().getId();
                int indiceVecino = getIndice(idVecino);

                // Se obtiene la velocidad del neurotransmisor desde la hash table
                double velocidad = getVelocidad(sinapsis.getIdNeurotransmisor());

                // Se calcula el peso W = d / (v * k)
                double peso = sinapsis.calcularPeso(velocidad);

                // Se relaja la arista si se encontro un camino mas corto
                double nuevaDistancia = distancias[indiceActual] + peso;
                if (nuevaDistancia < distancias[indiceVecino]) {
                    distancias[indiceVecino] = nuevaDistancia;
                    predecesores[indiceVecino] = idActual;
                    // Se agrega el vecino a pendientes si no esta ya
                    if (!estaEnLista(pendientes, idVecino)) {
                        pendientes.insertAtHead(idVecino);
                    }
                }
                nodo = nodo.getpNext();
            }
        }
    }

    /** Extrae y retorna el ID de la neurona con menor distancia de la lista de pendientes */
    private String extraerMinimo(LinkedList<String> pendientes) {
        ListNode<String> nodo = pendientes.getpFirst();
        String idMinimo = nodo.getElement();
        double distMin = distancias[getIndice(idMinimo)];

        // Se recorre la lista buscando el minimo
        while (nodo != null) {
            String id = nodo.getElement();
            double dist = distancias[getIndice(id)];
            if (dist < distMin) {
                distMin = dist;
                idMinimo = id;
            }
            nodo = nodo.getpNext();
        }

        // Se elimina el minimo de la lista de pendientes
        eliminarDeLista(pendientes, idMinimo);
        return idMinimo;
    }

    /** Verifica si un ID esta en la lista de pendientes */
    private boolean estaEnLista(LinkedList<String> lista, String id) {
        ListNode<String> nodo = lista.getpFirst();
        while (nodo != null) {
            if (nodo.getElement().equals(id)) {
                return true;
            }
            nodo = nodo.getpNext();
        }
        return false;
    }

    /** Elimina un ID de la lista de pendientes */
    private void eliminarDeLista(LinkedList<String> lista, String id) {
        if (lista.isEmpty()) {
            return;
        }
        // Caso especial: el primero es el buscado
        if (lista.getpFirst().getElement().equals(id)) {
            lista.deleteAtHead();
            return;
        }
        ListNode<String> nodo = lista.getpFirst();
        while (nodo.getpNext() != null) {
            if (nodo.getpNext().getElement().equals(id)) {
                nodo.setpNext(nodo.getpNext().getpNext());
                lista.setSize(lista.getSize() - 1);
                return;
            }
            nodo = nodo.getpNext();
        }
    }

    /** Retorna la ruta de menor tiempo desde la fuente hasta el destino como LinkedList de IDs */
    public LinkedList<String> getRuta(String idFuente, String idDestino) {
        // Se ejecuta Dijkstra desde la fuente
        dijkstra(idFuente);
        LinkedList<String> ruta = new LinkedList<>();
        int indiceDestino = getIndice(idDestino);

        // Si no hay ruta posible se retorna lista vacia
        if (indiceDestino == -1 || distancias[indiceDestino] == Double.MAX_VALUE) {
            return ruta;
        }

        // Se reconstruye la ruta siguiendo los predecesores hacia atras
        String actual = idDestino;
        while (actual != null) {
            ruta.insertAtHead(actual);
            actual = predecesores[getIndice(actual)];
        }
        return ruta;
    }

    /** Retorna el tiempo minimo de transmision desde la fuente hasta el destino */
    public double getTiempo(String idFuente, String idDestino) {
        dijkstra(idFuente);
        int indiceDestino = getIndice(idDestino);
        if (indiceDestino == -1) {
            return -1;
        }
        return distancias[indiceDestino];
    }

    /** Simula deterioro cognitivo por fatiga multiplicando todos los k de las sinapsis por 1.2 */
    public void simularFatiga() {
        Neurona[] neuronas = graph.getNeuronas();
        for (int i = 0; i < graph.getCantNeuronas(); i++) {
            LinkedList<Sinapsis> adyacentes = graph.getAdyacentes(neuronas[i].getId());
            if (adyacentes == null) {
                continue;
            }
            // Se multiplica el coeficiente de eficiencia de cada sinapsis por 1.2
            ListNode<Sinapsis> nodo = adyacentes.getpFirst();
            while (nodo != null) {
                Sinapsis s = nodo.getElement();
                s.setCoheficienteEficienciaSinaptica(s.getCoheficienteEficienciaSinaptica() * 1.2);
                nodo = nodo.getpNext();
            }
        }
    }

    /** Obtiene la velocidad del neurotransmisor desde la hash table, retorna 1.0 si no se encuentra */
    private double getVelocidad(String idNeurotransmisor) {
        if (hashTable.get(idNeurotransmisor) != null) {
            return hashTable.get(idNeurotransmisor).getVelocidad();
        }
        return 1.0;
    }

    /** Retorna el indice de una neurona en el grafo por su ID, -1 si no existe */
    private int getIndice(String id) {
        Neurona[] neuronas = graph.getNeuronas();
        for (int i = 0; i < graph.getCantNeuronas(); i++) {
            if (neuronas[i].getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    /** Retorna el grafo asociado */
    public Graph getGraph() {
        return graph;
    }

    /** Establece el grafo */
    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    /** Retorna la tabla hash asociada */
    public HashTable getHashTable() {
        return hashTable;
    }

    /** Establece la tabla hash */
    public void setHashTable(HashTable hashTable) {
        this.hashTable = hashTable;
    }
}