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
 * Clase que implementa el Método de Raíces Múltiples (Newton Modificado)
 * para encontrar raíces de funciones, especialmente útil para raíces con multiplicidad.
 * Forma parte de la capa Modelo en un diseño MVC.
 */
public class MetodoRaicesMultiplesModelo {

    private String funcion;
    private String derivadaPrimera;
    private String derivadaSegunda;
    private ScriptEngine engine; // Reutilizar el ScriptEngine

    /**
     * Constructor para inicializar el modelo con la función, su primera derivada y su segunda derivada.
     * Se inicializa el ScriptEngine aquí para mejorar el rendimiento al reutilizarlo.
     * @param funcion La función f(x) como una cadena de texto (ej. "x^3 - 7*x^2 + 15*x - 9").
     * @param derivadaPrimera La primera derivada f'(x) como una cadena de texto (ej. "3*x^2 - 14*x + 15").
     * @param derivadaSegunda La segunda derivada f''(x) como una cadena de texto (ej. "6*x - 14").
     */
    public MetodoRaicesMultiplesModelo(String funcion, String derivadaPrimera, String derivadaSegunda) {
        this.funcion = funcion;
        this.derivadaPrimera = derivadaPrimera;
        this.derivadaSegunda = derivadaSegunda;
        ScriptEngineManager manager = new ScriptEngineManager();
        this.engine = manager.getEngineByName("JavaScript");
        if (this.engine == null) {
            System.err.println("Error: El motor de JavaScript no está disponible. Asegúrate de usar un JRE completo.");
        }
    }

    /**
     * Aplica el método de Raíces Múltiples para encontrar una raíz de la función.
     *
     * @param x0 El valor inicial de aproximación.
     * @param toleranciaCriterio La tolerancia o criterio de parada para el error de aproximación.
     * @param maxIteraciones El número máximo de iteraciones para evitar bucles infinitos.
     * @return Una lista de arreglos de Object, donde cada arreglo representa una fila de resultados de la iteración.
     * Retorna null si ocurre un error durante la evaluación de las funciones/derivadas
     * o si el denominador se acerca a cero.
     */
    public List<Object[]> raicesMultiples(double x0, double toleranciaCriterio, int maxIteraciones) {
        List<Object[]> resultados = new ArrayList<>();

        double xi = x0; // Aproximación actual
        double fxi;     // f(xi)
        double dfxi;    // f'(xi)
        double ddfxi;   // f''(xi)
        double errorAproximacion = Double.MAX_VALUE;
        int iteracion = 1;

        // Bucle principal del método de Raíces Múltiples
        while (Math.abs(errorAproximacion) > toleranciaCriterio && iteracion <= maxIteraciones) {

            fxi = evaluarExpresion(xi, this.funcion);
            dfxi = evaluarExpresion(xi, this.derivadaPrimera);
            ddfxi = evaluarExpresion(xi, this.derivadaSegunda);

            // Manejo de errores en la evaluación
            if (Double.isNaN(fxi) || Double.isNaN(dfxi) || Double.isNaN(ddfxi)) {
                System.out.println("Error: No se pudo evaluar f(x), f'(x) o f''(x) en x = " + String.format("%.6f", xi));
                return null;
            }

            // Calcular el denominador: [f'(xi)]^2 - f(xi)f''(xi)
            double denominador = (dfxi * dfxi) - (fxi * ddfxi);

            // Evitar división por cero
            if (Math.abs(denominador) < 1e-9) { // Usamos una tolerancia pequeña
                System.out.println("Error: El denominador se aproxima a cero en la iteración " + iteracion + ". El método podría fallar o divergir.");
                return null; // O podrías lanzar una excepción.
            }

            double xi_anterior = xi;
            // Fórmula de Raíces Múltiples
            xi = xi - (fxi * dfxi) / denominador;

            errorAproximacion = Math.abs(xi - xi_anterior);

            // Guarda la fila como arreglo de Object
            Object[] fila = new Object[] {
                iteracion,
                String.format("%.6f", xi_anterior), // x_i-1 (anterior)
                String.format("%.6f", fxi),         // f(x_i-1)
                String.format("%.6f", dfxi),        // f'(x_i-1)
                String.format("%.6f", ddfxi),       // f''(x_i-1)
                String.format("%.6f", xi),          // x_i (actual)
                String.format("%.6f", errorAproximacion)
            };
            resultados.add(fila);

            // Criterio de parada adicional: si f(xi) es muy cercano a cero (aunque para raíces múltiples esto puede ser menos efectivo solo con f(xi))
            if (Math.abs(fxi) < toleranciaCriterio) { // Usamos fxi para este criterio de parada
                break;
            }
            
            iteracion++;
        }
        return resultados;
    }

    /**
     * Método privado para evaluar una expresión matemática (función o derivada) en un punto 'x'.
     * @param x El valor en el cual evaluar la expresión.
     * @param expression La cadena de texto de la expresión a evaluar.
     * @return El resultado de la evaluación, o Double.NaN si ocurre un error.
     */
    private double evaluarExpresion(double x, String expression) {
        if (engine == null) {
            System.err.println("Error: ScriptEngine no inicializado. No se puede evaluar la expresión.");
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