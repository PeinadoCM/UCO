package data.dao.pistas;

import java.sql.*;			
import java.util.ArrayList;

import data.dto.pistas.*;

import com.mysql.jdbc.ResultSet;
import data.common.DBConnection;
import data.common.SQLProperties;



/**
 * Clase que gestiona las operaciones relacionadas con las pistas y materiales en la base de datos.
 */
public class PistaDAO {

	private SQLProperties sqlProperties;

	/**
	 * Constructor de la clase PistaDAO.
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
	public PistaDAO(){

		sqlProperties=new SQLProperties();
	}
	
	/**
	 * Este método crea una nueva pista en la base de datos con los parámetros proporcionados.
	 * 
	 * @param nombre El nombre de la pista.
	 * @param estado El estado de la pista (por ejemplo, disponible o no disponible).
	 * @param tipo El tipo de pista (por ejemplo, interior o exterior).
	 * @param tamaño El tamaño de la pista (por ejemplo, pequeño, mediano, grande).
	 * @param maxjugadores El número máximo de jugadores permitidos en la pista.
	 * @return Devuelve `true` si la pista se crea correctamente, o `false` si ocurre un error.
	 */
	public boolean createPista(String nombre, Boolean estado, Boolean tipo, TipoPista tamaño,int maxjugadores) {
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();
			String query = sqlProperties.getSQLQuery("sql.insert.createPista");
			PreparedStatement pstmt =connection.prepareStatement(query);
			pstmt.setString(1, nombre);
            pstmt.setBoolean(2, estado);
			pstmt.setBoolean(3, tipo);
            pstmt.setString(4, tamaño.name());
            pstmt.setInt(5, maxjugadores);
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
	 * Este método crea un nuevo material en la base de datos.
	 * Utiliza los parámetros proporcionados (uso, tipo y estado) para insertar el material en la tabla correspondiente.
	 * 
	 * @param uso Indica si el material está en uso (true) o no (false).
	 * @param tipo El tipo del material (por ejemplo, raqueta, pelotas, etc.).
	 * @param estado El estado del material (por ejemplo, disponible, reservado, etc.).
	 * @return Devuelve `true` si la creación del nuevo material fue exitosa, de lo contrario devuelve `false`.
	 */
	public boolean createNewMaterial( boolean uso, Tipo tipo, Estado estado) {
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();
			String query = sqlProperties.getSQLQuery("sql.insert.createNewMaterial");
			PreparedStatement pstmt =connection.prepareStatement(query);
			pstmt.setBoolean(1, uso);
			pstmt.setString(2, tipo.name());
			pstmt.setString(3, estado.name());
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
     * Este método consulta todos los materiales disponibles en la base de datos.
     * Los materiales se consideran "disponibles" en función de su estado y uso.
     * 
     * @return Una lista de objetos `MaterialDTO` que representan los materiales disponibles.
     */
    public ArrayList<MaterialDTO> requestMaterialesDisponibles() {
         ArrayList<MaterialDTO> MaterialesDisponibles = new ArrayList<>();
         String query = sqlProperties.getSQLQuery("sql.select.requestMaterialesDisponibles");
         try {
             DBConnection dbConnection = new DBConnection();
             Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt =connection.prepareStatement(query);
             ResultSet rs = (ResultSet) pstmt.executeQuery(query);
             while (rs.next()) {
                 int id = rs.getInt("Id");
                 boolean uso = rs.getBoolean("Uso");
                 Tipo tipo = Tipo.valueOf(rs.getString("Tipo"));
                 Estado estado = Estado.valueOf(rs.getString("Estado"));
                 MaterialesDisponibles.add(new MaterialDTO(id, uso, tipo, estado));
             }
             // Cerrar recursos
             if (pstmt != null) {
                 pstmt.close();
             }
             dbConnection.closeConnection();
         } catch (SQLException e) {
             System.out.println("Error al obtener los materiales disponibles: " + e.getMessage());
         }
         return MaterialesDisponibles;
    }
    
    /**
     * Este método asigna un nuevo material a una pista específica.
     * 
     * Primero inserta el material y la pista en la tabla de relación entre materiales y pistas. Luego, actualiza el estado del material
     * a "RESERVADO" en la tabla de materiales para indicar que el material ha sido asignado a esa pista.
     * 
     * @param nombre_pista El nombre de la pista a la que se va a asignar el material.
     * @param id_material El ID del material que se va a asignar a la pista.
     * @return Retorna `true` si la asignación y actualización fueron exitosas, `false` en caso de un error.
     */
    public boolean assignNewMaterialToPista(String nombre_pista, int id_material) {
		try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();

            String query = sqlProperties.getSQLQuery("sql.update.assignNewMaterialToPista");
			PreparedStatement pstmt =connection.prepareStatement(query);
			pstmt.setString(1, Estado.RESERVADO.name());
            pstmt.setString(2, nombre_pista);
			pstmt.setInt(3, id_material);
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
     * Este método obtiene la información de una pista a partir de su nombre.
     * 
     * @param nombre_pista El nombre de la pista que se desea obtener.
     * @return Un objeto de tipo PistaDTO que contiene la información de la pista (estado, tipo, tamaño, maximo de jugadores).
     * Si no se encuentra una pista con el nombre proporcionado, se devuelve null.
     */
    public PistaDTO requestPista(String nombre_pista) {

        String query = sqlProperties.getSQLQuery("sql.select.requestPista");
        PistaDTO pista=null;
        try {
            DBConnection dbConnection = new DBConnection();
            Connection connection = dbConnection.getConnection();
            PreparedStatement pstmt =connection.prepareStatement(query);
            pstmt.setString(1, nombre_pista);
            ResultSet rs = (ResultSet) pstmt.executeQuery();
            if(rs.next()){
            	pista= new PistaDTO();
            	pista.setNombre(nombre_pista);
            	pista.setEstado(rs.getBoolean("Estado"));
                pista.setTipo(rs.getBoolean("Tipo"));
                pista.setTamaño(TipoPista.valueOf(rs.getString("Tamaño")));
                pista.setMaxJugadores(rs.getInt("Jugadores_maximos"));  
            }
            // Cerrar recursos
            if (pstmt != null) {
                pstmt.close();
            }
            dbConnection.closeConnection();
            
        } catch (SQLException e) {
            System.out.println("Error al obtener la pista: " + e.getMessage());
        }
        return pista;
        
    }
    
    /**
     * Este método obtiene la información de un material a partir de su ID en la base de datos.
     * 
     * @param id_material El ID del material que se desea obtener.
     * @return Un objeto de tipo MaterialDTO que contiene la información del material (estado, uso, tipo).
     * Si no se encuentra un material con el ID proporcionado, se devuelve null.
     */
    public MaterialDTO requestMaterial(int id_material) {
        String query= sqlProperties.getSQLQuery("sql.select.requestMaterial");
        MaterialDTO material=null;
        try {
            DBConnection dbConnection = new DBConnection();
            Connection connection = dbConnection.getConnection();
            PreparedStatement pstmt =connection.prepareStatement(query);
            pstmt.setInt(1, id_material);
            ResultSet rs = (ResultSet) pstmt.executeQuery();
            if(rs.next()){
            	material= new MaterialDTO();
            	material.setId(id_material);
            	material.setEstado(Estado.valueOf(rs.getString("Estado")));
                material.setUso(rs.getBoolean("Uso"));
                material.setTipo(Tipo.valueOf(rs.getString("Tipo"))); 
            }
            // Cerrar recursos
            if (pstmt != null) {
                pstmt.close();
            }
            dbConnection.closeConnection();
            
        } catch (SQLException e) {
            System.out.println("Error al obtener el material: " + e.getMessage());
        }
        return material;
    }

/**
 * Actualiza el estado de un material en la base de datos.
 *
 * @param id_material el identificador único del material cuyo estado se desea actualizar.
 * @param estado el nuevo estado que se asignará al material. Debe ser un valor del enumerado {@code Estado}.
 * @return {@code true} si la actualización fue exitosa, o {@code false} si ocurrió algún error durante el proceso.
 * 
 * <p>El método utiliza una conexión a la base de datos para ejecutar una consulta preparada que actualiza el estado
 * del material especificado por su identificador. Si la operación tiene éxito, la conexión y los recursos asociados
 * se cierran correctamente.</p>
 * 
 * <p>En caso de que ocurra una excepción, se devuelve {@code false} y la conexión también se libera.</p>
 * 
 * @throws Exception si ocurre un error al establecer la conexión, preparar la consulta o ejecutar la actualización.
 */
    public boolean updateEstadoMaterial(int id_material,Estado estado){
        try {
			DBConnection dbConnection = new DBConnection();
			Connection connection = dbConnection.getConnection();

			String query = sqlProperties.getSQLQuery("sql.update.updateEstadoMaterial");
			PreparedStatement pstmt =connection.prepareStatement(query);
			pstmt.setString(1, estado.name());
            pstmt.setInt(2, id_material);
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
 * Solicita los materiales que no están reservados en la base de datos.
 *
 * @return una lista de objetos {@code MaterialDTO} que representan los materiales disponibles 
 *         (no reservados) almacenados en la base de datos.
 *
 * <p>El método ejecuta una consulta SQL para recuperar los materiales que no están en uso
 * o reservados. Por cada fila del resultado, se crea un objeto {@code MaterialDTO} con los
 * datos correspondientes y se agrega a la lista.</p>
 * 
 * <p>En caso de que ocurra un error durante la conexión o consulta, se captura una excepción
 * {@code SQLException}, se imprime un mensaje de error en la consola, y se retorna una lista vacía.</p>
 *
 * @throws SQLException si ocurre un error al ejecutar la consulta o al procesar los resultados.
 */
    public ArrayList<MaterialDTO> requestMaterialesNoReservado() {
        ArrayList<MaterialDTO> MaterialesDisponibles = new ArrayList<>();
        String query = sqlProperties.getSQLQuery("sql.select.requestMaterialesNoReservado");
        try {
            DBConnection dbConnection = new DBConnection();
            Connection connection = dbConnection.getConnection();
            PreparedStatement pstmt =connection.prepareStatement(query);
            ResultSet rs = (ResultSet) pstmt.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("Id");
                boolean uso = rs.getBoolean("Uso");
                Tipo tipo = Tipo.valueOf(rs.getString("Tipo"));
                Estado estado = Estado.valueOf(rs.getString("Estado"));
                MaterialesDisponibles.add(new MaterialDTO(id, uso, tipo, estado));
            }
            // Cerrar recursos
            if (pstmt != null) {
                pstmt.close();
            }
            dbConnection.closeConnection();
        } catch (SQLException e) {
            System.out.println("Error al obtener los materiales disponibles: " + e.getMessage());
        }
   
        return MaterialesDisponibles;
    }

    /**
 * Recupera todas las pistas almacenadas en la base de datos.
 *
 * @return una lista de objetos {@code PistaDTO} que representan las pistas disponibles en la base de datos.
 *
 * <p>El método ejecuta una consulta SQL para obtener todas las pistas. Por cada fila del resultado,
 * se crea un objeto {@code PistaDTO} con los datos correspondientes y se agrega a la lista resultante.</p>
 * 
 * <p>Los campos de cada pista incluyen:</p>
 * <ul>
 *   <li><strong>Nombre:</strong> el nombre de la pista.</li>
 *   <li><strong>Estado:</strong> un valor booleano que indica si la pista está activa o no.</li>
 *   <li><strong>Tipo:</strong> un valor booleano que indica el tipo de pista.</li>
 *   <li><strong>Tamaño:</strong> el tipo de pista representado por un valor del enum {@code TipoPista}.</li>
 *   <li><strong>Jugadores máximos:</strong> el número máximo de jugadores permitidos en la pista.</li>
 * </ul>
 * 
 * <p>En caso de que ocurra un error durante la conexión o consulta, se captura una excepción
 * {@code SQLException}, se imprime un mensaje de error en la consola y se retorna una lista vacía.</p>
 *
 * @throws SQLException si ocurre un error al ejecutar la consulta o al procesar los resultados.
 */
    public ArrayList<PistaDTO> requestAllPistas() {

        ArrayList<PistaDTO> pistas = new ArrayList<>();
        try {
            DBConnection dbConnection = new DBConnection();
            Connection connection = dbConnection.getConnection();

            String query = sqlProperties.getSQLQuery("sql.select.requestAllPistas");

            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = (ResultSet) pstmt.executeQuery();

            while(rs.next()){
            	PistaDTO pista= new PistaDTO();
            	pista.setNombre(rs.getString("Nombre"));
            	pista.setEstado(rs.getBoolean("Estado"));
                pista.setTipo(rs.getBoolean("Tipo"));
                pista.setTamaño(TipoPista.valueOf(rs.getString("Tamaño")));
                pista.setMaxJugadores(rs.getInt("Jugadores_maximos"));  
                pistas.add(pista);
            }
            // Cerrar recursos
            if (pstmt != null) {
                pstmt.close();
            }
            dbConnection.closeConnection();
            
        } catch (SQLException e) {
            System.out.println("Error al obtener la pista: " + e.getMessage());
        }
        return pistas;
        
    }

    /**
 * Verifica la disponibilidad de una pista para un tipo específico de material.
 *
 * @param nombre_pista el nombre de la pista cuya disponibilidad se desea verificar.
 * @param tipo el tipo de material que se desea comprobar (por ejemplo, {@code Tipo.PELOTAS}, {@code Tipo.CONOS}, {@code Tipo.CANASTAS}).
 * @return {@code true} si la pista tiene disponibilidad para el tipo de material especificado,
 *         {@code false} si ya se ha alcanzado el límite para ese tipo de material.
 *
 * <p>El método consulta la base de datos para obtener el número actual de materiales asignados a la pista.
 * Luego, compara estos valores con los límites establecidos para cada tipo de material:</p>
 * <ul>
 *   <li><strong>Pelotas:</strong> máximo 12 unidades.</li>
 *   <li><strong>Conos:</strong> máximo 20 unidades.</li>
 *   <li><strong>Canastas:</strong> máximo 2 unidades.</li>
 * </ul>
 *
 * <p>Si el número de materiales del tipo solicitado ha alcanzado el límite, se devuelve {@code false}.
 * De lo contrario, se devuelve {@code true}.</p>
 * 
 * <p>En caso de que ocurra un error durante la conexión o consulta, se captura una excepción
 * {@code SQLException}, se imprime un mensaje de error en la consola y se retorna {@code false}.</p>
 *
 * @throws SQLException si ocurre un error al ejecutar la consulta o al procesar los resultados.
 */
    public boolean checkDisponibilidadPista(String nombre_pista, Tipo tipo) {
        String query= sqlProperties.getSQLQuery("sql.select.checkDisponibilidadPista");
        int pelotas = 0, canastas = 0, conos = 0;
        try {
            DBConnection dbConnection = new DBConnection();
            Connection connection = dbConnection.getConnection();
            PreparedStatement pstmt =connection.prepareStatement(query);
            pstmt.setString(1, nombre_pista);
            ResultSet rs = (ResultSet) pstmt.executeQuery();
            while(rs.next()){
                switch(rs.getString("Tipo")){
                    case "PELOTAS":
                        pelotas++;
                    break;
                    case "CONOS":
                        conos++;
                    break;
                    case "CANASTAS":
                        canastas++;
                    break;
                }
            }
            // Cerrar recursos
            if (pstmt != null) {
                pstmt.close();
            }
            dbConnection.closeConnection();
            if(canastas == 2 && tipo == Tipo.CANASTAS){
                return false;
            }
            if(conos == 20 && tipo == Tipo.CONOS){
                return false;
            }
            if(pelotas == 12 && tipo == Tipo.PELOTAS){
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("Error al obtener el material: " + e.getMessage());
            return false;
        }
        return true;
    }    


/**
 * Actualiza el estado de una pista en la base de datos.
 *
 * @param nombrePista el nombre de la pista que se desea actualizar.
 * @param estado el nuevo estado de la pista ({@code true} para disponible, {@code false} para no disponible).
 * @return {@code true} si la actualización se realiza correctamente, {@code false} si ocurre algún error.
 *
 * <p>El método ejecuta una consulta SQL para modificar el estado de una pista específica en la base de datos.
 * Utiliza un parámetro booleano para establecer el estado y un parámetro String para identificar la pista.</p>
 *
 * <p>En caso de error, se captura una excepción {@code Exception} y se retorna {@code false}.</p>
 *
 * <p>Ejemplo de consulta SQL esperada en la propiedad {@code sql.update.updateEstadoPista}:</p>
 * <pre>{@code
 * UPDATE Pistas SET Estado = ? WHERE Nombre = ?
 * }</pre>
 *
 * <p>Este método asegura el cierre de recursos como el {@code PreparedStatement} y la conexión de base de datos
 * mediante el método {@code closeConnection} del objeto {@code DBConnection}.</p>
 *
 * @throws Exception si ocurre un error al establecer la conexión, preparar la consulta, o ejecutarla.
 */
    public boolean updateEstadoPista(String nombrePista, boolean estado) {
    try {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConnection();

        String query = sqlProperties.getSQLQuery("sql.update.updateEstadoPista");
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setBoolean(1, estado);
        pstmt.setString(2, nombrePista);
        pstmt.executeUpdate();

        if (pstmt != null) {
            pstmt.close();
        }
        dbConnection.closeConnection();

        return true;
    } catch (Exception e) {
        return false;
    }
    }
/**
 * Obtiene una lista de pistas disponibles basándose en el tipo especificado.
 *
 * @param tipo Un valor booleano que indica el tipo de pista a consultar. 
 *             Por ejemplo, {@code true} puede representar un tipo específico de pista 
 *             (como cubierta) y {@code false} otro (como descubierta), dependiendo de la implementación.
 * @return Una lista de objetos {@link PistaDTO} que representan las pistas disponibles 
 *         del tipo especificado. Si no hay pistas disponibles o ocurre un error, 
 *         se devuelve una lista vacía.
 * @throws SQLException Si ocurre un error durante la consulta a la base de datos.
 */

public ArrayList<PistaDTO> requestTipoPista(boolean tipo) {

        ArrayList<PistaDTO> pistas = new ArrayList<>();
        try {
            DBConnection dbConnection = new DBConnection();
            Connection connection = dbConnection.getConnection();

            String query = sqlProperties.getSQLQuery("sql.select.requestTipoPista");

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setBoolean(1, tipo);
            ResultSet rs = (ResultSet) pstmt.executeQuery();

            while(rs.next()){
            	PistaDTO pista= new PistaDTO();
            	pista.setNombre(rs.getString("Nombre"));
            	pista.setEstado(rs.getBoolean("Estado"));
                pista.setTipo(rs.getBoolean("Tipo"));
                pista.setTamaño(TipoPista.valueOf(rs.getString("Tamaño")));
                pista.setMaxJugadores(rs.getInt("Jugadores_maximos"));  
                pistas.add(pista);
            }
            // Cerrar recursos
            if (pstmt != null) {
                pstmt.close();
            }
            dbConnection.closeConnection();
            
        } catch (SQLException e) {
            System.out.println("Error al obtener la pista: " + e.getMessage());
        }
        return pistas;
        
    }
    
/**
 * Obtiene una lista de pistas disponibles para una fecha específica, incluyendo información
 * detallada de cada pista.
 *
 * @param fecha La fecha para la cual se desea consultar la disponibilidad de las pistas.
 * @return Una lista de objetos {@link PistaDTO} que representan las pistas disponibles 
 *         para la fecha especificada. Si no hay pistas disponibles o ocurre un error, 
 *         se devuelve una lista vacía.
 * @throws SQLException Si ocurre un error durante la consulta a la base de datos.
 */

    public ArrayList<PistaDTO> requestFechaPista(java.sql.Date fecha) {

        ArrayList<PistaDTO> pistas = new ArrayList<>();
        try {
            DBConnection dbConnection = new DBConnection();
            Connection connection = dbConnection.getConnection();

            String query = sqlProperties.getSQLQuery("sql.select.requestFechaPista");

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setDate(1, fecha);
            ResultSet rs = (ResultSet) pstmt.executeQuery();

            while(rs.next()){
                PistaDTO pista= new PistaDTO();
                pista.setNombre(rs.getString("Nombre"));
                pista.setEstado(rs.getBoolean("Estado"));
                pista.setTipo(rs.getBoolean("Tipo"));
                pista.setTamaño(TipoPista.valueOf(rs.getString("Tamaño")));
                pista.setMaxJugadores(rs.getInt("Jugadores_maximos"));  
                pistas.add(pista);
            }
            // Cerrar recursos
            if (pstmt != null) {
                pstmt.close();
            }
            dbConnection.closeConnection();
            
        } catch (SQLException e) {
            System.out.println("Error al obtener la pista: " + e.getMessage());
        }
        return pistas;
    }
/**
 * Obtiene una lista de nombres de pistas disponibles según el tipo de reserva especificado
 * y el número de participantes.
 *
 * @param tipoReserva El tipo de reserva, que puede ser:
 *                    <ul>
 *                      <li>"infantil" - para pistas destinadas a niños.</li>
 *                      <li>"familiar" - para pistas destinadas a grupos familiares.</li>
 *                      <li>"adultos" - para pistas destinadas a adultos.</li>
 *                    </ul>
 * @param n_participantes El número de participantes que utilizarán la pista.
 * @return Una lista de cadenas con los nombres de las pistas disponibles que cumplen con los criterios.
 *         Si no se encuentran pistas o ocurre un error, se devuelve una lista vacía.
 * @throws SQLException Si ocurre un error durante la consulta a la base de datos.
 */

    public ArrayList<String> requestPistasDisponiblesTamañoJugadores(String tipoReserva, int n_participantes) {

        ArrayList<String> pistas = new ArrayList<>();
        try {
            DBConnection dbConnection = new DBConnection();
            Connection connection = dbConnection.getConnection();

            String query = null;
            switch(tipoReserva){
                case "infantil":
                    query = sqlProperties.getSQLQuery("sql.select.requestPistasDisponiblesTamañoJugadoresInfantil");
                break;
                case "familiar":
                    query = sqlProperties.getSQLQuery("sql.select.requestPistasDisponiblesTamañoJugadoresFamiliar");
                break;
                case "adultos":
                    query = sqlProperties.getSQLQuery("sql.select.requestPistasDisponiblesTamañoJugadoresAdultos");
                break;
            }

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, n_participantes);
            ResultSet rs = (ResultSet) pstmt.executeQuery();

            while(rs.next()){
            	pistas.add(rs.getString("Nombre"));
            }
            // Cerrar recursos
            if (pstmt != null) {
                pstmt.close();
            }
            dbConnection.closeConnection();
            
        } catch (SQLException e) {
            System.out.println("Error al obtener la pista: " + e.getMessage());
        }
        return pistas;
        
    }
/**
 * Verifica si el número de participantes especificado cumple con el máximo permitido
 * para una pista específica.
 *
 * @param nombre_pista El nombre de la pista a verificar.
 * @param n_participantes El número de participantes a comprobar.
 * @return {@code true} si el número de participantes es válido para la pista especificada, 
 *         {@code false} en caso contrario o si ocurre un error durante la verificación.
 * @throws SQLException Si ocurre un error al ejecutar la consulta en la base de datos.
 */

    
    public boolean checkMaxJugadoresPista(String nombre_pista, int n_participantes){
        try {
            DBConnection dbConnection = new DBConnection();
            Connection connection = dbConnection.getConnection();

            String query = sqlProperties.getSQLQuery("sql.select.checkMaxJugadoresPista");

            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, nombre_pista);
            pstmt.setInt(2, n_participantes);
            ResultSet rs = (ResultSet) pstmt.executeQuery();

            while(rs.next()){
                return true;
            }
            // Cerrar recursos
            if (pstmt != null) {
                pstmt.close();
            }
            dbConnection.closeConnection();
            
        } catch (SQLException e) {
            System.out.println("Error al obtener la pista: " + e.getMessage());
        }
        return false;
    }

}