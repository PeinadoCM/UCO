package ejer2;

import java.util.Date;


/**
 * La clase {@code ReservaIndividual} es una implementación concreta del patrón
 * de diseño Factory que permite crear diferentes tipos de reservas individuales
 * (infantil, familiar y de adultos).
 */
public class ReservaIndividual extends ReservaCreator{

	/**
     * Crea una reserva infantil con los parámetros proporcionados.
     *
     * @param id_user El identificador del usuario que realiza la reserva.
     * @param fecha_hora La fecha y hora en que se realiza la reserva.
     * @param duracion La duración de la reserva en minutos.
     * @param nombre_pista El nombre de la pista a reservar.
     * @param descuento El descuento aplicado a la reserva.
     * @param n_ninos El número de niños incluidos en la reserva.
     * @return Una instancia de {@code ReservaInfantil} con la información proporcionada.
     */
    @Override    
    public ReservaInfantil crearReservaInfantil(String id_user, Date fecha_hora, int duracion, String nombre_pista, float descuento,int n_ninos){

        
        ReservaInfantil reserva = new ReservaInfantil();
        reserva.setId_user(id_user);
        reserva.setFecha_hora(fecha_hora);
        reserva.setDuracion(duracion);
        reserva.setNombrePista(nombre_pista);
        reserva.setPrecio(reserva.AsignarPrecio(duracion));
        reserva.setDescuento(descuento);
        reserva.setNinos(n_ninos);


        return reserva;
    }

    /**
     * Crea una reserva familiar con los parámetros proporcionados.
     *
     * @param id_user El identificador del usuario que realiza la reserva.
     * @param fecha_hora La fecha y hora en que se realiza la reserva.
     * @param duracion La duración de la reserva en minutos.
     * @param nombre_pista El nombre de la pista a reservar.
     * @param descuento El descuento aplicado a la reserva.
     * @param n_ninos El número de niños incluidos en la reserva.
     * @param n_adultos El número de adultos incluidos en la reserva.
     * @return Una instancia de {@code ReservaFamiliar} con la información proporcionada.
     */
    @Override
    public ReservaFamiliar crearReservaFamiliar(String id_user, Date fecha_hora, int duracion, String nombre_pista, float descuento, int n_ninos, int n_adultos){

        ReservaFamiliar reserva = new ReservaFamiliar();
        reserva.setId_user(id_user);
        reserva.setFecha_hora(fecha_hora);
        reserva.setDuracion(duracion);
        reserva.setNombrePista(nombre_pista);
        reserva.setPrecio(reserva.AsignarPrecio(duracion));
        reserva.setDescuento(descuento);
        reserva.setNinos(n_ninos);
        reserva.setAdultos(n_adultos);

        return reserva;
    }

    /**
     * Crea una reserva de adultos con los parámetros proporcionados.
     *
     * @param id_user El identificador del usuario que realiza la reserva.
     * @param fecha_hora La fecha y hora en que se realiza la reserva.
     * @param duracion La duración de la reserva en minutos.
     * @param nombre_pista El nombre de la pista a reservar.
     * @param descuento El descuento aplicado a la reserva.
     * @param n_adultos El número de adultos incluidos en la reserva.
     * @return Una instancia de {@code ReservaAdultos} con la información proporcionada.
     */
    @Override
    public ReservaAdultos crearReservaAdultos(String id_user, Date fecha_hora, int duracion, String nombre_pista, float descuento,int n_adultos){

        ReservaAdultos reserva = new ReservaAdultos();
        reserva.setId_user(id_user);
        reserva.setFecha_hora(fecha_hora);
        reserva.setDuracion(duracion);
        reserva.setNombrePista(nombre_pista);
        reserva.setPrecio(reserva.AsignarPrecio(duracion));
        reserva.setDescuento(descuento);
        reserva.setAdultos(n_adultos);

        
        return reserva;
    }

}
