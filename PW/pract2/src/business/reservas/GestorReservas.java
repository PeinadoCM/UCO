package business.reservas;


import data.dto.pistas.TipoPista;	
import data.dto.reservas.*;
import data.dao.reservas.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;



/**
 * Clase que gestiona las operaciones relacionadas con las reservas de pistas.
 * 
 * La clase {@code GestorReservas} actúa como un controlador central para manejar las reservas de pistas deportivas,
 * incluidos los procesos de creación, modificación, cancelación y consulta de reservas. Implementa el patrón Singleton,
 * asegurando que solo exista una instancia de la clase durante el ciclo de vida de la aplicación.
 * 
 * Proporciona funcionalidades como:
 * - Verificar la disponibilidad de fechas para reservas.
 * - Crear y gestionar bonos para los usuarios.
 * - Modificar y cancelar reservas existentes.
 * - Consultar el estado de las reservas futuras y pasadas.
 * 
 * La clase interactúa con la base de datos a través de {@link ReservaDAO} para realizar las operaciones de acceso
 * y modificación de los datos de las reservas.
 */
public class GestorReservas{
	
	private static GestorReservas instance;

	 /**
     * Constructor privado para la clase GestorReservas.
     * 
     * Este constructor está marcado como privado para evitar la creación directa de instancias de la clase
     * desde fuera de la clase. Se utiliza para implementar el patrón Singleton, garantizando que solo haya
     * una única instancia de la clase en toda la aplicación.
     * 
     * La instancia única de la clase se crea mediante el método estático {@link GestorReservas#getInstance()}.
     */
	private GestorReservas() {

	}

	/**
     * Retorna la instancia única de la clase GestorReservas (Singleton).
     * 
     * Este método implementa el patrón de diseño Singleton, asegurando que solo exista una instancia
     * de la clase GestorReservas en todo el ciclo de vida de la aplicación. Si la instancia aún no ha sido
     * creada, se crea una nueva instancia y se retorna; si ya existe, simplemente se retorna la instancia existente.
     * 
     * @return La instancia única de la clase GestorReservas.
     */
	public static GestorReservas getInstance() {
		if(instance == null) {
			instance = new GestorReservas();
		}
		return instance;
	}

	/**
     * Crea un bono de reservas para un usuario, asociado a un tipo de pista específico.
     * 
     * Este método genera un bono de reservas para el usuario identificado por su `idUsuario`. 
     * El bono está vinculado a un tipo de pista determinado, especificado por el parámetro `tipo`.
     * 
     * @param idUsuario El identificador único del usuario que recibirá el bono.
     * @param tipo El tipo de pista asociado al bono (puede ser, por ejemplo, una pista para adultos, infantiles, etc.).
     * @return Retorna `true` si el bono fue creado exitosamente, de lo contrario retorna `false`.
     */
	public boolean crearBonoReservas(String idUsuario, TipoPista tipo) {
		
		ReservaDAO crear_bono= new ReservaDAO();
		return crear_bono.createNewBono(idUsuario,tipo);
    }
	
	/**
     * Muestra el número de reservas futuras para diferentes tipos de usuarios (infantiles, adultos y familiares).
     * 
     * Este método consulta el número de reservas futuras (es decir, con fechas posteriores al día actual) 
     * clasificadas por tipo (infantil, adulto y familiar). Luego, imprime en consola el número de reservas 
     * de cada tipo que existen para el futuro.
     * 
     * @return No retorna ningún valor. Imprime en consola mostrando la cantidad de reservas futuras
     *         por cada tipo de usuario (infantil, adulto, familiar).
     */
	public void mostrarReservasFuturas() {
		ReservaDAO reservaDAO = new ReservaDAO(); 
		java.sql.Date fecha= new java.sql.Date(new Date().getTime());
		int[] cont_reservas=reservaDAO.requestContReservasFuturas(fecha);
		if(cont_reservas[0] == 0) {
			System.out.println("No hay reservas infantiles futuras");
		}
		else {
			System.out.println("Hay " + cont_reservas[0] + " reservas infantiles futuras");
		}
		if(cont_reservas[1] == 0) {
			System.out.println("No hay reservas de adultos futuras");
		}
		else {
			System.out.println("Hay " + cont_reservas[1] + " reservas de adultos futuras");
		}
		if(cont_reservas[2] == 0) {
			System.out.println("No hay reservas familiares futuras");		
		}
		else {
			System.out.println("Hay " + cont_reservas[2] + " reservas familiares futuras");
		}
	}
	
	 /**
     * Muestra el número de reservas para diferentes tipos de usuarios (infantiles, adultos y familiares) 
     * en una pista específica en una fecha determinada.
     * 
     * Este método consulta las reservas existentes para una pista y una fecha específica. 
     * Luego, muestra el número de reservas clasificadas por tipo (infantil, adulto y familiar).
     * 
     * @param fecha La fecha de las reservas que se desean consultar.
     * @param nombre_pista El nombre de la pista para la que se desean mostrar las reservas.
     * 
     * @return No retorna ningún valor. Imprime el resultado en consola mostrando la cantidad de reservas
     *         por cada tipo de usuario en la pista indicada para la fecha dada.
     */
	public void mostrarReservas(Date fecha, String nombre_pista) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); 
		String fechaFormateada = formatter.format(fecha);
		ReservaDAO reservaDAO = new ReservaDAO(); 
		java.sql.Date fechasql= new java.sql.Date(fecha.getTime());
		int[] cont_reservas=reservaDAO.requestContReservasFechaNombre(fechasql, nombre_pista);
		if(cont_reservas[0] == 0) {
			System.out.println("No hay reservas infantiles en la pista " + nombre_pista + " en la fecha "+ fechaFormateada);
		}
		else {
			System.out.println("Hay " + cont_reservas[0] + " reservas infantiles en la pista " + nombre_pista + " en la fecha "+ fechaFormateada);
		}
		if(cont_reservas[1] == 0) {
			System.out.println("No hay reservas de adultos en la pista " + nombre_pista + " en la fecha "+ fechaFormateada);
		}
		else {
			System.out.println("Hay " + cont_reservas[1] + " reservas de adultos en la pista " + nombre_pista + " en la fecha "+ fechaFormateada);
		}
		if(cont_reservas[2] == 0) {
			System.out.println("No hay reservas familiares en la pista " + nombre_pista + " en la fecha "+ fechaFormateada);		
		}
		else {
			System.out.println("Hay " + cont_reservas[2] + " reservas familiares en la pista " + nombre_pista + " en la fecha "+ fechaFormateada);
		}
	}
	
	/**
     * Modifica una reserva existente para una pista específica en la fecha indicada.
     * 
     * Este método permite modificar una reserva existente cambiando su fecha, duración y precio. 
     * Si la modificación es exitosa, el método devuelve `true`; de lo contrario, devuelve `false`.
     * 
     * @param nombrePista El nombre de la pista cuya reserva se desea modificar.
     * @param fecha La fecha original de la reserva que se desea modificar.
     * @param nuevaFecha La nueva fecha para la reserva.
     * @param nuevaDuracion La nueva duración de la reserva en minutos.
     * @param precio El nuevo precio para la reserva.
     * 
     * @return `true` si la reserva fue modificada correctamente, `false` si no se pudo modificar la reserva.
     */
	public boolean modificarReserva(String nombrePista, Date fecha, Date nuevaFecha, int nuevaDuracion, float precio) {
        ReservaDAO reservaDAO = new ReservaDAO();
        return reservaDAO.modificarReserva(nombrePista, fecha, nuevaFecha, nuevaDuracion, precio);
    }

	 /**
     * Cancela una reserva en la pista especificada para la fecha dada.
     * 
     * Este método solicita al sistema cancelar una reserva asociada a un nombre de pista y una fecha específica.
     * Si la cancelación es exitosa, el método devuelve `true`; de lo contrario, devuelve `false`.
     * 
     * @param nombrePista El nombre de la pista para la cual se desea cancelar la reserva.
     * @param fecha La fecha de la reserva que se desea cancelar.
     * 
     * @return `true` si la reserva fue cancelada correctamente, `false` si no se pudo cancelar la reserva.
     */
    public boolean cancelarReserva(String nombrePista, Date fecha) {
        ReservaDAO reservaDAO = new ReservaDAO();
        return reservaDAO.cancelarReserva(nombrePista, fecha);
    }

    /**
     * Comprueba si la fecha y hora proporcionada está al menos 24 horas en el futuro.
     * 
     * Este método verifica si la fecha y hora indicadas están al menos 24 horas antes de la fecha y hora actuales. 
     * Si la fecha proporcionada es menor a 24 horas en el futuro, el método devuelve `false`. 
     * Si es al menos 24 horas en el futuro, devuelve `true`.
     * 
     * @param fecha_hora La fecha y hora que se desea comprobar.
     * 
     * @return `true` si la fecha y hora proporcionada es al menos 24 horas posterior a la fecha y hora actuales, 
     *         `false` si es menos de 24 horas en el futuro.
     */
	public boolean comprobar24hantelacion(Date fecha_hora){
	 	Calendar today = Calendar.getInstance();
		
	 	today.add(Calendar.DAY_OF_MONTH, 1);
		
		if (fecha_hora.before(today.getTime())) {
			return false;
	 	} 
	 	else {
	 		return true;
	 	}
	}
	
	/**
     * Comprueba si una pista está disponible en una fecha y hora específica para una duración determinada.
     * 
     * Este método verifica si hay conflictos de reservas en la pista para una fecha y hora especificadas. 
     * Si la nueva reserva se solapa con una reserva existente en la misma pista, el método devolverá `false`. 
     * Si la pista está disponible, el método devolverá `true`.
     * 
     * @param nombre_pista El nombre de la pista que se va a comprobar.
     * @param fecha_hora La fecha y hora de inicio de la nueva reserva.
     * @param duracion La duración de la reserva en minutos.
     * 
     * @return `true` si la pista está disponible para la nueva reserva en la fecha y hora indicadas, 
     *         `false` si ya hay una reserva existente que interfiera con la nueva.
     */
	public boolean comprobarDisponibilidadFecha(String nombre_pista, Date fecha_hora, int duracion) {
		ReservaDAO reservaDAO = new ReservaDAO();
		java.sql.Date fechasql= new java.sql.Date(fecha_hora.getTime());
		ArrayList<ReservaFamiliarDTO> reservas= reservaDAO.requestReservasByFechaPista(nombre_pista, fechasql);
		
		Calendar calNuevaReserva = Calendar.getInstance();
		calNuevaReserva.setTime(fecha_hora);
		calNuevaReserva.add(Calendar.MINUTE, duracion);
		Date fechaFinNuevaReserva = calNuevaReserva.getTime();
		
		for(ReservaFamiliarDTO reserva : reservas) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(reserva.getFecha_hora());
			calendar.add(Calendar.MINUTE, reserva.getDuracion());
			Date fechaFin = calendar.getTime();
			// Verifica si la nueva reserva interfiere con las reservas existentes 
			if ((fecha_hora.before(fechaFin) && fechaFinNuevaReserva.after(reserva.getFecha_hora())) || fecha_hora.equals(reserva.getFecha_hora()) || fechaFinNuevaReserva.equals(reserva.getFecha_hora()) || fecha_hora.equals(fechaFin)) {
				return false; 
			}
		}
		return true;
	}
	
	/**
     * Asigna un precio en función de la duración de la reserva.
     * 
     * Este método devuelve el precio de la reserva basado en su duración. Si la duración es de 60 minutos,
     * el precio será 20 unidades monetarias; si es de 90 minutos, el precio será 30 unidades monetarias; 
     * de lo contrario, el precio será 40 unidades monetarias.
     * 
     * @param duracion La duración de la reserva en minutos. Puede ser 60, 90 o cualquier otro valor.
     * 
     * @return El precio de la reserva basado en la duración especificada.
     *         - Si la duración es 60 minutos, devuelve 20.
     *         - Si la duración es 90 minutos, devuelve 30.
     *         - Si la duración es 1200 minutos, devuelve 40.
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
	
	/**
     * Añade una reserva a un bono para un usuario en una pista en una fecha y hora específicas.
     * 
     * Este método permite añadir una nueva reserva a un bono previamente existente. Se especifican los 
     * detalles de la reserva, como la pista, la fecha y hora, la duración, el número de niños y adultos, 
     * el descuento aplicable y el precio final. La reserva se asocia con el bono mediante la invocación de 
     * un método en la clase `ReservaDAO`.
     * 
     * @param id_bono El ID del bono al que se añadirá la reserva.
     * @param correo El correo electrónico del usuario que realiza la reserva.
     * @param nombre_pista El nombre de la pista en la que se realiza la reserva.
     * @param fecha_hora La fecha y hora de inicio de la reserva.
     * @param duracion La duración de la reserva en minutos.
     * @param nniños El número de niños que forman parte de la reserva.
     * @param nadultos El número de adultos que forman parte de la reserva.
     * @param descuento El descuento aplicado a la reserva. Si no hay descuento, se pasa 0.
     * @param precio El precio final de la reserva, después de aplicar cualquier descuento.
     * 
     * @return `true` si la reserva fue añadida correctamente al bono, `false` si hubo un error al añadir la reserva.
     * 
     * @see ReservaDAO#createAñadirReservaABono(int, String, String, java.sql.Date, java.sql.Time, int, int, int, float, float)
     */
	public boolean añadirReservaABono(int id_bono, String correo, String nombre_pista, Date fecha_hora, int duracion,int nniños,int nadultos,float descuento, float precio){

		ReservaDAO reservaDAO= new ReservaDAO();
		java.sql.Date fechasql= new java.sql.Date(fecha_hora.getTime());
		java.sql.Time horasql= new java.sql.Time(fecha_hora.getTime());

		return reservaDAO.createAñadirReservaABono(id_bono,correo,nombre_pista,fechasql,horasql,duracion,nniños,nadultos,descuento,precio);
	}
	
	/**
     * Añade una reserva individual para un usuario en una pista en una fecha y hora específicas.
     * 
     * Este método permite crear una nueva reserva individual para un usuario, especificando la pista, 
     * la fecha y hora, la duración, el número de niños y adultos, el descuento aplicable y el precio final. 
     * La reserva se crea en la base de datos mediante la invocación de un método en la clase `ReservaDAO`.
     * 
     * @param correo El correo electrónico del usuario que realiza la reserva.
     * @param nombre_pista El nombre de la pista en la que se realiza la reserva.
     * @param fecha_hora La fecha y hora de inicio de la reserva.
     * @param duracion La duración de la reserva en minutos.
     * @param nniños El número de niños que forman parte de la reserva.
     * @param nadultos El número de adultos que forman parte de la reserva.
     * @param descuento El descuento aplicado a la reserva. Si no hay descuento, se pasa 0.
     * @param precio El precio final de la reserva, después de aplicar cualquier descuento.
     * 
     * @return `true` si la reserva fue añadida correctamente, `false` si hubo un error al añadir la reserva.
     * 
     * @see ReservaDAO#createReservaIndividual(String, String, java.sql.Date, java.sql.Time, int, int, int, float, float)
     */
	public boolean añadirReservaIndividual(String correo, String nombre_pista, Date fecha_hora, int duracion,int nniños,int nadultos,float descuento, float precio){

		ReservaDAO crear_reserva_individual= new ReservaDAO();
		java.sql.Date fechasql= new java.sql.Date(fecha_hora.getTime());
		java.sql.Time horasql= new java.sql.Time(fecha_hora.getTime());

		return crear_reserva_individual.createReservaIndividual(correo,nombre_pista,fechasql,horasql,duracion,nniños,nadultos,descuento,precio);
	}
	
	 /**
     * Comprueba si un usuario tiene un bono válido para una pista específica en una fecha determinada
     * y devuelve el ID del bono.
     * 
     * Este método consulta la base de datos para verificar si el usuario, identificado por su correo electrónico, 
     * tiene un bono válido para la pista y la fecha proporcionadas. Si existe un bono válido, devuelve su ID. 
     * Si no existe bono o el bono no es válido para la pista y fecha especificadas, devuelve 0.
     * 
     * @param correo El correo electrónico del usuario cuyo bono se va a comprobar.
     * @param nombre_pista El nombre de la pista para la cual se está verificando el bono.
     * @param fecha La fecha en la que se está verificando la validez del bono.
     * 
     * @return El ID del bono si existe un bono válido para la pista y fecha, o 0 si no existe bono válido.
     * 
     * @see ReservaDAO#requestBonoCorreoPista(String, java.sql.Date, String)
     */
	public int comprobarBonoPista(String correo, String nombre_pista, Date fecha) {
		java.sql.Date fechasql= new java.sql.Date(fecha.getTime());
		ReservaDAO reservaDAO= new ReservaDAO();
		
		return reservaDAO.requestBonoCorreoPista(correo, fechasql, nombre_pista);
		
	}
	
	 /**
     * Comprobar y actualizar la fecha de caducidad de un bono, añadiendo un año a la fecha proporcionada.
     * 
     * Este método toma una fecha de referencia, le añade un año y luego actualiza la fecha de caducidad 
     * del bono especificado en la base de datos. El bono identificado por el `id_bono` será actualizado 
     * con la nueva fecha de caducidad calculada.
     * 
     * @param id_bono El identificador único del bono cuyo período de caducidad se va a actualizar.
     * @param fecha La fecha original que se usará para calcular la nueva fecha de caducidad. 
     *              Se le añadirá un año para establecer la nueva fecha.
     * 
     * @see ReservaDAO#updateFechaCaducidad(int, java.sql.Date)
     */
	public void comprobarFechaCaducidadBono(int id_bono, Date fecha) {
		ReservaDAO reservaDAO= new ReservaDAO();
		Calendar cal = Calendar.getInstance();
		cal.setTime(fecha);
		// Añadir un año 
		cal.add(Calendar.YEAR, 1); 
		// Obtener la nueva fecha 
		Date fechaActualizada = cal.getTime();
		java.sql.Date fechasql= new java.sql.Date(fechaActualizada.getTime());
		reservaDAO.updateFechaCaducidad(id_bono, fechasql);
	}
	
	 /**
     * Muestra los bonos asociados a un usuario específico.
     * 
     * Este método consulta la base de datos para obtener todos los bonos que están asociados al
     * correo del usuario proporcionado y luego muestra los detalles de los mismos en la consola. 
     * Si el usuario no tiene bonos asociados, se imprime un mensaje indicándolo.
     * 
     * @param correo El correo electrónico del usuario cuyos bonos se quieren mostrar.
     *               Este parámetro es utilizado para realizar la consulta a la base de datos.
     * 
     * @see ReservaDAO#requestBonos(String)
     */
	public void mostrarBonos(String correo) {
		ReservaDAO reservaDAO= new ReservaDAO();
		ArrayList<Bono> bonos=reservaDAO.requestBonos(correo);
		if(bonos.isEmpty()) {
			System.out.println("El usuario " + correo + " no tinen ningun bono");
		}
		else {
			System.out.println("Listando bonos:");
			for(Bono bono: bonos) {
				System.out.println(bono.toString());
			}
		}
	}

}
