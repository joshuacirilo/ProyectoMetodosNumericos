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

public class MetodoReglaFalsaModelo {
    private String funcion;

    public MetodoReglaFalsaModelo(String funcion) {
        this.funcion = funcion;
    }

    public List<Object[]> reglaFalsa(double a, double b) { // Agregado toleranciaCriterio
        List<Object[]> resultados = new ArrayList<>();

        double fa = evaluarFuncion(a);
        double fb = evaluarFuncion(b);

        if (Double.isNaN(fa) || Double.isNaN(fb)) {
            // Si la evaluación de la función falla, el error ya se imprimió en evaluarFuncion.
            // Aquí simplemente retornamos null.
            return null;
        }

        if (fa * fb >= 0) { // Cambiado a >= para incluir el caso donde una raíz es un límite inicial
            System.out.println("Error: f(a) y f(b) tienen el mismo signo o uno es cero. No se puede garantizar la aplicación de la Regla Falsa.");
            return null;
        }

        double xr = 0;
        double xrAnterior = b; // Inicializar xrAnterior con b para la primera iteración
        double fxr = 0;
        double errorAproximacion = Double.MAX_VALUE; // Cambiado a un nombre más descriptivo
        int iteracion = 1;

        // Bucle do-while para asegurar al menos una iteración y la condición de parada
        while (Math.abs(errorAproximacion) > 1 && iteracion <= 100) { // Usamos toleranciaCriterio del GUI
            xr = ((a * fb) - (b * fa)) / (fb - fa);
            fxr = evaluarFuncion(xr);

            if (Double.isNaN(fxr)) { // Manejar error si fxr no puede ser evaluado
                return null;
            }

            errorAproximacion = Math.abs(xr - xrAnterior);

            // Guarda la fila como arreglo de Object
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

            // Condición adicional para detener si la función evaluada en xr es muy cercana a cero
//            if (Math.abs(fxr) < toleranciaCriterio) {
//                break;
//            }
        }
        return resultados;
    }

    // Método privado para evaluar la función
    private double evaluarFuncion(double x) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");

            // Asegura que solo se reemplace 'x' como variable, no dentro de palabras
            String expr = funcion.replaceAll("\\bx\\b", "(" + x + ")");
            Object result = engine.eval(expr);

            // Manejar resultados que no son números (por ejemplo, si la función es inválida)
            if (result instanceof Number) {
                return ((Number) result).doubleValue();
            } else {
                System.out.println("Error al evaluar f(x): El resultado no es un número.");
                return Double.NaN;
            }
        } catch (ScriptException e) {
            System.out.println("Error de sintaxis en la función f(x): " + e.getMessage());
            return Double.NaN; // Devuelve NaN en caso de error de sintaxis en la función
        } catch (Exception e) {
            System.out.println("Error inesperado al evaluar f(x): " + e.getMessage());
            return Double.NaN; // Devuelve NaN en caso de otro error
        }
    }
}