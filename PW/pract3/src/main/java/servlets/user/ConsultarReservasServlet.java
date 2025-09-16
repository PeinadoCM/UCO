package servlets.user;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.util.*;

import data.dao.reservas.*;
import data.dto.reservas.*;
import display.CustomerBean;

@WebServlet("/user/ConsultarReservasServlet")
public class ConsultarReservasServlet  extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        
        HttpSession session = request.getSession();

        CustomerBean cB = (CustomerBean) session.getAttribute("customerBean");

        if(cB == null || cB.getCorreo() == null || cB.getCorreo() == "" || cB.getAdmin() == true){
            request.getRequestDispatcher("/include/iniciosesionUser.jsp").forward(request, response);
        } 
        if(request.getParameter("fechaInicio") == null || request.getParameter("fechaFin") == null){
            response.sendRedirect("/pract3/mvc/control/user/pagina_principalUserController.jsp");
            return;
        }
        // Obtener las fechas de inicio y fin del parámetro de la solicitud
        java.sql.Date fechaInicio = null;
        java.sql.Date fechaFin = null;
        try{
            fechaInicio = java.sql.Date.valueOf(request.getParameter("fechaInicio"));
            fechaFin = java.sql.Date.valueOf(request.getParameter("fechaFin"));
        } catch (Exception e){
            response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
            return;
        }
        
        Date fecha_actual = new Date();
        ReservaDAO reservaDAO = new ReservaDAO();
        
        ArrayList<ReservaFamiliarDTO> reservas = reservaDAO.requestReservasEntreFechas(fechaInicio, fechaFin, cB.getCorreo());
        ArrayList<ReservaFamiliarDTO> reservas_finalizadas = new ArrayList<>();
        ArrayList<ReservaFamiliarDTO> reservas_futuras = new ArrayList<>();

        for(ReservaFamiliarDTO reserva : reservas){
            if(reserva.getFecha_hora().before(fecha_actual)){
                reservas_finalizadas.add(reserva);
            }
            else{
                reservas_futuras.add(reserva);
            }
        }

        request.setAttribute("reservas_finalizadas", reservas_finalizadas);
        request.setAttribute("reservas_futuras", reservas_futuras);

        request.getRequestDispatcher("/mvc/view/user/consultar_reservasView.jsp").forward(request, response);
    }
}