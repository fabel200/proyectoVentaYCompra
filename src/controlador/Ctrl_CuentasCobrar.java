/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import conexion.Conexion;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vista.InterCuentasCobrar;

/**
 *
 * @author Marialyh
 */
public class Ctrl_CuentasCobrar {
    InterCuentasCobrar interCuentasCobrar;

    public Ctrl_CuentasCobrar(InterCuentasCobrar interCuentasCobrar) {
        this.interCuentasCobrar = interCuentasCobrar;
        this.Carga();
    }
    
    
      private void Carga() {
        Connection con = Conexion.conectar();
        DefaultTableModel model = new DefaultTableModel();
        String sql = "select * from vista_cuentas_por_cobrar;";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            interCuentasCobrar.jTable_CuentasCobrar = new JTable(model);
            interCuentasCobrar.jScrollPane1.setViewportView(interCuentasCobrar.jTable_CuentasCobrar);

            model.addColumn("N°");//ID
            model.addColumn("Tipo Documento");
            model.addColumn("id Cliente");
            model.addColumn("Cliente");
            model.addColumn("Monto Total");
            model.addColumn("fecha de Emision");
            model.addColumn("Forma de Pago");
            model.addColumn("Estado");

             while (rs.next()) {
                Object fila[] = new Object[8];
                for (int i = 0; i < 8; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                model.addRow(fila);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al llenar la tabla clientes: " + e);
        }
    }
}
