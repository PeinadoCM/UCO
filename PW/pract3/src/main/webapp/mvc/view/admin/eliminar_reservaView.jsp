<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ page import="java.util.*, java.text.*, java.io.*, java.sql.*, display.CustomerBean, data.dao.reservas.*, data.dao.jugadores.*, data.dto.reservas.*" %>
<%@ include file="/include/iniciosesionAdmin.jsp" %>
<%
    if(request.getAttribute("reservas") == null){
        response.sendRedirect("/pract3/mvc/control/admin/pagina_principalAdminController.jsp");
        return;
    }

    ArrayList<ReservaFamiliarDTO> reservas = (ArrayList<ReservaFamiliarDTO>) request.getAttribute("reservas");
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Formulario de Reserva</title>
    <link rel="stylesheet" href="/pract3/css/stylesAdmin.css">
</head>
<body>
    <div class="container">
        <h2>Formulario de Reserva</h2>

        <form action="/pract3/admin/EliminarReservaServlet" method="POST">

            <!-- Checklist para un conjunto de datos (en formato de tabla) -->
            <fieldset>
                <legend>Seleccione una opción:</legend>
                <table border="1">
                    <thead>
                        <tr>
                            <th>Seleccionar</th>
                            <th>Correo</th>
                            <th>Fecha y Hora</th>
                            <th>Nombre Pista</th>
                            <th>Duración (minutos)</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (int i = 0; i < reservas.size(); i++) { %>
                        <tr>
                            <td>
                                <!-- Opción de reserva -->
                                <input type="radio" name="opcion" value="<%= reservas.get(i) %>" required>
                            </td>
                            <td><%= reservas.get(i).getId_user() %></td>
                            <td><%= format.format(reservas.get(i).getFecha_hora()) %></td>
                            <td><%= reservas.get(i).getNombrePista() %></td>
                            <td><%= reservas.get(i).getDuracion() %></td>
                        </tr>
                        <% } %>
                    </tbody>
                </table>
            </fieldset>


            <!-- Botón para enviar el formulario -->
            <button type="submit">Eliminar</button>
        </form>
    </div>
</body>
</html>
