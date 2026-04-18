package controlador;

import conexion.Conexion;
import conexion.DAO_Categoria;
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
import modelo.Categoria;
import vista.*;
import static vista.FrmMenu.jDesktopPane_menu;
import static vista.InterGestionarCategoria.jTable_categorias;

public class Ctrl_categoria {

    InterCategoria interCategoria;
    InterGestionarCategoria interGestionarCategoria;
    private int idCategoria;

    public Ctrl_categoria(InterCategoria interCategoria, InterGestionarCategoria interGestionarCategoria) {
        this.interCategoria = interCategoria;
        this.interGestionarCategoria = interGestionarCategoria;

        this.interGestionarCategoria.jButton_Registrar.addActionListener(e -> AbrirRegistro());
        this.interGestionarCategoria.jButton_actualizar.addActionListener(e -> Actualizar());
        this.interCategoria.jButton_Guardar.addActionListener(e -> Guardar());
        
        this.CargarTablaCategorias();
    }

    public void AbrirRegistro() {
       
        jDesktopPane_menu.add(interCategoria);
        interCategoria.setVisible(true);
    }

    public void Guardar() {
        Categoria categoria = new Categoria();
        DAO_Categoria controlCategoria = new DAO_Categoria();

        //validamos camoos vacios
        if (interCategoria.txt_descripcion.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Complete  todos los campos");
        } else {
            if (!controlCategoria.existeCategoria(interCategoria.txt_descripcion.getText().trim())) {
                categoria.setDescripcion(interCategoria.txt_descripcion.getText().trim());
                categoria.setEstado(1);
                if (controlCategoria.guardar(categoria)) {
                    JOptionPane.showMessageDialog(null, "Registro Guardado");
                    this.interCategoria.dispose();
                    this.CargarTablaCategorias();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al Guardar");
                }
            } else {
                JOptionPane.showMessageDialog(null, "La Categoria ya esta registrada en la Base de Datos");
            }
        }
        //limpiar campo
        interCategoria.txt_descripcion.setText("");
    }

    public void Actualizar() {
        if (!interGestionarCategoria.txt_descripcion.getText().isEmpty()) {
            Categoria categoria = new Categoria();
            DAO_Categoria controlCategoria = new DAO_Categoria();

            categoria.setDescripcion(interGestionarCategoria.txt_descripcion.getText().trim());
            if (controlCategoria.actualizar(categoria, idCategoria)) {
                JOptionPane.showMessageDialog(null, "Categoria Actulizada");
                interGestionarCategoria.txt_descripcion.setText("");
                this.CargarTablaCategorias();
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar Categoria");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una categoria");
        }
    }

    public void Eliminar() {
        if (!interGestionarCategoria.txt_descripcion.getText().isEmpty()) {
            Categoria categoria = new Categoria();
            DAO_Categoria controlCategoria = new DAO_Categoria();

            categoria.setDescripcion(interGestionarCategoria.txt_descripcion.getText().trim());
            if (!controlCategoria.eliminar(idCategoria)) {
                JOptionPane.showMessageDialog(null, "Categoria Eliminada");
                interGestionarCategoria.txt_descripcion.setText("");
                this.CargarTablaCategorias();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Eliminar Categoria");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione una categoria");
        }
    }

    /*
     * *****************************************************
     * metodo para mostrar todos las categorias registradas
     * *****************************************************
     */
    public void CargarTablaCategorias() {
        Connection con = Conexion.conectar();
        DefaultTableModel model = new DefaultTableModel();
        String sql = "select idCategoria, descripcion, estado from tb_categoria";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            InterGestionarCategoria.jTable_categorias = new JTable(model);
            InterGestionarCategoria.jScrollPane1.setViewportView(InterGestionarCategoria.jTable_categorias);

            model.addColumn("idCategoria");
            model.addColumn("descripcion");
            model.addColumn("estado");

            while (rs.next()) {
                Object fila[] = new Object[3];
                for (int i = 0; i < 3; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                model.addRow(fila);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al llenar la tabla categorias: " + e);
        }
        //evento para obtener campo al cual el usuario da click
        //y obtener la interfaz que mostrara la informacion general
        jTable_categorias.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila_point = jTable_categorias.rowAtPoint(e.getPoint());
                int columna_point = 0;

                if (fila_point > -1) {
                    idCategoria = (int) model.getValueAt(fila_point, columna_point);
                    EnviarDatosCategoriaSeleccionada(idCategoria);
                }
            }
        });
    }

    /*
     * **************************************************
     * Metodo que envia datos seleccionados
     * **************************************************
     */
    private void EnviarDatosCategoriaSeleccionada(int idCategoria) {
        try {
            Connection con = Conexion.conectar();
            PreparedStatement pst = con.prepareStatement(
                    "select * from tb_categoria where idCategoria = '" + idCategoria + "'");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                interGestionarCategoria.txt_descripcion.setText(rs.getString("descripcion"));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al seleccionar categoria: " + e);
        }
    }
}
