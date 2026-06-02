/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;


/** Clase Neurotransmisor
 *  @author Diego Di Lima
 */
public class Neurotransmisor {

    /** Identificador unico del neurotransmisor*/
    private String id;

    /** Nombre completo del neurotransmisor */
    private String nombre;

    /** Efecto del neurotransmisor: Excitatorio, Inhibitorio o Modulador */
    private String efecto;

    /** Factor de velocidad de transmision sináptica, usado en el calculo del peso W = d / (v * k) */
    private double velocidad;

    /** Descripcion breve del rol biologico del neurotransmisor */
    private String descripcion;

    /** Constructor que inicializa todos los atributos del neurotransmisor */
    public Neurotransmisor(String id, String nombre, String efecto, double velocidad, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.efecto = efecto;
        this.velocidad = velocidad;
        this.descripcion = descripcion;
    }

    /** Retorna el ID unico del neurotransmisor */
    public String getId() {
        return id;
    }

    /** Establece el ID del neurotransmisor */
    public void setId(String id) {
        this.id = id;
    }

    /** Retorna el nombre completo del neurotransmisor */
    public String getNombre() {
        return nombre;
    }

    /** Establece el nombre del neurotransmisor */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** Retorna el efecto del neurotransmisor (Excitatorio, Inhibitorio, Modulador) */
    public String getEfecto() {
        return efecto;
    }

    /** Establece el efecto del neurotransmisor */
    public void setEfecto(String efecto) {
        this.efecto = efecto;
    }

    /** Retorna la velocidad de transmision del neurotransmisor */
    public double getVelocidad() {
        return velocidad;
    }

    /** Establece la velocidad de transmision */
    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    /** Retorna la descripcion breve del neurotransmisor */
    public String getDescripcion() {
        return descripcion;
    }

    /** Establece la descripcion del neurotransmisor */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}