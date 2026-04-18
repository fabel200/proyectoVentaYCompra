package modelo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class LibroDiario {
    private Date fecha;
    private List<MovimientoContable> movimientos;
    private BigDecimal totalDebe;
    private BigDecimal totalHaber;

    public LibroDiario() {
    }

    public LibroDiario(Date fecha, List<MovimientoContable> movimientos, 
                      BigDecimal totalDebe, BigDecimal totalHaber) {
        this.fecha = fecha;
        this.movimientos = movimientos;
        this.totalDebe = totalDebe;
        this.totalHaber = totalHaber;
    }

    // Getters y Setters
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    
    public List<MovimientoContable> getMovimientos() { return movimientos; }
    public void setMovimientos(List<MovimientoContable> movimientos) { this.movimientos = movimientos; }
    
    public BigDecimal getTotalDebe() { return totalDebe; }
    public void setTotalDebe(BigDecimal totalDebe) { this.totalDebe = totalDebe; }
    
    public BigDecimal getTotalHaber() { return totalHaber; }
    public void setTotalHaber(BigDecimal totalHaber) { this.totalHaber = totalHaber; }

    public boolean isBalanceado() {
        return totalDebe.compareTo(totalHaber) == 0;
    }
}
