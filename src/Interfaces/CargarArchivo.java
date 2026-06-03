/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Interfaces;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/** clase frontera que maneja la selección de archivos csv con JFileChooser
 * @author e miquilareno 2
 */
public class CargarArchivo {

    /** Abre el JFileChooser y retorna la ruta del archivo seleccionado, null si el usuario cancela */
    public String seleccionarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar archivo CSV");
        // Solo muestra archivos CSV
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos CSV", "csv"));

        int resultado = fileChooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        // El usuario cancelo
        return null;
    }
}
