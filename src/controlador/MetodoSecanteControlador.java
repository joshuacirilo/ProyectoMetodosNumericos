/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controlador;

import modelo.MetodoSecanteModelo;
import vista.Secante; // Asegúrate de que el nombre de la vista sea correcto y esté en el paquete 'vista'

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class MetodoSecanteControlador {

    private Secante vista;

    /**
     * Constructor del controlador que recibe la instancia de la vista.
     * Esto permite que el controlador interactúe con los componentes de la interfaz de usuario.
     * @param vista La instancia de la ventana del Método Secante.
     */
    public MetodoSecanteControlador(Secante vista) {
        this.vista = vista;
    }

    /**
     * Este método se encarga de orquestar el cálculo del Método de la Secante.
     * 1. Obtiene los datos de la interfaz de usuario.
     * 2. Realiza validaciones de entrada.
     * 3. Llama al modelo para ejecutar el algoritmo.
     * 4. Actualiza la tabla de la vista con los resultados o muestra mensajes de error.
     */
    public void calcularSecante() {
        // 1. Obtener datos de la vista usando los getters específicos para tus JTextFields
        String funcionStr = vista.getFuncion().getText();
        String xi1Str = vista.getXi1().getText(); // Lee el valor de Xi-1
        String xiStr = vista.getXi().getText();   // Lee el valor de Xi

        // Validamos que los campos de Xo y Xi1 NO se usen para el cálculo de Secante,
        // ya que el método solo necesita dos puntos iniciales (xi-1 y xi)
        // Si tu diseño requiere Xo para alguna otra cosa, ignora esta validación.
        // String xoStr = vista.getXo().getText(); // Lo leemos, pero no lo usamos para el cálculo del modelo Secante

        // 2. Validar entrada (básico pero esencial)
        if (funcionStr.isEmpty() || funcionStr.equals("F(x)")) {
            JOptionPane.showMessageDialog(vista, "Por favor, ingrese la función f(x).", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (xi1Str.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, ingrese el valor inicial para Xi-1.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (xiStr.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Por favor, ingrese el valor inicial para Xi.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double xi_minus_1;
        double xi_current;
        try {
            xi_minus_1 = Double.parseDouble(xi1Str);
            xi_current = Double.parseDouble(xiStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "Los valores iniciales (Xi-1, Xi) deben ser números válidos.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Definimos la tolerancia y el número máximo de iteraciones.
        // Es una buena práctica permitir al usuario configurar estos valores a través de JTextFields en la vista.
        // Por ahora, los dejamos hardcodeados como ejemplo.
        double tolerancia = 0.0001; // Tolerancia por defecto para el error
        int maxIteraciones = 100;   // Máximo de iteraciones para evitar bucles infinitos

        // 3. Crear instancia del Modelo, pasándole la función ingresada por el usuario
        MetodoSecanteModelo modelo = new MetodoSecanteModelo(funcionStr);

        // 4. Ejecutar el método numérico llamando al modelo
        // Pasamos los dos puntos iniciales obtenidos de la vista (Xi-1 y Xi).
        List<Object[]> resultados = modelo.secante(xi_minus_1, xi_current, tolerancia, maxIteraciones);

        // 5. Actualizar la vista con los resultados o mostrar mensajes de error
        DefaultTableModel tableModel = (DefaultTableModel) vista.getTabla1().getModel();
        tableModel.setRowCount(0); // Limpiar la tabla completamente antes de añadir nuevos resultados

        if (resultados != null && !resultados.isEmpty()) {
            // Si el modelo devolvió resultados válidos, los añadimos a la tabla
            for (Object[] fila : resultados) {
                tableModel.addRow(fila);
            }
        } else {
            // Si el modelo devolvió null o una lista vacía, significa que hubo un problema
            // El modelo ya imprime mensajes de error en la consola (System.err),
            // pero también informamos al usuario a través de un diálogo.
            JOptionPane.showMessageDialog(vista, "No se pudo calcular la raíz. Por favor, revise:\n" +
                    "- La sintaxis de la función f(x).\n" +
                    "- Los valores iniciales (Xi-1, Xi) podrían no ser adecuados o causar división por cero.\n" +
                    "- El método puede haber divergido o no converger en el número de iteraciones dadas.",
                    "Error de Cálculo", JOptionPane.WARNING_MESSAGE);
        }
    }
}