<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ page import="java.util.*, data.dto.pistas.*, java.text.*, java.io.*, java.sql.*, display.CustomerBean, data.dao.reservas.*, data.dao.jugadores.*, data.dto.reservas.*" %>
<%@ include file="/include/iniciosesionUser.jsp" %>
<%
    if(request.getAttribute("reservas") == null){
        response.sendRedirect("/pract3/mvc/control/user/pagina_principalUserController.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cancelar Reserva</title>
    <link rel="stylesheet" type="text/css" href="/pract3/css/stylesUser.css">
</head>
<body>
    <div class="container">
        <h1>Reservas Cancelables</h1>

        <!-- Formulario -->
        <form action="/pract3/user/CancelarReservaServlet" method="post">
            <table>
                <thead>
                    <tr>
                        <th>Seleccionar</th>
                        <th>Nombre</th>
                        <th>Fecha</th>
                        <th>Hora</th>
                        <th>Duración</th>
                    </tr>
                </thead>
                    <tbody>
                         <% 
                        ArrayList<ReservaFamiliarDTO> reservas = (ArrayList<ReservaFamiliarDTO>) request.getAttribute("reservas");
                        if (reservas != null) {
                            // Recorrer los materiales y mostrarlos en filas de la tabla
                            for (int i = 0; i < reservas.size(); i++) {
                        %>
                        <tr>
                            <td>
                                <!-- Opción de reserva -->
                            <input type="radio" name="opcion" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(reservas.get(i).getFecha_hora()) + "|" + reservas.get(i).getNombrePista() %>" required>                            
                            </td>
                            <td><%= reservas.get(i).getNombrePista() %></td>
                            <td>
                            <%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(reservas.get(i).getFecha_hora()) %>
                            <input type="hidden" name="fecha" value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(reservas.get(i).getFecha_hora()) %>">
                            </td>
                            <td>
                            <%= new java.text.SimpleDateFormat("HH:mm:ss").format(reservas.get(i).getFecha_hora()) %>
                            <input type="hidden" name="hora" value="<%= new java.text.SimpleDateFormat("HH:mm:ss").format(reservas.get(i).getFecha_hora()) %>">
                            </td>
                            <td><%= reservas.get(i).getDuracion() %></td>
                        </tr>
                        <% 
                            }
                        }
                        %>
                    </tbody>
            </table>
            <!-- Botón para enviar el formulario -->
            <button type="submit">Cancelar reserva</button>
        </form>
    </div>
</body>
</html>

