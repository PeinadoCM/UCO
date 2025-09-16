package display;

import java.io.Serializable;
import java.sql.Date;

public class CustomerBean implements Serializable {
    
    private String correo;
    private String contraseña;
    private boolean admin;
    private String nombre;
    private Date fecha_inscripcion;
    private Date fecha_nacimiento;

    public CustomerBean(){}

    public void setCorreo(String correo){
        this.correo=correo;
    }

    public void setContraseña(String contraseña){
        this.contraseña=contraseña;
    }

    public void setAdmin(boolean admin){
        this.admin=admin;
    }

    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public void setFecha_nacimiento(Date fecha_nacimiento){
        this.fecha_nacimiento=fecha_nacimiento;
    }

    public void setFecha_inscripcion(Date fecha_inscripcion){
        this.fecha_inscripcion=fecha_inscripcion;
    }

    public String getCorreo(){
        return correo;
    }

    public String getContraseña(){
        return contraseña;
    }

    public boolean getAdmin(){
        return admin;
    }

    public String getNombre(){
        return nombre;
    }
    
    public Date getFecha_nacimiento(){
        return fecha_nacimiento;
    }

    public Date getFecha_inscripcion(){
        return fecha_inscripcion;
    }
}
