/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MetodoSecanteModelo {
    private String funcion;
    private ScriptEngine engine; // Reutilizar el ScriptEngine

    /**
     * Constructor para inicializar el modelo con la función a evaluar.
     * Se inicializa el ScriptEngine aquí para mejorar el rendimiento al reutilizarlo.
     * @param funcion La función matemática como una cadena de texto (ej. "x^2 - 4").
     */
    public MetodoSecanteModelo(String funcion) {
        this.funcion = funcion;
        // Inicializar el ScriptEngine una sola vez
        ScriptEngineManager manager = new ScriptEngineManager();
        this.engine = manager.getEngineByName("JavaScript");
        if (this.engine == null) {
            System.err.println("Error: El motor de JavaScript no está disponible. Asegúrate de usar un JRE completo.");
        }
    }

    /**
     * Aplica el método de la Secante para encontrar una raíz de la función.
     * @param x0 El primer valor inicial de aproximación.
     * @param x1 El segundo valor inicial de aproximación.
     * @param toleranciaCriterio La tolerancia o criterio de parada para el error de aproximación y el valor de f(xn).
     * @param maxIteraciones El número máximo de iteraciones para evitar bucles infinitos.
     * @return Una lista de arreglos de Object, donde cada arreglo representa una fila de resultados de la iteración.
     * Retorna null si ocurre un error durante la evaluación de la función.
     */
    public List<Object[]> secante(double x0, double x1, double toleranciaCriterio, int maxIteraciones) {
        List<Object[]> resultados = new ArrayList<>();

        double xi_anterior = x0; // x(i-1)
        double xi_actual = x1;   // x(i)
        double f_xi_anterior;    // f(x(i-1))
        double f_xi_actual;      // f(x(i))
        double xi_siguiente;     // x(i+1)

        double errorAproximacion = Double.MAX_VALUE; // Inicializar con un valor grande
        int iteracion = 1;

        // Bucle principal del método de la Secante
        while (Math.abs(errorAproximacion) > toleranciaCriterio && iteracion <= maxIteraciones) {
            f_xi_anterior = evaluarFuncion(xi_anterior);
            f_xi_actual = evaluarFuncion(xi_actual);

            // Manejo de errores durante la evaluación
            if (Double.isNaN(f_xi_anterior) || Double.isNaN(f_xi_actual)) {
                System.out.println("Error: No se pudo evaluar la función en x = " + String.format("%.6f", xi_anterior) +
                                   " o en x = " + String.format("%.6f", xi_actual));
                return null;
            }

            // Evitar división por cero o un denominador muy pequeño
            if (Math.abs(f_xi_actual - f_xi_anterior) < 1e-9) { // Un valor pequeño para evitar la división por cero
                System.out.println("Error: El denominador (f(x_actual) - f(x_anterior)) se aproxima a cero. El método de la Secante podría fallar o divergir.");
                return null; // O podrías lanzar una excepción.
            }

            // Fórmula de la Secante
            xi_siguiente = xi_actual - (f_xi_actual * (xi_actual - xi_anterior)) / (f_xi_actual - f_xi_anterior);

            errorAproximacion = Math.abs(xi_siguiente - xi_actual);

            // Guarda la fila como arreglo de Object
            Object[] fila = new Object[] {
                iteracion,
                String.format("%.6f", xi_anterior),      // x_i-1
                String.format("%.6f", f_xi_anterior),    // f(x_i-1)
                String.format("%.6f", xi_actual),        // x_i
                String.format("%.6f", f_xi_actual),      // f(x_i)
                String.format("%.6f", xi_siguiente),     // x_i+1
                String.format("%.6f", errorAproximacion)
            };
            resultados.add(fila);

            // Criterio de parada adicional: si f(xi_siguiente) es muy cercano a cero
            // Evaluamos f(xi_siguiente) para esta condición de parada, pero no lo usamos en la siguiente iteración directamente
            double f_xi_siguiente = evaluarFuncion(xi_siguiente);
             if (Double.isNaN(f_xi_siguiente)) { // Manejar si la evaluación del siguiente falla
                System.out.println("Error: No se pudo evaluar la función en x_siguiente = " + String.format("%.6f", xi_siguiente));
                return null;
            }
            if (Math.abs(f_xi_siguiente) < toleranciaCriterio) {
                // Agregar la última fila con el valor final de f(xi_siguiente) si no se añadió antes
                if (iteracion <= maxIteraciones) { // Asegura que no se duplique si ya se alcanzó el límite
                    // Opcional: podrías querer añadir una fila final con el valor muy cercano a cero de f(xi_siguiente)
                    // Si ya se añadió la fila anterior, esta podría ser una fila "final" solo con la raíz y el error.
                    // Para simplificar, si ya está la fila de la iteración actual y la condición de parada se cumple, salimos.
                }
                break; // Se encontró una raíz con la tolerancia deseada
            }

            // Actualiza los valores para la siguiente iteración
            xi_anterior = xi_actual;
            xi_actual = xi_siguiente;
            iteracion++;
        }
        return resultados;
    }

    /**
     * Método privado para evaluar la función definida en el modelo en un punto dado 'x'.
     * @param x El valor en el cual evaluar la función.
     * @return El resultado de la evaluación de la función en 'x', o Double.NaN si ocurre un error.
     */
    private double evaluarFuncion(double x) {
        if (engine == null) {
            System.err.println("Error: ScriptEngine no inicializado. No se puede evaluar la función.");
            return Double.NaN;
        }
        try {
            String expr = funcion.replaceAll("\\bx\\b", "(" + x + ")");
            Object result = engine.eval(expr);

            if (result instanceof Number) {
                return ((Number) result).doubleValue();
            } else {
                System.out.println("Error al evaluar f(x): El resultado no es un número.");
                return Double.NaN;
            }
        } catch (ScriptException e) {
            System.out.println("Error de sintaxis en la función f(x): " + e.getMessage());
            return Double.NaN;
        } catch (Exception e) {
            System.out.println("Error inesperado al evaluar f(x): " + e.getMessage());
            return Double.NaN;
        }
    }
}