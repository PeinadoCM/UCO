package dto;

import java.sql.Date;

/**
 * Clase que representa un evento.
 */
public class Evento {
    private String nombre;
    private String descripcion;
    private String lugar;
    private Date fecha;
    private float limiteReventa;
    private String correoOrganizador;

    public Evento() {}

    public Evento(String nombre, String descripcion, String lugar, Date fecha, float limiteReventa, String correoOrganizador) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.lugar = lugar;
        this.fecha = fecha;
        this.limiteReventa = limiteReventa;
        this.correoOrganizador = correoOrganizador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getLimiteReventa() {
        return limiteReventa;
    }

    public void setLimiteReventa(float limiteReventa) {
        this.limiteReventa = limiteReventa;
    }

    public String getCorreoOrganizador() {
        return correoOrganizador;
    }

    public void setCorreoOrganizador(String correoOrganizador) {
        this.correoOrganizador = correoOrganizador;
    }
}

