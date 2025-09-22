import java.io.IOException;
import java.net.*;

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

                byte[] rpta = "Muy bien".getBytes();

                DatagramPacket sendPacket = new DatagramPacket(rpta, rpta.length, msgReceived.getAddress(), msgReceived.getPort());

                socketUDPServer.send(sendPacket);
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
