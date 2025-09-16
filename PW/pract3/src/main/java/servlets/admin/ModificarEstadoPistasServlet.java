package servlets.admin;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import data.dao.pistas.PistaDAO;
import data.dto.pistas.PistaDTO;
import display.CustomerBean;

@WebServlet("/admin/ModificarEstadoPistasServlet")
public class ModificarEstadoPistasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();

        CustomerBean cB = (CustomerBean) session.getAttribute("customerBean");

        if(cB == null || cB.getCorreo() == null || cB.getCorreo() == "" || cB.getAdmin() == false){
            request.getRequestDispatcher("/include/iniciosesionAdmin.jsp").forward(request, response);
        } 
        
        try {
            // Obtener la lista de pistas disponibles
            PistaDAO pistaDAO = new PistaDAO();
            ArrayList<PistaDTO> pistas = pistaDAO.requestAllPistas();

            // Pasar la lista de pistas a la vista
            request.setAttribute("pistas", pistas);
            request.getRequestDispatcher("/mvc/view/admin/modificar_estado_pistasView.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // Leer los parámetros enviados
        String nombrePista = request.getParameter("nombrePista");
        boolean estado = Boolean.parseBoolean(request.getParameter("estado_"+ nombrePista));

        try {
            // Actualizar el estado de la pista
            PistaDAO pistaDAO = new PistaDAO();
            boolean actualizado = pistaDAO.updateEstadoPista(nombrePista, estado);

            if (actualizado) {
                response.sendRedirect("/pract3/mvc/view/admin/modificar_estado_CorrectoView.jsp");
                return;
            } else {
                response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
                return;
            }
        } catch (Exception e) {
            response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
            return;
        }
    }
}
