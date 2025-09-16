<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ page import="java.util.*, data.dto.pistas.*" %>
<%@ include file="/include/iniciosesionAdmin.jsp" %>
<%
    if(request.getAttribute("pistas") == null || request.getAttribute("materiales") == null){
        response.sendRedirect("/pract3/mvc/control/admin/pagina_principalAdminController.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Asociar Materiales a Pistas</title>
    <link rel="stylesheet" href="/pract3/css/stylesAdmin.css">
</head>
<body>
    <div class="container">
        <h1>Asociar Materiales a Pistas</h1>
        <form action="/pract3/admin/AsociarMaterialAPistaServlet" method="post">
            <!-- Selector de Pistas -->
            <label for="nombrePista">Selecciona una pista:</label>
            <select id="nombrePista" name="nombrePista" required>
                <option value="" disabled selected>-- Selecciona una pista --</option>
                <% 
                    ArrayList<PistaDTO> pistas = (ArrayList<PistaDTO>) request.getAttribute("pistas");
                    if (pistas != null) {
                        for (PistaDTO pista : pistas) {
                %>
                <option value="<%= pista.getNombre() %>"><%= pista.getNombre() %> (Tipo: <%= pista.getTipo() ? "Exterior" : "Interior" %>)</option>
                <% 
                        }
                    }
                %>
            </select>

            <!-- Selector de Materiales -->
            <label for="id">Selecciona un material:</label>
            <select id="id" name="id" required>
                <option value="" disabled selected>-- Selecciona un material --</option>
                <% 
                    ArrayList<MaterialDTO> materiales = (ArrayList<MaterialDTO>) request.getAttribute("materiales");
                    if (materiales != null) {
                        for (MaterialDTO material : materiales) {
                %>
                <option value="<%= material.getId() %>">
                    ID: <%= material.getId() %> - Tipo: <%= material.getTipo() %> - Uso: <%= material.getUso() ? "Exterior" : "Interior" %>
                </option>
                <% 
                        }
                    }
                %>
            </select>

            <!-- Botón para asociar -->
            <button type="submit">Asociar Material</button>
        </form>
    </div>
</body>
</html>
