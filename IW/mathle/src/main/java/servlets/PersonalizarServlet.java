package servlets;

import data.dto.Usuario;
import data.dao.UsuarioDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/PersonalizarServlet")
public class PersonalizarServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String color = request.getParameter("color");

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath());
            return;
        }

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (color != null && !color.trim().isEmpty()) {
            // Actualizamos tema en el objeto usuario
            if(usuario != null){
                usuario.setTema(color);

                // Actualizamos la base de datos con el nuevo tema
                usuarioDAO.actualizarTema(usuario.getCorreo(), color);
            }

            // También podemos guardar el tema como atributo separado para el JSP, si lo usas así:
            session.setAttribute("color", color);
        }

        // Redirigimos a la página principal o donde corresponda
        response.sendRedirect(request.getContextPath());
    }
}
