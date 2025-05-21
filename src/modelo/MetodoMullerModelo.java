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

/**
 * Clase que implementa el método numérico de Müller para encontrar raíces de funciones.
 * Forma parte de la capa Modelo en un diseño MVC.
 */
public class MetodoMullerModelo {

    private String funcion;
    private ScriptEngine engine; // Reutilizar el ScriptEngine

    /**
     * Constructor para inicializar el modelo con la función a evaluar.
     * Se inicializa el ScriptEngine aquí para mejorar el rendimiento al reutilizarlo.
     * @param funcion La función matemática como una cadena de texto (ej. "x^3 - 2*x - 5").
     */
    public MetodoMullerModelo(String funcion) {
        this.funcion = funcion;
        ScriptEngineManager manager = new ScriptEngineManager();
        this.engine = manager.getEngineByName("JavaScript");
        if (this.engine == null) {
            System.err.println("Error: El motor de JavaScript no está disponible. Asegúrate de usar un JRE completo.");
        }
    }

    /**
     * Aplica el método de Müller para encontrar una raíz de la función.
     *
     * @param x0 Primer valor inicial de aproximación.
     * @param x1 Segundo valor inicial de aproximación.
     * @param x2 Tercer valor inicial de aproximación.
     * @param toleranciaCriterio La tolerancia o criterio de parada para el error de aproximación o el valor de f(xr).
     * @param maxIteraciones El número máximo de iteraciones para evitar bucles infinitos.
     * @return Una lista de arreglos de Object, donde cada arreglo representa una fila de resultados de la iteración.
     * Retorna null si ocurre un error durante la evaluación de la función o si la convergencia no se logra.
     */
    public List<Object[]> muller(double x0, double x1, double x2, double toleranciaCriterio, int maxIteraciones) {
        List<Object[]> resultados = new ArrayList<>();

        double xi_0 = x0; // x(i-2)
        double xi_1 = x1; // x(i-1)
        double xi_2 = x2; // x(i)

        double f_xi_0;
        double f_xi_1;
        double f_xi_2;

        double h1, h2;
        double delta1, delta2;
        double a, b, c;
        double discriminante;
        double xr; // La nueva aproximación a la raíz
        double errorAproximacion = Double.MAX_VALUE;
        int iteracion = 1;

        // Bucle principal del método de Müller
        while (Math.abs(errorAproximacion) > toleranciaCriterio && iteracion <= maxIteraciones) {

            f_xi_0 = evaluarFuncion(xi_0);
            f_xi_1 = evaluarFuncion(xi_1);
            f_xi_2 = evaluarFuncion(xi_2);

            // Manejo de errores en la evaluación de la función
            if (Double.isNaN(f_xi_0) || Double.isNaN(f_xi_1) || Double.isNaN(f_xi_2)) {
                System.out.println("Error: No se pudo evaluar la función en uno de los puntos iniciales o intermedios.");
                return null;
            }

            // Calculando los términos h y delta
            h1 = xi_1 - xi_0;
            h2 = xi_2 - xi_1;
            delta1 = (f_xi_1 - f_xi_0) / h1;
            delta2 = (f_xi_2 - f_xi_1) / h2;

            // Calculando los coeficientes de la parábola a*x^2 + b*x + c
            a = (delta2 - delta1) / (h2 + h1);
            b = delta2 + a * h2;
            c = f_xi_2;

            // Calculando el discriminante
            discriminante = Math.sqrt(b * b - 4 * a * c);

            // Elegir la raíz de la parábola que esté más cerca de xi_2 (la última aproximación)
            double den1 = b + discriminante;
            double den2 = b - discriminante;

            // Evitar división por cero
            if (Math.abs(den1) < 1e-9 && Math.abs(den2) < 1e-9) {
                 System.out.println("Error: Ambos denominadores para la raíz de la parábola son cero. El método de Müller podría fallar.");
                 return null;
            }

            // La condición para elegir la raíz es tomar el denominador más grande en valor absoluto
            // para evitar inestabilidad numérica.
            if (Math.abs(den1) >= Math.abs(den2)) {
                xr = xi_2 - (2 * c) / den1;
            } else {
                xr = xi_2 - (2 * c) / den2;
            }

            errorAproximacion = Math.abs(xr - xi_2); // Error basado en la diferencia con la última aproximación

            // Guarda la fila como arreglo de Object
            Object[] fila = new Object[] {
                iteracion,
                String.format("%.6f", xi_0),
                String.format("%.6f", xi_1),
                String.format("%.6f", xi_2),
                String.format("%.6f", f_xi_0),
                String.format("%.6f", f_xi_1),
                String.format("%.6f", f_xi_2),
                String.format("%.6f", xr), // La nueva aproximación
                String.format("%.6f", evaluarFuncion(xr)), // f(xr)
                String.format("%.6f", errorAproximacion)
            };
            resultados.add(fila);

            // Criterio de parada adicional: si f(xr) es muy cercano a cero
            if (Math.abs(evaluarFuncion(xr)) < toleranciaCriterio) {
                break;
            }

            // Actualiza los puntos para la siguiente iteración
            xi_0 = xi_1;
            xi_1 = xi_2;
            xi_2 = xr;
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