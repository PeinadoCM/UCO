package data.dao.jugadores;

import java.sql.*;			
import java.util.ArrayList;


import com.mysql.jdbc.ResultSet;

import data.dto.jugadores.JugadorDTO;
import data.common.DBConnection;
import data.common.SQLProperties;


/**
 * Clase que gestiona las operaciones relacionadas con los jugadores en la base de datos.
 */
public class JugadorDAO {

	private SQLProperties sqlProperties;

	/**
	 * Constructor de la clase JugadorDAO.
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
	public JugadorDAO(){

		sqlProperties=new SQLProperties();
	}

	/**
	 * Solicita todos los usuarios de la base de datos.
	 * 
	 * Este método recupera todos los usuarios registrados en la base de datos y los retorna
	 * en una lista de objetos {@link JugadorDTO}.
	 * 
	 * @return Una lista de objetos {@link JugadorDTO} con los detalles de todos los usuarios.
	 */
	public ArrayList<JugadorDTO> requestAllUsers() {
		ArrayList<JugadorDTO> listOfUsers = new ArrayList<JugadorDTO>();
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();
			String query = sqlProperties.getSQLQuery("sql.select.requestAllUsers");
			
			PreparedStatement pstmt =connection.prepareStatement(query);
			ResultSet rs = (ResultSet) pstmt.executeQuery();

			while (rs.next()) {
				String correo = rs.getString("Correo");
				String nombre_y_apellidos = rs.getString("Nombre_y_apellidos");
				Date fecha_nacimiento = rs.getDate("Fecha_de_nacimiento");
				Date fecha_inscripcion = rs.getDate("Fecha_de_inscripcion");
				listOfUsers.add(new JugadorDTO(nombre_y_apellidos, fecha_nacimiento, fecha_inscripcion, correo));
			}

			if (pstmt != null){ 
				pstmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (Exception e){
			System.err.println(e);
			e.printStackTrace();
		}
		return listOfUsers;
	}

	/**
	 * Crea un nuevo usuario en la base de datos.
	 * 
	 * Este método inserta un nuevo usuario con los detalles proporcionados, como el correo electrónico,
	 * nombre y apellidos, y fecha de nacimiento.
	 * 
	 * @param correo El correo electrónico del nuevo usuario.
	 * @param nombre_y_apellidos El nombre y apellidos del nuevo usuario.
	 * @param fecha_nacimiento La fecha de nacimiento del nuevo usuario.
	 * @return {@code true} si el usuario fue creado correctamente, {@code false} en caso de error.
	 */
	public boolean createNewUser(String correo, String nombre_y_apellidos, Date fecha_nacimiento) {
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();
			String query = sqlProperties.getSQLQuery("sql.insert.createNewUser");
			PreparedStatement pstmt =connection.prepareStatement(query);
			pstmt.setString(1, correo);
			pstmt.setString(2, nombre_y_apellidos);
			pstmt.setDate(3, fecha_nacimiento);
			pstmt.executeUpdate();
			if (pstmt != null){ 
				pstmt.close(); 
			}
			dbConnection.closeConnection();
			return true;
		} catch (Exception e){
			return false;
		}
	}

	/**
	 * Modifica los datos de un usuario en la base de datos.
	 * 
	 * Este método actualiza los datos del usuario, como el nombre y apellidos, la fecha de nacimiento,
	 * basándose en el correo electrónico proporcionado.
	 * 
	 * @param nombre_y_apellidos El nuevo nombre y apellidos del usuario.
	 * @param fecha_nacimiento La nueva fecha de nacimiento del usuario.
	 * @param correo El correo electrónico del usuario, que es usado para identificar el registro a modificar.
	 * @return {@code true} si los datos fueron modificados correctamente, {@code false} en caso de error.
	 */
	public boolean modifyUser(String nombre_y_apellidos, Date fecha_nacimiento, String correo) {
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();

			String query = sqlProperties.getSQLQuery("sql.update.modifyUser");
			PreparedStatement pstmt =connection.prepareStatement(query);
			pstmt.setString(1, nombre_y_apellidos);
			pstmt.setDate(2, fecha_nacimiento);
			pstmt.setString(3, correo);
			pstmt.executeUpdate();
			if (pstmt != null){ 
				pstmt.close(); 
			}
			dbConnection.closeConnection();
			return true;
		} catch (Exception e){
			return false;
		}
	}
	
	/**
	 * Solicita los datos de un usuario a partir de su correo electrónico.
	 * 
	 * Este método realiza una consulta SQL para obtener los detalles del usuario (nombre, fecha de nacimiento,
	 * fecha de inscripción) basándose en el correo electrónico proporcionado. Los datos obtenidos se almacenan en un 
	 * objeto {@link JugadorDTO}.
	 * 
	 * @param correo El correo electrónico del jugador cuya información se solicita.
	 * @return Un objeto {@link JugadorDTO} con la información del jugador. Si no se encuentra el jugador, 
	 *         se retorna un objeto vacío.
	 */
	public JugadorDTO requestUser(String correo) {
		JugadorDTO user = new JugadorDTO();
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();
			String query = sqlProperties.getSQLQuery("sql.select.requestUser");			
			
			PreparedStatement pstmt =connection.prepareStatement(query);
			pstmt.setString(1, correo);
			ResultSet rs = (ResultSet) pstmt.executeQuery();

			while (rs.next()) {
				String nombre_y_apellidos = rs.getString("Nombre_y_apellidos");
				Date fecha_nacimiento = rs.getDate("Fecha_de_nacimiento");
				Date fecha_inscripcion = rs.getDate("Fecha_de_inscripcion");
				user =new JugadorDTO(nombre_y_apellidos, fecha_nacimiento, fecha_inscripcion, correo);
			}

			if (pstmt != null){ 
				pstmt.close(); 
			}
			dbConnection.closeConnection();
		} catch (Exception e){
			System.err.println(e);
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * Actualiza la fecha de inscripción de un usuario.
	 * 
	 * Este método recibe un correo y una nueva fecha de inscripción, y luego actualiza 
	 * la fecha en la base de datos correspondiente al correo del usuario proporcionado.
	 * Utiliza una consulta SQL predefinida almacenada en un archivo de propiedades
	 * para ejecutar la actualización en la base de datos.
	 * 
	 * @param correo El correo electrónico del usuario cuya fecha de inscripción será actualizada.
	 * @param fecha La nueva fecha de inscripción que se establecerá en la base de datos.
	 * @return {@code true} si la fecha de inscripción se actualizó correctamente, 
	 *         {@code false} si ocurrió un error durante la operación.
	 */
	public boolean updateFechaInscripcion(String correo, Date fecha) {
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();		
			String query = sqlProperties.getSQLQuery("sql.update.updateFechaInscripcion");

			PreparedStatement pstmt =connection.prepareStatement(query);
			pstmt.setDate(1, fecha);
			pstmt.setString(2, correo);
			pstmt.executeUpdate();
			if (pstmt != null){ 
				pstmt.close(); 
			}
			dbConnection.closeConnection();
			return true;
		} catch (Exception e){
			return false;
		}
	}	
	
}