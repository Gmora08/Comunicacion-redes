package buscaminas;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Servidor {
    int[][] tablero;
    int nivel;
    String nombre;
    int minas;
    int f,c;
    ServerSocket ss;
    
    public Servidor(){
        try{
            ss = new ServerSocket(4000);
        }catch(IOException e){
            System.out.println("Error al Cerar Servidor: "+e.getMessage());
        }
    }
    
    public void servicio(){
        if(ss != null){
            while(true){
                try {
                    System.out.println("Servidor Iniciado...");
                    Socket c = ss.accept();
                    c.setSoTimeout(60000);

                    ObjectInputStream ois = new ObjectInputStream(c.getInputStream());
                    ObjectOutputStream oos = new ObjectOutputStream(c.getOutputStream());

                    nivel = ois.readInt();
                    System.out.println("Nivel:" + nivel);
                    nombre = ois.readUTF();
                    System.out.println("Jugador:" + nombre);
                    oos.writeInt(creaTablero());
                    oos.flush();
                    while(true){
                        try{
                            x = ois.readInt();
                            y = ois.readInt();
                            break;
                        }catch(IOException e){}
                        Thread.sleep(200);
                    }
                    System.out.println("X: " + x + " Y: " + y);

                    ois.close();
                    oos.close();
                    c.close();
                    ss.close();
                    break;
                } catch (IOException | InterruptedException e) {
                     System.out.println("Error Servicio:"+e.getMessage());
                }
            }
        }
    }
    
    public int creaTablero(){
        if(nivel == 0){
            f = 9;
            c = 9;
            minas = 10;
        }
        else if(nivel == 1){
            f = 16;
            c = 16;
            minas = 40;
        }
        else{
            f = 16;
            c = 30;
            minas = 99;
        }
        //Crear Tablero dependiendo la dificultad
        tablero = new int[f][c];
        for(int i = 0;i < f; i++){
            for(int j = 0;j < c;j++){
                tablero[f][c] = 0;
            }
        }
        //Generar Minas
        int fm,cm;
        for (int i=0;i<minas;i++){
            do{
                fm=(int)(Math.random()*f);
                cm=(int)(Math.random()*c);
            }while(tablero[fm][cm]!=0);
            tablero[fm][cm]=-1;
        }
        //Generar Casillas 
        int x,y;
        for(int y=0;y<f;y++){
            for(int c=0;c<minas1;c++){
                
                if(valoresPrincipiante[f][c]==9){
                    for(int f2=f-1;f2<=f+1;f2++){
                        for(int c2=c-1;c2<=c+1;c2++){
                            if(f2>=0 && f2<minas1 && c2>=0 && c2<minas1 && valoresPrincipiante[f2][c2]!=9)
                                valoresPrincipiante[f2][c2]++;
                        }
                      }
                   }
                }
        }
        
        return 0;
    }
    
    
    public static void main(String[] args) {
        Servidor s = new Servidor();
        s.servicio();
    }
}
