import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        final String HOST = "localhost";
        final int PORT_UDP = 2500;

        try {
            // Preparar socket UDP
            DatagramSocket udpSocket = new DatagramSocket();

            System.out.print("Introduce tu nombre: ");
            String nombre = sc.nextLine();

            int puertoTCP = 5000 + (int) (Math.random() * 1000); // puerto TCP aleatorio
            String fechaEnvio = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            // Enviar mensaje UDP al servidor
            String mensaje = puertoTCP + "," + nombre + "," + fechaEnvio;
            byte[] datos = mensaje.getBytes();
            DatagramPacket packet = new DatagramPacket(datos, datos.length, InetAddress.getByName(HOST), PORT_UDP);
            udpSocket.send(packet);
            System.out.println("Mensaje UDP enviado al servidor.");

            // Iniciar servidor TCP local para aceptar la conexión
            ServerSocket serverTCP = new ServerSocket(puertoTCP);
            System.out.println("Esperando conexión TCP del servidor...");

            Socket conexion = serverTCP.accept();
            System.out.println("Servidor conectado vía TCP. Enviando datos...");

            PrintWriter out = new PrintWriter(conexion.getOutputStream(), true);

            // Enviar varios mensajes de ejemplo
            for (int i = 1; i <= 3; i++) {
                out.println("Dato número " + i + " desde el cliente " + nombre);
                Thread.sleep(1000);
            }

            // Cerrar conexión TCP
            out.close();
            conexion.close();
            serverTCP.close();
            System.out.println("Conexión TCP cerrada correctamente.");

        } catch (Exception e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
