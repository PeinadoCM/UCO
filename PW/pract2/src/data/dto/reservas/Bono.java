package data.dto.reservas;

import java.util.Date;				
import data.dto.pistas.TipoPista;

/**
 * La clase {@code Bono} representa un bono que permite a un usuario acceder a pistas.
 * Contiene información sobre el usuario, el tipo de pista, la fecha de caducidad.
 */
public class Bono{
    private String idUsuario;
    private TipoPista tipo;
    private Date fecha_caducidad;
    private int sesionesDisponibles;

    /**
     * Constructor por defecto de la clase Bono. Este constructor inicializa los atributos de 
     * la instancia con valores predeterminados:
     * - `fecha_caducidad` se establece como `null` (sin fecha de caducidad definida).
     * - `sesionesDisponibles` se establece en 5, lo que indica que el bono comienza con 5 sesiones disponibles.
     */
    public Bono() {
        fecha_caducidad=null;
        this.sesionesDisponibles = 5;
    }

    /**
     * Constructor de la clase Bono. Este constructor inicializa los atributos de la instancia
     * con los valores proporcionados al crear el objeto.
     *
     * @param idUsuario El identificador del usuario asociado al bono.
     * @param tipo El tipo de pista asociado al bono (por ejemplo, pista de tenis, pista de pádel, etc.).
     * @param fecha_caducidad La fecha de caducidad del bono, indicando hasta cuándo es válido.
     * @param sesiones_disponibles El número de sesiones disponibles en el bono al momento de su creación.
     */
    public Bono(String idUsuario, TipoPista tipo, Date fecha_caducidad, int sesiones_disponibles) {
        this.idUsuario = idUsuario;
        this.tipo = tipo;
        this.fecha_caducidad=fecha_caducidad;
        this.sesionesDisponibles=sesiones_disponibles;
    }

     
    /**
     * Devuelve el identificador del usuario que posee este bono.
     *
     * @return El identificador del usuario.
     */
    public String getIdUsuario() {
    	return idUsuario;
    }
    
    /**
     * Devuelve el tipo de pista asociado a este bono.
     *
     * @return El tipo de pista.
     */
    public TipoPista getTipo() {
    	return tipo;
    }
    
    /**
     * Devuelve el número de sesiones disponibles en el bono.
     *
     * @return El número de sesiones restantes.
     */
    public int getSesiones() {
    	return sesionesDisponibles;
    }
    
    /**
     * Devuelve la fecha de caducidad del bono.
     *
     * @return La fecha de caducidad del bono.
     */
    public Date getFechaCaducidad() {
    	return fecha_caducidad;
    }
    
    /**
     * Devuelve una representación en forma de cadena de texto (String) del objeto Bono,
     * incluyendo los valores de los atributos más relevantes del bono.
     *
     * @return Una cadena de texto que describe el bono, incluyendo el identificador del usuario,
     *         el tipo de pista, la fecha de caducidad y el número de sesiones disponibles.
     */
    public String toString() {
    	return "Bono: Correo_usuario: " + idUsuario + ", Tipo: " + tipo + ", Fecha de caducidad: " + fecha_caducidad + ", Sesiones disponibles: " + sesionesDisponibles;
    }
    
}
