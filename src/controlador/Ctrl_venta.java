package controlador;

import conexion.Conexion;
import conexion.DAO_RegistrarVenta;
import conexion.VentaPDF;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
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
import modelo.CabeceraVenta;
import modelo.DetalleVenta;
import modelo.Dolar;
import org.json.JSONObject;
import vista.*;
import static vista.InterGestionarVentas.jTable_ventas;
import static vista.InterVentas.jTable_carrito;
import static vista.InterVentas.jTable_productos;

public class Ctrl_venta {

    //Modelo de los datos
    private DefaultTableModel modeloDatosProductos;
    //lista para el detalle de venta de los productos
    ArrayList<DetalleVenta> listaProductos = new ArrayList<>();
    private DetalleVenta producto;

    private int idCliente = 0;//id del cliente sleccionado

    private int idProducto = 0;
    private String nombre = "";
    private int cantidadProductoBBDD = 0;
    private double precioUnitario = 0.0;
    private int porcentajeIva = 0;

    private int cantidad = 0;//cantidad de productos a comprar
    private double subtotal = 0.0;//cantidad por precio
    private double descuento = 0.0;
    private double iva = 0.0;
    private double totalPagar = 0.0;

    //variables para calculos globales
    private double subtotalGeneral = 0.0;
    private double descuentoGeneral = 0.0;
    private double ivaGeneral = 0.0;
    private double totalPagarGeneral = 0.0;
    //fin de variables de calculos globales

    private int auxIdDetalle = 1;//id del detalle de venta
    private double ValorDolar;
    private double TotalDolares ;
    public String resultado = "";

    //  private int idCliente = 0;
    private int idVenta;

    InterVentas interVentas;
    InterGestionarVentas interGestionarVentas;
    Dolar dolar;

    public Ctrl_venta(InterVentas interVentas, InterGestionarVentas interGestionarVentas) {
        this.interVentas = interVentas;
        this.interGestionarVentas = interGestionarVentas;

        this.interGestionarVentas.jButton_actualizar.addActionListener(e -> Actualizar());
        this.interVentas.jButton_RegistrarVenta.addActionListener(e -> RegistrarVenta());
        this.interVentas.jButton_añadir_producto.addActionListener(e -> AñadirProducto());
        this.interVentas.jButton_busca_cliente.addActionListener(e -> BuscarCliente());
        this.interVentas.jButton_calcular_cambio.addActionListener(e -> CambioEfectivo());

        this.CargarComboClientes();
        this.inicializarTablaProducto();
        this.CargarTablaProductos();

        this.CargarComboClientes();
        this.CargarTablaVentas();
        this.ActualizarValorDolar();
    }
 

    public void RegistrarVenta() {

        CabeceraVenta cabeceraVenta = new CabeceraVenta();
        DetalleVenta detalleVenta = new DetalleVenta();
        DAO_RegistrarVenta controlVenta = new DAO_RegistrarVenta();

        String fechaActual = "";
        Date date = new Date();
        fechaActual = new SimpleDateFormat("yyyy/MM/dd").format(date);

        if (interVentas.jComboBox_cliente.getSelectedItem().equals("Seleccione cliente:")) {
             JOptionPane.showMessageDialog(null, "¡Seleccione un cliente!");
            return;
        }
               
            if (interVentas.jComboBox_tipoPago.getSelectedItem().equals("Seleccione tipo de pago")) {
                  JOptionPane.showMessageDialog(null, "¡Seleccione el tipo de pago!"); 
            return;
            }
    
                if (interVentas.jComboBox_tipoPago.getSelectedItem().equals("Seleccione estado del pago")) {
                    
                    JOptionPane.showMessageDialog(null, "¡Seleccione el estado del pago!"); 
                return;
                }
                    
                      String tipoPago = interVentas.jComboBox_tipoPago.getSelectedItem().toString().trim();
                      String estadoPago = interVentas.jComboBox_estadoPago.getSelectedItem().toString().trim();
                      
                if (listaProductos.size() > 0) {

                //metodo para obtener el id del cliente
                this.ObtenerIdCliente();
                //registrar cabecera
                cabeceraVenta.setIdCabeceraventa(0);
                cabeceraVenta.setIdCliente(idCliente);
                cabeceraVenta.setValorPagar(Double.parseDouble(interVentas.txt_total_pagar.getText()));
                cabeceraVenta.setTipoPago(tipoPago);
                cabeceraVenta.setFechaVenta(fechaActual);
                cabeceraVenta.setEstado(estadoPago);
                
                if (controlVenta.guardar(cabeceraVenta)) {
                    JOptionPane.showMessageDialog(null, "¡Venta Registrada!");
                    
                    VentaPDF pdf = new VentaPDF();
                    pdf.DatosCliente(idCliente);
                    pdf.generarFacturaPDF();

                    //guardar detalle
                    for (DetalleVenta elemento : listaProductos) {
                        detalleVenta.setIdDetalleVenta(0);
                        detalleVenta.setIdCabeceraVenta(0);
                        detalleVenta.setIdProducto(elemento.getIdProducto());
                        detalleVenta.setCantidad(elemento.getCantidad());
                        detalleVenta.setPrecioUnitario(elemento.getPrecioUnitario());
                        detalleVenta.setSubTotal(elemento.getSubTotal());
                        detalleVenta.setDescuento(elemento.getDescuento());
                        detalleVenta.setIva(elemento.getIva());
                        detalleVenta.setTotalPagar(elemento.getTotalPagar());
                        detalleVenta.setEstado(1);
                        
                        if (controlVenta.guardarDetalle(detalleVenta)) {
                            //System.out.println("Detalle de Venta Registrado");

                            interVentas.txt_subtotal.setText("0.0");
                            interVentas.txt_iva.setText("0.0");
                            interVentas.txt_descuento.setText("0.0");
                            interVentas.txt_total_pagar.setText("0.0");
                            interVentas.txt_efectivo.setText("");
                            interVentas.txt_cambio.setText("0.0");
                            auxIdDetalle = 1;
                            
                            this.RestarStockProductos(elemento.getIdProducto(), elemento.getCantidad());
                            
                        } else {
                            JOptionPane.showMessageDialog(null, "¡Error al guardar detalle de venta!");
                        }
                    }
                  
                                        //vaciamos la lista
                    listaProductos.clear();
                    listaTablaProductos();
                     this.CargarTablaProductos();
                    this.CargarComboClientes();
                    
                    
                    
                } else {
                    JOptionPane.showMessageDialog(null, "¡Error al guardar cabecera de venta!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "¡Seleccione un producto!");
            }
                
                
                  
          
            
         

    }

    public void AñadirProducto() {
        int filaSeleccionada = jTable_productos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showConfirmDialog(null, "Por Favor selecione un producto de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        nombreProducto = jTable_productos.getValueAt(filaSeleccionada, 1).toString();

        this.DatosDelProducto();
        String inputCantidad = JOptionPane.showInputDialog(null, "Ingresa la cantidad deseada para " + nombre + ":\nCantidad disponible:"
                + cantidadProductoBBDD, "Cantidad", JOptionPane.QUESTION_MESSAGE);

        if (inputCantidad == null || inputCantidad.trim().isEmpty()) {
            return;
        }

        try {
            cantidad = Integer.parseInt(inputCantidad);

            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(null, "La cantidad debe ser un numero mayor a cero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cantidad > cantidadProductoBBDD) {
                JOptionPane.showMessageDialog(null, "No hay suficiente stock. Cantidad disponible: " + cantidadProductoBBDD,
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            subtotal = cantidad * precioUnitario;
            totalPagar = subtotal * (1 + (iva / 100));

            producto = new DetalleVenta(auxIdDetalle,//idDetalleVenta
                    1, //idCabecera
                    idProducto,
                    nombre,
                    cantidad,
                    precioUnitario,
                    subtotal,
                    descuento,
                    iva,
                    totalPagar,
                    1//estado
            );
            listaProductos.add(producto);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por Favor ingrese un numero valido para la cantidad", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
        this.listaTablaProductos();
        this.CargarTablaProductos();
        this.CalcularTotalPagar();
        interVentas.txt_efectivo.setEnabled(true);
        interVentas.jButton_calcular_cambio.setEnabled(true);

    }

    public void BuscarCliente() {
        String clienteBuscar = interVentas.txt_cliente_buscar.getText().trim();
        Connection cn = Conexion.conectar();
        String sql = "select nombre, apellido from tb_cliente where cedula = '" + clienteBuscar + "'";
        Statement st;
        try {
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                interVentas.jComboBox_cliente.setSelectedItem(rs.getString("nombre") + " " + rs.getString("apellido"));
            } else {
                interVentas.jComboBox_cliente.setSelectedItem("Seleccione cliente:");
                JOptionPane.showMessageDialog(null, "¡Cedula de cliente incorrecta o no encontrada!");
            }
            interVentas.txt_cliente_buscar.setText("");
            cn.close();
        } catch (SQLException e) {
            System.out.println("¡Error al buscar cliente!, " + e);
        }
    }

    public void CambioEfectivo() {
        if (!interVentas.txt_efectivo.getText().isEmpty()) {
            //validamos que el usuario no ingrese otros caracteres no numericos 
            boolean validacion = validarDouble(interVentas.txt_efectivo.getText());
            if (validacion == true) {
                //validar que el efectivo sea mayor a cero
                double efc = Double.parseDouble(interVentas.txt_efectivo.getText().trim());
                double top = Double.parseDouble(interVentas.txt_total_pagar.getText().trim());

                if (efc < top) {
                    JOptionPane.showMessageDialog(null, "El Dinero en efectivo no es suficiente");
                } else {
                    double cambio = (efc - top);
                    double cambi = (double) Math.round(cambio * 100d) / 100;
                    String camb = String.valueOf(cambi);
                    interVentas.txt_cambio.setText(camb);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No de admiten caracteres no numericos");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese dinero en efectivo para calcular cambio");
        }
    }

    public void Actualizar() {

        CabeceraVenta cabeceraVenta = new CabeceraVenta();
        DAO_RegistrarVenta DAORegistrarVenta = new DAO_RegistrarVenta();
        String cliente, formaPago, estado;
        cliente = interGestionarVentas.jComboBox_cliente.getSelectedItem().toString().trim();
        formaPago = interGestionarVentas.jComboBox_tipoPago.getSelectedItem().toString().trim();
        estado = interGestionarVentas.jComboBox_estadoPago.getSelectedItem().toString().trim();

        //obtener el id del cliente
        try {
            Connection cn = Conexion.conectar();
            PreparedStatement pst = cn.prepareStatement(
                    "select idCliente, concat(nombre, ' ', apellido) as cliente "
                    + "from tb_cliente where concat(nombre, ' ', apellido) = '" + cliente + "'");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                idCliente = rs.getInt("idCliente");
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error en cargar el id cliente: " + e);
        }

        //Actualizar datos
        if (!cliente.equalsIgnoreCase("Seleccione cliente:")) {
            
              JOptionPane.showMessageDialog(null, "Seleccione un registro para actualizar datos");
              return;
        }
            cabeceraVenta.setIdCliente(idCliente);
              if (!formaPago.equalsIgnoreCase("Seleccione tipo de pago:")) {
                  cabeceraVenta.setTipoPago(formaPago);
                  if (estado.equalsIgnoreCase("Pagado")) {
                cabeceraVenta.setEstado("Pagado");
            } else if (estado.equalsIgnoreCase("Pendiente")){
                cabeceraVenta.setEstado("Pendiente");
            }

            if (DAORegistrarVenta.actualizar(cabeceraVenta, idVenta)) {
                JOptionPane.showMessageDialog(null, "¡Registro Actualizado!");
                this.CargarTablaVentas();
                this.Limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Actualizar");
            }
              } else {
                  
              }
            cabeceraVenta.setIdCliente(idCliente);
            if (estado.equalsIgnoreCase("Pagado")) {
                cabeceraVenta.setEstado("Pagado");
            } else {
                cabeceraVenta.setEstado("Pendiente");
            }

            if (DAORegistrarVenta.actualizar(cabeceraVenta, idVenta)) {
                JOptionPane.showMessageDialog(null, "¡Registro Actualizado!");
                this.CargarTablaVentas();
                this.Limpiar();
            } else {
                JOptionPane.showMessageDialog(null, "Error al Actualizar");
            }
       
    }
    
    /*****************************************************************************
     *              METODOS DEL REGISTRO DE VENTA
     ****************************************************************************/

    //metodo para inicializar la tabla de los productos
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
        this.interVentas.jTable_carrito.setModel(modeloDatosProductos);
    }

    //metodo para presentar la informacion de la tavla DetalleVenta
    private void listaTablaProductos() {
        this.modeloDatosProductos.setRowCount(listaProductos.size());
        for (int i = 0; i < listaProductos.size(); i++) {
            this.modeloDatosProductos.setValueAt(i + 1, i, 0);
            this.modeloDatosProductos.setValueAt(listaProductos.get(i).getNombre(), i, 1);
            this.modeloDatosProductos.setValueAt(listaProductos.get(i).getCantidad(), i, 2);
            this.modeloDatosProductos.setValueAt(listaProductos.get(i).getPrecioUnitario(), i, 3);
            this.modeloDatosProductos.setValueAt(listaProductos.get(i).getSubTotal(), i, 4);
            this.modeloDatosProductos.setValueAt(listaProductos.get(i).getDescuento(), i, 5);
            this.modeloDatosProductos.setValueAt(listaProductos.get(i).getIva(), i, 6);
            this.modeloDatosProductos.setValueAt(listaProductos.get(i).getTotalPagar(), i, 7);
            this.modeloDatosProductos.setValueAt("Eliminar", i, 8);//aqui luego poner un boton de eliminar
        }
        //añadir al Jtable
        jTable_carrito.setModel(modeloDatosProductos);
    }

    /*
    Metodo para cargar los clientes en el jComboBox
     */
    private void CargarComboClientes() {
        Connection cn = Conexion.conectar();
        String sql = "select * from tb_cliente";
        Statement st;
        try {
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            interVentas.jComboBox_cliente.removeAllItems();
            interVentas.jComboBox_cliente.addItem("Seleccione cliente:");
            while (rs.next()) {
                interVentas.jComboBox_cliente.addItem(rs.getString("nombre") + " " + rs.getString("apellido"));
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("¡Error al cargar clientes, !" + e);
        }
    }


    /*
        Metodo para validar que el usuario no ingrese caracteres no numericos
     */
    private boolean validar(String valor) {
        try {
            int num = Integer.parseInt(valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /*
        Metodo para validar que el usuario no ingrese caracteres no numericos
     */
    private boolean validarDouble(String valor) {
        try {
            double num = Double.parseDouble(valor);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    /*
        Metodo para calcular iva
     */
    private double CalcularIva(double precio, int porcentajeIva) {
        int p_iva = porcentajeIva;

        switch (p_iva) {
            case 0:
                iva = 0.0;
                break;
            case 12:
                iva = (precio * cantidad) * 0.12;
                break;
            case 14:
                iva = (precio * cantidad) * 0.14;
                break;
            default:
                break;
        }

        return iva;
    }

    /*
    Metodo para calcular el total a pagar de todos los productos agregados
     */
    private void CalcularTotalPagar() {
        /*
        totalPagarGeneral= subtotalGeneral + ivaGeneral - descuentoGeneral*/
     
       
        for (DetalleVenta elemento : listaProductos) {
            subtotalGeneral += elemento.getSubTotal();
            descuentoGeneral += elemento.getDescuento();
            ivaGeneral += elemento.getIva();
            totalPagarGeneral += elemento.getTotalPagar();
        }
        //redondear decimales
        subtotalGeneral = (double) Math.round(subtotalGeneral * 100) / 100;
        ivaGeneral = (double) Math.round(ivaGeneral * 100) / 100;
        descuentoGeneral = (double) Math.round(descuentoGeneral * 100) / 100;
        totalPagarGeneral = (double) Math.round(totalPagarGeneral * 100) / 100;
      
        TotalDolares = totalPagarGeneral * ValorDolar;
        TotalDolares = (double) Math.round(TotalDolares * 100) / 100;

        //enviar datos a la vista
        interVentas.txt_subtotal.setText(String.valueOf(subtotalGeneral));
        interVentas.txt_iva.setText(String.valueOf(ivaGeneral));
        interVentas.txt_descuento.setText(String.valueOf(descuentoGeneral));
        interVentas.txt_total_pagar.setText(String.valueOf(totalPagarGeneral));
      
        
    }

    /*
    Metodo para obtener id del cliente
     */
    private void ObtenerIdCliente() {
        try {
            String sql = "select * from tb_cliente where concat(nombre,' ',apellido) = '" + this.interVentas.jComboBox_cliente.getSelectedItem() + "'";
            Connection cn = Conexion.conectar();
            Statement st;
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                idCliente = rs.getInt("idCliente");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener id del cliente, " + e);
        }
    }

    //metodo para restar la cantidad (stock) de los productos vendidos
    private void RestarStockProductos(int idProducto, int cantidad) {
        int cantidadProductosBaseDeDatos = 0;
        try {
            Connection cn = Conexion.conectar();
            String sql = "select idProducto, cantidad from tb_producto where idProducto = '" + idProducto + "'";
            Statement st;
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                cantidadProductosBaseDeDatos = rs.getInt("cantidad");
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al restar cantidad 1, " + e);
        }

        try {
            Connection cn = Conexion.conectar();
            PreparedStatement consulta = cn.prepareStatement("update tb_producto set cantidad=? where idProducto = '" + idProducto + "'");
            int cantidadNueva = cantidadProductosBaseDeDatos - cantidad;
            consulta.setInt(1, cantidadNueva);
            if (consulta.executeUpdate() > 0) {
                System.out.println("Todo bien");
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al restar cantidad 2, " + e);
        }
    }

    double IVA = 0;
    double precio = 0.0;

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

    // METODO PARA CARGAR LA TALA DE PRODUCTOS EN EL REGISTRO DE VENTA
    private void CargarTablaProductos() {
        Connection con = Conexion.conectar();
        DefaultTableModel model = new DefaultTableModel();
        String sql = "select p.idProducto, p.nombre, p.cantidad, p.precio, p.descripcion, p.porcentajeIva, c.descripcion, p.estado from tb_producto As p, tb_categoria As c where p.idCategoria = c.idCategoria and p.estado = 1;";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            InterVentas.jTable_productos = new JTable(model);
            InterVentas.jScrollPane1.setViewportView(InterVentas.jTable_productos);
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

    }

   
    String nombreProducto;
    
    // METODO PARA OBTENER LOS DATOS DEL PRODUCTO

    private void DatosDelProducto() {
        try {
            String sql = "select * from tb_producto where nombre = '" + this.nombreProducto + "'";
            Connection cn = Conexion.conectar();
            Statement st;
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                idProducto = rs.getInt("idProducto");
                nombre = rs.getString("nombre");
                cantidadProductoBBDD = rs.getInt("cantidad");
                precioUnitario = rs.getDouble("precio");
                porcentajeIva = rs.getInt("porcentajeIva");
                this.CalcularIva(precioUnitario, porcentajeIva);//calcula y retorna el iva
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener datos del producto, " + e);
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
                interVentas.lbldolar.setText(resultado);
            }
        };
        worker.execute();
    }
    
    /*************************************************************************************++
     * 
     * METODOS PARA GESTIONAR LAS VENTAS
     * 
     ***************************************************************************************/

    /*
     * *****************************************************
     * metodo para limpiar
     * *****************************************************
     */
    private void Limpiar() {
        interGestionarVentas.txt_total_pagar.setText("");
        interGestionarVentas.txt_fecha.setText("");
        interGestionarVentas.jComboBox_cliente.setSelectedItem("Seleccione cliente:");
        interGestionarVentas.jComboBox_estadoPago.setSelectedItem("Pagado");
        idCliente = 0;
    }


    /*
     * *****************************************************
     * metodo para mostrar todos las ventas registradas
     * *****************************************************
     */
   private void CargarTablaVentas() {
        Connection con = Conexion.conectar();
        DefaultTableModel model = new DefaultTableModel();
        String sql = "select cv.idCabeceraVenta as id, concat(c.nombre, ' ', c.apellido) as cliente, "
                + "cv.valorPagar as total,cv.formaPago as forma_pago, cv.fechaVenta as fecha, cv.estado "
                + "from tb_cabecera_venta as cv, tb_cliente as c where cv.idCliente = c.idCliente;";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            InterGestionarVentas.jTable_ventas = new JTable(model);
            InterGestionarVentas.jScrollPane1.setViewportView(InterGestionarVentas.jTable_ventas);

            model.addColumn("N°");//ID
            model.addColumn("Cliente");
            model.addColumn("Total Pagar");
            model.addColumn("Forma Pago");
            model.addColumn("Fecha Venta");
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
            System.out.println("Error al llenar la tabla de ventas: " + e);
        }
        //evento para obtener campo al cual el usuario da click
        //y obtener la interfaz que mostrara la informacion general
        jTable_ventas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila_point = jTable_ventas.rowAtPoint(e.getPoint());
                int columna_point = 0;

                if (fila_point > -1) {
                    idVenta = (int) model.getValueAt(fila_point, columna_point);
                    EnviarDatosVentaSeleccionada(idVenta);//metodo
                }
            }
        });
    }


    /*
     * **************************************************
     * Metodo que envia datos seleccionados
     * **************************************************
     */
    private void EnviarDatosVentaSeleccionada(int idVenta) {
        try {
            Connection con = Conexion.conectar();
            PreparedStatement pst = con.prepareStatement(
                    "select cv.idCabeceraVenta, cv.idCliente, concat(c.nombre, ' ', c.apellido) as cliente, "
                    + "cv.valorPagar,cv.formaPago, cv.fechaVenta, cv.estado  from tb_cabecera_venta as cv, "
                    + "tb_cliente as c where  cv.idCabeceraVenta = '" + idVenta + "' and cv.idCliente = c.idCliente;");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                interGestionarVentas.jComboBox_cliente.setSelectedItem(rs.getString("cliente"));
                interGestionarVentas.jComboBox_tipoPago.setSelectedItem(rs.getString("formaPago"));
                interGestionarVentas.jComboBox_estadoPago.setSelectedItem(rs.getString("estado"));
                interGestionarVentas.txt_total_pagar.setText(rs.getString("valorPagar"));
                interGestionarVentas.txt_fecha.setText(rs.getString("fechaVenta"));
                interGestionarVentas.jComboBox_cliente.setSelectedItem(rs.getString("cliente"));
          
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al seleccionar venta: " + e);
        }
    }

    /*
    Metodo para cargar los clientes en el jComboBox
     */
    private void CargarComboClientes2() {
        Connection cn = Conexion.conectar();
        String sql = "select * from tb_cliente";
        Statement st;
        try {
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            interGestionarVentas.jComboBox_cliente.removeAllItems();
            interGestionarVentas.jComboBox_cliente.addItem("Seleccione cliente:");
            while (rs.next()) {
                interGestionarVentas.jComboBox_cliente.addItem(rs.getString("nombre") + " " + rs.getString("apellido"));
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("¡Error al cargar clientes, !" + e);
        }
    }

}
