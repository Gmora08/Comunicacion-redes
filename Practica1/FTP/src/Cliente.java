/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.net.*;
import javax.swing.JFileChooser;
/**
 *
 * @author Gustavo
 */
public class Cliente {
    
    private Socket cliente;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    
    public void conexion() throws IOException
    {
        cliente = new Socket(InetAddress.getByName("127.0.0.1"),4000);
        System.out.println("Conectado a :"+cliente.getInetAddress().getHostName());
    }
    
    public void flujos() throws IOException
    {
        oos = new ObjectOutputStream(cliente.getOutputStream());
        oos.flush();
        
        ois = new ObjectInputStream(cliente.getInputStream());
    }
    public void cerrarConexion() throws IOException
    {
        oos.close();
        ois.close();
        cliente.close();
    }
    public void enviaArhivos() throws IOException
    {
        int i,tam_bloque, bits_leidos;
        long leidos,completados;
        byte[] buffer = new byte[1024];
        JFileChooser fileChooser = new JFileChooser(); 
        fileChooser.setMultiSelectionEnabled(true);
        int resultado = fileChooser.showOpenDialog(null);
        
        FileInputStream fis;
        if(resultado==JFileChooser.APPROVE_OPTION)      
        {
            //File f = fileChooser.getSelectedFile();
            File[] f = fileChooser.getSelectedFiles();
         
            //Enviar cantidad de archivos
            oos.writeInt(f.length);
            oos.flush();
            for(i=0; i < f.length; i++)
            {
                String nombre = f[i].getName();
                String ruta = f[i].getAbsolutePath();
                long tamano = f[i].length();
                System.out.println("Nombre archivo:" + nombre + " ruta: " +ruta +" Tamaño archivo" + f[i].length() );
                oos.writeUTF(nombre);
                oos.flush();
                oos.writeLong(tamano);
                oos.flush();
                fis = new FileInputStream(f[i].getAbsolutePath());
                
                leidos = 0;
                tam_bloque = (fis.available() >= 1024)? 1024 : fis.available();
                
                while((bits_leidos = fis.read(buffer, 0, tam_bloque)) > 0)
                {
                    oos.write(buffer,0,bits_leidos);
                    oos.flush();
                    leidos = leidos + tam_bloque;
                    completados = (leidos * 100)/tamano;
                    System.out.println("\b\b\b\b\b\b\b\b\b\b\b");
                    System.out.println("Porcentaje de completado " + completados + "%");
                    tam_bloque = (fis.available() >= 1024)? 1024 : fis.available();

                }
                System.out.println("\nArchivo Enviado al Servidor Con Exito.\n");
                
                
            }
        }
        
      
        
    }
    
     public static void main(String[] args) throws IOException
     {
         System.out.println("Enviar archivos");
         Cliente c = new Cliente();
         c.conexion();
         c.flujos();
         c.enviaArhivos();
         System.out.println("Archivos Enviados al servidor con exito");
         System.out.println("Cerrando conexion");
         c.cerrarConexion();
         
     }
}
