import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 8010;

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port: " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                System.out.println("Connected to " + clientSocket.getRemoteSocketAddress());
                toClient.println("Welcome! Type a message. Type 'exit' to quit.");

                String message;
                while ((message = fromClient.readLine()) != null) {
                    System.out.println("Client says: " + message);
                    if ("exit".equalsIgnoreCase(message)) {
                        toClient.println("Goodbye from server!");
                        break;
                    }
                    toClient.println("Server received: " + message);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException ignored) {
                    // Ignore close errors
                }
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}