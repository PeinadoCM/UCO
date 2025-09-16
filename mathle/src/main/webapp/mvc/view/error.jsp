<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="data.dto.Usuario" %>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <title>Error</title>
        <%
            String tema = "";
            Usuario usuario = (Usuario) session.getAttribute("usuario");

            if(usuario == null){
                tema = (String) session.getAttribute("color");
            if (tema == null) {
                tema = "claro"; // valor por defecto
            }
            }
            else{
                tema = usuario.getTema();
            }
        %>

        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/Tema<%= tema.substring(0,1).toUpperCase() + tema.substring(1) %>.css">

    </head>

    <body>
        <div class="container">
            <h2>¡Vaya parece que ha ocurrido un error!</h2>

            <form action="/mathle" method="get">
                <button type="submit" class="button">Volver al menu principal</button>
            </form>
        </div>
    </body>
</html>