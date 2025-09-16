<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ page import="java.util.*,java.text.*,java.sql.*, java.io.InputStream, data.dao.jugadores.*"%>
<%@ include file="/include/no_iniciosesion.jsp" %>
<%
    // Obtener los parámetros del formulario
    String nombre = request.getParameter("nombre");
    String correo = request.getParameter("correo");
    String contrasena = request.getParameter("contrasena");
    String fechaNacimiento = request.getParameter("fechaNacimiento");

    boolean validacionCorrecta = true;

    if (nombre == null || nombre.isEmpty()) {
        validacionCorrecta = false;
    }
    if (correo == null || correo.isEmpty() || !correo.contains("@")) {
        validacionCorrecta = false;
    }
    if (contrasena == null || contrasena.isEmpty()) {
        validacionCorrecta = false;
    }
    if (fechaNacimiento == null || fechaNacimiento.isEmpty()) {
        validacionCorrecta = false;
    }

    if (validacionCorrecta) {
        JugadorDAO jugadorDAO = new JugadorDAO();

        if(jugadorDAO.createNewUserRegistro(correo, nombre, java.sql.Date.valueOf(fechaNacimiento), contrasena)){
            response.sendRedirect("/pract3/mvc/view/common/registro_correctoView.jsp");
            return;
        }
        else{
            response.sendRedirect("/pract3/mvc/view/common/registro_fallidoView.jsp");
            return;
        }

    } else {
        response.sendRedirect("/pract3/mvc/view/common/registro_fallidoView.jsp");
        return;
    }
%>