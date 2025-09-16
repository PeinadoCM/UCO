package mgr;

import java.util.ArrayList;
import java.util.Iterator;
import dao.*;
import dto.*;

/**
 * Clase que gestiona la lógica de negocio relacionada con transacciones y entradas.
 */
public class TransaccionMgr {

    private static TransaccionMgr instance;

    private TransaccionMgr() {
    }

    /**
     * Método para obtener la instancia única de TransaccionMgr (Singleton).
     * @return Instancia única de TransaccionMgr.
     */
    public static TransaccionMgr getInstance() {
        if (instance == null) {
            instance = new TransaccionMgr();
        }
        return instance;
    }

    /**
     * Devuelve una lista de todas las entradas del usuario.
     * @param correo Correo del usuario
     * @return Lista de entradas válidas para reventa
     */
    public ArrayList<Entrada> verEntradasUsuario(String correo) {
        ArrayList<Entrada> entradas = new ArrayList<>();
        ArrayList<Integer> idEntradas = new ArrayList<>();
        ArrayList<Evento> eventos = new ArrayList<>();
        ArrayList<String> nombreEventos = new ArrayList<>();
        EntradaDAO entradaDAO = new EntradaDAO();
        EventoDAO eventoDAO = new EventoDAO();

        idEntradas = entradaDAO.getIdEntradasByCorreo(correo);

        entradas = entradaDAO.getEntradasById(idEntradas);

        eventos = eventoDAO.listarEventosDisponibles();

        for (Evento evento : eventos) {
            nombreEventos.add(evento.getNombre());
        }

        Iterator<Entrada> iterator = entradas.iterator();
        while (iterator.hasNext()) {
            Entrada entrada = iterator.next();
            if (!nombreEventos.contains(entrada.getNombreEvento())) {
                iterator.remove();
            }
        }

        return entradas;
    }

    /**
     * Devuelve una lista de todas las entradas del usuario, menos aquellas que ha puesto en reventa.
     * @param correo Correo del usuario
     * @return Lista de entradas válidas para reventa
     */
    public ArrayList<Entrada> verEntradasUsuarioSinReventa(String correo) {
        ArrayList<Entrada> entradas = new ArrayList<>();
        ArrayList<Integer> idEntradas = new ArrayList<>();
        ArrayList<Evento> eventos = new ArrayList<>();
        ArrayList<String> nombreEventos = new ArrayList<>();
        EntradaDAO entradaDAO = new EntradaDAO();
        EventoDAO eventoDAO = new EventoDAO();

        idEntradas = entradaDAO.getIdEntradasByCorreo(correo);

        entradas = entradaDAO.getEntradasByIdSinReventa(idEntradas);

        eventos = eventoDAO.listarEventosDisponibles();

        for (Evento evento : eventos) {
            nombreEventos.add(evento.getNombre());
        }

        Iterator<Entrada> iterator = entradas.iterator();
        while (iterator.hasNext()) {
            Entrada entrada = iterator.next();
            if (!nombreEventos.contains(entrada.getNombreEvento())) {
                iterator.remove();
            }
        }

        return entradas;
    }


    /**
     * Devuelve el dinero a los compradores y resta el saldo al vendedor en caso de cancelación de evento.
     * @param nombreEvento Nombre del evento cancelado
     */
    public void devolverDinero(String nombreEvento) {
        TransaccionDAO transaccionDAO = new TransaccionDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        OrganizadorDAO organizadorDAO = new OrganizadorDAO();
        ArrayList<Transaccion> transacciones = new ArrayList<>();

        transacciones = transaccionDAO.getTransaccionesByNombreEvento(nombreEvento);

        for (Transaccion transaccion : transacciones) {
            usuarioDAO.recargarMonedero(transaccion.getCorreoComprador(), transaccion.getPrecio());
            if (transaccion.getTipo().equals(TipoTransaccion.VENTASECUNDARIA) == true) {
                usuarioDAO.restarMonedero(transaccion.getCorreoVendedor(), transaccion.getPrecio());
            } else {
                organizadorDAO.restarMonedero(transaccion.getCorreoVendedor(), transaccion.getPrecio());
            }
        }
    }

    /**
     * Elimina las transacciones asociadas a un evento.
     * @param nombreEvento Nombre del evento
     * @return true si se eliminaron correctamente
     */
    public Boolean eliminarTransacciones(String nombreEvento) {
        TransaccionDAO transaccionDAO = new TransaccionDAO();
        return transaccionDAO.eliminarTransaccionesByNombreEvento(nombreEvento);
    }


    /**
     * Elimina las entradas asociadas a un evento (tanto las entradas vendidas como las disponibles).
     * @param nombreEvento Nombre del evento
     * @return true si se eliminaron correctamente
     */
    public Boolean eliminarEntradas(String nombreEvento) {
        EntradaDAO entradaDAO = new EntradaDAO();
        ArrayList<Integer> idEntradas = new ArrayList<>();
        Boolean eliminado = false;

        idEntradas = entradaDAO.getIdEntradasByEvento(nombreEvento);

        eliminado = entradaDAO.eliminarEntradasVendidasByIdEntrada(idEntradas);

        eliminado = entradaDAO.eliminarEntradasByNombreEvento(nombreEvento);

        return eliminado;
    }


    /**
     * Publica una entrada como reventa y actualiza su ID.
     * @param entradaReventa Objeto Entrada con los datos de reventa
     * @param idEntrada ID de la entrada original
     * @return true si la reventa se publica y actualiza correctamente
     */
    public boolean publicarReventa(Entrada entradaReventa, int idEntrada) {
        EntradaDAO entradaDAO = new EntradaDAO();
        int idReventa = 0;
        boolean actualizado = false;

        idReventa = entradaDAO.insertarReventa(entradaReventa);

        if(idReventa != 0){
            actualizado = entradaDAO.actualizarId(idEntrada, idReventa); 
        }

        return actualizado;
    }


    /**
     * Lista las entradas disponibles para un evento concreto y tipo de transacción (primaria/secundaria).
     * @param nombreEvento Nombre del evento
     * @param tipoTransaccion Tipo de transacción (primaria o secundaria)
     * @return Lista de entradas disponibles
     */
    public ArrayList<Entrada> verEntradasPorEvento(String nombreEvento, TipoTransaccion tipoTransaccion) {
        EntradaDAO entradaDAO = new EntradaDAO();
        return entradaDAO.getEntradasDisponiblesByNombreEvento(nombreEvento, tipoTransaccion);
    }


    /**
     * Procesa la compra de una entrada, actualiza stock, transacciones y monederos.
     * @param correoUsuario Correo del comprador
     * @param entrada Objeto Entrada comprada
     * @param tipoTransaccion Tipo de transacción (primaria o secundaria)
     * @return true si la compra fue exitosa
     */
    public boolean comprarEntrada(String correoUsuario, Entrada entrada, TipoTransaccion tipoTransaccion) {
        EntradaDAO entradaDAO = new EntradaDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Transaccion transaccion = new Transaccion(correoUsuario, entrada.getCorreoVendedor(), entrada.getPrecio(),
                tipoTransaccion);
        TransaccionDAO transaccionDAO = new TransaccionDAO();
        OrganizadorDAO organizadorDAO = new OrganizadorDAO();
        ArrayList<Integer> idEntradas = new ArrayList<>();
        Boolean comprado = false;
        idEntradas.add(entrada.getId());

        
        comprado = entradaDAO.disminuirCantidadEntrada(entrada.getId());

        comprado = usuarioDAO.restarMonedero(correoUsuario, entrada.getPrecio());
        
        if (tipoTransaccion.equals(TipoTransaccion.VENTASECUNDARIA) == true) {
            comprado = usuarioDAO.recargarMonedero(entrada.getCorreoVendedor(), entrada.getPrecio());
            comprado = entradaDAO.eliminarEntradasVendidasByIdEntrada(idEntradas);
        } else {
            
            comprado = organizadorDAO.recargarMonedero(entrada.getCorreoVendedor(), entrada.getPrecio());
        }

        comprado = entradaDAO.insertarEntradaVendida(entrada.getId(), correoUsuario);
        
        comprado = transaccionDAO.insertarTransaccion(transaccion, entrada.getNombreEvento());
        
        return comprado;
    }

}
