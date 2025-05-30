/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.MetodoReglaFalsaModelo;
import vista.ReglaFalsa;

public class MetodoReglaFalsaControlador {
    
    private final ReglaFalsa vista;

    public MetodoReglaFalsaControlador(ReglaFalsa vista) {
        this.vista = vista;
    }

    public void calcularReglaFalsa() {
        try {
            String funcion = vista.getFuncion().getText().trim();
            double a = Double.parseDouble(vista.getIntervalo1().getText().trim());
            double b = Double.parseDouble(vista.getIntervalo2().getText().trim());

            MetodoReglaFalsaModelo modelo = new MetodoReglaFalsaModelo(funcion);
            List<Object[]> resultados = modelo.reglaFalsa(a, b);

            if (resultados == null) {
                JOptionPane.showMessageDialog(vista, "Error: No se pudo evaluar la función o los intervalos no cumplen los requisitos.");
                return;
            }

            DefaultTableModel tableModel = (DefaultTableModel) vista.getTabla1().getModel();
            tableModel.setRowCount(0); // Limpiar tabla antes de agregar resultados

            for (Object[] fila : resultados) {
                tableModel.addRow(fila);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "Error: Los intervalos deben ser números válidos.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error inesperado: " + e.getMessage());
        }
    }
}
