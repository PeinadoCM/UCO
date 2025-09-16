<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ page import="java.util.*, java.text.*, java.io.*, java.sql.*, display.CustomerBean, data.dto.pistas.*" %>
<%@ include file="/include/iniciosesionUser.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>Seleccionar Criterio</title>
    <link rel="stylesheet" type="text/css" href="/pract3/css/stylesUser.css">
</head>
<body>
    <div class="container">
    <h1>Seleccionar Criterio de Búsqueda</h1>
    <p>Selecciona entre una de estas dos opciones:</p>

    <form action="/pract3/user/BuscarPistaTipoServlet" method="GET">
        <!-- Botones para seleccionar el criterio -->
        <select name="tipo" required>
            <option value="true">Exterior</option>
            <option value="false">Interior</option>
        </select>
        <button type="submit" name="criterio" value="tipo_pista">Buscar por Tipo de Pista</button>
        <br><br>
    </form>

    <form action="/pract3/user/BuscarPistaFechaServlet" method="GET">
        <label for="fechaInicio">Fecha de Inicio:</label>
        <input type="date" id="fechaInicio" name="fechaInicio" required>
        <button type="submit" name="criterio" value="fecha">Buscar por Fecha</button>
    </form>
    <div>
    <!-- Los resultados se mostrarán aquí -->
        <% if (request.getAttribute("pistas") != null) { %>
        <h2>Reservas finalizadas</h2>
            <table border="1">
                <thead>
                    <tr>
                        <th>Nombre de pista</th>
                        <th>Tipo</th>
                        <th>Tamaño</th>
                        <th>Maximo jugadores</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        ArrayList<PistaDTO> pistas= (ArrayList<PistaDTO>) request.getAttribute("pistas");
                        for ( PistaDTO pista : pistas) {
                    %>
                    <tr>
                        <td><%= pista.getNombre() %></td>
                        <td><%= pista.getTipo() ? "Exterior" : "Interior" %></td>
                        <td><%= pista.getTamaño() %></td>
                        <td><%= pista.getMaxJugadores() %></td>
                        
                    </tr>
                    <% } %>
                </tbody>
            </table>
        <% } %>
    </div>
    </div>
</body>
</html>