package servlets.admin;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import data.dao.pistas.*;
import data.dto.pistas.Tipo;
import data.dto.pistas.Estado;

@WebServlet("/admin/DarAltaMaterialServlet")
public class DarAltaMaterialServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Configurar la codificación de caracteres
        request.setCharacterEncoding("UTF-8");

        // 1. Leer los parámetros enviados desde el formulario
         boolean uso= Boolean.parseBoolean(request.getParameter("uso"));
         String tipo = request.getParameter("tipo");
         System.out.println(tipo);
         Tipo tipoaux= Tipo.CANASTAS;
         if(tipo.equals("CANASTAS")){
                tipoaux=Tipo.CANASTAS;
         }
         if(tipo.equals("CONOS")){
                tipoaux=Tipo.CONOS;
         }
        if(tipo.equals("PELOTAS")){
                tipoaux=Tipo.PELOTAS;
         }
         String estado = request.getParameter("estado");
         Estado estadoaux= Estado.DISPONIBLE;
         System.out.println(estado);
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
            pista.createNewMaterial(uso,tipoaux,estadoaux);
        } catch (Exception e) {
            response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
            return;
        }
        response.sendRedirect("/pract3/mvc/view/admin/dar_alta_materialCorrectoView.jsp");
        return;
       }

}