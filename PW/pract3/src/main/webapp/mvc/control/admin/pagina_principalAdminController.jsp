<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ page import="java.util.*, java.text.*, java.io.*, java.sql.*, display.CustomerBean, data.dao.reservas.*, data.dao.jugadores.*, data.dto.jugadores.*" %>
<%@ include file="/include/iniciosesionAdmin.jsp" %>

<%
    ArrayList<String> nombres = new ArrayList<>();
    ArrayList<String> correos = new ArrayList<>();
    ArrayList<java.sql.Date> fechas_inscripcion = new ArrayList<>();
    ArrayList<Integer> numero_reservas = new ArrayList<>();

    JugadorDAO jugadorDAO = new JugadorDAO();
    ArrayList<JugadorDTO> usuarios = jugadorDAO.requestAllClients();
    ReservaDAO reservaDAO = new ReservaDAO();

    for(JugadorDTO usuario : usuarios){
        correos.add(usuario.getCorreo());
        nombres.add(usuario.getNombreyapellidos());
        java.util.Date fecha = usuario.getFechainscripcion();
        if(fecha == null){
            fechas_inscripcion.add(null);
        }
        else{
            fechas_inscripcion.add(new java.sql.Date(fecha.getTime()));
        }
    }
    numero_reservas=reservaDAO.requestNumeroReservas(correos,fechas_inscripcion);

    String nombresstr = nombres.toString();
    String fechas_inscripcionstr = fechas_inscripcion.toString();
    String numero_reservasstr = numero_reservas.toString();

%>

<jsp:forward page="/mvc/view/admin/pagina_principalAdminView.jsp">
    <jsp:param name="nombresstr" value="<%= nombresstr %>" />
    <jsp:param name="fechas_inscripcionstr" value="<%= fechas_inscripcionstr %>" />
    <jsp:param name="numero_reservasstr" value="<%= numero_reservasstr %>" />
</jsp:forward>