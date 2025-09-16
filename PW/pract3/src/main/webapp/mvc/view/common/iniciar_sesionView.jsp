<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ include file="/include/no_iniciosesion.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Inicio de Sesión</title>
    <link rel="stylesheet" href="/pract3/css/styles.css">
    <script src="/pract3/js/mostrar_contrasena.js" defer></script>
</head>
<body>
    <div class="bg"></div>
    <div class="form-container">
    <h2>Inicio de Sesión</h2>
    <form action="/pract3/mvc/control/common/iniciar_sesionController.jsp" method="POST">
        <label for="correo">Correo Electrónico</label>
        <input type="text" id="correo" name="correo" placeholder="Ingresa tu correo electrónico" required>
        
        <label for="contrasena">Contraseña</label>
        <div class="password-container">
            <input type="password" id="contrasena" name="contrasena" placeholder="Ingresa tu contraseña" required>
            <button type="button" id="togglePassword" onclick="togglePasswordVisibility()">👁️</button>
        </div>

        <button type="submit">Iniciar Sesión</button>
    </form>
    <div class="links">
        <p>¿No tienes cuenta? <a href="/pract3/mvc/view/common/registroView.jsp">Regístrate aquí</a></p>
    </div>
</div>

