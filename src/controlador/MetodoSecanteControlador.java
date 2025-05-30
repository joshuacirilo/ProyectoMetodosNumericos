/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controlador;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.MetodoSecanteModelo;
import vista.Secante;

public class MetodoSecanteControlador {

    private Secante vista;
    private MetodoSecanteModelo modelo;

    public MetodoSecanteControlador(Secante vista) {
        this.vista = vista;
    }

    public void calcularSecante() {
        String funcion = vista.getFuncion().getText();
        String x0Str = vista.getXi1().getText(); // xi-1
        String x1Str = vista.getXi().getText();  // xi
        double x0, x1;

        // Validación de números
        try {
            x0 = Double.parseDouble(x0Str);
            x1 = Double.parseDouble(x1Str);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Xi y Xi-1 deben ser números válidos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear modelo con función
        modelo = new MetodoSecanteModelo(funcion);
        if (!modelo.isFunctionValid()) {
            JOptionPane.showMessageDialog(vista, "La función ingresada no es válida.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Ejecutar método secante
        List<Object[]> resultados = modelo.secante(x0, x1, 0.0001, 50); // tolerancia fija y 50 iteraciones

        if (resultados == null || resultados.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No se pudo calcular el método de la secante.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Limpiar tabla y llenar con resultados
        DefaultTableModel model = (DefaultTableModel) vista.getTabla1().getModel();
        model.setRowCount(0);

        for (Object[] fila : resultados) {
            model.addRow(fila);
        }
    }
}
