/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import modelo.MetodoSusticucionModelo;
import vista.Sustitucion;
import javax.swing.JOptionPane;

/**
 *
 * @author moralesjs_
 */
public class MetodoSustitucionControlador {
      private MetodoSusticucionModelo modelo;
    private Sustitucion vista;

    public MetodoSustitucionControlador(MetodoSusticucionModelo modelo, Sustitucion vista) {
        this.modelo = modelo;
        this.vista = vista;

        // Asignar acción al botón Calcular
        this.vista.getBtnCalcular().addActionListener(e -> {
            try {
                double a1 = Double.parseDouble(vista.getTxtA1().getText());
                double b1 = Double.parseDouble(vista.getTxtB1().getText());
                double c1 = Double.parseDouble(vista.getTxtC1().getText());
                double a2 = Double.parseDouble(vista.getTxtA2().getText());
                double b2 = Double.parseDouble(vista.getTxtB2().getText());
                double c2 = Double.parseDouble(vista.getTxtC2().getText());

                double[] resultado = modelo.resolverSistema(a1, b1, c1, a2, b2, c2);

                // Mostrar resultado en el área de texto
                vista.getResultadoArea().setText("x = " + resultado[0] + "\ny = " + resultado[1]);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Por favor ingresa solo números válidos.");
            } catch (ArithmeticException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });
    }
}
