/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controllers;

import Graph.Graph;
import Classes.Neurona;
import Classes.Sinapsis;
import LinkedList.LinkedList;
import LinkedList.ListNode;

/** Controlador que detecta zonas aisladas en la red sinaptica usando BFS o DFS
 * @author Ariadna Lechin
 */
public class ZonasAisladas {

    /** Grafo de la red sinaptica */
    private Graph graph;

    /** Constructor que recibe el grafo a analizar */
    public ZonasAisladas(Graph graph) {
        this.graph = graph;
    }

    /** Recorrido BFS desde la neurona fuente, marca visitadas y aisladas */
    public void BFS(String idFuente) {
        graph.resetEstados();
        Neurona fuente = graph.getNeurona(idFuente);
        if (fuente == null) {
            return;
        }
        // Se usa LinkedList como cola FIFO
        LinkedList<Neurona> cola = new LinkedList<>();
        fuente.setVisitada(true);
        cola.append(fuente);
        while (!cola.isEmpty()) {
            Neurona actual = cola.poll();
            LinkedList<Sinapsis> adyacentes = graph.getAdyacentes(actual.getId());
            if (adyacentes == null) {
                continue;
            }
            // Se encolan los vecinos no visitados
            ListNode<Sinapsis> nodo = adyacentes.getpFirst();
            while (nodo != null) {
                Neurona vecino = nodo.getElement().getDestino();
                if (!vecino.isVisitada()) {
                    vecino.setVisitada(true);
                    cola.append(vecino);
                }
                nodo = nodo.getpNext();
            }
        }
        marcarAisladas();
    }

    /** Recorrido DFS desde la neurona fuente, marca visitadas y aisladas */
    public void DFS(String idFuente) {
        graph.resetEstados();
        Neurona fuente = graph.getNeurona(idFuente);
        if (fuente == null) {
            return;
        }
        // Se usa LinkedList como pila LIFO
        LinkedList<Neurona> pila = new LinkedList<>();
        pila.insertAtHead(fuente);
        while (!pila.isEmpty()) {
            Neurona actual = pila.poll();
            if (actual.isVisitada()) {
                continue;
            }
            actual.setVisitada(true);
            LinkedList<Sinapsis> adyacentes = graph.getAdyacentes(actual.getId());
            if (adyacentes == null) {
                continue;
            }
            // Se apilan los vecinos no visitados
            ListNode<Sinapsis> nodo = adyacentes.getpFirst();
            while (nodo != null) {
                Neurona vecino = nodo.getElement().getDestino();
                if (!vecino.isVisitada()) {
                    pila.insertAtHead(vecino);
                }
                nodo = nodo.getpNext();
            }
        }
        marcarAisladas();
    }

    /** Marca como aisladas las neuronas no visitadas tras el recorrido */
    private void marcarAisladas() {
        Neurona[] neuronas = graph.getNeuronas();
        for (int i = 0; i < graph.getCantNeuronas(); i++) {
            if (!neuronas[i].isVisitada()) {
                neuronas[i].setAislada(true);
            }
        }
    }

    /** Retorna true si todas las neuronas son alcanzables desde la fuente */
    public boolean esFuertementeConexo(String idFuente) {
        BFS(idFuente);
        Neurona[] neuronas = graph.getNeuronas();
        for (int i = 0; i < graph.getCantNeuronas(); i++) {
            if (neuronas[i].isAislada()) {
                return false;
            }
        }
        return true;
    }

    /** Retorna el grafo asociado */
    public Graph getGraph() {
        return graph;
    }

    /** Establece el grafo a analizar */
    public void setGraph(Graph graph) {
        this.graph = graph;
    }
}