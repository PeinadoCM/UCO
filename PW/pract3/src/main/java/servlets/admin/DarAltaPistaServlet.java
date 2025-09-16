package servlets.admin;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import data.dao.pistas.*;
import data.dto.pistas.TipoPista;

@WebServlet("/admin/DarAltaPistaServlet")
public class DarAltaPistaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Configurar la codificación de caracteres
        request.setCharacterEncoding("UTF-8");

        // 1. Leer los parámetros enviados desde el formulario
        String nombre = request.getParameter("nombre");

        // Leer y convertir los valores booleanos (como enteros: 0 o 1)
        boolean estado = Boolean.parseBoolean(request.getParameter("estado")); // 0 = Ocupada, 1 = Libre
        boolean tipo = Boolean.parseBoolean(request.getParameter("tipo")); // 0 = Interior, 1 = Exterior

        // Leer los valores numéricos
        String tamanio = request.getParameter("tamanio"); // "MINIBASKET", "ADULTOS", "TRESVSTRES"
        TipoPista tamaño =TipoPista.MINIBASKET ;
        String maximoJugadoresStr = request.getParameter("maximoJugadores");

        // Validar y convertir los datos numéricos
        int maximoJugadores = 0;
        try {
            maximoJugadores = Integer.parseInt(maximoJugadoresStr);
        } catch (NumberFormatException e) {
            response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
            return;
        }
        if (tamanio.equals("MINIBASKET")){
            tamaño =TipoPista.MINIBASKET ;

        }

        if (tamanio.equals("ADULTOS")){
            tamaño = TipoPista.ADULTOS;

        }
        if (tamanio.equals("TRESVSTRES")){
         tamaño =TipoPista.TRESVSTRES;

        }

        // 2. Acceder al DAO para guardar la pista en la base de datos
        try {
            PistaDAO pista = new PistaDAO();
            pista.createPista(nombre, estado, tipo, tamaño, maximoJugadores);
        } catch (Exception e) {
            response.sendRedirect("/pract3/mvc/view/error/error_general.jsp");
            return;
        }

        response.sendRedirect("/pract3/mvc/view/admin/dar_alta_pistaCorrectoView.jsp");
        return;
    }
}
