/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.net.*;
import java.io.*;

/**
 *
 * @author Gustavo
 */
public class Servidor_FTP2 {
    
    public static void main(String args[])throws Exception
    {
        int puerto = 4000;
        ServerSocket servidor = new ServerSocket(puerto);
        System.out.println("Servidor iniciado esperando una conexión del cliente");
        for(;;)
        {
            Socket cliente = servidor.accept();
            System.out.println("Cliente conectado desde:"+cliente.getInetAddress()+":"+cliente.getPort());
            System.out.println("Esperando transferencia de archivos");   
            byte[]buf = new byte[1500];
            BufferedInputStream bis = new BufferedInputStream(cliente.getInputStream());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("mimo.jpg"));
            int leidos;
            while((leidos=bis.read(buf,0,buf.length))!=-1)
            {
                bos.write(buf,0,leidos);
		bos.flush();
            }
            bis.close();
            bos.close();
            System.out.println("Archivos copiado....");
        }
    }
    
}
