
package controlador;

import conexion.Conexion;
import conexion.DAO_Producto;
import java.awt.Color;
import java.awt.HeadlessException;
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
import modelo.Producto;
import vista.*;
import static vista.FrmMenu.jDesktopPane_menu;
import static vista.InterGestionarProducto.jTable_productos;


public class Ctrl_producto {
    
    private int idProducto;
 //   int obtenerIdCategoriaCombo = 0;
    int obtenerIdCategoriaCombo = 0;
    InterProducto interProducto;
    InterGestionarProducto interGestionarProducto;

    public Ctrl_producto(InterProducto interProducto, InterGestionarProducto interGestionarProducto) {
        this.interProducto = interProducto;
        this.interGestionarProducto = interGestionarProducto;
        
        this.interGestionarProducto.jButton_Registrar.addActionListener(e -> AbrirRegistro());
        this.interGestionarProducto.jButton_actualizar.addActionListener(e -> Actualizar());
        this.interProducto.jButton_Guardar.addActionListener(e -> Guardar());
        
        this.CargarComboCategorias();
        this.CargarComboCategoria2();
        
        this.CargarTablaProductos();
     
    }
    
   
    
    public void AbrirRegistro() {
        jDesktopPane_menu.add(interProducto);
        interProducto.setVisible(true);
    }
    
    public void Guardar() {
        
        Producto producto = new Producto();
        DAO_Producto controlProducto = new DAO_Producto();
        String iva = "";
        String categoria = "";
        iva = interProducto.jComboBox_iva.getSelectedItem().toString().trim();
        categoria = interProducto.jComboBox_categoria.getSelectedItem().toString().trim();

        //validar campos
        if (interProducto.txt_nombre.getText().equals("") || interProducto.txt_cantidad.getText().equals("") || interProducto.txt_precio.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos");
            interProducto.txt_nombre.setBackground(Color.red);
            interProducto.txt_cantidad.setBackground(Color.red);
            interProducto.txt_precio.setBackground(Color.red);
        } else {
            //consulta para ver si el producto ya existe
            if (!controlProducto.existeProducto(interProducto.txt_nombre.getText().trim())) {
                if (iva.equalsIgnoreCase("Seleccione iva:")) {
                    JOptionPane.showMessageDialog(null, "Seleccione iva.");
                } else {
                    if (categoria.equalsIgnoreCase("Seleccione categoria:")) {
                        JOptionPane.showMessageDialog(null, "Seleccione categoria");
                    } else {
                        try {
                            producto.setNombre(interProducto.txt_nombre.getText().trim());
                            producto.setCantidad(Integer.parseInt(interProducto.txt_cantidad.getText().trim()));
                            String precioTXT = "";
                            double Precio = 0.0;
                            precioTXT = interProducto.txt_precio.getText().trim();
                            boolean aux = false;
                            /*
                            *Si el usuario ingresa , (coma) como punto decimal,
                            lo transformamos a punto (.)
                             */
                            for (int i = 0; i < precioTXT.length(); i++) {
                                if (precioTXT.charAt(i) == ',') {
                                    String precioNuevo = precioTXT.replace(",", ".");
                                    Precio = Double.parseDouble(precioNuevo);
                                    aux = true;
                                }
                            }
                            //evaluar la condicion
                            if (aux == true) {
                               
                                producto.setPrecioUnitario(Precio);
                            } else {
                                Precio = Double.parseDouble(precioTXT);
                                producto.setPrecioUnitario(Precio);
                            }

                            producto.setDescripcion(interProducto.txt_descripcion.getText().trim());
                            //Porcentaje IVA
                            if (iva.equalsIgnoreCase("No grava iva")) {
                                producto.setPorcentajeIva(0);
                            } else if (iva.equalsIgnoreCase("12%")) {
                                producto.setPorcentajeIva(12);
                            } else if (iva.equalsIgnoreCase("14%")) {
                                producto.setPorcentajeIva(14);
                            }

                            //idcategoria - cargar metodo que obtiene el id de categoria
                            this.IdCategoria();
                            producto.setIdCategoria(obtenerIdCategoriaCombo);
                            producto.setEstado(1);

                            if (controlProducto.guardar(producto)) {
                                JOptionPane.showMessageDialog(null, "Registro Guardado");
                                interProducto.txt_nombre.setBackground(Color.green);
                                interProducto.txt_cantidad.setBackground(Color.green);
                                interProducto.txt_precio.setBackground(Color.green);
                                interProducto.txt_descripcion.setBackground(Color.green);

                                this.CargarComboCategorias();
                                this.interProducto.jComboBox_iva.setSelectedItem("Seleccione iva:");
                                this.Limpiar();
                            } else {
                                JOptionPane.showMessageDialog(null, "Error al Guardar");
                            }

                        } catch (HeadlessException | NumberFormatException e) {
                            System.out.println("Error en: " + e);
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "El producto ya existe en la Base de Datos");
            }
        }
    }
    
    public void Actualizar() {
        
        Producto producto = new Producto();
        DAO_Producto controlProducto = new DAO_Producto();
        String iva = "";
        String categoria = "";
        iva = interGestionarProducto.jComboBox_iva.getSelectedItem().toString().trim();
        categoria = interGestionarProducto.jComboBox_categoria.getSelectedItem().toString().trim();

        //validar campos
        if (interGestionarProducto.txt_nombre.getText().isEmpty() || interGestionarProducto.txt_Iva.getText().isEmpty() || interGestionarProducto.txt_cantidad.getText().isEmpty() || interGestionarProducto.txt_precio.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos");
        } else {
            
                if (categoria.equalsIgnoreCase("Seleccione categoria:")) {
                    JOptionPane.showMessageDialog(null, "Seleccione categoria");
                } else {
                    try {
                        producto.setNombre(interGestionarProducto.txt_nombre.getText().trim());
                        producto.setCantidad(Integer.parseInt(interGestionarProducto.txt_cantidad.getText().trim()));
                        
                        String precioTXT = "";
                        double Precio = 0.0;
                        precioTXT = interGestionarProducto.txt_precio.getText().trim();
                        boolean aux = false;
                        /*
                            *Si el usuario ingresa , (coma) como punto decimal,
                            lo transformamos a punto (.)
                         */
                        for (int i = 0; i < precioTXT.length(); i++) {
                            if (precioTXT.charAt(i) == ',') {
                                String precioNuevo = precioTXT.replace(",", ".");
                                Precio = Double.parseDouble(precioNuevo);
                                aux = true;
                            }
                        }
                        //evaluar la condicion
                        if (aux == true) {
                            producto.setPrecioUnitario(Precio);
                        } else {
                            Precio = Double.parseDouble(precioTXT);
                            producto.setPrecioUnitario(Precio);
                        }

                        producto.setDescripcion(interGestionarProducto.txt_descripcion.getText().trim());
                        //Porcentaje IVA
                        if (iva.equalsIgnoreCase("No grava iva")) {
                            producto.setPorcentajeIva(0);
                        } else if (iva.equalsIgnoreCase("12%")) {
                            producto.setPorcentajeIva(12);
                        } else if (iva.equalsIgnoreCase("14%")) {
                            producto.setPorcentajeIva(14);
                        }

                        //idcategoria - cargar metodo que obtiene el id de categoria
                        this.IdCategoria();
                        producto.setIdCategoria(obtenerIdCategoriaCombo);
                        producto.setEstado(1);

                        if (controlProducto.actualizar(producto, idProducto)) {
                            JOptionPane.showMessageDialog(null, "Registro Actualizado");
                            this.CargarComboCategoria2();
                            this.CargarTablaProductos();
                            this.interGestionarProducto.jComboBox_iva.setSelectedItem("Seleccione iva:");
                            this.Limpiar2();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al Actualizar");
                        }
                    } catch (HeadlessException | NumberFormatException e) {
                        System.out.println("Error en: " + e);
                    }
                }
            }
        }
    
    
    public void Eliminar() {
          DAO_Producto controlProducto = new DAO_Producto();
        if (idProducto == 0) {
            JOptionPane.showMessageDialog(null, "¡Seleccione un Producto!");
        } else {
            if (!controlProducto.eliminar(idProducto)) {
                JOptionPane.showMessageDialog(null, "¡Producto Eliminado!");
                this.CargarTablaProductos();
                this.CargarComboCategoria2();
                this.Limpiar2();
            } else {
                JOptionPane.showMessageDialog(null, "¡Error al eliminar producto!");
            }
        }  
    }
    
      /**
     *
     * Metodo para limpiar campos
     */
    private void Limpiar() {
        interProducto.txt_nombre.setText("");
        interProducto.txt_cantidad.setText("");
        interProducto.txt_precio.setText("");
        interProducto.txt_descripcion.setText("");

    }

    /**
     *
     * Metodo para cargar las categorias
     */
    private void CargarComboCategorias() {
        Connection cn = Conexion.conectar();
        String sql = "select * from tb_categoria";
        Statement st;

        try {

            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            interProducto.jComboBox_categoria.removeAllItems();
            interProducto.jComboBox_categoria.addItem("Seleccione categoria:");
            while (rs.next()) {
                interProducto.jComboBox_categoria.addItem(rs.getString("descripcion"));
            }
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al cargar categorias");
        }
    }

    /**
     *
     * Metodo para obtener id categoria
     */
    private int IdCategoria() {
        String sql = "select * from tb_categoria where descripcion = '" + this.interProducto.jComboBox_categoria.getSelectedItem() + "'";
        Statement st;
        try {
            Connection cn = Conexion.conectar();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                obtenerIdCategoriaCombo = rs.getInt("idCategoria");
            }
        } catch (SQLException e) {
            System.out.println("Error al obener id categoria");
        }
        return obtenerIdCategoriaCombo;
    }
    
       /*
     * *****************************************************
     * metodo para limpiar
     * *****************************************************
     */
    private void Limpiar2() {
        interGestionarProducto.txt_nombre.setText("");
        interGestionarProducto.txt_cantidad.setText("");
        interGestionarProducto.txt_precio.setText("");
        interGestionarProducto.txt_descripcion.setText("");
        interGestionarProducto.jComboBox_iva.setSelectedItem("Seleccione iva:");
        interGestionarProducto.jComboBox_categoria.setSelectedItem("Seleccione categoria:");
    }

    /*
     * *****************************************************
     * metodo para cargar las categorias en el JCombox
     * *****************************************************
     */
    private void CargarComboCategoria2() {
        Connection cn = Conexion.conectar();
        String sql = "select * from tb_categoria";
        Statement st;
        try {

            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            interGestionarProducto.jComboBox_categoria.removeAllItems();
            interGestionarProducto.jComboBox_categoria.addItem("Seleccione categoria:");
            while (rs.next()) {
                interGestionarProducto.jComboBox_categoria.addItem(rs.getString("descripcion"));
            }
            cn.close();

        } catch (SQLException e) {
            System.out.println("¡Error al cargar categorias!");
        }
    }

    /*
     * *****************************************************
     * metodo para mostrar todos los productos registrados
     * *****************************************************
     */
    String descripcionCategoria = "";
    double precio = 0.0;
    int porcentajeIva = 0;
    double IVA = 0;

    private void CargarTablaProductos() {
        Connection con = Conexion.conectar();
        DefaultTableModel model = new DefaultTableModel();
        String sql = "select p.idProducto, p.nombre, p.cantidad, p.precio, p.descripcion, p.porcentajeIva, c.descripcion, p.estado from tb_producto As p, tb_categoria As c where p.idCategoria = c.idCategoria;";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            InterGestionarProducto.jTable_productos = new JTable(model);
            InterGestionarProducto.jScrollPane1.setViewportView(InterGestionarProducto.jTable_productos);

            model.addColumn("N°");//ID
            model.addColumn("nombre");
            model.addColumn("cantidad");
            model.addColumn("precio");
            model.addColumn("descripcion");
            model.addColumn("Iva");
            model.addColumn("Categoria");
            model.addColumn("estado");

            while (rs.next()) {

                precio = rs.getDouble("precio");
                porcentajeIva = rs.getInt("porcentajeIva");

                Object fila[] = new Object[8];
                for (int i = 0; i < 8; i++) {

                    if (i == 5) {
                        this.calcularIva(precio, porcentajeIva);//metodo
                        fila[i] = IVA;
                        rs.getObject(i + 1);
                    } else {
                        fila[i] = rs.getObject(i + 1);
                    }
                }
                model.addRow(fila);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al llenar la tabla productos: " + e);
        }
        //evento para obtener campo al cual el usuario da click
        //y obtener la interfaz que mostrara la informacion general
        jTable_productos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila_point = jTable_productos.rowAtPoint(e.getPoint());
                int columna_point = 0;

                if (fila_point > -1) {
                    idProducto = (int) model.getValueAt(fila_point, columna_point);
                    EnviarDatosProductoSeleccionado(idProducto);//metodo
                }
            }
        });
    }

    /*
     * **************************************************
     * Metodo para calcular Iva
     * **************************************************
     */
    private double calcularIva(double precio, int iva) {
        int p_iva = iva;
        switch (p_iva) {
            case 0:
                IVA = 0.0;
                break;
            case 12:
                IVA = precio * 0.12;
                break;
            case 14:
                IVA = precio * 0.14;
                break;
            default:
                break;
        }
        //redondear decimales
        IVA = (double) Math.round(IVA * 100) / 100;
        return IVA;
    }

    /*
     * **************************************************
     * Metodo que envia datos seleccionados
     * **************************************************
     */
    private void EnviarDatosProductoSeleccionado(int idProducto) {
        try {
            Connection con = Conexion.conectar();
            PreparedStatement pst = con.prepareStatement(
                    "select * from tb_producto where idProducto = '" + idProducto + "'");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                interGestionarProducto.txt_nombre.setText(rs.getString("nombre"));
                interGestionarProducto.txt_cantidad.setText(rs.getString("cantidad"));
                interGestionarProducto.txt_precio.setText(rs.getString("precio"));
                interGestionarProducto.txt_descripcion.setText(rs.getString("descripcion"));
                int iva = rs.getInt("porcentajeIva");
                switch (iva) {
                    case 0:
                        interGestionarProducto.jComboBox_iva.setSelectedItem("No grava iva");
                        break;
                    case 12:
                        interGestionarProducto.jComboBox_iva.setSelectedItem("12%");
                        break;
                    case 14:
                        interGestionarProducto.jComboBox_iva.setSelectedItem("14%");
                        break;
                    default:
                        interGestionarProducto.jComboBox_iva.setSelectedItem("Seleccione iva:");
                        break;
                }
                int idCate = rs.getInt("idCategoria");
                interGestionarProducto.jComboBox_categoria.setSelectedItem(relacionarCategoria(idCate));
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al seleccionar producto: " + e);
        }
    }

    /*
     * **************************************************
     * Metodo para relacionar categorias
     * **************************************************
     */
    private String relacionarCategoria(int idCategoria) {

        String sql = "select descripcion from tb_categoria where idCategoria = '" + idCategoria + "'";
        Statement st;
        try {
            Connection cn = Conexion.conectar();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                descripcionCategoria = rs.getString("descripcion");
            }
            cn.close();

        } catch (SQLException e) {
            System.out.println("¡Error al obtener el id de la categoria!");
        }
        return descripcionCategoria;
    }

    /**
     *
     * Metodo para obtener id categoria
     */
    private int IdCategoria2() {
        String sql = "select * from tb_categoria where descripcion = '" + this.interGestionarProducto.jComboBox_categoria.getSelectedItem() + "'";
        Statement st;
        try {
            Connection cn = Conexion.conectar();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                obtenerIdCategoriaCombo = rs.getInt("idCategoria");
            }
        } catch (SQLException e) {
            System.out.println("Error al obener id categoria");
        }
        return obtenerIdCategoriaCombo;
    }
}
