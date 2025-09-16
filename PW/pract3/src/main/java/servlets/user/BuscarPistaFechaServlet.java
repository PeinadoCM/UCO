package servlets.user;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.*;

import data.dao.pistas.*;
import data.dto.pistas.*;
import display.CustomerBean;

@WebServlet("/user/BuscarPistaFechaServlet")
public class BuscarPistaFechaServlet  extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            
        java.sql.Date fecha = null;
        HttpSession session = request.getSession();

        CustomerBean cB = (CustomerBean) session.getAttribute("customerBean");

        if(cB == null || cB.getCorreo() == null || cB.getCorreo() == "" || cB.getAdmin() == true){
            request.getRequestDispatcher("/include/iniciosesionUser.jsp").forward(request, response);
        } 
        if(request.getParameter("fechaInicio") == null){
            response.sendRedirect("/pract3/mvc/control/user/pagina_principalUserController.jsp");
            return;
        }
        
        try {
            // Obtener la lista de pistas disponibles
            fecha = java.sql.Date.valueOf(request.getParameter("fechaInicio"));
            PistaDAO pistaDAO = new PistaDAO();
            ArrayList<PistaDTO> pistas = pistaDAO.requestFechaPista(fecha);

            // Pasar la lista de pistas a la vista
            request.setAttribute("pistas", pistas);
            request.getRequestDispatcher("/mvc/view/user/mostrar_pistasUserView.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
            return;
        }

    }
        
}