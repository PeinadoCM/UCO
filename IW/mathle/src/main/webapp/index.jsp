<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="data.dto.Partida" %>
<%@ page import="data.dto.Usuario" %>
<%
    if(session.getAttribute("partidasJugadas") == null){
        ArrayList<Partida> partidasJugadas = new ArrayList<>();
        session.setAttribute("partidasJugadas", partidasJugadas);
    }
    
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
<html lang="es">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Menu Principal</title>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/Tema<%= tema.substring(0,1).toUpperCase() + tema.substring(1) %>.css">
    </head>
    <body>
        <div class="container">
        <h1>¡Bienvenido a MATHLE!</h1>
        <% if(tema.equals("claro") || tema.equals("verde")) { %>
            <img src="<%= request.getContextPath() %>/img/Mathle.png" alt="Logo Mathle" class="logo">
        <% } else { %>
            <img src="<%= request.getContextPath() %>/img/Mathle_Azul.png" alt="Logo Mathle" class="logo">
        <% } %>
        <h2>Menú Principal</h2>  

        <div class="button-container">

        <%
        if (session.getAttribute("usuario") == null) {
        %>
            <form action="/mathle/mvc/view/registro.jsp" method="get">
                <button type="submit" class="button">Registrarse</button>
            </form>
            <form action="/mathle/mvc/view/inicioSesion.jsp" method="get">
                <button type="submit" class="button">Iniciar Sesión</button>
            </form>
        <%
        }
        %>
            <form action="/mathle/mvc/view/seleccionarModoDeJuego.jsp" method="get">
                <button type="submit" class="button">Jugar Partida</button>
            </form>
            <form action="/mathle/mvc/view/tutorial.jsp" method="get">
                <button type="submit" class="button">Ver Tutorial</button>
            </form>
            <form action="/mathle/RankingGlobalServlet" method="get">
                <button type="submit" class="button">Ver Ranking</button>
            </form>
            <form action="/mathle/mvc/view/personalizarWeb.jsp" method="get">
                <button type="submit" class="button">Personalizar Web</button>
            </form>
        </div>
        <%
        if (session.getAttribute("usuario") != null) {
        %>
            <form action="/mathle/MensajeServlet" method="get">
                <button type="submit" class="button">Ver Blog</button>
            </form>

            <form action="/mathle/logout" method="get">
                <input type="submit" value="Cerrar sesión">
            </form>

        <%
        }
        %>
    <div>  
    </body>
</html>
