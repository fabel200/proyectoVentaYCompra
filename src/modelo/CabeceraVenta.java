package modelo;

/**
 * @author ediso
 */
public class CabeceraVenta {

    //Atributos
    private int idCabeceraventa;
    private int idCliente;
    private double valorPagar;
    private String tipoPago;
    private String fechaVenta;
    private String estado;

    //constructor
    public CabeceraVenta() {
        this.idCabeceraventa = 0;
        this.idCliente = 0;
        this.valorPagar = 0.0;
        this.tipoPago = "";
        this.fechaVenta = "";
        this.estado = "";
    }

    //constructor sobrecargado
    public CabeceraVenta(int idCabeceraventa, int idCliente, double valorPagar, String fechaVenta, String tipoPago, String estado) {
        this.idCabeceraventa = idCabeceraventa;
        this.idCliente = idCliente;
        this.valorPagar = valorPagar;
        this.tipoPago = tipoPago;
        this.fechaVenta = fechaVenta;
        this.estado = estado;
    }

    //get and set 
    public int getIdCabeceraventa() {
        return idCabeceraventa;
    }

    public void setIdCabeceraventa(int idCabeceraventa) {
        this.idCabeceraventa = idCabeceraventa;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
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
        return "CabeceraVenta{" + "idCabeceraventa=" + idCabeceraventa + ", idCliente=" + idCliente + ", valorPagar=" + valorPagar + ", fechaVenta=" + fechaVenta + ", estado=" + estado + '}';
    }
}
