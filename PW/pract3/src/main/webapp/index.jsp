<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ include file="/include/no_iniciosesion.jsp" %>
<html lang="es">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro e Inicio de Sesión</title>
    <link rel="stylesheet" href="/pract3/css/styles.css">
   
</head>
<body>
<div class="bg"></div>
<div class="container">
    <h1>Bienvenido</h1>
    <div class="button-group">
        <div class="column">
            <p>Si no tiene cuenta registrese</p>
            <form action="/pract3/mvc/view/common/registroView.jsp" method="get">
                <button type="submit" class="button">Registrarse</button>
            </form>
        </div>
        <div class="divider"></div>
        <div class="column">
            <p>Si ya tiene cuenta inicie sesión</p>
            <form action="/pract3/mvc/view/common/iniciar_sesionView.jsp" method="get">
                <button type="submit" class="button">Iniciar Sesión</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>