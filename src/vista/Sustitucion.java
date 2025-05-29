/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.MetodoSustitucionControlador;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import modelo.MetodoSusticucionModelo;
import practica.mvc.PracticaMVC;

/**
 *
 * @author moralesjs_
 */
public class Sustitucion extends javax.swing.JFrame {
    private JTextField[][] campos3 = new JTextField[3][3];
    private JTextField[] resultados3 = new JTextField[3];
    
    private JTextField[] resultados4 = new JTextField[3];
    private JTextField[][] campos4 = new JTextField[4][4];
    private JTextField txtCampo4_21 = new JTextField(); // Faltaba este

    
// --- 2 incógnitas ---
    public JTextField txtA1, txtB1, txtC1;
    public JTextField txtA2, txtB2, txtC2;
    public JButton btnCalcular2;
    public JTextArea resultadoArea2;

    // --- 3 incógnitas ---
    public JTextField txtA1_3, txtB1_3, txtC1_3, txtD1_3;
    public JTextField txtA2_3, txtB2_3, txtC2_3, txtD2_3;
    public JTextField txtA3_3, txtB3_3, txtC3_3, txtD3_3;
    public JButton btnCalcular3;
    public JTextArea resultadoArea3;

    // --- 4 incógnitas ---
    public JTextField txtA1_4, txtB1_4, txtC1_4, txtD1_4, txtE1_4;
    public JTextField txtA2_4, txtB2_4, txtC2_4, txtD2_4, txtE2_4;
    public JTextField txtA3_4, txtB3_4, txtC3_4, txtD3_4, txtE3_4;
    public JTextField txtA4_4, txtB4_4, txtC4_4, txtD4_4, txtE4_4;
    public JButton btnCalcular4;
    public JTextArea resultadoArea4;
    private JTextField txtB3_1 = new JTextField();
    private JTextField txtC3_1 = new JTextField();
    private JTextField txtD3_1 = new JTextField();
    private JTextField txtA3_2 = new JTextField();
    private JTextField txtA4_1 = new JTextField();
    private JTextField txtA4_2 = new JTextField();
    private JTextField txtB4_1 = new JTextField();
    private JTextField txtC4_1 = new JTextField();
    private JTextField txtE4_3 = new JTextField();
    private JTextField txtB3_2 = new JTextField();
    private JTextField txtC3_2 = new JTextField();
    private JTextField txtD3_2 = new JTextField();
    private JTextField txtA3_1 = new JTextField(); //------------------
    private JTextField txtD4_1 = new JTextField(); //------------------
    private JTextField txtE4_1 = new JTextField(); //------------------
    private JTextField txtA4_3 = new JTextField(); //------------------
    private JTextField txtB4_3 = new JTextField(); //------------------
    private JTextField txtC4_3 = new JTextField(); //------------------
    private JTextField txtD4_3 = new JTextField(); //------------------
    private JTextField txtE4_2 = new JTextField(); //------------------
    private JTextField txtD4_2 = new JTextField(); //------------------
    private JTextField txtC4_2 = new JTextField(); 
    private JTextField txtB4_2 = new JTextField(); 
    
    
    private JTextField txtCampo4_00 = new JTextField(); //------------------
    private JTextField txtCampo4_01 = new JTextField(); //------------------
    private JTextField txtCampo4_02 = new JTextField(); //------------------
    private JTextField txtCampo4_03 = new JTextField(); //------------------
    private JTextField txtCampo4_10 = new JTextField(); //------------------
    private JTextField txtCampo4_11 = new JTextField(); //------------------
    private JTextField txtCampo4_12 = new JTextField(); //------------------
    private JTextField txtCampo4_13 = new JTextField(); //------------------
    private JTextField txtCampo4_20 = new JTextField(); //------------------
    private JTextField txtCampo4_22 = new JTextField(); //------------------
    private JTextField txtCampo4_23 = new JTextField(); //------------------
    private JTextField txtCampo4_30 = new JTextField(); //------------------
    private JTextField txtCampo4_31 = new JTextField(); //------------------
    private JTextField txtCampo4_32 = new JTextField(); //------------------
    private JTextField txtCampo4_33 = new JTextField(); //------------------
    
    
    public void inicializarCampos4() {
    campos4[0][0] = txtCampo4_00;
    campos4[0][1] = txtCampo4_01;
    campos4[0][2] = txtCampo4_02;
    campos4[0][3] = txtCampo4_03;

    campos4[1][0] = txtCampo4_10;
    campos4[1][1] = txtCampo4_11;
    campos4[1][2] = txtCampo4_12;
    campos4[1][3] = txtCampo4_13;

    campos4[2][0] = txtCampo4_20;
    campos4[2][1] = txtCampo4_21;
    campos4[2][2] = txtCampo4_22;
    campos4[2][3] = txtCampo4_23;

    campos4[3][0] = txtCampo4_30;
    campos4[3][1] = txtCampo4_31;
    campos4[3][2] = txtCampo4_32;
    campos4[3][3] = txtCampo4_33;
}

    public Sustitucion() {

        setTitle("Método de Sustitución");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();

        // Panel para 2 incógnitas
        JPanel panel2 = new JPanel(new GridLayout(4, 1));
        JPanel eq1_2 = new JPanel();
        txtA1 = new JTextField(3);
        txtB1 = new JTextField(3);
        txtC1 = new JTextField(3);
        
        eq1_2.add(new JLabel("Ecuación 1:"));
        eq1_2.add(txtA1);
        eq1_2.add(new JLabel("X +"));
        eq1_2.add(txtB1);
        eq1_2.add(new JLabel("Y ="));
        eq1_2.add(txtC1);
        JPanel eq2_2 = new JPanel();
        txtA2 = new JTextField(3);
        txtB2 = new JTextField(3);
        txtC2 = new JTextField(3);
        eq2_2.add(new JLabel("Ecuación 2:"));
        eq2_2.add(txtA2);
        eq2_2.add(new JLabel("X +"));
        eq2_2.add(txtB2);
        eq2_2.add(new JLabel("Y ="));
        eq2_2.add(txtC2);
        btnCalcular2 = new JButton("Calcular");
        resultadoArea2 = new JTextArea(3, 40);
        resultadoArea2.setEditable(false);
        panel2.add(eq1_2);
        panel2.add(eq2_2);
        panel2.add(btnCalcular2);
        panel2.add(new JScrollPane(resultadoArea2));

        // Panel para 3 incógnitas
        JPanel panel3 = new JPanel(new GridLayout(5, 1));
        JPanel eq1_3 = new JPanel();
        txtA1_3 = new JTextField(3);
        txtB1_3 = new JTextField(3);
        txtC1_3 = new JTextField(3);
        txtD1_3 = new JTextField(3);
        eq1_3.add(new JLabel("Ecuación 1:"));
        eq1_3.add(txtA1_3); eq1_3.add(new JLabel("X +"));
        eq1_3.add(txtB1_3); eq1_3.add(new JLabel("Y +"));
        eq1_3.add(txtC1_3); eq1_3.add(new JLabel("Z ="));
        eq1_3.add(txtD1_3);
        JPanel eq2_3 = new JPanel();
        txtA2_3 = new JTextField(3); txtB2_3 = new JTextField(3);
        txtC2_3 = new JTextField(3); txtD2_3 = new JTextField(3);
        eq2_3.add(new JLabel("Ecuación 2:"));
        eq2_3.add(txtA2_3); eq2_3.add(new JLabel("X +"));
        eq2_3.add(txtB2_3); eq2_3.add(new JLabel("Y +"));
        eq2_3.add(txtC2_3); eq2_3.add(new JLabel("Z ="));
        eq2_3.add(txtD2_3);
        JPanel eq3_3 = new JPanel();
        txtA3_3 = new JTextField(3); txtB3_3 = new JTextField(3);
        txtC3_3 = new JTextField(3); txtD3_3 = new JTextField(3);
        eq3_3.add(new JLabel("Ecuación 3:"));
        eq3_3.add(txtA3_3); eq3_3.add(new JLabel("X +"));
        eq3_3.add(txtB3_3); eq3_3.add(new JLabel("Y +"));
        eq3_3.add(txtC3_3); eq3_3.add(new JLabel("Z ="));
        eq3_3.add(txtD3_3);
        btnCalcular3 = new JButton("Calcular");
        resultadoArea3 = new JTextArea(3, 40);
        resultadoArea3.setEditable(false);
        panel3.add(eq1_3); panel3.add(eq2_3); panel3.add(eq3_3);
        panel3.add(btnCalcular3);
        panel3.add(new JScrollPane(resultadoArea3));

        // Panel para 4 incógnitas
        JPanel panel4 = new JPanel(new GridLayout(6, 1));
        txtA1_4 = new JTextField(3); txtB1_4 = new JTextField(3); txtC1_4 = new JTextField(3);
        txtD1_4 = new JTextField(3); txtE1_4 = new JTextField(3);
        txtA2_4 = new JTextField(3); txtB2_4 = new JTextField(3); txtC2_4 = new JTextField(3);
        txtD2_4 = new JTextField(3); txtE2_4 = new JTextField(3);
        txtA3_4 = new JTextField(3); txtB3_4 = new JTextField(3); txtC3_4 = new JTextField(3);
        txtD3_4 = new JTextField(3); txtE3_4 = new JTextField(3);
        txtA4_4 = new JTextField(3); txtB4_4 = new JTextField(3); txtC4_4 = new JTextField(3);
        txtD4_4 = new JTextField(3); txtE4_4 = new JTextField(3);

        JPanel eq1_4 = new JPanel(); eq1_4.add(new JLabel("Ecuación 1:"));
        eq1_4.add(txtA1_4); eq1_4.add(new JLabel("X +"));
        eq1_4.add(txtB1_4); eq1_4.add(new JLabel("Y +"));
        eq1_4.add(txtC1_4); eq1_4.add(new JLabel("Z +"));
        eq1_4.add(txtD1_4); eq1_4.add(new JLabel("W ="));
        eq1_4.add(txtE1_4);

        JPanel eq2_4 = new JPanel(); eq2_4.add(new JLabel("Ecuación 2:"));
        eq2_4.add(txtA2_4); eq2_4.add(new JLabel("X +"));
        eq2_4.add(txtB2_4); eq2_4.add(new JLabel("Y +"));
        eq2_4.add(txtC2_4); eq2_4.add(new JLabel("Z +"));
        eq2_4.add(txtD2_4); eq2_4.add(new JLabel("W ="));
        eq2_4.add(txtE2_4);

        JPanel eq3_4 = new JPanel(); eq3_4.add(new JLabel("Ecuación 3:"));
        eq3_4.add(txtA3_4); eq3_4.add(new JLabel("X +"));
        eq3_4.add(txtB3_4); eq3_4.add(new JLabel("Y +"));
        eq3_4.add(txtC3_4); eq3_4.add(new JLabel("Z +"));
        eq3_4.add(txtD3_4); eq3_4.add(new JLabel("W ="));
        eq3_4.add(txtE3_4);

        JPanel eq4_4 = new JPanel(); eq4_4.add(new JLabel("Ecuación 4:"));
        eq4_4.add(txtA4_4); eq4_4.add(new JLabel("X +"));
        eq4_4.add(txtB4_4); eq4_4.add(new JLabel("Y +"));
        eq4_4.add(txtC4_4); eq4_4.add(new JLabel("Z +"));
        eq4_4.add(txtD4_4); eq4_4.add(new JLabel("W ="));
        eq4_4.add(txtE4_4);

        btnCalcular4 = new JButton("Calcular");
        resultadoArea4 = new JTextArea(3, 40);
        resultadoArea4.setEditable(false);
        panel4.add(eq1_4); panel4.add(eq2_4);
        panel4.add(eq3_4); panel4.add(eq4_4);
        panel4.add(btnCalcular4);
        panel4.add(new JScrollPane(resultadoArea4));

        // Añadir pestañas
        tabs.addTab("2 incógnitas", panel2);
        tabs.addTab("3 incógnitas", panel3);
        tabs.addTab("4 incógnitas", panel4);
        add(tabs);
        
        new MetodoSustitucionControlador(null, this);
        
    }
        
       
        
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 51, 102));
        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 420));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
       
    
    
    
    
    /**
     * @param args the command line arguments
     */
        
    public static void main(String args[]) {
        
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sustitucion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sustitucion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sustitucion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sustitucion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sustitucion().setVisible(true);
            }
        });
    }
    
    
// Getters para 2 incógnitas
public JTextField getTxtA1() {
    return txtA1;
}

public JTextField getTxtB1() {
    return txtB1;
}

public JTextField getTxtC1() {
    return txtC1;
}

public JTextField getTxtA2() {
    return txtA2;
}

public JTextField getTxtB2() {
    return txtB2;
}

public JTextField getTxtC2() {
    return txtC2;
}

public JButton getBtnCalcular2() {
    return btnCalcular2;
}

public JTextArea getResultadoArea2() {
    return resultadoArea2;
}

// Getters para 3 incógnitas
public JTextField getTxtA3_1() {
    return txtA3_1;
}

public JTextField getTxtB3_1() {
    return txtB3_1;
}

public JTextField getTxtC3_1() {
    return txtC3_1;
}

public JTextField getTxtD3_1() {
    return txtD3_1;
}

public JTextField getTxtA3_2() {
    return txtA3_2;
}

public JTextField getTxtB3_2() {
    return txtB3_2;
}

public JTextField getTxtC3_2() {
    return txtC3_2;
}

public JTextField getTxtD3_2() {
    return txtD3_2;
}

public JTextField getTxtA3_3() {
    return txtA3_3;
}

public JTextField getTxtB3_3() {
    return txtB3_3;
}

public JTextField getTxtC3_3() {
    return txtC3_3;
}

public JTextField getTxtD3_3() {
    return txtD3_3;
}

public JButton getBtnCalcular3() {
    return btnCalcular3;
}

public JTextArea getResultadoArea3() {
    return resultadoArea3;
}

// Getters para 4 incógnitas
public JTextField getTxtA4_1() {
    return txtA4_1;
}

public JTextField getTxtB4_1() {
    return txtB4_1;
}

public JTextField getTxtC4_1() {
    return txtC4_1;
}

public JTextField getTxtD4_1() {
    return txtD4_1;
}

public JTextField getTxtE4_1() {
    return txtE4_1;
}

public JTextField getTxtA4_2() {
    return txtA4_2;
}

public JTextField getTxtB4_2() {
    return txtB4_2;
}

public JTextField getTxtC4_2() {
    return txtC4_2;
}

public JTextField getTxtD4_2() {
    return txtD4_2;
}

public JTextField getTxtE4_2() {
    return txtE4_2;
}

public JTextField getTxtA4_3() {
    return txtA4_3;
}

public JTextField getTxtB4_3() {
    return txtB4_3;
}

public JTextField getTxtC4_3() {
    return txtC4_3;
}

public JTextField getTxtD4_3() {
    return txtD4_3;
}

public JTextField getTxtE4_3() {
    return txtE4_3;
}

public JTextField getTxtA4_4() {
    return txtA4_4;
}

public JTextField getTxtB4_4() {
    return txtB4_4;
}

public JTextField getTxtC4_4() {
    return txtC4_4;
}

public JTextField getTxtD4_4() {
    return txtD4_4;
}

public JTextField getTxtE4_4() {
    return txtE4_4;
}

public JButton getBtnCalcular4() {
    return btnCalcular4;
}

public JTextArea getResultadoArea4() {
    return resultadoArea4;
}


public JTextField getCampo3(int fila, int columna) {
    return campos3[fila][columna]; // Asegúrate de tener esta matriz de campos creada.
}

public JTextField getCampoResultado3(int fila) {
    return resultados3[fila]; // Asegúrate de tener este arreglo de campos creado.
}



public JTextField getCampo4(int fila, int columna) {
    return campos4[fila][columna]; // Asegúrate de tener esta matriz de campos creada.
}

public JTextField getCampoResultado4(int fila) {
    return resultados4[fila]; // Asegúrate de tener este arreglo de campos creado.
}




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
