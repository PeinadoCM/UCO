package servlets.user;

import java.io.IOException;
import java.text.SimpleDateFormat;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.*;

import data.dao.jugadores.JugadorDAO;
import data.dao.pistas.PistaDAO;
import data.dao.reservas.ReservaDAO;
import display.CustomerBean;

@WebServlet("/user/RealizarReservaBonoServlet")
public class RealizarReservaBonoServlet  extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();

        CustomerBean cB = (CustomerBean) session.getAttribute("customerBean");

        if(cB == null || cB.getCorreo() == null || cB.getCorreo() == "" || cB.getAdmin() == true){
            request.getRequestDispatcher("/include/iniciosesionUser.jsp").forward(request, response);
        } 
        if(request.getParameter("tipoReserva") == null || request.getParameter("numNinos") == null || request.getParameter("numAdultos") == null || request.getParameter("duracion") == null || request.getParameter("fechaHora") == null){
            response.sendRedirect("/pract3/mvc/control/user/pagina_principalUserController.jsp");
            return;
        }
        ReservaDAO reservaDAO = new ReservaDAO();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        String tipoReserva = request.getParameter("tipoReserva");
        int n_niños = 0;
        int n_adultos = 0;
        switch(tipoReserva){
            case "infantil":
                n_niños = Integer.parseInt(request.getParameter("numNinos"));
            break;
            case "familiar":
                n_niños = Integer.parseInt(request.getParameter("numNinos"));
                n_adultos = Integer.parseInt(request.getParameter("numAdultos"));
            break;
            case "adultos":
                n_adultos = Integer.parseInt(request.getParameter("numAdultos"));
            break;
        }

        int duracion = Integer.parseInt(request.getParameter("duracion"));
        String fecha_hora = request.getParameter("fechaHora");

        java.util.Date fecha = null;

        try{
            fecha = sdf.parse(fecha_hora);
        } catch (Exception e){}

        
        //Comprobar que el usuario tiene un bono que permita la reserva del tipo seleccionado
        int id_bono = reservaDAO.checkBonoTipoReserva(cB.getCorreo(), tipoReserva);
        if(id_bono == -1){
            response.sendRedirect("/pract3/mvc/view/user/error_sin_bono_disponible.jsp");
            return;
        }

        //Seleccionar una pista disponible que este disponible para el rango horario seleccionado, para los usuarios seleccionados
        PistaDAO pistaDAO = new PistaDAO();
        ArrayList<String> pistas = new ArrayList<>();
        //Traerme las pistas disponibles
        pistas = pistaDAO.requestPistasDisponiblesTamañoJugadores(tipoReserva, n_niños+n_adultos);
        //Comprobar que estan disponibles en el rango horario
        pistas = reservaDAO.checkDisponibilidadPistas(pistas, fecha, duracion);

        if(pistas.isEmpty()){
            response.sendRedirect("/pract3/mvc/view/user/error_no_hay_pistas_disponibles.jsp");
            return;
        }

        request.setAttribute("pistas", pistas);
        request.setAttribute("id_bono", id_bono);
        request.setAttribute("n_ninos", n_niños);
        request.setAttribute("n_adultos", n_adultos);
        request.setAttribute("duracion", duracion);
        request.setAttribute("fechaHora", fecha);

        request.getRequestDispatcher("/mvc/view/user/realizar_reserva_bono_seleccionar_pistaView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        //Hacer la reserva 
        HttpSession session = request.getSession();
        CustomerBean cB = (CustomerBean) session.getAttribute("customerBean");

        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        ReservaDAO reservaDAO = new ReservaDAO();

        String seleccion = request.getParameter("seleccion"); // Pista seleccionada
        int id_bono = Integer.parseInt(request.getParameter("id_bono"));
        int nNiños = Integer.parseInt(request.getParameter("numNinos"));
        int nAdultos = Integer.parseInt(request.getParameter("numAdultos"));
        int duracion = Integer.parseInt(request.getParameter("duracion"));
        String fechaHora = request.getParameter("fechaHora");
        int precio = 0;

        java.util.Date fecha = null;
        try{
            fecha = sdf.parse(fechaHora);
        } catch (Exception e){}

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

        //Si es la primera reserva del usuario actualizamos su fecha de inscripcion
        if(cB.getFecha_inscripcion() == null){
            JugadorDAO jugadorDAO = new JugadorDAO();
            if(!jugadorDAO.updateFechaInscripcion(cB.getCorreo(), new java.sql.Date(fecha.getTime()))){
                response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
                return;
            }
        }

        if(reservaDAO.createAñadirReservaABono(id_bono, cB.getCorreo(), seleccion, new java.sql.Date(fecha.getTime()), new java.sql.Time(fecha.getTime()), duracion, nNiños, nAdultos, 0.05f, precio)){
            response.sendRedirect("/pract3/mvc/view/user/realizar_reserva_bono_correctoView.jsp");
            return;
        }
        else{
            response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
            return;
        }
    }
}