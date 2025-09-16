<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ include file="/include/iniciosesionUser.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Límite de Jugadores Excedido</title>
    <link rel="stylesheet" href="/pract3/css/stylesUser.css">
</head>
<body>
    <div class="container">
        <h1>¡Vaya! Has superado el máximo de jugadores</h1>
        <p>No se permite más jugadores en esta pista.</p>
        <a href="/pract3">Volver a la página principal</a>
    </div>
</body>
</html>
