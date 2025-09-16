package dao;

import aux.SQLProperties;
import java.util.ArrayList;
import dto.Transaccion;
import aux.DBConnection;
import java.sql.*;
import dto.TipoTransaccion;

/**
 * Clase que gestiona la persistencia de las transacciones en la base de datos.
 */
public class TransaccionDAO {

    private SQLProperties sqlProperties;

    public TransaccionDAO() {
        sqlProperties = new SQLProperties();
    }


    /**
     * Obtiene todas las transacciones asociadas a un evento específico.
     * @param nombreEvento Nombre del evento.
     * @return Lista de transacciones relacionadas con el evento.
     */
    public ArrayList<Transaccion> getTransaccionesByNombreEvento(String nombreEvento) {
        ArrayList<Transaccion> transacciones = new ArrayList<>();
        String sql = sqlProperties.getSQLQuery("ver_transacciones");
        DBConnection dbConnection = new DBConnection();
        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, nombreEvento);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Transaccion transaccion = new Transaccion();
                transaccion.setCorreoComprador(rs.getString("Comprador"));
                transaccion.setCorreoVendedor(rs.getString("Vendedor"));
                transaccion.setPrecio(rs.getFloat("importe"));
                transaccion.setTipo(TipoTransaccion.valueOf(rs.getString("tipo")));
                transacciones.add(transaccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transacciones;
    }

    /**
     * Elimina todas las transacciones asociadas a un evento.
     * @param nombreEvento Nombre del evento del que se eliminarán las transacciones.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    public Boolean eliminarTransaccionesByNombreEvento(String nombreEvento) {
        Boolean eliminado = false;
        String sql = sqlProperties.getSQLQuery("eliminar_transaccion");
        DBConnection dbConnection = new DBConnection();
        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, nombreEvento);
            pstmt.executeUpdate();
            eliminado = true;
            if (pstmt != null) {
                pstmt.close();
            }
            dbConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return eliminado;
    }

    /**
     * Inserta una nueva transacción en la base de datos.
     * @param transaccion Objeto Transaccion que contiene los datos a insertar.
     * @param nombreEvento Nombre del evento asociado a la transacción.
     * @return true si se insertó correctamente, false si falló.
     */
    public Boolean insertarTransaccion(Transaccion transaccion, String nombreEvento) {
        Boolean insertado = false;
        String sql = sqlProperties.getSQLQuery("insertar_transaccion");
        DBConnection dbConnection = new DBConnection();
        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, transaccion.getCorreoComprador());
            pstmt.setString(2, transaccion.getCorreoVendedor());
            pstmt.setFloat(3, transaccion.getPrecio());
            pstmt.setString(4, transaccion.getTipo().toString());
            pstmt.setString(5, nombreEvento);
            pstmt.executeUpdate();
            insertado = true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return insertado;
    }

}
