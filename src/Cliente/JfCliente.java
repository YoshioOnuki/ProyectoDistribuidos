
package Cliente;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Susana
 */
public class JfCliente extends javax.swing.JFrame {
    
    public static String[][] datos;
    public static int puntero;
    public static int cantidadAtributos;
    public static String txtConsulta = "";
    public static String nombreTabla = "";
    
    String barra = File.separator;
    String crearUbicacion = System.getProperty("user.dir")+barra+"dbDistribuidos"+barra;
    
    String crearUbicacionBackup = System.getProperty("user.dir")+barra+"dbDistribuidos"+barra+"backup"+barra;
    String crearUbicacionBackupPuntero = System.getProperty("user.dir")+barra+"dbDistribuidos"+barra+"backup"+barra;
    String mensaje = "";
    File archiBackupFile = null;
    
    String coma = ",";
    String puntoComa = ";";
    String espacio = " ";
    String parenInicio = "(";
    String parenFinal = ")";
    
    FileOutputStream fos;
    
    ImageIcon icoWar = new ImageIcon("src/Imagenes/IconWarning.png");
    ImageIcon icoOk = new ImageIcon("src/Imagenes/IconOk.png");
    
    
    public JfCliente() {
        initComponents();
        setLocationRelativeTo(null);
        txtArea.setEditable(false);
        
    }
    
    private void crearTabla(){
        String[] da = validarCreate();
        nombreTabla = da[0];
        cantidadAtributos = (da.length-1)/2;
        puntero = 1;
        String contenido = "";
        String conteBackup = "";
        int c = 1;
                
        String archivo = nombreTabla+".txt";

        datos = new String[puntero][cantidadAtributos];
        for (int i = 0; i < cantidadAtributos; i++) {
            datos[0][i] = da[c]+" "+da[c+1];
            c += 2;
            contenido += datos[0][i];
            if(i != cantidadAtributos-1){
                contenido += "\t|\t";
            }else{
                contenido += "\t|\n";
            }
        }
        for (int i = 0; i < cantidadAtributos; i++) {
            if(i != 0){
                conteBackup += ",";
            }
            conteBackup += datos[0][i];
            System.out.println(conteBackup);
        }
        
        String archivoPuntero = "puntero"+nombreTabla+".txt";
        String contenidopuntero = ""+puntero; 
        File crearUbiBackupPuntero = new File(crearUbicacionBackupPuntero);
        File crearArchiBackupPuntero = new File(crearUbicacionBackupPuntero+archivoPuntero);
        
        File crearUbi = new File(crearUbicacion);
        File crearArchi = new File(crearUbicacion+archivo);
        
        String archivoBackup = "backup"+nombreTabla+".txt";
        File crearUbiBackup = new File(crearUbicacionBackup);
        File crearArchiBackup = new File(crearUbicacionBackup+archivoBackup);
        
        try {
            if(crearArchi.exists()){
                JOptionPane.showMessageDialog(null, "La tabla ya se encuentra registrada.", "¡Advertencia!",JOptionPane.WARNING_MESSAGE, icoWar);
                txtMensaje.requestFocus();
            }else{
                crearUbi.mkdirs();
                crearArchi.createNewFile();

                crearUbiBackup.mkdirs();
                crearArchiBackup.createNewFile();
                
                crearUbiBackupPuntero.mkdirs();
                crearArchiBackupPuntero.createNewFile();
                
                int respuesta1 = escribir(crearArchiBackupPuntero, contenidopuntero);
                
                int respuesta2 = escribir(crearArchiBackup, conteBackup);

                int respuesta3 = escribir(crearArchi, contenido);

                JOptionPane.showMessageDialog(null, "Directorios creados correctamente.", "¡Correcto!",JOptionPane.WARNING_MESSAGE, icoOk);
                txtMensaje.requestFocus();
                limpiarMensaje();
            }

        } catch (Exception e) {
            System.out.println("Error al crear directorios"+e);
        }
        
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
    
    void cargarDatos(String nombreT){
        String punt = "puntero"+nombreT+".txt";
        int cantAtri = 0;
        File archiPuntero = new File(crearUbicacionBackupPuntero+punt);
        String archivoBackup = "";
        File archiBackupFile = null;
        
        JfCliente obj = new JfCliente();
        
        if(nombreT.equalsIgnoreCase("alumno2")){
            cantAtri = 2;
            archivoBackup = "alumno2Backup.txt";
            archiBackupFile = new File(crearUbicacionBackup+archivoBackup);
        }
        
        int puntero = Integer.parseInt(obj.leerDatos(archiPuntero));
        datos = new String[puntero][cantAtri];
        String dat = obj.leerDatos(archiBackupFile);
        String atri = "";
        int posi = 0;
        int k = 0;
        int l = 0;
        for (int j = 0; j < cantAtri*puntero; j++) {
//            System.out.println(dat.length());
            for (int i = posi; i < dat.length(); i++) {
//                System.out.println(coma);
                if(dat.charAt(posi) != coma.charAt(0)){
                    atri += dat.charAt(i);
                    posi++;
                }else if(dat.charAt(posi) == coma.charAt(0)){
                    posi++;
                    break;
                }
            }
            datos[k][l] = atri;
            System.out.print(datos[k][l]+" ");
            l++;
            if(l == cantAtri && k != puntero-1){
                k++;
                l=0;
            }
            atri = "";
        }
        System.out.println("");
        System.out.println(cantAtri);
        System.out.println(puntero);
        
//        String[][] d = dat;
    }
    
    public String leerDatos(File file){
        String r = "";
        
        try {
            FileInputStream fis = new FileInputStream(file);
            int dato;
            
            while(true){
                dato = fis.read();
                if(dato != -1){
                    r += (char)dato;
                }else
                    break;
            }
            fis.close();
            
        } catch (FileNotFoundException ex) {
            System.out.println("Archivo no ubicado");
        } catch (IOException ex){
            System.out.println("Error de Entrada o Salida");
        }
        return r;
        
    }
    
    String tipoConsulta(){
        String r = "";
        
        for (int i = 0; i < 6; i++) {
            r += txtConsulta.charAt(i);
        }
        
        return r;
    }
    
    String[] validarCreate(){
        int cantAtri = 0;
        int cont = 0;
        String consul = "";
        int posi = 13;
        String nombre = "";
        int pAtri = 1;
        
        for (int i = 0; i < 12; i++) {
            consul += txtConsulta.charAt(i);
        }
        
        for (int i = posi; i < txtConsulta.length(); i++) {
            if(txtConsulta.charAt(i) == coma.charAt(0)){
                cantAtri++;
            }
        }
        cantAtri++;
        System.out.println(cantAtri);
        
        String[] r = new String[(cantAtri*2)+1];
        
        if(!consul.equalsIgnoreCase("CREATE TABLE")){
            JOptionPane.showMessageDialog(null, "Consulta ingresada es incorrecta", "¡Advertencia!",JOptionPane.WARNING_MESSAGE, icoWar);
            System.out.println(consul);
            txtMensaje.requestFocus();
        }else{
            for (int i = posi; i < txtConsulta.length(); i++) {
                if(txtConsulta.charAt(i) != espacio.charAt(0)){
                    nombre += txtConsulta.charAt(i);
                }else if(txtConsulta.charAt(i) == espacio.charAt(0)){
                    System.out.println(nombre);
                    r[cont] = nombre;
                    cont++;
                    pAtri++;
                    nombre = "";
                    posi = i+3;
                    break;
                }
            }
            for (int j = 0; j < cantAtri*2; j++) {
                for (int i = posi; i < txtConsulta.length(); i++) {
                    if(txtConsulta.charAt(i) != espacio.charAt(0)){
                        nombre += txtConsulta.charAt(i);
                    }else if(txtConsulta.charAt(i) == espacio.charAt(0)){
                        if(pAtri%2==0){
                            System.out.println(nombre);
                            r[cont] = nombre;
                            cont++;
                            pAtri++;
                            nombre = "";
                        }else{
                            System.out.println(nombre);
                            r[cont] = nombre;
                            cont++;
                            pAtri++;
                            nombre = "";
                            posi = i+3;
                            break;
                        }
                        
                    }
                }
            }
            
        }
        
        return r;
    }
    
    void view2(){
        archiBackupFile = new File(crearUbicacion+"alumno2.txt");
        mensaje = leerDatos(archiBackupFile);
        txtArea.setText(mensaje);
    }
    
    void limpiarMensaje(){
        txtMensaje.setText("");
    }
    
    void limpiarTodo(){
        txtMensaje.setText("");
        txtArea.setText("");
    }
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        PanelPrincipal = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtArea = new javax.swing.JTextArea();
        panelEnvio = new javax.swing.JPanel();
        txtMensaje = new javax.swing.JTextField();
        btnEnviar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(689, 482));
        setMinimumSize(new java.awt.Dimension(689, 482));

        jPanel3.setBackground(new java.awt.Color(8, 32, 50));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CLIENTE");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(305, 305, 305)
                .addComponent(jLabel1)
                .addContainerGap(310, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.PAGE_START);

        PanelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        PanelPrincipal.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PanelPrincipal.setMaximumSize(new java.awt.Dimension(688, 343));
        PanelPrincipal.setMinimumSize(new java.awt.Dimension(688, 343));

        jScrollPane1.setMaximumSize(new java.awt.Dimension(689, 343));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(689, 343));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(689, 343));

        txtArea.setColumns(20);
        txtArea.setFont(new java.awt.Font("SF UI Display", 0, 16)); // NOI18N
        txtArea.setRows(5);
        txtArea.setMaximumSize(new java.awt.Dimension(689, 343));
        txtArea.setMinimumSize(new java.awt.Dimension(689, 343));
        jScrollPane1.setViewportView(txtArea);

        javax.swing.GroupLayout PanelPrincipalLayout = new javax.swing.GroupLayout(PanelPrincipal);
        PanelPrincipal.setLayout(PanelPrincipalLayout);
        PanelPrincipalLayout.setHorizontalGroup(
            PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 689, Short.MAX_VALUE)
            .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 608, Short.MAX_VALUE))
        );
        PanelPrincipalLayout.setVerticalGroup(
            PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 343, Short.MAX_VALUE)
            .addGroup(PanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, Short.MAX_VALUE))
        );

        panelEnvio.setBackground(new java.awt.Color(255, 255, 255));
        panelEnvio.setMaximumSize(new java.awt.Dimension(689, 100));
        panelEnvio.setMinimumSize(new java.awt.Dimension(689, 100));
        panelEnvio.setPreferredSize(new java.awt.Dimension(689, 100));

        txtMensaje.setFont(new java.awt.Font("SF UI Display", 0, 16)); // NOI18N

        btnEnviar.setBackground(new java.awt.Color(51, 71, 86));
        btnEnviar.setFont(new java.awt.Font("SF UI Display", 1, 16)); // NOI18N
        btnEnviar.setForeground(new java.awt.Color(255, 255, 255));
        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelEnvioLayout = new javax.swing.GroupLayout(panelEnvio);
        panelEnvio.setLayout(panelEnvioLayout);
        panelEnvioLayout.setHorizontalGroup(
            panelEnvioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEnvioLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelEnvioLayout.setVerticalGroup(
            panelEnvioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEnvioLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelEnvioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(PanelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(panelEnvio, javax.swing.GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(PanelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelEnvio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        
        if(txtMensaje.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Campos de texto vacíos...", "¡Advertencia!",JOptionPane.WARNING_MESSAGE, icoWar);
            txtMensaje.requestFocus();
        }else{
            txtConsulta = txtMensaje.getText();
            System.out.println(tipoConsulta());
            if(tipoConsulta().equalsIgnoreCase("CREATE")){
                crearTabla();
            }else if(tipoConsulta().equalsIgnoreCase("SELECT")){
                File crearArchi = new File(crearUbicacion+"alumno2.txt");
                if(!crearArchi.exists()){
                    JOptionPane.showMessageDialog(null, "La tabla no se encuentra registrada.", "¡Advertencia!",JOptionPane.WARNING_MESSAGE, icoWar);
                    txtMensaje.requestFocus();
                }else{
                    view2();
                    cargarDatos("alumno2");
                }
            }else if(tipoConsulta().equalsIgnoreCase("UPDATE")){
                
            }else if(tipoConsulta().equalsIgnoreCase("DELETE")){
                
            }else{
                JOptionPane.showMessageDialog(null, "Consulta ingresada es incorrecta", "¡Advertencia!",JOptionPane.WARNING_MESSAGE, icoWar);
                txtMensaje.requestFocus();
            }
        }
        
        
    }//GEN-LAST:event_btnEnviarActionPerformed

    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JfCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JPanel PanelPrincipal;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelEnvio;
    private javax.swing.JTextArea txtArea;
    private javax.swing.JTextField txtMensaje;
    // End of variables declaration//GEN-END:variables
}
