<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, data.dto.Mensaje" %>
<%@ page import="data.dto.Usuario" %>
<%
    if(session.getAttribute("usuario") == null){
        response.sendRedirect("/mathle");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>

    <meta charset="UTF-8">
    <title>Mensajes Blog</title>

    <%
    String tema = "";
    Usuario usuario = (Usuario) session.getAttribute("usuario");

    if(usuario == null){
        tema = (String) session.getAttribute("color");
        if (tema == null) {
            tema = "claro"; // valor por defecto
        }
    }
    else{
        tema = usuario.getTema();
    }
  %>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/Tema<%= tema.substring(0,1).toUpperCase() + tema.substring(1) %>.css">
</head>
<body>
<div class="container">
<div>
    <h2>MENSAJES BLOG</h2>
</div>

<br>

<!-- Mostrar mensaje de error si existe -->
<%
    String error = (String) request.getAttribute("error");
    if (error != null) {
%>
    <p><%= error %></p>
<%
    }
%>

<!-- Botón para publicar nuevo mensaje -->
<div>
    <form action="/mathle/mvc/view/publicarMensaje.jsp" method="get">
        <input type="submit" value="Publicar mensaje">
    </form>
<div>

<!-- Botón para salir -->
<form action="<%= request.getContextPath() %>/index.jsp" method="get">
    <input type="submit" value="Salir">
    <br><br>
</form>

<!-- Listado de mensajes -->
<div>
<%
    List<Mensaje> mensajes = (List<Mensaje>) request.getAttribute("mensajes");
    if (mensajes != null && !mensajes.isEmpty()) {
        for (Mensaje m : mensajes) {
%>
    
    <div class="mensaje-card">
        <p>
            <strong>Autor:</strong> <%= m.getAutor() %><br>
            <strong>Mensaje:</strong> <%= m.getMensaje() %><br>
            <span><%= m.getFecha() %></span>
        </p>

    </div>
    
<%
        }
    } else {
%>
    <p>No hay mensajes todavía.</p>
<%
    }
%>
</div>

<br>

<div>
</body>
</html>
