package data.dto;

public class Usuario {
    private String correo;
    private String nombre;
    private float puntuacion;
    private String tema;
    
    public Usuario(String nombre, float puntuacion) {
        this.nombre = nombre;
        this.puntuacion = puntuacion;
    }

    public Usuario(){}
    
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }

    public void aumentarPuntuacion(float puntuacion) {
        this.puntuacion += puntuacion;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }
}