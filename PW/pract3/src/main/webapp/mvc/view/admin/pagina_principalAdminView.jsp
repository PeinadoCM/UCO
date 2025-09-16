<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" errorPage="/pract3/mvc/view/error/error_general.jsp"%>
<%@ page import="java.util.*, java.text.*, java.io.*, java.sql.*, display.CustomerBean, data.dao.reservas.*, data.dao.jugadores.*, data.dto.jugadores.*" %>
<%@ include file="/include/iniciosesionAdmin.jsp" %>

<%
    String nombresstr = request.getParameter("nombresstr");
    String fechas_inscripcionstr = request.getParameter("fechas_inscripcionstr");
    String numero_reservasstr = request.getParameter("numero_reservasstr");

    String[] nombresArray = null;
    String[] fechasArray = null;
    String[] reservasArray = null;

    if (nombresstr != null && !nombresstr.isEmpty()) {
        nombresstr = nombresstr.substring(1, nombresstr.length() - 1);
        nombresArray = nombresstr.split(",");

        fechas_inscripcionstr = fechas_inscripcionstr.substring(1, fechas_inscripcionstr.length() - 1);
        fechasArray = fechas_inscripcionstr.split(",");

        numero_reservasstr = numero_reservasstr.substring(1, numero_reservasstr.length() - 1);
        reservasArray = numero_reservasstr.split(",");
    }
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Página Principal - Administrador</title>
    <link rel="stylesheet" href="/pract3/css/stylesAdmin.css">
</head>
<body>
    <div class="container">
        <h1>Bienvenido Administrador: <jsp:getProperty property="correo" name="customerBean"/> </h1>
        <h2>Listado de Clientes</h2>

        <table border="1">
            <thead>
                <tr>
                    <th>Nombre del Cliente</th>
                    <th>Fecha de Inscripción</th>
                    <th>Número de Reservas</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    if (nombresArray != null && fechasArray != null && reservasArray != null) {
                        for (int i = 0; i < nombresArray.length; i++) {
                %>
                    <tr>
                        <td><%= nombresArray[i] %></td>
                        <td><%= fechasArray[i] %></td>
                        <td><%= reservasArray[i] %></td>
                    </tr>
                <% 
                        }
                    }
                %>
            </tbody>
        </table>
        <div class="button-group">
            <form action="/pract3/mvc/control/common/desconectarController.jsp" method="post">
                <button type="submit">Desconectar</button>
            </form>
            <form action="/pract3/mvc/view/common/modificar_datosView.jsp" method="get">
                <button type="submit">Modificar datos</button>
            </form>
            <form action="/pract3/mvc/view/admin/dar_alta_pistaView.jsp" method="get">
                <button type="submit">Crear pista</button>
            </form>
            <form action="/pract3/mvc/view/admin/dar_alta_materialView.jsp" method="get">
                <button type="submit">Crear material</button>
            </form>
            <form action="/pract3/admin/ModificarEstadoMaterialesServlet" method="get">
                <button type="submit">Modificar estado material</button>
            </form>
            <form action="/pract3/admin/ModificarEstadoPistasServlet" method="get">
                <button type="submit">Modificar estado pista</button>
            </form>
            <form action="/pract3/admin/AsociarMaterialAPistaServlet" method="get">
                <button type="submit">Asociar material a pista</button>
            </form>
            <form action="/pract3/admin/EliminarReservaServlet" method="get">
                <button type="submit">Eliminar reserva</button>
            </form>
        </div>
    </div>
</body>
</html>
