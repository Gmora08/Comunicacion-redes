/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package buscaminas;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class principiante extends JPanel implements ActionListener{
	
    private int casillasFaltantes = 0, minas1 = 9;
    int valoresPrincipiante[][] = new int[minas1][minas1];
    public JLabel labelP;	
    private JButton botonesPrincipiante[][] = new JButton[minas1][minas1];
    public JButton botonP;
	
    public String[] archi = {"/iconos/gano.png", "/iconos/perdio.png", "/iconos/nueva.png"};
    private String archivos[] = {"/iconos/0.PNG", "/iconos/1.PNG", "/iconos/2.PNG", "/iconos/3.PNG", "/iconos/4.PNG", "/iconos/5.PNG", "/iconos/6.PNG", "/iconos/7.PNG", "/iconos/8.PNG", "/iconos/9.PNG"};
	
    private ImageIcon[] imagenes = new ImageIcon[10];    
    public ImageIcon[] ima = new ImageIcon[3];   
    
    private boolean visiblePrincipiante[][] = new boolean[minas1][minas1];
    	
    public principiante(){
        this.setLayout(null);
	for(int i=0;i<3;i++){
            ima[i] = new ImageIcon(getClass().getResource(archi[i]));
        }
	nuevaPartidaPrincipiante();        
	this.setSize(400, 440);
	botonP = new JButton();
	botonP.setBounds(86, 5, 30, 30);
        botonP.setIcon(ima[2]);                
        this.add(botonP);
        this.botonP.addActionListener(this);
		labelP = new JLabel();
		labelP.setBounds(15, 15, 40, 15);
		this.add(labelP);
	}
	
    /** Inicia una una partida **/
	public void nuevaPartidaPrincipiante(){      
            ponerBotonesPrincipiante();	 //coloca los botones grises
            casillasFaltantes = 0;
            verPrincipiante(false); // pone todas las casillas en false
            //ponerMinasPrincipiante(); //coloca las minas que van en el tablero
            //contornoPrincipiante();
            //visualizarMinasPrincipiante();
            eventosPrincipiante();         
        }	
	
	public void ponerBotonesPrincipiante(){
        for(int i=0;i<10;i++){
            imagenes[i] = new ImageIcon(getClass().getResource(archivos[i]));
        }        
        for(int f = 0; f<minas1; f++){
            for(int c = 0; c<minas1; c++){
                botonesPrincipiante[f][c] = new JButton();
                botonesPrincipiante[f][c].setBounds(40*f, 40*c+40,40, 40);
                botonesPrincipiante[f][c].setBackground(Color.gray);
                this.add(botonesPrincipiante[f][c]);
            }
        }        
    }
	
	public void ponerMinasPrincipiante(){
        for(int f=0;f<minas1;f++){
            for(int c=0;c<minas1;c++){
                valoresPrincipiante[f][c]=0;
            }
        }
        
        int f1, c1;
        for ( int i=0;i<minas1;i++){
            do{
                f1=(int)(Math.random()*minas1);
                c1=(int)(Math.random()*minas1);
            }while(valoresPrincipiante[f1][c1]!=0);
            valoresPrincipiante[f1][c1]=9;
        }
    }
	
	public void contornoPrincipiante(){
        for(int f=0;f<minas1;f++){
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
        }
	
	public void verPrincipiante(boolean valor){
        for(int f=0;f<minas1;f++){
            for(int c=0;c<minas1;c++){
                visiblePrincipiante[f][c]=valor;
        }
        }
    }
	
	public void pulsarBotonVasPrincipiante(int f, int c){
        if(f>=0 && f<minas1 && c>=0 && c<minas1 && visiblePrincipiante[f][c]==false){
            if(visiblePrincipiante[f][c]==false){
            	visiblePrincipiante[f][c]=true;
                if(valoresPrincipiante[f][c]==9){ //Si la mina apretada tiene el valor de 9 pierdes
                    verPrincipiante(true); 
                    JOptionPane.showMessageDialog(null, "              PERDISTE");
                    botonP.setIcon(ima[1]);}
                else if(visiblePrincipiante[f][c]==true){
                    casillasFaltantes++;
                    if (casillasFaltantes == 90){
                        verPrincipiante(true);
                        JOptionPane.showMessageDialog(null, "              GANASTE");
                        botonP.setIcon(ima[0]);
                        labelP.setText("");
                    }
                    labelP.setText(casillasFaltantes+"/90");
                }
            }
            if(valoresPrincipiante[f][c]==0){
                pulsarBotonVasPrincipiante(f, c-1);
                pulsarBotonVasPrincipiante(f, c+1);
                pulsarBotonVasPrincipiante(f-1, c);
                pulsarBotonVasPrincipiante(f+1, c);
            }
        }
    }
	
	public void pulsarBotonPrincipiante(int f, int c) {
            try {
                Tablero.tirada(f, c);
            } catch (IOException ex) {
                Logger.getLogger(principiante.class.getName()).log(Level.SEVERE, null, ex);
            }
            int num = 0;
        try {
            num = Tablero.leerTiro();
        } catch (IOException ex) {
            Logger.getLogger(principiante.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            if(num == -1)
            {
                botonesPrincipiante[f][c].setText("XD");
                botonesPrincipiante[f][c].setEnabled(false);
                JOptionPane.showMessageDialog(null, "Has perdido el juego");
                try {
                    Tablero.CerrarConexion();
                } catch (IOException ex) {
                    Logger.getLogger(principiante.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }
            else if (num == 0)
            {
                botonesPrincipiante[f][c].setText("B");
                botonesPrincipiante[f][c].setEnabled(false);
            }
            else if (num == 1)
            {
                botonesPrincipiante[f][c].setText("1");
                botonesPrincipiante[f][c].setEnabled(false);
            }
            else if (num == 2)
            {
                botonesPrincipiante[f][c].setText("2");
                botonesPrincipiante[f][c].setEnabled(false);
            }
             else if (num == 10)
            {
                JOptionPane.showMessageDialog(null, "Ya ganastesesss");
            }
            
            //pulsarBotonVasPrincipiante(f,c);
            //visualizarMinasPrincipiante();
    }
	
	public void eventosPrincipiante(){
        for(int f=0;f<minas1;f++){
            for(int c=0;c<minas1;c++){
                this.botonesPrincipiante[f][c].addActionListener( new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    for(int f=0;f<minas1;f++){
                        for(int c=0;c<minas1;c++){
                            if(e.getSource()==botonesPrincipiante[f][c])
                                pulsarBotonPrincipiante(f,c);
                    }
                    }
                }
                }
            );
        }
        }
    }
	
	public void visualizarMinasPrincipiante(){
        for(int f=0;f<minas1;f++){
            for(int c=0;c<minas1;c++){
                if(visiblePrincipiante[f][c]==false){
                botonesPrincipiante[f][c].setText("");
                }else if(visiblePrincipiante[f][c]==true){
                    if(valoresPrincipiante[f][c]==0){
                            botonesPrincipiante[f][c].setIcon(imagenes[0]);
                        }else if(valoresPrincipiante[f][c]==1){
                            botonesPrincipiante[f][c].setIcon(imagenes[1]);
                        }else if(valoresPrincipiante[f][c]==2){
                            botonesPrincipiante[f][c].setIcon(imagenes[2]);
                        }else if(valoresPrincipiante[f][c]==3){
                            botonesPrincipiante[f][c].setIcon(imagenes[3]);
                        }else if(valoresPrincipiante[f][c]==4){
                            botonesPrincipiante[f][c].setIcon(imagenes[4]);
                        }else if(valoresPrincipiante[f][c]==5){
                            botonesPrincipiante[f][c].setIcon(imagenes[5]);
                        }else if(valoresPrincipiante[f][c]==6){
                            botonesPrincipiante[f][c].setIcon(imagenes[6]);
                        }else if(valoresPrincipiante[f][c]==7){
                            botonesPrincipiante[f][c].setIcon(imagenes[7]);
                        }else if(valoresPrincipiante[f][c]==8){
                            botonesPrincipiante[f][c].setIcon(imagenes[8]);
                        }else if(valoresPrincipiante[f][c]==9)
                            botonesPrincipiante[f][c].setIcon(imagenes[9]);
                     }

        }
       }
   }
	
	public void quitarBotonesPrincipiante(){
        for(int f1 = 0; f1<minas1; f1++){
               for(int c1 = 0; c1<minas1; c1++){
                   this.remove(botonesPrincipiante[f1][c1]);
               }
   }
 }

	@Override
	public void actionPerformed(ActionEvent eP) {
		if(eP.getSource()==botonP){
			botonP.setIcon(ima[2]);
            quitarBotonesPrincipiante();
            this.setVisible(false);            
            labelP.setText("");
			nuevaPartidaPrincipiante();
			this.setVisible(true);
		}		
	}	
}
