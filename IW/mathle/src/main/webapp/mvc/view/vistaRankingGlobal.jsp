<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, data.dto.Usuario" %>

<%
    List<Usuario> ranking = (List<Usuario>) request.getAttribute("ranking");
    if (ranking == null){
        response.sendRedirect("/mathle");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ranking Global de Usuarios</title>
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

<h2 style="text-align: center;">Ranking Global de Usuarios</h2>

<%
    if (ranking.isEmpty()) {
%>
    <p style="text-align: center;">No hay datos de usuarios disponibles para mostrar el ranking.</p>
<%
    } else {
%>
    <div style="display: flex; justify-content: center; margin-top: 20px;">
  <table border="1" cellpadding="5" cellspacing="0">
    <thead>
      <tr>
        <th>Posición</th>
        <th>Nombre</th>
        <th>Puntuación</th>
      </tr>
    </thead>
    <tbody>
    <%
        int posicion = 1;
        for (Usuario usuario : ranking) {
            boolean esusuario = usuarioActual != null && usuario.getNombre().equals(usuarioActual.getNombre());
    %>
        <tr <%= esusuario ? "class='ranking-usuario-actual'" : "" %>>
            <td><%= posicion++ %></td>
            <td><%= usuario.getNombre() %></td>
            <td><%= usuario.getPuntuacion() %></td>
        </tr>
    <%
        }
    %>

    </tbody>
  </table>
</div>
<%
    }
%>

<br>
<div style="text-align: center;">
    <button onclick="history.back()">Atrás</button>
</div>
<div>
</body>
</html>
