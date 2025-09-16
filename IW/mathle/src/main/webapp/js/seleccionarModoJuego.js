function mostrarDesplegable() {
    const modo = document.querySelector('input[name="modo"]:checked')?.value;
    const contenedor = document.getElementById("opcionesAnteriores");
    const radios = document.getElementsByName("modoAnterior");

    if (modo === "anteriores") {
        contenedor.style.display = "block";
        Array.from(radios).forEach(r => r.required = true);
    } else {
        contenedor.style.display = "none";
        Array.from(radios).forEach(r => r.required = false);
    }
}

function generarFechas() {
    const select = document.getElementById("fechaSeleccionada");
    const hoy = new Date();
    for (let i = 1; i <= 7; i++) {
        const fecha = new Date(hoy);
        fecha.setDate(hoy.getDate() - i);
        const opcion = document.createElement("option");
        opcion.value = fecha.toISOString().split('T')[0]; // formato YYYY-MM-DD
        opcion.text = fecha.toLocaleDateString(); // formato local
        select.appendChild(opcion);
    }
}

window.onload = function () {
    generarFechas();
    mostrarDesplegable(); // inicializar visibilidad al cargar
};