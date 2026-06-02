/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 
/**
 * . Inicializa el Singleton App y lanza la interfaz grafica
 * @author Ariadna Lechin
 */
public class Main {
 
    /** Metodo principal. Inicializa App y lanza la interfaz grafica.
     */
    public static void main(String[] args) {
        // Se inicializa el Singleton, que carga los CSV preestablecidos
        App.getInstance();
    }
}