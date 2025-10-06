import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws UnknownHostException {
        int port = 26404;
        InetAddress ip = InetAddress.getByName("192.168.128.137");
        System.out.println("ip creada");
        try{
            Socket socket = new Socket(ip, port);
            System.out.println("conectado");
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            String msg = in.readLine();
            out.writeUTF(msg);

        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}