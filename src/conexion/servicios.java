
package conexion;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class servicios {



    // Ejemplo de asiento para una venta en contado (USD) convertida a Bs:
    // Debe: Caja/Banco (monto en Bs)
    // Haber: Ventas (monto en Bs)
    public static void registrarAsientoVenta(int idVenta, double totalBs, int idUsuario) throws SQLException {
        Connection cn = Conexion.conectar();
        cn.setAutoCommit(false);
        try {
            String insertAsiento = "INSERT INTO tb_asientos (fecha, descripcion, referencia, creado_por) VALUES (CURDATE(), ?, ?, ?)";
            PreparedStatement pst = cn.prepareStatement(insertAsiento, PreparedStatement.RETURN_GENERATED_KEYS);
            pst.setString(1, "Venta ID: " + idVenta);
            pst.setString(2, String.valueOf(idVenta));
            pst.setInt(3, idUsuario);
            pst.executeUpdate();
            ResultSet rs = pst.getGeneratedKeys();
            int idAsiento = 0;
            if (rs.next()) idAsiento = rs.getInt(1);

            // Asiento linea: Debe (Caja/Banco) -> supongamos cuenta id 1 = Caja
            String linea1 = "INSERT INTO tb_asiento_lineas (idAsiento, idCuenta, debe, haber) VALUES (?, ?, ?, ?)";
            PreparedStatement pstL1 = cn.prepareStatement(linea1);
            pstL1.setInt(1, idAsiento);
            pstL1.setInt(2, 1); // idCuenta caja (ajusta según tu plan de cuentas)
            pstL1.setDouble(3, totalBs);
            pstL1.setDouble(4, 0);
            pstL1.executeUpdate();

            // Asiento linea: Haber (Ventas) -> supongamos idCuenta 2 = Ventas
            PreparedStatement pstL2 = cn.prepareStatement(linea1);
            pstL2.setInt(1, idAsiento);
            pstL2.setInt(2, 2); // idCuenta ventas
            pstL2.setDouble(3, 0);
            pstL2.setDouble(4, totalBs);
            pstL2.executeUpdate();

            cn.commit();
        } catch (SQLException ex) {
            cn.rollback();
            throw ex;
        } finally {
            cn.setAutoCommit(true);
            cn.close();
        }
    }
}
