package servlets.admin;

import java.io.IOException;
import java.util.*;
import java.text.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import data.dao.reservas.*;
import data.dto.reservas.*;
import display.CustomerBean;

@WebServlet("/admin/EliminarReservaServlet")
public class EliminarReservaServlet extends HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        CustomerBean cB = (CustomerBean) session.getAttribute("customerBean");

        if(cB == null || cB.getCorreo() == null || cB.getCorreo() == "" || cB.getAdmin() == false){
            request.getRequestDispatcher("/include/iniciosesionAdmin.jsp").forward(request, response);
        } 
        
        ReservaDAO reservaDAO = new ReservaDAO();
        ArrayList<ReservaFamiliarDTO> reservas = reservaDAO.requestReservasFuturas();
        
        if(reservas.isEmpty()){
            response.sendRedirect("/pract3/mvc/view/admin/eliminar_reservasVacioView.jsp");
        }
        else{
            request.setAttribute("reservas", reservas);

            request.getRequestDispatcher("/mvc/view/admin/eliminar_reservaView.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String reserva = request.getParameter("opcion");
        String[] partes = reserva.split(", ");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        Date fechaYHora = null;
        try{
            fechaYHora = sdf.parse(partes[1].substring("fecha y hora".length()).trim());
        } catch(Exception e){
            System.out.println(e);
        }
        String nombre_pista = partes[3].substring("nombre de pista".length()).trim();

        

        ReservaDAO reservaDAO = new ReservaDAO();
        if(reservaDAO.cancelarReserva(nombre_pista, fechaYHora)){
            System.out.println("SI");
        }
        else{
            System.out.println("NO");
        }

        response.sendRedirect("/pract3/mvc/control/admin/pagina_principalAdminController.jsp");
    }
}
