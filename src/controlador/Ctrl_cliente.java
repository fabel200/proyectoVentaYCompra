package controlador;

import conexion.Conexion;
import conexion.DAO_Cliente;
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
import modelo.Cliente;
import modelo.Validacion;
import vista.*;
import static vista.FrmMenu.jDesktopPane_menu;
import static vista.InterGestionarCliente.jTable_clientes;

public class Ctrl_cliente {
private int idCliente;
    InterGestionarCliente interGestionarCliente;
    InterCliente interCliente;

    public Ctrl_cliente(InterGestionarCliente interGestionarCliente, InterCliente interCliente) {
        this.interGestionarCliente = interGestionarCliente;
        this.interCliente = interCliente;
        
         this.interGestionarCliente.jButton_registrar.addActionListener(e -> AbrirRegistro());
        this.interGestionarCliente.jButton_actualizar.addActionListener(e -> Actualizar());
        this.interCliente.jButton_Guardar.addActionListener(e -> Guardar());
        
        this.CargarTablaClientes();
    }

    /**
     * *********************************************************************************
     *
     * Metodos para los Botones
     *
     * ************************************************************************************
     */
    public void AbrirRegistro() {
        jDesktopPane_menu.add(interCliente);
        interCliente.setVisible(true);
    }

    public void Guardar() {
        Cliente cliente = new Cliente();
        DAO_Cliente controlCliente = new DAO_Cliente();

        if (!interCliente.txt_nombre.getText().isEmpty() && !interCliente.txt_apellido.getText().isEmpty() && !interCliente.txt_cedula.getText().isEmpty()) {
            //JOptionPane.showMessageDialog(null, "Correcto");

            if (validarFormulario(interCliente.txt_nombre.getText(), interCliente.txt_nombre.getText(), interCliente.txt_cedula.getText().trim(), interCliente.txt_telefono.getText().trim())) {

                if (!controlCliente.existeCliente(interCliente.txt_cedula.getText().trim())) {

                    cliente.setNombre(interCliente.txt_nombre.getText().trim());
                    cliente.setApellido(interCliente.txt_apellido.getText().trim());
                    cliente.setCedula(interCliente.txt_cedula.getText().trim());
                    cliente.setTelefono(interCliente.txt_telefono.getText().trim());
                    cliente.setDireccion(interCliente.txt_direccion.getText().trim());
                    cliente.setEstado(1);

                    if (controlCliente.guardar(cliente)) {
                        JOptionPane.showMessageDialog(null, "Registro Guardado");
                        interCliente.txt_nombre.setBackground(Color.green);
                        interCliente.txt_apellido.setBackground(Color.green);
                        interCliente.txt_cedula.setBackground(Color.green);
                        interCliente.txt_telefono.setBackground(Color.green);
                        interCliente.txt_direccion.setBackground(Color.green);

                        this.CargarTablaClientes();
                        this.interCliente.dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al Guardar");
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "El cliente ya esta registrado en la Base de Datos.");
                    interCliente.txt_nombre.setBackground(Color.white);
                    interCliente.txt_apellido.setBackground(Color.white);
                    interCliente.txt_cedula.setBackground(Color.white);
                    interCliente.txt_telefono.setBackground(Color.white);
                    interCliente.txt_direccion.setBackground(Color.white);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Completa todos los campos");
            interCliente.txt_nombre.setBackground(Color.red);
            interCliente.txt_apellido.setBackground(Color.red);
            interCliente.txt_cedula.setBackground(Color.red);
            interCliente.txt_telefono.setBackground(Color.red);
            interCliente.txt_direccion.setBackground(Color.red);
        }
        //metodo limpiar
        this.Limpiar();

    }

    public void Actualizar() {

        if (interGestionarCliente.txt_nombre.getText().isEmpty() && interGestionarCliente.txt_apellido.getText().isEmpty()
                && interGestionarCliente.txt_cedula.getText().isEmpty() && interGestionarCliente.txt_telefono.getText().isEmpty() && interGestionarCliente.txt_direccion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "¡Completa todos los campos!");
        } else {

            Cliente cliente = new Cliente();
            DAO_Cliente controlCliente = new DAO_Cliente();

            cliente.setNombre(interGestionarCliente.txt_nombre.getText().trim());
            cliente.setApellido(interGestionarCliente.txt_apellido.getText().trim());
            cliente.setCedula(interGestionarCliente.txt_cedula.getText().trim());
            cliente.setTelefono(interGestionarCliente.txt_telefono.getText().trim());
            cliente.setDireccion(interGestionarCliente.txt_direccion.getText().trim());

            if (controlCliente.actualizar(cliente, idCliente)) {
                JOptionPane.showMessageDialog(null, "¡Datos del cliente actualizados!");
                this.CargarTablaClientes();
                this.Limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "¡Error al actualizar!");
            }

        }

    }

    public void Eliminar() {

        DAO_Cliente controlCliente = new DAO_Cliente();
        if (idCliente == 0) {
            JOptionPane.showMessageDialog(null, "¡Seleccione un cliente!");
        } else {
            if (!controlCliente.eliminar(idCliente)) {
                JOptionPane.showMessageDialog(null, "¡Cliente Eliminado!");
                this.CargarTablaClientes();
                this.Limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "¡Error al eliminar cliente!");
                this.Limpiar();
            }
        }

    }

    /**
     *
     * Metodo para limpiar campos
     */
    private void Limpiar() {
        interCliente.txt_nombre.setText("");
        interCliente.txt_apellido.setText("");
        interCliente.txt_cedula.setText("");
        interCliente.txt_telefono.setText("");
        interCliente.txt_direccion.setText("");
    }

    public static boolean validarFormulario(String nombre, String apellido, String cedula, String telefono) {
        if (!Validacion.validarNombre(nombre)) {
            JOptionPane.showMessageDialog(null, "Nombre invalido solo letras y espacios");
            return false;
        }
        if (!Validacion.validarNombre(apellido)) {
            JOptionPane.showMessageDialog(null, "   Apellido invalido solo letras y espacios");
            return false;
        }

        if (!Validacion.validarCedula(cedula)) {
            JOptionPane.showMessageDialog(null, "Cedula invalido ");
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
        interGestionarCliente.txt_nombre.setText("");
        interGestionarCliente.txt_telefono.setText("");
        interGestionarCliente.txt_apellido.setText("");
        interGestionarCliente.txt_direccion.setText("");
        interGestionarCliente.txt_cedula.setText("");
    }


    /*
     * *****************************************************
     * metodo para mostrar todos los clientes registrados
     * *****************************************************
     */
    public void CargarTablaClientes() {
        Connection con = Conexion.conectar();
        DefaultTableModel model = new DefaultTableModel();
        String sql = "select * from tb_cliente";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            InterGestionarCliente.jTable_clientes = new JTable(model);
            InterGestionarCliente.jScrollPane1.setViewportView(InterGestionarCliente.jTable_clientes);

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
            System.out.println("Error al llenar la tabla clientes: " + e);
        }
        //evento para obtener campo al cual el usuario da click
        //y obtener la interfaz que mostrara la informacion general
        jTable_clientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila_point = jTable_clientes.rowAtPoint(e.getPoint());
                int columna_point = 0;

                if (fila_point > -1) {
                    idCliente = (int) model.getValueAt(fila_point, columna_point);
                    EnviarDatosClienteSeleccionado(idCliente);//metodo
                }
            }
        });
    }


    /*
     * **************************************************
     * Metodo que envia datos seleccionados
     * **************************************************
     */
    private void EnviarDatosClienteSeleccionado(int idCliente) {
        try {
            Connection con = Conexion.conectar();
            PreparedStatement pst = con.prepareStatement(
                    "select * from tb_cliente where idCliente = '" + idCliente + "'");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                interGestionarCliente.txt_nombre.setText(rs.getString("nombre"));
                interGestionarCliente.txt_apellido.setText(rs.getString("apellido"));
                interGestionarCliente.txt_cedula.setText(rs.getString("cedula"));
                interGestionarCliente.txt_telefono.setText(rs.getString("telefono"));
                interGestionarCliente.txt_direccion.setText(rs.getString("direccion"));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al seleccionar cliente: " + e);
        }
    }

}
