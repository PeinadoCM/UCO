package dao;

import aux.SQLProperties;
import aux.DBConnection;
import dto.Organizador;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Clase que gestiona la persistencia de los organizadores en la base de datos.
 */
public class OrganizadorDAO {

    private SQLProperties sqlProperties;

    public OrganizadorDAO() {
        sqlProperties = new SQLProperties();
    }

    /**
     * Valida las credenciales de un organizador.
     * @param organizador Objeto Organizador con correo y contraseña introducidos por el usuario.
     * @return true si las credenciales son correctas, false si no coinciden o hay error.
     */
    public Boolean validarOrganizador(Organizador organizador) {
        Boolean permitirAcceso = false;
        DBConnection dbConnection = new DBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = dbConnection.getConnection();
            String sql = sqlProperties.getSQLQuery("validar_organizador");
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, organizador.getCorreo());

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("contrasena");
                if (BCrypt.checkpw(organizador.getContrasena(), hashedPassword)) {
                    permitirAcceso = true;
                } else {
                    permitirAcceso = false;
                }
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            dbConnection.closeConnection();
        } catch (SQLException e) {
            permitirAcceso = false;
        }
        return permitirAcceso;
    }

    /**
     * Recarga saldo en el monedero del organizador.
     * @param correo Correo del organizador cuyo monedero se va a recargar.
     * @param cantidad Cantidad positiva a añadir al monedero.
     * @return true si la actualización en la base de datos fue exitosa, false en caso de error.
     */
    public boolean recargarMonedero(String correo, float cantidad) {
        DBConnection dbConnection = new DBConnection();
        boolean exito = false;
        String sql = sqlProperties.getSQLQuery("recargar_monedero_organizador");
        int filas;

        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, cantidad);
            ps.setString(2, correo);

            filas = ps.executeUpdate();
            exito = filas > 0;

            ps.close();
            dbConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exito;
    }

    /**
     * Resta saldo del monedero del organizador.
     * @param correo Correo del organizador al que se le descontará saldo.
     * @param cantidad Cantidad a restar (debe ser positiva y menor o igual al saldo actual).
     * @return true si se realizó el descuento correctamente, false en caso de error o fallo en la base de datos.
     */
    public boolean restarMonedero(String correo, float cantidad) {
        DBConnection dbConnection = new DBConnection();
        boolean exito = false;
        String sql = sqlProperties.getSQLQuery("restar_monedero_organizador");
        int filas;

        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, cantidad);
            ps.setString(2, correo);

            filas = ps.executeUpdate();
            exito = filas > 0;

            ps.close();
            dbConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exito;
    }
}