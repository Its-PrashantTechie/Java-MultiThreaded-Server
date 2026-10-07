import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int PORT = 8010;
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("ThreadPool server is listening on port: " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                executor.submit(new ClientHandler(clientSocket));
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
                toClient.println("Welcome to the ThreadPool server. Type 'exit' to quit.");

                String message;
                while ((message = fromClient.readLine()) != null) {
                    System.out.println("Client says: " + message);
                    if ("exit".equalsIgnoreCase(message)) {
                        toClient.println("Goodbye from thread-pool server!");
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
