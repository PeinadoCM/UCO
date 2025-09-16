<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="data.dto.Usuario" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tutorial - ¿Cómo jugar a Mathle?</title>
    <%
        String tema = "";
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            tema = (String) session.getAttribute("color");
            if (tema == null) {
                tema = "claro"; // valor por defecto
            }
        } else {
            tema = usuario.getTema();
        }
    %>

    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/Tema<%= tema.substring(0,1).toUpperCase() + tema.substring(1) %>.css">
</head>
<body>

<div class="container tutorial">
    <h1>¿Cómo jugar a Mathle?</h1>

    <p>Mathle es un juego de lógica matemática en el que debes resolver una ecuación correcta utilizando dígitos y operadores.</p>
    
    <h2>Modos de juego:</h2>
    <ul>
        <li><b>Modo Niño:</b> Debes completar una ecuación parcialmente resuelta. Ejemplo: 
        <span class="ejemplo-operacion">
            <span class="cuadro verde">1</span>
            <span class="cuadro verde">2</span>
            <span class="cuadro verde">+</span>
            <span class="cuadro gris">_</span>
            <span class="cuadro gris">_</span>
            <span class="cuadro gris">_</span>
        </span></li>
        <li><b>Modo Normal:</b> Resuelve el desafío diario para el número de dígitos seleccionado (6, 8, 10 o 12).</li>
        <li><b>Desafíos Anteriores:</b> Puedes jugar los desafíos de los últimos 7 días.</li>
    </ul>

    <h2>Validación de Operaciones:</h2>
    <p>Tu ecuación debe ser válida y estar en el formato <b>operación = resultado</b>. Mathle te indicará:</p>

    <div class="color-example">
        <div class="color-box green">5</div>
        <p>Correcto y en posición correcta.</p>
    </div>
    <div class="color-example">
        <div class="color-box yellow">+</div>
        <p>Correcto pero en posición incorrecta.</p>
    </div>
    <div class="color-example">
        <div class="color-box gray">7</div>
        <p>Incorrecto.</p>
    </div>

    <h2>Sistema de Puntuación:</h2>
    <p>Si estás registrado, tu puntuación se calcula al completar la ecuación:</p>
    <ul>
        <li><b>Número de intentos:</b> Mayor puntuación con menos intentos.</li>
        <li><b>Tiempo:</b> Menor tiempo, mayor puntuación.</li>
        <li><b>Dificultad:</b> Más puntos en ecuaciones de mayor longitud.</li>
    </ul>

    <div class="example mensaje-card">
        <h3>Ejemplo de cálculo:</h3>
        <p>Si resuelves en 3 intentos y 20 segundos en modo 8 dígitos:</p>
        <p><b>Puntos = (4 puntos por intentos) + (3 puntos por tiempo) × 1.2 = 8.4 puntos</b></p>
    </div>

    <h2>Persistencia de la Partida:</h2>
    <p>Si estás registrado, tu partida se mantiene incluso si refrescas la página.</p>

    <h2>¡Empieza a jugar ahora!</h2>
    <a href="/mathle/mvc/view/seleccionarModoDeJuego.jsp" class="start-button">¡Jugar ahora!</a>

    <form>
        <input type="button" value="Volver" onclick="history.back()" class="volver-button" />
    </form>
</div>

</body>
</html>