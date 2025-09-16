package data.dto.reservas;


import java.util.Date;


/**
 * La clase abstracta {@code ReservaDTO} representa una reserva de una pista deportiva.
 * Contiene información sobre el usuario que realiza la reserva, la fecha y hora, 
 * la duración, el nombre de la pista, el precio y el descuento aplicable.
 */
public abstract class ReservaDTO {
    
    protected String id_user;
    protected Date fecha_hora;
    protected int duracion;
    protected String nombre_pista;
    protected float precio;
    protected float descuento;

    /**
     * Constructor por defecto que inicializa una nueva reserva sin datos.
     */
    public ReservaDTO(){

    }
    

    /**
     * Devuelve el identificador del usuario que realizó la reserva.
     *
     * @return El identificador del usuario.
     */
    public String getId_user(){

        return id_user;
    }
    
    /**
     * Devuelve la fecha y hora de la reserva.
     *
     * @return La fecha y hora de la reserva.
     */
    public Date getFecha_hora(){

        return fecha_hora;
    }
    
    /**
     * Devuelve la duración de la reserva en minutos.
     *
     * @return La duración de la reserva.
     */
    public int getDuracion(){

        return duracion;
    }
    
    /**
     * Devuelve el nombre de la pista reservada.
     *
     * @return El nombre de la pista.
     */
    public String getNombrePista(){

        return nombre_pista;
    }
    
    /**
     * Devuelve el precio de la reserva.
     *
     * @return El precio de la reserva.
     */
    public float getPrecio(){

        return precio;
    }
    
    /**
     * Devuelve el descuento aplicable a la reserva.
     *
     * @return El descuento de la reserva.
     */
    public float getDescuento(){

        return descuento;
    }

    /**
     * Establece el identificador del usuario que realizó la reserva.
     *
     * @param id_user El nuevo identificador del usuario.
     */
    public void setId_user(String id_user){

        this.id_user=id_user;
    }
    
    /**
     * Establece la fecha y hora de la reserva.
     *
     * @param fecha_hora La nueva fecha y hora de la reserva.
     */
    public void setFecha_hora(Date fecha_hora){
        
        this.fecha_hora=fecha_hora;
    }
    
    /**
     * Establece la duración de la reserva.
     *
     * @param duracion La nueva duración de la reserva en minutos.
     */
    public void setDuracion(int duracion){

        this.duracion=duracion;
    }
    
    /**
     * Establece el nombre de la pista reservada.
     *
     * @param nombre_pista El nuevo nombre de la pista.
     */
    public void setNombrePista(String nombre_pista){
        
        this.nombre_pista=nombre_pista;
    }
    
    /**
     * Establece el precio de la reserva.
     *
     * @param precio El nuevo precio de la reserva.
     */
    public void setPrecio(float precio){

        this.precio=precio;
    }
    
    /**
     * Establece el descuento aplicable a la reserva.
     *
     * @param descuento El nuevo descuento de la reserva.
     */

    public void setDescuento(float descuento){

        this.descuento=descuento;
    }

    /**
     * Devuelve una representación en cadena de la reserva.
     *
     * @return Una cadena que representa la información de la reserva.
     */
    public String toString(){

        return "Id del usuario "+this.id_user+", fecha y hora "+this.fecha_hora+
        ", duracion "+this.duracion+", nombre de pista "+this.nombre_pista+", precio "+this.precio+
        ", descuento "+this.descuento;
    }
    
    /**
     * Asigna un precio basado en la duración de la reserva.
     *
     * @param duracion La duración de la reserva en minutos.
     * @return El precio correspondiente a la duración especificada.
     */
    public float AsignarPrecio(int duracion) {
		if(duracion == 60) {
			return 20;
		}
		else if(duracion == 90) {
			return 30;
		}
		else{
			return 40;
		}
    }
    
}
