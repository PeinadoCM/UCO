package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

import data.dao.UsuarioDAO;
import data.dao.PartidaDAO;
import data.dto.Partida;
import data.dto.Usuario;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");
        Usuario usuario = new Usuario();

        if (usuarioDAO.verificarCredenciales(correo, contrasena)) {
            usuario = usuarioDAO.obtenerDatosUsuario(correo);
            HttpSession session = request.getSession();

            ArrayList<Partida> partidas = (ArrayList<Partida>) session.getAttribute("partidasJugadas");
            if(partidas.isEmpty() == false){
                PartidaDAO partidaDAO = new PartidaDAO();
                for (Partida partida : partidas) {
                    if(partidaDAO.comprobarPartida(usuario.getNombre(), partida.getIdProblema()) == false) {
                        partidaDAO.insertarPartida(usuario.getNombre(), partida.getIdProblema(), partida.getPuntuacion(), partida.getDuracion(), partida.getIntentos());
                        usuario.aumentarPuntuacion(partida.getPuntuacion());
                    }
                }
                usuarioDAO.actualizarPuntuacion(usuario.getCorreo(), usuario.getPuntuacion());
            }

            session.setAttribute("usuario", usuario);
            response.sendRedirect("/mathle"); // Redirige al juego si es exitoso
        } else {
            request.setAttribute("mensaje", "Correo o contraseña incorrectos.");
            request.getRequestDispatcher("mvc/view/inicioSesion.jsp").forward(request, response);
        }
    }
}
