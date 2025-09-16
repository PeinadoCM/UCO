package aux;

import java.sql.Connection;	
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * Clase que gestiona la conexión a una base de datos MySQL.
 */
public class DBConnection {

	protected Connection connection = null;
	
	protected String url;

	protected String user;

	protected String password;

	/**
	 * Constructor de la clase  que inicializa la conexión a la base de datos utilizando los parámetros
	 * definidos en un archivo de configuración externo (`config.properties`).
	 */
	public DBConnection() {
		Properties properties = new Properties();
		try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
			if (input == null) {
				System.out.println("Error, unable to find config.properties");
				return;
			} 
			properties.load(input); 
			url = properties.getProperty("db.url"); 
			user = properties.getProperty("db.username"); 
			password = properties.getProperty("db.password"); 
		} catch (IOException ex) {
			ex.printStackTrace(); 
		} 
	}
	
	/**
	 * Establece y devuelve una conexión a la base de datos.
	 * @return La conexión a la base de datos.
	 */
	public Connection getConnection(){

		try{
			Class.forName("org.mariadb.jdbc.Driver");
			this.connection = (Connection) DriverManager.getConnection(url, user, password);
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
	 */
	public void closeConnection() {
		try {
			if(this.connection != null && !this.connection.isClosed()) {
				this.connection.close();
			}
		} catch (SQLException e) {
			System.err.println("Error while trying to close the connection.");
			e.printStackTrace();
		}
	}
}
