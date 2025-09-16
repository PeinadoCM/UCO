<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ page import="java.util.*,java.text.*,java.sql.*, java.io.InputStream, data.dao.jugadores.*, data.dto.jugadores.*"%>
<%@ include file="/include/no_iniciosesion.jsp" %>
<%
    // Obtener los parámetros del formulario
    String correo = request.getParameter("correo");
    String contrasena = request.getParameter("contrasena");

    JugadorDAO jugadorDAO = new JugadorDAO();
    JugadorDTO usuario = jugadorDAO.requestUserInicioSesion(correo,contrasena);

    if(usuario != null){
%>
        <jsp:setProperty property="correo" value="<%=usuario.getCorreo()%>" name="customerBean"/>
        <jsp:setProperty property="contraseña" value="<%=usuario.getContraseña()%>" name="customerBean"/>
        <jsp:setProperty property="nombre" value="<%=usuario.getNombreyapellidos()%>" name="customerBean"/>
        <jsp:setProperty property="fecha_nacimiento" value="<%=usuario.getFechanacimiento()%>" name="customerBean"/>
        <jsp:setProperty property="fecha_inscripcion" value="<%=usuario.getFechainscripcion()%>" name="customerBean"/>
        <jsp:setProperty property="admin" value="<%=usuario.getAdmin()%>" name="customerBean"/>
<%
        if(usuario.getAdmin()){
            response.sendRedirect("/pract3/mvc/control/admin/pagina_principalAdminController.jsp");
            return;
        }
        else{
            response.sendRedirect("/pract3/mvc/control/user/pagina_principalUserController.jsp");
            return;
        }
    }
    else{
        response.sendRedirect("/pract3/mvc/view/common/iniciar_sesion_fallidoView.jsp");
        return;
    }

%>