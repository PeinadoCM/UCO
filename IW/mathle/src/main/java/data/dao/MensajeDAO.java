package data.dao;

import java.sql.*;
import java.util.*;

import data.common.DBProperties;
import data.common.SQLProperties;
import data.dto.Mensaje;

public class MensajeDAO {
    private final String url;
    private final String user;
    private final String password;

    private SQLProperties sqlProperties;
    private DBProperties dbProperties;

    public MensajeDAO() {
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

    // Publicar mensaje
    public boolean publicarMensaje(String autor, String mensaje, Timestamp fecha) {
        String query = sqlProperties.getSQLQuery("publicar_mensaje");
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, autor);
            stmt.setString(2, mensaje);
            stmt.setTimestamp(3, fecha);
            stmt.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener mensajes como lista de mapas
    public List<Mensaje> obtenerMensajes() {
        List<Mensaje> mensajes = new ArrayList<>();
        String query = sqlProperties.getSQLQuery("obtener_mensaje");

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Mensaje mensaje = new Mensaje( rs.getString("autor"), rs.getTimestamp("fecha"), rs.getString("mensaje"));
                mensajes.add(mensaje);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mensajes;
    }

}
