/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.MetodoReglaFalsaModelo;
import vista.ReglaFalsa;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MetodoReglaFalsaControlador {

    private ReglaFalsa vista;

    public MetodoReglaFalsaControlador(ReglaFalsa vista) {
        this.vista = vista;
    }

    public void calcularReglaFalsa() {
        String funcionTexto = vista.getFuncion().getText();
        String aTexto = vista.getIntervalo1().getText();
        String bTexto = vista.getIntervalo2().getText();

        // Validación de campos vacíos
        if (funcionTexto.isEmpty() || aTexto.isEmpty() || bTexto.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.");
            return;
        }
        String funcion;
        double a, b;
        try {
            funcion = funcionTexto;
            a = Double.parseDouble(aTexto);
            b = Double.parseDouble(bTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Los intervalos deben ser números válidos.");
            return;
        }

        MetodoReglaFalsaModelo modelo = new MetodoReglaFalsaModelo();
        List<Object[]> resultados = modelo.calcularReglaFalsa(funcion,a, b);

        if (resultados == null || resultados.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No se pudo calcular la raíz. Verifique la función o los intervalos.");
            return;
        }

        // Mostrar resultados en la tabla
        DefaultTableModel tabla = (DefaultTableModel) vista.getTabla1().getModel();
        tabla.setRowCount(0); // Limpiar tabla

        for (Object[] fila : resultados) {
            tabla.addRow(fila);
        }
    }
}
