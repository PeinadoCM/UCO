package mgr;

import java.util.ArrayList;

import dto.Entrada;
import dto.Evento;
import dao.EventoDAO;
import dao.EntradaDAO;


/**
 * Clase que gestiona la lógica de negocio relacionada con eventos.
 */
public class EventoMgr {

    private static EventoMgr instance;

    private EventoMgr() {
    }

    /**
     * Método para obtener la instancia única de EventoMgr (Singleton).
     * @return Instancia única de EventoMgr.
     */
    public static EventoMgr getInstance() {
        if (instance == null) {
            instance = new EventoMgr();
        }
        return instance;
    }

    /**
     * Publica un evento y sus entradas asociadas.
     * @param evento Objeto Evento con los datos del nuevo evento.
     * @param entradas Lista de entradas (de tipo GENERAL, VIP, NUMERADA) asociadas al evento.
     * @return true si el evento y todas las entradas se publicaron correctamente.
     */
    public Boolean publicarEvento(Evento evento, ArrayList<Entrada> entradas) {
        EventoDAO eventoDAO = new EventoDAO(); 
        EntradaDAO entradaDAO = new EntradaDAO();   
        Boolean publicado = false;

        publicado = eventoDAO.insertarEvento(evento);
        for (Entrada entrada : entradas) {
            publicado = entradaDAO.insertarEntrada(entrada);
        }

        return publicado;
    }

    /**
     * Cancela un evento dado su nombre.
     * @param nombreEvento Nombre del evento a cancelar.
     * @return true si se canceló correctamente, false si hubo error.
     */
    public Boolean cancelarEvento(String nombreEvento) {
        EventoDAO eventoDAO = new EventoDAO();
        return eventoDAO.cancelarEvento(nombreEvento);
    }

    /**
     * Modifica un evento existente.
     * @param evento Objeto Evento con los nuevos datos (nombre, fecha, etc.).
     * @return true si la modificación se hizo correctamente.
     */
    public Boolean modificarEvento(Evento evento) {
        EventoDAO eventoDAO = new EventoDAO();
        return eventoDAO.modificarEvento(evento);
    }

    /**
     * Devuelve una lista de eventos disponibles (activos y no pasados).
     * Se usa para mostrar al usuario eventos en los que puede comprar entradas.
     * @return Lista de eventos disponibles.
     */
    public ArrayList<Evento> listarEventosDisponibles() {
        EventoDAO eventoDAO = new EventoDAO();
        return eventoDAO.listarEventosDisponibles();
    }

    /**
     * Devuelve los eventos publicados por un organizador específico.
     * @param correo Correo del organizador.
     * @return Lista de eventos de ese organizador.
     */
    public ArrayList<Evento> verEventosOrganizador(String correo) {
        EventoDAO eventoDAO = new EventoDAO();
        return eventoDAO.verEventosOrganizador(correo);
    }

    /**
     * Consulta el límite de reventa permitido para un evento.
     * 
     * @param nombreEvento Nombre del evento.
     * @return Límite de reventa como float.
     */
    public float getLimiteReventaEvento(String nombreEvento) {
        EventoDAO eventoDAO = new EventoDAO();
        return eventoDAO.consultarLimiteReventa(nombreEvento);
    }

}
