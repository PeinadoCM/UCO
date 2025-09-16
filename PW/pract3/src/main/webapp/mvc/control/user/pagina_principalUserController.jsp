<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ page import="java.util.*, java.text.*, java.io.*, java.sql.*, display.CustomerBean, data.dao.reservas.*" %>
<%@ include file="/include/iniciosesionUser.jsp" %>

<%
    CustomerBean cB = (CustomerBean) session.getAttribute("customerBean");

    ReservaDAO reservaDAO = new ReservaDAO();
    java.sql.Date proximaReservasql = reservaDAO.requestProximaReserva(cB.getCorreo());

%>

<jsp:forward page="/mvc/view/user/pagina_principalUserView.jsp">
    <jsp:param name="proximaReserva" value="<%= proximaReservasql %>" />
</jsp:forward>
