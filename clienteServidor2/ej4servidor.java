import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int port = 27010;
        String logFile = "log.txt";

        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    String identificador = in.readLine();

                    out.println("OK");

                    try (FileWriter fw = new FileWriter(logFile, true);
                         BufferedWriter bw = new BufferedWriter(fw);
                         PrintWriter log = new PrintWriter(bw)) {

                        String registro = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        log.println(registro + " | Identificador: " + identificador);
                    }
                } catch (IOException e) {
                    System.out.println("Error en la conexión: " + e.getMessage());
                }
            }

        } catch (IOException e) {
            System.out.println("No se puede abrir el puerto: " + e.getMessage());
        }
    }
}