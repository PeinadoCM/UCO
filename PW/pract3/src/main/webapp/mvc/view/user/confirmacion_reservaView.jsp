<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ include file="/include/iniciosesionUser.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirmación de Reserva</title>
    <link rel="stylesheet" href="/pract3/css/stylesUser.css">
</head>
<body>
    <div class="container">
        <h1>¡Reserva Confirmada!</h1>
        <p>Gracias por realizar tu reserva. Aquí tienes los detalles:</p>
        <div class="details">
            <p><strong>Tipo de Reserva:</strong> ${tipoReserva}</p>
            <p><strong>Fecha:</strong> ${fechaReserva}</p>
            <p><strong>Hora:</strong> ${horaReserva}</p>
            <p><strong>Pista:</strong> ${nombrePista}</p>
            <p><strong>Duración:</strong> ${duracion} minutos</p>
            <p><strong>Número de niños:</strong> ${numNinos}</p>
            <p><strong>Número de adultos:</strong> ${numAdultos}</p>
        </div>
        <a href="/pract3/user/home.jsp">Volver a la página principal</a>
    </div>
</body>
</html>
