/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

    import java.math.BigDecimal;
import java.util.Date;

public class CierreCaja {
    private int idCierre;
    private String fecha;
    private int usuario;
    private BigDecimal totalEfectivo,
            totalTarjeta,
            totalPagoMovil,
            totalTransferencia,
            totalDolares;
    private BigDecimal totalBs;
    private String observaciones;
    private String creadoEn;
    
 

    public CierreCaja(int idCierre, String fecha, int usuario, BigDecimal totalEfectivo, BigDecimal totalTarjeta, BigDecimal totalPagoMovil, BigDecimal totalTransferencia, BigDecimal totalDolares, BigDecimal totalBs, String observaciones, String creadoEn) {
        this.idCierre = idCierre;
        this.fecha = fecha;
        this.usuario = usuario;
        this.totalEfectivo = totalEfectivo;
        this.totalTarjeta = totalTarjeta;
        this.totalPagoMovil = totalPagoMovil;
        this.totalTransferencia = totalTransferencia;
        this.totalDolares = totalDolares;
        this.totalBs = totalBs;
        this.observaciones = observaciones;
        this.creadoEn = creadoEn;
    }

    public CierreCaja() {
    }
    
    
    
    // Getters y Setters
    public int getIdCierre() { return idCierre; }
    public void setIdCierre(int idCierre) { this.idCierre = idCierre; }
    
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    
    public int getUsuario() { return usuario; }
    public void setUsuario(int usuario) { this.usuario = usuario; }
    
    public BigDecimal getTotalEfectivo() { return totalEfectivo; }
    public void setTotalEfectivo(BigDecimal totalEfectivo) { this.totalEfectivo = totalEfectivo; }
    
    public BigDecimal getTotalTarjeta() { return totalTarjeta; }
    public void setTotalTarjeta(BigDecimal totalTarjeta) { this.totalTarjeta = totalTarjeta; }
    
    public BigDecimal getTotalPagoMovil() { return totalPagoMovil; }
    public void setTotalPagoMovil(BigDecimal totalPagoMovil) { this.totalPagoMovil = totalPagoMovil; }
    
    public BigDecimal getTotalTransferencia() { return totalTransferencia; }
    public void setTotalTransferencia(BigDecimal totalTransferencia) { this.totalTransferencia = totalTransferencia; }
    
    public BigDecimal getTotalDolares() { return totalDolares; }
    public void setTotalDolares(BigDecimal totalDolares) { this.totalDolares = totalDolares; }
    
    public BigDecimal getTotalBs() { return totalBs; }
    public void setTotalBs(BigDecimal totalBs) { this.totalBs = totalBs; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    
    public String getCreadoEn() { return creadoEn; }
    public void setCreadoEn(String creadoEn) { this.creadoEn = creadoEn; }
    
    public BigDecimal getTotalGeneral() {
        BigDecimal total = BigDecimal.ZERO;
        if (totalEfectivo != null) total = total.add(totalEfectivo);
        if (totalTarjeta != null) total = total.add(totalTarjeta);
        if (totalPagoMovil != null) total = total.add(totalPagoMovil);
        if (totalTransferencia != null) total = total.add(totalTransferencia);
        if (totalDolares != null) total = total.add(totalDolares);
        if (totalBs != null) total = total.add(totalBs);
        return total;
    }
    
    public BigDecimal getTotalVentasDelDia() {
        BigDecimal total = BigDecimal.ZERO;
        if (totalEfectivo != null) total = total.add(totalEfectivo);
        if (totalTarjeta != null) total = total.add(totalTarjeta);
        if (totalPagoMovil != null) total = total.add(totalPagoMovil);
        if (totalTransferencia != null) total = total.add(totalTransferencia);
        return total;
    }
}


