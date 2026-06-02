/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LinkedList;

/**
 * . Clase nodo para las listas simples enlazadas.
 * @author Diego Di Lima
 */
public class ListNode<T> {

    /** Elemento almacenado en el nodo */
    public T element;
    /** Referencia al siguiente nodo en la lista */
    public ListNode<T> pNext;

    /** Constructor que inicializa el nodo con un elemento y sin siguiente */
    public ListNode(T element) {
        this.element = element;
        this.pNext = null;
    }

    /** Constructor que inicializa el nodo con un elemento y un siguiente nodo */
    public ListNode(T element, ListNode<T> pNext) {
        this.element = element;
        this.pNext = pNext;
    }

    /** Constructor vacío, Crea un nodo sin elemento ni siguiente  */
    public ListNode() {
    }

    /** Retorna el elemento almacenado en el nodo */
    public T getElement() {
        return element;
    }

    /** Establece el elemento del nodo */
    public void setElement(T element) {
        this.element = element;
    }

    /** Retorna la referencia al siguiente nodo */
    public ListNode<T> getpNext() {
        return pNext;
    }

    /** @description Establece la referencia al siguiente nodo */
    public void setpNext(ListNode<T> pNext) {
        this.pNext = pNext;
    }
}