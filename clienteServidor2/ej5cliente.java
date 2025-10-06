import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws UnknownHostException {
        int udpPort = 2500;
        int tcpPort = udpPort + 1;
        InetAddress ip = InetAddress.getByName("127.0.0.1");

        try {
            DatagramSocket udpSocket = new DatagramSocket();
            byte[] msg = "Hola mundo".getBytes();
            DatagramPacket packet = new DatagramPacket(msg, msg.length, ip, udpPort);

            udpSocket.send(packet);
            System.out.println("Mensaje enviado al puerto: " + udpPort);
            udpSocket.close();

            Socket tcpSocket = new Socket(ip, tcpPort);
            System.out.println("Conectado al puerto TCP: " + tcpPort);

            InputStream in = tcpSocket.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = in.read(buffer);

            if (bytesRead > 0) {
                String recibido = new String(buffer, 0, bytesRead);
                System.out.print("Mensaje recibido del servidor: ");
                System.out.println(recibido);
            }

            tcpSocket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}