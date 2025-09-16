<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ include file="/include/iniciosesionUser.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error al Realizar la Reserva</title>
    <link rel="stylesheet" href="/pract3/css/stylesUser.css">
</head>
<body>
    <div class="container">
        <h1>Error en la Reserva</h1>
        <p>Lo sentimos, no se pudo completar tu reserva debido a un problema.</p>
        <p><strong>Detalles del Error:</strong> ${errorMessage}</p>
        <a href="/pract3/user/realizar_reservaView.jsp">Volver al formulario</a>
    </div>
</body>
</html>
