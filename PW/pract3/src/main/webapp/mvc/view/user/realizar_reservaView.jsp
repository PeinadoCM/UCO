<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ include file="/include/iniciosesionUser.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulario de Reserva</title>
    <link rel="stylesheet" type="text/css" href="/pract3/css/stylesUser.css">
    <script src="/pract3/js/mostrar_campos_reservas.js"></script>
    <script src="/pract3/js/fecha_minima.js"></script>
</head>
<body>
    <div class="container">
        <h2>Formulario de Reserva</h2>
        <form action="/pract3/user/RealizarReservaServlet" method="get">
            <!-- Tipo de Reserva -->
                <label for="tipoReserva">Tipo de Reserva:</label>
                <select name="tipoReserva" id="tipoReserva" required onchange="mostrarCamposAdicionales()">
                    <option value="">-- Selecciona un tipo --</option>
                    <option value="infantil">Infantil</option>
                    <option value="familiar">Familiar</option>
                    <option value="adultos">Adultos</option>
                </select>

            <!-- Fecha y Hora -->
                <label for="fechaHora">Fecha y Hora de la Reserva:</label>
                <input type="datetime-local" name="fechaHora" id="fechaHora" required>

            <!-- Duración -->
                <label for="duracion">Duración:</label>
                <select name="duracion" id="duracion" required>
                    <option value="">-- Selecciona la duración --</option>
                    <option value="60">60 minutos</option>
                    <option value="90">90 minutos</option>
                    <option value="120">120 minutos</option>
                </select>

            <!-- Número de Niños -->
            <div class="hidden" id="campoNinos">
                <label for="numNinos">Número de Niños:</label>
                <input type="number" name="numNinos" id="numNinos" min="0" max="50" value="0" required>
            </div>

            <!-- Número de Adultos -->
            <div class="hidden" id="campoAdultos">
                <label for="numAdultos">Número de Adultos:</label>
                <input type="number" name="numAdultos" id="numAdultos" min="0" max="50" value="0" required>
            </div>

            <!-- Botón Enviar -->
                <button type="submit">Reservar</button>
        </form>
    </div>
</body>
</html>
