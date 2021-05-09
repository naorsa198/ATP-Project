package Server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
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

    }

    // Main threade
    public void start() {
        new Thread(() -> {
            runServer();
        }).start();
    }



    // opening socket and staring thread Pool
    private void runServer(){
        try {
            int size;
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningInterval);
         //   System.out.println("Starting server at port = " + port);

            // get properties for size of pool
            Configurations cf =Configurations.getInstance();
            Properties properties= cf.getProperties();
            String stNum;
            stNum= properties.getProperty("threadPoolSize");
            size=Integer.parseInt(stNum);

            // initialize thread pool, run when server start
            this.threadPool = Executors.newFixedThreadPool(size);

            while (!stop) {
                try {
                    //get client
                    Socket clientSocket = serverSocket.accept();
           //         System.out.println("Client accepted: " + clientSocket.toString());
                    //create the thread
                    runable run =new runable(serverStrategy,clientSocket);
                    threadPool.execute(run);

                    // Insert the new task into the thread pool:

                } catch (SocketTimeoutException e){
          //          System.out.println("Socket timeout");
                }
            }
        } catch (IOException e) {
     //       System.out.println("connect refused______");
            //LOG.error("IOException", e);
        }

    }


    public void stop(){
     //   System.out.println("Stopping server...");
        stop = true;
    }


    // creating class for run the func in thread pool for each client

    private class runable implements Runnable{
        private IServerStrategy serverStrategy;
        private Socket clientSocket;

        public runable(IServerStrategy serverStrategy,Socket clientSocket){
            this.serverStrategy=serverStrategy;
            this.clientSocket=clientSocket;
        }


        @Override
        public void run() {
            try {
                serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
          //      System.out.println("Done handling client: " + clientSocket.toString());
                clientSocket.close();
            } catch (IOException e){
                //  LOG.error("IOException", e);
            }
        }


    }


}