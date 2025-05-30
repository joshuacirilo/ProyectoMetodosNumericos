/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.MetodoMullerModelo;
import vista.MetodoMuller; // Asegúrate de que esta importación coincida con tu paquete

public class MetodoMullerControlador {

    private MetodoMuller vista;

    public MetodoMullerControlador(MetodoMuller vista) {
        this.vista = vista;
    }

    public void calcularMetodoMuller() {
        try {
            String funcion = vista.getFuncion().getText();
            double x0 = Double.parseDouble(vista.getx0().getText());
            double x1 = Double.parseDouble(vista.getx1().getText());
            double x2 = Double.parseDouble(vista.getx2().getText());

            double tolerancia = 0.0001; // Puedes ponerlo como entrada también si quieres
            int maxIteraciones = 100;

            MetodoMullerModelo modelo = new MetodoMullerModelo(funcion);
            List<Object[]> resultados = modelo.muller(x0, x1, x2, tolerancia, maxIteraciones);

            JTable tabla = vista.getTabla1();
            DefaultTableModel tableModel = (DefaultTableModel) tabla.getModel();
            tableModel.setRowCount(0); // Limpiar tabla

            for (Object[] fila : resultados) {
                tableModel.addRow(fila);
            }

        } catch (NumberFormatException ex) {
            System.err.println("Error en la conversión numérica: " + ex.getMessage());
        } catch (Exception ex) {
            System.err.println("Error en el cálculo: " + ex.getMessage());
        }
    }
}
