/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controlador;

import modelo.MetodoNewtonModelo;
import vista.MetodonNewton;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class MetodoNewtonControlador {

    private MetodonNewton vista; // La vista que este controlador maneja

    public MetodoNewtonControlador(MetodonNewton vista) {
        this.vista = vista;
    }

    public void calcularNewton() {
        // 1. Obtener datos de la vista
        String funcionStr = vista.getFuncion().getText();
        String derivadaStr = vista.getDerivado().getText();
        String xoStr = vista.getXi().getText();

        // 2. Validar entrada (básico)
        if (funcionStr.isEmpty() || funcionStr.equals("F(x)")) {
            JOptionPane.showMessageDialog(vista, "Por favor, ingrese la función f(x).", "Error de entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (derivadaStr.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, ingrese la función derivada f'(x).", "Error de entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (xoStr.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, ingrese el valor inicial (Xo).", "Error de entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double xo;
        try {
            xo = Double.parseDouble(xoStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "El valor de Xo debe ser un número válido.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Definimos la tolerancia y el número máximo de iteraciones
        // Estos podrían ser inputs de la vista también, pero por ahora los hardcodeamos.
        double tolerancia = 0.0001; // Puedes ajustar este valor o añadir un JTextField en la vista
        int maxIteraciones = 100; // Puedes ajustar este valor o añadir un JTextField en la vista

        // 3. Crear instancia del Modelo
        MetodoNewtonModelo modelo = new MetodoNewtonModelo(funcionStr, derivadaStr);

        // Verificar si las funciones son válidas
        if (!modelo.areFunctionsValid()) {
            JOptionPane.showMessageDialog(vista, "Las funciones ingresadas no son válidas. Revise la sintaxis.", "Error de Función", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 4. Ejecutar el método numérico
        List<Object[]> resultados = modelo.newtonRaphson(xo, tolerancia, maxIteraciones);

        // 5. Actualizar la vista con los resultados
        if (resultados != null) {
            DefaultTableModel tableModel = (DefaultTableModel) vista.getTabla1().getModel();
            tableModel.setRowCount(0); // Limpiar tabla antes de añadir nuevos resultados

            // Actualizar los nombres de las columnas si es necesario (ya los tienes bien definidos en la vista)
            // String[] columnNames = {"Iteración", "xi", "f(xi)", "f'(xi)", "xi+1", "Tolerancia"};
            // tableModel.setColumnIdentifiers(columnNames);

            for (Object[] fila : resultados) {
                tableModel.addRow(fila);
            }
        } else {
            // El modelo devolvió null, lo que indica un error específico
            // El modelo ya imprime mensajes de error en System.err, pero aquí podemos dar un feedback al usuario.
            JOptionPane.showMessageDialog(vista, "No se pudo calcular la raíz. Posibles problemas:\n" +
                    "- Derivada igual a cero en algún punto.\n" +
                    "- Funciones inválidas o problemas de evaluación.", "Error de Cálculo", JOptionPane.WARNING_MESSAGE);
        }
    }
}