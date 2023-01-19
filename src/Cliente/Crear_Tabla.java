
package Cliente;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Susana
 */
public class Crear_Tabla extends javax.swing.JPanel {

    String barra = File.separator;
    String crearUbicacion = System.getProperty("user.dir")+barra+"dbDistribuidos"+barra;
    
    FileOutputStream fos;
    
    public Crear_Tabla() {
        initComponents();
        System.out.println();
    }

    private void crearTxt(){
        String archivo = txtNombreTabla.getText() + ".txt";
        String contenido = ""+txtAtri1.getText()+"\t"+txtAtri2.getText()+"\t";
        File crearUbi = new File(crearUbicacion);
        File crearArchi = new File(crearUbicacion+archivo);
        
        if(txtNombreTabla.getText().isEmpty() || txtAtri1.getText().isEmpty() || txtAtri2.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Campos requeridos vacios");
            if(txtNombreTabla.getText().isEmpty()){
                txtNombreTabla.requestFocus();
            }else if(txtAtri1.getText().isEmpty()){
                txtAtri1.requestFocus();
            }else if(txtAtri2.getText().isEmpty()){
                txtAtri2.requestFocus();
            }
        }else{
            try {
                if(crearArchi.exists()){
                    JOptionPane.showMessageDialog(null, "La tabla ya se encuentra registrada");
                }else{
                    crearUbi.mkdirs();
                    crearArchi.createNewFile();
                    
                    String crearUbicacionBackup = System.getProperty("user.dir")+barra+"dbDistribuidos"+barra+"backup";
                    JfCliente.puntero = 0;
                    JfCliente.datos[0][JfCliente.puntero] = txtAtri1.getText();
                    JfCliente.datos[1][JfCliente.puntero] = txtAtri2.getText();
                    String archivoBackup = "backup.txt";
                    String conte = "";
                    for (int i = 0; i < 2; i++) {
                        conte = JfCliente.datos[0][i]+"\t";
                    }
                    File crearUbiBackup = new File(crearUbicacionBackup);
                    File crearArchiBackup = new File(crearUbicacionBackup+archivoBackup);
                    
                    crearUbiBackup.mkdirs();
                    crearArchiBackup.createNewFile();
                    int respuesta1 = escribir(crearArchiBackup, conte);
                    
                    int respuesta2 = escribir(crearArchi, contenido);
                    
                    JOptionPane.showMessageDialog(null, "Directorios creados correctamente");
                    limpiar();
//                    JfCliente.PanelPrincipal.removeAll();
//                    JfCliente.PanelPrincipal.revalidate();
//                    JfCliente.PanelPrincipal.repaint();
                }

            } catch (Exception e) {
                System.out.println("Error al crear directorios"+e);
            }
        }
    }
    
    void limpiar(){
        txtNombreTabla.setText("");
        txtAtri1.setText("");
        txtAtri2.setText("");
    }
    
    
    public int escribir(File file, String contenido){
        int r = 0;
        
        if(file != null){
            try {
                fos = new FileOutputStream(file);
                byte[] bytes= contenido.getBytes();
                fos.write(bytes);
                fos.close();
            } catch (FileNotFoundException ex) {
                System.out.println("Error 3: "+ex.getMessage());
            } catch (IOException ex) {
                System.out.println("Error 3: "+ex.getMessage());
            }
        }
        
        return r;
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        btnCrear = new javax.swing.JButton();
        txtNombreTabla = new javax.swing.JTextField();
        txtAtri1 = new javax.swing.JTextField();
        txtAtri2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setMaximumSize(new java.awt.Dimension(688, 343));
        setMinimumSize(new java.awt.Dimension(688, 343));
        setPreferredSize(new java.awt.Dimension(688, 343));

        jLabel1.setFont(new java.awt.Font("SF UI Display", 1, 16)); // NOI18N
        jLabel1.setText("CREAR TABLA");

        btnCrear.setText("CREAR");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
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
        txtAtri1.setMaximumSize(new java.awt.Dimension(6, 29));
        txtAtri1.setMinimumSize(new java.awt.Dimension(6, 29));
        txtAtri1.setPreferredSize(new java.awt.Dimension(6, 29));

        txtAtri2.setFont(new java.awt.Font("SF UI Display", 0, 14)); // NOI18N
        txtAtri2.setMaximumSize(new java.awt.Dimension(6, 29));
        txtAtri2.setMinimumSize(new java.awt.Dimension(6, 29));
        txtAtri2.setPreferredSize(new java.awt.Dimension(6, 29));

        jLabel2.setFont(new java.awt.Font("SF UI Display", 0, 14)); // NOI18N
        jLabel2.setText("Nombre de la tabla:");

        jLabel3.setFont(new java.awt.Font("SF UI Display", 0, 14)); // NOI18N
        jLabel3.setText("Atributo 1:");

        jLabel4.setFont(new java.awt.Font("SF UI Display", 0, 14)); // NOI18N
        jLabel4.setText("Atributo 2:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(308, Short.MAX_VALUE)
                .addComponent(btnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(293, 293, 293))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(286, 286, 286)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombreTabla, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                            .addComponent(txtAtri1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtAtri2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreTabla, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAtri1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAtri2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(30, 30, 30)
                .addComponent(btnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreTablaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreTablaActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        crearTxt();
    }//GEN-LAST:event_btnCrearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCrear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField txtAtri1;
    private javax.swing.JTextField txtAtri2;
    private javax.swing.JTextField txtNombreTabla;
    // End of variables declaration//GEN-END:variables
}
