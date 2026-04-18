package controlador;

import conexion.Conexion;
import conexion.DAO_Producto;
import conexion.DAO_RegistrarCompra;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
import modelo.CabeceraCompra;
import modelo.DetalleCompra;
import modelo.Producto;
import org.json.JSONObject;
import vista.*;
import static vista.InterCompra.jTable_compras;

public class Ctrl_compra {

    private int idProveedor = 0;
    private int idCompra;
    private DefaultTableModel modeloDatosProductos;
    //lista para el detalle de venta de los productos
    ArrayList<DetalleCompra> listaDetalle = new ArrayList<>();
    ArrayList<Producto> listaProducto = new ArrayList<>();
    private DetalleCompra detalle;
    private Producto producto;
    int IdCategoria = 0;

    private int auxIdDetalle = 1;//id del detalle de venta

    private int idProducto = 0;
    private String nombre = "";
    private int idProductoBD = 0;

    private int cantidad = 0;
    private double subtotal = 0.0;
    private double IVA = 0.0;//subtotal * (p.getPorcentajeIva() / 100);
    private double descuento = 0.0;
    private double totalPagar = 0.0;
    private double precioUnitario = 0.0;
    private int porcentajeIva = 0;
    private String Descripcion = " ";
    private int stockActual = 0;
    private double ValorDolar;
    private double TotalDolares ;
    public String resultado = "";

    InterCompra interCompra;
    InterGestionarCompras interGestionarCompras;

    public Ctrl_compra(InterCompra interCompra, InterGestionarCompras interGestionarCompras) {
        this.interCompra = interCompra;
        this.interGestionarCompras = interGestionarCompras;

        this.interCompra.jButton_RegistrarCompra.addActionListener(e -> RegistrarCompra());
        this.interCompra.jButton_añadir.addActionListener(e -> AñadirProducto());
        this.interCompra.jButton_busca_proveedor.addActionListener(e -> BuscarProveedor());

        this.interGestionarCompras.jButton_actualizar.addActionListener(e -> Actualizar());
        
        this.CargarComboClientes();

        this.CargarComboProveeores();
        this.CargarComboCategoria();
        this.inicializarTablaProducto();
       // this.borrar();
    }

    public void AñadirProducto() {
        DAO_Producto controlProducto = new DAO_Producto();
        String ivaSelec = "";
        String categoria = "";
        ivaSelec = interCompra.jComboBox_iva1.getSelectedItem().toString().trim();
        categoria = interCompra.jComboBox_categoria1.getSelectedItem().toString().trim();

        //validar campos
        if (interCompra.txt_nombre1.getText().equals("") || interCompra.txt_cantidad1.getText().equals("") || interCompra.txt_precio1.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos");
        } else {
            if (ivaSelec.equalsIgnoreCase("Seleccione iva:")) {
                JOptionPane.showMessageDialog(null, "Seleccione iva.");
            } else {
                if (categoria.equalsIgnoreCase("Seleccione categoria:")) {
                    JOptionPane.showMessageDialog(null, "Seleccione categoria");
                } else {

                    try {

                        nombre = interCompra.txt_nombre1.getText().trim();
                        cantidad = Integer.parseInt(interCompra.txt_cantidad1.getText().trim());
                        Descripcion = interCompra.txt_descripcion1.getText().trim();
                        precioUnitario = Double.parseDouble(interCompra.txt_precio1.getText().trim());
                        if (ivaSelec.equalsIgnoreCase("No grava iva")) {
                            porcentajeIva = 0;
                        } else if (ivaSelec.equalsIgnoreCase("12%")) {
                            porcentajeIva = 12;
                        } else if (ivaSelec.equalsIgnoreCase("14%")) {
                            porcentajeIva = 14;
                        }
                        //idcategoria - cargar metodo que obtiene el id de categoria
                        this.IdoCategoria();

                        //    producto.setIdCategoria(obtenerIdCategoriaCombo);
                        //  producto.setEstado(1);
                        double iva = (double) porcentajeIva;
                        subtotal = cantidad * precioUnitario;
                        IVA = subtotal * (iva / 100);
                        IVA = (double) Math.round(IVA * 100) / 100;
                        totalPagar = subtotal + IVA - descuento;

                        detalle = new DetalleCompra(auxIdDetalle,//idDetalleVentaz
                                1, //idCabecera
                                idProducto,
                                nombre,
                                cantidad,
                                precioUnitario,
                                subtotal,
                                descuento,
                                IVA,
                                totalPagar,
                                1//estado
                        );

                        producto = new Producto(
                                idProducto,
                                nombre,
                                cantidad,
                                100,
                                10,
                                precioUnitario,
                                Descripcion,
                                porcentajeIva,
                                IdCategoria,
                                1);//estado

                        listaDetalle.add(detalle);
                        listaProducto.add(producto);

                        this.listaTablaProductos();
                        this.calcularTotales();
                        this.Limpiar();
                        this.interCompra.jComboBox_iva1.setSelectedItem("Seleccione iva:");
                        this.interCompra.jComboBox_categoria1.setSelectedItem("Seleccione categoria:");

                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "cantidad y precio deben ser numericos.");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error al agregar el product" + e.getMessage());
                    } catch (ExceptionInInitializerError e) {
                        System.out.println("Error en: " + e);
                    }

                }
            }
        }
    }

    public void BuscarProveedor() {
        String proveedorBuscar = interCompra.txt_proveedor_buscar.getText().trim();
        Connection cn = Conexion.conectar();
        String sql = "select nombre, apellido from tb_proveedor where cedula = '" + proveedorBuscar + "'";
        Statement st;
        try {
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                interCompra.jComboBox_proveedor.setSelectedItem(rs.getString("nombre") + " " + rs.getString("apellido"));
            } else {
                interCompra.jComboBox_proveedor.setSelectedItem("Seleccione proveedor:");
                JOptionPane.showMessageDialog(null, "¡Cedula de proveedor incorrecta o no encontrada!");
            }
            interCompra.txt_proveedor_buscar.setText("");
            cn.close();
        } catch (SQLException e) {
            System.out.println("¡Error al buscar proveedor!, " + e.getMessage());
        }
    }

    public void RegistrarCompra() {

        CabeceraCompra cabeceraCompra = new CabeceraCompra();
        DetalleCompra detalleCompra = new DetalleCompra();
        DAO_RegistrarCompra controlCompra = new DAO_RegistrarCompra();
        DAO_Producto controlProducto = new DAO_Producto();

        String fechaActual = "";
        Date date = new Date();
        fechaActual = new SimpleDateFormat("yyyy/MM/dd").format(date);
        // DefaultTableModel modeloProductos = (DefaultTableModel) jTable_compras.getModel();

        if (interCompra.jComboBox_proveedor.getSelectedItem().equals("Seleccione proveedor:")) {
            JOptionPane.showMessageDialog(null, "Selecciona un proveedor");
            return;
        }

        if (listaDetalle.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos en la compra");
            return;
        }
        
        if (interCompra.jComboBox_tipoPago.getSelectedItem().equals("Seleccione tipo de pago")) {
            JOptionPane.showMessageDialog(null, "Selecciona una forma de pago");
            return;
        }
        
        if (interCompra.jComboBox_estadoPago.getSelectedItem().equals("Seleccione estado del pago")) {
            JOptionPane.showMessageDialog(null, "Selecciona el estado de pago");
            return;
        }

        String tipoPago = interCompra.jComboBox_tipoPago.getSelectedItem().toString().trim();
        String estadoPago = interCompra.jComboBox_estadoPago.getSelectedItem().toString().trim();
        boolean detalleguardado = false;
        try {
            //metodo para obtener el id del cliente
                this.ObtenerIdProveedor();
                //registrar cabecera
                cabeceraCompra.setIdCabeceraCompra(0);
                cabeceraCompra.setIdProveedor(idProveedor);
                cabeceraCompra.setValorPagar(Double.parseDouble(interCompra.txt_total_pagar.getText()));
                cabeceraCompra.setTipoPago(tipoPago);
                cabeceraCompra.setFechaCompra(fechaActual);
                cabeceraCompra.setEstado(estadoPago);

            if (controlCompra.guardar(cabeceraCompra)) {
                    JOptionPane.showMessageDialog(null, "¡Compra  Registrada!");
                    
                     for (DetalleCompra elemento : listaDetalle) {
                    detalleCompra.setIdDetalleCompra(0);
                    detalleCompra.setIdCabeceraCompra(0);
                    detalleCompra.setIdProducto(elemento.getIdProducto());
                    detalleCompra.setCantidad(elemento.getCantidad());
                    detalleCompra.setPrecioUnitario(elemento.getPrecioUnitario());
                    detalleCompra.setSubTotal(elemento.getSubTotal());
                    detalleCompra.setDescuento(elemento.getDescuento());
                    detalleCompra.setIva(elemento.getIva());
                    detalleCompra.setTotalPagar(elemento.getTotalPagar());
                    detalleCompra.setEstado(1);

                    if (controlCompra.guardarDetalle(detalleCompra)) {
                        System.out.println("Detalle de Compra Registrado");

                    } else {
                        JOptionPane.showMessageDialog(null, "¡Error al guardar detalle de Compra!");
                    }
                    
                }
                     
                    
 

                Producto p = new Producto();
                for (Producto pro : listaProducto) {

                    if (!controlProducto.existeProducto(pro.getNombre())) { // Revisa en la base de datos si el producto existe
//JOptionPane.showMessageDialog(this, "Producto " + pro.getNombre() + " no existe");
                        p.setIdProducto(pro.getIdProducto());
                        p.setCantidad(pro.getCantidad());
                        p.setStockMaximo(100);
                        p.setStockMinimo(10);
                        p.setPrecioUnitario(pro.getPrecioUnitario());
                        p.setNombre(pro.getNombre());
                        p.setDescripcion(pro.getDescripcion());
                        p.setPorcentajeIva(pro.getPorcentajeIva());
                        p.setIdCategoria(pro.getIdCategoria());
                        p.setEstado(1);

                        if (controlProducto.guardar(p)) {     // SI EL PRODUCTO NO EXISTE LO GUARDA NORMAL
                            System.out.println("producto de Compra Registrado");
                            //    this.RestarStockProductos(elemento.getIdProducto(), elemento.getCantidad());
                        } else {
                            System.out.println("Error al registrar");
                        }

                    } else {
//JOptionPane.showMessageDialog(this, "Producto " + pro.getNombre() + " si existe"); // SI EL PRODUCTO  EXISTE LO ACTUALIZA
                        int stockNuevo = pro.getCantidad();
                        //  int stockNuevo = Integer.parseInt(txt_cantidad_nueva.getText().trim());
                        MostrarStock(pro);
                        stockNuevo = stockActual + stockNuevo;
                        // pro.setCantidad(stockNuevo);
                        p.setCantidad(stockNuevo);
                        if (controlProducto.actualizarStock(p, idProductoBD)) {
                            System.out.println("Stock Actualizado");
                        } else {
                            System.out.println("Error al Actualizar Stock");
                        }// VER LA VISTA ACTUALIZAR STOCK AHI SE ENCUENTRA EL CODIGO NECESARIO, Y BUSCAR UNA MANERA DE OBTENER LA CANTIDAD ACTUAL DEL STOCK EN LA BASE DE DATOS
                    }

                }
                
            
                
                
              //vaciamos la lista
        this.listaDetalle.clear();
        this.listaTablaProductos();
        this.CargarComboProveeores();
        this.calcularTotales();
        interCompra.txt_subtotal.setText("0.0");
        interCompra.txt_iva.setText("0.0");
        interCompra.txt_descuento.setText("0.0");
        interCompra.txt_total_pagar.setText("0.0");
        auxIdDetalle = 1;  
                
                
            }
        
        } catch (Exception e) {
            System.out.println("¡Error al guardar cabecera de venta!  " + e);
        }

    }

    public void Actualizar() {

        CabeceraCompra cabeceraCompra = new CabeceraCompra();
        DAO_RegistrarCompra controlRegistrarCompra = new DAO_RegistrarCompra();
        String proveedor,formaPago, estado;
        proveedor = interGestionarCompras.jComboBox_proveedor.getSelectedItem().toString().trim();
        formaPago = interGestionarCompras.jComboBox_tipoPago.getSelectedItem().toString().trim();
        estado = interGestionarCompras.jComboBox_estadoPago.getSelectedItem().toString().trim();

        //obtener el id del proveedor
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "select idProveedor, concat(nombre, ' ', apellido) as proveedor "
                    + "from tb_proveedor where concat(nombre, ' ', apellido) = '" + proveedor + "'");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                idProveedor = rs.getInt("idProveedor");
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error en cargar el id Proveedor: " + e);
        }

        //Actualizar datos
        try {
            if (!proveedor.equalsIgnoreCase("Seleccione Proveedor:")) {
             cabeceraCompra.setIdProveedor(idProveedor)
                     ;
            if (!formaPago.equalsIgnoreCase("Seleccione tipo de pago:")) {
                
                cabeceraCompra.setTipoPago(formaPago);
                if (estado.equalsIgnoreCase("Pagado")) {
                    cabeceraCompra.setEstado("Pagado");
                } else{ 
                    cabeceraCompra.setEstado("Pendiente");
                }

                if (controlRegistrarCompra.actualizar(cabeceraCompra, idCompra)) {
                    JOptionPane.showMessageDialog(null, "¡Registro Actualizado!");
                    this.CargarTablaCompras();
                    this.Limpiar();
                } else {
                    
                    JOptionPane.showMessageDialog(null, "Error al Actualizar");
                  
                }

            } else {
                JOptionPane.showMessageDialog(null, "Seleccione un registro para actualizar datos");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Seleccione un registro para actualizar datos");
        }
        } catch (Exception e) {
            System.out.println("Error  "+ e.getMessage());
        }
        
    }            
    
/******************************************************+++
 *      METODOS PARA REGISTRAR UNA COMPRA
 */
    private void inicializarTablaProducto() {
        modeloDatosProductos = new DefaultTableModel();
        //añadir columnas
        modeloDatosProductos.addColumn("N");
        modeloDatosProductos.addColumn("Nombre");
        modeloDatosProductos.addColumn("Cantidad");
        modeloDatosProductos.addColumn("P. Unitario");
        modeloDatosProductos.addColumn("SubTotal");
        modeloDatosProductos.addColumn("Descuento");
        modeloDatosProductos.addColumn("Iva");
        modeloDatosProductos.addColumn("Total Pagar");
        modeloDatosProductos.addColumn("Accion");
        //agregar los datos del modelo a la tabla
        this.interCompra.jTable_compras.setModel(modeloDatosProductos);
    }

    //metodo para presentar la informacion de la tavla DetalleVenta
    private void listaTablaProductos() {
        this.modeloDatosProductos.setRowCount(listaDetalle.size());
        for (int i = 0; i < listaDetalle.size(); i++) {
            this.modeloDatosProductos.setValueAt(i + 1, i, 0);
            this.modeloDatosProductos.setValueAt(listaDetalle.get(i).getNombre(), i, 1);
            this.modeloDatosProductos.setValueAt(listaDetalle.get(i).getCantidad(), i, 2);
            this.modeloDatosProductos.setValueAt(listaDetalle.get(i).getPrecioUnitario(), i, 3);
            this.modeloDatosProductos.setValueAt(listaDetalle.get(i).getSubTotal(), i, 4);
            this.modeloDatosProductos.setValueAt(listaDetalle.get(i).getDescuento(), i, 5);
            this.modeloDatosProductos.setValueAt(listaDetalle.get(i).getIva(), i, 6);
            this.modeloDatosProductos.setValueAt(listaDetalle.get(i).getTotalPagar(), i, 7);
            this.modeloDatosProductos.setValueAt("Eliminar", i, 8);//aqui luego poner un boton de eliminar
        }
        //añadir al Jtable
        jTable_compras.setModel(modeloDatosProductos);
    }

    /**
     *
     * Metodo para limpiar campos
     */
    private void Limpiar() {
        interCompra.txt_nombre1.setText("");
        interCompra.txt_cantidad1.setText("");
        interCompra.txt_precio1.setText("");
        interCompra.txt_descripcion1.setText("");

    }

    private void calcularTotales() {
        double subtotalGeneral = 0;
        double descuentoGeneral = 0;
        double ivaGeneral = 0;
        double totalGeneral = 0;

        for (DetalleCompra elemento : listaDetalle) {
            subtotalGeneral += elemento.getSubTotal();
            descuentoGeneral += elemento.getDescuento();
            ivaGeneral += elemento.getIva();
            totalGeneral += elemento.getTotalPagar();
        }
        //redondear decimales
        subtotalGeneral = (double) Math.round(subtotalGeneral * 100) / 100;
        ivaGeneral = (double) Math.round(ivaGeneral * 100) / 100;
        descuentoGeneral = (double) Math.round(descuentoGeneral * 100) / 100;
        totalGeneral = (double) Math.round(totalGeneral * 100) / 100;

        //enviar datos a la vista
        interCompra.txt_subtotal.setText(String.valueOf(subtotalGeneral));
        interCompra.txt_iva.setText(String.valueOf(ivaGeneral));
        interCompra.txt_descuento.setText(String.valueOf(descuentoGeneral));
        interCompra.txt_total_pagar.setText(String.valueOf(totalGeneral));

    }

    /*
    Metodo para cargar los proveedorea en el jComboBox
     */
    private void CargarComboProveeores() {
        Connection cn = Conexion.conectar();
        String sql = "select * from tb_proveedor";
        Statement st;
        try {
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            interCompra.jComboBox_proveedor.removeAllItems();
            interCompra.jComboBox_proveedor.addItem("Seleccione proveedor:");
            while (rs.next()) {
                interCompra.jComboBox_proveedor.addItem(rs.getString("nombre") + " " + rs.getString("apellido"));
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("¡Error al cargar proveedores, !" + e);
        }
    }

    /*
    Metodo para obtener id del proveedor
     */
    private void ObtenerIdProveedor() {
        try {
            String sql = "select * from tb_proveedor where concat(nombre,' ',apellido) = '" + this.interCompra.jComboBox_proveedor.getSelectedItem() + "'";
            Connection cn = Conexion.conectar();
            Statement st;
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                idProveedor = rs.getInt("idProveedor");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener id del Proveedor, " + e);
        }
    }

    /*
     * *****************************************************
     * metodo para cargar las categorias en el JCombox
     * *****************************************************
     */
    private void CargarComboCategoria() {
        Connection cn = Conexion.conectar();
        String sql = "select * from tb_categoria";
        Statement st;
        try {

            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            interCompra.jComboBox_categoria1.removeAllItems();
            interCompra.jComboBox_categoria1.addItem("Seleccione categoria:");
            while (rs.next()) {
                interCompra.jComboBox_categoria1.addItem(rs.getString("descripcion"));
            }
            cn.close();

        } catch (SQLException e) {
            System.out.println("¡Error al cargar categorias!");
        }
    }

    private void IdoCategoria() {
        String sql = "select * from tb_categoria where descripcion = '" + this.interCompra.jComboBox_categoria1.getSelectedItem() + "'";
        Statement st;
        try {
            Connection cn = Conexion.conectar();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                IdCategoria = rs.getInt("idCategoria");
            }
        } catch (SQLException e) {
            System.out.println("Error al obener id categoria");
        }
    }

    private int IdCategoria() {
        String sql = "select * from tb_categoria where descripcion = '" + this.interCompra.jComboBox_categoria1.getSelectedItem() + "'";
        Statement st;
        try {
            Connection cn = Conexion.conectar();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                IdCategoria = rs.getInt("idCategoria");
            }
        } catch (SQLException e) {
            System.out.println("Error al obener id categoria");
        }
        return IdCategoria;
    }

    private void MostrarStock(Producto pro) {
        try {

            Connection cn = Conexion.conectar();
            String sql = "select * from tb_producto where nombre = '" + this.producto.getNombre() + "'";
            Statement st;
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                idProductoBD = rs.getInt("idProducto");
                stockActual = rs.getInt("cantidad");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener stock del producto en: " + e);
        }
    }

    /**
     * *******************************************************************
     *
     * METODOS PARA GESTIONAR LAS VENTAS
     *
     **********************************************************************
     */
    /*
     * *****************************************************
     * metodo para limpiar
     * *****************************************************
     */
    private void Limpiar2() {
        interGestionarCompras.txt_total_pagar.setText("");
        interGestionarCompras.txt_fecha.setText("");
        interGestionarCompras.jComboBox_proveedor.setSelectedItem("Seleccione proveedor:");
        interGestionarCompras.jComboBox_estadoPago.setSelectedItem("Pagado");
        idProveedor = 0;
    }


    /*
     * *****************************************************
     * metodo para mostrar todos los clientes registrados
     * *****************************************************
     */
    private void CargarTablaCompras() {
        Connection con = Conexion.conectar();
        DefaultTableModel model = new DefaultTableModel();
        String sql = "select cc.idCabeceraCompra as id, concat(p.nombre, ' ', p.apellido) as proveedor, "
                + "cc.valorPagar as total,cc.formaPago as forma_pago, cc.fechaCompra as fecha, cc.estado "
                + "from tb_cabecera_compra as cc, tb_proveedor as p where cc.idProveedor = p.idProveedor;";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            InterGestionarCompras.jTable_compras = new JTable(model);
            InterGestionarCompras.jScrollPane1.setViewportView(InterGestionarCompras.jTable_compras);

            model.addColumn("N°");//ID
            model.addColumn("Proveedor");
            model.addColumn("Total Pagar");
            model.addColumn("Forma de Pago");
            model.addColumn("Fecha Compra");
            model.addColumn("estado");

            while (rs.next()) {
                Object fila[] = new Object[6];
                for (int i = 0; i < 6; i++) {
                    if (i == 6) {
                        String estado = String.valueOf(rs.getObject(i + 1));

                    } else {
                        fila[i] = rs.getObject(i + 1);
                    }
                }
                model.addRow(fila);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al llenar la tabla de compras: " + e);
        }
        //evento para obtener campo al cual el usuario da click
        //y obtener la interfaz que mostrara la informacion general
        jTable_compras.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila_point = jTable_compras.rowAtPoint(e.getPoint());
                int columna_point = 0;

                if (fila_point > -1) {
                    idCompra = (int) model.getValueAt(fila_point, columna_point);
                    EnviarDatosVentaSeleccionada(idCompra);//metodo
                }
            }
        });
    }
    int idArrayList;
    private void borrar(){
        DefaultTableModel model = new DefaultTableModel();
        interCompra.jTable_compras = new JTable(model);
            interCompra.jScrollPane1.setViewportView(interCompra.jTable_compras);
    jTable_compras.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
int fila_point = jTable_compras.rowAtPoint(e.getPoint());
        int columna_point = 0;
        if (fila_point > -1) {
//            idArrayList = (int) modeloDatosProductos.getValueAt(fila_point, columna_point);
        }
        int respuesta = JOptionPane.showConfirmDialog(null, "¿Estas seguro de que quieres eliminar " + cantidad
                + " unidad(es) de " + nombre + " del Carrito?", "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) { 
//presione si
            listaDetalle.remove(idArrayList - 1);
            listaTablaProductos();
           // this.calcularTotales();
        }
            }
        });
}


    /*
     * **************************************************
     * Metodo que envia datos seleccionados
     * **************************************************
     */
    private void EnviarDatosVentaSeleccionada(int idCompra) {
        try {
            Connection con = Conexion.conectar();
            PreparedStatement pst = con.prepareStatement(
                    "select cc.idCabeceraCompra, cc.idProveedor, concat(p.nombre, ' ', p.apellido) as proveedor, "
                    + "cc.valorPagar,cc.formaPago, cc.fechaCompra, cc.estado  from tb_cabecera_compra as cc, "
                    + "tb_proveedor as p where  cc.idCabeceraCompra = '" + idCompra + "' and cc.idProveedor = p.idProveedor;");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                interGestionarCompras.jComboBox_proveedor.setSelectedItem(rs.getString("proveedor"));
                interGestionarCompras.jComboBox_tipoPago.setSelectedItem(rs.getString("formaPago"));
                interGestionarCompras.jComboBox_estadoPago.setSelectedItem(rs.getString("estado"));
                interGestionarCompras.txt_total_pagar.setText(rs.getString("valorPagar"));
                interGestionarCompras.txt_fecha.setText(rs.getString("fechaCompra"));

            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al seleccionar Compra: " + e);
        }
    }

    /*
    Metodo para cargar los proveedorea en el jComboBox
     */
    private void CargarComboClientes() {
        Connection cn = Conexion.conectar();
        String sql = "select * from tb_proveedor";
        Statement st;
        try {
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            interGestionarCompras.jComboBox_proveedor.removeAllItems();
            interGestionarCompras.jComboBox_proveedor.addItem("Seleccione proveedor:");
            while (rs.next()) {
                interGestionarCompras.jComboBox_proveedor.addItem(rs.getString("nombre") + " " + rs.getString("apellido"));
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("¡Error al cargar proveedores, !" + e);
        }
    }
    
     public void ActualizarValorDolar() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

        

            @Override
            protected Void doInBackground() {

                try {
                    // String api = "https://bcv-api.rafnixg.dev/rates/";
                   String api = "https://ve.dolarapi.com/v1/dolares/oficial";
                    URL url = new URL(api);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("GET");

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);

                    }
                    in.close();

                    JSONObject obj = new JSONObject(response.toString());

                    ValorDolar = obj.getDouble("promedio");
                    resultado = "Dólar BCV: " + ValorDolar;

                } catch (Exception e) {
                    resultado = "Error: " + e.getMessage();
                }
                return null;
            }

            @Override
            protected void done() {
            
            }
        };
        worker.execute();
    }

}
