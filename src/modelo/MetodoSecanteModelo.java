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

public class MetodoSecanteModelo {

    private String funcionStr;
    private Expression expression;
    private boolean funcionValida;

    public MetodoSecanteModelo(String funcionStr) {
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

    public List<Object[]> secante(double x0, double x1, double tolerancia, int maxIteraciones) {
        List<Object[]> resultados = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.0000");

        if (!funcionValida) {
            System.err.println("No se puede realizar el método de la secante: la función es inválida.");
            return null;
        }

        double f0, f1, x2 = 0, error = 0;

        try {
            f0 = evaluarFuncion(x0);
            f1 = evaluarFuncion(x1);
        } catch (RuntimeException e) {
            System.err.println("Error al evaluar f(x0) o f(x1): " + e.getMessage());
            return null;
        }

        for (int i = 0; i < maxIteraciones; i++) {
            if (f1 - f0 == 0) {
                System.err.println("División por cero en iteración " + (i + 1));
                break;
            }

            x2 = x1 - f1 * (x1 - x0) / (f1 - f0);
            error = Math.abs(x2 - x1);

            resultados.add(new Object[]{
                i + 1,
                df.format(x0),
                df.format(x1),
                df.format(f0),
                df.format(f1),
                df.format(x2),
                df.format(error)
            });

            if (error < tolerancia) {
                break;
            }

            // Preparar para la siguiente iteración
            x0 = x1;
            f0 = f1;
            x1 = x2;

            try {
                f1 = evaluarFuncion(x1);
            } catch (RuntimeException e) {
                System.err.println("Error al evaluar f(x1) en iteración " + (i + 1) + ": " + e.getMessage());
                return null;
            }
        }

        return resultados;
    }
}
