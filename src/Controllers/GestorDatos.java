/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Controllers;

import Classes.Neurona;
import Classes.Sinapsis;
import Classes.Neurotransmisor;
import Graph.Graph;
import HashTable.HashTable;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/** 
 * . Controlador encargado de leer los archivos CSV y poblar las estructuras de datos del sistema 
 * Instancia las neuronas y sinapsis segun el CSV del grafo y las guarda en el Graph 
 * Instancia los neurotransmisores segun el CSV del diccionario y los guarda en la HashTable.
 * @author Ariadna Lechin
 */
public class GestorDatos {

    /** Grafo de la red sinaptica que sera poblado con los datos del CSV */
    private Graph graph;

    /** Tabla hash del diccionario de neurotransmisores */
    private HashTable hashTable;

    /** Constructor que recibe las estructuras de datos a poblar */
    public GestorDatos(Graph graph, HashTable hashTable) {
        this.graph = graph;
        this.hashTable = hashTable;
    }

    /** Lee el CSV del grafo y puebla el Graph con neuronas y sinapsis
     * Si una neurona origen o destino no existe aun en el grafo, la crea automaticamente Omite la primera linea del archivo asumiendo que es el header
     * Formato esperado: origen,destino,distancia,ID_Neurotransmisor,coheficiente_eficiencia_sinaptica
     */
    public void cargarGrafo(String ruta) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(ruta));
            // Se omite la primera linea (header)
            String linea = br.readLine();
            // Se lee cada linea del CSV
            while ((linea = br.readLine()) != null) {
                // Se ignoran lineas vacias
                if (linea.trim().isEmpty()) {
                    continue;
                }
                // Se separan los campos por coma
                String[] campos = linea.split(",");
                // Se valida que la linea tenga exactamente 5 campos
                if (campos.length != 5) {
                    continue;
                }
                // Se obtienen los valores de cada campo
                String idOrigen = campos[0].trim();
                String idDestino = campos[1].trim();
                double distancia = Double.parseDouble(campos[2].trim());
                String idNeurotransmisor = campos[3].trim();
                double coheficiente = Double.parseDouble(campos[4].trim());
                // Se crea la neurona origen si no existe en el grafo
                if (graph.getNeurona(idOrigen) == null) {
                    graph.agregarNeurona(new Neurona(idOrigen));
                }
                // Se crea la neurona destino si no existe en el grafo
                if (graph.getNeurona(idDestino) == null) {
                    graph.agregarNeurona(new Neurona(idDestino));
                }
                // Se obtienen las referencias a las neuronas ya existentes en el grafo
                Neurona origen = graph.getNeurona(idOrigen);
                Neurona destino = graph.getNeurona(idDestino);
                // Se crea la sinapsis y se agrega al grafo
                Sinapsis sinapsis = new Sinapsis(origen, destino, distancia, idNeurotransmisor, coheficiente);
                graph.agregarSinapsis(sinapsis);
            }
        } catch (IOException e) {
            // Se delega el manejo del error a la interfaz grafica
            throw new RuntimeException("Error al leer el archivo del grafo: " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error en el formato numerico del archivo del grafo: " + e.getMessage());
        } finally {
            // Se cierra el BufferedReader en el bloque finally para garantizar su cierre
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException("Error al cerrar el archivo del grafo: " + e.getMessage());
                }
            }
        }
    }

    /** Lee el CSV del diccionario de neurotransmisores y puebla la HashTable.
     * Omite la primera linea del archivo asumiendo que es el encabezado.
     * Formato esperado: id,nombre,efecto,velocidad,descripcion
     */
    public void cargarDiccionario(String ruta) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(ruta));
            // Se omite la primera linea (encabezado)
            String linea = br.readLine();
            // Se lee cada linea del CSV
            while ((linea = br.readLine()) != null) {
                // Se ignoran lineas vacias
                if (linea.trim().isEmpty()) {
                    continue;
                }
                // Se separan los campos por coma, limitando a 5 partes para preservar
                // la descripcion que puede contener comas internas entre comillas
                String[] campos = linea.split(",", 5);
                // Se valida que la linea tenga al menos 5 campos
                if (campos.length < 5) {
                    continue;
                }
                // Se obtienen los valores de cada campo
                String id = campos[0].trim();
                String nombre = campos[1].trim();
                String efecto = campos[2].trim();
                double velocidad = Double.parseDouble(campos[3].trim());
                // Se eliminan las comillas de la descripcion si las tiene
                String descripcion = campos[4].trim().replace("\"", "");
                // Se crea el neurotransmisor y se inserta en la tabla hash
                Neurotransmisor nt = new Neurotransmisor(id, nombre, efecto, velocidad, descripcion);
                hashTable.put(nt);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo del diccionario: " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error en el formato numerico del archivo del diccionario: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException("Error al cerrar el archivo del diccionario: " + e.getMessage());
                }
            }
        }
    }

    /** Retorna el grafo poblado con las neuronas y sinapsis del CSV */
    public Graph getGraph() {
        return graph;
    }

    /** Establece el grafo a poblar */
    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    /** Retorna la tabla hash poblada con los neurotransmisores del CSV */
    public HashTable getHashTable() {
        return hashTable;
    }

    /** Establece la tabla hash a poblar */
    public void setHashTable(HashTable hashTable) {
        this.hashTable = hashTable;
    }
}