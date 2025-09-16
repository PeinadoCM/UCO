package ejer2;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * La clase {@code ReservaAdultos} extiende la clase {@code Reserva} 
 * y representa una reserva específica para un grupo de adultos.
 */
public class ReservaAdultos extends Reserva{
    
    private int n_adultos;

    /**
     * Constructor por defecto que inicializa una nueva reserva para adultos sin datos.
     */
    public ReservaAdultos(){}

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

    /**
     * Crea una instancia de {@code ReservaAdultos} a partir de una cadena de texto.
     *
     * @param linea La cadena de texto que contiene los datos de la reserva.
     * @return Una nueva instancia de {@code ReservaAdultos}.
     * @throws ParseException Si hay un error en el formato de la fecha.
     */
    public static ReservaAdultos fromTexto(String linea)throws ParseException {
    	
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String[] partes = linea.split(",");
        ReservaAdultos reserva = new ReservaAdultos();
        reserva.id_user = partes[0]; 
        reserva.fecha_hora = formatter.parse(partes[1]); 
        reserva.duracion = Integer.parseInt(partes[2]);
        reserva.nombre_pista = partes[3];
        reserva.precio =Float.parseFloat(partes[4]);
        reserva.descuento = Float.parseFloat(partes[5]);
        reserva.n_adultos = Integer.parseInt(partes[6]);

        return reserva;
    }
}
