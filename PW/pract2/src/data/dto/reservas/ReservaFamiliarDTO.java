package data.dto.reservas;

import java.util.Date;

public class ReservaFamiliarDTO extends ReservaDTO{

    private int n_ninos;
    private int n_adultos;

    /**
     * Constructor por defecto que inicializa una nueva reserva familiar sin datos.
     */
    public ReservaFamiliarDTO(){}
    
    public ReservaFamiliarDTO(String id_user, Date fecha_hora, int duracion) {
    	this.id_user=id_user;
    	this.fecha_hora=fecha_hora;
    	this.duracion=duracion;
    }

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

}
