/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.InterpolacionLagrangeModelo;
import vista.LinealLagrange;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

public class InterpolacionLangrangeControlador {

    private final LinealLagrange vista;

    public InterpolacionLangrangeControlador(LinealLagrange vista) {
        this.vista = vista;
    }

    public void calcularInterpolacion() {
        try {
            JTable tabla = vista.getTabla1();
            DefaultTableModel model = (DefaultTableModel) tabla.getModel();

            double x;
            try {
                x = Double.parseDouble(vista.getvalorx().getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un valor numérico válido para X a interpolar.");
                return;
            }

            double[] xVals;
            double[] yVals;

            // Verificar si hay datos en la tabla (más de 2 filas llenas)
            int rowCount = model.getRowCount();
            int validRows = 0;

            for (int i = 0; i < rowCount; i++) {
                Object xObj = model.getValueAt(i, 1);
                Object yObj = model.getValueAt(i, 2);
                if (xObj != null && yObj != null && !xObj.toString().isEmpty() && !yObj.toString().isEmpty()) {
                    validRows++;
                }
            }

            // Si hay 2 o más puntos válidos en la tabla, usar esos
            if (validRows >= 2) {
                xVals = new double[validRows];
                yVals = new double[validRows];
                int index = 0;

                for (int i = 0; i < rowCount; i++) {
                    Object xObj = model.getValueAt(i, 1);
                    Object yObj = model.getValueAt(i, 2);
                    if (xObj != null && yObj != null && !xObj.toString().isEmpty() && !yObj.toString().isEmpty()) {
                        xVals[index] = Double.parseDouble(xObj.toString());
                        yVals[index] = Double.parseDouble(yObj.toString());
                        index++;
                    }
                }

            } else {
                // Si la tabla no tiene datos válidos, usar los campos de texto
                double x1 = Double.parseDouble(vista.getvalorx1().getText());
                double y1 = Double.parseDouble(vista.getvalory1().getText());
                double x2 = Double.parseDouble(vista.getvalorx2().getText());
                double y2 = Double.parseDouble(vista.getvalory2().getText());

                xVals = new double[]{x1, x2};
                yVals = new double[]{y1, y2};

                // Llenar la tabla si está vacía (opcional)
                if (model.getRowCount() == 0) {
                    model.setRowCount(2);
                    model.setValueAt("X", 0, 0);
                    model.setValueAt(x1, 0, 1);
                    model.setValueAt(x2, 0, 2);

                    model.setValueAt("Y", 1, 0);
                    model.setValueAt(y1, 1, 1);
                    model.setValueAt(y2, 1, 2);
                }
            }

            InterpolacionLagrangeModelo modelo = new InterpolacionLagrangeModelo(xVals, yVals);
            double resultado = modelo.interpolar(x);
            vista.getresultado().setText(String.format("%.4f", resultado));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al calcular la interpolación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
