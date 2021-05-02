package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;
import java.util.Properties;
public class ServerStrategyGenerateMaze implements IServerStrategy{

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {

        try {
            //create the input stream
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            //create the output stream
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            int[]mazeDimensions=new int[2];

            if (mazeDimensions!= null && mazeDimensions.length==2) {

                mazeDimensions = (int[]) fromClient.readObject();
                int rows = mazeDimensions[0];
                int cols = mazeDimensions[1];
                MyMazeGenerator makeMaze = new MyMazeGenerator();
                Maze maze = makeMaze.generate(rows, cols);
                maze.toByteArray();


                ByteArrayOutputStream array = new ByteArrayOutputStream();
                MyCompressorOutputStream cmp = new MyCompressorOutputStream(array);
                cmp.write(maze.toByteArray());

                toClient.writeObject(array.toByteArray());
                cmp.flush();
                cmp.close();
            }

            //TODO ELSE ITS BAD INPUT


        }
         catch (Exception e){
            e.printStackTrace();
        }
    }
}
