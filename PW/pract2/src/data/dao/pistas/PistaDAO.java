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
	public boolean createPista(String nombre, Boolean estado, Boolean tipo, TipoPista tamaño,int maxjugadores) {//probado y NO funciona
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
	 * Este método consulta todas las pistas que no están disponibles en la base de datos.
	 * Las pistas no disponibles se determinan por su estado.
	 * 
	 * @return Una lista de objetos `PistaDTO` que representan las pistas no disponibles.
	 */
    public ArrayList<PistaDTO> requestPistasNoDisponibles() { 
         ArrayList<PistaDTO> pistasNoDisponibles = new ArrayList<>();
         String query = sqlProperties.getSQLQuery("sql.select.requestPistasNoDisponibles");
         try {
             DBConnection dbConnection = new DBConnection();
             Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt =connection.prepareStatement(query);
             ResultSet rs = (ResultSet) pstmt.executeQuery();
             while (rs.next()) {
                 String nombre = rs.getString("Nombre");
                 boolean tipo = rs.getBoolean("Tipo");
                 TipoPista tamaño = TipoPista.valueOf(rs.getString("Tamaño"));
                 int maxJugadores = rs.getInt("Jugadores_maximos");
                //Añadir materiales a la pista
                ArrayList<MaterialDTO> materiales= new ArrayList<>();
                query = sqlProperties.getSQLQuery("sql.select.requestPistasNoDisponiblesMateriales");
                PreparedStatement pstmtMateriales =connection.prepareStatement(query);
                pstmtMateriales.setString(1, nombre);
                ResultSet rs2 = (ResultSet) pstmtMateriales.executeQuery();
                while(rs2.next()){
                    MaterialDTO material= new MaterialDTO();
                    material.setId(rs2.getInt("Id_material"));
                    material.setEstado(Estado.valueOf(rs2.getString("Estado")));
                    material.setUso(rs2.getBoolean("Uso"));
                    material.setTipo(Tipo.valueOf(rs2.getString("Tipo")));
                    materiales.add(material);
                }
                if (pstmtMateriales != null) {
                	pstmtMateriales.close();
                }

                 PistaDTO pista = new PistaDTO(nombre, false, tipo, tamaño, maxJugadores,materiales);
                 pistasNoDisponibles.add(pista);
             }
             // Cerrar recursos
             if (pstmt != null) {
                 pstmt.close();
             }
             dbConnection.closeConnection();
         } catch (SQLException e) {
             System.out.println("Error al obtener pistas no disponibles: " + e.getMessage());
         }
         return pistasNoDisponibles;
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
     * Este método consulta las pistas que tienen un número máximo de jugadores específico y un tamaño determinado.
     * Además, para cada pista que cumple con los criterios, obtiene los materiales asociados a esa pista.
     * 
     * @param maxjugadores El número máximo de jugadores que pueden usar la pista.
     * @param tamaño El tipo de tamaño de la pista (por ejemplo, pequeña, mediana, grande).
     * @return Una lista de objetos `PistaDTO` que cumplen con los criterios de búsqueda.
     */
    public ArrayList<PistaDTO> requestPistasJugadoresTamaño(int maxjugadores, TipoPista tamaño) {
         ArrayList<PistaDTO> buscarpistas = new ArrayList<>();
                  
         String query = sqlProperties.getSQLQuery("sql.select.requestPistasJugadoresTamaño");
         try {
             DBConnection dbConnection = new DBConnection();
             Connection connection = dbConnection.getConnection();
             PreparedStatement pstmt =connection.prepareStatement(query);
             pstmt.setInt(1, maxjugadores);
             pstmt.setString(2, tamaño.name());
             ResultSet rs = (ResultSet) pstmt.executeQuery();
             while (rs.next()) {
                String nombre = rs.getString("Nombre");
                boolean tipo = rs.getBoolean("Tipo");
                TipoPista tamaño_pist = TipoPista.valueOf(rs.getString("Tamaño"));
                boolean estado = rs.getBoolean("Estado");
                int maxjugadores_pist = rs.getInt("Jugadores_maximos");
                //Añadir materiales a la pista
                ArrayList<MaterialDTO> materiales= new ArrayList<>();
                
                query = sqlProperties.getSQLQuery("sql.select.requestPistasJugadoresTamañoMateriales");
                PreparedStatement pstmtMateriales =connection.prepareStatement(query);
                pstmtMateriales.setString(1, nombre);
                ResultSet rs2 = (ResultSet) pstmtMateriales.executeQuery();
                while(rs2.next()){
                    MaterialDTO material= new MaterialDTO();
                    material.setId(rs2.getInt("Id_material"));
                    material.setEstado(Estado.valueOf(rs2.getString("Estado")));
                    material.setUso(rs2.getBoolean("Uso"));
                    material.setTipo(Tipo.valueOf(rs2.getString("Tipo")));
                    materiales.add(material);
                }
                
                if (pstmtMateriales != null) {
                	pstmtMateriales.close(); 
                }
                PistaDTO pista = new PistaDTO(nombre, estado, tipo, tamaño_pist, maxjugadores_pist, materiales);
                buscarpistas.add(pista);
             }
             // Cerrar recursos
             if (pstmt != null) {
                 pstmt.close();
             }
             dbConnection.closeConnection();
         } catch (SQLException e) {
             System.out.println("Error al obtener pistas buscando por jugadores y tamaño: " + e.getMessage());
         }
         return buscarpistas;
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
			//Inserta el material y la pista en la tabla Materiales_Pistas
            String query = sqlProperties.getSQLQuery("sql.insert.assignNewMaterialToPista");
			PreparedStatement pstmt =connection.prepareStatement(query);
			pstmt.setInt(1, id_material);
			pstmt.setString(2, nombre_pista);
			pstmt.executeUpdate();
			if (pstmt != null){ 
				pstmt.close(); 
			}
			//Actualiza el estado del material en la tabla MAteriales
            query = sqlProperties.getSQLQuery("sql.update.assignNewMaterialToPista");
			pstmt =connection.prepareStatement(query);
			pstmt.setString(1, Estado.RESERVADO.name());
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
     * Este método obtiene el conteo de materiales de un tipo específico asignados a una pista dada.
     * 
     * @param nombre_pista El nombre de la pista para la cual se solicita el conteo de materiales.
     * @param tipo El tipo de material que se desea contar (por ejemplo, pelotas, canastas, conos).
     * @return Un número entero que representa el conteo de materiales del tipo especificado para la pista dada.
     * Si no se encuentra ningún material de ese tipo, devuelve 0.
     */
    public int requestContTipoMaterial(String nombre_pista,Tipo tipo) {
        String query = sqlProperties.getSQLQuery("sql.select.requestContTipoMaterial");
        int cont=0;
        try {
            DBConnection dbConnection = new DBConnection();
            Connection connection = dbConnection.getConnection();
            PreparedStatement pstmt =connection.prepareStatement(query);
            pstmt.setString(1, tipo.name());
            pstmt.setString(2, nombre_pista);
            ResultSet rs = (ResultSet) pstmt.executeQuery();
            if(rs.next()){
            	cont++;
            }
            // Cerrar recursos
            if (pstmt != null) {
                pstmt.close();
            }
            dbConnection.closeConnection();
            
        } catch (SQLException e) {
            System.out.println("Error al obtener conteo del tipo de material: " + e.getMessage());
        }
        return cont;
    }
    
}