<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ include file="/include/iniciosesionUser.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Selector de Bono</title>
    <link rel="stylesheet" type="text/css" href="/pract3/css/stylesUser.css">
</head>
<body>
    <div class="container">
        <h2>Selecciona un Tipo de Bono</h2>
        <form action="/pract3/user/AdquirirBonoServlet" method="post">
            <label for="tipoBono">Tipo de Bono:</label>
            <select name="tipoBono" id="tipoBono" required>
                <option value="">-- Selecciona un tipo --</option>
                <option value="MINIBASKET">Minibasket</option>
                <option value="TRESVSTRES">3VS3</option>
                <option value="ADULTOS">Adultos</option>
            </select>
            <button type="submit">Enviar</button>
        </form>
    </div>
</body>
</html>
