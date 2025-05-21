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

public class MetodoNewtonModelo {
    private String funcion;
    private String derivadaFuncion; // Nueva propiedad para la derivada
    private ScriptEngine engine; // Reutilizar el ScriptEngine

    /**
     * Constructor para inicializar el modelo con la función y su derivada a evaluar.
     * Se inicializa el ScriptEngine aquí para mejorar el rendimiento al reutilizarlo.
     * @param funcion La función matemática como una cadena de texto (ej. "x^2 - 4").
     * @param derivadaFuncion La derivada de la función matemática como una cadena de texto (ej. "2*x").
     */
    public MetodoNewtonModelo(String funcion, String derivadaFuncion) {
        this.funcion = funcion;
        this.derivadaFuncion = derivadaFuncion;
        // Inicializar el ScriptEngine una sola vez
        ScriptEngineManager manager = new ScriptEngineManager();
        this.engine = manager.getEngineByName("JavaScript");
        if (this.engine == null) {
            System.err.println("Error: El motor de JavaScript no está disponible. Asegúrate de usar un JRE completo.");
        }
    }

    /**
     * Aplica el método de Newton-Raphson para encontrar una raíz de la función.
     * @param x0 El valor inicial de aproximación.
     * @param toleranciaCriterio La tolerancia o criterio de parada para el error de aproximación y el valor de f(xi).
     * @param maxIteraciones El número máximo de iteraciones para evitar bucles infinitos.
     * @return Una lista de arreglos de Object, donde cada arreglo representa una fila de resultados de la iteración.
     * Retorna null si ocurre un error durante la evaluación de la función o su derivada.
     */
    public List<Object[]> newtonRaphson(double x0, double toleranciaCriterio, int maxIteraciones) {
        List<Object[]> resultados = new ArrayList<>();

        double xi = x0; // Aproximación actual
        double fxi;     // Valor de la función en xi
        double dfxi;    // Valor de la derivada en xi
        double errorAproximacion = Double.MAX_VALUE; // Inicializar con un valor grande
        int iteracion = 1;

        // Bucle principal del método de Newton-Raphson
        while (Math.abs(errorAproximacion) > toleranciaCriterio && iteracion <= maxIteraciones) {
            fxi = evaluarFuncion(xi, this.funcion);
            dfxi = evaluarFuncion(xi, this.derivadaFuncion);

            // Manejo de errores durante la evaluación
            if (Double.isNaN(fxi) || Double.isNaN(dfxi)) {
                System.out.println("Error: No se pudo evaluar la función o su derivada en x = " + String.format("%.6f", xi));
                return null;
            }

            // Evitar división por cero si la derivada es muy cercana a cero
            if (Math.abs(dfxi) < 1e-9) { // Un valor pequeño para evitar la división por cero
                System.out.println("Error: La derivada se aproxima a cero en x = " + String.format("%.6f", xi) + ". El método de Newton-Raphson podría divergir o no converger.");
                return null; // O podrías lanzar una excepción para que el controlador la maneje.
            }

            double xi_anterior = xi; // Guarda el valor actual para calcular el error
            xi = xi - (fxi / dfxi); // Fórmula de Newton-Raphson

            errorAproximacion = Math.abs(xi - xi_anterior);

            // Guarda la fila como arreglo de Object
            Object[] fila = new Object[] {
                iteracion,
                String.format("%.6f", xi_anterior), // x_i-1 (anterior)
                String.format("%.6f", fxi),         // f(x_i-1)
                String.format("%.6f", dfxi),        // f'(x_i-1)
                String.format("%.6f", xi),          // x_i (actual)
                String.format("%.6f", errorAproximacion)
            };
            resultados.add(fila);

            // Criterio de parada adicional: si f(xi) es muy cercano a cero
            if (Math.abs(fxi) < toleranciaCriterio) { // Usamos fxi, ya que al calcular la nueva xi, la fxi anterior es la relevante
                break; // Se encontró una raíz con la tolerancia deseada
            }

            iteracion++;
        }
        return resultados;
    }

    /**
     * Método privado para evaluar una función dada en un punto 'x' utilizando el ScriptEngine.
     * Es más genérico para poder evaluar tanto la función original como su derivada.
     * @param x El valor en el cual evaluar la función.
     * @param expression La expresión de la función o derivada como cadena de texto.
     * @return El resultado de la evaluación, o Double.NaN si ocurre un error.
     */
    private double evaluarFuncion(double x, String expression) {
        if (engine == null) {
            System.err.println("Error: ScriptEngine no inicializado. No se puede evaluar la función.");
            return Double.NaN;
        }
        try {
            String expr = expression.replaceAll("\\bx\\b", "(" + x + ")");
            Object result = engine.eval(expr);

            if (result instanceof Number) {
                return ((Number) result).doubleValue();
            } else {
                System.out.println("Error al evaluar expresión '" + expression + "': El resultado no es un número.");
                return Double.NaN;
            }
        } catch (ScriptException e) {
            System.out.println("Error de sintaxis en la expresión '" + expression + "': " + e.getMessage());
            return Double.NaN;
        } catch (Exception e) {
            System.out.println("Error inesperado al evaluar expresión '" + expression + "': " + e.getMessage());
            return Double.NaN;
        }
    }
}