import java.io.IOException;
import java.net.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        InetAddress ip = null;
        try {
            int port = 25565;
            //ip = InetAddress.getByName("192.168.128.81");
            DatagramSocket socket = new DatagramSocket(port);
            while(true){
                byte[] buffer = new byte[65156];
                DatagramPacket msgReceived = new DatagramPacket(buffer, buffer.length);
                socket.receive(msgReceived);
                System.out.println(new String(msgReceived.getData()));

                if(msgReceived.getData().toString().equals("Ping")) {
                    byte[] dato = "Pong".getBytes();
                    DatagramPacket msgSend = new DatagramPacket(dato, dato.length, msgReceived.getAddress(), msgReceived.getPort());
                    socket.send(msgSend);
                }
            }
        } catch (UnknownHostException | SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}