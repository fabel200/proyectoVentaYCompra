package vista;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author Edison Zambrano - © Programador Fantama
 */
public class FrmMenu extends javax.swing.JFrame {

    //public static JDesktopPane jDesktopPane_menu;

    public FrmMenu() {
        initComponents();
        //this.setSize(new Dimension(1200, 700));
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.setLocationRelativeTo(null);
        //this.setTitle("Sistema de Ventas");

        //this.setLayout(null);
        //jDesktopPane_menu = new JDesktopPane();

       // int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
       // int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
       // this.jDesktopPane_menu.setBounds(0, 0, ancho, (alto - 110));
       // this.add(jDesktopPane_menu);

     //   ConfigurarPermisos();
     
        ImageIcon icon  = new ImageIcon(getClass().getResource("/img/fondo_principal.jpg"));
        JDesktopPane pane = new WallpaperDesktopPane(icon.getImage());
        
       jDesktopPane_menu = pane;
        setContentPane(pane);
      Image icono =  new ImageIcon(getClass().getResource("/img/supermarket.png")).getImage();
      
      this.setIconImage(icono);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane_menu = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem_gestionar_usuario = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem_GestionarProveedor = new javax.swing.JMenuItem();
        jMenuItem_NuevaCompra = new javax.swing.JMenuItem();
        jMenuItem_GestionarCompra = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem_gestionar_producto = new javax.swing.JMenuItem();
        jMenuItem_reportes_productos = new javax.swing.JMenuItem();
        jMenuItem_gestionar_categorias = new javax.swing.JMenuItem();
        jMenuItem_reportes_categorias = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem_gestionar_cliente = new javax.swing.JMenuItem();
        jMenuItem_reportes_clientes = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem_nueva_venta = new javax.swing.JMenuItem();
        jMenuItem_gestionar_ventas = new javax.swing.JMenuItem();
        jMenuItem_reportes_ventas = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem_Cuentas_pagar = new javax.swing.JMenuItem();
        jMenuItem_Cuentas_cobrar = new javax.swing.JMenuItem();
        jMenuItem_cierre_caja = new javax.swing.JMenuItem();
        jMenuItem_OrdenesCompra = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        jMenuItem_ver_historial = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem_cerrar_sesion = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("OptiBodega");

        jDesktopPane_menu.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255), new java.awt.Color(255, 255, 255)));
        jDesktopPane_menu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jDesktopPane_menu, java.awt.BorderLayout.CENTER);

        jMenuBar1.setBackground(new java.awt.Color(51, 51, 255));
        jMenuBar1.setBorder(null);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/usuario.png"))); // NOI18N
        jMenu1.setText("Usuario");
        jMenu1.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jMenu1.setPreferredSize(new java.awt.Dimension(150, 50));

        jMenuItem_gestionar_usuario.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jMenuItem_gestionar_usuario.setText("Gestionar Usuarios");
        jMenuItem_gestionar_usuario.setPreferredSize(new java.awt.Dimension(180, 30));
        jMenuItem_gestionar_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_gestionar_usuarioActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem_gestionar_usuario);

        jMenuBar1.add(jMenu1);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/proveedor.png"))); // NOI18N
        jMenu4.setText("Proveedor");
        jMenu4.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jMenu4.setPreferredSize(new java.awt.Dimension(150, 50));

        jMenuItem_GestionarProveedor.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jMenuItem_GestionarProveedor.setText("Gestionar Proveedor");
        jMenuItem_GestionarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_GestionarProveedorActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem_GestionarProveedor);

        jMenuItem_NuevaCompra.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jMenuItem_NuevaCompra.setText("Nueva Compra");
        jMenuItem_NuevaCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_NuevaCompraActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem_NuevaCompra);

        jMenuItem_GestionarCompra.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jMenuItem_GestionarCompra.setText("Gestionar Compra");
        jMenuItem_GestionarCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_GestionarCompraActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem_GestionarCompra);

        jMenuBar1.add(jMenu4);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/producto.png"))); // NOI18N
        jMenu2.setText("Producto");
        jMenu2.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jMenu2.setPreferredSize(new java.awt.Dimension(150, 50));

        jMenuItem_gestionar_producto.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jMenuItem_gestionar_producto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/producto.png"))); // NOI18N
        jMenuItem_gestionar_producto.setText("Gestionar Productos");
        jMenuItem_gestionar_producto.setPreferredSize(new java.awt.Dimension(200, 30));
        jMenuItem_gestionar_producto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_gestionar_productoActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem_gestionar_producto);

        jMenuItem_reportes_productos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jMenuItem_reportes_productos.setText("Reportes Productos");
        jMenuItem_reportes_productos.setPreferredSize(new java.awt.Dimension(200, 30));
        jMenuItem_reportes_productos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_reportes_productosActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem_reportes_productos);

        jMenuItem_gestionar_categorias.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jMenuItem_gestionar_categorias.setText("Gestionar Categorias");
        jMenuItem_gestionar_categorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_gestionar_categoriasActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem_gestionar_categorias);

        jMenuItem_reportes_categorias.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jMenuItem_reportes_categorias.setText("Reportes Categorias");
        jMenuItem_reportes_categorias.setPreferredSize(new java.awt.Dimension(200, 30));
        jMenuItem_reportes_categorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_reportes_categoriasActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem_reportes_categorias);

        jMenuBar1.add(jMenu2);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/clientes.png"))); // NOI18N
        jMenu3.setText("Cliente");
        jMenu3.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jMenu3.setPreferredSize(new java.awt.Dimension(150, 50));

        jMenuItem_gestionar_cliente.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jMenuItem_gestionar_cliente.setText("Gestionar Clientes");
        jMenuItem_gestionar_cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_gestionar_clienteActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem_gestionar_cliente);

        jMenuItem_reportes_clientes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jMenuItem_reportes_clientes.setText("Reportes Clientes");
        jMenuItem_reportes_clientes.setPreferredSize(new java.awt.Dimension(200, 30));
        jMenuItem_reportes_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_reportes_clientesActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem_reportes_clientes);

        jMenuBar1.add(jMenu3);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/venta.png"))); // NOI18N
        jMenu5.setText("Ventas");
        jMenu5.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jMenu5.setPreferredSize(new java.awt.Dimension(150, 50));

        jMenuItem_nueva_venta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jMenuItem_nueva_venta.setText("Nueva Venta");
        jMenuItem_nueva_venta.setPreferredSize(new java.awt.Dimension(200, 30));
        jMenuItem_nueva_venta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_nueva_ventaActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem_nueva_venta);

        jMenuItem_gestionar_ventas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jMenuItem_gestionar_ventas.setText("Gestionar Ventas");
        jMenuItem_gestionar_ventas.setPreferredSize(new java.awt.Dimension(200, 30));
        jMenuItem_gestionar_ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_gestionar_ventasActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem_gestionar_ventas);

        jMenuItem_reportes_ventas.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jMenuItem_reportes_ventas.setText("Reportes Ventas");
        jMenuItem_reportes_ventas.setPreferredSize(new java.awt.Dimension(200, 30));
        jMenuItem_reportes_ventas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_reportes_ventasActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem_reportes_ventas);

        jMenuBar1.add(jMenu5);

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cuentas.png"))); // NOI18N
        jMenu6.setText("Cuentas");
        jMenu6.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jMenu6.setPreferredSize(new java.awt.Dimension(150, 50));

        jMenuItem_Cuentas_pagar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jMenuItem_Cuentas_pagar.setText("Cuentas por Pagar");
        jMenuItem_Cuentas_pagar.setPreferredSize(new java.awt.Dimension(200, 30));
        jMenuItem_Cuentas_pagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_Cuentas_pagarActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem_Cuentas_pagar);

        jMenuItem_Cuentas_cobrar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jMenuItem_Cuentas_cobrar.setText("Cuentas por Cobrar");
        jMenuItem_Cuentas_cobrar.setPreferredSize(new java.awt.Dimension(200, 30));
        jMenuItem_Cuentas_cobrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_Cuentas_cobrarActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem_Cuentas_cobrar);

        jMenuItem_cierre_caja.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jMenuItem_cierre_caja.setText("Cierre de caja");
        jMenuItem_cierre_caja.setPreferredSize(new java.awt.Dimension(200, 30));
        jMenuItem_cierre_caja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_cierre_cajaActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem_cierre_caja);

        jMenuItem_OrdenesCompra.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jMenuItem_OrdenesCompra.setText("Ordenes de Compra");
        jMenuItem_OrdenesCompra.setPreferredSize(new java.awt.Dimension(200, 30));
        jMenuItem_OrdenesCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_OrdenesCompraActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem_OrdenesCompra);

        jMenuBar1.add(jMenu6);

        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/historial.png"))); // NOI18N
        jMenu7.setText("Historial");
        jMenu7.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jMenu7.setPreferredSize(new java.awt.Dimension(150, 50));

        jMenuItem_ver_historial.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jMenuItem_ver_historial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/historial.png"))); // NOI18N
        jMenuItem_ver_historial.setText("Ver Historial");
        jMenuItem_ver_historial.setPreferredSize(new java.awt.Dimension(150, 30));
        jMenuItem_ver_historial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_ver_historialActionPerformed(evt);
            }
        });
        jMenu7.add(jMenuItem_ver_historial);

        jMenuBar1.add(jMenu7);

        jMenu8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logout.png"))); // NOI18N
        jMenu8.setText("Salir");
        jMenu8.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jMenu8.setPreferredSize(new java.awt.Dimension(200, 50));

        jMenuItem_cerrar_sesion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jMenuItem_cerrar_sesion.setText("Cerrar Sesión");
        jMenuItem_cerrar_sesion.setPreferredSize(new java.awt.Dimension(150, 30));
        jMenuItem_cerrar_sesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_cerrar_sesionActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem_cerrar_sesion);

        jMenuBar1.add(jMenu8);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem_reportes_ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_reportes_ventasActionPerformed
      
    }//GEN-LAST:event_jMenuItem_reportes_ventasActionPerformed

    private void jMenuItem_gestionar_categoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_gestionar_categoriasActionPerformed
       
    }//GEN-LAST:event_jMenuItem_gestionar_categoriasActionPerformed

    private void jMenuItem_gestionar_productoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_gestionar_productoActionPerformed
     
    }//GEN-LAST:event_jMenuItem_gestionar_productoActionPerformed

    private void jMenuItem_gestionar_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_gestionar_clienteActionPerformed
       
    }//GEN-LAST:event_jMenuItem_gestionar_clienteActionPerformed

    private void jMenuItem_cerrar_sesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_cerrar_sesionActionPerformed
    
    }//GEN-LAST:event_jMenuItem_cerrar_sesionActionPerformed

    private void jMenuItem_gestionar_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_gestionar_usuarioActionPerformed
 
    }//GEN-LAST:event_jMenuItem_gestionar_usuarioActionPerformed

    private void jMenuItem_nueva_ventaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_nueva_ventaActionPerformed
       
    }//GEN-LAST:event_jMenuItem_nueva_ventaActionPerformed

    private void jMenuItem_gestionar_ventasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_gestionar_ventasActionPerformed
  
    }//GEN-LAST:event_jMenuItem_gestionar_ventasActionPerformed

    private void jMenuItem_reportes_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_reportes_clientesActionPerformed
      
    }//GEN-LAST:event_jMenuItem_reportes_clientesActionPerformed

    private void jMenuItem_reportes_productosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_reportes_productosActionPerformed
       
    }//GEN-LAST:event_jMenuItem_reportes_productosActionPerformed

    private void jMenuItem_reportes_categoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_reportes_categoriasActionPerformed
       
    }//GEN-LAST:event_jMenuItem_reportes_categoriasActionPerformed

    private void jMenuItem_ver_historialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_ver_historialActionPerformed
   
    }//GEN-LAST:event_jMenuItem_ver_historialActionPerformed

    private void jMenuItem_GestionarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_GestionarProveedorActionPerformed
        
    }//GEN-LAST:event_jMenuItem_GestionarProveedorActionPerformed

    private void jMenuItem_GestionarCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_GestionarCompraActionPerformed
        
    }//GEN-LAST:event_jMenuItem_GestionarCompraActionPerformed

    private void jMenuItem_NuevaCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_NuevaCompraActionPerformed
      
    }//GEN-LAST:event_jMenuItem_NuevaCompraActionPerformed

    private void jMenuItem_Cuentas_pagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_Cuentas_pagarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem_Cuentas_pagarActionPerformed

    private void jMenuItem_Cuentas_cobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_Cuentas_cobrarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem_Cuentas_cobrarActionPerformed

    private void jMenuItem_cierre_cajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_cierre_cajaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem_cierre_cajaActionPerformed

    private void jMenuItem_OrdenesCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_OrdenesCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem_OrdenesCompraActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JDesktopPane jDesktopPane_menu;
    public javax.swing.JMenu jMenu1;
    public javax.swing.JMenu jMenu2;
    public javax.swing.JMenu jMenu3;
    public javax.swing.JMenu jMenu4;
    public javax.swing.JMenu jMenu5;
    public javax.swing.JMenu jMenu6;
    public javax.swing.JMenu jMenu7;
    public javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    public javax.swing.JMenuItem jMenuItem_Cuentas_cobrar;
    public javax.swing.JMenuItem jMenuItem_Cuentas_pagar;
    public javax.swing.JMenuItem jMenuItem_GestionarCompra;
    public javax.swing.JMenuItem jMenuItem_GestionarProveedor;
    public javax.swing.JMenuItem jMenuItem_NuevaCompra;
    public javax.swing.JMenuItem jMenuItem_OrdenesCompra;
    public javax.swing.JMenuItem jMenuItem_cerrar_sesion;
    public javax.swing.JMenuItem jMenuItem_cierre_caja;
    public javax.swing.JMenuItem jMenuItem_gestionar_categorias;
    public javax.swing.JMenuItem jMenuItem_gestionar_cliente;
    public javax.swing.JMenuItem jMenuItem_gestionar_producto;
    public javax.swing.JMenuItem jMenuItem_gestionar_usuario;
    public javax.swing.JMenuItem jMenuItem_gestionar_ventas;
    public javax.swing.JMenuItem jMenuItem_nueva_venta;
    public javax.swing.JMenuItem jMenuItem_reportes_categorias;
    public javax.swing.JMenuItem jMenuItem_reportes_clientes;
    public javax.swing.JMenuItem jMenuItem_reportes_productos;
    public javax.swing.JMenuItem jMenuItem_reportes_ventas;
    public javax.swing.JMenuItem jMenuItem_ver_historial;
    // End of variables declaration//GEN-END:variables

    
    public static JDesktopPane getjDesktopPane_menu() {
        return jDesktopPane_menu;
    }

    public static void setjDesktopPane_menu(JDesktopPane jDesktopPane_menu) {
        FrmMenu.jDesktopPane_menu = jDesktopPane_menu;
    }

    public JMenu getjMenu1() {
        return jMenu1;
    }

    public void setjMenu1(JMenu jMenu1) {
        this.jMenu1 = jMenu1;
    }

    public JMenu getjMenu2() {
        return jMenu2;
    }

    public void setjMenu2(JMenu jMenu2) {
        this.jMenu2 = jMenu2;
    }

    public JMenu getjMenu3() {
        return jMenu3;
    }

    public void setjMenu3(JMenu jMenu3) {
        this.jMenu3 = jMenu3;
    }

    public JMenu getjMenu4() {
        return jMenu4;
    }

    public void setjMenu4(JMenu jMenu4) {
        this.jMenu4 = jMenu4;
    }

    public JMenu getjMenu5() {
        return jMenu5;
    }

    public void setjMenu5(JMenu jMenu5) {
        this.jMenu5 = jMenu5;
    }

    public JMenu getjMenu6() {
        return jMenu6;
    }

    public void setjMenu6(JMenu jMenu6) {
        this.jMenu6 = jMenu6;
    }

    public JMenu getjMenu7() {
        return jMenu7;
    }

    public void setjMenu7(JMenu jMenu7) {
        this.jMenu7 = jMenu7;
    }

    public JMenu getjMenu8() {
        return jMenu8;
    }

    public void setjMenu8(JMenu jMenu8) {
        this.jMenu8 = jMenu8;
    }

    public JMenuItem getjMenuItem_GestionarCompra() {
        return jMenuItem_GestionarCompra;
    }

    public void setjMenuItem_GestionarCompra(JMenuItem jMenuItem_GestionarCompra) {
        this.jMenuItem_GestionarCompra = jMenuItem_GestionarCompra;
    }

    public JMenuItem getjMenuItem_GestionarProveedor() {
        return jMenuItem_GestionarProveedor;
    }

    public void setjMenuItem_GestionarProveedor(JMenuItem jMenuItem_GestionarProveedor) {
        this.jMenuItem_GestionarProveedor = jMenuItem_GestionarProveedor;
    }

    public JMenuItem getjMenuItem_NuevaCompra() {
        return jMenuItem_NuevaCompra;
    }

    public void setjMenuItem_NuevaCompra(JMenuItem jMenuItem_NuevaCompra) {
        this.jMenuItem_NuevaCompra = jMenuItem_NuevaCompra;
    }



    public JMenuItem getjMenuItem_cerrar_sesion() {
        return jMenuItem_cerrar_sesion;
    }

    public void setjMenuItem_cerrar_sesion(JMenuItem jMenuItem_cerrar_sesion) {
        this.jMenuItem_cerrar_sesion = jMenuItem_cerrar_sesion;
    }

    public JMenuItem getjMenuItem_gestionar_categorias() {
        return jMenuItem_gestionar_categorias;
    }

    public void setjMenuItem_gestionar_categorias(JMenuItem jMenuItem_gestionar_categorias) {
        this.jMenuItem_gestionar_categorias = jMenuItem_gestionar_categorias;
    }

    public JMenuItem getjMenuItem_gestionar_cliente() {
        return jMenuItem_gestionar_cliente;
    }

    public void setjMenuItem_gestionar_cliente(JMenuItem jMenuItem_gestionar_cliente) {
        this.jMenuItem_gestionar_cliente = jMenuItem_gestionar_cliente;
    }

    public JMenuItem getjMenuItem_gestionar_producto() {
        return jMenuItem_gestionar_producto;
    }

    public void setjMenuItem_gestionar_producto(JMenuItem jMenuItem_gestionar_producto) {
        this.jMenuItem_gestionar_producto = jMenuItem_gestionar_producto;
    }

    public JMenuItem getjMenuItem_gestionar_usuario() {
        return jMenuItem_gestionar_usuario;
    }

    public void setjMenuItem_gestionar_usuario(JMenuItem jMenuItem_gestionar_usuario) {
        this.jMenuItem_gestionar_usuario = jMenuItem_gestionar_usuario;
    }

    public JMenuItem getjMenuItem_gestionar_ventas() {
        return jMenuItem_gestionar_ventas;
    }

    public void setjMenuItem_gestionar_ventas(JMenuItem jMenuItem_gestionar_ventas) {
        this.jMenuItem_gestionar_ventas = jMenuItem_gestionar_ventas;
    }

    public JMenuItem getjMenuItem_nueva_venta() {
        return jMenuItem_nueva_venta;
    }

    public void setjMenuItem_nueva_venta(JMenuItem jMenuItem_nueva_venta) {
        this.jMenuItem_nueva_venta = jMenuItem_nueva_venta;
    }


    public JMenuItem getjMenuItem_reportes_categorias() {
        return jMenuItem_reportes_categorias;
    }

    public void setjMenuItem_reportes_categorias(JMenuItem jMenuItem_reportes_categorias) {
        this.jMenuItem_reportes_categorias = jMenuItem_reportes_categorias;
    }

    public JMenuItem getjMenuItem_reportes_clientes() {
        return jMenuItem_reportes_clientes;
    }

    public void setjMenuItem_reportes_clientes(JMenuItem jMenuItem_reportes_clientes) {
        this.jMenuItem_reportes_clientes = jMenuItem_reportes_clientes;
    }

    public JMenuItem getjMenuItem_reportes_productos() {
        return jMenuItem_reportes_productos;
    }

    public void setjMenuItem_reportes_productos(JMenuItem jMenuItem_reportes_productos) {
        this.jMenuItem_reportes_productos = jMenuItem_reportes_productos;
    }

    public JMenuItem getjMenuItem_reportes_ventas() {
        return jMenuItem_reportes_ventas;
    }

    public void setjMenuItem_reportes_ventas(JMenuItem jMenuItem_reportes_ventas) {
        this.jMenuItem_reportes_ventas = jMenuItem_reportes_ventas;
    }

    public JMenuItem getjMenuItem_ver_historial() {
        return jMenuItem_ver_historial;
    }

    public void setjMenuItem_ver_historial(JMenuItem jMenuItem_ver_historial) {
        this.jMenuItem_ver_historial = jMenuItem_ver_historial;
    }

    
    
}