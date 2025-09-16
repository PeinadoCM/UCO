<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="data.dto.Usuario" %>
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
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <title>Personalizar Página Web</title>

  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/Tema<%= tema.substring(0,1).toUpperCase() + tema.substring(1) %>.css">
</head>
<body>
  <div class="container">
  <h1>Personalizar Página Web</h1>

  <form method="post" action="<%= request.getContextPath() %>/PersonalizarServlet">
    <label for="color">Tema de color:</label><br>
    <select id="color" name="color">
      <option value="claro" <%= "claro".equals(tema) ? "selected" : "" %>>Verde Claro</option>
      <option value="oscuro" <%= "oscuro".equals(tema) ? "selected" : "" %>>Azul Oscuro</option>
      <option value="azul" <%= "azul".equals(tema) ? "selected" : "" %>>Azul Claro</option>
      <option value="verde" <%= "verde".equals(tema) ? "selected" : "" %>>Verde Oscuro</option>
    </select><br><br>

    <input type="submit" value="Confirmar personalización">
    <button type="button" onclick="history.back()">Atrás</button>

    <p><em>Nota:</em> Al confirmar, se aplicará el fondo y se volverá al inicio.</p>
  </form>
  </div>
</body>
</html>
