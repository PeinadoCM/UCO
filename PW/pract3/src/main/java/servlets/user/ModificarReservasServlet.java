package servlets.user;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import data.dao.pistas.PistaDAO;
import data.dao.reservas.*;
import data.dto.reservas.*;
import display.CustomerBean;

@WebServlet("/user/ModificarReservasServlet")
public class ModificarReservasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        CustomerBean cB = (CustomerBean) session.getAttribute("customerBean");

        if(cB == null || cB.getCorreo() == null || cB.getCorreo() == "" || cB.getAdmin() == true){
            request.getRequestDispatcher("/include/iniciosesionUser.jsp").forward(request, response);
        } 

        ReservaDAO reservaDAO = new ReservaDAO();
        ArrayList<ReservaFamiliarDTO> reservas = reservaDAO.requestReservasFuturasCorreo(cB.getCorreo());

        if(reservas.isEmpty()){
            response.sendRedirect("/pract3/mvc/view/user/error_no_reservas.jsp");
            return;
        }
        else{
            request.setAttribute("reservas", reservas);

            request.getRequestDispatcher("/mvc/view/user/modificar_reservaView.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        ReservaDAO reservaDAO = new ReservaDAO();

        int n_niños = Integer.parseInt(request.getParameter("num_Niños"));
        int n_adultos = Integer.parseInt(request.getParameter("num_Adultos"));
        int duracion = Integer.parseInt(request.getParameter("duracion"));
        String fecha_hora = request.getParameter("fechaHora");
        String fecha = request.getParameter("fecha");
        String nombre_pista = request.getParameter("nombre_pista");

        java.util.Date fechanueva = null;
        java.util.Date fechaantigua = null;
        try{
            fechanueva = sdf.parse(fecha_hora);
            fechaantigua = sdf.parse(fecha);
        } catch (Exception e){}

        //Comprobar disponibilidad de la reserva
        ArrayList<String> pistas = new ArrayList<>();
        pistas.add(nombre_pista);
        pistas = reservaDAO.checkDisponibilidadPistas(pistas, fechanueva, duracion);

        if(pistas.size() == 0){
            response.sendRedirect("/pract3/mvc/view/user/error_no_hay_pistas_disponibles.jsp");
            return;
        }

        //Comprobar maximo de jugadores para la pista
        PistaDAO pistaDAO = new PistaDAO();

        if(!pistaDAO.checkMaxJugadoresPista(nombre_pista, n_niños+n_adultos)){
            response.sendRedirect("/pract3/mvc/view/user/error_maximo_jugadores.jsp");
            return;
        }

        int precio = 0;
        switch(duracion){
            case 60:
                precio = 20;
            break;
            case 90:
                precio = 30;
            break;
            case 120:
                precio = 40;
            break;
        }

        if(reservaDAO.modificarReserva(nombre_pista, fechaantigua, fechanueva, duracion, precio, n_niños, n_adultos)){
            response.sendRedirect("/pract3/mvc/view/user/modificar_reserva_CorrectoView.jsp");
            return;
        }
        else{
            response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
            return;
        }

    }

}