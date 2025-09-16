package servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import data.dao.ProblemaDAO;
import data.dao.PartidaDAO;
import data.dto.Usuario;

@WebServlet("/RankingPartidaServlet")
public class RankingPartidaServlet extends HttpServlet {
    private PartidaDAO partidaDAO = new PartidaDAO();
    private ProblemaDAO problemaDAO = new ProblemaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        if (session.getAttribute("modoJuego") == null || session.getAttribute("dificultad") == null || session.getAttribute("fecha") == null) {
            response.sendRedirect("/mathle");
            return;
        }

        String modo = (String) session.getAttribute("modoJuego");
        int dificultad = (int) session.getAttribute("dificultad");
        String fechaStr = (String) session.getAttribute("fecha");

        Date fecha;
        try {
            fecha = Date.valueOf(fechaStr); // formato esperado: yyyy-MM-dd
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato de fecha inválido. Usa yyyy-MM-dd.");
            return;
        }

        Integer idProblema = problemaDAO.obtenerIdProblemaPorCriterios(modo, dificultad, fecha);

        session.removeAttribute("modoJuego");
        session.removeAttribute("dificultad");
        session.removeAttribute("fecha");

        if (idProblema == null) {
            request.setAttribute("error", "No se encontró una partida con esos criterios.");
            request.getRequestDispatcher("./mvc/view/vistaRankingPartida.jsp").forward(request, response);
            return;
        }

        List<Usuario> ranking = partidaDAO.obtenerRankingPorProblema(idProblema);
        request.setAttribute("ranking", ranking);
        request.getRequestDispatcher("./mvc/view/vistaRankingPartida.jsp").forward(request, response);
    }
}
