<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ page import="display.CustomerBean"%>
<%@ include file="/include/iniciosesion.jsp" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modificar Datos del Usuario</title>
    <link rel="stylesheet" type="text/css" href="/pract3/css/stylesModificarDatos.css">
    <script src="/pract3/js/mostrar_contrasena.js" defer></script>
    <script src="/pract3/js/mayor_edad.js" defer></script>
</head>
<body>
<div class="bg"></div>
<div class="form-container">
    <h2>Modificar Datos del Usuario</h2>
    <form action="/pract3/mvc/control/common/modificar_datosController.jsp" method="post">

        <!-- Campo Correo Electrónico (No modificable) -->
        <div class="form-group">
            <label for="correo">Correo Electrónico:</label>
            <input type="email" id="correo" name="correo" value="<jsp:getProperty property="correo" name="customerBean"/>" disabled>
        </div>
<%
    CustomerBean cB = (CustomerBean) session.getAttribute("customerBean");
    if(cB.getAdmin()){
%>
        <!-- Campo Tipo de Usuario (No modificable) -->
        <div class="form-group">
            <label for="tipoUsuario">Tipo de Usuario:</label>
            <input type="text" id="tipoUsuario" name="tipoUsuario" value="Administrador" disabled>
        </div>
<%
    }
    else{
%>
        <!-- Campo Tipo de Usuario (No modificable) -->
        <div class="form-group">
            <label for="tipoUsuario">Tipo de Usuario:</label>
            <input type="text" id="tipoUsuario" name="tipoUsuario" value="Usuario" disabled>
        </div>
<%
    }
%>
        <!-- Campo Nombre -->
        <div class="form-group">
            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombre" value="<jsp:getProperty property="nombre" name="customerBean"/>" required>
        </div>

        <!-- Campo Fecha de nacimiento -->
        <div class="form-group">
            <label for="fechaNacimiento">Fecha de nacimiento:</label>
            <input type="date" id="fechaNacimiento" name="fechaNacimiento" value="<jsp:getProperty property="fecha_nacimiento" name="customerBean"/>" required>
        </div>

        <!-- Campo Contraseña -->
        <div class="form-group">
            <label for="contrasena">Contraseña</label>
            <input type="password" id="contrasena" name="contrasena" value="<jsp:getProperty property="contraseña" name="customerBean"/>" required>
        </div>

        <div class="form-group">
            <button type="button" id="togglePassword" onclick="togglePasswordVisibility()">👁️</button>
        </div>

        <!-- Botón para Guardar -->
        <div class="form-group">
            <button type="submit">Guardar Cambios</button>
        </div>
    </form>
</div>

</body>
</html>
