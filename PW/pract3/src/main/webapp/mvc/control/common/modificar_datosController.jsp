<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ page import="java.util.*,java.text.*,java.sql.*, java.io.InputStream, display.CustomerBean, data.dao.jugadores.*"%>
<%@ include file="/include/iniciosesion.jsp" %>

<%
    String nombre = request.getParameter("nombre");
    String contrasena = request.getParameter("contrasena");
    String fecha_nacimiento = request.getParameter("fechaNacimiento");
    CustomerBean cB = (CustomerBean) session.getAttribute("customerBean");
    java.sql.Date sqlfecha_nacimiento = java.sql.Date.valueOf(fecha_nacimiento);
    boolean validacionCorrecta = true;

    if (nombre == null || nombre.isEmpty()) {
        validacionCorrecta = false;
    }
    if (contrasena == null || contrasena.isEmpty()) {
        validacionCorrecta = false;
    }
    if (fecha_nacimiento == null || fecha_nacimiento.isEmpty()) {
        validacionCorrecta = false;
    }

    if(validacionCorrecta){
        JugadorDAO jugadorDAO = new JugadorDAO();
        if(jugadorDAO.updateDatos(nombre, contrasena, sqlfecha_nacimiento, cB.getCorreo())){
%>
            <jsp:setProperty property="contraseña" value="<%=contrasena%>" name="customerBean"/>
            <jsp:setProperty property="nombre" value="<%=nombre%>" name="customerBean"/>
            <jsp:setProperty property="fecha_nacimiento" value="<%=sqlfecha_nacimiento%>" name="customerBean"/>
<%
        }
    }
    if(cB.getAdmin()){
        response.sendRedirect("/pract3/mvc/control/admin/pagina_principalAdminController.jsp");
        return;
    }
    else{
        response.sendRedirect("/pract3/mvc/control/user/pagina_principalUserController.jsp");
        return;
    }

%>