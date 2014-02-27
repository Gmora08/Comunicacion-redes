/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package buscaminas;

/**
 *
 * @author eric
 */
import java.awt.Color;
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

public class demente extends JPanel implements ActionListener{
	private int casillasFaltantes = 0, minas3 = 30;
	int valoresDemente[][] = new int[minas3][minas3];
	public JLabel labelD;	
	private JButton botonesDemente[][] = new JButton[minas3][minas3];
	public JButton botonD;
	
	public String[] archi = {"/iconos/gano.png", "/iconos/perdio.png", "/iconos/nueva.png"};
	private String archivos[] = {"/iconos/0.PNG", "/iconos/1.PNG", "/iconos/2.PNG", "/iconos/3.PNG", "/iconos/4.PNG", "/iconos/5.PNG", "/iconos/6.PNG", "/iconos/7.PNG", "/iconos/8.PNG", "/iconos/9.PNG"};
	
	private ImageIcon[] imagenes = new ImageIcon[10];    
	public ImageIcon[] ima = new ImageIcon[3];   
    
    private boolean visibleDemente[][] = new boolean[minas3][minas3];
	

	public demente() {
		this.setLayout(null);
		for(int i=0;i<3;i++){
            ima[i] = new ImageIcon(getClass().getResource(archi[i]));
        }
		nuevaPartidaDemente();        
		this.setSize(600, 640);
		botonD = new JButton();
		botonD.setBounds(286, 5, 30, 30);
        botonD.setIcon(ima[2]);                
        this.add(botonD);
        this.botonD.addActionListener(this);
		labelD = new JLabel();
		labelD.setBounds(15, 15, 60, 15);
		this.add(labelD);
	}
	
	public void nuevaPartidaDemente(){
        casillasFaltantes = 0;        
        ponerBotonesDemente();
        verDemente(false);
        ponerMinasDemente();
        contornoDemente();
        visualizarMinasDemente();
        eventosDemente();
    }
	
	public void ponerBotonesDemente(){               
        
        for(int i=0;i<10;i++){
            imagenes[i] = new ImageIcon(getClass().getResource(archivos[i]));
        }
        
        for(int f = 0; f<minas3; f++){
        for(int c = 0; c<minas3; c++){
            botonesDemente[f][c] = new JButton();
            botonesDemente[f][c].setBounds(20*f, 20*c+40,20, 20);
            botonesDemente[f][c].setBackground(Color.gray);            
            this.add(botonesDemente[f][c]);
        }
        }        
    }
	
	public void ponerMinasDemente(){
        for(int f=0;f<minas3;f++){
        for(int c=0;c<minas3;c++){
            valoresDemente[f][c]=0;
        }
        }
           int f1, c1;
        for ( int i=0;i<3*minas3;i++){
            do{
                f1=(int)(Math.random()*minas3);
                c1=(int)(Math.random()*minas3);
            }while(valoresDemente[f1][c1]!=0);
            valoresDemente[f1][c1]=9;
        }
    }
	
	public void contornoDemente(){
        for(int f=0;f<minas3;f++){
            for(int c=0;c<minas3;c++){
                if(valoresDemente[f][c]==9){
                    for(int f2=f-1;f2<=f+1;f2++){
                        for(int c2=c-1;c2<=c+1;c2++){
                            if(f2>=0 && f2<minas3 && c2>=0 && c2<minas3 && valoresDemente[f2][c2]!=9)
                                valoresDemente[f2][c2]++;
                        }
                      }
                   }
                }
            }
        }
	
	public void verDemente(boolean valor){
        for(int f=0;f<minas3;f++){
        for(int c=0;c<minas3;c++){
            visibleDemente[f][c]=valor;
        }
        }
    }
	
	public void pulsarBotonVasDemente(int f, int c){
        if(f>=0 && f<minas3 && c>=0 && c<minas3 && visibleDemente[f][c]==false){
            if(visibleDemente[f][c]==false){
                visibleDemente[f][c]=true;
                if(valoresDemente[f][c]==9){
                    verDemente(true);
                    JOptionPane.showMessageDialog(null, "              PERDISTE");
                    botonD.setIcon(ima[1]);}
            else if(visibleDemente[f][c]==true){
                casillasFaltantes++;
                if (casillasFaltantes == 810){
                    verDemente(true);
                    JOptionPane.showMessageDialog(null, "              GANASTE");
                    botonD.setIcon(ima[0]);
                    labelD.setText("");
                }
                labelD.setText(casillasFaltantes+"/810");
            }
            }
            if(valoresDemente[f][c]==0){
                pulsarBotonVasDemente(f, c-1);
                pulsarBotonVasDemente(f, c+1);
                pulsarBotonVasDemente(f-1, c);
                pulsarBotonVasDemente(f+1, c);
                
            }
        }
    }
	
	public void pulsarBotonDemente(int f, int c) {
             try {
                Tablero.tirada(f, c);
            } catch (IOException ex) {
                Logger.getLogger(principiante.class.getName()).log(Level.SEVERE, null, ex);
            }
            pulsarBotonVasDemente(f,c);
            visualizarMinasDemente();
    }
	
	public void eventosDemente(){
        for(int f=0;f<minas3;f++){
        for(int c=0;c<minas3;c++){
            botonesDemente[f][c].addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    for(int f=0;f<minas3;f++){
                    for(int c=0;c<minas3;c++){
                        if(e.getSource()==botonesDemente[f][c])
                            pulsarBotonDemente(f,c);
                    }
                    }
                }
                }
            );
        }
        }
    }
	
	public void visualizarMinasDemente(){
        for(int f=0;f<minas3;f++){
        for(int c=0;c<minas3;c++){
         if(visibleDemente[f][c]==false){
           botonesDemente[f][c].setText("");
         }else if(visibleDemente[f][c]==true){
           if(valoresDemente[f][c]==0){
           botonesDemente[f][c].setIcon(imagenes[0]);
           }else if(valoresDemente[f][c]==1){
           botonesDemente[f][c].setIcon(imagenes[1]);
           }else if(valoresDemente[f][c]==2){
           botonesDemente[f][c].setIcon(imagenes[2]);
           }else if(valoresDemente[f][c]==3){
           botonesDemente[f][c].setIcon(imagenes[3]);
           }else if(valoresDemente[f][c]==4){
           botonesDemente[f][c].setIcon(imagenes[4]);
           }else if(valoresDemente[f][c]==5){
           botonesDemente[f][c].setIcon(imagenes[5]);
           }else if(valoresDemente[f][c]==6){
           botonesDemente[f][c].setIcon(imagenes[6]);
           }else if(valoresDemente[f][c]==7){
           botonesDemente[f][c].setIcon(imagenes[7]);
           }else if(valoresDemente[f][c]==8){
           botonesDemente[f][c].setIcon(imagenes[8]);
           }else if(valoresDemente[f][c]==9)
           botonesDemente[f][c].setIcon(imagenes[9]);
          }
        }
       }
   }
	
	public void quitarBotonesDemente(){
        for(int f1 = 0; f1<minas3; f1++){
               for(int c1 = 0; c1<minas3; c1++){
                   this.remove(botonesDemente[f1][c1]);
               }
        }
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==botonD){
			botonD.setIcon(ima[2]);
            quitarBotonesDemente();
            this.setVisible(false);            
            labelD.setText("");
			nuevaPartidaDemente();
			this.setVisible(true);
		}		
	}
}