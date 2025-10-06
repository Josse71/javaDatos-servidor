import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int port = 26404;

        try {
            ServerSocket socketServer = new ServerSocket(port);
            DataInputStream in;
            DataOutputStream out;

            while (true) {
                Socket socketClient = socketServer.accept();
                System.out.println("Conectado");
                in = new DataInputStream(new BufferedInputStream(socketClient.getInputStream()));
                String mensaje = "";

                try{
                    mensaje = in.readUTF();
                    System.out.println(mensaje);
                }catch(EOFException e){
                    System.out.println("El cliente cerró la conexión.");
                    throw new EOFException();
                }

                socketClient.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}