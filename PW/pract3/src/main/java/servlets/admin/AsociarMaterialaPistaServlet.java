package servlets.admin;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;

import data.dao.pistas.PistaDAO;
import data.dto.pistas.MaterialDTO;
import data.dto.pistas.PistaDTO;
import display.CustomerBean;
import data.dto.pistas.Estado;

@WebServlet("/admin/AsociarMaterialAPistaServlet")
public class AsociarMaterialaPistaServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        CustomerBean cB = (CustomerBean) session.getAttribute("customerBean");

        if(cB == null || cB.getCorreo() == null || cB.getCorreo() == "" || cB.getAdmin() == false){
            request.getRequestDispatcher("/include/iniciosesionAdmin.jsp").forward(request, response);
        } 
    
        PistaDAO pistaDAO = new PistaDAO();
        ArrayList<MaterialDTO> materiales = pistaDAO.requestMaterialesDisponibles();

        ArrayList<PistaDTO> pistas = pistaDAO.requestAllPistas();

        request.setAttribute("materiales", materiales);
        request.setAttribute("pistas", pistas);

        request.getRequestDispatcher("/mvc/view/admin/asociar_material_pistaView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Recoger los parámetros enviados por el cliente
        String nombrePista = request.getParameter("nombrePista");
        String idMaterialStr = request.getParameter("id");
        
        if (nombrePista == null || idMaterialStr == null) {
            response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
            return;
        }

        int idMaterial;
        try {
            idMaterial = Integer.parseInt(idMaterialStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
            return;
        }

        // Crear instancia del DAO
        PistaDAO pistaDAO = new PistaDAO();

        try {
            // Validar que el material existe y está disponible
            MaterialDTO material = pistaDAO.requestMaterial(idMaterial);
            if (material == null) {
                response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
                return;
            }
            
            if (material.getEstado() != Estado.DISPONIBLE) {
                response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
                return;
            }

            // Validar que la pista existe
            PistaDTO pista = pistaDAO.requestPista(nombrePista);
            if (pista == null) {
                response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
                return;
            }
            
            if(pista.getTipo() && !material.getUso()){
                response.sendRedirect("/pract3/mvc/view/admin/asociar_material_pistaErrorExteriorInteriorView.jsp");
                return;
            }

            if(pistaDAO.checkDisponibilidadPista(nombrePista, material.getTipo()) == false){
                request.setAttribute("tipoMaterial", material.getTipo().name());
                request.getRequestDispatcher("/mvc/view/admin/asociar_material_pistaErrorMaximoMaterialView.jsp").forward(request, response);
                return;
            }

            // Asociar el material a la pista
            if (pistaDAO.assignNewMaterialToPista(nombrePista, idMaterial)) {
                response.sendRedirect("/pract3/mvc/control/admin/pagina_principalAdminController.jsp");
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