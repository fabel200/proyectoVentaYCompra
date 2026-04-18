/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Marialyh
 */
public class Validacion {
    
      public static boolean  validarNombre(String nombre){
        Pattern pattern = Pattern.compile("^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$");
        Matcher matcher = pattern.matcher(nombre);
        return matcher.matches();  
    }
    
    public static boolean  validarUsuario(String usuario){
        Pattern pattern = Pattern.compile("^[A-Za-z0-9_]{4,}$");
        Matcher matcher = pattern.matcher(usuario);
        return matcher.matches();  
    }
    
    public static boolean  validarContraseña(String contraseña){
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[A-Za-z]).{6,15}$");
        Matcher matcher = pattern.matcher(contraseña);
        return matcher.matches();  
    }
    
    public static boolean  validarTelefono(String telefono){
        Pattern pattern = Pattern.compile("^[0-9]{7,15}$");
        Matcher matcher = pattern.matcher(telefono);
        return matcher.matches();  
    }
    
        public static boolean  validarCedula(String telefono){
        Pattern pattern = Pattern.compile("^[0-9]{7,8}$");
        Matcher matcher = pattern.matcher(telefono);
        return matcher.matches();  
    }
}
