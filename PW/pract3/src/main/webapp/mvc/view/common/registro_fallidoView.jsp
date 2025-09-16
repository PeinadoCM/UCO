<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ include file="/include/no_iniciosesion.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <title>Registro Fallido</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/pract3/css/styles.css">
</head>
<body>
    <div class="bg"></div>
    <div class="message-container">
        <h2>Registro Fallido</h2>
        <div class="error-message">
            <p>Ups, ha ocurrido un error en el registro. Inténtalo de nuevo.</p>
        </div>
        <a href="/pract3/index.jsp" class="back-link">Volver a la página principal</a>
    </div>
</body>
</html>
