
package controlador;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vista.*;
public class Ctrl_OrdenesCompra {
    InterOrdenesCompra interOrdenesCompra;

    public Ctrl_OrdenesCompra(InterOrdenesCompra interOrdenesCompra) {
        this.interOrdenesCompra = interOrdenesCompra;
        this.Carga();
    }
    
    
      private void Carga() {
        Connection con = Conexion.conectar();
        DefaultTableModel model = new DefaultTableModel();
        String sql = "select * from vw_ordenes_compra;";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            interOrdenesCompra.jTable_OrdenesCompra = new JTable(model);
            interOrdenesCompra.jScrollPane1.setViewportView(interOrdenesCompra.jTable_OrdenesCompra);

            model.addColumn("N°");//ID
            model.addColumn("Nombre");
            model.addColumn("Stock Actual");
            model.addColumn("Stock Minimo");
            model.addColumn("Stock Maximo");
            model.addColumn("Precio");
            model.addColumn("Categoria");
            model.addColumn("Nivel de Stock");
            model.addColumn("Cantidad a Comprar");
            model.addColumn("Valor Estimado");

             while (rs.next()) {
                Object fila[] = new Object[10];
                for (int i = 0; i < 10; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                model.addRow(fila);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error al llenar la tabla cuentas: " + e);
        }
    }
}
