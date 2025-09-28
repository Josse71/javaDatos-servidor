import java.io.IOException;
import java.net.*;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
            DatagramSocket socketUDPServer = new DatagramSocket(25552);

            while(true){
                byte[] buffer = new byte[65156];
                DatagramPacket msgReceived =  new DatagramPacket(buffer, buffer.length);
                socketUDPServer.receive(msgReceived);

                System.out.println("Host: " + msgReceived.getAddress());
                System.out.println("Port: " + msgReceived.getPort());
                System.out.println("Data: " +  new String(msgReceived.getData(), 0, msgReceived.getLength()));

                Random rnd = new Random();
                int random = rnd.nextInt(4) + 1;
                System.out.println(random);
                if(random != 2){
                    String mensaje = new String(msgReceived.getData(), 0, msgReceived.getLength());
                    int mensajeInt = Integer.parseInt(mensaje) * 2;
                    byte[] rpta = String.valueOf( mensajeInt ).getBytes();

                    DatagramPacket sendPacket = new DatagramPacket(rpta, rpta.length, msgReceived.getAddress(), msgReceived.getPort());

                    socketUDPServer.send(sendPacket);
                }
                else{
                    System.out.println("El paquete no se pudo enviar.");
                }
            }

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
