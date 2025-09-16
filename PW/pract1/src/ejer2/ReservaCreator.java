package ejer2;
import java.util.Date;

/**
 * La clase abstracta {@code ReservaCreator} implementa el patrón de diseño Factory,
 * proporcionando métodos abstractos para crear diferentes tipos de reservas.
 * 
 * <p>Este patrón permite la creación de objetos sin especificar la clase exacta
 * del objeto que se va a crear. Las subclases de {@code ReservaCreator} son responsables
 * de implementar la lógica específica para crear instancias de {@code ReservaInfantil}, 
 * {@code ReservaFamiliar} y {@code ReservaAdultos}.</p>
 */
public abstract class ReservaCreator {

	/**
     * Método abstracto para crear una reserva infantil.
     *
     * @param id_user El identificador del usuario que realiza la reserva.
     * @param fecha_hora La fecha y hora en que se realiza la reserva.
     * @param duracion La duración de la reserva en minutos.
     * @param nombre_pista El nombre de la pista a reservar.
     * @param descuento El descuento aplicado a la reserva.
     * @param n_ninos El número de niños incluidos en la reserva.
     * @return Una instancia de {@code ReservaInfantil} creada con los parámetros proporcionados.
     */
    public abstract ReservaInfantil crearReservaInfantil(String id_user, Date fecha_hora, int duracion, String nombre_pista, float descuento, int n_ninos);
    
    /**
     * Método abstracto para crear una reserva familiar.
     *
     * @param id_user El identificador del usuario que realiza la reserva.
     * @param fecha_hora La fecha y hora en que se realiza la reserva.
     * @param duracion La duración de la reserva en minutos.
     * @param nombre_pista El nombre de la pista a reservar.
     * @param descuento El descuento aplicado a la reserva.
     * @param n_ninos El número de niños incluidos en la reserva.
     * @param n_adultos El número de adultos incluidos en la reserva.
     * @return Una instancia de {@code ReservaFamiliar} creada con los parámetros proporcionados.
     */
    public abstract ReservaFamiliar crearReservaFamiliar(String id_user, Date fecha_hora, int duracion, String nombre_pista, float descuento, int n_ninos, int n_adultos);
    
    /**
     * Método abstracto para crear una reserva de adultos.
     *
     * @param id_user El identificador del usuario que realiza la reserva.
     * @param fecha_hora La fecha y hora en que se realiza la reserva.
     * @param duracion La duración de la reserva en minutos.
     * @param nombre_pista El nombre de la pista a reservar.
     * @param descuento El descuento aplicado a la reserva.
     * @param n_adultos El número de adultos incluidos en la reserva.
     * @return Una instancia de {@code ReservaAdultos} creada con los parámetros proporcionados.
     */
    public abstract ReservaAdultos crearReservaAdultos(String id_user, Date fecha_hora, int duracion, String nombre_pista, float descuento, int n_adultos);

}

