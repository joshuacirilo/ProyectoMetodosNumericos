/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author moralesjs_
 */
public class MetodoSusticucionModelo {
    
      public double[] resolverSistema(double a1, double b1, double c1, double a2, double b2, double c2) {
        double x, y;

        // Despejamos Y de la primera ecuación: a1 * x + b1 * y = c1
        // y = (c1 - a1 * x) / b1 → sustituimos en segunda ecuación
        if (b1 != 0) {
            // y = (c1 - a1 * x) / b1
            // Sustituimos en segunda ecuación: a2 * x + b2 * [(c1 - a1 * x) / b1] = c2
            // → resolvemos para x
            double numerador = (c2 * b1) - (b2 * c1);
            double denominador = (a2 * b1) - (b2 * a1);

            x = numerador / denominador;
            y = (c1 - a1 * x) / b1;
        } else if (a1 != 0) {
            // x = (c1 - b1 * y) / a1 → sustituimos en segunda ecuación
            double numerador = (c2 * a1) - (a2 * c1);
            double denominador = (b2 * a1) - (a2 * b1);

            y = numerador / denominador;
            x = (c1 - b1 * y) / a1;
        } else {
            throw new ArithmeticException("No se puede resolver con sustitución.");
        }

        return new double[]{x, y};
    }
}
