package modelo;

import java.math.BigDecimal;
import java.util.Date;

public class MovimientoContable {
    private int id;
    private Date fecha;
    private String descripcion;
    private String tipo; // DEBE o HABER
    private BigDecimal monto;
    private String cuenta;
    private Integer referenciaId;
    private String referenciaTipo;

    public MovimientoContable() {
    }

    public MovimientoContable(int id, Date fecha, String descripcion, String tipo, 
                             BigDecimal monto, String cuenta, Integer referenciaId, 
                             String referenciaTipo) {
        this.id = id;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.monto = monto;
        this.cuenta = cuenta;
        this.referenciaId = referenciaId;
        this.referenciaTipo = referenciaTipo;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    
    public String getCuenta() { return cuenta; }
    public void setCuenta(String cuenta) { this.cuenta = cuenta; }
    
    public Integer getReferenciaId() { return referenciaId; }
    public void setReferenciaId(Integer referenciaId) { this.referenciaId = referenciaId; }
    
    public String getReferenciaTipo() { return referenciaTipo; }
    public void setReferenciaTipo(String referenciaTipo) { this.referenciaTipo = referenciaTipo; }

    @Override
    public String toString() {
        return "MovimientoContable{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", descripcion='" + descripcion + '\'' +
                ", tipo='" + tipo + '\'' +
                ", monto=" + monto +
                ", cuenta='" + cuenta + '\'' +
                ", referenciaId=" + referenciaId +
                ", referenciaTipo='" + referenciaTipo + '\'' +
                '}';
    }
}
