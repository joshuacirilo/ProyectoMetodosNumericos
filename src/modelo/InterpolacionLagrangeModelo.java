/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Modelo que implementa el método de Interpolación de Lagrange para múltiples puntos.
 */
public class InterpolacionLagrangeModelo {

    private final double[] xValues;
    private final double[] yValues;

    /**
     * Inicializa el modelo con los puntos (x, y) conocidos.
     *
     * @param xValues Arreglo con valores de X.
     * @param yValues Arreglo con valores de Y correspondientes a cada X.
     * @throws IllegalArgumentException Si los arreglos son nulos, vacíos o de distinta longitud.
     */
    public InterpolacionLagrangeModelo(double[] xValues, double[] yValues) {
        if (xValues == null || yValues == null || xValues.length == 0 || yValues.length == 0) {
            throw new IllegalArgumentException("Los arreglos de puntos no pueden ser nulos o vacíos.");
        }
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException("Los arreglos de X y Y deben tener el mismo tamaño.");
        }

        this.xValues = xValues;
        this.yValues = yValues;
    }

    /**
     * Realiza la interpolación de Lagrange para un valor dado de X.
     *
     * @param x Valor en el cual se desea interpolar.
     * @return Valor interpolado de Y.
     */
    public double interpolar(double x) {
        double resultado = 0.0;
        int n = xValues.length;

        for (int i = 0; i < n; i++) {
            double termino = yValues[i];
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    double denominador = xValues[i] - xValues[j];
                    if (Math.abs(denominador) < 1e-9) {
                        System.err.println("Error: Hay puntos X duplicados. No se puede calcular la interpolación.");
                        return Double.NaN;
                    }
                    termino *= (x - xValues[j]) / denominador;
                }
            }
            resultado += termino;
        }

        return resultado;
    }

    /**
     * Devuelve los puntos usados para la interpolación en forma de lista.
     *
     * @return Lista de Object[] donde cada uno contiene {x, y}.
     */
    public List<Object[]> obtenerPuntosDeDatos() {
        List<Object[]> puntos = new ArrayList<>();
        for (int i = 0; i < xValues.length; i++) {
            puntos.add(new Object[]{xValues[i], yValues[i]});
        }
        return puntos;
    }
}
