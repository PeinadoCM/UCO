package dao;

import aux.SQLProperties;
import aux.DBConnection;
import dto.Usuario;
import java.sql.*;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Clase que gestiona la persistencia de los usuarios en la base de datos.
 */
public class UsuarioDAO {
    
    private SQLProperties sqlProperties;

    public UsuarioDAO() {
        sqlProperties = new SQLProperties();
    }  
    
    /**
     * Inserta un nuevo usuario en la base de datos con contraseña encriptada.
     * @param usuario Objeto Usuario con la información a registrar.
     * @return true si se insertó correctamente, false si falló.
     */
    public Boolean insertarUsuario(Usuario usuario) {
        Boolean registrado = false;
        DBConnection dbConnection = new DBConnection();
        PreparedStatement preparedStatement = null;
        String hashedPassword = BCrypt.hashpw(usuario.getContrasena(), BCrypt.gensalt());

        try {
            Connection connection = dbConnection.getConnection();
            String sql = sqlProperties.getSQLQuery("insertar_usuario");
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usuario.getCorreo());
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setString(3, usuario.getNombreCompleto());
            preparedStatement.setString(4, usuario.getDni());
            preparedStatement.setInt(5, usuario.getTelefono());
            preparedStatement.setFloat(6, usuario.getMonedero());
            

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                registrado = true;
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            dbConnection.closeConnection();
        } catch (SQLException e) {
            registrado = false;
        } 
        return registrado;
    }


    /**
     * Valida las credenciales del usuario comprobando su contraseña encriptada.
     * @param usuario Objeto Usuario con correo y contraseña ingresados.
     * @return true si las credenciales son correctas, false si no.
     */
    public Boolean validarUsuario(Usuario usuario) {
        Boolean permitirAcceso = false;
        DBConnection dbConnection = new DBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = dbConnection.getConnection();
            String sql = sqlProperties.getSQLQuery("validar_usuario");
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, usuario.getCorreo());

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String hashedPassword = resultSet.getString("contrasena");
                if(BCrypt.checkpw(usuario.getContrasena(), hashedPassword)) {
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
     * Actualiza el saldo del monedero del usuario.
     * @param correo Correo del usuario a actualizar.
     * @param cantidad Cantidad a agregar al monedero.
     * @return true si se actualizó correctamente, false si falló.
     */
    public boolean recargarMonedero(String correo, float cantidad) {
        DBConnection dbConnection = new DBConnection();
        boolean exito = false;
        String sql = sqlProperties.getSQLQuery("recargar_monedero");
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
     * Resta una cantidad del monedero del usuario.
     * @param correo Correo del usuario a actualizar.
     * @param cantidad Cantidad a restar del monedero.
     * @return true si se actualizó correctamente, false si falló.
     */
    public boolean restarMonedero(String correo, float cantidad) {
        DBConnection dbConnection = new DBConnection();
        boolean exito = false;
        String sql = sqlProperties.getSQLQuery("restar_monedero_usuario");
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
     * Consulta el saldo actual del monedero de un usuario.
     * @param correo Correo del usuario.
     * @return Saldo actual como float.
     */
    public float consultarMonedero(String correo) {
        DBConnection dbConnection = new DBConnection();
        float saldo = 0.0f;
        String sql = sqlProperties.getSQLQuery("consultar_monedero");

        try {
            Connection conn = dbConnection.getConnection();
            sql = sqlProperties.getSQLQuery("consultar_monedero");
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, correo);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                saldo = rs.getFloat("monedero");
            }

            rs.close();
            ps.close();
            dbConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

            return saldo;
    }


}
