package co.edu.escuelaing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) throws IOException {
        System.out.println("¡El cliente esta corriendo!");
        Socket clientSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            clientSocket = new Socket("127.0.0.1", 3500);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("No se sabe sobre el host");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("No se puede conectar a: localhost.");
            System.exit(1);
        }
        BufferedReader stdIn = new BufferedReader(
                new InputStreamReader(System.in));
        String userInput, serverResponse;
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            serverResponse = in.readLine();
            System.out.println("echo: " + serverResponse);
            if (serverResponse.equals("respuesta del servidor: apagar") ){
                break;
            }
        }
        out.close();
        in.close();
        stdIn.close();
        clientSocket.close();
    }

}
