let filaActual = 0;
let intentos = 0;
let tiempoInicio = Date.now();
let tiempo;
let intentosArray = [];

function avanzarFocus(event, fila, col) {
    fila = parseInt(fila);
    col = parseInt(col);

    const key = event.key;
    const siguiente = document.getElementById("input-" + fila + "-" + (col + 1));
    const anterior = document.getElementById("input-" + fila + "-" + (col - 1));

    if (key.length === 1 && siguiente) {
        setTimeout(() => siguiente.focus(), 10);
    } else if (key === "Backspace" && anterior && event.target.value === "") {
        setTimeout(() => anterior.focus(), 10);
    }
}

function leerFila(fila) {
    let intento = "";
    for (let col = 0; col < dificultad; col++) {
        const input = document.getElementById(`input-${fila}-${col}`);
        intento += input.value;
    }
    return intento;
}

function esOperacionValida(op) {
    try {
        const [izq, der] = op.split("=");
        if (!der) return false;
        return eval(izq) == eval(der);
    } catch (e) {
        return false;
    }
}

function activarFila(fila) {
    for (let f = 0; f < 6; f++) {
        for (let col = 0; col < dificultad; col++) {
            const input = document.getElementById(`input-${f}-${col}`);
            if (!input) continue;
            // Solo habilita la fila activa
            if (f === fila) {
                // En modo niños, la primera mitad está bloqueada
                if (modoJuego === "ninos" && col < dificultad / 2) {
                    input.value = operacionCorrecta[col];
                    input.disabled = true;
                    input.classList.add("verde");
                } else {
                    input.disabled = false;
                }
            } else {
                input.disabled = true;
            }
        }
    }
    // Enfoca el primer input editable de la fila activa
    let startCol = (modoJuego === "ninos" && fila === 0) ? Math.floor(dificultad / 2) : 0;
    const firstEditable = document.getElementById(`input-${fila}-${startCol}`);
    if (firstEditable) firstEditable.focus();
}

function desactivarFila(fila) {
    for (let col = 0; col < dificultad; col++) {
        const input = document.getElementById(`input-${fila}-${col}`);
        if (input) {
            input.disabled = true;
        }
    }
}

function comprobarIntento() {
    const intento = leerFila(filaActual);

    if (intento.length !== dificultad) {
        mostrarMensaje("Completa toda la fila.");
        return;
    }

    if (!esOperacionValida(intento)) {
        mostrarMensaje("La operación no es válida.");
        return;
    }

    intentos++;

    const resultado = Array(dificultad).fill("gris");
    const usados = Array(dificultad).fill(false);

    for (let i = 0; i < dificultad; i++) {
        if (intento[i] === operacionCorrecta[i]) {
            resultado[i] = "verde";
            usados[i] = true;
        }
    }

    for (let i = 0; i < dificultad; i++) {
        if (resultado[i] !== "gris") continue;
        for (let j = 0; j < dificultad; j++) {
            if (!usados[j] && intento[i] === operacionCorrecta[j]) {
                resultado[i] = "amarillo";
                usados[j] = true;
                break;
            }
        }
    }

    intentosArray.push({
        intento: intento,
        colores: resultado
    });
    guardarIntentos();

    for (let col = 0; col < dificultad; col++) {
        const input = document.getElementById(`input-${filaActual}-${col}`);
        input.classList.add(resultado[col]);
        input.disabled = true;
    }

    if (intento === operacionCorrecta) {
        mostrarMensaje("¡Correcto! Has ganado.", "exito");
        setTimeout(() => {
            tiempo = calcularTiempo();
            enviarResultado(intentos, tiempo);
        }, 1500);
        return;
    }

    if (filaActual < 5) {
        filaActual++;
        activarFila(filaActual);
    } else {
        mostrarMensaje("Has perdido. La operación era: " + operacionCorrecta);
        setTimeout(() => {
            tiempo = calcularTiempo();
            enviarResultado(7, tiempo);
        }, 1500);
    }
}

function calcularTiempo() {
    return Math.floor((Date.now() - tiempoInicio) / 1000);
}

function enviarResultado(numIntentos, tiempo) {
    limpiarIntentos();
    document.getElementById("inputIntentos").value = numIntentos;
    document.getElementById("inputTiempo").value = tiempo;
    document.getElementById("formResultado").submit();
}

function mostrarMensaje(texto, tipo = "error") {
    const mensaje = document.getElementById("mensaje");
    mensaje.textContent = texto;
    mensaje.className = "mensaje " + (tipo === "exito" ? "exito" : "");
    mensaje.style.display = "block";

    // Reinicia animación (permite mostrar varias veces)
    mensaje.style.animation = "none";
    mensaje.offsetHeight; // forzar repaint
    mensaje.style.animation = "fadeOut 3s forwards";
}

// Guarda los intentos en sessionStorage
function guardarIntentos() {
    sessionStorage.setItem("intentosPartida_" + partidaId, JSON.stringify(intentosArray));
}

function limpiarIntentos() {
    sessionStorage.removeItem("intentosPartida_" + partidaId);
}

// Rellena una fila con un intento anterior
function restaurarIntento(fila, intentoObj) {
    const intento = intentoObj.intento;
    const colores = intentoObj.colores;
    for (let col = 0; col < dificultad; col++) {
        const input = document.getElementById(`input-${fila}-${col}`);
        if (input) {
            input.value = intento[col] || "";
            input.classList.add(colores[col]);
            input.disabled = true;
        }
    }
}

window.onload = function () {
    activarFila(filaActual);

    // Restaurar intentos desde sessionStorage SOLO de esta partida
    const guardados = sessionStorage.getItem("intentosPartida_" + partidaId);
    if (guardados) {
        intentosArray = JSON.parse(guardados);
        intentos = intentosArray.length;
        for (let i = 0; i < intentosArray.length; i++) {
            restaurarIntento(i, intentosArray[i]);
        }
        filaActual = intentosArray.length;
        if (filaActual < 6) activarFila(filaActual);
    }

    if (modoJuego === "ninos" && operacionVisible) {
        for (let i = 0; i < operacionVisible.length; i++) {
            const input = document.getElementById(`input-0-${i}`);
            if (input) {
                input.value = operacionVisible[i];
                input.disabled = true;
                input.classList.add("verde");
            }
        }
        const siguiente = document.getElementById(`input-0-${operacionVisible.length}`);
        if (siguiente) siguiente.focus();
    }
};