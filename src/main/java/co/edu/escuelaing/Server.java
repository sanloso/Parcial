package co.edu.escuelaing;

import co.edu.escuelaing.annotations.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Server {

    private static final Server _instance = new Server();

    public static Server get_instance(){
        return _instance;
    }

    private Server(){}

    public void start( String[] args ) throws IOException, URISyntaxException {

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
            connection(clientSocket);
        }
        serverSocket.close();
    }

    public void connection (Socket clientSocket) throws IOException, URISyntaxException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));
        String inputLine, outputLine;
        ArrayList<String> request = new ArrayList<String>();
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Recivido: " + inputLine);
            request.add(inputLine);
            if(!in.ready()){
                break;
            }
        }

        String uriStr = request.get(0).split(" ") [1];
        URI resourceUri = new URI (uriStr);
        System.out.println("URI PATH: " + resourceUri.getPath());
        System.out.println("URI QUERY: " + resourceUri.getQuery());

        if(resourceUri.toString().startsWith("/clima")){
            outputLine = getComponentResource(resourceUri);
            out.println(outputLine);
        }else{
            outputLine = getHTMLResourse(resourceUri);
            out.println(outputLine);
        }
        out.close();
        in.close();
        clientSocket.close();
    }

    private String getHTMLResourse(URI resourceUri) {
        Path file = Paths.get("public_html" + resourceUri.getPath());
        String response = "";
        Charset charset = Charset.forName("UTF-8");
        try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
            response = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: text/html\r\n"
                    + "\r\n";
            String line = null;
            while ((line = reader.readLine()) != null){
                System.out.println(line);
                response = response + line;
            }
        }catch(IOException x){
            System.err.format("IOException: %s%n", x);
            response = default404HTMLResponse();
        }
        return response;
    }

    private String getComponentResource(URI resourceUri) {
        String response = default404HTMLResponse();
        try{
            String classPath = resourceUri.getPath().toString().replace("/clima/", "");
            Class clase = Class.forName(classPath);
            for (Method m : clase.getDeclaredMethods()){
                if(m.isAnnotationPresent(Service.class)){
                    response = m.invoke(null).toString();
                    response = "HTTP/1.1 200 OK\r\n"
                            + "Content-Type: text/html\r\n"
                            + "\r\n" + response;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String default404HTMLResponse() {
        String outputLine = "HTTP/1.1 400 Not Found\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<title>404 Not Found</title>"
                + "<meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"
                + "</head>"
                + "<body>"
                + "<div><h1>Error 404</h1></div>"
                + "</body>"
                + "</html>";
        return outputLine;
    }

    public static void main(String [] args) throws IOException, URISyntaxException{
        Server.get_instance().start(args);
    }
}