package dao;

import aux.SQLProperties;
import aux.DBConnection;
import dto.Evento;
import java.sql.*;
import java.util.ArrayList;

/**
 * Clase que gestiona la persistencia de los eventos en la base de datos.
 */
public class EventoDAO {

    private SQLProperties sqlProperties;

    public EventoDAO() {
        sqlProperties = new SQLProperties();
    }

    /**
     * Inserta un nuevo evento en la base de datos.
     * @param evento Objeto Evento con todos los datos necesarios.
     * @return true si el evento fue insertado correctamente, false si ocurrió algún error.
     */
    public Boolean insertarEvento(Evento evento) {
        Boolean publicado = false;
        DBConnection dbConnection = new DBConnection();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = dbConnection.getConnection();
            String sql = sqlProperties.getSQLQuery("insertar_evento");
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, evento.getNombre());
            preparedStatement.setString(2, evento.getDescripcion());
            preparedStatement.setString(3, evento.getLugar());
            preparedStatement.setDate(4, evento.getFecha());
            preparedStatement.setFloat(5, evento.getLimiteReventa());
            preparedStatement.setString(6, evento.getCorreoOrganizador());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                publicado = true;
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }
            dbConnection.closeConnection();
        } catch (SQLException e) {
            publicado = false;
        }

        return publicado;
    }

    /**
     * Elimina un evento de la base de datos a partir de su nombre.
     * @param nombreEvento Nombre del evento que se desea cancelar.
     * @return true si el evento fue eliminado, false si ocurrió un error.
     */
    public Boolean cancelarEvento(String nombreEvento) {
        Boolean cancelado = false;
        DBConnection dbConnection = new DBConnection();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = dbConnection.getConnection();
            String sql = sqlProperties.getSQLQuery("cancelar_evento");
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nombreEvento);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                cancelado = true;
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }
            dbConnection.closeConnection();
        } catch (SQLException e) {
            cancelado = false;
        }

        return cancelado;
    }

    /**
     * Modifica los datos de un evento ya existente.
     * @param evento Objeto Evento con los nuevos datos.
     * @return true si la modificación fue exitosa, false si falló.
     */
    public Boolean modificarEvento(Evento evento) {
        Boolean modificado = false;
        DBConnection dbConnection = new DBConnection();
        PreparedStatement preparedStatement = null;

        try {
            Connection connection = dbConnection.getConnection();
            String sql = sqlProperties.getSQLQuery("modificar_evento");
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, evento.getDescripcion());
            preparedStatement.setString(2, evento.getLugar());
            preparedStatement.setDate(3, evento.getFecha());
            preparedStatement.setFloat(4, evento.getLimiteReventa());
            preparedStatement.setString(5, evento.getNombre());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                modificado = true;
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }
            dbConnection.closeConnection();
        } catch (SQLException e) {
            System.out.println("Error al modificar el evento: " + e.getMessage());
            modificado = false;
        }

        return modificado;
    }

    /**
     * Devuelve una lista de eventos publicados por un organizador.
     * @param correoOrganizador Correo del organizador que publicó los eventos.
     * @return Lista de objetos Evento publicados por el organizador.
     */
    public ArrayList<Evento> verEventosOrganizador(String correoOrganizador) {
        ArrayList<Evento> eventos = new ArrayList<>();
        DBConnection dbConnection = new DBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = dbConnection.getConnection();
            String sql = sqlProperties.getSQLQuery("ver_eventos_organizador");
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, correoOrganizador);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Evento evento = new Evento();
                evento.setNombre(resultSet.getString("nombre"));
                evento.setDescripcion(resultSet.getString("descripcion"));
                evento.setLugar(resultSet.getString("ubicacion"));
                evento.setFecha(resultSet.getDate("fecha"));
                evento.setLimiteReventa(resultSet.getFloat("limiteReventa"));
                eventos.add(evento);
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }
            dbConnection.closeConnection();
        } catch (SQLException e) {
            System.out.println("Error al obtener eventos: " + e.getMessage());
        }

        return eventos;
    }

    /**
     * Devuelve una lista de eventos publicados por un organizador.
     * Se filtra por el correo del organizador y por eventos cuya fecha sea igual o posterior a hoy.
     * @param correoOrganizador Correo del organizador que publicó los eventos.
     * @return Lista de objetos Evento publicados por el organizador.
     */
    public ArrayList<Evento> listarEventosDisponibles() {
        ArrayList<Evento> eventos = new ArrayList<>();
        DBConnection dbConnection = new DBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            Connection connection = dbConnection.getConnection();
            String sql = sqlProperties.getSQLQuery("listar_eventos_disponibles");
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Evento evento = new Evento();
                evento.setNombre(resultSet.getString("nombre"));
                evento.setDescripcion(resultSet.getString("descripcion"));
                evento.setLugar(resultSet.getString("ubicacion"));
                evento.setFecha(resultSet.getDate("fecha"));
                eventos.add(evento);
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }
            dbConnection.closeConnection();
        } catch (SQLException e) {
            System.out.println("Error al listar eventos: " + e.getMessage());
        }

        return eventos;
    }

    /**
     * Consulta el límite de reventa asignado a un evento determinado.
     * @param nombreEventoAsociado Nombre del evento del que se quiere conocer el límite.
     * @return Valor float entre 0 y 1 representando el porcentaje límite de reventa.
     */
    public float consultarLimiteReventa(String nombreEventoAsociado) {
        float limite = 0.1f;
        DBConnection dbConnection = new DBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Connection connection = null;

        try {
            connection = dbConnection.getConnection();
            String sql = sqlProperties.getSQLQuery("consultar_limite_reventa");
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nombreEventoAsociado);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                limite = resultSet.getFloat("limiteReventa");
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar el límite de reventa: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return limite;
    }

}
