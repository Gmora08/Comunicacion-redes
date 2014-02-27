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

public class intermedio extends JPanel implements ActionListener{
	private int casillasFaltantes = 0, minas2 = 20;
	int valoresIntermedio[][] = new int[minas2][minas2];
	public JLabel labelI;	
	private JButton botonesIntermedio[][] = new JButton[minas2][minas2];
	public JButton botonI;
	
	public String[] archi = {"/iconos/gano.png", "/iconos/perdio.png", "/iconos/nueva.png"};
	private String archivos[] = {"/iconos/0.PNG", "/iconos/1.PNG", "/iconos/2.PNG", "/iconos/3.PNG", "/iconos/4.PNG", "/iconos/5.PNG", "/iconos/6.PNG", "/iconos/7.PNG", "/iconos/8.PNG", "/iconos/9.PNG"};
	
	private ImageIcon[] imagenes = new ImageIcon[10];    
	public ImageIcon[] ima = new ImageIcon[3];   
    
    private boolean visibleIntermedio[][] = new boolean[minas2][minas2];
	
	
	public intermedio() {
		this.setLayout(null);
		for(int i=0;i<3;i++){
            ima[i] = new ImageIcon(getClass().getResource(archi[i]));
        }
		nuevaPartidaIntermedio();        
		this.setSize(400, 440);
		botonI = new JButton();
		botonI.setBounds(186, 5, 30, 30);
        botonI.setIcon(ima[2]);                
        this.add(botonI);
        this.botonI.addActionListener(this);
		labelI = new JLabel();
		labelI.setBounds(15, 15, 60, 15);
		this.add(labelI);
	}

	public void nuevaPartidaIntermedio(){        
        casillasFaltantes = 0;        
        ponerBotonesIntermedio();
        verIntermedio(false);
        ponerMinasIntermedio();
        contornoIntermedio();
        visualizarMinasIntermedio();
        eventosIntermedio();
    }
	
	public void ponerBotonesIntermedio(){        
        for(int i=0;i<10;i++){
            imagenes[i] = new ImageIcon(getClass().getResource(archivos[i]));
        }
        
        for(int f = 0; f<minas2; f++){
        for(int c = 0; c<minas2; c++){
            botonesIntermedio[f][c] = new JButton();
            botonesIntermedio[f][c].setBounds(20*f, 20*c+40,20, 20);
            botonesIntermedio[f][c].setBackground(Color.gray);
            //el evento del boton
            this.add(botonesIntermedio[f][c]);
        }
        }        
    }
	
	public void ponerMinasIntermedio(){
        for(int f=0;f<minas2;f++){
        for(int c=0;c<minas2;c++){
            valoresIntermedio[f][c]=0;
        }
        }
           int f1, c1;
        for ( int i=0;i<2*minas2;i++){
            do{
                f1=(int)(Math.random()*minas2);
                c1=(int)(Math.random()*minas2);
            }while(valoresIntermedio[f1][c1]!=0);
            valoresIntermedio[f1][c1]=9;
        }
    }
	
	public void contornoIntermedio(){
        for(int f=0;f<minas2;f++){
            for(int c=0;c<minas2;c++){
                if(valoresIntermedio[f][c]==9){
                    for(int f2=f-1;f2<=f+1;f2++){
                        for(int c2=c-1;c2<=c+1;c2++){
                            if(f2>=0 && f2<minas2 && c2>=0 && c2<minas2 && valoresIntermedio[f2][c2]!=9)
                                valoresIntermedio[f2][c2]++;
                        }
                      }
                   }
                }
            }
        }
	
	public void verIntermedio(boolean valor){
        for(int f=0;f<minas2;f++){
        for(int c=0;c<minas2;c++){
            visibleIntermedio[f][c]=valor;
        }
        }
    }
	
	public void pulsarBotonVasIntermedio(int f, int c){
	        if(f>=0 && f<minas2 && c>=0 && c<minas2 && visibleIntermedio[f][c]==false){
	            if(visibleIntermedio[f][c]==false){
	                visibleIntermedio[f][c]=true;
	                if(valoresIntermedio[f][c]==9){
	                    verIntermedio(true);
	                    JOptionPane.showMessageDialog(null, "              PERDISTE");
	                    botonI.setIcon(ima[1]);}
	            else if(visibleIntermedio[f][c]==true){
	                casillasFaltantes++;
	                if (casillasFaltantes == 360){
	                    verIntermedio(true);
	                    JOptionPane.showMessageDialog(null, "              GANASTE");
	                    botonI.setIcon(ima[0]);
	                    labelI.setText("");
	                }
	                labelI.setText(casillasFaltantes+"/360");
	            }
	            }
	            if(valoresIntermedio[f][c]==0){
	                pulsarBotonVasIntermedio(f, c-1);
	                pulsarBotonVasIntermedio(f, c+1);
	                pulsarBotonVasIntermedio(f-1, c);
	                pulsarBotonVasIntermedio(f+1, c);
	            }
	        }
	    }
	 
	public void pulsarBotonIntermedio(int f, int c) {
                 try {
                Tablero.tirada(f, c);
            } catch (IOException ex) {
                Logger.getLogger(principiante.class.getName()).log(Level.SEVERE, null, ex);
            }
	        pulsarBotonVasIntermedio(f,c);
	        visualizarMinasIntermedio();
	    }
	 
	public void eventosIntermedio(){
	        for(int f=0;f<minas2;f++){
	        for(int c=0;c<minas2;c++){
	            botonesIntermedio[f][c].addActionListener(
	            new ActionListener(){
	                public void actionPerformed(ActionEvent e){
	                    for(int f=0;f<minas2;f++){
	                    for(int c=0;c<minas2;c++){
	                        if(e.getSource()==botonesIntermedio[f][c])
	                            pulsarBotonIntermedio(f,c);
	                    }
	                    }
	                }
	                }
	            );
	        }
	        }
	    }
	 
	public void visualizarMinasIntermedio(){
         for(int f=0;f<minas2;f++){
         for(int c=0;c<minas2;c++){
          if(visibleIntermedio[f][c]==false){
            botonesIntermedio[f][c].setText("");
          }else if(visibleIntermedio[f][c]==true){
            if(valoresIntermedio[f][c]==0){
            botonesIntermedio[f][c].setIcon(imagenes[0]);
            }else if(valoresIntermedio[f][c]==1){
            botonesIntermedio[f][c].setIcon(imagenes[1]);
            }else if(valoresIntermedio[f][c]==2){
            botonesIntermedio[f][c].setIcon(imagenes[2]);
            }else if(valoresIntermedio[f][c]==3){
            botonesIntermedio[f][c].setIcon(imagenes[3]);
            }else if(valoresIntermedio[f][c]==4){
            botonesIntermedio[f][c].setIcon(imagenes[4]);
            }else if(valoresIntermedio[f][c]==5){
            botonesIntermedio[f][c].setIcon(imagenes[5]);
            }else if(valoresIntermedio[f][c]==6){
            botonesIntermedio[f][c].setIcon(imagenes[6]);
            }else if(valoresIntermedio[f][c]==7){
            botonesIntermedio[f][c].setIcon(imagenes[7]);
            }else if(valoresIntermedio[f][c]==8){
            botonesIntermedio[f][c].setIcon(imagenes[8]);
            }else if(valoresIntermedio[f][c]==9)
            botonesIntermedio[f][c].setIcon(imagenes[9]);
           }
         }
        }
    }
	 
	public void quitarBotonesIntermedio(){
         for(int f1 = 0; f1<minas2; f1++){
                for(int c1 = 0; c1<minas2; c1++){
                    this.remove(botonesIntermedio[f1][c1]);
                }
    }
  }

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==botonI){
			botonI.setIcon(ima[2]);
            quitarBotonesIntermedio();
            this.setVisible(false);            
            labelI.setText("");
			nuevaPartidaIntermedio();
			this.setVisible(true);
		}	
	}
}