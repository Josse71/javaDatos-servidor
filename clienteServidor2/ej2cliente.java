import java.io.IOException;
import java.net.*;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            int port = 25565;
            DatagramSocket socketUDPClient = new DatagramSocket(port);
            InetAddress IPServer = InetAddress.getByName("192.168.128.137");

            while(true){
                Scanner entrada = new Scanner(System.in);
                String msg = entrada.nextLine();

                byte[] buf = msg.getBytes();
                DatagramPacket packetSend = new DatagramPacket(buf, buf.length, IPServer, port);
                socketUDPClient.send(packetSend);

                byte[] bufReceive = new byte[65156];
                DatagramPacket packetReceive = new DatagramPacket(bufReceive, bufReceive.length);
                socketUDPClient.receive(packetReceive);

                System.out.println(new String(packetReceive.getData(), 0, packetReceive.getLength()));

                socketUDPClient.close();
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}