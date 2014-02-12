
import java.io.File;
import javax.swing.JFileChooser;




public class Seleccion {
    
    public static void main(String[] args){
      int i;
      JFileChooser fileChooser = new JFileChooser(); 
      fileChooser.setMultiSelectionEnabled(true);
      int resultado = fileChooser.showOpenDialog(null);
      if(resultado==JFileChooser.APPROVE_OPTION){
          //File f = fileChooser.getSelectedFile();
          File[] f = fileChooser.getSelectedFiles();
          for(i=0; i < f.length; i++)
          {
              String nombre = f[i].getName();
              String ruta = f[i].getAbsolutePath();
              System.out.println("Nombre archivo:" + nombre + " ruta: " +ruta +" Tamaño archivo" + f[i].length() );
          }
          
            //System.out.println("Archivo seleccionado: "+f.getAbsolutePath());
      }
    }//main

}
