/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import Graph.Graph;
import HashTable.HashTable;
import Controllers.GestorDatos;
import javax.swing.JOptionPane;

/** . Clase App: Singleton que mantiene las instancias unicas de Graph, HashTable y GestorDatos para que toda la aplicacion comparta los mismos datos, al crearse carga automaticamente los CSV preestablecidos del paquete Data
 * @author Ariadna Lechin
 */
public class App {

    /** Singleton */
    private static App app;

    /** Grafo de la red sinaptica  */
    private Graph graph;

    /** Tabla hash del diccionario de neurotransmisores */
    private HashTable hashTable;

    /** Gestor de datos encargado de leer los CSV y poblar las estructuras */
    private GestorDatos gestorDatos;

    /** Ruta del CSV del grafo preestablecido en el paquete Data */
    private static final String RUTA_GRAFO = "src/Data/red_sinaptica.csv";

    /** Ruta del CSV del diccionario de neurotransmisores preestablecido en el paquete Data */
    private static final String RUTA_DICCIONARIO = "src/Data/diccionario_neurotransmisores.csv";

    /** Constructor privado del Singleton, y carga los CSV preestablecidos del paquete Data  */
    private App() {
        // Se inicializan las estructuras de datos
        this.graph = new Graph();
        this.hashTable = new HashTable();
        this.gestorDatos = new GestorDatos(graph, hashTable);
        // Se cargan los CSV preestablecidos
        this.cargarDatosDefault();
    }

    /** Retorna la instancia unica del Singleton, Si no existe la crea     */
    public static synchronized App getInstance() {
        if (app == null) {
            app = new App();
        }
        return app;
    }

    /** Carga los CSV preestablecidos del paquete Data al iniciar la aplicacion, Se carga primero el diccionario y luego el grafo */
    private void cargarDatosDefault() {
        // Se carga primero el diccionario para que los neurotransmisores
        // esten disponibles antes de procesar las sinapsis del grafo
        try {
            gestorDatos.cargarDiccionario(RUTA_DICCIONARIO);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null,
                "No se pudo cargar el diccionario de neurotransmisores.\n" + e.getMessage(),
                "Error al cargar datos",
                JOptionPane.ERROR_MESSAGE);
        }
        // Se carga el grafo de la red sinaptica
        try {
            gestorDatos.cargarGrafo(RUTA_GRAFO);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null,
                "No se pudo cargar la red sinaptica.\n" + e.getMessage(),
                "Error al cargar datos",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Recarga el grafo desde un CSV indicado por el usuario, Reemplaza el grafo actual por uno nuevo con los datos del archivo */
    public void recargarGrafo(String ruta) {
        // Se reinicia el grafo para no mezclar datos anteriores con los nuevos
        this.graph = new Graph();
        this.gestorDatos.setGraph(this.graph);
        try {
            gestorDatos.cargarGrafo(ruta);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null,
                "No se pudo cargar el archivo.\n" + e.getMessage(),
                "Error al cargar archivo",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Recarga el diccionario de neurotransmisores desde un CSV indicado por el usuario, Reemplaza la tabla hash actual por una nueva con los datos del archivo */
    public void recargarDiccionario(String ruta) {
        // Se reinicia la tabla hash para no mezclar datos anteriores con los nuevos
        this.hashTable = new HashTable();
        this.gestorDatos.setHashTable(this.hashTable);
        try {
            gestorDatos.cargarDiccionario(ruta);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(null,
                "No se pudo cargar el diccionario.\n" + e.getMessage(),
                "Error al cargar diccionario",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Retorna el grafo de la red sinaptica */
    public Graph getGraph() {
        return graph;
    }

    /** Establece el grafo de la red sinaptica */
    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    /** Retorna la tabla hash del diccionario de neurotransmisores */
    public HashTable getHashTable() {
        return hashTable;
    }

    /** Establece la tabla hash del diccionario de neurotransmisores */
    public void setHashTable(HashTable hashTable) {
        this.hashTable = hashTable;
    }

    /** Retorna el gestor de datos */
    public GestorDatos getGestorDatos() {
        return gestorDatos;
    }

    /** Establece el gestor de datos */
    public void setGestorDatos(GestorDatos gestorDatos) {
        this.gestorDatos = gestorDatos;
    }
    
    
}