window.onload = function () {
    const mensaje = document.getElementById("mensaje");
    if (mensaje) {
    setTimeout(() => {
        mensaje.style.transition = "opacity 0.5s";
        mensaje.style.opacity = 0;
        setTimeout(() => mensaje.style.display = "none", 500);
    }, 3500);
    }
};