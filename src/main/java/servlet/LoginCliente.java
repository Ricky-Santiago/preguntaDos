package servlet;

import dao.ClienteJpaController;
import util.HashUtil;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LoginCliente", urlPatterns = {"/logincliente"})
public class LoginCliente extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Solo mostrar info simple en caso que se llame sin parámetros (opcional)
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginCliente</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginCliente at " + request.getContextPath() + "</h1>");
            out.println("<p>Envía login y clave como parámetros GET para validar.</p>");
            out.println("<p>Ejemplo: ?login=usuario&clave=1234</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // Aquí hacemos la validación por GET con SHA-256 y mostramos resultado
    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String login = request.getParameter("login");
    String claveHash = request.getParameter("clave"); // ya viene cifrada desde el frontend

    response.setContentType("text/html;charset=UTF-8");

    try (PrintWriter out = response.getWriter()) {

        if (login == null || claveHash == null) {
            processRequest(request, response);
            return;
        }

        // Ya no cifrar aquí, claveHash ya está cifrada desde frontend
        ClienteJpaController controller = new ClienteJpaController();
        boolean esValido = controller.validar(login, claveHash);

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Resultado Login</title>");
        out.println("</head>");
        out.println("<body>");

        if (esValido) {
            out.println("<h2 style='color:green;'>✅ Login exitoso para usuario: " + login + "</h2>");
        } else {
            out.println("<h2 style='color:red;'>❌ Usuario o contraseña incorrectos.</h2>");
        }

        out.println("</body>");
        out.println("</html>");
    }
}


    // Mantener el doPost igual con processRequest (o lo puedes eliminar si no usas POST)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet que valida login cliente con SHA-256 vía parámetros GET";
    }
}
