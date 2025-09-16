<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ include file="/include/iniciosesionAdmin.jsp" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error - Límite Alcanzado</title>
    <link rel="stylesheet" href="/pract3/css/stylesAdmin.css">
</head>
<body>
    <div class="container">
        <%-- Mostrar mensaje de error --%>
        <p>Error: Este material es de interior y no se puede asignar a una pista de exterior</p>
        <%-- Enlace para volver a la página principal --%>
        <a href="/pract3/mvc/control/admin/pagina_principalAdminController.jsp" class="back-link">Volver a la página principal</a>
    </div>
</body>
</html>