package servlets;

import java.sql.Timestamp;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDateTime;
import data.dao.MensajeDAO;
import data.dto.Mensaje;
import data.dto.Usuario;
import java.util.List;


@WebServlet("/MensajeServlet")
public class MensajeServlet extends HttpServlet {
    private MensajeDAO mensajeDAO = new MensajeDAO();

    // Manejar GET: mostrar mensajes
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;

        if (usuario == null) {
            response.sendRedirect("/mathle");
            return;
        }

        List<Mensaje> mensajes = mensajeDAO.obtenerMensajes(); // Asegúrate de tener este método en tu DAO
        request.setAttribute("mensajes", mensajes);
        request.getRequestDispatcher("./mvc/view/visualizarMensaje.jsp").forward(request, response);
    }

    // Manejar POST: publicar mensaje
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String contenido = request.getParameter("mensaje");
        HttpSession session = request.getSession(false);
        Usuario usuario = (session != null) ? (Usuario) session.getAttribute("usuario") : null;
        String autor = (usuario != null) ? usuario.getNombre() : null;

        if (autor != null && contenido != null && !contenido.trim().isEmpty()) {
            boolean exito = mensajeDAO.publicarMensaje(autor, contenido, Timestamp.valueOf(LocalDateTime.now()));

            if (exito) {
                response.sendRedirect(request.getContextPath() + "/MensajeServlet");
            } else {
                request.setAttribute("error", "No se pudo guardar el mensaje.");
                doGet(request, response);
            }
        } else {
            request.setAttribute("error", "Debes escribir un mensaje.");
            doGet(request, response);
        }
    }

}

