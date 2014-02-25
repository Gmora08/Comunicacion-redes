/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Gustavo
 */
public class Objeto{
    
    private String nombre;
    private String path;
    private long tamano; 
    
    public Objeto(String nombre, String path, long tamano)
    {
        this.nombre = nombre;
        this.path = path;
        this.tamano = tamano;
    }

    public String getNombre() {
        return nombre;
    }

    public long getTamano() {
        return tamano;
    }

    public String getPath() {
        return path;
    }
    
    
     
    
    
    
}
