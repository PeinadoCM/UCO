package data.dto.reservas;


/**
 * La clase {@code ReservaInfantilDTO} extiende la clase {@code ReservaDTO} 
 * y representa una reserva específica para un grupo de niños.
 */
public class ReservaInfantilDTO extends ReservaDTO{
    
    private int n_ninos;

    /**
     * Constructor por defecto que inicializa una nueva reserva infantil sin datos.
     */
    public ReservaInfantilDTO(){}

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
     * @param ninos El nuevo número de niños.
     */
    public void setNinos(int ninos){
        this.n_ninos=ninos;
    }

    /**
     * Devuelve una representación en cadena de la reserva infantil, 
     * incluyendo la información de la reserva general y el número de niños.
     *
     * @return Una cadena que representa la información de la reserva infantil.
     */
    public String toString(){
        String info=super.toString();
        if(this.n_ninos!=0){
            info+=" numero de niños "+this.n_ninos;
        }
        return info;
    }

}
