import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            DatagramSocket socketUDPCliente = new DatagramSocket(27000);
            InetAddress IPServer = InetAddress.getByName("127.0.0.1");
            int portServer = 25552;

            Scanner lector = new Scanner(System.in);
            int n = 0;

            while(n != -1){
                try {
                    n = lector.nextInt();
                    byte[] msgSend = String.valueOf(n).getBytes();
                    DatagramPacket packetSend = new DatagramPacket(msgSend, msgSend.length, IPServer, portServer);
                    socketUDPCliente.send(packetSend);

                    byte[] buffer = new byte[65156];
                    DatagramPacket packetReceive = new DatagramPacket(buffer, buffer.length);
                    socketUDPCliente.receive(packetReceive);

                    String respuesta = new String(packetReceive.getData(), 0, packetReceive.getLength());
                    System.out.println("Respuesta del servidor: " + respuesta);


                }catch (EOFException e){
                    System.out.println("Cliente cerrado.");
                    break;
                }
            }
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