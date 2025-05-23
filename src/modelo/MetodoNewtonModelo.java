package modelo;

//import java.beans.Expression;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

public class MetodoNewtonModelo {

    private String funcionStr;
    private String derivadaStr; // ¡NUEVO! Para la función derivada
    private Expression funcionExpression;
    private Expression derivadaExpression; // ¡NUEVO! Para la expresión derivada
    private boolean funcionesValidas; // Ahora valida ambas funciones

    public MetodoNewtonModelo(String funcionStr, String derivadaStr) {
        this.funcionStr = funcionStr;
        this.derivadaStr = derivadaStr;
        this.funcionesValidas = false;

        try {
            // Construir la expresión para la función principal
            this.funcionExpression = new ExpressionBuilder(funcionStr)
                                            .variables("x")
                                            .build();

            // Construir la expresión para la función derivada
            this.derivadaExpression = new ExpressionBuilder(derivadaStr)
                                            .variables("x")
                                            .build();

            this.funcionesValidas = true;
        } catch (IllegalArgumentException e) {
            System.err.println("Error al parsear una de las funciones: " + e.getMessage());
            this.funcionExpression = null;
            this.derivadaExpression = null;
            this.funcionesValidas = false;
        } catch (Exception e) {
            System.err.println("Error inesperado al construir las expresiones: " + e.getMessage());
            this.funcionExpression = null;
            this.derivadaExpression = null;
            this.funcionesValidas = false;
        }
    }

    public boolean areFunctionsValid() { // Cambiado a plural
        return funcionesValidas;
    }

    // Método para evaluar la función principal
    public double evaluarFuncion(double x) {
        if (!funcionesValidas || funcionExpression == null) {
            throw new IllegalStateException("Las funciones no son válidas y no pueden ser evaluadas.");
        }
        funcionExpression.setVariable("x", x);
        return funcionExpression.evaluate();
    }

    // ¡NUEVO! Método para evaluar la función derivada
    public double evaluarDerivada(double x) {
        if (!funcionesValidas || derivadaExpression == null) {
            throw new IllegalStateException("Las funciones no son válidas y no pueden ser evaluadas.");
        }
        derivadaExpression.setVariable("x", x);
        return derivadaExpression.evaluate();
    }

    // Método principal del Método de Newton
    public List<Object[]> newtonRaphson(double x0, double tolerancia, int maxIteraciones) {
        List<Object[]> resultados = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.0000");

        if (!funcionesValidas) {
            System.err.println("No se puede realizar Newton-Raphson: una o ambas funciones son inválidas.");
            return null;
        }

        double xi = x0;
        double fx = 0;
        double fPrimaX = 0;
        double xr = 0; // x_i+1
        double error = Double.MAX_VALUE; // Inicializamos con un valor grande

        for (int i = 0; i < maxIteraciones; i++) {
            try {
                fx = evaluarFuncion(xi);
                fPrimaX = evaluarDerivada(xi);
            } catch (RuntimeException e) {
                System.err.println("Error al evaluar f(xi) o f'(xi) en iteración " + (i + 1) + ": " + e.getMessage());
                return null; // O lanzar una excepción para que el controlador la maneje
            }

            if (fPrimaX == 0) {
                System.err.println("La derivada es cero en xi = " + xi + ". No se puede continuar (división por cero).");
                return null; // O lanzar una excepción más específica
            }

            xr = xi - (fx / fPrimaX);

            // Calcular el error. Puede ser absoluto o relativo.
            // Aquí un error absoluto entre la aproximación actual y la siguiente
            error = Math.abs(xr - xi);

            // Añadir fila de resultados
            resultados.add(new Object[]{
                i + 1,
                df.format(xi),
                df.format(fx),
                df.format(fPrimaX),
                df.format(xr),
                df.format(error) // Mostrar el error calculado en la tabla
            });

            // Criterios de parada
            if (Math.abs(fx) < tolerancia || error < tolerancia) {
                break;
            }

            xi = xr; // Actualizar xi para la siguiente iteración
        }

        return resultados;
    }
}