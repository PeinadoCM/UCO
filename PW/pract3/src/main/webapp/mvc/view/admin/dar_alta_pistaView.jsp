<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ include file="/include/iniciosesionAdmin.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alta de Pista</title>
    <link rel="stylesheet" href="/pract3/css/stylesAdmin.css">
</head>
<body>
    <div class="container">
        <h1>Dar de alta una pista</h1>
        <form action="/pract3/admin/DarAltaPistaServlet" method="post">
            <!-- Nombre -->
            <label for="nombre">Nombre de la pista:</label>
            <input type="text" id="nombre" name="nombre" required>

            <!-- Estado -->
            <label for="estado">Estado de la pista:</label>
            <select id="estado" name="estado" required>
                <option value="true">Libre</option>
                <option value="false">Ocupada</option>
            </select>

            <!-- Tipo -->
            <label for="tipo">Tipo de pista:</label>
            <select id="tipo" name="tipo" required>
                <option value="true">Exterior</option>
                <option value="false">Interior</option>
            </select>

            <!-- Tamaño -->
            <label for="tamanio">Tamaño de la pista:</label>
            <select id="tamanio" name="tamanio" required>
                <option value="MINIBASKET">Minibasket</option>
                <option value="ADULTOS">Adultos</option>
                <option value="TRESVSTRES">3vs3</option>
            </select>

            <!-- Máximo jugadores -->
            <label for="maximoJugadores">Máximo de jugadores:</label>
            <input type="number" id="maximoJugadores" name="maximoJugadores" min="1" required>

            <!-- Botón de envío -->
            <button type="submit">Registrar Pista</button>
        </form>
    </div>
</body>
</html>
