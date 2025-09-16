package dto;

/**
 * Clase que representa un usuario de la aplicación.
 */
public class Usuario {
    String correo;
    String contrasena;
    String nombreCompleto;
    String dni;
    int telefono;
    float monedero;


    public Usuario(String correo, String contrasena, String nombreCompleto, String dni, int telefono, float monedero) {
        this.correo = correo;
        this.contrasena = contrasena;
        this.nombreCompleto = nombreCompleto;
        this.dni = dni;
        this.telefono = telefono;
        this.monedero = monedero;
    }

    public Usuario() {}

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
    public float getMonedero() {
    return monedero;
}

    public void setMonedero(float monedero) {
        this.monedero = monedero;
    }
}
