
package Servidor;

import Cliente.*;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

public class ChatServidor extends javax.swing.JFrame implements Observer{

    
    public static String[][] datos;
    public static int puntero;
    public static int cantidadAtributos;
    public static String txtConsulta = "";
    public static String nombreTabla = "";
    
    String barra = File.separator;
    String crearUbicacion = System.getProperty("user.dir")+barra+"dbDistribuidos"+barra;
    
    String crearUbicacionBackup = System.getProperty("user.dir")+barra+"dbDistribuidos"+barra+"datos"+barra+"backup"+barra;
    String crearUbicacionBackupPuntero = System.getProperty("user.dir")+barra+"dbDistribuidos"+barra+"datos"+barra+"punteros"+barra;
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
    
    String mensajeError = "";
    
    
    public ChatServidor() {
        
        initComponents();
        initMetodos();
        setLocationRelativeTo(null);
        txtArea.setEditable(false);
        btnEnv();
    }
    
    void initMetodos(){
        Servidor s = new Servidor(5050);
        s.addObserver(this);
        Thread t = new Thread (s);
        t.start();
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
        
        if(da[0] == null){
            enviar("Correcto...");
            panelEstado.setBackground(new Color(228, 65, 65));
            txtError.setText("Error:  Se encontraron errores en la consulta a la base de datos TXT...");
        }else{
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
                    enviar("Correcto...");
                    panelEstado.setBackground(new Color(228, 65, 65));
                    txtError.setText("Error:  Se encontraron errores en la consulta a la base de datos TXT...");
    //                JOptionPane.showMessageDialog(null, "Consulta ingresada es incorrecta.", "¡Advertencia!",JOptionPane.WARNING_MESSAGE, icoWar);
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

                    enviar("Correcto...");
                    panelEstado.setBackground(new Color(76, 175, 80));
                    txtError.setText("Correcto:  Tabla '"+ nombreTabla +"' creada correctamente en la base de datos TXT...");
    //                JOptionPane.showMessageDialog(null, "Directorios creados correctamente.", "¡Correcto!",JOptionPane.WARNING_MESSAGE, icoOk);
                }

            } catch (Exception e) {
                System.out.println("Error al crear directorios"+e);
            }
        }
        
    }
    
    void cargarDatos(String nombreT){
        String punt = "puntero"+nombreT+".txt";
        int cantAtri = 0;
        File archiPuntero = new File(crearUbicacionBackupPuntero+punt);
        String archivoBackup = "";
        File archiBackupFile = null;
        
        ChatServidor obj = new ChatServidor();
        
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
    
    String tipoConsulta(){
        String r = "";
        if(txtConsulta.length() < 6){
            
        }else{
            for (int i = 0; i < 6; i++) {
                r += txtConsulta.charAt(i);
            }
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
        
        for (int i = 0; i < 13; i++) {
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
        
        if(!consul.equalsIgnoreCase("CREATE TABLE ")){
//            JOptionPane.showMessageDialog(null, "Consulta ingresada es incorrecta", "¡Advertencia!",JOptionPane.WARNING_MESSAGE, icoWar);
            System.out.println(consul);
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
    
    void limpiarTodo(){
        txtArea.setText("");
    }
    
    void enviar(String mensa){
        // TODO add your handling code here:
//        String mensaje = "" + this.txtMensaje.getText() + "\n";
//        this.txtArea.setText(mensaje);
        this.txtArea.setText(mensa);
        
//        panelEstado.setBackground(new Color(228, 65, 65));
//        this.txtError.setText(mensajeError);
        
        
        Cliente c1 = new Cliente("192.168.1.47",5050,mensa);
        Thread t1 = new Thread(c1); 
        t1.start();
    }
    
    void btnEnv(){
        txtConsulta = txtArea.getText();
//        System.out.println(txtConsulta);
        if(txtConsulta.isEmpty()){
            
        }else{
            
            System.out.println("\n"+tipoConsulta());
            if(tipoConsulta().equalsIgnoreCase("CREATE")){
                crearTabla();
            }else if(tipoConsulta().equalsIgnoreCase("SELECT")){
                File crearArchi = new File(crearUbicacion+"alumno2.txt");
                if(!crearArchi.exists()){
                    enviar("Error...");
                    panelEstado.setBackground(new Color(228, 65, 65));
                    txtError.setText("Error:  Se encontraron errores en la consulta a la base de datos TXT...");
//                    JOptionPane.showMessageDialog(null, "Consulta ingresada es incorrecta.", "¡Advertencia!",JOptionPane.WARNING_MESSAGE, icoWar);
                }else{
                    view2();
                    cargarDatos("alumno2");
                }
            }else if(tipoConsulta().equalsIgnoreCase("UPDATE")){
                
            }else if(tipoConsulta().equalsIgnoreCase("DELETE")){
                
            }else{
                enviar("Error...");
                panelEstado.setBackground(new Color(228, 65, 65));
                txtError.setText("Error:  Se encontraron errores en la consulta a la base de datos TXT...");
//                JOptionPane.showMessageDialog(null, "Consulta ingresada es incorrecta.", "¡Advertencia!",JOptionPane.WARNING_MESSAGE, icoWar);
            }
        }
    }    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtArea = new javax.swing.JTextArea();
        panelEstado = new javax.swing.JPanel();
        txtError = new javax.swing.JLabel();
        salir = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtArea.setColumns(20);
        txtArea.setFont(new java.awt.Font("SF UI Display", 0, 16)); // NOI18N
        txtArea.setRows(5);
        txtArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAreaKeyReleased(evt);
            }
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
                .addComponent(txtError, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SERVIDOR");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(298, 298, 298)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 713, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 82, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addComponent(panelEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(panelEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void salirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salirMouseClicked
        panelEstado.setBackground(new Color(255,255,255));
        txtError.setText("");
    }//GEN-LAST:event_salirMouseClicked

    private void txtAreaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAreaKeyTyped
        
    }//GEN-LAST:event_txtAreaKeyTyped

    private void txtAreaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAreaKeyReleased
        
    }//GEN-LAST:event_txtAreaKeyReleased

    /**
     * @param args the command line arguments
     */
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
                new ChatServidor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelEstado;
    private javax.swing.JLabel salir;
    private javax.swing.JTextArea txtArea;
    private javax.swing.JLabel txtError;
    // End of variables declaration//GEN-END:variables

    @Override
    public void update(Observable o, Object arg) {
        this.txtArea.setText((String) arg);
        btnEnv();
    }
}
