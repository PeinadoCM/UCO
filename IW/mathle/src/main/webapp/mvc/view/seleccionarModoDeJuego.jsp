<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="data.dto.Usuario" %>
<!DOCTYPE html>
<html>
<head>
    <title>Seleccionar Modo de Juego</title>
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
    <script src="<%= request.getContextPath() %>/js/seleccionarModoJuego.js" defer></script>

</head>
<body>
    <div class="container">
    <h2>Selecciona un modo de juego</h2>

    <form action="/mathle/modoJuego" method="post">
        <div class="radio-option">
            <input type="radio" id="nino" name="modo" value="ninos" required onclick="mostrarDesplegable()">
            <label for="nino">Niño</label>
        </div>

        <div class="radio-option">
            <input type="radio" id="desafio" name="modo" value="normal" onclick="mostrarDesplegable()">
            <label for="desafio">Desafío</label>
        </div>

        <div class="radio-option">
            <input type="radio" id="anteriores" name="modo" value="anteriores" onclick="mostrarDesplegable()">
            <label for="anteriores">Desafíos anteriores</label>
        </div>


        <!-- Opciones adicionales para "anteriores" -->
        <div id="opcionesAnteriores" style="display:none;">
            <label>Selecciona el modo del desafío anterior:</label><br>
            <input type="radio" id="anterioresNino" name="modoAnterior" value="ninos">
            <label for="anterioresNino">Niño</label><br>
            <input type="radio" id="anterioresNormal" name="modoAnterior" value="normal">
            <label for="anterioresNormal">Normal</label><br><br>

            <label for="fechaSeleccionada">Selecciona el día para realizar la operación:</label><br>
            <select name="fechaSeleccionada" id="fechaSeleccionada"></select><br><br>
        </div>

        <input type="submit" value="Continuar">
    </form>

    <button onclick="history.back()">Atrás</button>
    <div>
</body>
</html>
