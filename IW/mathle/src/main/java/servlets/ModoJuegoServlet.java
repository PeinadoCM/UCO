package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/modoJuego")
public class ModoJuegoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String modo = request.getParameter("modo"); // puede ser "nino", "normal", o "anteriores"
        String modoAnterior = request.getParameter("modoAnterior"); // solo si modo == "anteriores"
        String fechaSeleccionada = request.getParameter("fechaSeleccionada");

        if(modo == null){
            response.sendRedirect("/mathle");
            return;
        }

        HttpSession session = request.getSession();

        // Si el modo es "anteriores", sustituimos el modo por el submodo (modoAnterior)
        if ("anteriores".equals(modo)) {
            if ("normal".equals(modoAnterior) || "ninos".equals(modoAnterior)) {
                modo = modoAnterior; // reemplazamos el valor
            }
        }

        // Guardamos el modo final
        session.setAttribute("modoJuego", modo);

        // Guardamos la fecha seleccionada o la actual
        if ("anteriores".equals(request.getParameter("modo")) && fechaSeleccionada != null && !fechaSeleccionada.isEmpty()) {
            session.setAttribute("fecha", fechaSeleccionada);
        } else {
            session.setAttribute("fecha", LocalDate.now().toString());
        }

        // Redirige a la siguiente vista
        response.sendRedirect("/mathle/mvc/view/seleccionarDificultad.jsp");
    }
}
