package data.dao.reservas;

import java.sql.*;						
import java.util.ArrayList;
import java.util.Iterator;

import com.mysql.jdbc.ResultSet;
import data.common.DBConnection;
import data.common.SQLProperties;

import java.util.Date;
import java.util.Calendar;

import data.dto.pistas.TipoPista;
import data.dto.reservas.*;


/**
 * Clase que gestiona las operaciones relacionadas con las reservas y los bonos en la base de datos.
 */
public class ReservaDAO {

	private SQLProperties sqlProperties;

	/**
	 * Constructor de la clase ReservaDAO.
	 * <p>
	 * Este constructor inicializa una nueva instancia de la clase {@link SQLProperties},
	 * la cual se encarga de cargar las consultas SQL desde el archivo de configuración de propiedades. 
	 * </p>
	 * <p>
	 * La instancia de {@link SQLProperties} es utilizada para obtener las consultas SQL necesarias
	 * para interactuar con la base de datos en los métodos de esta clase.
	 * </p>
	 * <p>
	 * En caso de que el archivo de propiedades no esté disponible o haya un problema al cargarlo,
	 * se generará una excepción que puede ser manejada por otras partes de la aplicación.
	 * </p>
	 */
	public ReservaDAO(){

		sqlProperties=new SQLProperties();
	}

    /**
     * Modifica una reserva existente en la base de datos, actualizando tanto la tabla `Reservas` 
     * como la tabla `Reserva_Bonos`, si corresponde. Se puede cambiar la fecha, la duración y el precio de la reserva.
     * 
     * @param nombrePista El nombre de la pista de la reserva que se desea modificar.
     * @param fecha La fecha y hora original de la reserva a modificar.
     * @param nuevaFecha La nueva fecha y hora que se desea asignar a la reserva.
     * @param nuevaDuracion La nueva duración de la reserva en minutos.
     * @param precio El nuevo precio de la reserva.
     * @return `true` si la reserva fue modificada correctamente, `false` si ocurrió un error.
     */
	public boolean modificarReserva(String nombrePista, Date fecha, Date nuevaFecha, int nuevaDuracion, float precio, int n_niños, int n_adultos) {
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();

			//Actualizamos la tabla Reservas
			String query = sqlProperties.getSQLQuery("sql.update.modificarReserva1");
        	PreparedStatement pstmt = connection.prepareStatement(query);
        
        	pstmt.setDate(1, new java.sql.Date(nuevaFecha.getTime()));
        	pstmt.setTime(2, new java.sql.Time(nuevaFecha.getTime()));
        	pstmt.setInt(3, nuevaDuracion);
        	pstmt.setFloat(4, precio);
			pstmt.setInt(5, n_niños);
			pstmt.setInt(6, n_adultos);
        	pstmt.setString(7, nombrePista);
        	pstmt.setDate(8, new java.sql.Date(fecha.getTime()));
        	pstmt.setTime(9, new java.sql.Time(fecha.getTime()));
	
			pstmt.executeUpdate();
	
			//Actualizamos la tabla Reserva_Bonos
			query = sqlProperties.getSQLQuery("sql.update.modificarReserva2");
			pstmt = connection.prepareStatement(query);
	        
        	pstmt.setDate(1, new java.sql.Date(nuevaFecha.getTime()));
        	pstmt.setTime(2, new java.sql.Time(nuevaFecha.getTime()));
        	pstmt.setString(3, nombrePista);
        	pstmt.setDate(4, new java.sql.Date(fecha.getTime()));
        	pstmt.setTime(5, new java.sql.Time(fecha.getTime()));
	
			pstmt.executeUpdate();
			
			
			if (pstmt != null) {
				pstmt.close();
			}
			dbConnection.closeConnection();
		} catch (Exception e) {
			return false;
		}
		return true;
	}  

	/**
	 * Cancela una reserva en la base de datos, eliminando la entrada de la tabla `Reservas` 
	 * y, si la reserva está asociada a un bono, también elimina la entrada correspondiente 
	 * en la tabla `Reserva_Bonos` y actualiza la disponibilidad del bono.
	 * 
	 * @param nombrePista El nombre de la pista de la reserva a cancelar.
	 * @param fecha La fecha y hora de la reserva a cancelar.
	 * @return `true` si la reserva fue cancelada correctamente, `false` si ocurrió un error.
	 */
	public boolean cancelarReserva(String nombrePista, Date fecha) {
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();
	
			//Eliminamos la reserva de la tabla Reservas
			String query = sqlProperties.getSQLQuery("sql.delete.cancelarReserva1");
			PreparedStatement pstmt = connection.prepareStatement(query);
	
			pstmt.setString(1, nombrePista);
			pstmt.setDate(2, new java.sql.Date(fecha.getTime()));
			pstmt.setTime(3, new java.sql.Time(fecha.getTime()));
	
			pstmt.executeUpdate();
			
			//Comprobamos si la reserva es de un bono
			query = sqlProperties.getSQLQuery("sql.select.cancelarReserva");
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, nombrePista);
			pstmt.setDate(2, new java.sql.Date(fecha.getTime()));
			pstmt.setTime(3, new java.sql.Time(fecha.getTime()));
			ResultSet rs = (ResultSet) pstmt.executeQuery();
			int id=-1;
			while(rs.next()){
				id= rs.getInt("Id_bono");
			}

			if(id != -1){
				//Eliminamos la linea de Reserva_Bonos
				query = sqlProperties.getSQLQuery("sql.delete.cancelarReserva2");
				pstmt = connection.prepareStatement(query);
		
				pstmt.setString(1, nombrePista);
				pstmt.setDate(2, new java.sql.Date(fecha.getTime()));
				pstmt.setTime(3, new java.sql.Time(fecha.getTime()));
		
				pstmt.executeUpdate();
				
				//Aumentamos las sesiones disponibles del bono
				query = sqlProperties.getSQLQuery("sql.update.cancelarReserva");
				pstmt = connection.prepareStatement(query);
		
				pstmt.setInt(1, id);
		
				pstmt.executeUpdate();

			}
			if (pstmt != null) {
				pstmt.close();
			}
			dbConnection.closeConnection();
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Crea un nuevo bono asociado a un usuario y un tipo de pista.
	 * 
	 * @param idUsuario El identificador del usuario al que se le va a crear el bono.
	 * @param tipo El tipo de pista asociado al bono (se asume que es un valor del enum {@link TipoPista}).
	 * @return `true` si el bono se crea correctamente en la base de datos, `false` si ocurre un error.
	 */
	public boolean createNewBono(String idUsuario, TipoPista tipo){

		try {
            DBConnection dbConnection = new DBConnection();
            Connection connection = dbConnection.getConnection();
			String query = sqlProperties.getSQLQuery("sql.insert.createNewBono");
			
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, idUsuario);
            pstmt.setString(2, tipo.name());

            pstmt.executeUpdate();
            dbConnection.closeConnection();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al crear el bono: " + e.getMessage());
            return false;
        }
	}
	
	/**
	 * Crea una reserva individual para un usuario, insertando los datos en la tabla `reservas`.
	 * 
	 * @param correo El correo electrónico del usuario que realiza la reserva.
	 * @param nombre_pista El nombre de la pista que se reserva.
	 * @param fecha La fecha en la que se realiza la reserva.
	 * @param hora La hora en la que se realiza la reserva.
	 * @param duracion La duración de la reserva en minutos.
	 * @param nniños El número de niños incluidos en la reserva.
	 * @param nadultos El número de adultos incluidos en la reserva.
	 * @param descuento El descuento aplicado a la reserva, si corresponde.
	 * @param precio El precio total de la reserva.
	 * @return `true` si la reserva se crea correctamente, `false` si ocurre un error.
	 */
	public boolean createReservaIndividual(String correo, String nombre_pista, java.sql.Date fecha ,Time hora, int duracion,int nniños,int nadultos,float descuento,float precio){

		try {
            DBConnection dbConnection = new DBConnection();
            Connection connection = dbConnection.getConnection();
			
			String query = sqlProperties.getSQLQuery("sql.insert.createReservaIndividual");
            PreparedStatement pstmt = connection.prepareStatement(query);
		
			pstmt.setString(1, correo);
            pstmt.setString(2, nombre_pista);
			pstmt.setDate(3, fecha);
			pstmt.setTime(4, hora);
			pstmt.setInt(5, duracion);
			pstmt.setInt(6, nadultos);
			pstmt.setInt(7, nniños);
			pstmt.setFloat(8, descuento);
			pstmt.setFloat(9, precio);
		
            pstmt.executeUpdate();
            dbConnection.closeConnection();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al crear la reserva individual: " + e.getMessage());
            return false;
        }
		
	}

	/**
	 * Crea una reserva para un bono, añadiéndola tanto en la tabla `reserva_bonos` como en la tabla `reservas`.
	 * Además, actualiza la disponibilidad del bono restando una sesión.
	 * 
	 * @param id_bono El identificador del bono al que se va a añadir la reserva.
	 * @param correoUsuario El correo electrónico del usuario que realiza la reserva.
	 * @param nombre_pista El nombre de la pista en la que se realiza la reserva.
	 * @param fecha La fecha en la que se realiza la reserva.
	 * @param hora La hora en la que se realiza la reserva.
	 * @param duracion La duración de la reserva, en minutos.
	 * @param nniños El número de niños incluidos en la reserva.
	 * @param nadultos El número de adultos incluidos en la reserva.
	 * @param descuento El descuento aplicable a la reserva, si corresponde.
	 * @param precio El precio total de la reserva.
	 * @return `true` si la reserva se crea y actualiza correctamente, `false` si ocurre un error.
	 */
	public boolean createAñadirReservaABono(int id_bono, String correoUsuario, String nombre_pista, java.sql.Date fecha ,Time hora, int duracion,int nniños,int nadultos,float descuento,float precio){

		try {
            DBConnection dbConnection = new DBConnection();
            Connection connection = dbConnection.getConnection();
			
            //Insertamos la reserva en la tabla reserva_bonos
			String query = sqlProperties.getSQLQuery("sql.insert.createAñadirReservaABono1");
            PreparedStatement pstmt = connection.prepareStatement(query);
            
			pstmt.setInt(1, id_bono);
            pstmt.setString(2, nombre_pista);
			pstmt.setDate(3, fecha);
			pstmt.setTime(4, hora);

            pstmt.executeUpdate();
            
            //Insertamos la reserva en la tabla reservas
            query = sqlProperties.getSQLQuery("sql.insert.createAñadirReservaABono2");
            pstmt = connection.prepareStatement(query);
		
			pstmt.setString(1, correoUsuario);
            pstmt.setString(2, nombre_pista);
			pstmt.setDate(3, fecha);
			pstmt.setTime(4, hora);
			pstmt.setInt(5, duracion);
			pstmt.setInt(6, nadultos);
			pstmt.setInt(7, nniños);
			pstmt.setFloat(8, descuento);
			pstmt.setFloat(9, precio);
		
            pstmt.executeUpdate();
            
            //Quitamos una sesion disponible en el bonp
            query = sqlProperties.getSQLQuery("sql.update.createAñadirReservaABono");
            pstmt = connection.prepareStatement(query);
            
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fecha);
			calendar.add(Calendar.YEAR, 1);  // Sumar un año

			// Obtener el nuevo java.sql.Date
			java.sql.Date fecha_cad = new java.sql.Date(calendar.getTimeInMillis());

			pstmt.setDate(1, fecha_cad);
			pstmt.setInt(2, id_bono);
            pstmt.executeUpdate();
            
            dbConnection.closeConnection();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al crear la reserva en bono: " + e.getMessage());
            return false;
        }
		
	}
	
	/**
     * Obtiene la fecha de la próxima reserva de un usuario a partir de su correo electrónico.
     * 
     * @param correo El correo electrónico del usuario cuya próxima reserva se desea consultar.
     * @return Un objeto `java.sql.Date` que representa la fecha de la próxima reserva, 
     *         o `null` si no se encuentra ninguna reserva futura.
     */
	public java.sql.Date requestProximaReserva(String correo){
		java.sql.Date fecha = null;
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();
			String query = sqlProperties.getSQLQuery("sql.select.requestProximaReserva");			

			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, correo);
			pstmt.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
			ResultSet rs = (ResultSet) pstmt.executeQuery();

			while (rs.next()) {
				fecha = rs.getDate("Fecha");
			}

			if (pstmt != null){ 
				pstmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (Exception e){
			System.err.println(e);
			e.printStackTrace();
		}
		return fecha;
	}
	
	/**
     * Obtiene el número de reservas asociadas a una lista de correos electrónicos a partir de fechas específicas.
     * 
     * @param correos Una lista de correos electrónicos de usuarios para los cuales se desean consultar las reservas.
     * @param fechas Una lista de fechas que corresponden a los correos electrónicos, utilizadas como filtro para las reservas.
     * @return Un `ArrayList<Integer>` donde cada elemento representa el número de reservas de cada usuario
     *         en la lista de correos electrónicos.
     */
	public ArrayList<Integer> requestNumeroReservas(ArrayList<String> correos, ArrayList<java.sql.Date> fechas){
		ArrayList<Integer> reservas = new ArrayList<>();
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();
			String query = sqlProperties.getSQLQuery("sql.select.requestNumeroReservas");			
			PreparedStatement pstmt = connection.prepareStatement(query);		

			for(int i=0; i < correos.size(); i++){
				Integer reserva=0;
				if(fechas.get(i) != null){
					pstmt.setString(1, correos.get(i));
					pstmt.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
					ResultSet rs = (ResultSet) pstmt.executeQuery();

					while (rs.next()) {
						reserva++;
					}
				}
				reservas.add(reserva);
			}

			if (pstmt != null){ 
				pstmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (Exception e){
			System.err.println(e);
			e.printStackTrace();
		}
		return reservas;
	}

    /**
     * Obtiene una lista de reservas futuras almacenadas en la base de datos.
     * Cada reserva incluye información del usuario, pista, duración, fecha y hora.
     * 
     * @return Una lista (`ArrayList<ReservaFamiliarDTO>`) de objetos `ReservaFamiliarDTO` que representan
     *         las reservas futuras. Si no hay reservas futuras o ocurre un error, retorna una lista vacía.
     */
	public ArrayList<ReservaFamiliarDTO> requestReservasFuturas(){
		ArrayList<ReservaFamiliarDTO> reservas = new ArrayList<>();
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();
			String query = sqlProperties.getSQLQuery("sql.select.requestReservasFuturas");			

			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setDate(1, new java.sql.Date(new java.util.Date().getTime()));
			ResultSet rs = (ResultSet) pstmt.executeQuery();

			while (rs.next()) {
				String correo = rs.getString("Correo_usuario");
				String nombre_pista = rs.getString("Nombre_pista");
				int duracion = rs.getInt("Duracion");
				java.sql.Date fecha = rs.getDate("Fecha");
				java.sql.Time hora=rs.getTime("Hora");
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(fecha);
				cal.set(Calendar.HOUR_OF_DAY, hora.toLocalTime().getHour());
				cal.set(Calendar.MINUTE, hora.toLocalTime().getMinute());
				Date fecha_hora = cal.getTime();

				reservas.add(new ReservaFamiliarDTO(correo, fecha_hora, duracion, nombre_pista));

			}

			if (pstmt != null){ 
				pstmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (Exception e){
			System.err.println(e);
			e.printStackTrace();
		}
		return reservas;
	}

    /**
     * Obtiene una lista de reservas realizadas por un usuario entre dos fechas específicas.
     * Cada reserva incluye información sobre el usuario, pista, duración, número de niños, 
     * número de adultos, y la fecha y hora de la reserva.
     * 
     * @param fecha_inicio La fecha inicial del rango para buscar reservas (inclusive).
     * @param fecha_fin La fecha final del rango para buscar reservas (inclusive).
     * @param userId El identificador del usuario (correo electrónico) cuyas reservas se desean consultar.
     * @return Una lista (`ArrayList<ReservaFamiliarDTO>`) de objetos `ReservaFamiliarDTO` que representan
     *         las reservas dentro del rango de fechas especificado. Retorna una lista vacía si no hay reservas 
     *         o si ocurre un error.
     */
	public ArrayList<ReservaFamiliarDTO> requestReservasEntreFechas(java.sql.Date fecha_inicio, java.sql.Date fecha_fin, String userId){
		ArrayList<ReservaFamiliarDTO> reservas = new ArrayList<>();
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();
			String query = sqlProperties.getSQLQuery("sql.select.requestReservasEntreFechas");			

			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, userId);
			pstmt.setDate(2, fecha_fin);
			pstmt.setDate(3, fecha_inicio);
			ResultSet rs = (ResultSet) pstmt.executeQuery();

			while (rs.next()) {
				String correo = rs.getString("Correo_usuario");
				String nombre_pista = rs.getString("Nombre_pista");
				int duracion = rs.getInt("Duracion");
				int numero_de_niños = rs.getInt("Numero_de_niños");
				int numero_de_adultos = rs.getInt("Numero_de_adultos");
				java.sql.Date fecha = rs.getDate("Fecha");
				java.sql.Time hora=rs.getTime("Hora");
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(fecha);
				cal.set(Calendar.HOUR_OF_DAY, hora.toLocalTime().getHour());
				cal.set(Calendar.MINUTE, hora.toLocalTime().getMinute());
				Date fecha_hora = cal.getTime();

				reservas.add(new ReservaFamiliarDTO(correo, fecha_hora, duracion, nombre_pista, numero_de_niños, numero_de_adultos));

			}

			if (pstmt != null){ 
				pstmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (Exception e){
			System.err.println(e);
			e.printStackTrace();
		}
		return reservas;
	}

	/**
	 * Verifica si existe un bono activo para un usuario en función del tipo de reserva solicitado.
	 * 
	 * Este método consulta la base de datos para verificar si un usuario tiene un bono activo 
	 * asociado al tipo de reserva indicado (infantil, familiar o adultos). Si encuentra un bono válido, 
	 * devuelve el identificador del bono; de lo contrario, devuelve -1.
	 * 
	 * @param correo El correo electrónico del usuario para el cual se verifica el bono.
	 * @param tipoReserva El tipo de reserva (puede ser "infantil", "familiar" o "adultos").
	 * @return El identificador del bono si se encuentra un bono válido; -1 si no se encuentra ninguno 
	 *         o si ocurre un error durante la consulta.
	 * @throws IllegalArgumentException Si el tipo de reserva proporcionado no es válido.
	 * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
	 */
	public int checkBonoTipoReserva(String correo, String tipoReserva){

		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();
			String query = null;

			switch(tipoReserva){
				case "infantil":
					query = sqlProperties.getSQLQuery("sql.select.checkBonoTipoReservaInfantil");
				break;
				case "familiar":
					query = sqlProperties.getSQLQuery("sql.select.checkBonoTipoReservaFamiliar");
				break;
				case "adultos":
					query = sqlProperties.getSQLQuery("sql.select.checkBonoTipoReservaAdultos");
				break;			
			}

			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, correo);
			pstmt.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
			ResultSet rs = (ResultSet) pstmt.executeQuery();

			while (rs.next()) {
				return rs.getInt("Id");
			}

			if (pstmt != null){ 
				pstmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (Exception e){
			System.err.println(e);
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * Verifica la disponibilidad de una lista de pistas en una fecha y hora específicas con una duración dada.
	 * 
	 * Este método toma una lista de nombres de pistas, una fecha de inicio de reserva y la duración de la reserva,
	 * y elimina de la lista las pistas que no están disponibles en el rango de tiempo especificado debido a 
	 * conflictos con reservas existentes.
	 * 
	 * @param pistas Una lista de nombres de pistas a verificar.
	 * @param fecha La fecha y hora de inicio de la nueva reserva.
	 * @param duracion La duración de la reserva en minutos.
	 * @return Una lista de nombres de pistas que están disponibles para el rango de tiempo especificado.
	 *         Si ninguna pista está disponible, devuelve una lista vacía.
	 * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
	 * @throws NullPointerException Si se pasa una lista de pistas nula o algún dato es incorrecto.
	 */
	public ArrayList<String> checkDisponibilidadPistas(ArrayList<String> pistas, java.util.Date fecha, int duracion){

		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();
			String query = sqlProperties.getSQLQuery("sql.select.checkDisponibilidadPista2");

			PreparedStatement pstmt = connection.prepareStatement(query);

			Iterator<String> iterator = pistas.iterator(); // Usamos un iterador para la lista
			while (iterator.hasNext()) {
				String pista = iterator.next();
				boolean borrado = false;
				pstmt.setString(1, pista);
				ResultSet rs = (ResultSet) pstmt.executeQuery();

				while (rs.next() && borrado == false) {
					java.sql.Date fecha_reserva = rs.getDate("Fecha");
					java.sql.Time hora_reserva = rs.getTime("Hora");
					int duracion_reserva = rs.getInt("Duracion");

					Calendar cal = Calendar.getInstance();
					cal.setTime(fecha_reserva);
					cal.set(Calendar.HOUR_OF_DAY, hora_reserva.toLocalTime().getHour());
					cal.set(Calendar.MINUTE, hora_reserva.toLocalTime().getMinute());
					Date fecha_hora = cal.getTime();

					Calendar calNuevaReserva = Calendar.getInstance();
					calNuevaReserva.setTime(fecha);
					calNuevaReserva.add(Calendar.MINUTE, duracion);
					Date fechaFinNuevaReserva = calNuevaReserva.getTime();

					Calendar calendar = Calendar.getInstance();
					calendar.setTime(fecha_hora);
					calendar.add(Calendar.MINUTE, duracion_reserva);
					Date fechaFin = calendar.getTime();

					if ((fecha.before(fechaFin) && fechaFinNuevaReserva.after(fecha_hora)) || fecha.equals(fecha_hora) || fechaFinNuevaReserva.equals(fecha_hora) || fecha.equals(fechaFin)) {
						iterator.remove();
						borrado = true;
					}
				}
			}
			if (pstmt != null){ 
				pstmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (Exception e){
			System.err.println(e);
			e.printStackTrace();
		}
		return pistas;
	}

	/**
	 * Recupera las reservas cancelables asociadas a un correo electrónico.
	 * 
	 * Este método obtiene una lista de reservas familiares que son cancelables, vinculadas a un usuario
	 * identificado por su correo electrónico. Las reservas cancelables cumplen con ciertos criterios
	 * definidos en la consulta SQL, como estar programadas en una fecha futura.
	 * 
	 * @param correo El correo electrónico del usuario cuyas reservas cancelables se desean consultar.
	 * @return Una lista de objetos {@link ReservaFamiliarDTO} que contienen las reservas cancelables del usuario.
	 *         Si no se encuentran reservas cancelables, retorna una lista vacía.
	 * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
	 * @throws NullPointerException Si ocurre algún error inesperado durante la construcción de la lista.
	 */
	public ArrayList<ReservaFamiliarDTO> requestReservaCancelables(String correo){
		ArrayList<ReservaFamiliarDTO> reservas=new ArrayList<>();
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();
			String query = sqlProperties.getSQLQuery("sql.select.requestReservasCancelable");
			
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, correo);
			ResultSet rs = (ResultSet) pstmt.executeQuery();

			while (rs.next()) {
				ReservaFamiliarDTO reserva = new ReservaFamiliarDTO();
				reserva.setNombrePista(rs.getString("Nombre_pista"));
				java.sql.Date fecha = rs.getDate("Fecha");
				java.sql.Time hora = rs.getTime("Hora");
	
				Calendar cal = Calendar.getInstance();
				cal.setTime(fecha);
				cal.set(Calendar.HOUR_OF_DAY, hora.toLocalTime().getHour());
				cal.set(Calendar.MINUTE, hora.toLocalTime().getMinute());
				Date fecha_hora = cal.getTime();

				reserva.setFecha_hora(fecha_hora);
	
				// Obtener otras propiedades necesarias
				reserva.setDuracion(rs.getInt("Duracion"));
	
				reservas.add(reserva);
				
			}

			if (pstmt != null){ 
				pstmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (Exception e){
			System.err.println(e);
			e.printStackTrace();
		}
		return reservas;
	}

	/**
	 * Recupera las reservas futuras asociadas a un correo electrónico.
	 * 
	 * Este método obtiene todas las reservas familiares futuras vinculadas a un usuario identificado por su correo electrónico.
	 * Solo se consideran las reservas cuya fecha sea posterior a la actual.
	 * 
	 * @param correo El correo electrónico del usuario cuyas reservas futuras se desean consultar.
	 * @return Una lista de objetos {@link ReservaFamiliarDTO} que contienen las reservas futuras del usuario.
	 *         Si no se encuentran reservas, retorna una lista vacía.
	 * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
	 * @throws NullPointerException Si ocurre algún error inesperado durante la construcción de la lista.
	 */
	public ArrayList<ReservaFamiliarDTO> requestReservasFuturasCorreo(String correo){
		ArrayList<ReservaFamiliarDTO> reservas = new ArrayList<>();
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();
			String query = sqlProperties.getSQLQuery("sql.select.requestReservasFuturasCorreo");			

			PreparedStatement pstmt = connection.prepareStatement(query);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new java.util.Date());
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			java.util.Date newDate = calendar.getTime();
			
			pstmt.setDate(1, new java.sql.Date(newDate.getTime()));
			pstmt.setString(2, correo);
			ResultSet rs = (ResultSet) pstmt.executeQuery();

			while (rs.next()) {
				String nombre_pista = rs.getString("Nombre_pista");
				int duracion = rs.getInt("Duracion");
				float descuento = rs.getFloat("Descuento");
				int n_niños = rs.getInt("Numero_de_niños");
				int n_adultos = rs.getInt("Numero_de_adultos");
				java.sql.Date fecha = rs.getDate("Fecha");
				java.sql.Time hora=rs.getTime("Hora");
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(fecha);
				cal.set(Calendar.HOUR_OF_DAY, hora.toLocalTime().getHour());
				cal.set(Calendar.MINUTE, hora.toLocalTime().getMinute());
				Date fecha_hora = cal.getTime();

				reservas.add(new ReservaFamiliarDTO(correo, fecha_hora, duracion, nombre_pista, descuento, n_niños, n_adultos));

			}

			if (pstmt != null){ 
				pstmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (Exception e){
			System.err.println(e);
			e.printStackTrace();
		}
		return reservas;
	}
}