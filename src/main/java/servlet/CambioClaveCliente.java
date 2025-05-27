package servlet;

import dao.ClienteJpaController;
import util.HashUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CambioClaveCliente", urlPatterns = {"/cambioClaveCliente"})
public class CambioClaveCliente extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String login = request.getParameter("login");
        String claveActual = request.getParameter("claveActual");
        String nuevaClave = request.getParameter("nuevaClave");

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            if (login == null || claveActual == null || nuevaClave == null ||
                login.isEmpty() || claveActual.isEmpty() || nuevaClave.isEmpty()) {
                out.println("Faltan parámetros");
                return;
            }

            ClienteJpaController controller = new ClienteJpaController();

            String claveActualHash = HashUtil.cifrarClave(claveActual);
            String nuevaClaveHash = HashUtil.cifrarClave(nuevaClave);

            boolean esValida = controller.validar(login, claveActualHash);

            if (!esValida) {
                out.println("Clave actual incorrecta.");
                return;
            }

            boolean actualizado = controller.actualizarClave(login, nuevaClaveHash);

            if (actualizado) {
                out.println("Clave actualizada correctamente.");
            } else {
                out.println("Error al actualizar la clave.");
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<form method='POST'>");
            out.println("Login: <input type='text' name='login' required><br>");
            out.println("Clave actual: <input type='password' name='claveActual' required><br>");
            out.println("Nueva clave: <input type='password' name='nuevaClave' required><br>");
            out.println("<button type='submit'>Cambiar Clave</button>");
            out.println("</form>");
        }
    }
}
