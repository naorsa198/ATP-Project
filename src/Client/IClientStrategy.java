package Client;
import algorithms.mazeGenerators.Maze;

import algorithms.mazeGenerators.Maze;

import java.io.InputStream;
import java.io.OutputStream;

public interface IClientStrategy {

    void clientStrategy(InputStream inFromServer, OutputStream outToServer);
}
