import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress ip = InetAddress.getByName("127.0.0.1");
        int port = 27010;

        try (Socket socket = new Socket(ip, port)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            out.println(id);

            String respuesta = in.readLine();
            System.out.println("Respuesta del servidor: " + respuesta);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}