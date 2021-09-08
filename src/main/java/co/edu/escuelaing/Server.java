package co.edu.escuelaing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main( String[] args ) throws IOException {
        System.out.println("¡El servidor esta corriendo!");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket (3500);
        }catch (IOException e){
            System.err.println("No puede escuchar el puerto: 3500.");
            System.exit(1);
        }
        boolean running = true;
        while(running){
            Socket clientSocket = null;
            try{
                clientSocket = serverSocket.accept();
            }catch (IOException e){
                System.err.println("Fallo");
                System.exit(1);
            }
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println( "mensaje recibido desde cliente: " + inputLine);
                outputLine = "respuesta del servidor: " + inputLine;
                out.println(outputLine);
                if(outputLine.equals("respuesta del servidor: apagar")){
                    break;
                }
            }
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }
}