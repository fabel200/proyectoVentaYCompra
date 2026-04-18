package controlador;

import conexion.Conexion;
import conexion.DAO_Usuario;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.Usuario;
import vista.*;

public class Ctrl_login implements KeyListener{
    
FrmLogin lo;

    public Ctrl_login(FrmLogin lo) {
        this.lo = lo;
        this.lo.jButton_IniciarSesion.addActionListener(e -> Login());
    this.lo.txt_usuario.addKeyListener(this);
    this.lo.txt_password.addKeyListener(this);
     //   this.getIconImage();
    }
    
    /***************************************************************+
     * 
     *  Metodo para iniciar Login
     * 
     ****************************************************************/
    public void iniciar() {
        lo.setVisible(true);
    }

    /****************************************************************
     * metodo Iniciar Sesion
     ***************************************************************/
    private void Login() {
        if (!lo.txt_usuario.getText().isEmpty() && !lo.txt_password.getText().isEmpty()) {
            DAO_Usuario controlUsuario = new DAO_Usuario();
            Usuario usuario = new Usuario();
            usuario.setUsuario(lo.txt_usuario.getText().trim());
            usuario.setPassword(lo.txt_password.getText().trim());
          
          
            
            if (controlUsuario.loginUser(usuario)) {
                //JOptionPane.showMessageDialog(null, "Login Correcto...");
                FrmMenu menu = new FrmMenu();
                Ctrl_menu con = new Ctrl_menu(menu);
                DatosUsuario(usuario);
                
                if (usuario.getEstado() == 1) {
                   if (usuario.getRol().equalsIgnoreCase("Administrador")) {
                    //JOptionPane.showMessageDialog(this,"Su rol es"+ rol);
                    
                    menu.jMenu1.setEnabled(true);
                  menu.jMenu2.setEnabled(true);
                  menu.jMenu3.setEnabled(true);
                  menu.jMenu4.setEnabled(true);
                }else if (usuario.getRol().equalsIgnoreCase("Encargado")) {
                  //  JOptionPane.showMessageDialog(this, "Su rol es"+ rol);
                  
                  menu.jMenu1.setEnabled(false);
                  
                
                }else if (usuario.getRol().equalsIgnoreCase("Vendedor")) {
                  //  JOptionPane.showMessageDialog(this, "Su rol es"+ rol);
                  
                  menu.jMenu1.setEnabled(false);
                  menu.jMenu2.setEnabled(false);
                  menu.jMenu6.setEnabled(false);
                  menu.jMenu4.setEnabled(false);
                  menu.jMenu7.setEnabled(false);
                  menu.jMenuItem_reportes_clientes.setEnabled(false);
                  
                
                }  else {
                    JOptionPane.showMessageDialog(this.lo, "ROL NO ENCONTRADO");
                } 
                   
                   
                      menu.setVisible(true);
                this.lo.dispose();
                
                
                } else {
                    JOptionPane.showMessageDialog(this.lo, "USUARIO DESACTIVADO");
                }
                
          
             //   menu.jMenu3.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o Clave Incorrectos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese sus credenciales");
        }
    }
       
 /*
     * **************************************************
     * Metodo que busca el rol del usurio
    
     * **************************************************
     */
    private void DatosUsuario(Usuario usu) {
        try {
            Connection con = Conexion.conectar();
            PreparedStatement pst = con.prepareStatement(
                    "select * from tb_usuario where usuario = '" + usu.getUsuario() + "'");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
           //     rol = rs.getString("rol");
                usu.setRol(rs.getString("rol"));
                usu.setEstado(rs.getInt("estado"));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al seleccionar usuario: " + e);
        }
    }
    
    
    
    
  //  @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("img/ventas.png"));
        return retValue;
    }

    @Override
    public void keyTyped(KeyEvent e) {
   }

    @Override
    public void keyPressed(KeyEvent e) {
                                         
        // ACCION PARA QUE AL DAR ENTER CAMBIE DE CAMPO DE INGRESO DE DATOS
        if (e.getSource() == lo.txt_usuario && e.getKeyCode() == KeyEvent.VK_ENTER) {
            lo.txt_password.requestFocus();
        }                                       
                                     
        // ACCION PARA QUE AL DAR ENTER INICIE SESION
        if (e.getSource() == lo.txt_password && e.getKeyCode() == KeyEvent.VK_ENTER) {
            this.Login();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
