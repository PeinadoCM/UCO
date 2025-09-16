package servlets.user;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import data.dao.reservas.*;
import data.dto.pistas.*;
import display.CustomerBean;

@WebServlet("/user/AdquirirBonoServlet")
public class AdquirirBonoServlet  extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();

        CustomerBean cB = (CustomerBean) session.getAttribute("customerBean");
        TipoPista tipo = null;
        try{
            tipo = TipoPista.valueOf(request.getParameter("tipoBono"));
        } catch (Exception e){
            response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
            return;
        }

        ReservaDAO reservaDAO = new ReservaDAO();

        if(reservaDAO.createNewBono(cB.getCorreo(), tipo)){
            response.sendRedirect("/pract3/mvc/view/user/adquirir_bonoCorrectoView.jsp");
            return;
        }
        else{
            response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
            return;
        }

    }

}