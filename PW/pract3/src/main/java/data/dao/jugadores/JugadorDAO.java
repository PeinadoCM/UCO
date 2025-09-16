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
	//usada
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
	
	/**
	 * Crea un nuevo registro de usuario en la base de datos.
	 * 
	 * Este método inserta un nuevo usuario en la base de datos utilizando los parámetros proporcionados.
	 * Establece una conexión con la base de datos, prepara una declaración SQL, ejecuta la consulta 
	 * y asegura que los recursos se cierren correctamente.
	 * 
	 * @param correo El correo electrónico del usuario. Debe ser un correo válido y único.
	 * @param nombre_y_apellidos El nombre completo del usuario.
	 * @param fecha_nacimiento La fecha de nacimiento del usuario. Se espera un objeto de tipo {@link java.sql.Date}.
	 * @param contraseña La contraseña para la cuenta del usuario. Se recomienda que esta esté previamente 
	 *                   cifrada o validada antes de pasarla como parámetro.
	 * @return {@code true} si el usuario se creó exitosamente; {@code false} si ocurrió algún error.
	 * @throws NullPointerException Si alguno de los parámetros es {@code null}.
	 * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
	 */
	public boolean createNewUserRegistro(String correo, String nombre_y_apellidos, Date fecha_nacimiento, String contraseña) {
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();
			String query = sqlProperties.getSQLQuery("sql.insert.createNewUserRegistro");
			PreparedStatement pstmt =connection.prepareStatement(query);
			pstmt.setString(1, correo);
			pstmt.setString(2, nombre_y_apellidos);
			pstmt.setDate(3, fecha_nacimiento);
			pstmt.setString(4,contraseña);

			pstmt.executeUpdate();
			if (pstmt != null){ 
				pstmt.close(); 
			}
			dbConnection.closeConnection();
			return true;
		} catch (Exception e){
			System.out.println(e);
			return false;
		}
	}
	
	/**
	 * Solicita un usuario de la base de datos para el inicio de sesión.
	 * 
	 * Este método busca en la base de datos un usuario con el correo y la contraseña proporcionados.
	 * Si encuentra un usuario que coincide, retorna un objeto {@link JugadorDTO} con la información del usuario.
	 * 
	 * @param correo El correo electrónico del usuario que intenta iniciar sesión. Debe ser un correo válido y existente.
	 * @param contraseña La contraseña asociada al usuario. Se recomienda que ya esté cifrada antes de ser utilizada en esta consulta.
	 * @return Un objeto {@link JugadorDTO} que contiene los datos del usuario si las credenciales son válidas;
	 *         {@code null} si no se encuentra ningún usuario que coincida.
	 * @throws NullPointerException Si alguno de los parámetros es {@code null}.
	 * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
	 */
	public JugadorDTO requestUserInicioSesion(String correo, String contraseña) {
		JugadorDTO user = null;
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();

			String query = sqlProperties.getSQLQuery("sql.select.requestUserInicioSesion");			
			
			PreparedStatement pstmt = connection.prepareStatement(query);
			pstmt.setString(1, correo);
			pstmt.setString(2,contraseña);
			ResultSet rs = (ResultSet) pstmt.executeQuery();

			while (rs.next()) {
				String nombre_y_apellidos = rs.getString("Nombre_y_apellidos");
				Date fecha_nacimiento = rs.getDate("Fecha_de_nacimiento");
				Date fecha_inscripcion = rs.getDate("Fecha_de_inscripcion");
				boolean admin = rs.getBoolean("Administrador");
				user =new JugadorDTO(nombre_y_apellidos, fecha_nacimiento, fecha_inscripcion, correo, contraseña, admin);
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
	 * Actualiza los datos de un usuario en la base de datos.
	 * 
	 * Este método permite actualizar el nombre, la contraseña y la fecha de nacimiento de un usuario 
	 * identificado por su correo electrónico en la base de datos.
	 * 
	 * @param nombre El nuevo nombre del usuario. Puede incluir nombres y apellidos.
	 * @param contraseña La nueva contraseña del usuario. Es recomendable que esté cifrada antes de ser proporcionada.
	 * @param fecha La nueva fecha de nacimiento del usuario. Se espera un objeto de tipo {@link java.sql.Date}.
	 * @param correo El correo electrónico del usuario, utilizado como identificador único para localizar el registro.
	 * @return {@code true} si los datos del usuario se actualizaron correctamente; 
	 *         {@code false} si ocurrió algún error durante el proceso.
	 * @throws NullPointerException Si alguno de los parámetros es {@code null}.
	 * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
	 */
	public boolean updateDatos(String nombre, String contraseña, Date fecha, String correo) {
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();		
			String query = sqlProperties.getSQLQuery("sql.update.updateDatos");

			PreparedStatement pstmt =connection.prepareStatement(query);
			pstmt.setString(1, nombre);
			pstmt.setString(2, contraseña);
			pstmt.setDate(3, fecha);
			pstmt.setString(4, correo);
			pstmt.executeUpdate();
			if (pstmt != null){ 
				pstmt.close(); 
			}
			dbConnection.closeConnection();
			return true;
		} catch (Exception e){
			System.out.println(e);
			return false;
		}
	}
	
	/**
	 * Recupera todos los clientes de la base de datos.
	 * 
	 * Este método obtiene una lista de todos los usuarios registrados en la base de datos 
	 * y los devuelve como una lista de objetos {@link JugadorDTO}.
	 * 
	 * @return Una lista de objetos {@link JugadorDTO} que contiene los datos de todos los usuarios registrados.
	 *         Si no hay usuarios, retorna una lista vacía.
	 * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
	 * @throws NullPointerException Si ocurre algún error inesperado durante la construcción de la lista.
	 */
	public ArrayList<JugadorDTO> requestAllClients() {
		ArrayList<JugadorDTO> listOfUsers = new ArrayList<JugadorDTO>();
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();
			String query = sqlProperties.getSQLQuery("sql.select.requestAllClients");
			
			PreparedStatement pstmt =connection.prepareStatement(query);
			ResultSet rs = (ResultSet) pstmt.executeQuery();

			while (rs.next()) {
				String correo = rs.getString("Correo");
				String nombre_y_apellidos = rs.getString("Nombre_y_apellidos");
				Date fecha_inscripcion = rs.getDate("Fecha_de_inscripcion");
				listOfUsers.add(new JugadorDTO(correo, nombre_y_apellidos, fecha_inscripcion));
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
}