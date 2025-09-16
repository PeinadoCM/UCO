<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="data.dto.Usuario" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ranking de Partida</title>
    <%
    String tema = "";
    Usuario usuarioActual = (Usuario) session.getAttribute("usuario");

    if(usuarioActual == null){
        tema = (String) session.getAttribute("color");
        if (tema == null) {
            tema = "claro"; // valor por defecto
        }
    }
    else{
        tema = usuarioActual.getTema();
    }
    %>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/Tema<%= tema.substring(0,1).toUpperCase() + tema.substring(1) %>.css">

</head>
<body>
<div class="container">

<h1>Ranking de Partida</h1>
<h2>Este es el Ranking actual de esta Partida</h2>

<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
    <p style="color:red;"><%= error %></p>
<%
    }
%>

<%
    List<Usuario> ranking = (List<Usuario>) request.getAttribute("ranking");
    if (ranking == null){
        response.sendRedirect("/mathle");
        return;
    }
    if (ranking.isEmpty()) {
%>
    <p>No hay datos de ranking para esta partida. Inicia Sesión para que aparezca la tuya.</p>
<%
    } else {
%>
    <table border="1" cellpadding="5" cellspacing="0">
        <thead>
            <tr>
                <th>Posición</th>
                <th>Nombre de Usuario</th>
                <th>Puntuación</th>
            </tr>
        </thead>
        <tbody>
        <%
            int posicion = 1;
            for (Usuario usuario : ranking) {
                boolean esUsuarioActual = usuarioActual != null && usuario.getNombre().equals(usuarioActual.getNombre());
        %>
            <tr <%= esUsuarioActual ? "class='ranking-usuario-actual'" : "" %>>
                <td><%= posicion++ %></td>
                <td><%= usuario.getNombre() %></td>
                <td><%= usuario.getPuntuacion() %></td>
            </tr>
        <%
            }
        %>
        </tbody>
    </table>
<%
    }
%>

<!-- Botón para salir -->
<form action="<%= request.getContextPath() %>/index.jsp" method="get">
    <input type="submit" value="Volver al Menú Principal">
</form>
<div>
</body>
</html>
