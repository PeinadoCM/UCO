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

@WebServlet("/user/BuscarPistaTipoServlet")
public class BuscarPistaTipoServlet  extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        CustomerBean cB = (CustomerBean) session.getAttribute("customerBean");

        if(cB == null || cB.getCorreo() == null || cB.getCorreo() == "" || cB.getAdmin() == true){
            request.getRequestDispatcher("/include/iniciosesionUser.jsp").forward(request, response);
        } 
        if(request.getParameter("tipo") == null){
            response.sendRedirect("/pract3/mvc/control/user/pagina_principalUserController.jsp");
            return;
        }

        Boolean tipo = Boolean.parseBoolean(request.getParameter("tipo"));
        
        PistaDAO pista = new PistaDAO();
        ArrayList<PistaDTO> pistas = pista.requestTipoPista(tipo);

        request.setAttribute("pistas",pistas);
        request.getRequestDispatcher("/mvc/view/user/mostrar_pistasUserView.jsp").forward(request, response);
    }
}