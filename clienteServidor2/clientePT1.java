import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
        Scanner entrada = new Scanner(System.in);
        int udpPort = 2500; //Puerto UDP inicial.
        InetAddress ip = InetAddress.getByName("127.0.0.1"); //IP local.

        try {
            DatagramSocket udpSocket = new DatagramSocket(); //Preparamos socket UDP.

            System.out.print("Ingresa el puerto (int): ");
            int tcpPort = entrada.nextInt();
            //Pedimos el puerto y verificamos que esté en el rango permitido.
            while(tcpPort <= 0 || tcpPort > 65535) {
                System.out.println("El rango del puerto es (1 - 65535).");
                System.out.print("Ingresa un puerto válido: ");
                tcpPort = entrada.nextInt();
            }

            entrada.nextLine(); //Vaciamos el buffer.

            System.out.print("Ingresa tu nombre: ");
            String nombre = entrada.nextLine();
            //Fecha en la que se envía el mensaje al servidor.
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String mensaje = tcpPort + "," + nombre + "," + fecha; //Enviamos los 3 paquetes a la vez separados por ',' para luego identificarlos.
            byte[] msg = mensaje.getBytes();

            DatagramPacket packet = new DatagramPacket(msg, msg.length, ip, udpPort);
            udpSocket.send(packet); //Enviamos el paquete.

            ServerSocket serverTCP = new ServerSocket(tcpPort); //Iniciamos el servidor TCP.
            Socket conexion = serverTCP.accept();
            System.out.println("Conectado al puerto TCP: " + tcpPort);
            //Usamos printwriter para escribir los datos.
            PrintWriter out = new PrintWriter(conexion.getOutputStream(), true);
            int n = 0; //Entero que nos ayuda a controlar el bucle.

            while(n != -1){
                //Enviamos el mensaje ingresado por el usuario, si escribe 'salir' salimos del bucle.
                System.out.print("Escribe un mensaje a enviar o 'salir' para acabar el programa: ");
                String envio = entrada.nextLine().toLowerCase(); //Usamos 'tolowercase' para evitar comparar con mayúsculas.
                out.println(envio);

                if(envio.equals("salir")){
                    n = -1;
                }
            }
            //Cerramos todos los procesos e indicamos por consola que la conexión se ha cerrado.
            out.close();
            conexion.close();
            serverTCP.close();
            System.out.println("Conexión TCP cerrada correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}