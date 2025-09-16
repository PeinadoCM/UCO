package dao;

import dto.Entrada;
import dto.EntradaFactory;
import dto.TipoEntrada;
import dto.TipoTransaccion;

import java.util.ArrayList;
import aux.DBConnection;
import aux.SQLProperties;

import java.sql.*;

public class EntradaDAO {

    private SQLProperties sqlProperties;

    public EntradaDAO() {
        sqlProperties = new SQLProperties();
    }

    /**
     * Recupera los IDs de las entradas vendidas asociadas a un usuario.
     * @param correo Correo del usuario.
     * @return Lista de IDs de entradas.
     */
    public ArrayList<Integer> getIdEntradasByCorreo(String correo) {
        ArrayList<Integer> entradas = new ArrayList<>();
        String sql = sqlProperties.getSQLQuery("ver_entradas_correo");
        DBConnection dbConnection = new DBConnection();

        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, correo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Integer idEntrada = rs.getInt("idEntrada");
                entradas.add(idEntrada);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entradas;
    }

    /**
     * Obtiene los IDs de todas las entradas registradas para un evento.
     * @param nombreEvento Nombre del evento.
     * @return Lista de IDs de entradas del evento.
     */
    public ArrayList<Integer> getIdEntradasByEvento(String nombreEvento) {
        ArrayList<Integer> entradas = new ArrayList<>();
        String sql = sqlProperties.getSQLQuery("ver_identradas_evento");
        DBConnection dbConnection = new DBConnection();

        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, nombreEvento);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Integer idEntrada = rs.getInt("id");
                entradas.add(idEntrada);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return entradas;
    }

    /**
     * Recupera los objetos Entrada a partir de una lista de IDs.
     * Incluye tipo, precio y nombre del evento.
     * @param idEntradas Lista de IDs de entrada.
     * @return Lista de objetos Entrada.
     */
    public ArrayList<Entrada> getEntradasById(ArrayList<Integer> idEntradas) {
        ArrayList<Entrada> entradas = new ArrayList<>();
        String sql = sqlProperties.getSQLQuery("ver_entrada");
        DBConnection dbConnection = new DBConnection();
        TipoEntrada tipoEntrada = null;
        String nombreEvento = null;
        Entrada entrada = null;
        float precio = 0;
        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);

            for (Integer id : idEntradas) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    tipoEntrada = TipoEntrada.valueOf(rs.getString("tipo"));
                    precio = rs.getFloat("precio");
                    nombreEvento = rs.getString("evento");
                    entrada = EntradaFactory.createEntrada(tipoEntrada, precio, nombreEvento);
                    entrada.setId(id);
                    entradas.add(entrada);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entradas;
    }

    /**
     * Similar a getEntradasById, pero omite las entradas que ya han sido puestas en reventa.
     * @param idEntradas Lista de IDs de entrada.
     * @return Lista de objetos Entrada aún no revendidas.
     */
    public ArrayList<Entrada> getEntradasByIdSinReventa(ArrayList<Integer> idEntradas) {
        ArrayList<Entrada> entradas = new ArrayList<>();
        String sql = sqlProperties.getSQLQuery("ver_entrada_sin_reventa");
        DBConnection dbConnection = new DBConnection();
        TipoEntrada tipoEntrada = null;
        String nombreEvento = null;
        Entrada entrada = null;
        float precio = 0;
        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);

            for (Integer id : idEntradas) {
                pstmt.setInt(1, id);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    tipoEntrada = TipoEntrada.valueOf(rs.getString("tipo"));
                    precio = rs.getFloat("precio");
                    nombreEvento = rs.getString("evento");
                    entrada = EntradaFactory.createEntrada(tipoEntrada, precio, nombreEvento);
                    entrada.setId(id);
                    entradas.add(entrada);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entradas;
    }

    /**
     * Elimina todas las entradas vendidas de la base de datos.
     * @param idEntradas Lista de IDs de entradas a eliminar.
     * @return true si se eliminaron correctamente, false en caso contrario.
     */
    public Boolean eliminarEntradasVendidasByIdEntrada(ArrayList<Integer> idEntradas) {
        Boolean eliminado = false;
        String sql = sqlProperties.getSQLQuery("eliminar_entradas_vendidas");
        DBConnection dbConnection = new DBConnection();

        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement pstmt = connection.prepareStatement(sql);

            for (Integer id : idEntradas) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
            }
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
     * Elimina todas las entradas de un evento específico.
     * @param nombreEvento Nombre del evento cuyas entradas se desean eliminar.
     * @return true si se eliminaron correctamente, false en caso contrario.
     */
    public Boolean eliminarEntradasByNombreEvento(String nombreEvento) {
        Boolean eliminado = false;
        String sql = sqlProperties.getSQLQuery("eliminar_entradas_evento");
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
     * Inserta una nueva entrada en la base de datos.
     * @param entrada Objeto Entrada con los datos a insertar.
     * @return true si la entrada se insertó correctamente, false en caso contrario.
     */
    public boolean insertarEntrada(Entrada entrada) {
        DBConnection dbConnection = new DBConnection();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        boolean publicada = false;

        try {
            connection = dbConnection.getConnection();
            String sql = sqlProperties.getSQLQuery("insertar_entrada");
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entrada.getTipo().name());
            preparedStatement.setFloat(2, entrada.getPrecio());
            preparedStatement.setString(3, entrada.getNombreEvento());
            preparedStatement.setInt(4, entrada.getCantidad());
            preparedStatement.setString(5, TipoTransaccion.VENTAPRIMARIA.name());
            preparedStatement.setString(6, entrada.getCorreoVendedor());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                publicada = true;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar entrada: " + e.getMessage());
            publicada = false;
        } finally {
        
            try {
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

        return publicada;
    }

    /**
     * Inserta una entrada de reventa en la base de datos.
     * @param entrada Objeto Entrada con los datos a insertar.
     * @return ID de la reventa insertada, o 0 si hubo error.
     */
    public int insertarReventa(Entrada entrada) {
        DBConnection dbConnection = new DBConnection();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet generatedKeys = null;
        int idReventa = 0;
        TipoTransaccion tipotransaccion = TipoTransaccion.VENTASECUNDARIA;

        try {
            connection = dbConnection.getConnection();
            String sql = sqlProperties.getSQLQuery("insertar_reventa");
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entrada.getNombreEvento());
            preparedStatement.setString(2, entrada.getCorreoVendedor());
            preparedStatement.setString(3, entrada.getTipo().name());
            preparedStatement.setFloat(4, entrada.getPrecio());
            preparedStatement.setString(5, tipotransaccion.name());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idReventa = generatedKeys.getInt(1); 
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar entrada de reventa: " + e.getMessage());
        } finally {
       
            try {
                if (generatedKeys != null) generatedKeys.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return idReventa;
    }

    /**
     * Actualiza el ID de una entrada en la base de datos.
     * @param idEntradaOriginal ID de la entrada original.
     * @param idReventa Nuevo ID de reventa.
     * @return true si se actualizó correctamente, false en caso contrario.
     */
    public boolean actualizarId(int idEntradaOriginal, int idReventa) {
        DBConnection dbConnection = new DBConnection();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        boolean actualizado = false;

        try {
            connection = dbConnection.getConnection();
            String sql = sqlProperties.getSQLQuery("actualizar_id_entrada_vendida");
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idReventa);
            preparedStatement.setInt(2, idEntradaOriginal);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                actualizado = true;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar id de entrada vendida: " + e.getMessage());
        } finally {
  
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return actualizado;
    }

    /**
     * Recupera las entradas disponibles de un tipo de venta especifico para un evento específico.
     * @param nombreEvento Nombre del evento.
     * @param tipoTransaccion Tipo de transacción (venta primaria o secundaria).
     * @return Lista de entradas disponibles.
     */
    public ArrayList<Entrada> getEntradasDisponiblesByNombreEvento(String nombreEvento,
            TipoTransaccion tipoTransaccion) {
        ArrayList<Entrada> entradas = new ArrayList<>();
        DBConnection dbConnection = new DBConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = sqlProperties.getSQLQuery("ver_entradas_disponibles_evento");

        try {
            Connection connection = dbConnection.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nombreEvento);
            preparedStatement.setString(2, tipoTransaccion.name());
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                TipoEntrada tipo = TipoEntrada.valueOf(resultSet.getString("tipo"));
                float precio = resultSet.getFloat("precio");
                String correoVendedor = resultSet.getString("correoVendedor");
                Entrada entrada = EntradaFactory.createEntradaConId(id, tipo, precio, nombreEvento, correoVendedor);

                entradas.add(entrada);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener entradas del evento: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null)
                    resultSet.close();
                if (preparedStatement != null)
                    preparedStatement.close();
                dbConnection.closeConnection();
            } catch (SQLException e) {
                System.out.println("Error cerrando recursos: " + e.getMessage());
            }
        }

        return entradas;
    }

    /**
     * Disminuye la cantidad de entradas disponibles en la base de datos.
     * @param idEntrada ID de la entrada a disminuir.
     * @return true si se disminuyó correctamente, false en caso contrario.
     */
    public boolean disminuirCantidadEntrada(int idEntrada) {
        DBConnection dbConnection = new DBConnection();
        Connection conn = null;
        PreparedStatement psUpdateEntrada = null;

        try {
            conn = dbConnection.getConnection();

            String sql = sqlProperties.getSQLQuery("disminuir_cantidad_entrada");
            psUpdateEntrada = conn.prepareStatement(sql);
            psUpdateEntrada.setInt(1, idEntrada);
            int filasAfectadas = psUpdateEntrada.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al disminuir cantidad de entrada: " + e.getMessage());
            return false;
        } finally {
            try {
                if (psUpdateEntrada != null)
                    psUpdateEntrada.close();
                dbConnection.closeConnection();
            } catch (SQLException e) {
                System.out.println("Error cerrando recursos: " + e.getMessage());
            }
        }
    }

    /**
     * Inserta una entrada vendida en la base de datos.
     * @param idEntrada ID de la entrada vendida.
     * @param correoUsuario Correo del usuario que compró la entrada.
     * @return true si se insertó correctamente, false en caso contrario.
     */
    public boolean insertarEntradaVendida(int idEntrada, String correoUsuario) {
        DBConnection dbConnection = new DBConnection();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        boolean insertada = false;

        try {
            connection = dbConnection.getConnection();
            String sql = sqlProperties.getSQLQuery("insertar_entrada_vendida");
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idEntrada);
            preparedStatement.setString(2, correoUsuario);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                insertada = true;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar entrada vendida: " + e.getMessage());
            insertada = false;
        } finally {

            try {
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

        return insertada;
    }
}
