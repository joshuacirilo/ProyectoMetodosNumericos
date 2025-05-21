/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author luisd
 */
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import modelo.MetodoBiseccionModelo;
import vista.Biseccion;

public class MetodoBiseccionControlador {

    private Biseccion vista;

    public MetodoBiseccionControlador(Biseccion vista) {
        this.vista = vista;
    }

    public void calcularBiseccion() {
        // 1. Obtener los datos de la vista
        String funcionStr = vista.getFuncion().getText();
        String toleranciaStr = vista.getTolerancia().getText(); // Asumimos que es fija a 0.0001
        String intervaloAStr = vista.getIntervalos().getText();
        String intervaloBStr = vista.getIntervalos1().getText();

        // 2. Validar que los campos obligatorios no estén vacíos
        if (funcionStr.isEmpty() || intervaloAStr.isEmpty() || intervaloBStr.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Los campos de Función e Intervalos son obligatorios.", "Error de entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Parsear los datos numéricos
        double a, b, tolerancia;
        try {
            tolerancia = Double.parseDouble(toleranciaStr);
            a = Double.parseDouble(intervaloAStr);
            b = Double.parseDouble(intervaloBStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "Asegúrate de que la tolerancia y los intervalos sean números válidos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int maxIteraciones = 1000;

        // 4. Crear el modelo e intentar validar la función
        MetodoBiseccionModelo modelo = new MetodoBiseccionModelo(funcionStr);

        // <--- ¡IMPORTANTE! Validar si la función se construyó correctamente
        if (!modelo.isFunctionValid()) {
            JOptionPane.showMessageDialog(vista,
                                          "La función ingresada no es válida. Por favor, revisa la sintaxis." +
                                          "\nEjemplos: 'x*x - 4', 'sin(x)', 'exp(x)', 'log(x)'." +
                                          "\nUsa '*' para multiplicación, '^' para potencias." +
                                          "\nVariable debe ser 'x'.",
                                          "Error de Función",
                                          JOptionPane.ERROR_MESSAGE);
            DefaultTableModel model = (DefaultTableModel) vista.getTabla1().getModel();
            model.setRowCount(0); // Limpiar la tabla si hay un error
            return;
        }

        // 5. Ejecutar el método de bisección y manejar posibles errores durante el cálculo
        List<Object[]> resultados = null;
        try {
            resultados = modelo.biseccion(a, b, tolerancia, maxIteraciones);
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(vista,
                                          "Error durante el cálculo de la bisección: " + e.getMessage(),
                                          "Error de Cálculo",
                                          JOptionPane.ERROR_MESSAGE);
            DefaultTableModel model = (DefaultTableModel) vista.getTabla1().getModel();
            model.setRowCount(0);
            return;
        }


        // 6. Mostrar los resultados o un mensaje de error si no se encontró la raíz
        if (resultados == null) {
            JOptionPane.showMessageDialog(vista,
                                          "No se pudo encontrar una raíz en el intervalo dado. " +
                                          "Asegúrate de que f(a) y f(b) tengan signos opuestos.",
                                          "Error de Bisección",
                                          JOptionPane.WARNING_MESSAGE);
            DefaultTableModel model = (DefaultTableModel) vista.getTabla1().getModel();
            model.setRowCount(0);
        } else {
            DefaultTableModel model = (DefaultTableModel) vista.getTabla1().getModel();
            model.setRowCount(0); // Limpiar la tabla antes de añadir nuevos resultados

            for (Object[] fila : resultados) {
                model.addRow(fila);
            }
        }
    }
}