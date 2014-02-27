/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package buscaminas;

import java.net.Socket;
import java.io.*;
import java.net.*;

/**
 *
 * @author usuario
 */
public class Tablero2 extends javax.swing.JFrame {
    
    static String ip;
    static int puerto;
    static int dificultad;
    static String jugador;
    static Socket cliente;
    static ObjectOutputStream oos;
    static ObjectInputStream ois;
    /**
     * Creates new form Tablero2
     */
    public Tablero2(String ip, int puerto, int dificultad, String jugador) {
        initComponents();
        this.ip = ip;
        this.puerto = puerto;
        this.dificultad = dificultad;
        this.jugador = jugador;
    }
    
    public static void conexion() throws IOException
    {
        int error;
        cliente = new Socket(InetAddress.getByName(ip), puerto);
        System.out.println("Conectado a :"+cliente.getInetAddress().getHostName());
        oos = new ObjectOutputStream(cliente.getOutputStream());
        oos.flush();
        ois = new ObjectInputStream(cliente.getInputStream());
        oos.writeInt(dificultad);
        oos.flush();
        oos.writeUTF(jugador);
        oos.flush();
        error = ois.readInt();
        if (error == 1 )
        {
            //Habilitar panel
        }
        oos.writeInt(2);
        oos.flush();
        oos.writeInt(50);
        oos.flush();
        oos.close();
        ois.close();
        cliente.close();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
