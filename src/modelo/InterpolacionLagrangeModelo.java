/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que implementa el método de Interpolación de Lagrange.
 * Forma parte de la capa Modelo en un diseño MVC.
 */
public class InterpolacionLagrangeModelo {

    private double[] xValues;
    private double[] yValues;

    /**
     * Constructor para inicializar el modelo con los puntos de datos conocidos.
     * @param xValues Un arreglo de coordenadas X de los puntos de datos.
     * @param yValues Un arreglo de coordenadas Y (valores de la función) de los puntos de datos.
     * @throws IllegalArgumentException Si los arreglos de x y y son de diferente tamaño, nulos o vacíos.
     */
    public InterpolacionLagrangeModelo(double[] xValues, double[] yValues) {
        if (xValues == null || yValues == null || xValues.length == 0 || yValues.length == 0) {
            throw new IllegalArgumentException("Los arreglos de puntos no pueden ser nulos o vacíos.");
        }
        if (xValues.length != yValues.length) {
            throw new IllegalArgumentException("Los arreglos de x y y deben tener el mismo tamaño.");
        }
        this.xValues = xValues;
        this.yValues = yValues;
    }

    /**
     * Evalúa el polinomio de interpolación de Lagrange en un punto 'x' dado.
     * @param x_interpolar El valor de x en el que se desea interpolar.
     * @return El valor interpolado y en el punto x_interpolar.
     */
    public double interpolar(double x_interpolar) {
        int n = xValues.length; // Número de puntos de datos (n)
        double resultado = 0.0;

        // Bucle principal para calcular la suma de L_k(x) * y_k
        for (int k = 0; k < n; k++) {
            double Lk_x = 1.0; // Inicializamos el término L_k(x) para el punto k

            // Bucle para calcular el producto de los términos (x - x_j) / (x_k - x_j)
            for (int j = 0; j < n; j++) {
                if (k != j) { // Excluimos el término cuando j = k
                    // Verificar si hay puntos x duplicados, lo que causaría división por cero
                    if (Math.abs(xValues[k] - xValues[j]) < 1e-9) { // Usamos una tolerancia para comparar doubles
                        // Esto indica que hay valores de x duplicados en los puntos de datos,
                        // lo cual hace que el método de Lagrange sea inviable directamente.
                        // Podrías lanzar una excepción o manejarlo de otra manera.
                        System.err.println("Error: Puntos X duplicados encontrados. La interpolación de Lagrange requiere puntos X distintos.");
                        return Double.NaN; // Retorna NaN para indicar un error
                    }
                    Lk_x *= (x_interpolar - xValues[j]) / (xValues[k] - xValues[j]);
                }
            }
            resultado += Lk_x * yValues[k]; // Suma el término completo (L_k(x) * y_k)
        }
        return resultado;
    }

    /**
     * Opcional: Podríamos querer devolver los puntos de datos para mostrarlos en la vista.
     * @return Un List de Object[] donde cada Object[] es {x, y}.
     */
    public List<Object[]> obtenerPuntosDeDatos() {
        List<Object[]> puntos = new ArrayList<>();
        for (int i = 0; i < xValues.length; i++) {
            puntos.add(new Object[]{xValues[i], yValues[i]});
        }
        return puntos;
    }
}