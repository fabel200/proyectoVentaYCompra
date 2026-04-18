/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import modelo.CierreCaja;
import conexion.Conexion;
import conexion.DAO_CierreCaja;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Usuario;
import vista.InterCierreCaja1;

public class Ctrl_Cierrecaja1 {
    InterCierreCaja1 interCierreCaja;
    private int usuarioId;
    double total;
    
    String fechaActual = "";
        Date date = new Date();

    public Ctrl_Cierrecaja1(InterCierreCaja1 interCierreCaja ) {
        this.interCierreCaja = interCierreCaja;
        
        // Establecer fecha actual
         fechaActual = new SimpleDateFormat("yyyy/MM/dd").format(date);
       // String fechaActual = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        this.interCierreCaja.txtFechaActual.setText(fechaActual);
        
        this.interCierreCaja.jButton_Calcular.addActionListener(e ->  calcularAutomatico());
        this.interCierreCaja.jButton_limpiar.addActionListener(e ->  limpiarCampos());
        this.interCierreCaja.jButton_registrarCierre.addActionListener(e -> registrarCierre());
        this.interCierreCaja.jButton_verHistorial.addActionListener(e -> verHistorial());
        
       // Listeners para actualizar totales en tiempo real
        this.agregarListenersCamposNumericos();
        this.CargarTablaTarjeta();
        this.CargarTablaEfectivo();
        this.CargarTablaPagoMovil();
        this.CargarTablaTrnasferencia();
    }
    
    private void calcular(String formaPago) {
        try {
            Connection con = Conexion.conectar();
            PreparedStatement pst = con.prepareStatement(
                    "SELECT COALESCE(SUM(valorPagar), 0) as total FROM tb_cabecera_venta " +
                "WHERE fechaVenta = CURDATE() AND formaPago = '"+ formaPago+"' AND estado = 'Pagado'");
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
               total = rs.getDouble("total");
            
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al seleccionar usuario: " + e);
        }
    
    }
    
    
    private void calcularAutomatico() {
        CierreCaja cierre = new CierreCaja();
        try {
            calcular("Tarjeta");
            cierre.setTotalTarjeta(BigDecimal.valueOf(total));
            calcular("Efectivo");
            cierre.setTotalEfectivo(BigDecimal.valueOf(total));
            calcular("Pago Movil");
            cierre.setTotalPagoMovil(BigDecimal.valueOf(total));
            calcular("Transferencia");
            cierre.setTotalTransferencia(BigDecimal.valueOf(total));
            
            interCierreCaja.txtTotalTarjeta.setText(String.valueOf(cierre.getTotalTarjeta()));
            interCierreCaja.txtTotalEfectivo.setText(String.valueOf(cierre.getTotalEfectivo()));
            
            interCierreCaja.txtTotalPagoMovil.setText(String.valueOf(cierre.getTotalPagoMovil()));
            interCierreCaja.txtTotalTransferencia.setText(String.valueOf(cierre.getTotalTransferencia()));
            
            actualizarTotales();
             JOptionPane.showMessageDialog(null,  "Totales calculados automáticamente desde las ventas del día.", "Cálculo Automático", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,  "Error al calcular automáticamente: " + e.getMessage(),  "Error",  JOptionPane.ERROR_MESSAGE);
            System.out.println("Error:  " + e.getMessage());
        }
    }
    
    private void registrarCierre() {
        
        CierreCaja cierre = new CierreCaja();
     DAO_CierreCaja cierreCajaDAO = new DAO_CierreCaja();
        try {
            // Validar campos obligatorios
            if (interCierreCaja.txtFechaActual.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "La fecha es obligatoria.", "Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Obtener y validar valores
            BigDecimal efectivo = validarCampoNumerico(interCierreCaja.txtTotalEfectivo.getText());
            BigDecimal tarjeta = validarCampoNumerico(interCierreCaja.txtTotalTarjeta.getText());
            BigDecimal pagoMovil = validarCampoNumerico(interCierreCaja.txtTotalPagoMovil.getText());
            BigDecimal transferencia = validarCampoNumerico(interCierreCaja.txtTotalTransferencia.getText());
            BigDecimal dolares = validarCampoNumerico(interCierreCaja.txtTotalDolares.getText());
            BigDecimal bolivares = validarCampoNumerico(interCierreCaja.txtTotalBs.getText());
            
            // Crear objeto CierreCaja
            
            cierre.setFecha(interCierreCaja.txtFechaActual.getText());
            cierre.setUsuario(usuarioId);
            cierre.setTotalEfectivo(efectivo);
            cierre.setTotalTarjeta(tarjeta);
            cierre.setTotalPagoMovil(pagoMovil);
            cierre.setTotalTransferencia(transferencia);
            cierre.setTotalDolares(dolares);
            cierre.setTotalBs(bolivares);
            cierre.setCreadoEn(fechaActual);
            
            
            // Validar que no exista cierre para la misma fecha
            if (!cierreCajaDAO.existeCierreParaFecha()){
                 if (cierreCajaDAO.registrarCierreCaja(cierre)) {
                                JOptionPane.showMessageDialog(null, "Registro Guardado");
                                limpiarCampos();

                 }else{JOptionPane.showMessageDialog(null, "Error al Guardar");
                     
                 }}else {JOptionPane.showMessageDialog(null, "Ya existe un cierre de caja para esta fecha.", "Error", JOptionPane.ERROR_MESSAGE);}
           
          
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,  "Error al registrar cierre: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarCampos() {
        interCierreCaja.txtTotalEfectivo.setText("0.00");
        interCierreCaja.txtTotalTarjeta.setText("0.00");
        interCierreCaja.txtTotalPagoMovil.setText("0.00");
        interCierreCaja.txtTotalTransferencia.setText("0.00");
        interCierreCaja.txtTotalDolares.setText("0.00");
        interCierreCaja.txtTotalBs.setText("0.00");
        actualizarTotales();
    }
    
    private void verHistorial() {
        // Aquí puedes implementar la vista del historial
        JOptionPane.showMessageDialog(null, "Funcionalidad de historial en desarrollo.", "Información",JOptionPane.INFORMATION_MESSAGE);
    }
private void CargarTablaTarjeta() {
         String formaPago = "Tarjeta";
        Connection con = Conexion.conectar();
        DefaultTableModel model = new DefaultTableModel();
        String sql = "select cv.idCabeceraVenta as id, concat(c.nombre, ' ', c.apellido) as cliente, "
                + "cv.valorPagar as total,cv.formaPago as forma_pago, cv.fechaVenta as fecha, cv.estado "
                + "from tb_cabecera_venta as cv, tb_cliente as c where cv.idCliente = c.idCliente and date(cv.fechaVenta) = curdate() and cv.formaPago = '"+formaPago+"';";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            interCierreCaja.jTable_Tarjeta = new JTable(model);
            interCierreCaja.jScrollPane1.setViewportView(interCierreCaja.jTable_Tarjeta);

            model.addColumn("N°");//ID
            model.addColumn("Cliente");
            model.addColumn("Total Pagar");
            model.addColumn("Forma Pago");
            model.addColumn("Fecha Venta");
            model.addColumn("estado");

            while (rs.next()) {
               Object fila[] = new Object[6];
                for (int i = 0; i < 6; i++) {
              
                        fila[i] = rs.getObject(i + 1);
                    
                }
                model.addRow(fila);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al llenar la tabla de ventas: " + e);
        }
     }    
private void CargarTablaEfectivo() {
         String formaPago = "Efectivo";
        Connection con = Conexion.conectar();
        DefaultTableModel model = new DefaultTableModel();
        String sql = "select cv.idCabeceraVenta as id, concat(c.nombre, ' ', c.apellido) as cliente, "
                + "cv.valorPagar as total,cv.formaPago as forma_pago, cv.fechaVenta as fecha, cv.estado "
                + "from tb_cabecera_venta as cv, tb_cliente as c where cv.idCliente = c.idCliente and date(cv.fechaVenta) = curdate() and cv.formaPago = '"+formaPago+"';";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            interCierreCaja.jTable_Efectivo = new JTable(model);
            interCierreCaja.jScrollPane2.setViewportView(interCierreCaja.jTable_Efectivo);

            model.addColumn("N°");//ID
            model.addColumn("Cliente");
            model.addColumn("Total Pagar");
            model.addColumn("Forma Pago");
            model.addColumn("Fecha Venta");
            model.addColumn("estado");

            while (rs.next()) {
               Object fila[] = new Object[6];
                for (int i = 0; i < 6; i++) {
              
                        fila[i] = rs.getObject(i + 1);
                    
                }
                model.addRow(fila);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al llenar la tabla de ventas: " + e);
        }
     }    
private void CargarTablaPagoMovil() {
         String formaPago = "Pago Movil";
        Connection con = Conexion.conectar();
        DefaultTableModel model = new DefaultTableModel();
        String sql = "select cv.idCabeceraVenta as id, concat(c.nombre, ' ', c.apellido) as cliente, "
                + "cv.valorPagar as total,cv.formaPago as forma_pago, cv.fechaVenta as fecha, cv.estado "
                + "from tb_cabecera_venta as cv, tb_cliente as c where cv.idCliente = c.idCliente and date(cv.fechaVenta) = curdate() and cv.formaPago = '"+formaPago+"';";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            interCierreCaja.jTable_PagoMovil = new JTable(model);
            interCierreCaja.jScrollPane3.setViewportView(interCierreCaja.jTable_PagoMovil);

            model.addColumn("N°");//ID
            model.addColumn("Cliente");
            model.addColumn("Total Pagar");
            model.addColumn("Forma Pago");
            model.addColumn("Fecha Venta");
            model.addColumn("estado");

            while (rs.next()) {
               Object fila[] = new Object[6];
                for (int i = 0; i < 6; i++) {
              
                        fila[i] = rs.getObject(i + 1);
                    
                }
                model.addRow(fila);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al llenar la tabla de ventas: " + e);
        }
     }    
    
     private void CargarTablaTrnasferencia() {
         String formaPago = "Transferencia";
        Connection con = Conexion.conectar();
        DefaultTableModel model = new DefaultTableModel();
        String sql = "select cv.idCabeceraVenta as id, concat(c.nombre, ' ', c.apellido) as cliente, "
                + "cv.valorPagar as total,cv.formaPago as forma_pago, cv.fechaVenta as fecha, cv.estado "
                + "from tb_cabecera_venta as cv, tb_cliente as c where cv.idCliente = c.idCliente and date(cv.fechaVenta) = curdate() and cv.formaPago = '"+formaPago+"';";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            interCierreCaja.jTable_Transferencia = new JTable(model);
            interCierreCaja.jScrollPane4.setViewportView(interCierreCaja.jTable_Transferencia);

            model.addColumn("N°");//ID
            model.addColumn("Cliente");
            model.addColumn("Total Pagar");
            model.addColumn("Forma Pago");
            model.addColumn("Fecha Venta");
            model.addColumn("estado");

            while (rs.next()) {
               Object fila[] = new Object[6];
                for (int i = 0; i < 6; i++) {
              
                        fila[i] = rs.getObject(i + 1);
                    
                }
                model.addRow(fila);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al llenar la tabla de ventas: " + e);
        }
     }    
     
     
     
    private void actualizarTotales() {
        try {
            BigDecimal efectivo = validarCampoNumerico(interCierreCaja.txtTotalEfectivo.getText());
            BigDecimal tarjeta = validarCampoNumerico(interCierreCaja.txtTotalTarjeta.getText());
            BigDecimal pagoMovil = validarCampoNumerico(interCierreCaja.txtTotalPagoMovil.getText());
            BigDecimal transferencia = validarCampoNumerico(interCierreCaja.txtTotalTransferencia.getText());
            BigDecimal dolares = validarCampoNumerico(interCierreCaja.txtTotalDolares.getText());
            BigDecimal bolivares = validarCampoNumerico(interCierreCaja.txtTotalBs.getText());
            
            BigDecimal totalVentas = efectivo.add(tarjeta).add(pagoMovil).add(transferencia);
            BigDecimal totalGeneral = totalVentas.add(dolares).add(bolivares);
            
            interCierreCaja.txtTotalVentas.setText(String.format("%.2f Bs", totalVentas));
            interCierreCaja.txtTotalGeneral.setText(String.format("%.2f Bs", totalGeneral));
            
        } catch (Exception e) {
            interCierreCaja.txtTotalVentas.setText("Error");
            interCierreCaja.txtTotalGeneral.setText("Error");
        }
    }
    
    private void agregarListenersCamposNumericos() {
        // Agregar listeners para actualizar totales en tiempo real
        javax.swing.event.DocumentListener docListener = new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { actualizarTotales(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { actualizarTotales(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { actualizarTotales(); }
        };
        
        interCierreCaja.txtTotalEfectivo.getDocument().addDocumentListener(docListener);
        interCierreCaja.txtTotalTarjeta.getDocument().addDocumentListener(docListener);
        interCierreCaja.txtTotalPagoMovil.getDocument().addDocumentListener(docListener);
        interCierreCaja.txtTotalTransferencia.getDocument().addDocumentListener(docListener);
        interCierreCaja.txtTotalDolares.getDocument().addDocumentListener(docListener);
        interCierreCaja.txtTotalBs.getDocument().addDocumentListener(docListener);
    }
    
    private BigDecimal validarCampoNumerico(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(texto.trim());
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }
    
  
    
}