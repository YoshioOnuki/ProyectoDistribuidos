/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author yoshio
 */
public class Proceso extends Thread{
    private JLabel loading;
    
    public Proceso(JLabel loading){
        this.loading = loading;
    }
    
    public void run(){
        loading.setVisible(true);
        proceso();
        loading.setVisible(false);
    }
    
    public void proceso(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            System.out.println("Error en pantalla de carga... "+ex);
        }
    }
}
