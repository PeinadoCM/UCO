package business.jugadores;

import data.dto.jugadores.JugadorDTO;			
import data.dao.jugadores.JugadorDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

/**
 * Clase encargada de gestionar las operaciones relacionadas con los usuarios del sistema.
 * 
 * Esta clase implementa el patrón de diseño Singleton, lo que garantiza que solo 
 * haya una única instancia de la clase {@code GestorUsuarios} en la aplicación. 
 * Proporciona métodos para gestionar las operaciones de alta, modificación y listado 
 * de usuarios, así como la verificación de la edad y el cálculo de descuentos para los mismos.
 */
public class GestorUsuarios {
	
	private static GestorUsuarios instance;
	
	/**
	 * Constructor privado de la clase {@code GestorUsuarios}.
	 * 
	 * Este constructor está marcado como privado para evitar la creación de nuevas 
	 * instancias de la clase desde fuera de la misma. Está diseñado para ser utilizado 
	 * exclusivamente dentro del método {@code getInstance()} para implementar el patrón 
	 * Singleton y garantizar que solo exista una única instancia de la clase en la aplicación.
	 */
    private GestorUsuarios() {

	}
	
    /**
     * Obtiene la instancia única de la clase {@code GestorUsuarios}.
     * 
     * Este método implementa el patrón de diseño Singleton, asegurando que solo 
     * exista una instancia de {@code GestorUsuarios} en toda la aplicación.
     * Si la instancia aún no ha sido creada, se crea una nueva; de lo contrario, 
     * se retorna la instancia existente.
     * 
     * @return la instancia única de {@code GestorUsuarios}.
     */
	public static GestorUsuarios getInstance() {
		if(instance == null) {
			instance = new GestorUsuarios();
		}
		return instance;
	}

	/**
	 * Registra un nuevo usuario en la base de datos.
	 * 
	 * Este método crea un nuevo usuario con el nombre, la fecha de nacimiento
	 * y el correo proporcionados.
	 * 
	 * @param nombre         el nombre del usuario a registrar.
	 * @param fechanacimiento la fecha de nacimiento del usuario a registrar.
	 * @param correo         el correo electrónico del usuario a registrar.
	 * @return {@code true} si el usuario fue creado con éxito, {@code false} en caso contrario.
	 */
    public boolean AltaUsuario(String nombre,Date fechanacimiento, String correo){
        JugadorDAO crear_usuario = new JugadorDAO();
        java.sql.Date fecha= new java.sql.Date(fechanacimiento.getTime());
        return crear_usuario.createNewUser(correo, nombre,fecha);
    }

    /**
     * Lista todos los usuarios registrados en la base de datos.
     * 
     * Si no hay usuarios registrados, se muestra un mensaje indicándolo.
     * En caso contrario, se imprime la información de cada usuario.
     */
    public void listarUsuarios() {    
        JugadorDAO todos_usuarios = new JugadorDAO();                
        ArrayList<JugadorDTO> jugadores = todos_usuarios.requestAllUsers();
        if(jugadores.isEmpty()){
            System.out.println("No hay jugadores registrados");
        }
        else{
            for(JugadorDTO usuario : jugadores){
                System.out.println(usuario.toString());
            }
        }
    }

    /**
     * Modifica los datos de un usuario en la base de datos.
     * 
     * @param nombre          el nuevo nombre del usuario.
     * @param fechanacimiento la nueva fecha de nacimiento del usuario.
     * @param correo          el correo electrónico del usuario a modificar.
     * @return {@code true} si la modificación se realizó con éxito, {@code false} en caso contrario.
     */
    public boolean modificarUsuario(String nombre,Date fechanacimiento, String correo){

        java.sql.Date fecha= new java.sql.Date(fechanacimiento.getTime());
        JugadorDAO modificar_usuarios = new JugadorDAO();              
        return  modificar_usuarios.modifyUser(nombre,fecha,correo);
    } 
    
    /**
     * Comprueba si un usuario es mayor de edad (18 años o más) en función de su fecha de nacimiento.
     * 
     * @param correo el correo electrónico del usuario cuya mayoría de edad se va a verificar.
     * @return {@code true} si el usuario es mayor de edad, {@code false} si no lo es o si no tiene fecha de nacimiento registrada.
     */
    public boolean comprobarMayorEdad(String correo) {
    	
    	Calendar today = Calendar.getInstance();
        today.add(Calendar.YEAR, -18);

        JugadorDAO jugadorDAO = new JugadorDAO();              
        JugadorDTO usuario = jugadorDAO.requestUser(correo);
        if(usuario.getFechanacimiento() == null) {
        	return false;
        }
        if (usuario.getFechanacimiento().before(today.getTime())) {
            return true;
        } else {
            return false;
        }
    	
    }

    /**
     * Añade o actualiza la fecha de inscripción de un usuario en la base de datos.
     * 
     * @param correo el correo electrónico del usuario cuya fecha de inscripción se va a añadir o actualizar.
     * @param fecha  la nueva fecha de inscripción a asignar al usuario.
     * @return {@code true} si la operación de actualización fue exitosa, {@code false} en caso contrario.
     */
    public boolean añadirFechaInscripcion(String correo, Date fecha) {
    	JugadorDAO jugadorDAO = new JugadorDAO();
    	java.sql.Date fechasql= new java.sql.Date(fecha.getTime());
    	return jugadorDAO.updateFechaInscripcion(correo, fechasql);
    }
    
    /**
     * Calcula el descuento para un usuario en base a su fecha de inscripción.
     * Si el usuario lleva inscrito más de dos años, se aplica un descuento del 10%.
     * Si la fecha de inscripción no está registrada, se asigna la fecha actual como fecha de inscripción.
     * 
     * @param correo el correo electrónico del usuario.
     * @param fecha  la fecha actual utilizada para asignar la fecha de inscripción si no existe.
     * @return el porcentaje de descuento (0.0f si no aplica, 0.1f si aplica).
     */
    public float calcularDescuento(String correo, Date fecha) {
    	
    	Calendar today = Calendar.getInstance();
        today.add(Calendar.YEAR, -2);

        JugadorDAO jugadorDAO = new JugadorDAO();              
        JugadorDTO usuario = jugadorDAO.requestUser(correo);
        if(usuario.getFechainscripcion() == null) {
        	añadirFechaInscripcion(correo,fecha);
        	return 0.0f;
        }
        if (usuario.getFechainscripcion().before(today.getTime())) {
            return 0.1f;
        } else {
            return 0.0f;
        }

    }

}