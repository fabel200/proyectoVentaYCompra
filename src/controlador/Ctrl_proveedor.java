
package controlador;

import conexion.Conexion;
import conexion.DAO_Proveedor;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Proveedor;
import modelo.Validacion;
import vista.*;
import static vista.FrmMenu.jDesktopPane_menu;
import static vista.InterGestionarProveedor.jTable_clientes;

public class Ctrl_proveedor {
    
    InterGestionarProveedor interGestionarProveedor;
    InterProveedor interProveedor;
    private int idProveedor;

    public Ctrl_proveedor(InterGestionarProveedor interGestionarProveedor, InterProveedor interProveedor) {
        this.interGestionarProveedor = interGestionarProveedor;
        this.interProveedor = interProveedor;
        
        this.interGestionarProveedor.jButton_actualizar1.addActionListener(e -> AbrirRegistro());
        this.interGestionarProveedor.jButton_actualizar.addActionListener(e -> Actualizar());
        this.interProveedor.btn_Guardar.addActionListener(e -> Guardar());
        
        this.CargarTablaproveedor();
    }
    
    
    
   public void AbrirRegistro() {
        jDesktopPane_menu.add(interProveedor);
        interProveedor.setVisible(true);
    }
    
    public void Guardar() {
       
        Proveedor proveedor = new Proveedor();
        DAO_Proveedor controlProveedor = new DAO_Proveedor();

        if (!interProveedor.txt_nombre.getText().isEmpty() && !interProveedor.txt_apellido.getText().isEmpty() && !interProveedor.txt_cedula.getText().isEmpty()) {
            //JOptionPane.showMessageDialog(null, "Correcto");

            if (validarFormulario(interProveedor.txt_nombre.getText(),interProveedor.txt_nombre.getText(), interProveedor.txt_cedula.getText().trim(), interProveedor.txt_telefono.getText().trim())) {

            
            if (!controlProveedor.existeProveedor(interProveedor.txt_cedula.getText().trim())) {

                proveedor.setNombre(interProveedor.txt_nombre.getText().trim());
                proveedor.setApellido(interProveedor.txt_apellido.getText().trim());
                proveedor.setCedula(interProveedor.txt_cedula.getText().trim());
                proveedor.setTelefono(interProveedor.txt_telefono.getText().trim());
                proveedor.setDireccion(interProveedor.txt_direccion.getText().trim());
                proveedor.setEstado(1);

                if (controlProveedor.guardar(proveedor)) {
                    JOptionPane.showMessageDialog(null, "Registro Guardado");
                    interProveedor.txt_nombre.setBackground(Color.green);
                    interProveedor.txt_apellido.setBackground(Color.green);
                    interProveedor.txt_cedula.setBackground(Color.green);
                    interProveedor.txt_telefono.setBackground(Color.green);
                    interProveedor.txt_direccion.setBackground(Color.green);
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Guardar");
                }

            } else {
                JOptionPane.showMessageDialog(null, "El cliente ya esta registrado en la Base de Datos.");
                interProveedor.txt_nombre.setBackground(Color.white);
                interProveedor.txt_apellido.setBackground(Color.white);
                interProveedor.txt_cedula.setBackground(Color.white);
                interProveedor.txt_telefono.setBackground(Color.white);
                interProveedor.txt_direccion.setBackground(Color.white);
                this.CargarTablaproveedor();
            }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Completa todos los campos");
            interProveedor.txt_nombre.setBackground(Color.red);
            interProveedor.txt_apellido.setBackground(Color.red);
            interProveedor.txt_cedula.setBackground(Color.red);
            interProveedor.txt_telefono.setBackground(Color.red);
            interProveedor.txt_direccion.setBackground(Color.red);
        }
 
    }
    
    public void Actualizar() {
        
        if (interGestionarProveedor.txt_nombre.getText().isEmpty() && interGestionarProveedor.txt_apellido.getText().isEmpty()
                && interGestionarProveedor.txt_cedula.getText().isEmpty() && interGestionarProveedor.txt_telefono.getText().isEmpty() && interGestionarProveedor.txt_direccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "¡Completa todos los campos!");
        } else {

            Proveedor proveedor = new Proveedor();
            DAO_Proveedor controlProveedor = new DAO_Proveedor();

            proveedor.setNombre(interGestionarProveedor.txt_nombre.getText().trim());
            proveedor.setApellido(interGestionarProveedor.txt_apellido.getText().trim());
            proveedor.setCedula(interGestionarProveedor.txt_cedula.getText().trim());
            proveedor.setTelefono(interGestionarProveedor.txt_telefono.getText().trim());
            proveedor.setDireccion(interGestionarProveedor.txt_direccion.getText().trim());

            if (controlProveedor.actualizar(proveedor, idProveedor)) {
                JOptionPane.showMessageDialog(null, "¡Datos del proveedor actualizados!");
                this.CargarTablaproveedor();
                this.Limpiar2();
            } else {
                JOptionPane.showMessageDialog(null, "¡Error al actualizar!");
            }

        }

    }
    
    public void Eliminar() {
        
        DAO_Proveedor controlProveedor = new DAO_Proveedor();
        if (idProveedor == 0) {
            JOptionPane.showMessageDialog(null, "¡Seleccione un proveedor!");
        } else {
            if (!controlProveedor.eliminar(idProveedor)) {
                JOptionPane.showMessageDialog(null, "¡Proveedot Eliminado!");
                this.CargarTablaproveedor();
                this.Limpiar2();
            } else {
                JOptionPane.showMessageDialog(null, "¡Error al eliminar proveedor!");
                this.Limpiar2();
            }
        }
    }
    
      /**
     *
     * Metodo para limpiar campos
     */
    private void Limpiar() {
        interProveedor.txt_nombre.setText("");
        interProveedor.txt_apellido.setText("");
        interProveedor.txt_cedula.setText("");
        interProveedor.txt_telefono.setText("");
        interProveedor.txt_direccion.setText("");
    }
    
        public static boolean  validarFormulario(String nombre,String apellido,String cedula, String telefono){
        if (!Validacion.validarNombre(nombre)) {
          JOptionPane.showMessageDialog(null, "Nombre invalido solo letras y espacios");
          return false;
        }
        if (!Validacion.validarNombre(apellido)) {
            JOptionPane.showMessageDialog(null, "   Apellido invalido solo letras y espacios");
          return false;
        }
        
        if (!Validacion.validarCedula(cedula)) {
            JOptionPane.showMessageDialog(null, "Telefono invalido ");
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
        interGestionarProveedor.txt_nombre.setText("");
        interGestionarProveedor.txt_telefono.setText("");
        interGestionarProveedor.txt_apellido.setText("");
        interGestionarProveedor.txt_direccion.setText("");
        interGestionarProveedor.txt_cedula.setText("");
    }


    /*
     * *****************************************************
     * metodo para mostrar todos los provedores registrados
     * *****************************************************
     */
    private void CargarTablaproveedor() {
        Connection con = Conexion.conectar();
        DefaultTableModel model = new DefaultTableModel();
        String sql = "select * from tb_proveedor";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            InterGestionarProveedor.jTable_clientes = new JTable(model);
            InterGestionarProveedor.jScrollPane1.setViewportView(InterGestionarProveedor.jTable_clientes);

            model.addColumn("N°");//ID
            model.addColumn("nombre");
            model.addColumn("apellido");
            model.addColumn("cedula");
            model.addColumn("telefono");
            model.addColumn("direccion");
            model.addColumn("estado");

            while (rs.next()) {
                Object fila[] = new Object[7];
                for (int i = 0; i < 7; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                model.addRow(fila);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al llenar la tabla proveedor: " + e);
        }
        //evento para obtener campo al cual el usuario da click
        //y obtener la interfaz que mostrara la informacion general
        jTable_clientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila_point = jTable_clientes.rowAtPoint(e.getPoint());
                int columna_point = 0;

                if (fila_point > -1) {
                    idProveedor = (int) model.getValueAt(fila_point, columna_point);
                    EnviarDatosProveedorSeleccionado(idProveedor);//metodo
                }
            }
        });
    }


    /*
     * **************************************************
     * Metodo que envia datos seleccionados
     * **************************************************
     */
    private void EnviarDatosProveedorSeleccionado(int idCliente) {
        try {
            Connection con = Conexion.conectar();
            PreparedStatement pst = con.prepareStatement(
                    "select * from tb_proveedor where idProveedor = '" + idCliente + "'");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                interGestionarProveedor.txt_nombre.setText(rs.getString("nombre"));
                interGestionarProveedor.txt_apellido.setText(rs.getString("apellido"));
                interGestionarProveedor.txt_cedula.setText(rs.getString("cedula"));
                interGestionarProveedor.txt_telefono.setText(rs.getString("telefono"));
                interGestionarProveedor.txt_direccion.setText(rs.getString("direccion"));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al seleccionar proveedor: " + e);
        }
    }

}
