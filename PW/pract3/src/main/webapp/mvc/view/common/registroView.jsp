<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ include file="/include/no_iniciosesion.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro</title>
    <link rel="stylesheet" href="/pract3/css/styles.css">
    <script src="/pract3/js/mostrar_contrasena.js" defer></script>
    <script src="/pract3/js/mayor_edad.js" defer></script>
</head>
<body>
    <div class="bg"></div>
    <div class="form-container">
        <h2>Registro</h2>
        <form action="/pract3/mvc/control/common/registroController.jsp" method="POST">
            <label for="nombre">Nombre y Apellidos</label>
            <input type="text" id="nombre" name="nombre" placeholder="Introduce tu nombre y apellidos" required>

            <label for="correo">Correo Electrónico</label>
            <input type="email" id="correo" name="correo" placeholder="Introduce tu correo electrónico" required>

            <label for="contrasena">Contraseña</label>
            <div class="password-container">
                <input type="password" id="contrasena" name="contrasena" placeholder="Introduce tu contraseña" required>
                <button type="button" id="togglePassword" onclick="togglePasswordVisibility()">👁️</button>
            </div>

            <label for="fechaNacimiento">Fecha de Nacimiento</label>
            <input type="date" id="fechaNacimiento" name="fechaNacimiento" required>

            <button type="submit">Registrarse</button>
        </form>
        <a href="/pract3/index.jsp" class="back-link">Volver a la página principal</a>
    </div>
</body>
</html>
