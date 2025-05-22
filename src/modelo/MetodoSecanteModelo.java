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
import java.text.DecimalFormat; // Importar DecimalFormat

public class MetodoSecanteModelo {
    private String funcion;
    private ScriptEngine engine;

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
     * Utiliza x_i-1 (xi_minus_1) y x_i (xi_current) como los dos valores iniciales.
     *
     * @param xi_minus_1 El primer valor inicial de aproximación (x_{i-1}).
     * @param xi_current El segundo valor inicial de aproximación (x_i).
     * @param toleranciaCriterio La tolerancia o criterio de parada para el error de aproximación.
     * @param maxIteraciones El número máximo de iteraciones para evitar bucles infinitos.
     * @return Una lista de arreglos de Object, donde cada arreglo representa una fila de resultados de la iteración.
     * Retorna null si ocurre un error irrecuperable durante la evaluación de la función o si el método falla (ej. división por cero).
     */
    public List<Object[]> secante(double xi_minus_1, double xi_current, double toleranciaCriterio, int maxIteraciones) {
        List<Object[]> resultados = new ArrayList<>();
        // Formateador para mostrar los números con precisión
        DecimalFormat df = new DecimalFormat("0.000000");

        double f_xi_anterior;
        double f_xi_actual;
        double xi_siguiente;
        double errorAproximacion = Double.MAX_VALUE;

        // Bucle principal del método de la Secante
        for (int iteracion = 1; iteracion <= maxIteraciones; iteracion++) {
            // Evaluar las funciones en los puntos actuales
            f_xi_anterior = evaluarFuncion(xi_minus_1);
            f_xi_actual = evaluarFuncion(xi_current);

            // Manejo de errores durante la evaluación de la función
            if (Double.isNaN(f_xi_anterior) || Double.isNaN(f_xi_actual)) {
                System.err.println("Error: No se pudo evaluar la función en x = " + df.format(xi_minus_1) +
                                   " o en x = " + df.format(xi_current) + ". Revise la función.");
                return null; // Indica un error irrecuperable, el controlador debe manejarlo
            }

            // Evitar división por cero o un denominador muy pequeño en la fórmula de la secante
            if (Math.abs(f_xi_actual - f_xi_anterior) < 1e-9) { // Usamos un umbral pequeño para evitar casi-cero
                System.err.println("Error: El denominador (f(xi) - f(xi-1)) se aproxima a cero en la iteración " + iteracion + ". El método de la Secante podría fallar o divergir.");
                return null; // Indica un error, el controlador debe notificar al usuario
            }

            // Fórmula de la Secante: x_{i+1} = x_i - f(x_i) * (x_i - x_{i-1}) / (f(x_i) - f(x_{i-1}))
            xi_siguiente = xi_current - (f_xi_actual * (xi_current - xi_minus_1)) / (f_xi_actual - f_xi_anterior);

            // Calcular el error de aproximación (error absoluto entre la aproximación actual y la siguiente)
            errorAproximacion = Math.abs(xi_siguiente - xi_current);

            // Guarda la fila de resultados en el orden esperado por la JTable de la vista
            // Vista espera: "Iteración", "xi-1", "xi", "f (xi-1)", "f(xi)", "xr", "Tolerancia"
            Object[] fila = new Object[] {
                iteracion,
                df.format(xi_minus_1),      // Corresponde a "xi-1" en la tabla
                df.format(xi_current),      // Corresponde a "xi" en la tabla
                df.format(f_xi_anterior),   // Corresponde a "f (xi-1)" en la tabla
                df.format(f_xi_actual),     // Corresponde a "f(xi)" en la tabla
                df.format(xi_siguiente),    // Corresponde a "xr" (x_i+1) en la tabla
                df.format(errorAproximacion) // Corresponde a "Tolerancia" (error) en la tabla
            };
            resultados.add(fila);

            // Criterios de parada:
            // 1. Si el error de aproximación es menor que la tolerancia
            if (errorAproximacion < toleranciaCriterio) {
                break;
            }
            // 2. Si el valor de la función en la nueva aproximación es muy cercano a cero
            // (evaluamos f(xi_siguiente) para esta condición, no para la siguiente iteración directamente)
            double f_xi_siguiente = evaluarFuncion(xi_siguiente);
            if (Double.isNaN(f_xi_siguiente)) {
                 System.err.println("Error: No se pudo evaluar la función en x_siguiente = " + df.format(xi_siguiente) + " para el criterio de parada. La función podría ser inválida en este punto.");
                 return null;
            }
            if (Math.abs(f_xi_siguiente) < toleranciaCriterio) {
                break;
            }

            // Actualiza los valores para la próxima iteración:
            // El 'x_i' actual se convierte en el nuevo 'x_i-1'
            // El 'x_i+1' calculado se convierte en el nuevo 'x_i'
            xi_minus_1 = xi_current;
            xi_current = xi_siguiente;
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
            // Reemplaza la variable 'x' en la expresión de la función con el valor numérico.
            // "\\bx\\b" asegura que solo se reemplace la variable 'x' completa (como palabra)
            // y no parte de otras palabras (ej. la 'x' en "sen(x)").
            String expr = funcion.replaceAll("\\bx\\b", "(" + x + ")");
            Object result = engine.eval(expr);

            if (result instanceof Number) {
                return ((Number) result).doubleValue();
            } else {
                System.err.println("Error al evaluar f(x): El resultado no es un número. Resultado obtenido: " + result);
                return Double.NaN;
            }
        } catch (ScriptException e) {
            System.err.println("Error de sintaxis en la función f(x) o al evaluar la expresión: " + e.getMessage());
            return Double.NaN;
        } catch (Exception e) {
            System.err.println("Error inesperado al evaluar f(x): " + e.getMessage());
            return Double.NaN;
        }
    }
}