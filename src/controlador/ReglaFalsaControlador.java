/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.MetodoReglaFalsaModelo;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vista.ReglaFalsa;

public class ReglaFalsaControlador {

    private ReglaFalsa vista; // Referencia a la vista
    private MetodoReglaFalsaModelo modelo; // Referencia al modelo

    public ReglaFalsaControlador(ReglaFalsa vista) {
        this.vista = vista;
    }

    public void calcularReglaFalsa() {
        // 1. Obtener datos de la vista
        String funcionStr = vista.getFuncion().getText();
//        String toleranciaStr = vista.getTolerancia().getText();
        String intervaloAStr = vista.getIntervalo1().getText();
        String intervaloBStr = vista.getIntervalo2().getText();

        // 2. Validar datos
        if (funcionStr.isEmpty() || intervaloAStr.isEmpty() || intervaloBStr.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            double a = Double.parseDouble(intervaloAStr);
            double b = Double.parseDouble(intervaloBStr);
//            double tolerancia = Double.parseDouble(toleranciaStr);
//
//            if (tolerancia <= 0) {
//                 JOptionPane.showMessageDialog(vista, "La tolerancia debe ser un valor positivo.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
//                 return;
//            }

            // 3. Instanciar el modelo y realizar el cálculo
            modelo = new MetodoReglaFalsaModelo(funcionStr);
            List<Object[]> resultados = modelo.reglaFalsa(a, b); // Pasa la tolerancia

            // 4. Mostrar resultados o errores
            if (resultados != null && !resultados.isEmpty()) {
                DefaultTableModel tableModel = (DefaultTableModel) vista.getTabla1().getModel();
                tableModel.setRowCount(0); // Limpiar la tabla antes de agregar nuevos resultados
                for (Object[] row : resultados) {
                    tableModel.addRow(row);
                }
            } else if (resultados == null) {
                // El modelo ya imprimió un error en consola, aquí solo mostramos un mensaje general
                JOptionPane.showMessageDialog(vista, "No se pudo aplicar el método o hubo un error al evaluar la función. Revise los intervalos o la sintaxis de la función.", "Error de Cálculo", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vista, "El método no encontró una raíz en el número máximo de iteraciones o con la tolerancia dada.", "Resultado", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "Por favor, ingrese valores numéricos válidos para la tolerancia y los intervalos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}