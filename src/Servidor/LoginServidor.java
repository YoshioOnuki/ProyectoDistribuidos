package Servidor;

import static Cliente.LoginCliente.ipCliente;
import Cliente.VistaCliente;
import Placeholders.TextPlace;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

/**
 *
 * @author yoshio
 */
public class LoginServidor extends javax.swing.JFrame {

    ImageIcon icoWar = new ImageIcon("src/Imagenes/IconWarning.png");

    public static String ipServidor = "";
    public static String punto = ".";

    public LoginServidor() {
        initComponents();
        setLocationRelativeTo(null);
        placeholders();
        txtip.setText("192.168.1.49");
    }

    void placeholders() {
        Placeholders.TextPlace txtu = new TextPlace("Ingrese IP del Cliente", txtip);
    }

    void validar() {
        String ip = txtip.getText();
        if (ip.isEmpty()) {
            JOptionPane.showMessageDialog(null, "¡Campo de texto IP es requerido!", "¡Advertencia!", JOptionPane.WARNING_MESSAGE, icoWar);
        } else {
            //192.168.1.79
            if (ip.length() < 11) {
                JOptionPane.showMessageDialog(null, "¡Le faltan números a su IP!", "¡Advertencia!", JOptionPane.WARNING_MESSAGE, icoWar);
            } else {
                int cont = 0;
                int punt = 0;
                for (int i = 0; i < ip.length(); i++) {
                    if (ip.charAt(i) == punto.charAt(0)) {
                        punt++;
                        if (i != ip.length() - 1) {
                            if (ip.charAt(i + 1) == punto.charAt(0)) {
                                cont++;
                            }
                        }
                    }
                }
                if (cont > 0) {
                    JOptionPane.showMessageDialog(null, "¡Formato de IP incorrecto!", "¡Advertencia!", JOptionPane.WARNING_MESSAGE, icoWar);
                } else {
                    if (punt < 3) {
                        JOptionPane.showMessageDialog(null, "¡Le faltan números o puntos a su IP!", "¡Advertencia!", JOptionPane.WARNING_MESSAGE, icoWar);
                    } else {
                        if (ip.charAt(ip.length() - 1) == punto.charAt(0)) {
                            JOptionPane.showMessageDialog(null, "¡La IP no puede terminar en un punto!", "¡Advertencia!", JOptionPane.WARNING_MESSAGE, icoWar);
                        } else {
                            ipServidor = txtip.getText();
                            VistaServidor servidor = new VistaServidor();
                            servidor.setVisible(true);
                            dispose();
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Fondo = new javax.swing.JPanel();
        Encabezado = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        PanelBienvenidoCliente = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtip = new javax.swing.JTextField();
        btnIngresar = new javax.swing.JPanel();
        lblAgregar6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Fondo.setBackground(new java.awt.Color(255, 255, 255));
        Fondo.setMaximumSize(new java.awt.Dimension(1000, 660));
        Fondo.setMinimumSize(new java.awt.Dimension(1000, 660));
        Fondo.setName(""); // NOI18N
        Fondo.setPreferredSize(new java.awt.Dimension(1000, 660));

        Encabezado.setBackground(new java.awt.Color(8, 32, 50));
        Encabezado.setMaximumSize(new java.awt.Dimension(1000, 80));
        Encabezado.setMinimumSize(new java.awt.Dimension(1000, 80));

        jLabel3.setBackground(new java.awt.Color(8, 32, 50));
        jLabel3.setFont(new java.awt.Font("SF UI Display", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("BIENVENIDO");
        jLabel3.setMaximumSize(new java.awt.Dimension(1050, 80));
        jLabel3.setMinimumSize(new java.awt.Dimension(1050, 80));
        jLabel3.setPreferredSize(new java.awt.Dimension(1050, 80));

        javax.swing.GroupLayout EncabezadoLayout = new javax.swing.GroupLayout(Encabezado);
        Encabezado.setLayout(EncabezadoLayout);
        EncabezadoLayout.setHorizontalGroup(
            EncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, Short.MAX_VALUE)
        );
        EncabezadoLayout.setVerticalGroup(
            EncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EncabezadoLayout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        PanelBienvenidoCliente.setBackground(new java.awt.Color(177, 208, 224));
        PanelBienvenidoCliente.setMaximumSize(new java.awt.Dimension(360, 520));
        PanelBienvenidoCliente.setMinimumSize(new java.awt.Dimension(360, 520));

        jLabel4.setFont(new java.awt.Font("SF UI Display", 1, 22)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("SERVIDOR");

        jPanel3.setBackground(new java.awt.Color(167, 183, 191));
        jPanel3.setMaximumSize(new java.awt.Dimension(245, 50));
        jPanel3.setMinimumSize(new java.awt.Dimension(245, 50));
        jPanel3.setVerifyInputWhenFocusTarget(false);

        txtip.setBackground(new java.awt.Color(167, 183, 191));
        txtip.setFont(new java.awt.Font("SF UI Display", 0, 16)); // NOI18N
        txtip.setBorder(null);
        txtip.setMaximumSize(new java.awt.Dimension(187, 28));
        txtip.setMinimumSize(new java.awt.Dimension(187, 28));
        txtip.setPreferredSize(new java.awt.Dimension(187, 28));
        txtip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtipActionPerformed(evt);
            }
        });
        txtip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtipKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtipKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(txtip, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(txtip, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnIngresar.setBackground(new java.awt.Color(8, 32, 50));
        btnIngresar.setMaximumSize(new java.awt.Dimension(180, 50));
        btnIngresar.setMinimumSize(new java.awt.Dimension(180, 50));
        btnIngresar.setPreferredSize(new java.awt.Dimension(180, 50));
        btnIngresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnIngresarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnIngresarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnIngresarMouseExited(evt);
            }
        });

        lblAgregar6.setFont(new java.awt.Font("SF UI Display", 1, 20)); // NOI18N
        lblAgregar6.setForeground(new java.awt.Color(255, 255, 255));
        lblAgregar6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblAgregar6.setText("Ingresar");

        javax.swing.GroupLayout btnIngresarLayout = new javax.swing.GroupLayout(btnIngresar);
        btnIngresar.setLayout(btnIngresarLayout);
        btnIngresarLayout.setHorizontalGroup(
            btnIngresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
            .addGroup(btnIngresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnIngresarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(lblAgregar6, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        btnIngresarLayout.setVerticalGroup(
            btnIngresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
            .addGroup(btnIngresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnIngresarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(lblAgregar6, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jLabel1.setFont(new java.awt.Font("SF UI Display", 0, 16)); // NOI18N
        jLabel1.setText("IP Cliente:");

        javax.swing.GroupLayout PanelBienvenidoClienteLayout = new javax.swing.GroupLayout(PanelBienvenidoCliente);
        PanelBienvenidoCliente.setLayout(PanelBienvenidoClienteLayout);
        PanelBienvenidoClienteLayout.setHorizontalGroup(
            PanelBienvenidoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBienvenidoClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelBienvenidoClienteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PanelBienvenidoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(PanelBienvenidoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelBienvenidoClienteLayout.createSequentialGroup()
                            .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(31, 31, 31))
                        .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(56, 56, 56))
        );
        PanelBienvenidoClienteLayout.setVerticalGroup(
            PanelBienvenidoClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelBienvenidoClienteLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jLabel4)
                .addGap(70, 70, 70)
                .addComponent(jLabel1)
                .addGap(20, 20, 20)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(btnIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout FondoLayout = new javax.swing.GroupLayout(Fondo);
        Fondo.setLayout(FondoLayout);
        FondoLayout.setHorizontalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Encabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(FondoLayout.createSequentialGroup()
                .addGap(320, 320, 320)
                .addComponent(PanelBienvenidoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        FondoLayout.setVerticalGroup(
            FondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(FondoLayout.createSequentialGroup()
                .addComponent(Encabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(PanelBienvenidoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(Fondo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Fondo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtipActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtipActionPerformed

    private void txtipKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtipKeyTyped
        char c = evt.getKeyChar();
        if (c < '0' || c > '9') {
            if (c == '.') {

            } else {
                evt.consume();
            }
        }
        if (txtip.getText().length() >= 15) {
            evt.consume();
        }
    }//GEN-LAST:event_txtipKeyTyped

    private void btnIngresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIngresarMouseClicked
        validar();
    }//GEN-LAST:event_btnIngresarMouseClicked

    private void btnIngresarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIngresarMouseEntered
        btnIngresar.setBackground(new Color(26, 77, 115));
    }//GEN-LAST:event_btnIngresarMouseEntered

    private void btnIngresarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnIngresarMouseExited
        btnIngresar.setBackground(new Color(8, 32, 50));
    }//GEN-LAST:event_btnIngresarMouseExited

    private void txtipKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtipKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            validar();
        }
    }//GEN-LAST:event_txtipKeyReleased

    public static void main(String args[]) {

        try {
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencySmallShadow;
            BeautyEyeLNFHelper.translucencyAtFrameInactive = false;
            UIManager.put("RootPane.setupButtonVisible", false);
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();

        } catch (Exception e) {
            System.out.println(e);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginServidor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Encabezado;
    public static javax.swing.JPanel Fondo;
    private javax.swing.JPanel PanelBienvenidoCliente;
    private javax.swing.JPanel btnIngresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblAgregar6;
    private javax.swing.JTextField txtip;
    // End of variables declaration//GEN-END:variables
}
