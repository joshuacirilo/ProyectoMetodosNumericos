/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Clase que implementa el método de Interpolación de Newton (Diferencias Divididas).
 * Forma parte de la capa Modelo en un diseño MVC.
 */
public class InterpolacionNewtonModelo {

    private double[] xValues;
    private double[] yValues;
    private double[][] dividedDifferencesTable; // Tabla para almacenar las diferencias divididas

    /**
     * Constructor para inicializar el modelo con los puntos de datos conocidos.
     * Se construye la tabla de diferencias divididas al momento de la inicialización.
     * @param xValues Un arreglo de coordenadas X de los puntos de datos.
     * @param yValues Un arreglo de coordenadas Y (valores de la función) de los puntos de datos.
     * @throws IllegalArgumentException Si los arreglos de x y y son de diferente tamaño, nulos o vacíos,
     * o si hay puntos X duplicados.
     */
    public InterpolacionNewtonModelo(double[] xValues, double[] yValues) {
        if (xValues == null || yValues == null || xValues.length == 0 || yValues.length == 0) {
            throw new IllegalArgumentException("Los arreglos de puntos no pueden ser nulos o vacíos.");
        }
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException("Los arreglos de x y y deben tener el mismo tamaño.");
        }

        this.xValues = Arrays.copyOf(xValues, xValues.length); // Copia para evitar modificaciones externas
        this.yValues = Arrays.copyOf(yValues, yValues.length); // Copia para evitar modificaciones externas

        // Validar que no haya puntos X duplicados
        for (int i = 0; i < xValues.length; i++) {
            for (int j = i + 1; j < xValues.length; j++) {
                if (Math.abs(xValues[i] - xValues[j]) < 1e-9) { // Tolerancia para doubles
                    throw new IllegalArgumentException("Puntos X duplicados encontrados. La interpolación de Newton (y Lagrange) requiere puntos X distintos.");
                }
            }
        }

        // Construir la tabla de diferencias divididas al crear el modelo
        buildDividedDifferencesTable();
    }

    /**
     * Construye la tabla de diferencias divididas que se utilizará para evaluar el polinomio.
     * La tabla es triangular superior.
     * La primera columna (index 0) contiene los f[x_i] = y_i.
     * La diagonal principal (f[x_0, ..., x_k]) contiene los coeficientes para el polinomio de Newton.
     */
    private void buildDividedDifferencesTable() {
        int n = xValues.length;
        // La tabla será (n x n) para almacenar todas las diferencias
        dividedDifferencesTable = new double[n][n];

        // Rellenar la primera columna con los valores de y (diferencias de orden 0)
        for (int i = 0; i < n; i++) {
            dividedDifferencesTable[i][0] = yValues[i];
        }

        // Calcular las diferencias divididas de orden superior
        for (int j = 1; j < n; j++) { // j representa el orden de la diferencia
            for (int i = 0; i < n - j; i++) { // i representa la fila de inicio
                dividedDifferencesTable[i][j] =
                    (dividedDifferencesTable[i + 1][j - 1] - dividedDifferencesTable[i][j - 1]) /
                    (xValues[i + j] - xValues[i]);
            }
        }
    }

    /**
     * Evalúa el polinomio de interpolación de Newton en un punto 'x_interpolar' dado.
     * Utiliza la tabla de diferencias divididas previamente construida.
     * @param x_interpolar El valor de x en el que se desea interpolar.
     * @return El valor interpolado y en el punto x_interpolar.
     */
    public double interpolar(double x_interpolar) {
        int n = xValues.length;
        double resultado = dividedDifferencesTable[0][0]; // f[x0] (primer término)
        double productTerm = 1.0; // (x - x0), (x - x0)(x - x1), etc.

        // Sumar los términos del polinomio de Newton
        // P(x) = f[x0] + f[x0,x1](x-x0) + f[x0,x1,x2](x-x0)(x-x1) + ...
        for (int i = 1; i < n; i++) {
            productTerm *= (x_interpolar - xValues[i - 1]); // Actualiza el término del producto
            resultado += dividedDifferencesTable[0][i] * productTerm; // Suma (f[x0,...,xi] * producto)
        }
        return resultado;
    }

    /**
     * Opcional: Devuelve los puntos de datos originales.
     * @return Un List de Object[] donde cada Object[] es {x, y}.
     */
    public List<Object[]> obtenerPuntosDeDatos() {
        List<Object[]> puntos = new ArrayList<>();
        for (int i = 0; i < xValues.length; i++) {
            puntos.add(new Object[]{xValues[i], yValues[i]});
        }
        return puntos;
    }

    /**
     * Opcional: Devuelve la tabla de diferencias divididas para visualización (útil para la Vista).
     * Esto puede ser útil para depuración o para mostrar al usuario cómo se construye el polinomio.
     * @return Una copia de la tabla de diferencias divididas.
     */
    public double[][] obtenerTablaDiferenciasDivididas() {
        // Devuelve una copia para que la tabla interna no sea modificada externamente
        double[][] copy = new double[dividedDifferencesTable.length][];
        for (int i = 0; i < dividedDifferencesTable.length; i++) {
            copy[i] = Arrays.copyOf(dividedDifferencesTable[i], dividedDifferencesTable[i].length);
        }
        return copy;
    }
}