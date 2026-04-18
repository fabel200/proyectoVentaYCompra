package controlador;

import conexion.Conexion;
import conexion.DAO_Usuario;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import modelo.Usuario;
import modelo.Validacion;
import vista.*;
import static vista.FrmMenu.jDesktopPane_menu;
import static vista.InterGestionarUsuario.jTable_usuarios;
//import static vista.InterUsuario.validarFormulario;

public class Ctrl_usuario implements ActionListener, MouseListener {

    InterGestionarUsuario interGestionarUsuario;
    InterUsuario interUsuario;
    Validacion val;
    int idUsuario = 0;

    public Ctrl_usuario(InterGestionarUsuario interGestionarUsuario, InterUsuario interUsuario) {
        this.interGestionarUsuario = interGestionarUsuario;
        this.interUsuario = interUsuario;
        this.interUsuario.jCheckBox_ver_clave.addMouseListener(this);

        this.interGestionarUsuario.jButton_Registrar.addActionListener(e -> AbrirRegistro());
        this.interGestionarUsuario.jButton_actualizar.addActionListener(e -> Actualizar());
        this.interUsuario.jButton_Guardar.addActionListener(e -> Guardar());
   
        this.CargarTablaUsuarios();
        this.configurarColumnaContraseña();

    }

    /**
     * ****************************************************+
     *
     * Metodos para los Botones
     *
     ******************************************************
     */
    public void AbrirRegistro() {
        jDesktopPane_menu.add(interUsuario);
        interUsuario.setVisible(true);
    }

    public void Guardar() {
        Usuario usuario = new Usuario();
        DAO_Usuario controlUsuario = new DAO_Usuario();

        String rol = "";
        rol = interUsuario.jComboBox_Roles.getSelectedItem().toString().trim();

        if (interUsuario.txt_nombre.getText().isEmpty() || interUsuario.txt_apellido.getText().isEmpty() || interUsuario.txt_usuario.getText().isEmpty()
                || interUsuario.txt_password.getText().isEmpty() || interUsuario.txt_telefono.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Completa todos los campos");
        } else {

            if (rol.equalsIgnoreCase("Seleccione un rol:")) {
                JOptionPane.showMessageDialog(null, "Seleccione un rol.");
            } else {
                //enviamos datos del usuario
                usuario.setNombre(interUsuario.txt_nombre.getText().trim());
                usuario.setApellido(interUsuario.txt_apellido.getText().trim());
                usuario.setUsuario(interUsuario.txt_usuario.getText().trim());
                usuario.setPassword(interUsuario.txt_password.getText().trim());
                usuario.setTelefono(interUsuario.txt_telefono.getText().trim());
                usuario.setRol(rol);
                usuario.setEstado(1);

                if (validarFormulario(usuario.getNombre(), usuario.getApellido(), usuario.getUsuario(), usuario.getPassword(), usuario.getTelefono())) {

                    //validamos si el usuaro ya esta registrado
                    if (!controlUsuario.existeUsuario(interUsuario.txt_usuario.getText().trim())) {

                        if (controlUsuario.guardar(usuario)) {
                            JOptionPane.showMessageDialog(null, "¡Usuario Registrado!");
                            this.Limpiar();
                            this.CargarTablaUsuarios();

                        } else {
                            JOptionPane.showMessageDialog(null, "¡Error al registrar Usuario!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "El Usuario ya esta registrado, ingrese otro.");
                    }

                    return;
                }
            }
        }

    }

    public void Actualizar() {
        Usuario usuario = new Usuario();
        DAO_Usuario controlUsuario = new DAO_Usuario();
         String rol;
        rol  = interGestionarUsuario.jComboBox_Roles.getSelectedItem().toString().trim();

        if (idUsuario == 0) {
            JOptionPane.showMessageDialog(null, "¡Seleccione un Usuario!");
        } else {
            //Actualizar datos
        if (rol.equalsIgnoreCase("Seleccione un rol:")) {
            
              JOptionPane.showMessageDialog(null, "Seleccione un registro para actualizar datos");
              return;
        }
        
            if (interGestionarUsuario.txt_nombre.getText().isEmpty() || interGestionarUsuario.txt_apellido.getText().isEmpty() || interGestionarUsuario.txt_usuario.getText().isEmpty()
                    || interGestionarUsuario.txt_password.getText().isEmpty() || interGestionarUsuario.txt_telefono.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "¡Completa todos los campos!");

            } else {
                usuario.setNombre(interGestionarUsuario.txt_nombre.getText().trim());
                usuario.setApellido(interGestionarUsuario.txt_apellido.getText().trim());
                usuario.setUsuario(interGestionarUsuario.txt_usuario.getText().trim());
                usuario.setPassword(interGestionarUsuario.txt_password.getText().trim());
                usuario.setTelefono(interGestionarUsuario.txt_telefono.getText().trim());
                usuario.setRol(rol);
                usuario.setEstado(1);

                if (controlUsuario.actualizar(usuario, idUsuario)) {
                    JOptionPane.showMessageDialog(null, "¡Actualizacion Exitosa!");
                    this.Limpiar();
                    this.CargarTablaUsuarios();
                    idUsuario = 0;

                } else {
                    JOptionPane.showMessageDialog(null, "¡Error al Actualizar usuario!");
                }
            }
            
        }
    }

    public void Eliminar() {
        DAO_Usuario controlUsuario = new DAO_Usuario();
        if (idUsuario == 0) {
            JOptionPane.showMessageDialog(null, "¡Seleccione un usuario!");
        } else {
            if (!controlUsuario.eliminar(idUsuario)) {
                JOptionPane.showMessageDialog(null, "¡Usuario Eliminado!");
                this.CargarTablaUsuarios();
                this.Limpiar();
                idUsuario = 0;
            } else {
                JOptionPane.showMessageDialog(null, "¡Error al eliminar usuario!");
                this.Limpiar();
            }
        }
    }

    /**
     * *************************************************************
     *
     * Metodo para Limpiar
     *
     *********************************************************
     */
    private void Limpiar() {
        interUsuario.txt_nombre.setText("");
        interUsuario.txt_apellido.setText("");
        interUsuario.txt_password.setText("");
        interUsuario.txt_usuario.setText("");
        interUsuario.txt_telefono.setText("");
    }

    /**
     * ***********************************************************
     *
     * Metodo para Validar el Usuario
     * ************************************************************
     */
    public static boolean validarFormulario(String nombre, String apellido, String usuario, String contraseña, String telefono) {
        if (!Validacion.validarNombre(nombre)) {
            JOptionPane.showMessageDialog(null, "Nombre invalido solo letras y espacios");
            return false;
        }
        if (!Validacion.validarNombre(apellido)) {
            JOptionPane.showMessageDialog(null, "   Apellido invalido solo letras y espacios");
            return false;
        }
        if (!Validacion.validarUsuario(usuario)) {
            JOptionPane.showMessageDialog(null, "Usuario invalido debe tener minimo 4 caracteres");
            return false;
        }
        if (!Validacion.validarContraseña(contraseña)) {
            JOptionPane.showMessageDialog(null, "contraseña insegura, debe tener letras, numeros y almenos 8 caracteres");
            return false;
        }
        if (!Validacion.validarTelefono(telefono)) {
            JOptionPane.showMessageDialog(null, "Telefono invalido ");
            return false;
        }
        return true;
    }

    /*
     * *****************************************************
     * metodo para limpiar
     * *****************************************************
     */
    private void Limpiar2() {
        interGestionarUsuario.txt_nombre.setText("");
        interGestionarUsuario.txt_password.setText("");
        interGestionarUsuario.txt_apellido.setText("");
        interGestionarUsuario.txt_telefono.setText("");
        interGestionarUsuario.txt_usuario.setText("");
    }


    /*
     * *****************************************************
     * metodo para mostrar todos los clientes registrados
     * *****************************************************
     */
    private void CargarTablaUsuarios() {
        Connection con = Conexion.conectar();
        DefaultTableModel model = new DefaultTableModel();
        String sql = "select * from tb_usuario";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            InterGestionarUsuario.jTable_usuarios = new JTable(model);
            InterGestionarUsuario.jScrollPane1.setViewportView(InterGestionarUsuario.jTable_usuarios);

            model.addColumn("N°");//ID
            model.addColumn("nombre");
            model.addColumn("apellido");
            model.addColumn("usuario");
            model.addColumn("password");
            model.addColumn("telefono");
            model.addColumn("rol");
            model.addColumn("estado");

            while (rs.next()) {
                Object fila[] = new Object[8];
                for (int i = 0; i < 8; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                model.addRow(fila);
            }

            con.close();
        } catch (SQLException e) {
            System.out.println("Error al llenar la tabla usuarios: " + e);
        }

        //evento para obtener campo al cual el usuario da click
        //y obtener la interfaz que mostrara la informacion general
        jTable_usuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila_point = jTable_usuarios.rowAtPoint(e.getPoint());
                int columna_point = 0;

                if (fila_point > -1) {
                    idUsuario = (int) model.getValueAt(fila_point, columna_point);
                    EnviarDatosUsuarioSeleccionado(idUsuario);//metodo
                }
            }
        });
        this.configurarColumnaContraseña();
    }

    private void configurarColumnaContraseña() {
        TableColumn columnaContraseña = jTable_usuarios.getColumnModel().getColumn(4);
        columnaContraseña.setCellEditor(new DefaultCellEditor(new JPasswordField()));
        columnaContraseña.setCellRenderer(new ClaveRender());
    }

   
    
     class ClaveRender extends JPasswordField implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((String) value);
            setBorder(null);
            return this;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == interUsuario.jCheckBox_ver_clave) {
          mostraClaveOculta();
 
        }
 }
   private void mostraClaveOculta() {
        if (interUsuario.jCheckBox_ver_clave.isSelected()) {
                char[] passwordIngresado = interUsuario.txt_password.getPassword();
                String pass = new String(passwordIngresado);
                
                interUsuario.txt_password_visible.setText(pass);
                interUsuario.txt_password_visible.setVisible(true);
                interUsuario.txt_password.setVisible(false);
            } else {
                String passwordIngresado = interUsuario.txt_password_visible.getText().trim();
                interUsuario.txt_password.setText(passwordIngresado);
                interUsuario.txt_password_visible.setVisible(false);
                interUsuario.txt_password.setVisible(true);
            } }  
    
    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    /*
     * **************************************************
     * Metodo que envia datos seleccionados
     * **************************************************
     */
    private void EnviarDatosUsuarioSeleccionado(int idUsuario) {
        try {
            Connection con = Conexion.conectar();
            PreparedStatement pst = con.prepareStatement(
                    "select * from tb_usuario where idUsuario = '" + idUsuario + "'");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                interGestionarUsuario.txt_nombre.setText(rs.getString("nombre"));
                interGestionarUsuario.txt_apellido.setText(rs.getString("apellido"));
                interGestionarUsuario.txt_usuario.setText(rs.getString("usuario"));
                interGestionarUsuario.txt_password.setText(rs.getString("password"));
                interGestionarUsuario.txt_telefono.setText(rs.getString("telefono"));
                interGestionarUsuario.jComboBox_Roles.setSelectedItem(rs.getString("rol"));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al seleccionar usuario: " + e);
        }
    }

}
