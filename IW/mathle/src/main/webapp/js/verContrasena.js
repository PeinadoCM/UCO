function togglePassword() {
      const input = document.getElementById("contrasena");
      const icon = document.getElementById("eye-icon");
      const isPassword = input.type === "password";
      input.type = isPassword ? "text" : "password";

      icon.setAttribute("d", isPassword
        ? "M12 5c-7.633 0-12 7-12 7s4.367 7 12 7 12-7 12-7-4.367-7-12-7zm0 12a5 5 0 1 1 0-10 5 5 0 0 1 0 10z"
        : "M1 1l22 22M9.9 9.9a3 3 0 0 0 4.2 4.2m-1.05-6.55a5 5 0 0 1 5.9 5.9M12 5c-7.633 0-12 7-12 7a21.878 21.878 0 0 0 5.2 5.6m3.3-3.3a5 5 0 0 0 7.1-7.1M12 19c7.633 0 12-7 12-7a21.878 21.878 0 0 0-5.2-5.6");
    }