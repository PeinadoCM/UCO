document.addEventListener("DOMContentLoaded", function () {
        // Obtener el elemento del input
        const fechaHoraInput = document.getElementById("fechaHora");
    
        if (fechaHoraInput) {
            // Crear un objeto de fecha 24 horas en el futuro
            const ahora = new Date();
            ahora.setHours(ahora.getHours() + 24); // Añadir 24 horas
    
            // Formatear la fecha para que sea compatible con el atributo min
            const anio = ahora.getFullYear();
            const mes = String(ahora.getMonth() + 1).padStart(2, "0");
            const dia = String(ahora.getDate()).padStart(2, "0");
            const horas = String(ahora.getHours()).padStart(2, "0");
            const minutos = String(ahora.getMinutes()).padStart(2, "0");
    
            const fechaMinima = `${anio}-${mes}-${dia}T${horas}:${minutos}`;
    
            // Establecer el valor mínimo
            fechaHoraInput.min = fechaMinima;
        }
    });
    