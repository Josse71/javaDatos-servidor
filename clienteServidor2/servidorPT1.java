import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int udpPort = 2500;  //Puerto UDP dado por el enunciado.

        try {
            DatagramSocket udpSocket = new DatagramSocket(udpPort);

            byte[] buffer = new byte[65156];

            while(true){
                try{
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length); //Esperamos mensaje del cliente
                    udpSocket.receive(packet);

                    String mensaje = new String(packet.getData(), 0, packet.getLength()); //Recibimos y almacenamos el mensaje en la variable 'mensaje'.
                    String[] mensajes = mensaje.split(","); //Al haber enviado más de un paquete lo separamos por ',' y guardamos los mensajes en un array de String.
                    int tcpPort = Integer.parseInt(mensajes[0].trim()); //Parseamos y guardamos cada mensaje donde corresponde.
                    String nombreCliente = mensajes[1].trim();
                    String fechaCliente = mensajes[2].trim();

                    String fechaRecepcion = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")); //Fecha del sistema en la que recibe el mensaje el servidor del cliente.

                    System.out.println("Conectado al puerto UDP: " + udpPort); //Mostramos los datos recibidos.
                    System.out.println("Cliente: " + nombreCliente);
                    System.out.println("Fecha de envío del cliente: " + fechaCliente);
                    System.out.println("Fecha de recibo: " + fechaRecepcion);
                    System.out.println("Puerto TCP a conectar: " + tcpPort);

                    Socket tcpSocket = new Socket(packet.getAddress(), tcpPort); //Conexión TCP al cliente mediante el puerto que recibimos.
                    BufferedReader entrada = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));

                    String linea;
                    System.out.println("Recibiendo datos del cliente...");
                    while ((linea = entrada.readLine()) != null) { //Bucle que se ejecuta mientras se reciba un dato desde el cliente.
                        System.out.println("Dato recibido: " + linea);
                    }

                    System.out.println("El cliente ha cerrado la conexión TCP.");
                    tcpSocket.close(); //Cerramos la conexión TCP.

                } catch (Exception e){

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}