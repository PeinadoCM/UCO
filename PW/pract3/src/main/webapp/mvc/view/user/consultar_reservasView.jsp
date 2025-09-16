<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ page import="java.util.*, java.text.*, java.io.*, java.sql.*, display.CustomerBean, data.dto.reservas.*" %>
<%@ include file="/include/iniciosesionUser.jsp" %>
<% SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");%>
<!DOCTYPE html>
<html>
<head>
    <title>Buscar Reservas</title>
    <link rel="stylesheet" type="text/css" href="/pract3/css/stylesUser.css">
</head>
<body>
<div class="container">
    <h1>Buscar Reservas</h1>

    <form action="/pract3/user/ConsultarReservasServlet" method="get">
        <div>
            <label for="fechaInicio">Fecha de Inicio:</label>
            <input type="date" id="fechaInicio" name="fechaInicio" required>
        </div>

        <div>
            <label for="fechaFin">Fecha de Fin:</label>
            <input type="date" id="fechaFin" name="fechaFin" required>
        </div>

        <div>
            <button type="submit">Buscar</button>
        </div>
    </form>

    <!-- Aquí puedes incluir un lugar para mostrar los resultados de la búsqueda -->
    <div id="resultados">
        <!-- Los resultados se mostrarán aquí -->
        <% if (request.getAttribute("reservas_finalizadas") != null) { %>
        <h2>Reservas finalizadas</h2>
            <table border="1">
                <thead>
                    <tr>
                        <th>Correo</th>
                        <th>Nombre de pista</th>
                        <th>Fecha y hora</th>
                        <th>Duracion</th>
                        <th>Numero de niños</th>
                        <th>Numero de adultos</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        ArrayList<ReservaFamiliarDTO> reservas_finalizadas = (ArrayList<ReservaFamiliarDTO>) request.getAttribute("reservas_finalizadas");
                        for (ReservaFamiliarDTO reserva : reservas_finalizadas) {
                    %>
                    <tr>
                        <td><%= reserva.getId_user() %></td>
                        <td><%= reserva.getNombrePista() %></td>
                        <td><%= sdf.format(reserva.getFecha_hora()) %></td>
                        <td><%= reserva.getDuracion() %></td>
                        <td><%= reserva.getNinos() %></td>
                        <td><%= reserva.getAdultos() %></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        <% } %>
        <% if (request.getAttribute("reservas_futuras") != null) { %>
            <h2>Reservas futuras</h2>
            <table border="1">
                <thead>
                    <tr>
                        <th>Correo</th>
                        <th>Nombre de pista</th>
                        <th>Fecha y hora</th>
                        <th>Duracion</th>
                        <th>Numero de niños</th>
                        <th>Numero de adultos</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        ArrayList<ReservaFamiliarDTO> reservas_futuras = (ArrayList<ReservaFamiliarDTO>) request.getAttribute("reservas_futuras");
                        for (ReservaFamiliarDTO reserva : reservas_futuras) {
                    %>
                    <tr>
                        <td><%= reserva.getId_user() %></td>
                        <td><%= reserva.getNombrePista() %></td>
                        <td><%= sdf.format(reserva.getFecha_hora()) %></td>
                        <td><%= reserva.getDuracion() %></td>
                        <td><%= reserva.getNinos() %></td>
                        <td><%= reserva.getAdultos() %></td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        <% } %>
    </div>
    <div>
</body>
</html>
