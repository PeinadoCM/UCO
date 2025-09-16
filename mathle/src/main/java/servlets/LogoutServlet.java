package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Invalida la sesión actual, si existe
        HttpSession session = request.getSession(false); // false para no crear una nueva si no hay
        if (session != null) {
            session.invalidate();
        }

        // Redirige al usuario al formulario de inicio de sesión
        response.sendRedirect("/mathle/");
    }
}
