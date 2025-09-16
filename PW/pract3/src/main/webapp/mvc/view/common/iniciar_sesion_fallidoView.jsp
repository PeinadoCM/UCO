<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ include file="/include/no_iniciosesion.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Inicio de Sesión Fallido</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/pract3/css/styles.css">
</head>
<body>
    <div class="bg"></div>
    <div class="message-container">
        <h2>Inicio de Sesión Fallido</h2>
        <div class="error-message">
            <p>Ocurrió un problema al registrar tus datos.</p>
        </div>
        <p><a href="/pract3/mvc/view/common/iniciar_sesionView.jsp">Volver al inicio de sesión</a></p>
    </div>
</body>
</html>
