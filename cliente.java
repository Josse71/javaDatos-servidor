import java.io.IOException;
import java.net.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            DatagramSocket socketUDPCliente = new DatagramSocket(27000);
            InetAddress IPServer = InetAddress.getByName("127.0.0.1");
            int portServer = 25552;

            byte[] msgSend = "Hola, ¿qué tal?".getBytes();
            DatagramPacket packetSend = new DatagramPacket(msgSend, msgSend.length, IPServer, portServer);
            socketUDPCliente.send(packetSend);

            byte[] buffer = new byte[65156];
            DatagramPacket packetReceive = new DatagramPacket(buffer, buffer.length);
            socketUDPCliente.receive(packetReceive);

            String respuesta = new String(packetReceive.getData(), 0, packetReceive.getLength());
            System.out.println("Respuesta del servidor: " + respuesta);

            socketUDPCliente.close();

        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


// Devolver el número * 2.
// Simular que un paquete no se responde.
