import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 8010;

    public void start() {
        try (
            Socket socket = new Socket(HOST, PORT);
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true)
        ) {
            System.out.println("Connected to server at " + HOST + ":" + PORT);

            String serverMessage;
            while ((serverMessage = fromServer.readLine()) != null) {
                System.out.println("Server: " + serverMessage);
                if ("Goodbye from thread-pool server!".equals(serverMessage)) {
                    break;
                }
                String message = userInput.readLine();
                if (message == null) {
                    break;
                }
                toServer.println(message);
                if ("exit".equalsIgnoreCase(message)) {
                    break;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Client().start();
    }
}
