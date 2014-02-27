/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.net.*;
/**
 *
 * @author Gustavo
 */
public class Servidor {
    
    ServerSocket servidor;
    Socket conexion;
    ObjectOutputStream oos;
    ObjectInputStream ois;
    FileOutputStream fos;
    String path;
    
    public void correrServidor()
    {
        try
        {
            servidor = new ServerSocket(4000);
            while(true)
            {
                try
                {
                    esperarConexion();
                    System.out.println("Ya sali");
                    flujos();
                    recibirArchivos();
                    System.out.println("Archivos recibidos en el servidor");

                }
                catch(EOFException e)
                {
                    e.printStackTrace();
                    System.out.println("Servidor termino la conexion");
                }
                finally
                {
                    cerrarConexion();
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void esperarConexion() throws IOException
    {
        System.out.println("Esperando conexion del cliente");
        conexion = servidor.accept();
        System.out.println("Conexion aceptada desde:"+conexion.getInetAddress().getHostName());
    }
    
    public void flujos() throws IOException
    {
        oos = new ObjectOutputStream(conexion.getOutputStream());
        oos.flush();
        
        ois = new ObjectInputStream(conexion.getInputStream());
    }
    
    public void recibirArchivos() throws IOException
    {
        int i,num_archivos,tam_bloque, bits_leidos;
        String nombreArchivo;
        byte[] buffer = new byte[1024];
        long leidos, completados, tamano_arch;
        
        num_archivos = ois.readInt();
        
        for (i=0; i<num_archivos; i++)
        {
            nombreArchivo = ois.readUTF();
            fos = new FileOutputStream(nombreArchivo);
            tamano_arch = ois.readLong();
            
            System.out.println("Archivo a leer: "+ nombreArchivo + "Tmaño total" + tamano_arch);
            
            leidos = 0;
            tam_bloque = (tamano_arch >= 1024)? 1024 : (int) tamano_arch;
            
            while((bits_leidos = ois.read(buffer, 0, tam_bloque)) != 0)
            {
                fos.write(buffer, 0, bits_leidos);
                fos.flush();
                leidos = leidos + tam_bloque;
                completados = (leidos * 100)/tamano_arch;
                System.out.print("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b");
                System.out.print("Completado:"+completados+"%");
                tam_bloque = (tamano_arch-leidos >= 1024)? 1024 : (int) (tamano_arch - leidos);
            }
            
            
        }
        
        
    }
    
    public void cerrarConexion() throws IOException
    {
        fos.close();
        ois.close();
        conexion.close();
    }
    
    public static void main(String[] args) throws IOException
    {
        Servidor s = new Servidor();
        s.correrServidor();
        
        
         
    }
    
}
