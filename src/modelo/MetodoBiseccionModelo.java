/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

public class MetodoBiseccionModelo {

    private String funcionStr;
    private Expression expression;
    private boolean funcionValida;

    public MetodoBiseccionModelo(String funcionStr) {
        this.funcionStr = funcionStr;
        this.funcionValida = false;

        try {
            this.expression = new ExpressionBuilder(funcionStr)
                                .variables("x")
                                .build();
            this.funcionValida = true;
        } catch (IllegalArgumentException e) {
            System.err.println("Error al parsear la función: " + e.getMessage());
            this.expression = null;
            this.funcionValida = false;
        } catch (Exception e) {
            System.err.println("Error inesperado al construir la expresión: " + e.getMessage());
            this.expression = null;
            this.funcionValida = false;
        }
    }

    public boolean isFunctionValid() {
        return funcionValida;
    }

    public double evaluarFuncion(double x) {
        if (!funcionValida || expression == null) {
            throw new IllegalStateException("La función no es válida y no puede ser evaluada.");
        }
        expression.setVariable("x", x);
        return expression.evaluate();
    }

    public List<Object[]> biseccion(double a, double b, double tolerancia, int maxIteraciones) {
        List<Object[]> resultados = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.0000");

        if (!funcionValida) {
            System.err.println("No se puede realizar la bisección: la función es inválida.");
            return null;
        }

        double fa, fb;
        try {
            fa = evaluarFuncion(a);
            fb = evaluarFuncion(b);
        } catch (RuntimeException e) { // <--- ¡CORRECCIÓN AQUÍ! Solo RuntimeException
            // RuntimeException ya cubre IllegalStateException
            System.err.println("Error al evaluar f(a) o f(b): " + e.getMessage());
            return null;
        }

        if (fa * fb >= 0) {
            System.err.println("f(a) y f(b) tienen el mismo signo. No se puede garantizar una raíz en el intervalo.");
            return null;
        }

        double xr = 0;
        double fxr = 0;
        double errorIteracion = Math.abs(b - a);

        for (int i = 0; i < maxIteraciones; i++) {
            xr = (a + b) / 2;
            
            try {
                fxr = evaluarFuncion(xr);
            } catch (RuntimeException e) { // <--- ¡CORRECCIÓN AQUÍ! Solo RuntimeException
                // RuntimeException ya cubre IllegalStateException
                System.err.println("Error al evaluar f(xr) en iteración " + (i + 1) + ": " + e.getMessage());
                return null;
            }

            errorIteracion = Math.abs(b - a) / 2;

            resultados.add(new Object[]{
                i + 1,
                df.format(a),
                df.format(b),
                df.format(fa),
                df.format(fb),
                df.format(xr),
                df.format(fxr),
                df.format(errorIteracion)
            });

            if (Math.abs(fxr) < tolerancia || errorIteracion < tolerancia) {
                break;
            }

            if (fa * fxr < 0) {
                b = xr;
                fb = fxr;
            } else {
                a = xr;
                fa = fxr;
            }
        }

        return resultados;
    }
}