import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor{
    private static class ManejadorCliente extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ManejadorCliente(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                synchronized (clientes) {
                    clientes.add(out);
                }

                String mensaje;
                while ((mensaje = in.readLine()) != null) {
                    System.out.println("Mensaje recibido: " + mensaje);

                    synchronized (clientes) {
                        for (PrintWriter cliente : clientes) {
                            cliente.println(mensaje);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Error con el cliente: " + e.getMessage());
            } finally {

                synchronized (clientes) {
                    clientes.remove(out);
                }

                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    } 
    private static final int PORT = 3000; 

    private static Set<PrintWriter> clientes = new HashSet<>();
    public static void main(String[] args) {
        System.out.println("Servidor iniciado en el puerto " + PORT);
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {

                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress().getHostAddress());

                new ManejadorCliente(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int getPort() {
        return PORT;
    }
    public static Set<PrintWriter> getClientes() {
        return clientes;
    }
    public static void setClientes(Set<PrintWriter> clientes) {
        Servidor.clientes = clientes;
    }
    @Override
    public String toString() {
        return "Servidor []";
    }
}

import java.util.Scanner;

public class OnMessageRecive {
    private static final String SERVER_ADDRESS = "127.0.0.1"; 
    private static final int SERVER_PORT = 3000;             

    public static void main(String[] args) {
        System.out.println("Conectándose al servidor...");
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(() -> {
                try {
                    String mensaje;
                    while ((mensaje = in.readLine()) != null) {
                        System.out.println(mensaje);
                    }
                } catch (IOException e) {
                    System.out.println("Conexión cerrada.");
                }
            }).start();

            Scanner scanner = new Scanner(System.in);
            while (true) {
                String mensaje = scanner.nextLine();
                if (mensaje.equalsIgnoreCase(":quit")) {
                    break;
                }
                out.println(mensaje);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
