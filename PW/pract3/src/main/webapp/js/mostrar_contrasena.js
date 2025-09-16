function togglePasswordVisibility() {
    const passwordField = document.getElementById('contrasena');
    const toggleButton = document.getElementById('togglePassword');

    if (passwordField.type === 'password') {
        passwordField.type = 'text'; // Cambiar a texto visible
        toggleButton.textContent = '🙈'; // Cambiar icono
    } else {
        passwordField.type = 'password'; // Volver a contraseña oculta
        toggleButton.textContent = '👁️'; // Cambiar icono
    }
}
 