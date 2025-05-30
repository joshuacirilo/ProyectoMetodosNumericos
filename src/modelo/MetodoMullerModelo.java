/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.List;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MetodoMullerModelo {

    private String funcionStr;

    public MetodoMullerModelo(String funcionStr) {
        this.funcionStr = funcionStr;
    }

    private double f(double x) {
        Expression e = new ExpressionBuilder(funcionStr)
                .variable("x")
                .build()
                .setVariable("x", x);
        return e.evaluate();
    }

    public List<Object[]> muller(double x0, double x1, double x2, double tol, int maxIter) {
        List<Object[]> resultados = new ArrayList<>();

        for (int i = 1; i <= maxIter; i++) {
            double fx0 = f(x0);
            double fx1 = f(x1);
            double fx2 = f(x2);

            double h0 = x1 - x0;
            double h1 = x2 - x1;

            double d0 = (fx1 - fx0) / h0;
            double d1 = (fx2 - fx1) / h1;

            double a = (d1 - d0) / (h1 + h0);
            double b = a * h1 + d1;
            double c = fx2;

            double discriminante = b * b - 4 * a * c;

            if (Double.isNaN(discriminante) || discriminante < 0 || a == 0) {
                break;
            }

            double sqrt = Math.sqrt(discriminante);
            double denom = (Math.abs(b + sqrt) > Math.abs(b - sqrt)) ? b + sqrt : b - sqrt;

            if (denom == 0) {
                break;
            }

            double dxr = -2 * c / denom;
            double x3 = x2 + dxr;
            double error = Math.abs(dxr);

            Object[] fila = {
                i,
                redondear(x0), redondear(x1), redondear(x2),
                redondear(fx0), redondear(fx1), redondear(fx2),
                redondear(h0), redondear(h1),
                redondear(d0), redondear(d1),
                redondear(a), redondear(b), redondear(c),
                redondear(x3),
                redondear(error)
            };

            resultados.add(fila);

            if (error < tol) {
                break;
            }

            // Actualizar valores para la siguiente iteración
            x0 = x1;
            x1 = x2;
            x2 = x3;
        }

        return resultados;
    }

    private String redondear(double valor) {
        if (Double.isNaN(valor) || Double.isInfinite(valor)) return "NaN";
        return String.format("%.6f", valor);
    }
}
