package ejer2;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ReservaFamiliar extends Reserva{

    private int n_ninos;
    private int n_adultos;

    /**
     * Constructor por defecto que inicializa una nueva reserva familiar sin datos.
     */
    public ReservaFamiliar(){}

    /**
     * Devuelve el número de niños en la reserva.
     *
     * @return El número de niños.
     */
    public int getNinos(){
        return n_ninos;
    }

    /**
     * Establece el número de niños en la reserva.
     *
     * @param n_ninos El nuevo número de niños.
     */
    public void setNinos(int n_ninos){
        this.n_ninos=n_ninos;
    }

    /**
     * Devuelve el número de adultos en la reserva.
     *
     * @return El número de adultos.
     */
    public int getAdultos(){
        return n_adultos;
    }

    /**
     * Establece el número de adultos en la reserva.
     *
     * @param n_adultos El nuevo número de adultos.
     */
    public void setAdultos(int n_adultos){
        this.n_adultos=n_adultos;
    }

    /**
     * Devuelve el número total de participantes en la reserva (niños + adultos).
     *
     * @return El número total de participantes.
     */
    public int getParticipantes(){
        return n_ninos+n_adultos;
    }

    /**
     * Devuelve una representación en cadena de la reserva familiar, 
     * incluyendo la información de la reserva general y el número de niños y adultos.
     *
     * @return Una cadena que representa la información de la reserva familiar.
     */
    public String toString(){
        String info=super.toString();
        if(this.n_ninos!=0){
            info+=" numero de niños "+this.n_ninos;
        }
        if(this.n_adultos!=0){
            info+=" numero de adultos "+this.n_adultos;
        }
        return info;
    }

    /**
     * Crea una instancia de {@code ReservaFamiliar} a partir de una cadena de texto.
     *
     * @param linea La cadena de texto que contiene los datos de la reserva.
     * @return Una nueva instancia de {@code ReservaFamiliar}.
     * @throws ParseException Si hay un error en el formato de la fecha.
     */
    public static ReservaFamiliar fromTexto(String linea)throws ParseException {
    	
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String[] partes = linea.split(",");
        ReservaFamiliar reserva = new ReservaFamiliar();
        reserva.id_user = partes[0]; 
        reserva.fecha_hora = formatter.parse(partes[1]); 
        reserva.duracion = Integer.parseInt(partes[2]);
        reserva.nombre_pista = partes[3];
        reserva.precio =Float.parseFloat(partes[4]);
        reserva.descuento = Float.parseFloat(partes[5]);
        reserva.n_adultos = Integer.parseInt(partes[6]);
        reserva.n_ninos = Integer.parseInt(partes[7]);

        return reserva;
    }

}
