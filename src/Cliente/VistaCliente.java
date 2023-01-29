package Cliente;

import Servidor.*;
import static Servidor.VistaServidor.nombreTabla;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author yoshio
 */
public class VistaCliente extends javax.swing.JFrame implements Observer{
    
    //Inicializamos un Icono de alerta para mis alertar JOptionPane
    ImageIcon icoWar = new ImageIcon("src/Imagenes/IconWarning.png");
    
    //Inicializamos variables estáticas para el manejo de datos
    public static String men = "";
    public static String ip = LoginCliente.ipCliente;

    public VistaCliente() {
        initComponents();
        setLocationRelativeTo(null);
        this.getRootPane().setDefaultButton(this.btnEnviar);
        initService();
        error();
        lblLoading.setVisible(false);
        txtMensaje.requestFocus();
        System.out.println(ip);
    }
    
    //Inicializamos al servidor
    void initService(){
        Servidor s = new Servidor(5050);
        s.addObserver(this);
        Thread t = new Thread (s);
        t.start();
    }
    
    //Validamos y enviamos el mensaje o la consulta al servidor de base de datos TXT
    void enviar(){
        if(txtMensaje.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Campo de texto vacío.", "¡Advertencia!",JOptionPane.WARNING_MESSAGE, icoWar);
            txtMensaje.requestFocus();
        }else{
            if(txtMensaje.getText().equalsIgnoreCase("cls")){
                txtArea.setText("");
                lblLoading.setVisible(false);
                limpiarError();
                txtMensaje.setText("");
                txtMensaje.requestFocus();
            }else{
                String mensaje = "" + this.txtMensaje.getText() + "\n";
                //this.txtArea.setText(mensaje);
                this.txtArea.setText(mensaje);
                this.txtMensaje.setText("");

                new Thread(){
                    public void run(){
                        txtArea.setText("");
                        lblLoading.setVisible(true);
                        txtError.setForeground(Color.black);
                        txtError.setText("Cargando...");
                        proceso(10);
                        error();
                    }
                }.start();

                txtMensaje.requestFocus();
                limpiarError();

                Cliente c = new Cliente(this.ip,5050,mensaje);
                Thread t = new Thread(c); 
                t.start();
            }
        }
    }
    
    //Validamos que tipo de error de consulta se enviará a la vista
    void error(){
        
        String str = "";
        String str2 = "";
        
        if(men.length() > 0){
            if(men.length() < 34){
                
            }else{
                for (int i = 0; i < 34; i++) {
                    str += men.charAt(i);
                }
            }
            
            if(men.length() < 18){
                
            }else{
                for (int i = 0; i < 18; i++) {
                    str2 += men.charAt(i);
                }
            }
            
            
        }
        
        String m = txtArea.getText();
        if(m.equalsIgnoreCase("Error...")){
            panelEstado.setBackground(new Color(228, 65, 65));
            this.txtError.setText("Error:  Se encontraron errores en la consulta a la base de datos TXT...");
        }else if(m.equalsIgnoreCase("Correcto...")){
            panelEstado.setBackground(new Color(76, 175, 80));
            txtError.setText("Correcto:  Tabla creada correctamente en la base de datos TXT...");
        }else if(m.equalsIgnoreCase("Modificado...")){
            panelEstado.setBackground(new Color(76, 175, 80));
            txtError.setText("Correcto:  Tabla fue modificada correctamente en la base de datos TXT...");
        }else if(m.equalsIgnoreCase("Eliminado...")){
            panelEstado.setBackground(new Color(76, 175, 80));
            txtError.setText("Correcto:  Tabla fue Eliminada correctamente en la base de datos TXT...");
        }else if(str.equalsIgnoreCase("Tabla actualizada correctamente...")){
            panelEstado.setBackground(new Color(76, 175, 80));
            txtError.setText("Correcto:  Tabla actualizada correctamente...");
        }else if(str2.equalsIgnoreCase("Lista de tablas...")){
            panelEstado.setBackground(new Color(76, 175, 80));
            txtError.setText("Correcto:  Tablas visualizadas correctamente...");
        }else if(!m.isEmpty()){
            panelEstado.setBackground(new Color(76, 175, 80));
            txtError.setText("Correcto:  Tabla visualizada correctamente...");
        }
    }
    
    //Limpiamos el error de la vista
    void limpiarError(){
        panelEstado.setBackground(new Color(255,255,255));
        txtError.setText("");
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblLoading = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtArea = new javax.swing.JTextArea();
        panelEstado = new javax.swing.JPanel();
        txtError = new javax.swing.JLabel();
        salir = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtMensaje = new javax.swing.JTextField();
        btnEnviar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1000, 603));
        setMinimumSize(new java.awt.Dimension(1000, 603));

        lblLoading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLoading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/loading2.gif"))); // NOI18N
        lblLoading.setMaximumSize(new java.awt.Dimension(200, 200));
        lblLoading.setMinimumSize(new java.awt.Dimension(200, 200));
        lblLoading.setPreferredSize(new java.awt.Dimension(200, 200));

        txtArea.setEditable(false);
        txtArea.setColumns(20);
        txtArea.setFont(new java.awt.Font("SF UI Display", 0, 16)); // NOI18N
        txtArea.setRows(5);
        txtArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAreaKeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(txtArea);

        panelEstado.setBackground(new java.awt.Color(255, 255, 255));

        txtError.setFont(new java.awt.Font("SF UI Display", 0, 16)); // NOI18N
        txtError.setForeground(new java.awt.Color(255, 255, 255));
        txtError.setText("Error");

        salir.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Salir.png"))); // NOI18N
        salir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                salirMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelEstadoLayout = new javax.swing.GroupLayout(panelEstado);
        panelEstado.setLayout(panelEstadoLayout);
        panelEstadoLayout.setHorizontalGroup(
            panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEstadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtError, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addComponent(salir)
                .addContainerGap())
        );
        panelEstadoLayout.setVerticalGroup(
            panelEstadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtError, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelEstadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(salir, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(8, 32, 50));
        jPanel3.setMaximumSize(new java.awt.Dimension(1000, 39));
        jPanel3.setMinimumSize(new java.awt.Dimension(1000, 39));

        jLabel1.setFont(new java.awt.Font("SF UI Display", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CLIENTE");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(455, 455, 455)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtMensaje.setFont(new java.awt.Font("SF UI Display", 0, 16)); // NOI18N
        txtMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMensajeActionPerformed(evt);
            }
        });
        txtMensaje.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMensajeKeyReleased(evt);
            }
        });

        btnEnviar.setBackground(new java.awt.Color(51, 71, 86));
        btnEnviar.setFont(new java.awt.Font("SF UI Display", 1, 16)); // NOI18N
        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(911, Short.MAX_VALUE)
                .addComponent(btnEnviar)
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(txtMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 890, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(100, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(txtMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(12, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(400, 400, 400)
                        .addComponent(lblLoading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(lblLoading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(panelEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void salirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salirMouseClicked
        limpiarError();
    }//GEN-LAST:event_salirMouseClicked

    private void txtMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMensajeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMensajeActionPerformed

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        enviar();
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void txtMensajeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMensajeKeyReleased
        //Para ingresar el mensaje o la consulta mediante ENTER
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            enviar();
        }
    }//GEN-LAST:event_txtMensajeKeyReleased

    private void txtAreaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAreaKeyTyped
        error();
    }//GEN-LAST:event_txtAreaKeyTyped

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblLoading;
    private javax.swing.JPanel panelEstado;
    private javax.swing.JLabel salir;
    public static javax.swing.JTextArea txtArea;
    private javax.swing.JLabel txtError;
    private javax.swing.JTextField txtMensaje;
    // End of variables declaration//GEN-END:variables

    //Hilo para Sleep, madando el tiempo de espera que se realizará
    public void proceso(int tiempo){
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException ex) {
            System.out.println("Error en pantalla de carga..."+ex);
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        new Thread(){
            public void run(){
                txtArea.setText("");
                txtError.setForeground(Color.WHITE);
                txtError.setText("");
                proceso(120);
                lblLoading.setVisible(false);
                txtArea.setText((String) arg);
                men = arg.toString();
                error();
            }
        }.start();        
    }
}
