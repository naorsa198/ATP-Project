package Client;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;


public class Client  implements IClientStrategy {
    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy strategy;
    private Maze maze;

    public Client(InetAddress serverIP, int serverPort, IClientStrategy strategy,Maze maze) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.strategy = strategy;
        this.maze=maze;
    }

    @Override
    public void clientStrategy(InputStream inFromServer, OutputStream outToServer,Maze maze) {
    }

    public void communicateWithServer() {
        try (Socket serverSocket = new Socket(serverIP, serverPort)) {
       //        System.out.println("connected to server - IP = " + serverIP + ", Port = " + serverPort);
            strategy.clientStrategy(serverSocket.getInputStream(), serverSocket.getOutputStream(),maze);
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}