package vista;

import conexion.Conexion;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.CabeceraVenta;
import modelo.DetalleVenta;
import static vista.InterGestionarProducto.jTable_productos;

public class InterVentas extends javax.swing.JInternalFrame {

    public InterVentas() {
        initComponents();
        this.setSize(new Dimension(1150, 720));
        this.setTitle("Ventas");

        //insertar imagen en nuestro JLabel
        ImageIcon wallpaper = new ImageIcon("src/img/fondo3.jpg");
        Icon icono = new ImageIcon(wallpaper.getImage().getScaledInstance(1150, 720, WIDTH));
        jLabel_wallpaper.setIcon(icono);
        this.repaint();
        
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox_cliente = new javax.swing.JComboBox<>();
        txt_cliente_buscar = new javax.swing.JTextField();
        jButton_busca_cliente = new javax.swing.JButton();
        jButton_añadir_producto = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_carrito = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_subtotal = new javax.swing.JTextField();
        txt_descuento = new javax.swing.JTextField();
        txt_iva = new javax.swing.JTextField();
        txt_total_pagar = new javax.swing.JTextField();
        txt_efectivo = new javax.swing.JTextField();
        txt_cambio = new javax.swing.JTextField();
        jButton_calcular_cambio = new javax.swing.JButton();
        jComboBox_tipoPago = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        lblTotalDolar = new javax.swing.JLabel();
        jComboBox_estadoPago = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        lbldolar = new javax.swing.JLabel();
        jButton_RegistrarVenta = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_productos = new javax.swing.JTable();
        jLabel_wallpaper = new javax.swing.JLabel();

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Producto:");

        setClosable(true);
        setIconifiable(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Ventas");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 20, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Cliente:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 80, 80, -1));

        jComboBox_cliente.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox_cliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione cliente:", "Item 2", "Item 3", "Item 4" }));
        getContentPane().add(jComboBox_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 80, 170, -1));

        txt_cliente_buscar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        getContentPane().add(txt_cliente_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 80, 150, -1));

        jButton_busca_cliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_busca_cliente.setText("Buscar");
        jButton_busca_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_busca_clienteActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_busca_cliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 80, 80, -1));

        jButton_añadir_producto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_añadir_producto.setText("Añadir Productos");
        jButton_añadir_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_añadir_productoActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_añadir_producto, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 360, 150, -1));

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable_carrito.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable_carrito.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_carritoMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable_carrito);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 11, 510, 190));

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 120, 540, 210));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Subtotal:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Descuento:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Iva:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("Total a pagar:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Efectivo:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setText("Cambio:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        txt_subtotal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_subtotal.setEnabled(false);
        jPanel2.add(txt_subtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 120, -1));

        txt_descuento.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_descuento.setEnabled(false);
        jPanel2.add(txt_descuento, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, 120, -1));

        txt_iva.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_iva.setEnabled(false);
        jPanel2.add(txt_iva, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 120, -1));

        txt_total_pagar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_total_pagar.setEnabled(false);
        jPanel2.add(txt_total_pagar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 120, -1));

        txt_efectivo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel2.add(txt_efectivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 230, 120, -1));

        txt_cambio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txt_cambio.setEnabled(false);
        jPanel2.add(txt_cambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 260, 120, -1));

        jButton_calcular_cambio.setBackground(new java.awt.Color(51, 255, 255));
        jButton_calcular_cambio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_calcular_cambio.setText("Calcular Cambio");
        jButton_calcular_cambio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_calcular_cambioActionPerformed(evt);
            }
        });
        jPanel2.add(jButton_calcular_cambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 220, 130, 50));

        jComboBox_tipoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione tipo de pago", "Tarjeta", "Efectivo", "Pago Movil ", "Transferencia" }));
        jPanel2.add(jComboBox_tipoPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 30, 170, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel20.setText("Estado:");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, -1, -1));

        lblTotalDolar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel2.add(lblTotalDolar, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 180, 130, 30));

        jComboBox_estadoPago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione estado del pago", "Pagado", "Pendiente" }));
        jPanel2.add(jComboBox_estadoPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 190, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("Tipo de pago:");
        jPanel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        lbldolar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jPanel2.add(lbldolar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 120, 130, 30));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 340, 460, 300));

        jButton_RegistrarVenta.setBackground(new java.awt.Color(51, 255, 255));
        jButton_RegistrarVenta.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton_RegistrarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/impresora.png"))); // NOI18N
        jButton_RegistrarVenta.setText("Registrar Venta");
        jButton_RegistrarVenta.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton_RegistrarVenta.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton_RegistrarVenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_RegistrarVentaActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_RegistrarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 350, 170, 100));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable_productos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable_productos);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 500, 190));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 530, 210));
        getContentPane().add(jLabel_wallpaper, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1160, 670));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton_busca_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_busca_clienteActionPerformed
     
    }//GEN-LAST:event_jButton_busca_clienteActionPerformed

    private void jButton_calcular_cambioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_calcular_cambioActionPerformed

    }//GEN-LAST:event_jButton_calcular_cambioActionPerformed
    int idArrayList = 0;
    private void jButton_RegistrarVentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_RegistrarVentaActionPerformed

    }//GEN-LAST:event_jButton_RegistrarVentaActionPerformed

    private void jButton_añadir_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_añadir_productoActionPerformed

    }//GEN-LAST:event_jButton_añadir_productoActionPerformed

    private void jTable_carritoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_carritoMouseClicked
       /* int fila_point = jTable_carrito.rowAtPoint(evt.getPoint());
        int columna_point = 0;
        if (fila_point > -1) {
            idArrayList = (int) modeloDatosProductos.getValueAt(fila_point, columna_point);
        }
        int respuesta =JOptionPane.showConfirmDialog(this,"¿Estas seguro de que quieres eliminar "+cantidad+
              " unidad(es) de "+nombre+" del Carrito?", "Confirmar Eliminacion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
      
        if (respuesta == JOptionPane.YES_OPTION) { //presione si
                listaProductos.remove(idArrayList - 1);
               this.listaTablaProductos();
              
               this.CalcularTotalPagar();
        }
        */
    }//GEN-LAST:event_jTable_carritoMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton jButton_RegistrarVenta;
    public javax.swing.JButton jButton_añadir_producto;
    public javax.swing.JButton jButton_busca_cliente;
    public javax.swing.JButton jButton_calcular_cambio;
    public javax.swing.JComboBox<String> jComboBox_cliente;
    public javax.swing.JComboBox<String> jComboBox_estadoPago;
    public javax.swing.JComboBox<String> jComboBox_tipoPago;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_wallpaper;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    public static javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JTable jTable_carrito;
    public static javax.swing.JTable jTable_productos;
    public javax.swing.JLabel lblTotalDolar;
    public javax.swing.JLabel lbldolar;
    public javax.swing.JTextField txt_cambio;
    public javax.swing.JTextField txt_cliente_buscar;
    public javax.swing.JTextField txt_descuento;
    public javax.swing.JTextField txt_efectivo;
    public javax.swing.JTextField txt_iva;
    public javax.swing.JTextField txt_subtotal;
    public static javax.swing.JTextField txt_total_pagar;
    // End of variables declaration//GEN-END:variables

    public JButton getjButton_RegistrarVenta() {
        return jButton_RegistrarVenta;
    }

    public void setjButton_RegistrarVenta(JButton jButton_RegistrarVenta) {
        this.jButton_RegistrarVenta = jButton_RegistrarVenta;
    }

    public JButton getjButton_añadir_producto() {
        return jButton_añadir_producto;
    }

    public void setjButton_añadir_producto(JButton jButton_añadir_producto) {
        this.jButton_añadir_producto = jButton_añadir_producto;
    }

    public JButton getjButton_busca_cliente() {
        return jButton_busca_cliente;
    }

    public void setjButton_busca_cliente(JButton jButton_busca_cliente) {
        this.jButton_busca_cliente = jButton_busca_cliente;
    }

    public JButton getjButton_calcular_cambio() {
        return jButton_calcular_cambio;
    }

    public void setjButton_calcular_cambio(JButton jButton_calcular_cambio) {
        this.jButton_calcular_cambio = jButton_calcular_cambio;
    }


    public JComboBox<String> getjComboBox_cliente() {
        return jComboBox_cliente;
    }

    public void setjComboBox_cliente(JComboBox<String> jComboBox_cliente) {
        this.jComboBox_cliente = jComboBox_cliente;
    }

    public JComboBox<String> getjComboBox_estadoPago() {
        return jComboBox_estadoPago;
    }

    public void setjComboBox_estadoPago(JComboBox<String> jComboBox_estadoPago) {
        this.jComboBox_estadoPago = jComboBox_estadoPago;
    }

    public JComboBox<String> getjComboBox_tipoPago() {
        return jComboBox_tipoPago;
    }

    public void setjComboBox_tipoPago(JComboBox<String> jComboBox_tipoPago) {
        this.jComboBox_tipoPago = jComboBox_tipoPago;
    }

    public static JScrollPane getjScrollPane1() {
        return jScrollPane1;
    }

    public static void setjScrollPane1(JScrollPane jScrollPane1) {
        InterVentas.jScrollPane1 = jScrollPane1;
    }

    public JScrollPane getjScrollPane2() {
        return jScrollPane2;
    }

    public void setjScrollPane2(JScrollPane jScrollPane2) {
        this.jScrollPane2 = jScrollPane2;
    }

    public static JTable getjTable_carrito() {
        return jTable_carrito;
    }

    public static void setjTable_carrito(JTable jTable_carrito) {
        InterVentas.jTable_carrito = jTable_carrito;
    }

    public static JTable getjTable_productos() {
        return jTable_productos;
    }

    public static void setjTable_productos(JTable jTable_productos) {
        InterVentas.jTable_productos = jTable_productos;
    }

    public JTextField getTxt_cambio() {
        return txt_cambio;
    }

    public void setTxt_cambio(JTextField txt_cambio) {
        this.txt_cambio = txt_cambio;
    }

    public JTextField getTxt_cliente_buscar() {
        return txt_cliente_buscar;
    }

    public void setTxt_cliente_buscar(JTextField txt_cliente_buscar) {
        this.txt_cliente_buscar = txt_cliente_buscar;
    }

    public JTextField getTxt_descuento() {
        return txt_descuento;
    }

    public void setTxt_descuento(JTextField txt_descuento) {
        this.txt_descuento = txt_descuento;
    }

    public JTextField getTxt_efectivo() {
        return txt_efectivo;
    }

    public void setTxt_efectivo(JTextField txt_efectivo) {
        this.txt_efectivo = txt_efectivo;
    }

    public JTextField getTxt_iva() {
        return txt_iva;
    }

    public void setTxt_iva(JTextField txt_iva) {
        this.txt_iva = txt_iva;
    }

    public JTextField getTxt_subtotal() {
        return txt_subtotal;
    }

    public void setTxt_subtotal(JTextField txt_subtotal) {
        this.txt_subtotal = txt_subtotal;
    }

    public static JTextField getTxt_total_pagar() {
        return txt_total_pagar;
    }

    public static void setTxt_total_pagar(JTextField txt_total_pagar) {
        InterVentas.txt_total_pagar = txt_total_pagar;
    }

   

    public JLabel getLblTotalDolar() {
        return lblTotalDolar;
    }

    public void setLblTotalDolar(JLabel lblTotalDolar) {
        this.lblTotalDolar = lblTotalDolar;
    }

    public JLabel getLbldolar() {
        return lbldolar;
    }

    public void setLbldolar(JLabel lbldolar) {
        this.lbldolar = lbldolar;
    }


     
}
