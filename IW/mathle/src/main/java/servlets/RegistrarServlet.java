package servlets;

import data.dao.UsuarioDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/registro")
public class RegistrarServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recoger los datos del formulario
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        if (nombre == null || nombre.trim().isEmpty() ||
            correo == null || correo.trim().isEmpty() ||
            contrasena == null || contrasena.trim().isEmpty()) {
            request.setAttribute("mensaje", "Todos los campos son obligatorios.");
            request.getRequestDispatcher("mvc/view/registro.jsp").forward(request, response);
            return;
        }


        UsuarioDAO usuarioDAO = new UsuarioDAO();

        if (usuarioDAO.existeCorreo(correo)) {
            // El email ya esta registrado
            request.setAttribute("mensaje", "El correo electrónico ya está en uso.");
            request.getRequestDispatcher("mvc/view/registro.jsp").forward(request, response);
        }
        else if (usuarioDAO.existeNombre(nombre)) {
            // El nombre ya esta registrado
            request.setAttribute("mensaje", "El nombre de usuario ya está en uso.");
            request.getRequestDispatcher("mvc/view/registro.jsp").forward(request, response);
        }
        else {
            boolean exito = usuarioDAO.insertarUsuario(correo, nombre, contrasena);
            if (exito) {
                // Registro exitoso, redirige a la siguiente vista
                response.sendRedirect("mvc/view/inicioSesion.jsp");
            } else {
                // Error al insertar en BD
                request.setAttribute("mensaje", "Hubo un error al registrar el usuario.");
                request.getRequestDispatcher("mvc/view/registro.jsp").forward(request, response);
            }
        }
    }
}
