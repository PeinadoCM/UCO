<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ page import="java.util.*, data.dto.pistas.*" %>
<%@ include file="/include/iniciosesionAdmin.jsp" %>
<%
    if(request.getAttribute("pistas") == null){
        response.sendRedirect("/pract3/mvc/control/admin/pagina_principalAdminController.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Estado de Pistas</title>
    <link rel="stylesheet" href="/pract3/css/stylesAdmin.css">
</head>
<body>
    <div class="container">
        <h1>Modificar Estado de Pistas</h1>

        <form action="/pract3/admin/ModificarEstadoPistasServlet" method="post">
            <table>
                <thead>
                    <tr>
                        <th>Seleccionar</th>
                        <th>Nombre</th>
                        <th>Estado Actual</th>
                        <th>Tipo</th>
                        <th>Tamaño</th>
                        <th>Máximo Jugadores</th>
                        <th>Nuevo Estado</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        // Obtener la lista de pistas del atributo de solicitud
                        List<PistaDTO> pistas = (List<PistaDTO>) request.getAttribute("pistas");
                        if (pistas != null) {
                            // Recorrer las pistas y mostrarlas en filas de la tabla
                            for (PistaDTO pista : pistas) {
                    %>
                    <tr>
                        <td><input type="radio" name="nombrePista" value="<%= pista.getNombre() %>" required></td>
                        <td><%= pista.getNombre() %></td>
                        <td><%= pista.getEstado() ? "Disponible" : "No Disponible" %></td>
                        <td><%= pista.getTipo() ? "Exterior" : "Interior" %></td>
                        <td><%= pista.getTamaño() %></td>
                        <td><%= pista.getMaxJugadores() %></td>
                        <td>
                            <select name="estado_<%= pista.getNombre() %>">
                                <option value="true">Disponible</option>
                                <option value="false">No Disponible</option>
                            </select>
                        </td>
                    
                    </tr>
                    <% 
                            }
                        }
                    %>
                </tbody>
            </table>
            <!-- Botón para enviar el formulario -->
            <button type="submit">Actualizar Estado</button>
        </form>
    </div>
</body>
</html>
