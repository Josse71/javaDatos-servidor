import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Servidor {
    public static void main(String[] args) {
        final int PORT_UDP = 2500;

        try {
            DatagramSocket udpSocket = new DatagramSocket(PORT_UDP);
            System.out.println("Servidor UDP escuchando en el puerto " + PORT_UDP + "...");

            byte[] buffer = new byte[1024];

            while (true) {
                try {
                    // Espera mensaje del cliente
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    udpSocket.receive(packet);

                    String mensaje = new String(packet.getData(), 0, packet.getLength());
                    String[] partes = mensaje.split(",");

                    int portTCP = Integer.parseInt(partes[0].trim());
                    String nombreCliente = partes[1].trim();
                    String fechaEnvio = partes[2].trim();

                    String fechaRecepcion = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

                    System.out.println("\n--- NUEVA CONEXIÓN UDP RECIBIDA ---");
                    System.out.println("Cliente: " + nombreCliente);
                    System.out.println("Fecha enviada por cliente: " + fechaEnvio);
                    System.out.println("Fecha de recepción: " + fechaRecepcion);
                    System.out.println("Intentando conexión TCP al puerto " + portTCP + "...");

                    // Conexión TCP al cliente
                    Socket tcpSocket = new Socket(packet.getAddress(), portTCP);
                    BufferedReader in = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));

                    String linea;
                    System.out.println("Recibiendo datos del cliente...");
                    while ((linea = in.readLine()) != null) {
                        System.out.println("Dato recibido: " + linea);
                    }

                    System.out.println("El cliente ha cerrado la conexión TCP.");
                    tcpSocket.close();

                } catch (Exception e) {
                    System.err.println("Error gestionando conexión: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }
}
