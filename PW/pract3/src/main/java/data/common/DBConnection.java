package data.common;

import java.sql.Connection;	
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * Clase {@link DBConnection} que gestiona la conexión a una base de datos MySQL.
 * 
 * Esta clase es responsable de establecer y gestionar una conexión con una base de datos, utilizando los parámetros
 * definidos en un archivo de configuración externo llamado {@code config.properties}. La clase proporciona métodos para
 * abrir y cerrar la conexión, asegurando que la conexión a la base de datos esté disponible cuando sea necesario
 * y que se cierre correctamente al finalizar su uso.
 * 
 * El archivo {@code config.properties} debe contener las siguientes propiedades de configuración:
 * - {@code db.url}: La URL de conexión a la base de datos.
 * - {@code db.username}: El nombre de usuario para acceder a la base de datos.
 * - {@code db.password}: La contraseña correspondiente al nombre de usuario de la base de datos.
 * 
 * La clase también maneja errores relacionados con la conexión a la base de datos e informa de cualquier problema
 * a través de mensajes de error en la consola.
 */

public class DBConnection {

	protected Connection connection = null;

	// Important: This configuration is hard-coded here for illustrative purposes only
	
	protected String url;

	protected String user;

	protected String password;

	/**
	 * Constructor de la clase {@link DBConnection} que inicializa la conexión a la base de datos utilizando los parámetros
	 * definidos en un archivo de configuración externo (`config.properties`).
	 * 
	 * Este constructor carga el archivo de propiedades `config.properties` desde el directorio `resources/`, donde se
	 * espera que estén definidos los parámetros de conexión a la base de datos, como la URL, el nombre de usuario y la
	 * contraseña. Si el archivo no se encuentra o ocurre algún error durante la carga, se imprime un mensaje de error.
	 * 
	 * El archivo `config.properties` debe contener las siguientes propiedades:
	 * - {@code db.url}: La URL de conexión a la base de datos.
	 * - {@code db.username}: El nombre de usuario para la base de datos.
	 * - {@code db.password}: La contraseña para la base de datos.
	 * 
	 * Si ocurre algún error en la lectura del archivo de propiedades, se captura la excepción {@link IOException} y se
	 * imprime la traza del error.
	 */
	public DBConnection() {
		Properties properties = new Properties();
		try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
			if (input == null) {
				System.out.println("Error, unable to find config.properties");
				return;
			} 
			// Cargar el archivo de propiedades 
			properties.load(input); 
			// Obtener los valores de las propiedades 
			url = properties.getProperty("db.url"); 
			user = properties.getProperty("db.username"); 
			password = properties.getProperty("db.password"); 
		} catch (IOException ex) {
			ex.printStackTrace(); 
		} 
	}
	
	/**
	 * Establece y devuelve una conexión a la base de datos MySQL.
	 * 
	 * Este método intenta cargar el controlador JDBC para MySQL y establecer una conexión a la base de datos utilizando los
	 * parámetros de conexión especificados (URL, usuario y contraseña). Si la conexión se establece correctamente, se
	 * imprime un mensaje de éxito en la consola. En caso de error, se capturan y manejan las excepciones {@link SQLException}
	 * y {@link ClassNotFoundException}, imprimiendo los mensajes de error y sus respectivas trazas en la consola.
	 * 
	 * @return {@link Connection} La conexión a la base de datos si la conexión se establece correctamente, o {@code null} 
	 *         si ocurre algún error.
	 * @see java.sql.Connection
	 * @see java.sql.DriverManager#getConnection(String, String, String)
	 */
	public Connection getConnection(){

		try{
			Class.forName("com.mysql.jdbc.Driver");
			this.connection = (Connection) DriverManager.getConnection(url, user, password);
			System.out.println("Database connection successfully opened!");
		} 
		catch (SQLException e) {
			System.err.println("Connection to MySQL has failed!");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("JDBC Driver not found.");
			e.printStackTrace();
		}
		return this.connection;
	}
	
	/**
	 * Cierra la conexión a la base de datos.
	 * 
	 * Este método verifica si la conexión a la base de datos está activa y no está cerrada antes de intentar cerrarla. 
	 * Si la conexión es válida, se cierra correctamente y se imprime un mensaje de éxito en la consola. 
	 * En caso de que ocurra un error durante el proceso de cierre, se captura la excepción {@link SQLException} y se imprime 
	 * un mensaje de error junto con la traza del error.
	 * 
	 * @see java.sql.Connection#close()
	 */
	public void closeConnection() {
		try {
			if(this.connection != null && !this.connection.isClosed()) {
				this.connection.close();
				System.out.println("Database connection successfully closed!");
			}
		} catch (SQLException e) {
			System.err.println("Error while trying to close the connection.");
			e.printStackTrace();
		}
	}
}
