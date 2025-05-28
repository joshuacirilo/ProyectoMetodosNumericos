package modelo;

/**
 *
 * @author moralesjs_
 */
public class SistemaDosIncognitasModelo {
    private double a1, b1, c1; // Coeficientes de la primera ecuación
    private double a2, b2, c2; // Coeficientes de la segunda ecuación

    
    public SistemaDosIncognitasModelo(double a1, double b1, double c1, double a2, double b2, double c2) {
        this.a1 = a1;
        this.b1 = b1;
        this.c1 = c1;
        this.a2 = a2;
        this.b2 = b2;
        this.c2 = c2;
    }
    
    
    // Método de Sustitución
    public double[] resolverPorSustitucion() {
        double y = (c1 * a2 - c2 * a1) / (b1 * a2 - b2 * a1);
        double x = (c1 - b1 * y) / a1;
        return new double[] {x, y};
    }
    
     // Método de Igualación
    public double[] resolverPorIgualacion() {
        double m1 = -a1 / b1, n1 = c1 / b1;
        double m2 = -a2 / b2, n2 = c2 / b2;

        double x = (n2 - n1) / (m1 - m2);
        double y = m1 * x + n1;
        return new double[] {x, y};
    }
    
    // Método de Eliminación
    public double[] resolverPorEliminacion() {
        double nuevoA1 = a1 * b2;
        double nuevoB1 = b1 * b2;
        double nuevoC1 = c1 * b2;

        double nuevoA2 = a2 * b1;
        double nuevoB2 = b2 * b1;
        double nuevoC2 = c2 * b1;

        double x = (nuevoC1 - nuevoC2) / (nuevoA1 - nuevoA2);
        double y = (c1 - a1 * x) / b1;
        return new double[] {x, y};
    }
    
    
    
    
}
