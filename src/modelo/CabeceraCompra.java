package modelo;

/**
 * @author ediso
 */
public class CabeceraCompra {
    
  private int idCabeceraCompra;
    private int idProveedor;
    private double valorPagar;
    private String fechaCompra;
    private String tipoPago;
    private String estado;

    //constructor
    public CabeceraCompra() {
        this.idCabeceraCompra = 0;
        this.idProveedor = 0;
        this.valorPagar = 0.0;
        this.tipoPago = "";
        this.fechaCompra = "";
        this.estado = "";
    }

    //constructor sobrecargado
    public CabeceraCompra(int idCabeceraCompra, int idProveedor, double valorPagar, String fechaCompra, String tipoPago, String estado) {
        this.idCabeceraCompra = idCabeceraCompra;
        this.idProveedor = idProveedor;
        this.valorPagar = valorPagar;
        this.tipoPago = tipoPago;
        this.fechaCompra = fechaCompra;
        this.estado = estado;
    }

    //get and set 
    public int getIdCabeceraCompra() {
        return idCabeceraCompra;
    }

    public void setIdCabeceraCompra(int idCabeceraCompra) {
        this.idCabeceraCompra = idCabeceraCompra;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public double getValorPagar() {
        return valorPagar;
    }

    public void setValorPagar(double valorPagar) {
        this.valorPagar = valorPagar;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    //toString 
    @Override
    public String toString() {
        return "CabeceraVenta{" + "idCabeceraCompra=" + idCabeceraCompra + ", idProveedor=" + idProveedor + ", valorPagar=" + valorPagar + ", fechaCompra=" + fechaCompra + ", estado=" + estado + '}';
    }

    
}
