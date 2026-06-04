/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HashTable;

import LinkedList.LinkedList;
import LinkedList.ListNode;
import Classes.Neurotransmisor;

/** Clase TablaHash: implementacion de la tabla hash con manejo de colisiones por encadenamiento (chaining) usando lista enlazada 
 * @author Diego Di Lima y Aridna Lechin
 */
public class HashTable {

    /** Arreglo de buckets; cada posicion es una lista enlazada de Neurotransmisor */
    private LinkedList<Neurotransmisor>[] tabla;

    /** Cantidad de buckets en la tabla, Numero primo para mejor distribucion */
    private final int capacidad;

    /** Cantidad de neurotransmisores actualmente almacenados */
    private int size;

    /** Inicializa la tabla con la capacidad indicada y crea una lista enlazada vacia en cada bucket */
    public HashTable(int capacidad) {
        this.capacidad = capacidad;
        this.size = 0;
        // Se inicializa el arreglo de listas enlazadas
        this.tabla = new LinkedList[capacidad];
        // Se crea una lista vacia en cada bucket para evitar NullPointerException al insertar
        for (int i = 0; i < capacidad; i++) {
            this.tabla[i] = new LinkedList<>();
        }
    }

    /** Creamos la tabla con capacidad 101 el cual es un numero primo que garantiza buena distribucion para el diccionario de neurotransmisores del proyecto (50 entradas) */
    public HashTable() {
        this(101);
    }

    /** Funcion de dispersion DJB2. Calcula el indice del bucket correspondiente a una clave de texto. Recorre cada caracter del key,
     * acumulando el hash con la formula: hash = (hash * 32) + hash + char. El valor se toma en modulo con la capacidad para obtener un indice valido.
     */
    public int DJB2(String key) {
        // Valor inicial primo descrito por Bernstein para DJB2
        long hash = 5381;
        for (int i = 0; i < key.length(); i++) {
            // hash = hash * 33 + valor ASCII del caracter actual
            hash = ((hash * 32) + hash) + key.charAt(i);
        }
        // Si hay overflow y el hash es negativo, se convierte a positivo
        if (hash < 0) {
            hash = -hash;
        }
        // Se retorna el indice como residuo del modulo con la capacidad
        return Long.valueOf(hash % Long.valueOf(capacidad)).intValue();
    }

    /** Recorre el bucket en el indice dado buscando un neurotransmisor por su ID.
     * Compara directamente con getId() para evitar dependencia de toString().
     * Retorna el neurotransmisor si lo encuentra, null si no existe en el bucket.
     */
    private Neurotransmisor buscarEnBucket(int index, String key) {
        // Se obtiene el primer nodo del bucket
        ListNode<Neurotransmisor> nodo = tabla[index].getpFirst();
        // Se recorre la lista enlazada comparando el ID de cada neurotransmisor
        while (nodo != null) {
            if (nodo.getElement().getId().equals(key)) {
                return nodo.getElement();
            }
            nodo = nodo.getpNext();
        }
        return null;
    }

    /** Se inserta un neurotransmisor en la tabla hash, se calcula el indice con DJB2 usando el ID del neurotransmisor.
     * Si ya existe un neurotransmisor con ese ID en el bucket, no lo duplica.
     * En caso de colision, el nuevo elemento se encadena al bucket existente mediante la lista enlazada.
     */
    public void put(Neurotransmisor neurotransmisor) {
        // Se obtiene la clave (ID) del neurotransmisor
        String key = neurotransmisor.getId();
        // Se calcula el indice del bucket usando DJB2
        int index = DJB2(key);
        // Se verifica si ya existe un neurotransmisor con ese ID en el bucket para evitar duplicados
        if (buscarEnBucket(index, key) != null) {
            return;
        }
        // Se inserta al inicio del bucket (O(1))
        tabla[index].insertAtHead(neurotransmisor);
        size++;
    }

    /** Busca y retorna un neurotransmisor por su ID.
     * Calcula el indice con DJB2 y recorre el bucket comparando con getId().
     */
    public Neurotransmisor get(String key) {
        // Se calcula el indice del bucket usando DJB2
        int index = DJB2(key);
        // Se busca en el bucket comparando directamente con getId()
        return buscarEnBucket(index, key);
    }

    /** Elimina un neurotransmisor de la tabla hash por su ID.
     * Recorre el bucket manualmente para encontrar y desenlazar el nodo correspondiente.
     */
    public boolean remove(String key) {
        // Se calcula el indice del bucket usando DJB2
        int index = DJB2(key);
        // Si el bucket esta vacio no hay nada que eliminar
        if (tabla[index].isEmpty()) {
            return false;
        }
        // Caso especial: el primer nodo del bucket es el buscado
        if (tabla[index].getpFirst().getElement().getId().equals(key)) {
            tabla[index].deleteAtHead();
            size--;
            return true;
        }
        // Se recorre el bucket buscando el nodo anterior al que se quiere eliminar
        ListNode<Neurotransmisor> nodo = tabla[index].getpFirst();
        while (nodo.getpNext() != null) {
            if (nodo.getpNext().getElement().getId().equals(key)) {
                // Se desenlaza el nodo encontrado
                nodo.setpNext(nodo.getpNext().getpNext());
                tabla[index].setSize(tabla[index].getSize() - 1);
                size--;
                return true;
            }
            nodo = nodo.getpNext();
        }
        return false;
    }

    /** Verifica si existe un neurotransmisor con el ID dado */
    public boolean contains(String key) {
        return get(key) != null;
    }

    /** Retorna la cantidad de neurotransmisores almacenados */
    public int getSize() {
        return size;
    }

    /** Retorna la capacidad total de buckets de la tabla */
    public int getCapacidad() {
        return capacidad;
    }

    /** Convierte el contenido de la tabla a String para visualizacion en la interfaz grafica. Recorre todos los buckets no vacios y lista sus elementos */
    public String tablaToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < capacidad; i++) {
            if (!tabla[i].isEmpty()) {
                sb.append("Bucket ").append(i).append(": ");
                // Se recorre el bucket manualmente para armar el string
                ListNode<Neurotransmisor> nodo = tabla[i].getpFirst();
                while (nodo != null) {
                    sb.append("[").append(nodo.getElement().getId()).append("] -> ");
                    nodo = nodo.getpNext();
                }
                sb.append("null\n");
            }
        }
        return sb.toString();
    }
    
    public LinkedList<Neurotransmisor> getBucket(int index) {
        return tabla[index];
    }
}