package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.sql.*;

import java.io.IOException;
import java.util.ArrayList;

import data.common.OperacionGenerator;
import data.dao.ProblemaDAO;
import data.dao.PartidaDAO;
import data.dto.Usuario;
import data.dto.Partida;

@WebServlet("/seleccionarDificultad")
public class SeleccionarDificultadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String dificultadStr = request.getParameter("dificultad");
        HttpSession session = request.getSession();

        if (dificultadStr == null || dificultadStr.trim().isEmpty() || session.getAttribute("modoJuego") == null || session.getAttribute("fecha") == null) {
            response.sendRedirect("/mathle");
            return;
        }

        // Guardar dificultad en sesión
        
        int dificultad = Integer.parseInt(dificultadStr);
        session.setAttribute("dificultad", dificultad);
        String modoJuego = (String) session.getAttribute("modoJuego");

        String fechaStr = (String) session.getAttribute("fecha");
        Date fecha = null;
        
        try{
            fecha = Date.valueOf(fechaStr);
        } catch (Exception e){
            response.sendRedirect("/mathle");
            return;
        }
        

        // Comprobar si el usuario ha jugado la partida antes

        ProblemaDAO problemaDAO = new ProblemaDAO();

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        String operacion = problemaDAO.obtenerOperacion(dificultad, modoJuego, fecha);

        if (operacion == null) {
            operacion = OperacionGenerator.generarOperacion(dificultad);
            problemaDAO.insertarProblema(dificultad, modoJuego, fecha, operacion);
        }

        int idProblema = problemaDAO.obtenerIdProblemaPorCriterios(modoJuego, dificultad, fecha);
        if(usuario != null) {
            PartidaDAO partidaDAO = new PartidaDAO();
            if(partidaDAO.comprobarPartida(usuario.getNombre(), idProblema)){
                response.sendRedirect("/mathle/RankingPartidaServlet");
                return;
            }
        }
        else{
            ArrayList<Partida> partidas = (ArrayList<Partida>) session.getAttribute("partidasJugadas");
            ArrayList<Integer> idProblemasPartidas = new ArrayList<>();
            if(partidas.isEmpty() == false){
                for (Partida partida : partidas) {
                    idProblemasPartidas.add(partida.getIdProblema());
                }
                if(idProblemasPartidas.contains(idProblema)){
                    response.sendRedirect("/mathle/RankingPartidaServlet");
                    return;
                } 
            }
        }

        session.setAttribute("operacion", operacion);

        //// Redirigir a la vista de partida
        request.getRequestDispatcher("mvc/view/vistaPartida.jsp").forward(request, response);
    }
}
