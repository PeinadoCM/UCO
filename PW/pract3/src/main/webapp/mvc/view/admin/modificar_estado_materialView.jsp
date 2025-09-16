<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ page import="java.util.*, data.dto.pistas.*" %>
<%@ include file="/include/iniciosesionAdmin.jsp" %>
<%
    if(request.getAttribute("materiales") == null){
        response.sendRedirect("/pract3/mvc/control/admin/pagina_principalAdminController.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gestión de Materiales</title>
    <link rel="stylesheet" href="/pract3/css/stylesAdmin.css">
</head>
<body>
    <div class="container">
        <h1>Lista de Materiales</h1>

        <!-- Formulario -->
        <form action="/pract3/admin/ModificarEstadoMaterialesServlet" method="post">
            <table>
                <thead>
                    <tr>
                        <th>Seleccionar</th>
                        <th>ID</th>
                        <th>Uso</th>
                        <th>Tipo</th>
                        <th>Estado Actual</th>
                        <th>Nuevo Estado</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        // Obtener la lista de materiales del atributo de solicitud
                        List<MaterialDTO> materiales = (List<MaterialDTO>) request.getAttribute("materiales");
                        if (materiales != null) {
                            // Recorrer los materiales y mostrarlos en filas de la tabla
                            for (MaterialDTO material : materiales) {
                    %>
                    <tr>
                        <td><input type="radio" name="id" value="<%= material.getId() %>" required></td>
                        <td><%= material.getId() %></td>
                        <td><%= material.getUso() ? "Exterior" : "Interior" %></td>
                        <td><%= material.getTipo() %></td>
                        <td><%= material.getEstado() %></td>
                        <td>
                            <select name="estado_<%= material.getId() %>">
                                <option value="DISPONIBLE">DISPONIBLE</option>
                                <option value="RESERVADO">RESERVADO</option>
                                <option value="MAL_ESTADO">MAL_ESTADO</option>
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
