package ejer2;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * La clase {@code ReservaInfantil} extiende la clase {@code Reserva} 
 * y representa una reserva específica para un grupo de niños.
 */
public class ReservaInfantil extends Reserva{
    
    private int n_ninos;

    /**
     * Constructor por defecto que inicializa una nueva reserva infantil sin datos.
     */
    public ReservaInfantil(){}

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

    /**
     * Crea una instancia de {@code ReservaInfantil} a partir de una cadena de texto.
     *
     * @param linea La cadena de texto que contiene los datos de la reserva.
     * @return Una nueva instancia de {@code ReservaInfantil}.
     * @throws ParseException Si hay un error en el formato de la fecha.
     */
    public static ReservaInfantil fromTexto(String linea)throws ParseException {
    	
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String[] partes = linea.split(",");
        ReservaInfantil reserva = new ReservaInfantil();
        reserva.id_user = partes[0]; 
        reserva.fecha_hora = formatter.parse(partes[1]); 
        reserva.duracion = Integer.parseInt(partes[2]);
        reserva.nombre_pista = partes[3];
        reserva.precio =Float.parseFloat(partes[4]);
        reserva.descuento = Float.parseFloat(partes[5]);
        reserva.n_ninos = Integer.parseInt(partes[6]);

        return reserva;
    }
}
