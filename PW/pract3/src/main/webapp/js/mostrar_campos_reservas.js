function mostrarCamposAdicionales() {
    const tipoReserva = document.getElementById("tipoReserva").value;
    const campoNinos = document.getElementById("campoNinos");
    const campoAdultos = document.getElementById("campoAdultos");

    // Resetea los campos
    campoNinos.classList.add("hidden");
    campoAdultos.classList.add("hidden");

    // Muestra los campos según el tipo de reserva seleccionado
    if (tipoReserva === "infantil") {
        campoNinos.classList.remove("hidden");
    } else if (tipoReserva === "familiar") {
        campoNinos.classList.remove("hidden");
        campoAdultos.classList.remove("hidden");
    } else if (tipoReserva === "adultos") {
        campoAdultos.classList.remove("hidden");
    }
}