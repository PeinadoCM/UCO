<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ include file="/include/no_iniciosesion.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Registro Correcto</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/pract3/css/styles.css">
</head>
<body>
    <div class="bg"></div>
    <div class="message-container">
        <h1>Registro realizado con éxito</h1>
        <p class="success-message">¡Tu cuenta ha sido creada correctamente! Ahora puedes iniciar sesión.</p>
        <form action="/pract3/mvc/view/common/iniciar_sesionView.jsp" method="get">
            <button type="submit">Iniciar Sesión</button>
        </form>
    </div>
</body>
</html>
