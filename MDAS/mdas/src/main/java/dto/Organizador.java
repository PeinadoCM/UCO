package dto;

/**
 * Clase que representa un organizador de eventos.
 */
public class Organizador {
    String correo;
    String contrasena;
    String nombreCompleto;
    String dni;
    int telefono;

    public Organizador(String correo, String contrasena, String nombreCompleto, String dni, int telefono) {
        this.correo = correo;
        this.contrasena = contrasena;
        this.nombreCompleto = nombreCompleto;
        this.dni = dni;
        this.telefono = telefono;
    }

    public Organizador() {}

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
}
