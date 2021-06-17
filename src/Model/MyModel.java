package Model;

import Client.Client;
import Client.IClientStrategy;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;


public class MyModel extends Observable implements IModel{

    private Maze maze;
    private Solution solution;
    private int currentRowToken;
    private int currentColToken;
    private boolean tokenInEndPoint;
    private boolean KnockWall;


    public boolean isKnockWall() {
        return KnockWall;
    }

    public void setKnockWall(boolean knockWall) {
        KnockWall = knockWall;
    }




    public Maze getMaze() {
        return maze;
    }
    @Override
    public int getCurrentRow() {
        return currentRowToken;
    }
    @Override
    public int getCurrentCol() {
        return currentColToken;
    }


    public Solution getSolution() {
        return solution;
    }

    public MyModel (){
        maze=null;
        tokenInEndPoint=false;
        KnockWall=false;
    }

    @Override
    public void generateMaze(int row, int col) {
        tokenInEndPoint = false;
        CommunicateWithServer_MazeGenerating(row,col);
        currentRowToken = maze.getStartRow();
        currentColToken = maze.getStartCol();
    }

    private void CommunicateWithServer_MazeGenerating(int row, int col) {
        try {
            //initalize the server
            Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
            mazeGeneratingServer.start();
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{row, col};
                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[mazeDimensions[0]*mazeDimensions[1]]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        maze = new Maze(decompressedMaze);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
            mazeGeneratingServer.stop();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void solveMaze() {
        if (maze!=null)
            CommunicateWithServer_SolveSearchProblem();
    }

    private void CommunicateWithServer_SolveSearchProblem() {
        try {
            Server solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
            solveSearchProblemServer.start();
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        toServer.writeObject(maze); //send maze to server
                        toServer.flush();
                        solution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
            solveSearchProblemServer.stop();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveMaze(File fileMaze) {

        try {
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(fileMaze));
            out.write(maze.toByteArray());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
    @Override
    public void loadMaze(File file) {
        InputStream in = null;
        try {
            in = new MyDecompressorInputStream(new FileInputStream(file));
            byte [] savedMazeBytes = new byte[126000];
            in.read(savedMazeBytes);
            in.close();
            Maze loadedMaze = new Maze(savedMazeBytes);
            this.maze = loadedMaze;
            currentColToken = maze.getStartCol();
            currentRowToken = maze.getStartRow();
            setChanged();
            notifyObservers();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void moveToken(KeyCode movement) {
        switch (movement){
            case DIGIT1:
            case NUMPAD1:
                if (isInBoundsAndLegalCell(currentRowToken+1,currentColToken-1)&& isLegalAlachson(currentRowToken,currentColToken,currentRowToken+1,currentColToken-1)) {
                    currentColToken--;
                    currentRowToken++;
                }
                else if (isInBoundsAndLegalWall(currentRowToken+1,currentColToken-1))
                    KnockWall=true;
                break;
            case DIGIT2:
            case NUMPAD2:
            case DOWN:
                if (isInBoundsAndLegalCell(currentRowToken+1,currentColToken))
                    currentRowToken++;
                else if (isInBoundsAndLegalWall(currentRowToken+1,currentColToken))
                    KnockWall=true;
                break;
            case DIGIT3:
            case NUMPAD3:
                if (isInBoundsAndLegalCell(currentRowToken+1,currentColToken+1) && isLegalAlachson(currentRowToken,currentColToken,currentRowToken+1,currentColToken+1)) {
                    currentRowToken++;
                    currentColToken++;
                }
                else if (isInBoundsAndLegalWall(currentRowToken+1,currentColToken+1))
                    KnockWall=true;
                break;
            case DIGIT4:
            case NUMPAD4:
            case LEFT:
                if (isInBoundsAndLegalCell(currentRowToken,currentColToken-1))
                    currentColToken--;
                else if (isInBoundsAndLegalWall(currentRowToken,currentColToken-1))
                    KnockWall=true;
                break;
            case DIGIT6:
            case NUMPAD6:
            case RIGHT:
                if (isInBoundsAndLegalCell(currentRowToken,currentColToken+1))
                    currentColToken++;
                else if (isInBoundsAndLegalWall(currentRowToken,currentColToken+1))
                    KnockWall=true;
                break;
            case DIGIT7:
            case NUMPAD7:
                if (isInBoundsAndLegalCell(currentRowToken-1,currentColToken-1) && isLegalAlachson(currentRowToken,currentColToken,currentRowToken-1,currentColToken-1)) {
                    currentRowToken--;
                    currentColToken--;
                }
                else if (isInBoundsAndLegalWall(currentRowToken-1,currentColToken-1))
                    KnockWall=true;
                break;
            case DIGIT8:
            case NUMPAD8:
            case UP:
                if (isInBoundsAndLegalCell(currentRowToken-1,currentColToken))
                    currentRowToken--;
                else if (isInBoundsAndLegalWall(currentRowToken-1,currentColToken))
                    KnockWall=true;
                break;
            case DIGIT9:
            case NUMPAD9:
                if (isInBoundsAndLegalCell(currentRowToken-1,currentColToken+1)&& isLegalAlachson(currentRowToken,currentColToken,currentRowToken-1,currentColToken+1)) {
                    currentRowToken--;
                    currentColToken++;
                }
                else if (isInBoundsAndLegalWall(currentRowToken-1,currentColToken+1))
                    KnockWall=true;
                break;

        }

        if (currentRowToken==maze.getEndRow() && currentColToken==maze.getEndCol())
            tokenInEndPoint = true;
        setChanged();
        notifyObservers();


    }

    /**
     *
     * @param row - location
     * @param col - location
     * @return true only if (row,col) are in bounds of maze
     */
    private boolean isInBoundsAndLegalCell(int row, int col) {
        if (row >= maze.getMazeRows() || row < 0 || col < 0 || col >= maze.getMazeCols())
            return false;
        if (maze.getVal(row,col)==1)
            return false;
        return true;
    }


    private boolean isInBoundsAndLegalWall(int row, int col) {
        if (row >= maze.getMazeRows() || row < 0 || col < 0 || col >= maze.getMazeCols())
            return false;
        if (maze.getVal(row,col)==1) //wall
            return true;
        return false;
    }


    private boolean isLegalAlachson(int fromRow, int fromCol, int toRow, int toCol) {
        if (maze.getVal(fromRow,toCol)==0 || maze.getVal(toRow,fromCol)==0)
            return true;
        return false;
    }

    public boolean isTokenInEndPoint() {
        return tokenInEndPoint;
    }

    public void setTokenInEnd(boolean b) {
        tokenInEndPoint=b;
    }
}
