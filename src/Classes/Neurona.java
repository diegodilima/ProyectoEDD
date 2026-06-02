/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

/** Clase Neurona: representa un nodo del grafo de red sinaptica.
 * @author Diego Di Lima
 */
public class Neurona {

    /** Identificador unico de la neurona */
    private String id;

    /** Indica si la neurona fue visitada durante un recorrido BFS o DFS */
    private boolean visitada;

    /** Indica si la neurona es una zona aislada (no alcanzable desde la fuente) */
    private boolean aislada;

    /** Constructor que inicializa la neurona con su ID. 
     * Por defecto no esta visitada ni aislada.
     */
    public Neurona(String id) {
        this.id = id;
        this.visitada = false;
        this.aislada = false;
    }

    /** Retorna el ID unico de la neurona */
    public String getId() {
        return id;
    }

    /** Establece el ID de la neurona */
    public void setId(String id) {
        this.id = id;
    }

    /** Retorna true si la neurona fue visitada durante el recorrido */
    public boolean isVisitada() {
        return visitada;
    }

    /** Establece el estado de visita de la neurona */
    public void setVisitada(boolean visitada) {
        this.visitada = visitada;
    }

    /** Retorna true si la neurona es una zona aislada */
    public boolean isAislada() {
        return aislada;
    }

    /** Establece si la neurona es una zona aislada */
    public void setAislada(boolean aislada) {
        this.aislada = aislada;
    }
}