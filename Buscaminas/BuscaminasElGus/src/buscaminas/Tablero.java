package buscaminas;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.Rectangle;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Insets;
 
public class Tablero extends JFrame {
  private JPanel jPanel1 = new JPanel();
  private JButton jButton1 = new JButton();
  private int ancho=19;
  private int alto=12;
  public  JButton Botones[][]=new JButton [ancho][alto];
  public  String [][] elArray =new String [ancho][alto];
 
  //—- Dependiendo del ancho también asignaré el número de bombas 
 
public static void main (String [] args){
   Tablero TabBuscaMin = new Tablero();
}
public Tablero()  {
    try    {
      jbInit();
    }
    catch(Exception e)    {
      e.printStackTrace();
    }
  }
 
  private void jbInit() throws Exception  {
    this.getContentPane().setLayout(null);
    this.setSize(new Dimension(483, 380));
    this.setTitle("Busca Minas");
    jPanel1.setBounds(new Rectangle(0, 40, 483, 380));
    jPanel1.setBackground(new Color(162, 175, 227));
    jPanel1.setLayout(null);
    jButton1.setText("START");
    jButton1.setBounds(new Rectangle(0, 0, 125, 40));
    jButton1.setFont(new Font("Tahoma", 0, 12));
    jButton1.setHorizontalTextPosition(SwingConstants.CENTER);
    jButton1.setAlignmentY((float)0.0);
    jButton1.setMargin(new Insets(2, 14, 2, 12));
    jButton1.addActionListener(new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        {
          jButton1_actionPerformed(e);
        }
      });
    this.getContentPane().add(jButton1, null);
    this.getContentPane().add(jPanel1, null);
    cargarTablero();
    colocarBomba(getAncho());
    comprueba();
 
    this.setVisible(true);  
 
  //—- Oculta los botones del contorno que no participaran en el juego.
 
    ocultaBotones();
  }
  private void jButton1_actionPerformed(ActionEvent e)  {
   for (int i=0;i<ancho;i++){
       for (int z=0;z<alto;z++){
        elArray[i][z]=" ";
        Botones[i][z].setEnabled(true);
        Botones[i][z].setText(" ");
        }
   }
   colocarBomba(getAncho());
   comprueba();
   this.setTitle("Busca Minas");
   jButton1.setText("START");
  }
  //—– Inicializa el tablero a 0
 
  public void cargarTablero(){
     for (int i=0;i<ancho;i++){
      for (int z=0;z<alto;z++){
      elArray[i][z]=" ";
         Botones[i][z]= new JButton();
             jPanel1.add(Botones[i][z]);
             Botones[i][z].setBounds(i*25,z*25,25,25);
             Botones[i][z].setMargin(new Insets(0, 0, 0, 0));
             Botones[i][z].setFont(new Font("Tahoma", 0,10));
 
  //—– Agrego un ActionListener a cada boton del Array de Botones
  //—– Ahora puede escuchar eventos.
 
             Botones[i][z].addActionListener(
               new ActionListener(){
      public void actionPerformed(ActionEvent ar) {
       for (int i=0;i<ancho;i++){
             for (int z=0;z<alto;z++){
             if (ar.getSource()==Botones[i][z]){
                  showTextTop(i,z);
  //—– Cada Evento llama al método pulsarBoton.
             }                }        }            }               }             );             
 
             }               }       }
  //—- Coloca el numero de bombas dependiendo el ancho.
 
  public void colocarBomba(int numeroBombas){
   double i=0;
   double z=0;
   int condicion=0;
  do  {
     i=Math.random()*getAncho();
        z=Math.random()*getAlto();
           i=(int)i;
           z=(int)z;
           if  (z!=0 && i!=0 && z!=alto-1 && i!=ancho-1){
             // Botones[(int) i][(int) z].setText("B");
              elArray[(int)i][(int) z ]="B";
              condicion++;
           }
       }
  while (condicion<=ancho);
  }
  public void setAncho (int sAncho){
  ancho=sAncho;
  }
  public int getAncho (){
  return ancho;
  }
  public void setAlto (int sAlto){
  alto=sAlto;
  }
  public int getAlto (){
  return alto;
  }
 
 //—- Asigna un número a cada boton dependiendo de las B que tenga al lado.
//--- codigo Busca Minas
  public void comprueba(){
   for (int i=0;i<ancho;i++){
       for (int z=0;z<alto;z++){
       int numeroComprueba=0;   //el numero que voy a asignar al boton
       if (elArray[i][z]!=("B")){
        if  (z!=0 && i!=0 && z!=alto-1 && i!=ancho-1){
           System.out.println(i+ " "+ z +" "+ ancho +" " +alto);
 
           if (elArray[i][z-1]=="B"){
                  numeroComprueba++;
                 }
                 if (elArray[i-1][z-1]=="B"){
                  numeroComprueba++;
                 }
                 if (elArray[i+1][z-1]=="B"){
                  numeroComprueba++;
                 }
                 if (elArray[i][z+1]=="B"){
                  numeroComprueba++;
                 }
                 if (elArray[i+1][z+1]=="B"){
                  numeroComprueba++;
                 }
                 if (elArray[i-1][z+1]=="B"){
                  numeroComprueba++;
                 }
                 if (elArray[i+1][z]=="B"){
                  numeroComprueba++;
                 }
                 if (elArray[i-1][z]=="B"){
                  numeroComprueba++;
                 }
                 if (numeroComprueba!=0){
                 elArray[i][z]=(String.valueOf(numeroComprueba));
              }          }         }                }     }
  }
  //—- Oculta los botones de los laterales.
  public void ocultaBotones(){
   for (int i=0;i<ancho;i++){
       for (int z=0;z<alto;z++){
   if  (z==0 || i==0 || z==alto-1 || i==ancho-1){
      Botones[i][z].setVisible(false);
   }       }    }   
 
}
 
  //—- Muestra el texto en todos los botones cuando hay bomba.
 
  public void textoBotones(){
  for (int i=0;i<ancho;i++){
      for (int z=0;z<alto;z++){
       Botones[i][z].setText(elArray[i][z]);
       Botones[i][z].setEnabled(false);
  //codigo para mostrar el texto encima del boton.
  }   }   
 
}
  //—- Muestra el Numero encima del boton.
  //—- Cambia las propiedades del boton mostrado.
 
  public void showTextTop(int i,int z){
    Botones[i][z].setText(elArray[i][z]);
    Botones[i][z].setEnabled(false);
 
    if (elArray[i][z]==" "){
    Botones[i][z].setEnabled(false);
    metodoStackOverFlow(i,z);
    }
    else {
     Botones[i][z].setEnabled(false);
    }
    if (Botones[i][z].getText()=="B"){
         textoBotones();
         }
 
 }
  //—-  Pone el numero en los botones cercanos.
 
private void metodoStackOverFlow(int i, int z) {
    if ( z!=0 && i!=0 && z!=alto-1 && i!=ancho-1){
    Botones[i-1][z].setEnabled(false);
    Botones[i-1][z-1].setEnabled(false);
    Botones[i-1][z+1].setEnabled(false);
    Botones[i][z-1].setEnabled(false);
    Botones[i][z+1].setEnabled(false);
    Botones[i+1][z].setEnabled(false);
    Botones[i+1][z+1].setEnabled(false);
    Botones[i+1][z-1].setEnabled(false);
 
    Botones[i-1][z].setText(elArray[i-1][z]);
    Botones[i-1][z-1].setText(elArray[i-1][z-1]);
    Botones[i-1][z+1].setText(elArray[i-1][z+1]);
    Botones[i][z-1].setText(elArray[i][z-1]);
    Botones[i][z+1].setText(elArray[i][z+1]);
    Botones[i+1][z].setText(elArray[i+1][z]);
    Botones[i+1][z+1].setText(elArray[i+1][z+1]);
    Botones[i+1][z-1].setText(elArray[i+1][z-1]);
    }
}
 
  //—- Este es el metodo que mostrara el final del game.
  //—- 17 columnas por 10 lineas visibles= 170 Botones[][]
  //—- 170 - 19 Bombas = 171 Botones con Texto
//--- codigo Busca Minas
  public void finalGame (){
   int contadorFinal=0;
    for (int i=1;i<ancho-1;i++){
       for (int z=1;z<alto-1;z++){
       if (Botones[i][z].getText()==" " || Botones[i][z].getText()=="1" ||
        Botones[i][z].getText()=="2" || Botones[i][z].getText()=="3" ||
        Botones[i][z].getText()=="5" || Botones[i][z].getText()=="4" ){
            contadorFinal++;
                 if (contadorFinal==171){
                  this.setTitle("Has Ganado… oooooo…..");
                  jButton1.setText("NEW…");
                 }        }   }  }     }
 
}