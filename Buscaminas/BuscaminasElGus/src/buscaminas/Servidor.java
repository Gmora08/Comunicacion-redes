package buscaminas;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    int[][] tablero;
    int nivel;
    String jugador;
    int minas,destapadas;
    int f,c,posf,posc;
    ServerSocket ss;
    boolean gano,perdio;
    
    public Servidor(){
        try{
            ss = new ServerSocket(4000);
        }catch(IOException e){
            System.out.println("Error al Crear Servidor: "+e.getMessage());
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
                    jugador = ois.readUTF();
                    System.out.println("Jugador:" + jugador);
                    //Envia Resultado de Crea Tablero
                    int  rtab = creaTablero();
                    gano = false;
                    perdio = false;
                    oos.writeInt(rtab);
                    oos.flush();
                    while(true){
                        if(gano || perdio){
                            break;
                        }
                        try{
                            posf = ois.readInt();
                            posc = ois.readInt();
                            System.out.println("PosFila: " + posf + " PosColumna: " + posc);
                            tirada(posf,posc,oos);
                        }catch(IOException e){}
                       // Thread.sleep(100);
                    }

                    ois.close();
                    oos.close();
                    c.close();
                    ss.close();
                    break;
                } catch (Exception e) {
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
        destapadas = f*c - minas;
        tablero = new int[f+1][c+1];
        for(int i = 0;i < f; i++){
            for(int j = 0;j < c;j++){
                tablero[i][j] = 0;
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
        for(x=0;x<f;x++){
            for(y=0;y<c;y++){
                if(tablero[x][y]==-1){
                    for(int f2=x-1;f2<=x+1;f2++){
                        for(int c2=y-1;c2<=y+1;c2++){
                            if(f2>=0 && f2<f && c2>=0 && c2<c && tablero[f2][c2]!=-1)
                                tablero[f2][c2]++;
                        }
                    }
                }
            }
        }
        //Imprimir tablero
        for(int i = 0;i < f; i++){
            for(int j = 0;j < c;j++){
                System.out.print(tablero[i][j] + ",");
            }
            System.out.print("\n");
        }
        return 0;
    }
    
    public void tirada(int fil,int col,ObjectOutputStream oos)throws IOException{
        int casilla = tablero[fil][col];
        //Mina
        System.out.println(casilla);
        if(destapadas != 0){
            if(casilla == -1){
                destapadas--;
                //Envio codigo de perdio
                oos.writeInt(-1);
                oos.flush();
                perdio = true;
            }
            //Casilla con Numero
            else if(casilla >=0){
                destapadas--;
                oos.writeInt(casilla);
                oos.flush();
                //Envio codigo de numero solo seguido del numero
            }
        }
        else
        {
            oos.writeInt(10);
            oos.flush();
        }
        
        //else if(casilla == 0){
            //destapadas = destapadas - total de espacios en blanco adyacentes
            //Envio codigo de espacio en blanco y un arreglo de las casillas adyacentes???
        //}
    }
    
    
    
    public static void main(String[] args) {
        Servidor s = new Servidor();
        s.servicio();
    }
}
