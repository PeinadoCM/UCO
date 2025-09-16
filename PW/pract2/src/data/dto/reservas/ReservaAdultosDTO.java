package data.dto.reservas;


/**
 * La clase {@code ReservaAdultosDTO} extiende la clase {@code ReservaDTO} 
 * y representa una reserva específica para un grupo de adultos.
 */
public class ReservaAdultosDTO extends ReservaDTO{
    
    private int n_adultos;

    /**
     * Constructor por defecto que inicializa una nueva reserva para adultos sin datos.
     */
    public ReservaAdultosDTO(){}

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
     * @param adultos El nuevo número de adultos.
     */
    public void setAdultos(int adultos){
        this.n_adultos=adultos;
    }

    /**
     * Devuelve una representación en cadena de la reserva para adultos, 
     * incluyendo la información de la reserva general y el número de adultos.
     *
     * @return Una cadena que representa la información de la reserva para adultos.
     */
    public String toString(){
        String info=super.toString();
        if(this.n_adultos!=0){
            info+=" numero de adultos "+this.n_adultos;
        }
        return info;
    }

}
