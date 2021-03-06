/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package buscaminas;
 

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.net.*;
import java.io.*;


public class Tablero extends JFrame implements ActionListener{

    principiante nivelPrincipiante = new principiante();
    intermedio nivelIntermedio = new intermedio();
    demente nivelDemente = new demente();
	
    private JMenuBar barra;
    private JMenu juego, ayuda;
    private JMenuItem principiante, intermedio, demente;    
    private boolean prin=true, inter=false, demen=false;  
    
    static boolean ciclo = true;
    String ip;
    int puerto;
    int dificultad;
    String jugador;
    static Socket cliente;
    static ObjectOutputStream oos;
    static ObjectInputStream ois;
    
    private String[] archi = {"/iconos/gano.png", "/iconos/perdio.png", "/iconos/nueva.png"};
	  
    private ImageIcon[] ima = new ImageIcon[3]; 
    
    public Tablero(String ip, int puerto, int dificultad, String jugador){    	
    	for(int i=0;i<3;i++){
            ima[i] = new ImageIcon(getClass().getResource(archi[i]));
        
        
    }
    if(dificultad == 0)
    {
        this.add(nivelPrincipiante);
        setSize(206, 294);
    }
    else if(dificultad == 1)
    {
        this.add(nivelIntermedio);
        setSize(500, 540);
    }
    else
    {
        this.add(nivelDemente);
        setSize(600, 640);
    }
    this.setLayout(null);
    this.setTitle("Buscaminas!!!");        
    barra = new JMenuBar();

    //juego = new JMenu("Juego");
    ayuda = new JMenu("Ayuda");

    principiante = new JMenuItem("Principiante");
    intermedio = new JMenuItem("Intermedio");
    demente = new JMenuItem("Demente");
    //agregamos los items de menu
    //juego.add(principiante);
    //juego.add(intermedio);
    //juego.add(demente);
    //agregado los menu ala barra
   // barra.add(juego);
    barra.add(ayuda);
    //bara agregada al frame
    this.setJMenuBar(barra);
    //this.principiante.addActionListener(this);
    //this.intermedio.addActionListener(this);
    //this.demente.addActionListener(this);
    //propiedades del frame
            
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
    setIconImage(new ImageIcon(getClass().getResource("/iconos/icono.png")).getImage());
    setVisible(true);
    
        System.out.println("Entro al construcutor");
        
    this.ip = ip;
    this.puerto = puerto;
    this.dificultad = dificultad;
    this.jugador = jugador;
      
    }

    
    public void actionPerformed(ActionEvent e) {        
        if(e.getSource()==demente){
        	nivelDemente.botonD.setIcon(ima[2]);
        	nivelDemente.quitarBotonesDemente();
        	nivelDemente.setVisible(false);            
        	nivelDemente.labelD.setText("");
        	nivelDemente.nuevaPartidaDemente();
        	nivelDemente.setVisible(true);
        	if(prin){        		
        		this.remove(nivelPrincipiante);
        		this.add(nivelDemente);
        		prin=false;        		
        		demen=true;
        	}else if(inter){  
        		this.remove(nivelIntermedio);
        		this.add(nivelDemente);
        		inter=false;
        		demen=true;  
        	}
            setSize(606, 695);            
            setLocationRelativeTo(null);      
        }else if(e.getSource()==intermedio){ 
        	nivelIntermedio.botonI.setIcon(ima[2]);
        	nivelIntermedio.quitarBotonesIntermedio();
        	nivelIntermedio.setVisible(false);            
        	nivelIntermedio.labelI.setText("");
        	nivelIntermedio.nuevaPartidaIntermedio();
        	nivelIntermedio.setVisible(true);
        	if(prin){
        		this.remove(nivelPrincipiante);
        		this.add(nivelIntermedio);
        		prin=false;
        		inter=true; 
        	}else if(demen){
        		this.remove(nivelDemente);
        		this.add(nivelIntermedio);
        		inter=true;
        		demen=false;  
        	}
            setSize(406, 495);
            setLocationRelativeTo(null);  
        }else if(e.getSource()==principiante){        	
            
            nivelPrincipiante.botonP.setIcon(ima[2]);
            nivelPrincipiante.quitarBotonesPrincipiante();
            nivelPrincipiante.setVisible(false);            
            nivelPrincipiante.labelP.setText("");
            nivelPrincipiante.nuevaPartidaPrincipiante();
            nivelPrincipiante.setVisible(true);
        	if(inter){
        		this.remove(nivelIntermedio);
        		this.add(nivelPrincipiante);
        		inter=false;
        		prin=true; 
        	}else if(demen){ 
        		this.remove(nivelDemente);
        		this.add(nivelPrincipiante);
        		prin=true;
        		demen=false;  
        	}	               
            setSize(206, 294);
            setLocationRelativeTo(null);   
        }
    } 
    
    
     public void conexion() throws IOException
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

        if (error != 0 )
        {
            System.out.println("Error del servidor al crear el tablero");
        }
       
        //oos.close();
        //ois.close();
        //cliente.close();
        
    }
    
    static void tirada(int x, int y) throws IOException
    {
        oos.writeInt(y);
        oos.flush();
        oos.writeInt(x);
        oos.flush();
    }
    
    static int leerTiro() throws IOException
    {
        
        while(ciclo){
            try{
                int num = ois.readInt();
                System.out.println(num);
                return num;
            }catch(Exception e){}
        }
        return 0;
    }
    
    static void CerrarConexion() throws IOException
    {
        ciclo = false;
        oos.close();
        ois.close();
        cliente.close();
    }

    
}