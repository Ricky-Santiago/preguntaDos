package util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

    // Método para cifrar una clave con SHA-256
    public static String cifrarClave(String clavePlano) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(clavePlano.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();

            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b)); // Convertir a hexadecimal
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al cifrar la clave con SHA-256", e);
        }
    }

    // Método main para probar la función de cifrado
    public static void main(String[] args) {
        String claveOriginal = "1234";
        String claveCifrada = cifrarClave(claveOriginal);

        System.out.println("Clave original: " + claveOriginal);
        System.out.println("Clave cifrada (SHA-256): " + claveCifrada);
    }
}

