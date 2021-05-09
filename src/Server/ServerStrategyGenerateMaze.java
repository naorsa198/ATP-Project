package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;
import algorithms.search.ASearchingAlgorithm;
import algorithms.search.BestFirstSearch;
import algorithms.search.BreadthFirstSearch;
import algorithms.search.DepthFirstSearch;

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
                AMazeGenerator makeMaze = null;
                makeMaze = MazeGenartoreProp();
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


    private AMazeGenerator MazeGenartoreProp() {
        //open the properties file:
        Configurations cf= Configurations.getInstance();
        Properties properties;
        properties=cf.getProperties();
        String maze_prop = properties.getProperty("mazeGeneratingAlgorithm");

        if(maze_prop.equals(MyMazeGenerator.class))
            return new MyMazeGenerator();

        if(maze_prop.equals(SimpleMazeGenerator.class))
            return new SimpleMazeGenerator();

        if(maze_prop.equals(EmptyMazeGenerator.class))
            return new EmptyMazeGenerator();

        return new MyMazeGenerator();
    }


}
