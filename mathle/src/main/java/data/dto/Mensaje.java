package data.dto;

import java.sql.Timestamp;

public class Mensaje {
    private int id;
    private String autor;
    private Timestamp fecha;
    private String mensaje;
    
    public Mensaje() {
    }
    
    public Mensaje(int id, String autor, Timestamp fecha, String mensaje) {
        this.id = id;
        this.autor = autor;
        this.fecha = fecha;
        this.mensaje = mensaje;
    }
    
    public Mensaje(String autor, Timestamp fecha, String mensaje) {
        this.autor = autor;
        this.fecha = fecha;
        this.mensaje = mensaje;
    }
    
    // Getters y Setters
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getAutor() {
        return autor;
    }
    
    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    public Timestamp getFecha() {
        return fecha;
    }
    
    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}