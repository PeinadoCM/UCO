package ejer3;

import java.util.Date;	
import java.util.Calendar;
import ejer1.TipoPista;
import ejer2.Reserva;

/**
 * La clase {@code Bono} representa un bono que permite a un usuario acceder a pistas.
 * Contiene información sobre el usuario, el tipo de pista, la fecha de caducidad,
 * las reservas realizadas y el número de sesiones restantes.
 */
public class Bono{
    private String idUsuario;
    private TipoPista tipo;
    private Date fecha_caducidad;
    private String[] nombre_pistas;
    private Date[] fechas;
    private int sesionesDisponibles;

    /**
     * Crea un nuevo bono sin asociarlo a un usuario o tipo de pista.
     * 
     * <p>Este constructor inicializa el número máximo de sesiones disponibles y crea 
     * los arrays necesarios para almacenar los nombres de las pistas y las fechas de 
     * las reservas. También se establece la fecha de caducidad inicial.</p>
     */
    public Bono() {
        nombre_pistas = new String[5];
        fechas = new Date[5];
        fecha_caducidad=new Date();
        this.sesionesDisponibles = 5;
    }

    /**
     * Crea un nuevo bono para el usuario especificado y del tipo de pista dado.
     * 
     * <p>Este constructor inicializa el identificador del usuario, el tipo de pista, 
     * establece el número máximo de sesiones disponibles y crea los arrays necesarios 
     * para almacenar los nombres de las pistas y las fechas de las reservas. También 
     * se establece la fecha de caducidad inicial.</p>
     * 
     * @param idUsuario El identificador del usuario que posee el bono.
     * @param tipo El tipo de pista asociado al bono.
     */
    public Bono(String idUsuario, TipoPista tipo) {
        this.idUsuario = idUsuario;
        this.tipo = tipo;
        nombre_pistas = new String[5];
        fechas = new Date[5];
        fecha_caducidad=new Date();
        this.sesionesDisponibles=5;
    }
    
    /**
     * Añade una reserva al bono para una pista específica en una fecha determinada.
     * 
     * <p>Si es la primera reserva añadida al bono, se establece la fecha de caducidad 
     * a un año a partir de la fecha de la reserva. El número de sesiones disponibles 
     * se decrementa en uno después de agregar la reserva.</p>
     * 
     * @param nombrePista El nombre de la pista para la que se realiza la reserva.
     * @param fecha La fecha en la que se realiza la reserva.
     */
    public void añadirReserva(String nombrePista, Date fecha) {
        if(sesionesDisponibles == 5) {
        	Calendar calendar = Calendar.getInstance();
            calendar.setTime(fecha);
            calendar.add(Calendar.YEAR, 1);
        	fecha_caducidad=calendar.getTime();
        }
        int i = 5 - sesionesDisponibles;
        nombre_pistas[i] = nombrePista;
        fechas[i] = fecha;
        sesionesDisponibles--;
        
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
     * Devuelve el nombre de la pista almacenada en la posición especificada del bono.
     *
     * @param pos La posición del array de nombres de pistas de la que se desea obtener el nombre.
     * @return El nombre de la pista en la posición especificada.
     * @throws ArrayIndexOutOfBoundsException Si la posición está fuera de los límites del array.
     */
    public String getNombrePista(int pos) {
    	return nombre_pistas[pos];
    }
    
    /**
     * Devuelve la fecha almacenada en la posición especificada del bono.
     *
     * @param pos La posición del array de fechas de la que se desea obtener la fecha.
     * @return La fecha en la posición especificada.
     * @throws ArrayIndexOutOfBoundsException Si la posición está fuera de los límites del array.
     */
    public Date getFecha(int pos) {
    	return fechas[pos];
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
     * Establece la fecha en la posición especificada del bono.
     *
     * @param pos La posición en la que se desea establecer la fecha.
     * @param fecha La fecha que se quiere asignar a la posición especificada.
     */
    public void setFecha(int pos, Date fecha) {
    	this.fechas[pos]=fecha;
    }
    
    /**
     * Elimina una reserva del bono en la posición especificada.
     * 
     * <p>Este método desplaza las reservas hacia la izquierda para 
     * mantener el orden de las reservas, de modo que no queden huecos 
     * en el array.</p>
     *
     * @param pos La posición de la reserva que se desea cancelar.
     */
    public void cancelarReserva(int pos) {
        for(int i=pos; i < 5-sesionesDisponibles; i++) {
        	nombre_pistas[pos]=nombre_pistas[pos+1];
        	fechas[pos]=fechas[pos+1];
        }
        sesionesDisponibles++;
    }
    
}
