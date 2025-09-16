// Calcular la fecha máxima permitida (18 años antes de la fecha actual)
const hoy = new Date();
const anioMayorEdad = hoy.getFullYear() - 18;
const mes = hoy.getMonth() + 1; // Los meses en JavaScript van de 0 a 11
const dia = hoy.getDate();

// Formatear la fecha en formato YYYY-MM-DD
const fechaMaxima = `${anioMayorEdad}-${mes.toString().padStart(2, '0')}-${dia.toString().padStart(2, '0')}`;

// Asignar la fecha máxima al input
const inputFecha = document.getElementById('fechaNacimiento');
inputFecha.max = fechaMaxima;

// Validar la fecha en el evento submit (opcional)
document.getElementById('formulario').addEventListener('submit', function(event) {
    const fechaSeleccionada = new Date(inputFecha.value);
    if (fechaSeleccionada > new Date(fechaMaxima)) {
        event.preventDefault();
        alert('Debes ser mayor de 18 años para enviar este formulario.');
    }
});