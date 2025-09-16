package servlets.user;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.*;
import java.text.SimpleDateFormat;


import data.dao.reservas.*;
import data.dto.reservas.*;
import display.CustomerBean;

@WebServlet("/user/CancelarReservaServlet")
public class CancelarReservaServlet extends HttpServlet {
     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException { 

        HttpSession session = request.getSession();

        CustomerBean cB = (CustomerBean) session.getAttribute("customerBean");

        if(cB == null || cB.getCorreo() == null || cB.getCorreo() == "" || cB.getAdmin() == true){
            request.getRequestDispatcher("/include/iniciosesionUser.jsp").forward(request, response);
        } 
        try {
            // Obtener la lista de pistas disponibles
            ReservaDAO reservaDAO = new ReservaDAO();
            ArrayList<ReservaFamiliarDTO> reservas=reservaDAO.requestReservaCancelables(cB.getCorreo());
            
            if(reservas.isEmpty()){
                response.sendRedirect("/pract3/mvc/view/user/error_no_reservas.jsp");
                return;
            }

            // Pasar la lista de pistas a la vista
            request.setAttribute("reservas", reservas);
            request.getRequestDispatcher("/mvc/view/user/cancelar_reservaView.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
            return;
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException { 
        String nombrePista = null;
        java.util.Date fecha = null;
        try {
            
            String reserva = request.getParameter("opcion");  // Formato: "YYYY-MM-DD HH:mm:ss|NombrePista"
            String[] partes = reserva.split("\\|");          // Dividir por el delimitador "|"
            System.out.println(reserva);
            // Parsear la fecha y la hora
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            fecha = sdf.parse(partes[0]); // Primera parte es la fecha y hora
            System.out.println(fecha);
            // Obtener el nombre de la pista
            nombrePista = partes[1];
         } catch (Exception e) {
            response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
            return;
        }
        
        ReservaDAO reservaDAO = new ReservaDAO();

        if(!reservaDAO.cancelarReserva(nombrePista,fecha)){
            response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
            return;
        }
        else{
            response.sendRedirect("/pract3/mvc/view/user/cancelar_reserva_CorrectoView.jsp");
            return;
        }         
    }
}
