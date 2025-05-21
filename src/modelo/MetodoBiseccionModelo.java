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

public class MetodoBiseccionModelo {
    private String funcion;
    private ScriptEngine engine; // Reutilizar el ScriptEngine

    /**
     * Constructor para inicializar el modelo con la función a evaluar.
     * Se inicializa el ScriptEngine aquí para mejorar el rendimiento al reutilizarlo.
     * @param funcion La función matemática como una cadena de texto (ej. "x^2 - 4").
     */
    public MetodoBiseccionModelo(String funcion) {
        this.funcion = funcion;
        // Inicializar el ScriptEngine una sola vez
        ScriptEngineManager manager = new ScriptEngineManager();
        this.engine = manager.getEngineByName("JavaScript");
        if (this.engine == null) {
            System.err.println("Error: El motor de JavaScript no está disponible. Asegúrate de usar un JRE completo.");
        }
    }

    /**
     * Aplica el método de Bisección para encontrar una raíz de la función dentro de un intervalo dado.
     * @param a Límite inferior del intervalo.
     * @param b Límite superior del intervalo.
     * @param toleranciaCriterio La tolerancia o criterio de parada para el error de aproximación y el valor de f(xm).
     * @param maxIteraciones El número máximo de iteraciones para evitar bucles infinitos.
     * @return Una lista de arreglos de Object, donde cada arreglo representa una fila de resultados de la iteración.
     * Retorna null si el intervalo inicial es inválido (f(a) y f(b) no tienen signos opuestos).
     */
    public List<Object[]> biseccion(double a, double b, double toleranciaCriterio, int maxIteraciones) {
        List<Object[]> resultados = new ArrayList<>();

        double fa = evaluarFuncion(a);
        double fb = evaluarFuncion(b);

        // Manejo de errores iniciales en la evaluación de la función
        if (Double.isNaN(fa) || Double.isNaN(fb)) {
            System.out.println("Error: No se pudo evaluar la función en los límites iniciales a o b.");
            return null;
        }

        // Validación fundamental para el método de Bisección
        if (fa * fb >= 0) {
            System.out.println("Error: f(a) y f(b) tienen el mismo signo o uno es cero. " +
                               "El método de Bisección no puede garantizar una raíz en este intervalo.");
            return null;
        }

        double xm = 0; // Punto medio
        double fxm = 0; // f(xm)
        double errorAproximacion = Double.MAX_VALUE; // Inicializar con un valor grande
        int iteracion = 1;

        // Bucle principal del método de Bisección
        while (Math.abs(errorAproximacion) > toleranciaCriterio && iteracion <= maxIteraciones) {
            xm = (a + b) / 2; // Cálculo del punto medio
            fxm = evaluarFuncion(xm);

            if (Double.isNaN(fxm)) {
                System.out.println("Error: No se pudo evaluar la función en el punto medio (xm).");
                return null;
            }

            // Calcula el error de aproximación (puede ser el ancho del intervalo o |xm - xm_anterior|)
            // Aquí usamos el ancho del nuevo intervalo para ser coherente con la naturaleza de bisección
            errorAproximacion = Math.abs(b - a); // O también Math.abs(xm - xmAnterior) si se guarda

            // Guarda la fila como arreglo de Object
            Object[] fila = new Object[] {
                iteracion,
                String.format("%.6f", a),
                String.format("%.6f", b),
                String.format("%.6f", xm),
                String.format("%.6f", fa), // Incluimos f(a)
                String.format("%.6f", fb), // Incluimos f(b)
                String.format("%.6f", fxm),
                String.format("%.6f", errorAproximacion)
            };
            resultados.add(fila);

            // Criterio de parada adicional: si f(xm) es muy cercano a cero
            if (Math.abs(fxm) < toleranciaCriterio) {
                break; // Se encontró una raíz con la tolerancia deseada
            }

            // Actualiza los límites del intervalo
            if (fa * fxm < 0) {
                b = xm;
                fb = fxm; // Actualiza f(b)
            } else {
                a = xm;
                fa = fxm; // Actualiza f(a)
            }

            iteracion++;
        }
        return resultados;
    }

    /**
     * Método privado para evaluar la función en un punto 'x' utilizando el ScriptEngine.
     * @param x El valor en el cual evaluar la función.
     * @return El resultado de la evaluación de la función en 'x', o Double.NaN si ocurre un error.
     */
    private double evaluarFuncion(double x) {
        if (engine == null) {
            System.err.println("Error: ScriptEngine no inicializado. No se puede evaluar la función.");
            return Double.NaN;
        }
        try {
            // Asegura que solo se reemplace 'x' como variable, no dentro de palabras
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