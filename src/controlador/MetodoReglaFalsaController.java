/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.MetodoReglaFalsaModelo;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MetodoReglaFalsaController {

    public void operar(String funcion, double a, double b, JTable tablaResultados) {
        MetodoReglaFalsaModelo modelo = new MetodoReglaFalsaModelo(funcion);
        List<Object[]> resultados = modelo.reglaFalsa(a, b);

        if (resultados == null) {
            // Manejar el error de signos iguales
            javax.swing.JOptionPane.showMessageDialog(null, "Error: f(a) y f(b) tienen el mismo signo. No se puede aplicar la Regla Falsa.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            DefaultTableModel tableModel = (DefaultTableModel) tablaResultados.getModel();
            tableModel.setRowCount(0); // Limpiar la tabla
            return;
        }

        if (resultados != null && !resultados.isEmpty()) {
            // Crear el modelo de la tabla
            DefaultTableModel tableModel = new DefaultTableModel();
            tableModel.setColumnIdentifiers(new Object[]{"Iteración", "a", "b", "f(a)", "f(b)", "xr", "f(xr)", "Tolerancia"});

            // Llenar la tabla con los resultados
            for (Object[] fila : resultados) {
                tableModel.addRow(fila);
            }

            // Asignar el modelo a la tabla
            tablaResultados.setModel(tableModel);
        } else if (resultados != null && resultados.isEmpty()) {
            // La convergencia no se alcanzó dentro del límite de iteraciones o tolerancia
            javax.swing.JOptionPane.showMessageDialog(null, "El método no convergió dentro del límite de iteraciones o tolerancia.", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
            DefaultTableModel tableModel = (DefaultTableModel) tablaResultados.getModel();
            tableModel.setRowCount(0); // Limpiar la tabla
        } else {
            // Esto podría ocurrir si hubo un error en la evaluación de la función
            javax.swing.JOptionPane.showMessageDialog(null, "Error al procesar la función.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            DefaultTableModel tableModel = (DefaultTableModel) tablaResultados.getModel();
            tableModel.setRowCount(0); // Limpiar la tabla
        }
    }
}