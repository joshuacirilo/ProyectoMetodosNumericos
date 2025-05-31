/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.InterpolacionNewtonModelo;
import vista.LinealNewton;

public class InterpolacionNewtonControlador {

    private LinealNewton vista;

    public InterpolacionNewtonControlador(LinealNewton vista) {
        this.vista = vista;
    }

    public void calcularInterpolacion() {
        try {
            // Obtener valores ingresados desde la vista
            double x1 = Double.parseDouble(vista.getvalorx1().getText());
            double y1 = Double.parseDouble(vista.getvalory1().getText());
            double x2 = Double.parseDouble(vista.getvalorx2().getText());
            double y2 = Double.parseDouble(vista.getvalory2().getText());
            double x_interpolar = Double.parseDouble(vista.getvalorx().getText());

            // Crear modelo con dos puntos
            double[] xValues = {x1, x2};
            double[] yValues = {y1, y2};

            InterpolacionNewtonModelo modelo = new InterpolacionNewtonModelo(xValues, yValues);
            double resultado = modelo.interpolar(x_interpolar);

            // Mostrar el resultado en la vista
            vista.getresultado().setText(String.valueOf(resultado));

        } catch (NumberFormatException e) {
            vista.getresultado().setText("Error: Entrada inválida.");
        } catch (IllegalArgumentException e) {
            vista.getresultado().setText("Error: " + e.getMessage());
        } catch (Exception e) {
            vista.getresultado().setText("Error inesperado.");
        }
    }
}
