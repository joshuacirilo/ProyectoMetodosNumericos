/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import modelo.MetodoSusticucionModelo;
import vista.Sustitucion;
import javax.swing.JOptionPane;

/**
 *
 * @author moralesjs_
 */
public class MetodoSustitucionControlador {
   private MetodoSusticucionModelo modelo;
    private Sustitucion vista;

    public MetodoSustitucionControlador(MetodoSusticucionModelo modelo, Sustitucion vista) {
        this.modelo = modelo;
        this.vista = vista;

        // Acción para 2 incógnitas
        vista.getBtnCalcular2().addActionListener(e -> {
            try {
                double a1 = Double.parseDouble(vista.getTxtA1().getText());
                double b1 = Double.parseDouble(vista.getTxtB1().getText());
                double c1 = Double.parseDouble(vista.getTxtC1().getText());
                double a2 = Double.parseDouble(vista.getTxtA2().getText());
                double b2 = Double.parseDouble(vista.getTxtB2().getText());
                double c2 = Double.parseDouble(vista.getTxtC2().getText());

                double[] resultado = modelo.resolverSistema(a1, b1, c1, a2, b2, c2);
                vista.getResultadoArea2().setText("x = " + resultado[0] + "\ny = " + resultado[1]);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error en los datos de entrada para 2 incógnitas.");
            }
        });

        // Acción para 3 incógnitas
        vista.getBtnCalcular3().addActionListener(e -> {
            try {
                double[][] matriz = new double[3][3];
                double[] r = new double[3];

                for (int i = 0; i < 3; i++) {
                    matriz[i][0] = Double.parseDouble(vista.getCampo3(i, 0).getText());
                    matriz[i][1] = Double.parseDouble(vista.getCampo3(i, 1).getText());
                    matriz[i][2] = Double.parseDouble(vista.getCampo3(i, 2).getText());
                    r[i] = Double.parseDouble(vista.getCampoResultado3(i).getText());
                }

                double[] resultado = modelo.resolverSistema3Incognitas(matriz, r);
                vista.getResultadoArea3().setText("x = " + resultado[0] + "\ny = " + resultado[1] + "\nz = " + resultado[2]);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error en los datos de entrada para 3 incógnitas.");
            }
        });
        

        // Acción para 4 incógnitas
vista.getBtnCalcular4().addActionListener(e -> {
            try {
                double[][] matriz = new double[4][4];
                double[] r = new double[4];

                for (int i = 0; i < 4; i++) {
                    for (int j = 0; j < 4; j++) {
                        matriz[i][j] = Double.parseDouble(vista.getCampo4(i, j).getText());
                    }
                    r[i] = Double.parseDouble(vista.getCampoResultado4(i).getText()); // <-- ESTA LÍNEA FALTABA
                }

                double[] resultado = modelo.resolverSistema4Incognitas(matriz, r);
                vista.getResultadoArea4().setText(
                        "x = " + resultado[0] + "\ny = " + resultado[1] + "\nz = " + resultado[2] + "\nw = " + resultado[3]);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error en los datos de entrada para 4 incógnitas.");
            }
        });
    }
}
