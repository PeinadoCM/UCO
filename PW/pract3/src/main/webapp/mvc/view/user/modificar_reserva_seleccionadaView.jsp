<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ page import="java.text.SimpleDateFormat, data.dto.reservas.ReservaFamiliarDTO, java.util.Locale" %>
<%@ include file="/include/iniciosesionUser.jsp" %>
<%
    // Obtener la reserva del request
    String reservastr = request.getParameter("opcion");

    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

    String partes[] = reservastr.split(", ");

    String fechaHora = partes[1].split("fecha y hora ")[1];
    String duracion = partes[2].split("duracion ")[1];
    String nombrePista = partes[3].split("nombre de pista ")[1];
    String numeroNinos = partes[6].split("numero de niños ")[1];
    String numeroAdultos = partes[7].split("numero de adultos ")[1];

    java.util.Date fecha = sdf.parse(fechaHora);
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modificar Reserva</title>
    <link rel="stylesheet" href="/pract3/css/stylesUser.css">
    <script src="/pract3/js/fecha_minima.js"></script>
</head>
<body>
    <div class="container">
        <h2>Modificar Reserva</h2>

        <form action="/pract3/user/ModificarReservasServlet" method="POST">
            <!-- Fecha y hora -->
            <label for="fechaHora">Fecha y Hora:</label>
            <input type="datetime-local" id="fechaHora" name="fechaHora" 
                value="<%= sdf2.format(fecha) %>" required>

            <!-- Duración -->
            <label for="duracion">Duración:</label>
            <select name="duracion" id="duracion" required>
                <option value="">-- Selecciona la duración --</option>
                <option value="60" <%= "60".equals(duracion) ? "selected" : "" %>>60 minutos</option>
                <option value="90" <%= "90".equals(duracion) ? "selected" : "" %>>90 minutos</option>
                <option value="120" <%= "120".equals(duracion) ? "selected" : "" %>>120 minutos</option>
            </select>
<%
            if(numeroNinos.equals("0")){
%>
            <input type="hidden" id="num_Niños" name="num_Niños" 
                value="<%= numeroNinos %>" required>
<%
            }
            else{
%>
            <label for="num_Niños">Numero de niños:</label>
            <input type="number" id="num_Niños" name="num_Niños" 
                value="<%= numeroNinos %>" required>
<%
            }
            if(numeroAdultos.equals("0")){
%>
            <input type="hidden" id="num_Adultos" name="num_Adultos" 
                value="<%= numeroAdultos %>" required>
<%
            }
            else{
%>
            <label for="num_Adultos">Numero de adultos:</label>
            <input type="number" id="num_Adultos" name="num_Adultos" 
                value="<%= numeroAdultos %>" required>
<%
            }
%>
            <!-- Nombre de la pista -->
            <input type="hidden" id="nombre_pista" name="nombre_pista" value="<%= nombrePista %>">

            <input type="hidden" id="fecha" name="fecha" value="<%= sdf2.format(fecha) %>" required>

            <!-- Botón para enviar -->
            <button type="submit">Guardar Cambios</button>
        </form>
    </div>
</body>
</html>
