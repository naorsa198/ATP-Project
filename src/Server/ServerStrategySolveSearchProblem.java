package Server;
import java.io.*;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.*;
import java.util.Properties;




public class ServerStrategySolveSearchProblem implements IServerStrategy {

    //temporary directory
    String tempDirectoryPath;
    static  AtomicInteger count =new AtomicInteger(0);

    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        this.tempDirectoryPath = System.getProperty("java.io.tmpdir");
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            //create the output stream
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();

            Maze clientMaze = (Maze) fromClient.readObject();

            String existMaze = MazeExistInDirctory(clientMaze);

            // if maze already solved
            if(!existMaze.equals("false")){
                String index= ReturnSolIndex(clientMaze);
                Solution solution=ReturnExistSolution(clientMaze,index);
                toClient.writeObject(solution);
            }
            // if not need to solve him
            else {


                BestFirstSearch solver = new BestFirstSearch();
                SearchableMaze searchableMaze = new SearchableMaze(clientMaze);

                Solution solClient = solver.solve(searchableMaze);

                toClient.writeObject(solClient);

                addSolutionAndMaze(solClient,clientMaze);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void addSolutionAndMaze(Solution solution, Maze maze) throws IOException {
        String name = maze.toString();
        String mazename = "name-" + name;
        String solName = "Solution- " + name;
        String path = tempDirectoryPath + mazename;

        int index=count.get();
        count.incrementAndGet();
        File mazefile = new File(path + "_"+index);


        File solFile = new File(tempDirectoryPath, solName + "_" + index);
        try {
            OutputStream out = new MyCompressorOutputStream(new FileOutputStream(mazefile));
            out.write(maze.toByteArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ObjectOutputStream outobj = new ObjectOutputStream(new FileOutputStream(solFile));
            outobj.writeObject(solution);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String NameCheckMaze(String Mazefile){
        int kav= Mazefile.indexOf("_");
        String name=Mazefile.substring(0,kav);
        return name;
    }



    private String MazeExistInDirctory(Maze maze) {
        String name = "name-" + maze.toString();
        File dirctory = new File(tempDirectoryPath);

        File[] files = dirctory.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().equals(NameCheckMaze(name))) {
                return files[i].getName();
            }
        }
        return "false";
    }


    private String ReturnSolIndex(Maze maze){
        String name= maze.toString();
        int x=name.indexOf("_");
        String orgName= name.substring(x,name.length());
        return  orgName;
    }

    private Solution ReturnExistSolution(Maze maze,String ins) {
        String name = maze.toString();
        String sol = "Solution- " + name + ins;
        try {
            File file = new File(tempDirectoryPath, name);
            InputStream in = new FileInputStream(file);
            ObjectInputStream objs = new ObjectInputStream(in);
            Solution solution = (Solution) objs.readObject();
            objs.close();
            return solution;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}