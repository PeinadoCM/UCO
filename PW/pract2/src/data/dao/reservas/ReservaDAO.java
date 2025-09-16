package data.dao.reservas;

import java.sql.*;						
import java.util.ArrayList;

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
	 * Solicita la cantidad de reservas futuras a partir de una fecha dada, 
	 * y clasifica las reservas en tres categorías: reservas sin adultos, reservas sin niños, 
	 * y reservas con tanto adultos como niños.
	 * 
	 * @param fecha La fecha a partir de la cual se desean contar las reservas futuras.
	 * @return Un arreglo de enteros con tres elementos que indican el número de reservas
	 *         en cada categoría:
	 *         - `cont_reservas[0]`: Número de reservas infantiles.
     *         - `cont_reservas[1]`: Número de reservas de adultos.
     *         - `cont_reservas[2]`: Número de reservas familiares.
	 */
    public int[] requestContReservasFuturas(java.sql.Date fecha) {
    	int[] cont_reservas = new int[3];
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();
			String query = sqlProperties.getSQLQuery("sql.select.requestContReservasFuturas");
			
			PreparedStatement pstmt =connection.prepareStatement(query);
			pstmt.setDate(1, fecha);
			ResultSet rs = (ResultSet) pstmt.executeQuery();

			while (rs.next()) {
				if(rs.getInt("Numero_de_adultos") == 0) {
					cont_reservas[0]++;
				}
				else if(rs.getInt("Numero_de_niños") == 0) {
					cont_reservas[1]++;
				}
				else {
					cont_reservas[2]++;
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
		return cont_reservas;
	}
    
    /**
     * Solicita la cantidad de reservas para una pista específica en una fecha determinada, 
     * y clasifica las reservas en tres categorías: reservas sin adultos, reservas sin niños, 
     * y reservas con tanto adultos como niños.
     * 
     * @param fecha La fecha para la cual se desean contar las reservas.
     * @param nombre_pista El nombre de la pista para la cual se quieren contar las reservas.
     * @return Un arreglo de enteros con tres elementos que indican el número de reservas
     *         en cada categoría:
     *         - `cont_reservas[0]`: Número de reservas infantiles.
     *         - `cont_reservas[1]`: Número de reservas de adultos.
     *         - `cont_reservas[2]`: Número de reservas familiares.
     */
    public int[] requestContReservasFechaNombre(java.sql.Date fecha, String nombre_pista) {
    	int[] cont_reservas = new int[3];
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();
			String query = sqlProperties.getSQLQuery("sql.select.requestContReservasFechaNombre");
			
			PreparedStatement pstmt =connection.prepareStatement(query);
			pstmt.setDate(1, fecha);
			pstmt.setString(2, nombre_pista);
			ResultSet rs = (ResultSet) pstmt.executeQuery();

			while (rs.next()) {
				if(rs.getInt("Numero_de_adultos") == 0) {
					cont_reservas[0]++;
				}
				else if(rs.getInt("Numero_de_niños") == 0) {
					cont_reservas[1]++;
				}
				else {
					cont_reservas[2]++;
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
		return cont_reservas;
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
	public boolean modificarReserva(String nombrePista, Date fecha, Date nuevaFecha, int nuevaDuracion, float precio) {
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
        	pstmt.setString(5, nombrePista);
        	pstmt.setDate(6, new java.sql.Date(fecha.getTime()));
        	pstmt.setTime(7, new java.sql.Time(fecha.getTime()));
	
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
            
			pstmt.setInt(1, id_bono);
            pstmt.executeUpdate();
            
            dbConnection.closeConnection();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al crear la reserva en bono: " + e.getMessage());
            return false;
        }
		
	}
	
	/**
	 * Realiza una consulta a la base de datos para obtener las reservas realizadas en una pista específica en una fecha dada.
	 * 
	 * @param nombre_pista El nombre de la pista para la que se quieren obtener las reservas.
	 * @param fecha La fecha para la cual se desean obtener las reservas.
	 * @return Una lista de objetos {@link ReservaFamiliarDTO} con la información de las reservas encontradas.
	 */
	public ArrayList<ReservaFamiliarDTO> requestReservasByFechaPista(String nombre_pista, java.sql.Date fecha){
		ArrayList<ReservaFamiliarDTO> reservas = new ArrayList<ReservaFamiliarDTO>();
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();
			String query = sqlProperties.getSQLQuery("sql.select.requestReservasByFechaPista");
			
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setDate(1, fecha);
			pstmt.setString(2, nombre_pista);
			ResultSet rs = (ResultSet) pstmt.executeQuery();

			while (rs.next()) {
				String correo = rs.getString("Correo_usuario");
				java.sql.Time hora=rs.getTime("Hora");
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(fecha);
				cal.set(Calendar.HOUR_OF_DAY, hora.toLocalTime().getHour());
				cal.set(Calendar.MINUTE, hora.toLocalTime().getMinute());
				Date fecha_hora = cal.getTime();
				
				int duracion = rs.getInt("Duracion");
				reservas.add(new ReservaFamiliarDTO(correo, fecha_hora, duracion));
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
	 * Recupera el ID de un bono basado en el correo electrónico, la fecha y el nombre de la pista.
	 * 
	 * Este método realiza una consulta en la base de datos para obtener el ID de un bono
	 * específico, usando el correo electrónico del usuario, la fecha y el nombre de la pista
	 * como parámetros de búsqueda. Si se encuentra el bono, se retorna su ID.
	 * 
	 * @param correo El correo electrónico del usuario asociado al bono.
	 * @param fecha La fecha de la cual se desea consultar la existencia del bono.
	 * @param nombre_pista El nombre de la pista asociada al bono.
	 * @return El ID del bono si existe, 0 si no se encuentra un bono que cumpla con los criterios.
	 */
	public int requestBonoCorreoPista(String correo, java.sql.Date fecha, String nombre_pista) {
		int id_bono=0;
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();
			String query = sqlProperties.getSQLQuery("sql.select.requestBonoCorreoPista");
			
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, correo);
			pstmt.setString(2, nombre_pista);
			pstmt.setDate(3,fecha);
			ResultSet rs = (ResultSet) pstmt.executeQuery();

			while (rs.next()) {
				id_bono=rs.getInt("Id");
			}

			if (pstmt != null){ 
				pstmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (Exception e){
			System.err.println(e);
			e.printStackTrace();
		}
		return id_bono;
	}
	
	/**
	 * Actualiza la fecha de caducidad de un bono en la base de datos.
	 * 
	 * Este método ejecuta una consulta SQL para modificar la fecha de caducidad de un bono
	 * en la tabla correspondiente, utilizando el ID del bono como referencia.
	 * 
	 * @param id_bono El identificador único del bono cuyo campo "Fecha_caducidad" se desea actualizar.
	 * @param fecha La nueva fecha de caducidad que se asignará al bono.
	 */
	public void updateFechaCaducidad(int id_bono, java.sql.Date fecha){

		try {
            DBConnection dbConnection = new DBConnection();
            Connection connection = dbConnection.getConnection();
			String query = sqlProperties.getSQLQuery("sql.update.updateFechaCaducidad");
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setDate(1, fecha);
            pstmt.setInt(2, id_bono);

            pstmt.executeUpdate();
            dbConnection.closeConnection();
        } catch (SQLException e) {
            System.err.println("Error al actualizar la fecha de caducidad del bono: " + e.getMessage());
        }
	}
	
	/**
	 * Solicita los bonos asociados a un usuario específico, identificado por su correo electrónico, desde la base de datos.
	 * 
	 * Este método ejecuta una consulta SQL para obtener los bonos disponibles para un usuario,
	 * incluyendo información como el tipo de pista, la fecha de caducidad y las sesiones disponibles.
	 * Los datos recuperados se almacenan en una lista de objetos `Bono`.
	 * 
	 * @param correo El correo electrónico del usuario cuyo bono se desea consultar.
	 * @return Devuelve una lista de objetos `Bono` con los detalles de los bonos asociados al usuario.
	 *         Si no se encuentran bonos, la lista estará vacía.
	 */
	public ArrayList<Bono> requestBonos(String correo){
		ArrayList<Bono> bonos=new ArrayList<>();
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();
			String query = sqlProperties.getSQLQuery("sql.select.requestBonos");
			
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, correo);
			ResultSet rs = (ResultSet) pstmt.executeQuery();

			while (rs.next()) {
				TipoPista tipo=TipoPista.valueOf(rs.getString("Tipo"));
				Date fecha_caducidad=rs.getDate("Fecha_caducidad");
				int sesiones_disponibles=rs.getInt("Sesiones_disponibles");
				bonos.add(new Bono(correo,tipo,fecha_caducidad,sesiones_disponibles));
			}

			if (pstmt != null){ 
				pstmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (Exception e){
			System.err.println(e);
			e.printStackTrace();
		}
		return bonos;
	}
	
}
	