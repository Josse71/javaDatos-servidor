import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int udpPort = 2500;
        int tcpPort = udpPort + 1;

        try {
            DatagramSocket udpSocket = new DatagramSocket(udpPort);
            System.out.println("Puerto UDP: " + udpPort);

            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            udpSocket.receive(packet);
            System.out.println("Mensaje recibido de: " + packet.getAddress());

            udpSocket.close();

            byte[] data = new byte[]{
                    0x4e, 0x65, 0x76, 0x65, 0x72, 0x20, 0x67, 0x6f,
                    0x6e, 0x6e, 0x61, 0x20, 0x67, 0x69, 0x76, 0x65,
                    0x20, 0x79, 0x6f, 0x75, 0x20, 0x75, 0x70
            };

            ServerSocket tcpServer = new ServerSocket(tcpPort);
            System.out.println("Puerto TCP: " + tcpPort);

            Socket client = tcpServer.accept();
            System.out.println("TCP cliente: " + client.getLocalAddress());

            OutputStream out = client.getOutputStream();
            out.write(data);
            out.flush();

            System.out.println("Datos enviados correctamente.");

            client.close();
            tcpServer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}