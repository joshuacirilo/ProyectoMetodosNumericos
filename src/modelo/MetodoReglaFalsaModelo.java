/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package modelo;

import java.util.ArrayList;
import java.util.List;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class MetodoReglaFalsaModelo {
    private String funcion;

    public MetodoReglaFalsaModelo(String funcion) {
        this.funcion = funcion;
    }

    public List<Object[]> reglaFalsa(double a, double b) {
        List<Object[]> resultados = new ArrayList<Object[]>();

        double fa = evaluarFuncion(a);
        double fb = evaluarFuncion(b);

        if (fa * fb > 0) {
            System.out.println("Error: f(a) y f(b) tienen el mismo signo. No se puede aplicar la Regla Falsa.");
            return null; // Devuelve null para indicar un error
        }

        double xr = 0;
        double xrAnterior = 0;
        double fxr = 0;
        double tolerancia = 0;
        int iteracion = 1;

        do {
            xr = ((a * fb) - (b * fa)) / (fb - fa);
            fxr = evaluarFuncion(xr);
            tolerancia = Math.abs(xr - xrAnterior);

            // Guarda la fila como arreglo de Object
            Object[] fila = new Object[] {
                    iteracion,
                    String.format("%.6f", a), // Formatear a 6 decimales para mejor presentación
                    String.format("%.6f", b),
                    String.format("%.6f", fa),
                    String.format("%.6f", fb),
                    String.format("%.6f", xr),
                    String.format("%.6f", fxr),
                    String.format("%.6f", tolerancia)
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

        } while (Math.abs(fxr) > 0.001 && iteracion <= 100);

        return resultados;
    }

    private double evaluarFuncion(double x) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");

            // Asegura que solo se reemplace 'x' como variable, no dentro de palabras
            String expr = funcion.replaceAll("\\bx\\b", "(" + x + ")");
            Object result = engine.eval(expr);

            return Double.parseDouble(result.toString());
        } catch (Exception e) {
            System.out.println("Error al evaluar f(x): " + e.getMessage());
            return Double.NaN; // Devuelve NaN en caso de error de evaluación
        }
    }
}