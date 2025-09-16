<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="data.dto.Usuario" %>
<!DOCTYPE html>
<html>
<head>
    <title>Seleccionar Dificultad</title>
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
    <h2>Selecciona una dificultad</h2>

    <form action="/mathle/seleccionarDificultad" method="post">
        <div class="radio-option">
            <input type="radio" id="d6" name="dificultad" value="6" required>
            <label for="d6">6 dígitos/operaciones</label><br>
        </div>
        <div class="radio-option">
            <input type="radio" id="d8" name="dificultad" value="8">
            <label for="d8">8 dígitos/operaciones</label><br>
        </div>
        <div class="radio-option">
            <input type="radio" id="d10" name="dificultad" value="10">
            <label for="d10">10 dígitos/operaciones</label><br>
        </div>
        <div class="radio-option">
            <input type="radio" id="d12" name="dificultad" value="12">
            <label for="d12">12 dígitos/operaciones</label><br><br>
        </div>

        <input type="submit" value="Continuar">
    </form>
    <!-- Botón para volver -->
    <button onclick="history.back()">Atrás</button>
<div>
</body>
</html>