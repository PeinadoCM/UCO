package dto;

/**
 * Clase que representa una transacción entre un comprador y un vendedor.
 */
public class Transaccion {
    private String correoComprador;
    private String correoVendedor;
    private float precio;
    private TipoTransaccion tipo;

    public Transaccion() {
    }

    public Transaccion(String correoComprador, String correoVendedor, float precio, TipoTransaccion tipo) {
        this.tipo = tipo;
        this.correoComprador = correoComprador;
        this.correoVendedor = correoVendedor;
        this.precio = precio;
    }

    public String getCorreoComprador() {
        return correoComprador;
    }

    public void setCorreoComprador(String correoComprador) {
        this.correoComprador = correoComprador;
    }

    public String getCorreoVendedor() {
        return correoVendedor;
    }

    public void setCorreoVendedor(String correoVendedor) {
        this.correoVendedor = correoVendedor;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public TipoTransaccion getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransaccion tipo) {
        this.tipo = tipo;
    }

}
