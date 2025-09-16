package ejer1;

import java.util.Date;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * La clase {@code Jugador} representa a un jugador con información personal como
 * nombre, fecha de nacimiento, fecha de inscripción y correo electrónico.
 */
public class Jugador {
    
    private String nombreyapellidos;
    private Date fechanacimiento;
    private Date fechainscripcion;
    private String correo;

    /**
     * Crea un nuevo jugador sin inicializar sus atributos.
     */
    public Jugador(){

    }

    /**
     * Crea un nuevo jugador con la información proporcionada.
     * 
     * @param nombreyapellidos El nombre y apellidos del jugador.
     * @param fechanacimiento La fecha de nacimiento del jugador.
     * @param correo El correo electrónico del jugador.
     */
    public Jugador(String nombreyapellidos, Date fechanacimiento, String correo){

        this.nombreyapellidos = nombreyapellidos;
        this.fechanacimiento = fechanacimiento;
        this.fechainscripcion = new Date();
        this.correo = correo;
    }

    /**
     * Devuelve el nombre y apellidos del jugador.
     *
     * @return El nombre y apellidos del jugador.
     */
    public String getNombreyapellidos(){

        return nombreyapellidos;
    }

    /**
     * Establece el nombre y apellidos del jugador.
     *
     * @param nombreyapellidos El nuevo nombre y apellidos del jugador.
     */
    public void setNombreyapellidos(String nombreyapellidos){

        this.nombreyapellidos = nombreyapellidos;
    }

    /**
     * Devuelve la fecha de nacimiento del jugador.
     *
     * @return La fecha de nacimiento del jugador.
     */
    public Date getFechanacimiento(){

        return fechanacimiento;
    }

    /**
     * Establece la fecha de nacimiento del jugador.
     *
     * @param fechanacimiento La nueva fecha de nacimiento del jugador.
     */
    public void setFechanacimiento(Date fechanacimiento){

        this.fechanacimiento = fechanacimiento;
    }

    /**
     * Devuelve la fecha de inscripción del jugador.
     *
     * @return La fecha de inscripción del jugador.
     */
    public Date getFechainscripcion(){

        return fechainscripcion;
    }
    
    /**
     * Establece la fecha de inscripción del jugador.
     *
     * @param fechainscripcion La nueva fecha de inscripción del jugador.
     */
    public void setFechainscripcion(Date fechainscripcion){

        this.fechainscripcion = fechainscripcion;
    }

    /**
     * Devuelve el correo electrónico del jugador.
     *
     * @return El correo electrónico del jugador.
     */
    public String getCorreo(){

        return correo;
    }
    
    /**
     * Establece el correo electrónico del jugador.
     *
     * @param correo El nuevo correo electrónico del jugador.
     */
    public void setCorreo(String correo){

        this.correo = correo;
    }

    /**
     * Devuelve una representación en cadena del jugador.
     *
     * @return Una cadena que representa la información del jugador.
     */
    public String toString(){

        return "Jugador: nombre y apellidos = "+nombreyapellidos+", fecha de nacimiento = "+fechanacimiento+
        ", fecha de inscripcion = "+fechainscripcion+", correo = "+correo+".";
    }

    /**
     * Calcula la antigüedad del jugador en años desde su fecha de inscripción.
     *
     * @return La antigüedad del jugador en años.
     */
    public int calcularAntiguedad(){

        Date fechaactual = new Date();

        Calendar calendarinscripcion = Calendar.getInstance();
        calendarinscripcion.setTime(fechainscripcion);

        Calendar calendarActual = Calendar.getInstance();
        calendarActual.setTime(fechaactual);

        int antiguedad = calendarActual.get(Calendar.YEAR) - calendarinscripcion.get(Calendar.YEAR);

        if (calendarActual.get(Calendar.MONTH) < calendarinscripcion.get(Calendar.MONTH) ||
        (calendarActual.get(Calendar.MONTH) == calendarinscripcion.get(Calendar.MONTH) && 
        calendarActual.get(Calendar.DAY_OF_MONTH) < calendarinscripcion.get(Calendar.DAY_OF_MONTH))){

            antiguedad--;
        }

        return antiguedad;
    }

    /**
     * Crea un jugador a partir de una línea de texto con formato específico.
     *
     * @param linea La línea de texto que contiene la información del jugador.
     * @return Un objeto {@code Jugador} con la información extraída de la línea de texto.
     * @throws ParseException Si hay un error al parsear las fechas.
     */
    public static Jugador fromTexto(String linea)throws ParseException {
    	
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String[] partes = linea.split(",");
        Jugador jugador = new Jugador();
        jugador.nombreyapellidos = partes[0]; 
        jugador.fechanacimiento = formatter.parse(partes[1]); 
        jugador.fechainscripcion = formatter.parse(partes[2]);
        jugador.correo = partes[3];

        return jugador;
    }
}
