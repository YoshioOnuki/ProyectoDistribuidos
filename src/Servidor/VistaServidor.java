
package Servidor;

import Cliente.*;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yoshio
 */
public class VistaServidor extends javax.swing.JFrame implements Observer{
    
    //Inicializamos variables y variables estáticas para el manejo de datos
    public static String ip = LoginServidor.ipServidor;
    public static String[][] datos;
    public static int puntero;
    public static int cantidadAtributos;
    public static String txtConsulta = "";
    public static String nombreTabla = "";
    String mensaje = "";
    String mensajeError = "";
    
    //Inicializamos caracteres conocidos para validaciones
    String barra = File.separator;
    String punto = ".";
    String coma = ",";
    String puntoComa = ";";
    String espacio = " ";
    String parenInicio = "(";
    String parenFinal = ")";
    String comillaSimple = "'";
    
    //Inicializamos las rutas de la base de datos, backups. Todos desde la carpeta raíz del proyecto
    String crearUbicacion = System.getProperty("user.dir")+barra+"dbDistribuidos"+barra;
    String crearUbicacionBackup = System.getProperty("user.dir")+barra+"dbDistribuidos"+barra+"datos"+barra+"backup"+barra;
    String crearUbicacionBackupPuntero = System.getProperty("user.dir")+barra+"dbDistribuidos"+barra+"datos"+barra+"punteros"+barra;
    String crearUbicacionBackupCantAtri = System.getProperty("user.dir")+barra+"dbDistribuidos"+barra+"datos"+barra+"cantidad_atributos"+barra;
    String crearUbicacionBackupCantDelete = System.getProperty("user.dir")+barra+"dbDistribuidos"+barra+"datos"+barra+"cantidad_delete"+barra;
    
    //File para el manejos de archivos en el Backup
    File archiBackupFile = null;
    //FileOutputStream para la escritura de datos
    FileOutputStream fos;
    //FileInputStream para la lectura de datos
    FileInputStream fis;
    
    
    public VistaServidor() {
        initComponents();
        initService();
        setLocationRelativeTo(null);
        txtArea.setEditable(false);
        txtMensaje.setEditable(false);
        lblLoading.setVisible(false);
        validarTipoConsulta();
        
        System.out.println(ip);
    }
    
    //Método para enviar una respuesta al cliente
    void enviar(String mensa){
        this.txtArea.setText(mensa);
        
        Cliente c1 = new Cliente(this.ip,5050,mensa);
        Thread t1 = new Thread(c1); 
        t1.start();
    }
    
    //Inicializamos al servidor
    void initService(){
        Servidor s = new Servidor(5050);
        s.addObserver(this);
        Thread t = new Thread (s);
        t.start();
    }
    
    //Métodos de manejos de archivos (leer y escribirDatos)
    public String leerDatos(File file){
        String r = "";
        
        try {
            fis = new FileInputStream(file);
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
    public int escribirDatos(File file, String contenido){
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
    
    //Métodos para validar consulta y Crear tablas en la base de datos TXT
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
            System.out.println(consul);
        }else{
            for (int i = posi; i < txtConsulta.length(); i++) {
                if(txtConsulta.charAt(i) != espacio.charAt(0)){
                    nombre += txtConsulta.charAt(i);
                }else if(txtConsulta.charAt(i) == espacio.charAt(0)){
                    System.out.println(nombre);
                    System.out.println(cont);
                    r[cont] = nombre;
                    cont++;
                    pAtri++;
                    nombre = "";
                    posi = i+3;
                    break;
                }
            }
            
            for (int j = 0; j < cantAtri*2; j++) {
                nombre = "";
                for (int i = posi; i < txtConsulta.length(); i++) {
                    if(txtConsulta.charAt(i) != espacio.charAt(0)){
                        nombre += txtConsulta.charAt(i);
                    }else if(txtConsulta.charAt(i) == espacio.charAt(0)){
                        if(pAtri%2==0){
                            System.out.println(nombre);
                            System.out.println(cont);
                            r[cont] = nombre;
                            cont++;
                            pAtri++;
                            nombre = "";
//                            break;
                        }else if(pAtri%2!=0){
                            System.out.println(nombre);
                            System.out.println(cont);
                            r[cont] = nombre;
                            cont++;
                            pAtri++;
                            nombre = "";
                            if(j == cantAtri*2-1){
                                posi = i+2;
                            }else{
                                posi = i+3; 
                            }
                            break;
                        }
                        
                    }
                }
            }
            
        }
        
        return r;
    }
    private void createTabla(){
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
            enviar("Error...");
            panelEstado.setBackground(new Color(228, 65, 65));
            txtError.setText("Error:  Se encontraron errores en la consulta a la base de datos TXT...");
        }else{
            for (int i = 0; i < cantidadAtributos; i++) {
                datos[0][i] = da[c]+" "+da[c+1];
                c += 2;
                contenido += datos[0][i];
                if(i != cantidadAtributos-1){
                    contenido += espacio+"|"+espacio;
                }else{
                    contenido += "\n";
                }
            }
            for (int i = 0; i < cantidadAtributos; i++) {
                if(i != 0){
                    conteBackup += ",";
                }
                conteBackup += datos[0][i];
                System.out.println(conteBackup);
            }
            
            String archivoCantidad = "cantidad_atributo_"+nombreTabla+".txt";
            String contenidoCantidad = ""+cantidadAtributos; 
            File crearUbiBackupCantidad = new File(crearUbicacionBackupCantAtri);
            File crearArchiBackupCantidad = new File(crearUbicacionBackupCantAtri+archivoCantidad);
            
            String archivoPuntero = "puntero_"+nombreTabla+".txt";
            String contenidopuntero = ""+puntero; 
            File crearUbiBackupPuntero = new File(crearUbicacionBackupPuntero);
            File crearArchiBackupPuntero = new File(crearUbicacionBackupPuntero+archivoPuntero);

            File crearUbi = new File(crearUbicacion);
            File crearArchi = new File(crearUbicacion+archivo);

            String archivoBackup = "backup_"+nombreTabla+".txt";
            File crearUbiBackup = new File(crearUbicacionBackup);
            File crearArchiBackup = new File(crearUbicacionBackup+archivoBackup);
            
            String archivoDelete = "delete_"+nombreTabla+".txt";
            File crearUbiDelete = new File(crearUbicacionBackupCantDelete);
            File archiDelete = new File(crearUbicacionBackupCantDelete+archivoDelete);
            

            try {
                if(crearArchi.exists()){
                    enviar("Error...");
                    panelEstado.setBackground(new Color(228, 65, 65));
                    txtError.setText("Error:  Se encontraron errores en la consulta a la base de datos TXT...");
                }else{
                    
                    crearUbi.mkdirs();
                    crearArchi.createNewFile();

                    crearUbiBackup.mkdirs();
                    crearArchiBackup.createNewFile();

                    crearUbiBackupPuntero.mkdirs();
                    crearArchiBackupPuntero.createNewFile();

                    crearUbiBackupCantidad.mkdirs();
                    crearArchiBackupCantidad.createNewFile();
                    
                    crearUbiDelete.mkdirs();
                    archiDelete.createNewFile();
                    
                    int respuesta = escribirDatos(archiDelete, "0");
                    
                    int respuesta0 = escribirDatos(crearArchiBackupCantidad, contenidoCantidad);
                    
                    int respuesta1 = escribirDatos(crearArchiBackupPuntero, contenidopuntero);

                    int respuesta2 = escribirDatos(crearArchiBackup, conteBackup);

                    int respuesta3 = escribirDatos(crearArchi, contenido);

                    enviar("Correcto...");
                    panelEstado.setBackground(new Color(76, 175, 80));
                    txtError.setText("Correcto:  Tabla '"+ nombreTabla +"' creada correctamente en la base de datos TXT...");
                }

            } catch (Exception e) {
                System.out.println("Error al crear directorios"+e);
            }
        }
        
    }
    
    //Métodos para validar consulta y Seleccionar tablas de la base de datos TXT
    String validarSelect(){
        String r = "";
        String consul = "";
        int posi = 14;
        String nombre = "";
        
        
        for (int i = 0; i < 14; i++) {
            consul += txtConsulta.charAt(i);
        }
        
        if(!consul.equalsIgnoreCase("SELECT * FROM ")){
            System.out.println(consul);
        }else{
            for (int i = posi; i < txtConsulta.length(); i++) {
                if(txtConsulta.charAt(i) != puntoComa.charAt(0)){
                    nombre += txtConsulta.charAt(i);
                }else if(txtConsulta.charAt(i) == puntoComa.charAt(0)){
                    System.out.println(nombre);
                    r = nombre;
                    nombre = "";
                    break;
                }
            }
        }
        
        return r;
    }
    void select(){
        String tab = validarSelect();
        File crearArchi = new File(crearUbicacion+tab+".txt");
        if(!crearArchi.exists()){
            enviar("Error...");
            panelEstado.setBackground(new Color(228, 65, 65));
            txtError.setText("Error:  Se encontraron errores en la consulta a la base de datos TXT...");
        }else{
            mensaje = leerDatos(crearArchi);
            enviar(mensaje);
            txtArea.setText(mensaje);
            panelEstado.setBackground(new Color(76, 175, 80));
            txtError.setText("Correcto:  Tabla visualizada correctamente...");
        }
    }
    
    //Métodos para validar consulta y Actualizar atributos de una tabla de la base de datos TXT
    String[] validarUpdateDelete(String consulta, int posicion){
        String consul = "";
        int posi = posicion;
        String nombre = "";
        
        int cantAtri = 0;
        int cont = 0;
        int pAtri = 1;  
        
        
        for (int i = 0; i < posi; i++) {
            consul += txtConsulta.charAt(i);
        }
        
        if(consulta.equalsIgnoreCase("UPDATE ")){
            for (int i = posi; i < txtConsulta.length(); i++) {
                if(txtConsulta.charAt(i) == coma.charAt(0)){
                    cantAtri++;
                }
            }
        }
        
        cantAtri++;
        System.out.println(cantAtri);
        
        int cantArray = 0;
        
        if(consulta.equalsIgnoreCase("UPDATE ")){
            cantArray = (cantAtri * 2) + 4;
        }else if(consulta.equalsIgnoreCase("DELETE FROM ")){
            cantArray = 3;
        }
        
        String[] r = new String[cantArray];
        
        if(!consul.equalsIgnoreCase(consulta)){
            System.out.println(consul);
        }else{
            
            for (int i = posi; i < txtConsulta.length(); i++) {
                if(txtConsulta.charAt(i) != espacio.charAt(0)){
                    nombre += txtConsulta.charAt(i);
                }else if(txtConsulta.charAt(i) == espacio.charAt(0)){
                    System.out.println(nombre);
                    r[cont] = nombre;
                    cont++;
                    if(consulta.equalsIgnoreCase("DELETE FROM ")){
                        posi = i+7;
                    }else if(consulta.equalsIgnoreCase("UPDATE ")){
                        posi = i+5;
                    }
                    nombre = "";
                    break;
                }
            }
            
            int estad = 0;
            if(consulta.equalsIgnoreCase("UPDATE ")){
                for (int j = 0; j < cantAtri*2; j++) {
                    for (int i = posi; i < txtConsulta.length(); i++) {
                        if(txtConsulta.charAt(i) != espacio.charAt(0)){
                            if(txtConsulta.charAt(i) != comillaSimple.charAt(0)){
                                nombre += txtConsulta.charAt(i);
                            }else{
                                estad++;
                            }
                        }else if(txtConsulta.charAt(i) == espacio.charAt(0)){
                            if(estad%2!=0){
                                nombre += " ";
                            }else{
                                System.out.println(nombre);
                                r[cont] = nombre;
                                cont++;
                                nombre = "";
                                if(j == cantAtri*2 - 1){
                                    posi = i+7;
                                    break;
                                }else{
                                    posi = i+3;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            
            for (int j = 0; j < 2; j++) {
                for (int i = posi; i < txtConsulta.length(); i++) {
                    if(txtConsulta.charAt(i) != espacio.charAt(0)){
                        if(txtConsulta.charAt(i) != puntoComa.charAt(0)){
                            nombre += txtConsulta.charAt(i);
                        }else{
                            System.out.println(nombre);
                            r[cont] = nombre;
                            cont++;
                            nombre = "";
                            break;
                        }
                    }else if(txtConsulta.charAt(i) == espacio.charAt(0)){
                        System.out.println(nombre);
                        r[cont] = nombre;
                        cont++;
                        posi = i+3;
                        nombre = "";
                        break;
                    }
                }
            }
            
        }
        
        if(consulta.equalsIgnoreCase("UPDATE ")){
            r[cont] = ""+cantAtri;
        }
        
        return r;
    }
    void updt(){
        //Validamos y capturamos el nombre de la tabla y los atributos a actualizar
        String[] d = validarUpdateDelete("UPDATE ", 7);
        String nombreT = d[0];
        System.out.println(nombreT);
        
        //Creamos el nombre y archivo de la cantidad de atributos, puntero, raiz y backup de la tabla
        String can = "cantidad_atributo_"+nombreT+".txt";
        File archiCan = new File(crearUbicacionBackupCantAtri+can);
        //
        String punt = "puntero_"+nombreT+".txt";
        File archiPuntero = new File(crearUbicacionBackupPuntero+punt);
        //
        String archi = nombreT+".txt";
        File archiFile = new File(crearUbicacion+archi);
        //
        String archivoBackup = "backup_"+nombreT+".txt";
        archiBackupFile = new File(crearUbicacionBackup+archivoBackup);
        //
        String archivoDelete = "delete_"+nombreT+".txt";
        File archiDelete = new File(crearUbicacionBackupCantDelete+archivoDelete);
            
        if(!archiFile.exists()){
            enviar("Error...");
            panelEstado.setBackground(new Color(228, 65, 65));
            txtError.setText("Error:  Se encontraron errores en la consulta a la base de datos TXT...");
        }else{
            
            try {
                //Capturamos la cantidad de atributos y el puntero de la tabla
                int cantAtri = Integer.parseInt(leerDatos(archiCan));
                int puntero = Integer.parseInt(leerDatos(archiPuntero));
                int delete = Integer.parseInt(leerDatos(archiDelete));
                
                //Instanciamos el arreglo con los parametros del puntero y cantidad de atributos
                datos = new String[puntero-delete][cantAtri];
                
                //Capturamos los datos del Backup de la tabla
                String dat = leerDatos(archiBackupFile);
                
                //Variables para el manejo de datos de la tabla
                String atri = "";
                int posi = 0;
                int k = 0;
                int l = 0;
                
                //Tamaño de los datos extraidos de la validacion (nombre de la tabla, atributos...
                int tamanio = d.length;
                
                //Puntero donde se va igualar al ID de donde se encuentra la Fila para actualizar
                int puntero2 = Integer.parseInt(d[tamanio-2]);
                System.out.println("Posision ID para actualizar"+puntero2);
                
                //Capturamos los datos de la tabla en un Array
                for (int j = 0; j < cantAtri*puntero - (delete*puntero); j++) {
                    for (int i = posi; i < dat.length(); i++) {
                        if(dat.charAt(i) != coma.charAt(0)){
                            atri += dat.charAt(i);
                            posi++;
                        }else if(dat.charAt(i) == coma.charAt(0)){
                            posi++;
                            break;
                        }
                    }
                    datos[k][l] = atri;
                    System.out.print(datos[k][l]+" ");
                    l++;
                    if(l == cantAtri && k != puntero - delete -1){
                        k++;
                        l=0;
                    }
                    atri = "";
                }
                System.out.println("");
                System.out.println(cantAtri);
                System.out.println(puntero);
                
                //Cantidad de atributos ingresados
                int cantAtriIngre = Integer.parseInt(d[tamanio-1]);
                System.out.println(cantAtriIngre);
                
                //Instancio Arrays de datos de cabecera y sus filas actualizadas
                String[] datIngre = new String[cantAtriIngre];
                String[] datoUpd = new String[cantAtriIngre];
                
                int contadorIngre = 0;
                
                //Capturo las cabeceras y sus filas de lo que quiere actualizar
                for (int i = 0; i < tamanio; i++) {
                    if(i%2!=0){
                        datIngre[contadorIngre] = d[i];
                        System.out.println(datIngre[contadorIngre]);

                        datoUpd[contadorIngre] = d[i+1];
                        System.out.println(datoUpd[contadorIngre]);
                        
                        contadorIngre++;
                        
                        if(contadorIngre == cantAtriIngre){
                            break;
                        }
                    }
                }
                
                String at = "";
                int n = 0;
                
                //Se carga el Array "datos" con los campos actualizados
                for (int j = 0; j < cantAtriIngre; j++) {
                    for (int i = 0; i < cantAtri; i++) {
                        for (int m = 0; m < datos[0][i].length(); m++) {
                            if(datos[0][i].charAt(m) != espacio.charAt(0)){
                                at += datos[0][i].charAt(m);
                                System.out.println(at);
                            }else if(datos[0][i].charAt(m) == espacio.charAt(0)){
                                if(datIngre[j].equalsIgnoreCase(at)){
                                    datos[puntero2][i] = datoUpd[n];
                                    System.out.println(datoUpd[n]);
                                    n++;
                                    at = "";
                                    break;
                                }else{
                                    at = "";
                                    break;
                                }
                            }
                        }
                    }
                    at = "";
                }
                
                String contenidoIngresado = "";
                
                //Cargamos todo el array en un String, con el formato que se guardará la base de datos raiz
                for (int i = 0; i < puntero - delete; i++) {
                    for (int j = 0; j < cantAtri; j++) {
                        contenidoIngresado += datos[i][j];
                        if(j != cantAtri-1){
                            contenidoIngresado += espacio+"|"+espacio;
                        }else{
                            contenidoIngresado += "\n";
                        }
                    }
                }
                System.out.println(contenidoIngresado);

                String contenidoBackup = "";
                
                //Cargamos todo el array en un String, con el formato que se guardará la base de datos backup
                for (int i = 0; i < puntero - delete; i++) {
                    for (int j = 0; j < cantAtri; j++) {
                        if(j == 0 && i == 0){
                            
                        }else{
                            contenidoBackup += ",";
                        }
                        contenidoBackup += datos[i][j];
                    }
                }
                System.out.println(contenidoBackup);

                //Creo el archivo raiz y escribo los datos actualizados en la base de datos TXT
                archiFile.createNewFile();
                int respuesta1 = escribirDatos(archiFile, contenidoIngresado);
                
                //Creo el archivo backup y escribo los datos actualizados en la base de datos backup
                archiBackupFile.createNewFile();
                int respuesta2 = escribirDatos(archiBackupFile, contenidoBackup);
                
                //Mensaje de confirmación
                txtArea.setText("Tabla actualizada correctamente...\n "
                        + "====================================\n"+contenidoIngresado);
                enviar("Tabla actualizada correctamente...\n "
                        + "====================================\n"+contenidoIngresado);
                panelEstado.setBackground(new Color(76, 175, 80));
                txtError.setText("Correcto:  Tabla actualizada correctamente...");
            } catch (IOException ex) {
                Logger.getLogger(VistaServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
                
            
        }
    }
    void delete(){
        //Validamos y capturamos el nombre de la tabla y los atributos a actualizar
        String[] d = validarUpdateDelete("DELETE FROM ", 12);
        String nombreT = d[0];
        
        //Creamos el nombre y archivo de la cantidad de atributos, puntero, raiz y backup de la tabla
        String can = "cantidad_atributo_"+nombreT+".txt";
        File archiCan = new File(crearUbicacionBackupCantAtri+can);
        //
        String punt = "puntero_"+nombreT+".txt";
        File archiPuntero = new File(crearUbicacionBackupPuntero+punt);
        //
        String archi = nombreT+".txt";
        File archiFile = new File(crearUbicacion+archi);
        //
        String archivoBackup = "backup_"+nombreT+".txt";
        archiBackupFile = new File(crearUbicacionBackup+archivoBackup);
        //
        String archivoDelete = "delete_"+nombreT+".txt";
        File archiDelete = new File(crearUbicacionBackupCantDelete+archivoDelete);
        
        if(!archiFile.exists()){
            enviar("Error...");
            panelEstado.setBackground(new Color(228, 65, 65));
            txtError.setText("Error:  Se encontraron errores en la consulta a la base de datos TXT...");
        }else{
            try {
                //Capturamos la cantidad de atributos y el puntero de la tabla
                int cantAtri = Integer.parseInt(leerDatos(archiCan));
                int puntero = Integer.parseInt(leerDatos(archiPuntero));
                int del = Integer.parseInt(leerDatos(archiDelete)) + 1;
                
                if(puntero - del <= 0){
                    enviar("Error delete...");
                    panelEstado.setBackground(new Color(228, 65, 65));
                    txtError.setText("Error:  No quedan datos de la tabla para eliminar...");
                }else{
                    //Instanciamos el arreglo con los parametros del puntero y cantidad de atributos
                    datos = new String[puntero-del+1][cantAtri];

                    //Capturamos los datos del Backup de la tabla
                    String dat = leerDatos(archiBackupFile);

                    //Variables para el manejo de datos de la tabla
                    String atri = "";
                    int estado = 0;
                    int estado2 = 0;
                    int posi = 0;
                    int k = 0;
                    int l = 0;

                    //Capturamos los datos de la tabla en un Array
                    System.out.print("ID: "+d[2]);
                    String dd = ""+d[2];
                    System.out.println("");
                    for (int j = 0; j < cantAtri*puntero; j++) {
                        for (int i = posi; i < dat.length(); i++) {
                            if(dat.charAt(i) != coma.charAt(0)){
                                atri += dat.charAt(i);
                                posi++;
                            }else if(dat.charAt(i) == coma.charAt(0)){
                                if(atri.equalsIgnoreCase(dd) && l == 0){
                                    estado = 1;
                                    estado2 = 1;
                                }
                                posi++;
                                break;
                            }
                        }
                        
                        if(estado != 1){
                            datos[k][l] = atri;
                            System.out.print(datos[k][l]+" ");
                        }
                        
                        l++;

                        if(l == cantAtri && k != puntero-del+1){
                            if(estado == 1){
                                l=0;
                                estado = 0;
                            }else{
                                k++;
                                l=0;
                            }
                        }
                        atri = "";

                        if(k == puntero-del+1){
                            break;
                        }

                    }
                    
                    if(estado2 == 0){
                        enviar("Error delete...");
                        panelEstado.setBackground(new Color(228, 65, 65));
                        txtError.setText("Error:  No quedan datos de la tabla para eliminar...");
                    }else{

                        String contenidoIngresado = "";

                        //Cargamos todo el array en un String, con el formato que se guardará la base de datos raiz
                        for (int i = 0; i < puntero-del; i++) {
                            for (int j = 0; j < cantAtri; j++) {
                                contenidoIngresado += datos[i][j];
                                if(j != cantAtri-1){
                                    contenidoIngresado += espacio+"|"+espacio;
                                }else{
                                    contenidoIngresado += "\n";
                                }
                            }
                        }
                        System.out.println(contenidoIngresado);

                        String contenidoBackup = "";

                        //Cargamos todo el array en un String, con el formato que se guardará la base de datos backup
                        for (int i = 0; i < puntero-del; i++) {
                            for (int j = 0; j < cantAtri; j++) {
                                if(j == 0 && i == 0){

                                }else{
                                    contenidoBackup += ",";
                                }
                                contenidoBackup += datos[i][j];
                            }
                        }
                        System.out.println(contenidoBackup);

                        //Actualizo la cantidad de eliminados de la tabla
                        int respuesta = escribirDatos(archiDelete, ""+del++);

                        //Creo el archivo raiz y escribo los datos actualizados en la base de datos TXT
                        archiFile.createNewFile();
                        int respuesta1 = escribirDatos(archiFile, contenidoIngresado);

                        //Creo el archivo backup y escribo los datos actualizados en la base de datos backup
                        archiBackupFile.createNewFile();
                        int respuesta2 = escribirDatos(archiBackupFile, contenidoBackup);

                        //Mensaje de confirmación
                        txtArea.setText("Registro eliminado correctamente...\n "
                                + "====================================\n"+contenidoIngresado);
                        enviar("Registro eliminado correctamente...\n "
                                + "====================================\n"+contenidoIngresado);
                        panelEstado.setBackground(new Color(76, 175, 80));
                        txtError.setText("Correcto:  Registro eliminado correctamente...");
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(VistaServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    //Métodos para validar consulta e Ingresar datos en la base de datos TXT
    String[] validarInsert(){
        String[] r2 = null;
        int cantAtri = 0;
        String consul = "";
        int posi = 12;
        int cant = 0;
        String nombre = "";
        
        for (int i = 0; i < 12; i++) {
            consul += txtConsulta.charAt(i);
        }
        System.out.println(consul);
        if(!consul.equalsIgnoreCase("INSERT INTO ")){
            System.out.println(consul);
        }else{
            for (int i = posi; i < txtConsulta.length(); i++) {
                if(txtConsulta.charAt(i) != espacio.charAt(0)){
                    nombre += txtConsulta.charAt(i);
                }else if(txtConsulta.charAt(i) == espacio.charAt(0)){
                    System.out.println(nombre);
                    posi = i+3;
                    break;
                }
            }
            
            String can = "cantidad_atributo_"+nombre+".txt";
            File archiCan = new File(crearUbicacionBackupCantAtri+can);
            if(archiCan.exists()){
                cantAtri = Integer.parseInt(leerDatos(archiCan));
            }
            
            int c = cantAtri-1;
            String[] r1 = new String[c];
            int cantAt = 0;
            r2 = new String[cantAtri];
            r2[cant] = nombre;
            cant++;
            nombre = "";
            
            for (int j = 0; j < cantAtri-1; j++) {
                for (int i = posi; i < txtConsulta.length(); i++) {
                    if(txtConsulta.charAt(i) != espacio.charAt(0)){
                        nombre += txtConsulta.charAt(i);
                    }else if(txtConsulta.charAt(i) == espacio.charAt(0)){
                        System.out.println(nombre);
                        r1[cantAt] = nombre;
                        cantAt++;
                        posi = i+3;
                        nombre = "";
                        break;
                    }
                }
            }
            
            posi += 9;
            int estad = 0;
            nombre = "";
            
            for (int j = 0; j < cantAtri-1; j++) {
                for (int i = posi; i < txtConsulta.length(); i++) {
                    if(txtConsulta.charAt(i) != espacio.charAt(0)){
                        if(txtConsulta.charAt(i) != comillaSimple.charAt(0)){
                            nombre += txtConsulta.charAt(i);
                        }else{
                            estad++;
                        }
                    }else if(txtConsulta.charAt(i) == espacio.charAt(0)){
                        if(estad%2!=0){
                            nombre += " ";
                        }else{
                            System.out.println(nombre);
                            r2[cant] = nombre;
                            cant++;
                            nombre = "";
                            if(j == cantAtri - 2){
                                break;
                            }else{
                                posi = i+3;
                                break;
                            }
                        }
                    }
                }
            }
            
            
        }
        
        return r2;
    }
    void insert(){
        //Validamos y capturamos el nombre de la tabla y los atributos a insertar
        String[] atributos = validarInsert();
        String nombreT = atributos[0];
        System.out.println(nombreT);
        
        //Creamos el nombre y archivo de la cantidad de atributos, puntero, raiz y backup de la tabla
        String can = "cantidad_atributo_"+nombreT+".txt";
        File archiCan = new File(crearUbicacionBackupCantAtri+can);
        //
        String punt = "puntero_"+nombreT+".txt";
        File archiPuntero = new File(crearUbicacionBackupPuntero+punt);
        //
        String archi = nombreT+".txt";
        File archiFile = new File(crearUbicacion+archi);
        //
        String archivoBackup = "backup_"+nombreT+".txt";
        archiBackupFile = new File(crearUbicacionBackup+archivoBackup);
        //
        String archivoDelete = "delete_"+nombreT+".txt";
        File archiDelete = new File(crearUbicacionBackupCantDelete+archivoDelete);
        
        if(!archiFile.exists()){
            enviar("Error...");
            panelEstado.setBackground(new Color(228, 65, 65));
            txtError.setText("Error:  Se encontraron errores en la consulta a la base de datos TXT...");
        }else{
            try {
                //Capturamos la cantidad de atributos y el puntero de la tabla
                int cantAtri = Integer.parseInt(leerDatos(archiCan));
                int puntero = Integer.parseInt(leerDatos(archiPuntero));
                int delete = Integer.parseInt(leerDatos(archiDelete));
                
                //Declaramos nos variables para llenar la base de datos TXT y su backup
                String contenido = "";
                String conteBackup = "";
                
                //Instanciamos el arreglo con los parametros del puntero y cantidad de atributos
                datos = new String[puntero - delete + 2][cantAtri];
                
                //Capturamos los datos del Backup de la tabla
                String dat = leerDatos(archiBackupFile);
                
                //Variables para el manejo de datos de la tabla
                String atri = "";
                int posi = 0;
                int k = 0;
                int l = 0;
                
                //Capturamos los datos de la tabla en un Array y le agregamos los nuevos campos al Array
                for (int j = 0; j < cantAtri*puntero - (delete*cantAtri); j++) {
                    for (int i = posi; i < dat.length(); i++) {
                        if(dat.charAt(i) != coma.charAt(0)){
                            atri += dat.charAt(i);
                            posi++;
                        }else if(dat.charAt(i) == coma.charAt(0)){
                            posi++;
                            break;
                        }
                    }
                    datos[k][l] = atri;
                    System.out.print(datos[k][l]+" | ");
                    l++;
                    if(l == cantAtri && k != puntero - delete - 1){
                        k++;
                        l=0;
                    }
                    
                    atri = "";
                    int cAtri = 1;
                    
                    if(j == cantAtri*puntero - (delete*cantAtri) - 1){
                        k++;
                        for (int i = 0; i < cantAtri; i++) {
                            if(i == 0){
                                String en = ""+puntero;
                                datos[k][i] = en;
                                System.out.print(datos[k][i]+" | ");
                            }else{
                                datos[k][i] = atributos[cAtri];
                                System.out.print(datos[k][i]+" | ");
                                cAtri++;
                            }
                        }
                    }
                }
                
                String contenidoIngresado = "";
                
                //Cargamos todo el array en un String, con el formato que se guardará la base de datos raiz
                for (int i = 0; i < puntero+1-delete; i++) {
                    for (int j = 0; j < cantAtri; j++) {
                        contenidoIngresado += datos[i][j];
                        
                        if(j != cantAtri-1){
                            contenidoIngresado += espacio+"|"+espacio;
                        }else{
                            contenidoIngresado += "\n";
                        }
                    }
                }
                System.out.println(contenidoIngresado);
                
                String contenidoBackup = "";
                
                //Cargamos todo el array en un String, con el formato que se guardará la base de datos backup
                for (int i = 0; i < puntero+1-delete; i++) {
                    for (int j = 0; j < cantAtri; j++) {
                        if(j == 0 && i == 0){
                            
                        }else{
                            contenidoBackup += ",";
                        }
                        contenidoBackup += datos[i][j];
                    }
                }
                System.out.println(contenidoBackup);
                    
                //Creo el archivo raiz y escribo los datos actualizados en la base de datos TXT
                archiFile.createNewFile();
                int respuesta1 = escribirDatos(archiFile, contenidoIngresado);
                
                //Creo el archivo backup y escribo los datos actualizados en la base de datos backup
                archiBackupFile.createNewFile();
                int respuesta2 = escribirDatos(archiBackupFile, contenidoBackup);
                
                //Creo el archivo backup puntero y escribo el puntero actual en la base de datos backup
                int envi = puntero+1;
                archiPuntero.createNewFile();
                int respuesta3 = escribirDatos(archiPuntero, ""+envi);
                
                //Mensaje de confirmación
                txtArea.setText("Tabla insertada correctamente...\n "
                        + "====================================\n"+contenidoIngresado);
                enviar("Tabla insertada correctamente...\n "
                        + "====================================\n"+contenidoIngresado);
                panelEstado.setBackground(new Color(76, 175, 80));
                txtError.setText("Correcto:  Tabla actualizada correctamente...");
            } catch (IOException ex) {
                Logger.getLogger(VistaServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    //Método Show, para visualizar todas las tablas de la base de datos TXT
    void showTables() {
        String sBase = System.getProperty("user.dir") + File.separator + "dbDistribuidos";
        File dir = new File(sBase);
        String listaFilestxt = "";

        if (!dir.exists()) {
            enviar("Error...");
            panelEstado.setBackground(new Color(228, 65, 65));
            txtError.setText("Error:  Se encontraron errores en la consulta a la base de datos TXT...");
            return;
        }
        File[] ficheros = dir.listFiles();
        int corre = ficheros.length;
        if (corre == 0) {
            enviar("Error de tablas...");
            panelEstado.setBackground(new Color(228, 65, 65));
            txtError.setText("Error:  No se encontraron tablas en la base de datos TXT...");
            return;
        }

        for (int x = 0; x < corre; x++) {
            if (ficheros[x].isFile()) {
                if (ficheros[x].getName().endsWith(".txt")) {
                    listaFilestxt = listaFilestxt + ficheros[x].getName() + "\n";
                }
            }
        }
        
        txtArea.setText("Lista de tablas...\n "
                        + "====================================\n" + listaFilestxt);
        enviar("Lista de tablas...\n "
                        + "====================================\n" + listaFilestxt);
        panelEstado.setBackground(new Color(76, 175, 80));
        txtError.setText("Correcto:  Tablas visualizadas correctamente...");
        
    }
    
    //Método Drop, para eliminar una tabla de la base de datos TXT
    //Tipo: 1 == DROP && 2 == TRUNCATE
    void dropTruncateTable(int pos, int tipo) {
        int posi = pos;
        String nombre = "";
        
        //Capturamos el nombre de la tabla a eliminar
        for (int i = posi; i < txtConsulta.length(); i++) {
            if(txtConsulta.charAt(i) != puntoComa.charAt(0)){
                nombre += txtConsulta.charAt(i);
            }else if(txtConsulta.charAt(i) == puntoComa.charAt(0)){
                
                System.out.println(nombre);
                break;
            }
        }
        
        //Creamos el nombre y archivo de la cantidad de atributos, puntero, raiz y backup de la tabla
        String archi = nombre+".txt";
        File archiFile = new File(crearUbicacion+archi);
        //
        String archivoBackup = "backup_"+nombre+".txt";
        archiBackupFile = new File(crearUbicacionBackup+archivoBackup);
        //
        String can = "cantidad_atributo_"+nombre+".txt";
        File archiCan = new File(crearUbicacionBackupCantAtri+can);
        //
        String punt = "puntero_"+nombre+".txt";
        File archiPuntero = new File(crearUbicacionBackupPuntero+punt);
        //
        String archivoDelete = "delete_"+nombre+".txt";
        File archiDelete = new File(crearUbicacionBackupCantDelete+archivoDelete);
        
        if(tipo == 1){
            if(archiFile.exists()){
                archiFile.delete();
                archiBackupFile.delete();
                archiCan.delete();
                archiPuntero.delete();
                archiDelete.delete();

                txtArea.setText("Tabla eliminada correctamente...");
                enviar("Tabla eliminada correctamente...");
                panelEstado.setBackground(new Color(76, 175, 80));
                txtError.setText("Correcto:  Tabla eliminada correctamente...");
            }else{
                enviar("Error: DROP TABLE...");
                panelEstado.setBackground(new Color(228, 65, 65));
                txtError.setText("Error:  No se encontro la tabla a eliminar...");
            }
        }else if(tipo == 2){
            if(archiFile.exists()){
                //Capturamos la cantidad de atributos y el puntero de la tabla
                int cantAtri = Integer.parseInt(leerDatos(archiCan));
                int puntero = Integer.parseInt(leerDatos(archiPuntero));
                
                //Instanciamos el arreglo con los parametros 1 y cantidad de atributos
                datos = new String[1][cantAtri];
                
                //Capturamos los datos del Backup de la tabla
                String dat = leerDatos(archiBackupFile);
                
                //Variables para el manejo de datos de la tabla
                String atri = "";
                int posii = 0;
                int l = 0;
                
                //Capturamos los datos de la tabla en un Array
                for (int j = 0; j < cantAtri; j++) {
                    for (int i = posii; i < dat.length(); i++) {
                        if(dat.charAt(i) != coma.charAt(0)){
                            atri += dat.charAt(i);
                            posii++;
                        }else if(dat.charAt(i) == coma.charAt(0)){
                            posii++;
                            break;
                        }
                    }
                    datos[0][l] = atri;
                    System.out.print(datos[0][l]+" ");
                    l++;
                    atri = "";
                }
                
            }else{
                enviar("Error: TRUNCATE TABLE...");
                panelEstado.setBackground(new Color(228, 65, 65));
                txtError.setText("Error:  No se encontro la tabla a limpiar...");
            }
        }
        
    }
    
    //Métodos de limpieza
    void limpiarTodo(){
        txtArea.setText("");
    }
    void limpiarError(){
        panelEstado.setBackground(new Color(255,255,255));
        txtError.setText("");
    }
    
    //Métodos para validar las consultas ingresadas por el cliente
    String tipoConsulta(int cant){
        String r = "";
        if(txtConsulta.length() < cant){
            
        }else{
            for (int i = 0; i < cant; i++) {
                r += txtConsulta.charAt(i);
            }
        }
        
        return r;
    }
    void validarTipoConsulta(){
        txtConsulta = txtArea.getText();
        
        //Validamos el tipo de consulta que mandó el Cliente
        if(!txtConsulta.isEmpty()){
            if(tipoConsulta(6).equalsIgnoreCase("CREATE")){
                createTabla();
            }else if(tipoConsulta(6).equalsIgnoreCase("SELECT")){
                select();
            }else if(tipoConsulta(6).equalsIgnoreCase("UPDATE")){
                updt();
            }else if(tipoConsulta(6).equalsIgnoreCase("DELETE")){
                delete();
            }else if(tipoConsulta(6).equalsIgnoreCase("INSERT")){
                insert();
            }else if(tipoConsulta(11).equalsIgnoreCase("SHOW TABLES")){
                showTables();
            }else if(tipoConsulta(10).equalsIgnoreCase("DROP TABLE")){
                System.out.println("Drop");
                dropTruncateTable(11,1);
            }else if(tipoConsulta(14).equalsIgnoreCase("TRUNCATE TABLE")){
                System.out.println("Truncate");
                dropTruncateTable(15,2);
            }else{
                enviar("Error...");
                panelEstado.setBackground(new Color(228, 65, 65));
                txtError.setText("Error:  Se encontraron errores en la consulta a la base de datos TXT...");
            }
        }
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1000, 604));
        setMinimumSize(new java.awt.Dimension(1000, 604));

        lblLoading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLoading.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/loading2.gif"))); // NOI18N
        lblLoading.setMaximumSize(new java.awt.Dimension(200, 200));
        lblLoading.setMinimumSize(new java.awt.Dimension(200, 200));
        lblLoading.setPreferredSize(new java.awt.Dimension(200, 200));

        txtArea.setEditable(false);
        txtArea.setColumns(20);
        txtArea.setFont(new java.awt.Font("SF UI Display", 0, 16)); // NOI18N
        txtArea.setRows(5);
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

        jLabel1.setFont(new java.awt.Font("SF UI Display", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SERVIDOR");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(451, 451, 451)
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 980, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(txtMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(400, 400, 400)
                        .addComponent(lblLoading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
                    .addComponent(panelEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(121, 121, 121)
                        .addComponent(lblLoading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(panelEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void salirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_salirMouseClicked
        panelEstado.setBackground(new Color(255,255,255));
        txtError.setText("");
    }//GEN-LAST:event_salirMouseClicked

    private void txtMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMensajeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMensajeActionPerformed

    private void txtMensajeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMensajeKeyReleased
        
    }//GEN-LAST:event_txtMensajeKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaServidor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
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

    public void proceso(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            System.out.println("Error en pantalla de carga..."+ex);
        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        new Thread(){
            public void run(){
                limpiarError();
                txtArea.setText("");
                lblLoading.setVisible(true);
                txtError.setForeground(Color.black);
                txtError.setText("Cargando...");
                proceso();
                lblLoading.setVisible(false);
                txtError.setForeground(Color.WHITE);
                txtError.setText("");
                txtArea.setText((String) arg);
                txtMensaje.setText((String) arg);
                validarTipoConsulta();
            }
        }.start();
        
        
    }
}
