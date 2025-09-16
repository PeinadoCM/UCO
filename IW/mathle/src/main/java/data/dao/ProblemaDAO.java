package data.dao;

import java.sql.*;

import data.common.DBProperties;
import data.common.SQLProperties;

public class ProblemaDAO {
    
    private final String url;
    private final String user;
    private final String password;

    private SQLProperties sqlProperties;
    private DBProperties dbProperties;

    public ProblemaDAO() {
        sqlProperties = new SQLProperties();
        dbProperties = new DBProperties();
        url = dbProperties.getDBString("db.url");
        user = dbProperties.getDBString("db.username");
        password = dbProperties.getDBString("db.password");
    }   


    //Fuerza a cargar el driver de MariaDB
    static {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String obtenerOperacion(int dificultad, String modoJuego, Date fecha) {
        String query = sqlProperties.getSQLQuery("obtener_operacion");
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, dificultad);
            stmt.setString(2, modoJuego);
            stmt.setDate(3, fecha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("operacion");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertarProblema(int dificultad, String modoJuego, Date fecha, String operacion) {
        String query = sqlProperties.getSQLQuery("insertar_problema");
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, dificultad);
            stmt.setString(2, modoJuego);
            stmt.setDate(3, fecha);
            stmt.setString(4, operacion);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer obtenerIdProblemaPorCriterios(String modoJuego, int dificultad, Date fecha) {
        String sql = sqlProperties.getSQLQuery("obtener_id_problema");

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, modoJuego);
            stmt.setInt(2, dificultad);
            stmt.setDate(3, fecha);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // No encontrado
    }

}
