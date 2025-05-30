/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MetodoReglaFalsaModelo {
    private String funcion;

    public MetodoReglaFalsaModelo(String funcion) {
        this.funcion = preprocesarFuncion(funcion);
    }

    public List<Object[]> reglaFalsa(double a, double b) {
        List<Object[]> resultados = new ArrayList<>();

        double fa = evaluarFuncion(a);
        double fb = evaluarFuncion(b);

        if (Double.isNaN(fa) || Double.isNaN(fb)) {
            return null;
        }

        if (fa * fb >= 0) {
            System.out.println("Error: f(a) y f(b) tienen el mismo signo o uno es cero. No se puede garantizar la aplicación de la Regla Falsa.");
            return null;
        }

        double xr = 0;
        double xrAnterior = b;
        double fxr = 0;
        double errorAproximacion = Double.MAX_VALUE;
        int iteracion = 1;

        // ✅ Cambiada la tolerancia a 0.001
        while (Math.abs(errorAproximacion) > 0.001 && iteracion <= 100) {
            xr = ((a * fb) - (b * fa)) / (fb - fa);
            fxr = evaluarFuncion(xr);

            if (Double.isNaN(fxr)) {
                return null;
            }

            errorAproximacion = Math.abs(xr - xrAnterior);

            Object[] fila = new Object[] {
                iteracion,
                String.format("%.6f", a),
                String.format("%.6f", b),
                String.format("%.6f", fa),
                String.format("%.6f", fb),
                String.format("%.6f", xr),
                String.format("%.6f", fxr),
                String.format("%.6f", errorAproximacion)
            };
            resultados.add(fila);

            if (fa * fxr < 0) {
                b = xr;
                fb = fxr;
            } else {
                a = xr;
                fa = fxr;
            }

            xrAnterior = xr;
            iteracion++;
        }

        return resultados;
    }

    private double evaluarFuncion(double x) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");

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

    private String preprocesarFuncion(String funcionOriginal) {
        String f = funcionOriginal;

        // Reemplazar potencias como x^2 o (2x)^3 por Math.pow(...)
        f = f.replaceAll("([a-zA-Z0-9\\.\\)]+)\\s*\\^\\s*([0-9]+)", "Math.pow($1,$2)");

        // Insertar multiplicación entre número y variable: 4x → 4*x
        f = f.replaceAll("(\\d)([a-zA-Z])", "$1*$2");

        // Insertar multiplicación entre variable y variable: xy → x*y
        f = f.replaceAll("([a-zA-Z])([a-zA-Z])", "$1*$2");

        return f;
    }
}
