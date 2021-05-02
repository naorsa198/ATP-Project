package Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
        private int port;
        private int listeningInterval;
        private IServerStrategy serverStrategy;
        private volatile boolean stop;
        private ExecutorService threadPool; // Thread pool

    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
        this.threadPool = Executors.newCachedThreadPool();

    }

    public void start(){
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningInterval);
            System.out.println("Starting server at port = " + port);

            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client accepted: " + clientSocket.toString());

                    // Insert the new task into the thread pool:
                    threadPool.submit(() -> {
                        handleClient(clientSocket);
                    });
                } catch (SocketTimeoutException e){
                    System.out.println("Socket timeout");
                }
            }
        } catch (IOException e) {
            //LOG.error("IOException", e);
        }

    }

    private void handleClient(Socket clientSocket) {
        try {
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            System.out.println("Done handling client: " + clientSocket.toString());
            clientSocket.close();
        } catch (IOException e){
          //  LOG.error("IOException", e);
        }
    }

    public void stop(){
        System.out.println("Stopping server...");
        stop = true;
    }
}