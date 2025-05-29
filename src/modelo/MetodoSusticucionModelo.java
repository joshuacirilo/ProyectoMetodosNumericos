/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author moralesjs_
 */
public class MetodoSusticucionModelo {
    
      public double[] resolverSistema(double a1, double b1, double c1, double a2, double b2, double c2) {
        double x, y;

        // Despejamos Y de la primera ecuación: a1 * x + b1 * y = c1
        // y = (c1 - a1 * x) / b1 → sustituimos en segunda ecuación
        if (b1 != 0) {
            // y = (c1 - a1 * x) / b1
            // Sustituimos en segunda ecuación: a2 * x + b2 * [(c1 - a1 * x) / b1] = c2
            // → resolvemos para x
            double numerador = (c2 * b1) - (b2 * c1);
            double denominador = (a2 * b1) - (b2 * a1);

            x = numerador / denominador;
            y = (c1 - a1 * x) / b1;
        } else if (a1 != 0) {
            // x = (c1 - b1 * y) / a1 → sustituimos en segunda ecuación
            double numerador = (c2 * a1) - (a2 * c1);
            double denominador = (b2 * a1) - (a2 * b1);

            y = numerador / denominador;
            x = (c1 - b1 * y) / a1;
        } else {
            throw new ArithmeticException("No se puede resolver con sustitución.");
        }

        return new double[]{x, y};
    } 
  
     // Sustitución para 3 incógnitas
    public double[] resolverSistema3Incognitas(double[][] m, double[] r) {
        // Sistema:
        // m[0] = [a11, a12, a13], r[0]
        // m[1] = [a21, a22, a23], r[1]
        // m[2] = [a31, a32, a33], r[2]

        // Despejamos x de la ecuación 1
        // x = (r1 - a12*y - a13*z)/a11
        // Sustituimos en ecuación 2 y 3 para obtener sistema 2x2 en y y z

        double a11 = m[0][0], a12 = m[0][1], a13 = m[0][2], r1 = r[0];
        double a21 = m[1][0], a22 = m[1][1], a23 = m[1][2], r2 = r[1];
        double a31 = m[2][0], a32 = m[2][1], a33 = m[2][2], r3 = r[2];

        // Sustituimos x en ecuaciones 2 y 3
        // ec2: a21*((r1 - a12*y - a13*z)/a11) + a22*y + a23*z = r2
        // Multiplicamos ambos lados por a11 para eliminar denominadores

        double b1 = a22 - (a21 * a12 / a11);
        double c1 = a23 - (a21 * a13 / a11);
        double d1 = r2 - (a21 * r1 / a11);

        double b2 = a32 - (a31 * a12 / a11);
        double c2 = a33 - (a31 * a13 / a11);
        double d2 = r3 - (a31 * r1 / a11);

        // Sistema reducido 2x2: b1*y + c1*z = d1, b2*y + c2*z = d2

        // Despejamos y
        double z = (d2 * b1 - d1 * b2) / (b1 * c2 - b2 * c1);
        double y = (d1 - c1 * z) / b1;
        double x = (r1 - a12 * y - a13 * z) / a11;

        return new double[]{x, y, z};
    }
    
    
     // Sustitución para 4 incógnitas
    public double[] resolverSistema4Incognitas(double[][] m, double[] r) {
        // Ecuación 1
    double a11 = m[0][0], a12 = m[0][1], a13 = m[0][2], a14 = m[0][3], r1 = r[0];

    // Ecuaciones reducidas 2, 3, 4
    double[] ec2 = new double[3];
    double[] ec3 = new double[3];
    double[] ec4 = new double[3];
    double r2, r3, r4;

    // Para ecuación 2 (fila 1)
    double factor2 = m[1][0] / a11;
    ec2[0] = m[1][1] - factor2 * a12;
    ec2[1] = m[1][2] - factor2 * a13;
    ec2[2] = m[1][3] - factor2 * a14;
    r2 = r[1] - factor2 * r1;

    // Para ecuación 3 (fila 2)
    double factor3 = m[2][0] / a11;
    ec3[0] = m[2][1] - factor3 * a12;
    ec3[1] = m[2][2] - factor3 * a13;
    ec3[2] = m[2][3] - factor3 * a14;
    r3 = r[2] - factor3 * r1;

    // Para ecuación 4 (fila 3)
    double factor4 = m[3][0] / a11;
    ec4[0] = m[3][1] - factor4 * a12;
    ec4[1] = m[3][2] - factor4 * a13;
    ec4[2] = m[3][3] - factor4 * a14;
    r4 = r[3] - factor4 * r1;

    // Creamos la matriz reducida 3x3 para y, z, w
    double[][] m3x3 = { ec2, ec3, ec4 };
    double[] r3x3 = { r2, r3, r4 };

    // Llamamos al método de 3 incógnitas para resolver y, z, w
    double[] yzw = resolverSistema3Incognitas(m3x3, r3x3);
    double y = yzw[0], z = yzw[1], w = yzw[2];

    // Calculamos x usando la ecuación 1 original
    double x = (r1 - a12 * y - a13 * z - a14 * w) / a11;

    return new double[] { x, y, z, w };
    }


}
