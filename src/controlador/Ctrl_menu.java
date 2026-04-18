/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import conexion.Reportes;
import vista.*;
import static vista.FrmMenu.jDesktopPane_menu;

public class Ctrl_menu {

    FrmMenu menu;

    public Ctrl_menu(FrmMenu menu) {
        this.menu = menu;

        this.menu.jMenuItem_Cuentas_cobrar.addActionListener(e -> AbrirCuentasCobrar());
        this.menu.jMenuItem_Cuentas_pagar.addActionListener(e -> AbrirCuentasPagar());
        this.menu.jMenuItem_cierre_caja.addActionListener(e -> AbrirCierreCaja());
        this.menu.jMenuItem_OrdenesCompra.addActionListener(e -> AbrirOrdenesCompra());

        this.menu.jMenuItem_gestionar_cliente.addActionListener(e -> AbrirGestionCliente());
        this.menu.jMenuItem_reportes_clientes.addActionListener(e -> ReporteCliente());
        this.menu.jMenuItem_GestionarProveedor.addActionListener(e -> AbrirGestionProveedor());

        this.menu.jMenuItem_gestionar_producto.addActionListener(e -> AbrirGestionProducto());
        this.menu.jMenuItem_reportes_productos.addActionListener(e -> ReporteProducto());
        this.menu.jMenuItem_gestionar_categorias.addActionListener(e -> AbrirGestionCategoria());
        this.menu.jMenuItem_reportes_categorias.addActionListener(e -> ReporteCategoria());
        this.menu.jMenuItem_NuevaCompra.addActionListener(e -> AbrirNuevaCompra());
        this.menu.jMenuItem_GestionarCompra.addActionListener(e -> AbrirGestionCompra());

        this.menu.jMenuItem_nueva_venta.addActionListener(e -> AbrirNuevaVenta());
        this.menu.jMenuItem_gestionar_ventas.addActionListener(e -> AbrirGestionVenta());
        this.menu.jMenuItem_reportes_ventas.addActionListener(e -> ReporteVenta());
        this.menu.jMenuItem_gestionar_usuario.addActionListener(e -> AbrirGestionusuario());
        this.menu.jMenuItem_ver_historial.addActionListener(e -> VerHistorial());
        this.menu.jMenuItem_cerrar_sesion.addActionListener(e -> CerrarSesion());;
    }

    public void Dolar() {
        /*   hola h = new hola();
       h.ActualizarValorDolar();
        jDesktopPane_menu.add(h);
        h.setVisible(true); */
    }

    public void AbrirGestionCliente() {
        InterGestionarCliente interGestionarCliente = new InterGestionarCliente();
        InterCliente interCliente = new InterCliente();
        Ctrl_cliente ctrl_cliente = new Ctrl_cliente(interGestionarCliente, interCliente);
        jDesktopPane_menu.add(interGestionarCliente);
        interGestionarCliente.setVisible(true);
    }

    public void ReporteCliente() {
        Reportes reporte = new Reportes();
        reporte.ReportesClientes();
    }

    public void AbrirGestionProveedor() {
        InterGestionarProveedor interGestionarProveedor = new InterGestionarProveedor();
        InterProveedor interProveedor = new InterProveedor();
        Ctrl_proveedor ctrl_proveedor = new Ctrl_proveedor(interGestionarProveedor, interProveedor);
        jDesktopPane_menu.add(interGestionarProveedor);
        interGestionarProveedor.setVisible(true);
    }

    public void ReporteProveedor() {

    }

    public void AbrirGestionProducto() {
        InterGestionarProducto interGestionarProducto = new InterGestionarProducto();
        InterProducto interProducto = new InterProducto();
        Ctrl_producto ctrl_producto = new Ctrl_producto(interProducto, interGestionarProducto);
        jDesktopPane_menu.add(interGestionarProducto);
        interGestionarProducto.setVisible(true);
    }

    public void ReporteProducto() {
        Reportes reporte = new Reportes();
        reporte.ReportesProductos();
    }

    public void AbrirGestionCategoria() {
        InterGestionarCategoria interGestionarCategoria = new InterGestionarCategoria();
        InterCategoria interCategoria = new InterCategoria();
        Ctrl_categoria ctrl_categoria = new Ctrl_categoria(interCategoria, interGestionarCategoria);
        jDesktopPane_menu.add(interGestionarCategoria);
        interGestionarCategoria.setVisible(true);
    }

    public void ReporteCategoria() {
        Reportes reporte = new Reportes();
        reporte.ReportesCategorias();
    }

    public void AbrirGestionusuario() {
        InterGestionarUsuario interGestionarUsuario = new InterGestionarUsuario();
        InterUsuario interUsuario = new InterUsuario();
        Ctrl_usuario ctrl_usuario = new Ctrl_usuario(interGestionarUsuario, interUsuario);
        jDesktopPane_menu.add(interGestionarUsuario);
        interGestionarUsuario.setVisible(true);
    }

    public void AbrirNuevaVenta() {
        InterVentas interVentas = new InterVentas();
        InterGestionarVentas interGestionarVentas = new InterGestionarVentas();
        Ctrl_venta ctrl_venta = new Ctrl_venta(interVentas, interGestionarVentas);
        jDesktopPane_menu.add(interVentas);
        interVentas.setVisible(true);
    }

    public void AbrirGestionVenta() {
        InterVentas interVentas = new InterVentas();
        InterGestionarVentas interGestionarVentas = new InterGestionarVentas();
        Ctrl_venta ctrl_venta = new Ctrl_venta(interVentas, interGestionarVentas);
        jDesktopPane_menu.add(interGestionarVentas);
        interGestionarVentas.setVisible(true);
    }

    public void ReporteVenta() {
        Reportes reporte = new Reportes();
        reporte.ReportesVentas();
    }

    public void AbrirNuevaCompra() {
        InterCompra interCompras = new InterCompra();
        InterGestionarCompras interGestionarCompras = new InterGestionarCompras();
        Ctrl_compra ctrl_compra = new Ctrl_compra(interCompras, interGestionarCompras);
        jDesktopPane_menu.add(interCompras);
        interCompras.setVisible(true);
    }

    public void AbrirGestionCompra() {
        InterCompra interCompras = new InterCompra();
        InterGestionarCompras interGestionarCompras = new InterGestionarCompras();
        Ctrl_compra ctrl_compra = new Ctrl_compra(interCompras, interGestionarCompras);
        jDesktopPane_menu.add(interGestionarCompras);
        interGestionarCompras.setVisible(true);
    }

    public void ReporteCompra() {

    }

    public void AbrirCuentasPagar() {
        InterCuentasPagar interCuentasPagar = new InterCuentasPagar();
        Ctrl_CuentasPagar ctrl_CuentasPagar = new Ctrl_CuentasPagar(interCuentasPagar);
        jDesktopPane_menu.add(interCuentasPagar);
        interCuentasPagar.setVisible(true);
    }

    public void AbrirCuentasCobrar() {
        InterCuentasCobrar interCuentasCobrar = new InterCuentasCobrar();
        Ctrl_CuentasCobrar ctrl_CuentasCobrar = new Ctrl_CuentasCobrar(interCuentasCobrar);
        jDesktopPane_menu.add(interCuentasCobrar);
        interCuentasCobrar.setVisible(true);
    }

    public void AbrirCierreCaja() {
        InterCierreCaja1 interCierreCaja = new InterCierreCaja1();
        Ctrl_Cierrecaja1 ctrl_cierre_caja = new Ctrl_Cierrecaja1(interCierreCaja);
        jDesktopPane_menu.add(interCierreCaja);
        interCierreCaja.setVisible(true);
    }

    public void AbrirOrdenesCompra() {
        InterOrdenesCompra interOrdenesCompra = new InterOrdenesCompra();
        Ctrl_OrdenesCompra ctrl_OrdenesCompra = new Ctrl_OrdenesCompra(interOrdenesCompra);
        jDesktopPane_menu.add(interOrdenesCompra);
        interOrdenesCompra.setVisible(true);
    }

    public void VerHistorial() {
        InterGraficas interGraficas = new InterGraficas();
       // Ctrl_graficas ctrl_graficas = new Ctrl_graficas( interGraficas);
        jDesktopPane_menu.add(interGraficas);
        interGraficas.setVisible(true);
    }

    public void CerrarSesion() {
        //  System.exit(0);
        FrmLogin login = new FrmLogin();
        Ctrl_login ctrl_login = new Ctrl_login(login);
        login.setVisible(true);
        this.menu.dispose();
    }

}
