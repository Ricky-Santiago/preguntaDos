package util;

import dao.ClienteJpaController;

public class ClienteTest {

    public static void main(String[] args) {
        // Crear instancia del controlador JPA
        ClienteJpaController controller = new ClienteJpaController();

        // Credenciales a verificar
        String login = "hola";
        String passwordPlano = "1234";

        // Cifrar la contraseña antes de validarla
        String passwordCifrada = HashUtil.cifrarClave(passwordPlano);

        // Llamar al método validar con la clave cifrada
        boolean esValido = controller.validar(login, passwordCifrada);

        // Mostrar resultado
        if (esValido) {
            System.out.println("✅ Login exitoso.");
        } else {
            System.out.println("❌ Credenciales incorrectas.");
        }
    }
}
