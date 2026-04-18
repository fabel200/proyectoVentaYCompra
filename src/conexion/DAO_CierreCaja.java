
package conexion;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import modelo.CierreCaja;

public class DAO_CierreCaja {
    
    // Registrar un nuevo cierre de caja
    public boolean registrarCierreCaja(CierreCaja objeto) {
            boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try {
            PreparedStatement consulta = cn.prepareStatement("insert into tb_cierre_caja values(?,?,?,?,?,?,?,?,?)");
            consulta.setInt(1, 0);//id
            consulta.setString(2, objeto.getFecha());
            consulta.setBigDecimal(3, objeto.getTotalEfectivo());
            consulta.setBigDecimal(4, objeto.getTotalTarjeta());
            consulta.setBigDecimal(5, objeto.getTotalPagoMovil());
            consulta.setBigDecimal(6, objeto.getTotalTransferencia());
            consulta.setBigDecimal(7, objeto.getTotalDolares());
            consulta.setBigDecimal(8, objeto.getTotalBs());
            consulta.setString(9, objeto.getCreadoEn());
            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
            cn.close();
        } catch (SQLException e) {
            System.out.println("Error al guardar Proveedor: " + e);
        }
        return respuesta;
    }
    
 
    // Verificar si ya existe cierre para una fecha
    public boolean existeCierreParaFecha() {
        String sql = "SELECT COUNT(*) as existe_cierre FROM tb_cierre_caja WHERE fecha = curdate()";
         boolean respuesta = false;
        Connection cn = Conexion.conectar();
        try (PreparedStatement pstmt = cn.prepareStatement(sql)) {
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("existe_cierre") > 0;
            }
            cn.close();
        } catch (SQLException e) {
                System.out.println("Error al consultar: " + e);
        }
        return false;
    }
    
    public boolean existe() {
        boolean respuesta = false;
        String sql = "select count(*) as existe_cierre from tb_cierre_caja where fecha = curdate()";
        Statement st;

        try {
            Connection cn = Conexion.conectar();
            st = cn.createStatement();
            ResultSet rs = st.executeQuery(sql);
             if (rs.next()) {
                return rs.getInt("existe_cierre") > 0;
            }
             
            cn.close();

        } catch (SQLException e) {
            System.out.println("Error al consultar: " + e);
        }
        return respuesta;
    }
    


   
}



