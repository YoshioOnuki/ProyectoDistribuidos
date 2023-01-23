
package Servidor;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author yoshi
 */
public class Agregar extends javax.swing.JPanel {

    String barra = File.separator;
    String nombreTabla = "";
    
    public Agregar() {
        initComponents();
    }
    
    public void leerDatos(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader bf = new BufferedReader(new InputStreamReader(fis, "utf-8"));

            String cadena = "";

            while (true) {
                cadena = bf.readLine();
                if (cadena != null) {
                    System.out.println("" + cadena);
                } else {
                    break;
                }
            }
            fis.close();
            bf.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no ubicado");
        } catch (IOException ex) {
            System.out.println("Errir de I/0");
        }
    }
    
    void buscarFile(){
        nombreTabla = txtNombreTabla.getText()+".txt";
        String ubicacion = System.getProperty("user.dir")+barra+"dbDistribuidos"+barra;
        System.out.println(nombreTabla);
        File file = new File(ubicacion+nombreTabla);
        if(file.exists()){
            leerDatos(file);
            String a1 = "";
            String a2 = "";


            txtNombreTabla.setEnabled(false);
            txtAtri1.setEnabled(true);
            txtAtri2.setEnabled(true);
            lblAtri1.setText(a1);
            lblAtri2.setText(a2);
            
        }else{
            System.out.println("no encontrado");
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        txtNombreTabla = new javax.swing.JTextField();
        txtAtri1 = new javax.swing.JTextField();
        txtAtri2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        lblAtri1 = new javax.swing.JLabel();
        lblAtri2 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setMaximumSize(new java.awt.Dimension(688, 343));
        setMinimumSize(new java.awt.Dimension(688, 343));
        setPreferredSize(new java.awt.Dimension(688, 343));

        jLabel1.setFont(new java.awt.Font("SF UI Display", 1, 16)); // NOI18N
        jLabel1.setText("AGREGAR EN TABLA");

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        txtNombreTabla.setFont(new java.awt.Font("SF UI Display", 0, 14)); // NOI18N
        txtNombreTabla.setMaximumSize(new java.awt.Dimension(6, 29));
        txtNombreTabla.setMinimumSize(new java.awt.Dimension(6, 29));
        txtNombreTabla.setPreferredSize(new java.awt.Dimension(6, 29));
        txtNombreTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreTablaActionPerformed(evt);
            }
        });

        txtAtri1.setFont(new java.awt.Font("SF UI Display", 0, 14)); // NOI18N
        txtAtri1.setEnabled(false);
        txtAtri1.setMaximumSize(new java.awt.Dimension(6, 29));
        txtAtri1.setMinimumSize(new java.awt.Dimension(6, 29));
        txtAtri1.setPreferredSize(new java.awt.Dimension(6, 29));

        txtAtri2.setFont(new java.awt.Font("SF UI Display", 0, 14)); // NOI18N
        txtAtri2.setEnabled(false);
        txtAtri2.setMaximumSize(new java.awt.Dimension(6, 29));
        txtAtri2.setMinimumSize(new java.awt.Dimension(6, 29));
        txtAtri2.setPreferredSize(new java.awt.Dimension(6, 29));

        jLabel2.setFont(new java.awt.Font("SF UI Display", 0, 14)); // NOI18N
        jLabel2.setText("Nombre de la tabla:");

        lblAtri1.setFont(new java.awt.Font("SF UI Display", 0, 14)); // NOI18N
        lblAtri1.setText("Atributo 1:");

        lblAtri2.setFont(new java.awt.Font("SF UI Display", 0, 14)); // NOI18N
        lblAtri2.setText("Atributo 2:");

        btnBuscar.setBackground(new java.awt.Color(51, 71, 86));
        btnBuscar.setMaximumSize(new java.awt.Dimension(30, 29));
        btnBuscar.setMinimumSize(new java.awt.Dimension(30, 29));
        btnBuscar.setPreferredSize(new java.awt.Dimension(30, 29));
        btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnBuscarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnBuscarMouseExited(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Iconlupa.png"))); // NOI18N
        jLabel3.setMaximumSize(new java.awt.Dimension(30, 29));
        jLabel3.setMinimumSize(new java.awt.Dimension(30, 29));
        jLabel3.setPreferredSize(new java.awt.Dimension(30, 29));

        javax.swing.GroupLayout btnBuscarLayout = new javax.swing.GroupLayout(btnBuscar);
        btnBuscar.setLayout(btnBuscarLayout);
        btnBuscarLayout.setHorizontalGroup(
            btnBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnBuscarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        btnBuscarLayout.setVerticalGroup(
            btnBuscarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnBuscarLayout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(263, 263, 263)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(lblAtri2)
                                    .addComponent(lblAtri1))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNombreTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                                    .addComponent(txtAtri1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtAtri2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, 0)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(109, 269, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNombreTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAtri1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAtri1))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAtri2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblAtri2))
                .addGap(30, 30, 30)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtNombreTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreTablaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreTablaActionPerformed

    private void btnBuscarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseExited
        // TODO add your handling code here:
        btnBuscar.setBackground(new Color(51,71,86));
    }//GEN-LAST:event_btnBuscarMouseExited

    private void btnBuscarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseEntered
        // TODO add your handling code here:
        btnBuscar.setBackground(new Color(48, 105, 147));
    }//GEN-LAST:event_btnBuscarMouseEntered

    private void btnBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseClicked
        buscarFile();
    }//GEN-LAST:event_btnBuscarMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnBuscar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblAtri1;
    private javax.swing.JLabel lblAtri2;
    private javax.swing.JTextField txtAtri1;
    private javax.swing.JTextField txtAtri2;
    private javax.swing.JTextField txtNombreTabla;
    // End of variables declaration//GEN-END:variables
}
