package servlets.admin;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import data.dao.pistas.*;
import data.dto.pistas.*;
import display.CustomerBean;


@WebServlet("/admin/ModificarEstadoMaterialesServlet")
public class ModificarEstadoMaterialesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession();

        CustomerBean cB = (CustomerBean) session.getAttribute("customerBean");

        if(cB == null || cB.getCorreo() == null || cB.getCorreo() == "" || cB.getAdmin() == false){
            request.getRequestDispatcher("/include/iniciosesionAdmin.jsp").forward(request, response);
        } 

        try {
            PistaDAO pista = new PistaDAO();
            ArrayList<MaterialDTO> materiales= pista.requestMaterialesNoReservado();
            request.setAttribute("materiales",materiales);
            request.getRequestDispatcher("/mvc/view/admin/modificar_estado_materialView.jsp").forward(request, response);
            
        } catch (Exception e) {
            response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String idMaterialStr = request.getParameter("id");
        String estado = request.getParameter("estado_"+idMaterialStr);
        // Validar ID del material
        int idMaterial;
        try {
            idMaterial = Integer.parseInt(idMaterialStr);
        } catch (NumberFormatException e) {
            response.setContentType("text/html");
            response.getWriter().println("<h1>Error: El ID del material debe ser un número válido.</h1>");
            return;
        }

        // Validar estado
        if (!estado.equals("DISPONIBLE") && !estado.equals("RESERVADO") && !estado.equals("MAL_ESTADO")) {
            response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
            return;
        }
        Estado estadoaux= Estado.RESERVADO;
         if(estado.equals("DISPONIBLE")){
                estadoaux = Estado.DISPONIBLE;
         }
        if(estado.equals("MAL_ESTADO")){
                estadoaux = Estado.MAL_ESTADO;
         }
        if(estado.equals("RESERVADO")){
                estadoaux = Estado.RESERVADO;
         }

        try {
            PistaDAO pista = new PistaDAO();
            pista.updateEstadoMaterial(idMaterial,estadoaux);
            
        } catch (Exception e) {
            response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
            return;
        }
        response.sendRedirect("/pract3/mvc/view/admin/modificar_estado_CorrectoView.jsp");
        return;
    }
}
