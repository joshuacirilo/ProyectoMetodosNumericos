/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MetodoReglaFalsaModelo {

    public List<Object[]> calcularReglaFalsa(String funcion, double a, double b) {
        List<Object[]> resultados = new ArrayList<>();
        final double TOLERANCIA = 0.0001;
        int iteracion = 0;
        double xrAnterior = 0;

        // Formatear la función para que JavaScript la entienda
        funcion = prepararFuncion(funcion);

        try {
            double fa = evaluarFuncion(funcion, a);
            double fb = evaluarFuncion(funcion, b);

            if (fa * fb > 0) {
                System.out.println("La función no cambia de signo en el intervalo dado.");
                return resultados;
            }

            double xr = a; // Valor inicial
            double error;

            do {
                xr = b - (fb * (a - b)) / (fa - fb);
                double fxr = evaluarFuncion(funcion, xr);
                error = Math.abs(xr - xrAnterior);
                xrAnterior = xr;

                Object[] fila = {
                    iteracion,
                    formatear(a),
                    formatear(b),
                    formatear(fa),
                    formatear(fb),
                    formatear(xr),
                    formatear(fxr),
                    iteracion == 0 ? "-" : formatear(error),
                    "", "", "", "", ""
                };
                resultados.add(fila);

                if (fa * fxr < 0) {
                    b = xr;
                    fb = fxr;
                } else {
                    a = xr;
                    fa = fxr;
                }

                iteracion++;
                if (iteracion > 1000) break; // Evitar bucles infinitos

            } while (error > TOLERANCIA);

        } catch (ScriptException e) {
            System.out.println("Error de sintaxis en f(x): " + e.getMessage());
        }

        return resultados;
    }

    private double evaluarFuncion(String funcion, double x) throws ScriptException {
        ScriptEngineManager mgr = new ScriptEngineManager();
        ScriptEngine engine = mgr.getEngineByName("JavaScript");

        // Reemplazar "x" por su valor
        String expresion = funcion.replaceAll("x", "(" + x + ")");
        Object resultado = engine.eval(expresion);

        return Double.parseDouble(resultado.toString());
    }

    private String prepararFuncion(String funcion) {
        // Reemplazar x^n con Math.pow(x,n)
        return funcion.replaceAll("x\\^([0-9]+)", "Math.pow(x,$1)");
    }

    private String formatear(double valor) {
        DecimalFormat df = new DecimalFormat("0.0000");
        return df.format(valor);
    }
}
