<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="data.dto.Usuario" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Registro de Usuario</title>
  <%
    String tema = "";
    Usuario usuario = (Usuario) session.getAttribute("usuario");

    if(usuario != null){
      response.sendRedirect("/mathle");
      return;
    }

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
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/contrasenaRegisterLogin.css">
  <script src="/js/notificacionMensaje.js" defer></script>
  <script src="<%= request.getContextPath() %>/js/verContrasena.js" defer></script>
  
</head>

<body>
  <div class="container">
    <h2>Formulario de Registro</h2>

    <%
      String mensaje = (String) request.getAttribute("mensaje");
    %>
    <% if (mensaje != null) { %>
      <div id="mensaje" class="mensaje">
        <%= mensaje %>
      </div>
    <% } %>

    <form action="/mathle/registro" method="post">
      <label for="nombre">Nombre de usuario:</label>
      <input type="text" id="nombre" name="nombre" required>

      <label for="correo">Correo electrónico:</label>
      <input type="email" id="correo" name="correo" required>

      <label for="contrasena">Contraseña:</label>
      <div class="input-group">
        <input type="password" id="contrasena" name="contrasena" required>
        <button type="button" class="eye-button" onclick="togglePassword()" aria-label="Mostrar/Ocultar contraseña">
        <svg viewBox="0 0 24 24">
        <path id="eye-icon" d="M1 1l22 22M9.9 9.9a3 3 0 0 0 4.2 4.2m-1.05-6.55a5 5 0 0 1 5.9 5.9M12 5c-7.633 0-12 7-12 7a21.878 21.878 0 0 0 5.2 5.6m3.3-3.3a5 5 0 0 0 7.1-7.1M12 19c7.633 0 12-7 12-7a21.878 21.878 0 0 0-5.2-5.6" />
        </svg>
        </button>
      </div><br>

      <input type="submit" value="Registrarse">
    </form>

    <button onclick="history.back()">Atrás</button>
  </div>
</body>
</html>
