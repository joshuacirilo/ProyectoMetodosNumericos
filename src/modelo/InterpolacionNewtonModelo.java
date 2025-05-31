/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Arrays;

/**
 * Clase que implementa el método de Interpolación de Newton (Diferencias Divididas).
 * Adaptado para usarse sin tabla en la vista.
 */
public class InterpolacionNewtonModelo {

    private double[] xValues;
    private double[] yValues;
    private double[] coeficientes; // Solo guardamos la diagonal principal (coeficientes del polinomio)

    /**
     * Constructor para inicializar el modelo con los puntos de datos conocidos.
     * Se calculan los coeficientes del polinomio de Newton al inicializar.
     *
     * @param xValues Arreglo con los valores de X.
     * @param yValues Arreglo con los valores de Y.
     */
    public InterpolacionNewtonModelo(double[] xValues, double[] yValues) {
        if (xValues == null || yValues == null || xValues.length == 0 || yValues.length == 0) {
            throw new IllegalArgumentException("Los arreglos de puntos no pueden ser nulos o vacíos.");
        }
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException("Los arreglos de x y y deben tener el mismo tamaño.");
        }

        // Validar que no existan duplicados en X
        for (int i = 0; i < xValues.length; i++) {
            for (int j = i + 1; j < xValues.length; j++) {
                if (Math.abs(xValues[i] - xValues[j]) < 1e-9) {
                    throw new IllegalArgumentException("Puntos X duplicados encontrados.");
                }
            }
        }

        this.xValues = Arrays.copyOf(xValues, xValues.length);
        this.yValues = Arrays.copyOf(yValues, yValues.length);
        this.coeficientes = calcularCoeficientes();
    }

    /**
     * Calcula los coeficientes del polinomio de Newton usando diferencias divididas.
     *
     * @return Arreglo con los coeficientes.
     */
    private double[] calcularCoeficientes() {
        int n = xValues.length;
        double[] coef = Arrays.copyOf(yValues, n);

        for (int j = 1; j < n; j++) {
            for (int i = n - 1; i >= j; i--) {
                coef[i] = (coef[i] - coef[i - 1]) / (xValues[i] - xValues[i - j]);
            }
        }

        return coef;
    }

    /**
     * Evalúa el polinomio de Newton en el valor dado.
     *
     * @param x Valor donde se desea interpolar.
     * @return Valor interpolado.
     */
    public double interpolar(double x) {
        int n = coeficientes.length;
        double resultado = coeficientes[n - 1];

        for (int i = n - 2; i >= 0; i--) {
            resultado = resultado * (x - xValues[i]) + coeficientes[i];
        }

        return resultado;
    }
}
