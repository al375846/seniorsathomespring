package seniorsathome.seniorsathomespring.model;

public class Correo {

    private static final String general = "sah@info.com";

    public static void enviarMensajeSah(String receptor, String asunto, String mensaje) {
        System.out.println("A message has been sent from: " + general + " to: " + receptor + " with subject: " + asunto + " and body: " + mensaje);
    }

    public static void enviarMensajeSah(String emisor, String receptor, String asunto, String mensaje) {
        System.out.println("A message has been sent from: " + emisor + " to: " + receptor + " with subject: " + asunto + " and body: " + mensaje);
    }
}
