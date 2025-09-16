<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ include file="/include/iniciosesionAdmin.jsp" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Alta de Material</title>
    <link rel="stylesheet" href="/pract3/css/stylesAdmin.css">
</head>
<body>
    <div class="container">
        <h1>Registrar Material</h1>
        <form action="/pract3/admin/DarAltaMaterialServlet" method="post">
            <!-- Uso -->
            <label for="uso">Uso del material:</label>
            <select id="uso" name="uso" required>
                <option value="true">Exterior</option>
                <option value="false">Interior</option>
            </select>

            <!-- Tipo -->
            <label for="tipo">Tipo:</label>
            <select id="tipo" name="tipo" required>
                <option value="CANASTAS">Canastas</option>
                <option value="CONOS">Conos</option>
                <option value="PELOTAS">Pelotas</option>
            </select>

            <!-- Estado -->
            <label for="estado">Estado:</label>
            <select id="estado" name="estado" required>
                <option value="DISPONIBLE">Disponible</option>
                <option value="MAL_ESTADO">Mal estado</option>
                <option value="RESERVADO">Reservado</option>
            </select>

            <!-- Botón de envío -->
            <button type="submit">Registrar Material</button>
        </form>
    </div>
</body>
</html>

