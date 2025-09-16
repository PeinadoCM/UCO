<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ page import="java.util.*, java.text.*, java.io.*, java.sql.*, display.CustomerBean" %>
<%@ include file="/include/iniciosesionUser.jsp" %>
<%
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    CustomerBean cB = (CustomerBean) session.getAttribute("customerBean");
    String proximaReserva = request.getParameter("proximaReserva");
    java.sql.Date proximaReservasql= null;
    if(!proximaReserva.equals("null")){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date utilDate = format.parse(proximaReserva);
        proximaReservasql = new java.sql.Date(utilDate.getTime());
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página Principal - Usuario</title>
    <link rel="stylesheet" href="/pract3/css/stylesUser.css">
</head>
<body>
    <div class="container">
        <h1>Bienvenido, <jsp:getProperty property="nombre" name="customerBean"/></h1>
        <h2>Hoy es <%= dateFormat.format(new java.util.Date()) %></h2>
        <h2>Detalles de tu cuenta:</h2>
        <%
            if(cB.getFecha_inscripcion() != null) {
        %>
            <p>Fecha de inscripción: <jsp:getProperty property="fecha_inscripcion" name="customerBean"/></p>
            <%
                if (proximaReserva != null) {
            %>
                <p>Fecha de la próxima reserva: <%= proximaReserva %></p>
            <%
                } else {
            %>
                <p>No tienes reservas futuras.</p>
            <%
                } 
            } else {
            %>
            <p>Todavía no has hecho ninguna reserva.</p>
        <%
            }
        %>

        <div class="button-group">
            <form action="/pract3/mvc/control/common/desconectarController.jsp" method="post" style="display: inline;">
                <button type="submit">Desconectar</button>
            </form>
            <form action="/pract3/mvc/view/common/modificar_datosView.jsp" method="get" style="display: inline;">
                <button type="submit">Modificar datos</button>
            </form>
            <form action="/pract3/mvc/view/user/consultar_reservasView.jsp" method="get" style="display: inline;">
                <button type="submit">Consultar reservas</button>
            </form>
            <form action="/pract3/mvc/view/user/mostrar_pistasUserView.jsp" method="get" style="display: inline;">
                <button type="submit">Buscar pistas</button>
            </form>
            <form action="/pract3/mvc/view/user/realizar_reservaView.jsp" method="get" style="display: inline;">
                <button type="submit">Realizar reserva</button>
            </form>
            <form action="/pract3/mvc/view/user/adquirir_bonoView.jsp" method="get" style="display: inline;">
                <button type="submit">Adquirir bono</button>
            </form>
            <form action="/pract3/mvc/view/user/realizar_reserva_bonoView.jsp" method="get" style="display: inline;">
                <button type="submit">Realizar reserva en bono</button>
            </form>
            <form action="/pract3/user/CancelarReservaServlet" method="get" style="display: inline;">
                <button type="submit">Cancelar reserva</button>
            </form>
            <form action="/pract3/user/ModificarReservasServlet" method="get" style="display: inline;">
                <button type="submit">Modificar reserva</button>
            </form>
            
        </div>
    </div>
</body>
</html>
