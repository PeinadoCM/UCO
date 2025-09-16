package data.dao;

import org.checkerframework.checker.units.qual.s;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import data.common.DBProperties;
import data.common.SQLProperties;
import data.dto.Usuario;


public class PartidaDAO {
    private final String url;
    private final String user;
    private final String password;

    private SQLProperties sqlProperties;
    private DBProperties dbProperties;

    public PartidaDAO() {
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

    public List<Usuario> obtenerRankingPorProblema(int idProblema) {
        List<Usuario> ranking = new ArrayList<>();
        String sql = sqlProperties.getSQLQuery("obtener_ranking_problema");

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProblema);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setNombre(rs.getString("nombreUsuario"));
                usuario.setPuntuacion(rs.getFloat("puntuacion")); // puntuación de esa partida
                ranking.add(usuario);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ranking;
    }

    public Boolean insertarPartida(String nombreUsuario, int idProblema, float puntuacion, int duracion, int intentos) {
        String query = sqlProperties.getSQLQuery("insertar_partida");
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombreUsuario);
            stmt.setInt(2, idProblema);
            stmt.setFloat(3, puntuacion);
            stmt.setInt(4, duracion);
            stmt.setInt(5, intentos);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean comprobarPartida(String nombreUsuario, int idProblema) {
        String query = sqlProperties.getSQLQuery("comprobar_partida");
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nombreUsuario);
            stmt.setInt(2, idProblema);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

