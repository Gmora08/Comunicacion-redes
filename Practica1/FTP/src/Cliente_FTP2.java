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
public class Cliente_FTP2 {
    public static void main(String args[])throws Exception
    {
        Socket cl = new Socket(InetAddress.getByName("127.0.0.1"),4000);
        BufferedOutputStream bos = new BufferedOutputStream(cl.getOutputStream());
    }
}
