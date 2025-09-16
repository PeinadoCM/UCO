<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ page import="java.util.ArrayList" %>
<%@ include file="/include/iniciosesionUser.jsp" %>
<%
    if(request.getAttribute("pistas") == null || request.getAttribute("n_ninos") == null || request.getAttribute("n_adultos") == null || request.getAttribute("duracion") == null || request.getAttribute("fechaHora") == null){
        response.sendRedirect("/pract3/mvc/control/user/pagina_principalUserController.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Seleccion Pista</title>
    <link rel="stylesheet" href="/pract3/css/stylesUser.css">
</head>
<body>
    <div class="container">
        <h2>Selecciona la pista que desees</h2>

        <%
            ArrayList<String> pistas = (ArrayList<String>) request.getAttribute("pistas");
            int nNinos = (Integer) request.getAttribute("n_ninos");
            int nAdultos = (Integer) request.getAttribute("n_adultos");
            int duracion = (Integer) request.getAttribute("duracion");
            java.util.Date fechaHora = (java.util.Date) request.getAttribute("fechaHora");
        %>

        <form action="/pract3/user/RealizarReservaServlet" method="post">
            <label for="opciones">Opciones:</label>
            <select name="seleccion" id="opciones">
                <%
                    for (String pista : pistas) {
                %>
                    <option value="<%= pista %>"><%= pista %></option>
                <%
                    }
                %>
            </select>
            <br><br>

            <!-- Campos ocultos para pasar parámetros adicionales -->
            <input type="hidden" name="numNinos" value="<%= nNinos %>">
            <input type="hidden" name="numAdultos" value="<%= nAdultos %>">
            <input type="hidden" name="duracion" value="<%= duracion %>">
            <input type="hidden" name="fechaHora" value="<%= fechaHora %>">

            <button type="submit">Enviar</button>
        </form>
    </div>
</body>
</html>