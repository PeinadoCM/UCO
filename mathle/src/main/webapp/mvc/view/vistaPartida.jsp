<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="data.dto.Usuario" %>
<%
    Integer dificultad = (Integer) session.getAttribute("dificultad");
    String operacion = (String) session.getAttribute("operacion");
    String modoJuego = (String) session.getAttribute("modoJuego");
    String operacionVisible = "";

    if (dificultad == null || operacion == null || modoJuego == null) {
        response.sendRedirect("/mathle");
        return;
    }

    if ("ninos".equals(modoJuego)) {
        int mitad = dificultad / 2;
        operacionVisible = operacion.substring(0, mitad);
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
<!DOCTYPE html>
<html>
<head>
    <title>Jugar Partida</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/estilosJuego.css" />
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/Tema<%= tema.substring(0,1).toUpperCase() + tema.substring(1) %>.css">
    <script>
        const operacionCorrecta = "<%= operacion %>";
        const dificultad = <%= dificultad %>;
        const modoJuego = "<%= modoJuego %>";
        const operacionVisible = "<%= operacionVisible %>";
        const partidaId = operacionCorrecta + "_" + modoJuego;
    </script>
    <script src="<%= request.getContextPath() %>/js/jugarPartida.js" defer></script>
</head>
<body>
<div id="mensaje" style="display:none;" class="mensaje"></div>
<div class="container">
    <h2>Introduce la operación matemática</h2>

    <form onsubmit="event.preventDefault(); comprobarIntento();">
        <% for (int fila = 0; fila < 6; fila++) { %>
            <div class="fila">
                <% for (int col = 0; col < dificultad; col++) {
                    boolean mostrarFijo = "ninos".equals(modoJuego) && col < (dificultad / 2);
                    char caracter = mostrarFijo ? operacion.charAt(col) : ' ';
                %>
                    <input type="text"
                        id="input-<%= fila %>-<%= col %>"
                        maxlength="1"
                        class="casilla"
                        data-fila="<%= fila %>"
                        data-col="<%= col %>"
                        autocomplete="off"
                        value=""
                        onkeyup="avanzarFocus(event, this.dataset.fila, this.dataset.col)">
                <% } %>
            </div>
        <% } %>
        <div style="text-align: center; margin-top: 10px;">
            <input type="submit" value="Comprobar">
        </div>
    </form>

    <div style="text-align: center; margin-top: 20px;">
        <button onclick="history.back()">Atrás</button>
    </div>

    <form id="formResultado" method="post" action="/mathle/partida" style="display:none;">
        <input type="hidden" name="intentos" id="inputIntentos">
        <input type="hidden" name="tiempo" id="inputTiempo">
    </form>
</div>
</body>
</html>
