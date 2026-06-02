/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LinkedList;

/**
 * . Clase LinkedList: lista simple enlazada 
 * @author Diego Di Lima
 */
public class LinkedList<T> {

    /** Primer nodo de la lista */
    private ListNode<T> pFirst;

    /** Cantidad de elementos en la lista */
    private int size;

    /** Constructor, Crea una lista vacía sin elementos */
    public LinkedList() {
        this.pFirst = null;
        this.size = 0;
    }

    /** Verificar si la lista está vacía */
    public boolean isEmpty() {
        return (pFirst == null);
    }

    /** Retornar el primer nodo de la lista sin eliminarlo  */
    public ListNode<T> first() {
        return pFirst;
    }

    /** Retorna el último nodo de la lista sin eliminarlo  */
    public ListNode<T> last() {
        if (isEmpty()) {
            return null;
        }
        ListNode<T> pAux = pFirst;
        while (pAux.pNext != null) {
            pAux = pAux.pNext;
        }
        return pAux;
    }

    /** Inserta un elemento al inicio de la lista */
    public void insertAtHead(T element) {
        ListNode<T> newNode = new ListNode<>(element);
        newNode.pNext = pFirst;
        pFirst = newNode;
        size++;
    }

    /** Elimina el primer nodo de la lista  */
    public void deleteAtHead() {
        if (pFirst != null) {
            pFirst = pFirst.pNext;
            size--;
        }
    }

    /** Elimina y retorna el elemento al inicio de la lista (poll)  */
    public T poll() {
        if (pFirst != null) {
            ListNode<T> pAux = pFirst;
            pFirst = pFirst.pNext;
            size--;
            return pAux.element;
        }
        return null;
    }

    /** Retorna la cantidad de elementos en la lista */
    public int len() {
        return size;
    }

    /** Inserta un elemento en una posición determinada (0-indexado) Si la posición es inválida, no realiza ninguna operación */
    public void insertAtPosition(T element, int position) {
        if (position < 0 || position > size) {
            return; 
        }
        ListNode<T> newNode = new ListNode<>(element);
        if (position == 0) {
            newNode.pNext = pFirst;
            pFirst = newNode;
        } else {
            ListNode<T> pAux = pFirst;
            for (int i = 0; i < position - 1; i++) {
                pAux = pAux.pNext;
            }
            newNode.pNext = pAux.pNext;
            pAux.pNext = newNode;
        }
        size++;
    }

    /** Inserta un elemento al final de la lista  */
    public void append(T element) {
        ListNode<T> newNode = new ListNode<>(element);
        if (pFirst == null) {
            pFirst = newNode;
        } else {
            ListNode<T> pAux = pFirst;
            while (pAux.pNext != null) {
                pAux = pAux.pNext;
            }
            pAux.pNext = newNode;
        }
        size++;
    }

    /** Retorna el elemento en una posición determinada (0-indexado) */
    public T getByPosition(int position) {
        if (position < 0 || position >= size) {
            return null; // posición inválida
        }
        ListNode<T> pAux = pFirst;
        for (int i = 0; i < position; i++) {
            pAux = pAux.pNext;
        }
        return pAux.element;
    }

    /**  Busca un elemento en la lista por su key, es usado por la tabla hash para resolver colisiones: al encontrar el bucket correcto, se itera esta lista buscando el neurotransmisor por ID */
    public T searchByKey(String key) {
        ListNode<T> pAux = pFirst;
        while (pAux != null) {
            if (pAux.element != null && pAux.element.toString().startsWith(key + ",")) {
                return pAux.element;
            }
            pAux = pAux.pNext;
        }
        return null;
    }

    /** Elimina el primer nodo cuyo elemento coincida con la clave, es util para eliminar un neurotransmisor especifico dentro de un bucket en caso de ser necesario*/
    public boolean deleteByKey(String key) {
        if (isEmpty()) {
            return false;
        }
        // caso: el primero es el buscado
        if (pFirst.element != null && pFirst.element.toString().startsWith(key + ",")) {
            pFirst = pFirst.pNext;
            size--;
            return true;
        }
        ListNode<T> pAux = pFirst;
        while (pAux.pNext != null) {
            if (pAux.pNext.element != null && pAux.pNext.element.toString().startsWith(key + ",")) {
                pAux.pNext = pAux.pNext.pNext;
                size--;
                return true;
            }
            pAux = pAux.pNext;
        }
        return false;
    }

    /** Convierte la lista a una representación en String */
    public String listToString() {
        StringBuilder sb = new StringBuilder();
        ListNode<T> pAux = pFirst;
        while (pAux != null) {
            sb.append(pAux.element).append(" -> ");
            pAux = pAux.pNext;
        }
        sb.append("null");
        return sb.toString();
    }

    /**  Retorna el primer nodo de la lista */
    public ListNode<T> getpFirst() {
        return pFirst;
    }

    /** Establece el primer nodo de la lista */
    public void setpFirst(ListNode<T> pFirst) {
        this.pFirst = pFirst;
    }

    /** Retorna el tamaño actual de la lista */
    public int getSize() {
        return size;
    }

    /** @description Establece el tamaño de la lista manualmente. */
    public void setSize(int size) {
        this.size = size;
    }
}