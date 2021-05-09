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
    private static int count = 0;

    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        this.tempDirectoryPath = System.getProperty("java.io.tmpdir");
        try {
         //   System.out.println("in the try:: ");

            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            //create the output stream
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();

            Maze clientMaze = (Maze) fromClient.readObject();
            String existMaze = MazeExistInDirctory(clientMaze);

       //     System.out.println("herer is the name" + existMaze);
            // if maze already solved
            if (!existMaze.equals("false")) {
       //         System.out.println("i am exist @@@@@@@@@@@");
                //      String index= ReturnSolIndex(existMaze);
                Solution solution = ReturnExistSolution(existMaze);
                toClient.writeObject(solution);
            }
            // if not need to solve him
            else {

                ASearchingAlgorithm solver = null;
                solver= SolverAlgorithem();
              //  System.out.println("solving maze..........");
            //    BestFirstSearch solver = new BestFirstSearch();
                SearchableMaze searchableMaze = new SearchableMaze(clientMaze);

                Solution solClient = solver.solve(searchableMaze);
           //     System.out.println("writing to cliet............");
                toClient.writeObject(solClient);

                addSolutionAndMaze(solClient, clientMaze);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * @param solution
     * @param maze
     * add solution to directory
     * @throws IOException
     */
    private void addSolutionAndMaze(Solution solution, Maze maze) throws IOException {
    //    System.out.println("adding solution...........");
        String name = maze.toString();
        String mazename = "name-" + name + maze.hashCode();
//        String solName = "Solution- " + name;
        String path = tempDirectoryPath + mazename;

        int index = getFileId();
        File solFile = new File(path + "_" + index);
        System.out.println(solFile);


        try {
            ObjectOutputStream outobj = new ObjectOutputStream(new FileOutputStream(solFile));
            outobj.writeObject(solution);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param Mazefile return the name of the file without the index
     * @return
     */
    private String NameCheckMaze(String Mazefile) {
        int kav = Mazefile.indexOf("_");
        String name = "";
        if (kav != -1)
            name = Mazefile.substring(0, kav);
        return name;
    }


    /**
     * @param maze maze to solve
     * @return the name of the file if this maze already been solved
     */
    private String MazeExistInDirctory(Maze maze) {
        String name = "name-" + maze.toString() + maze.hashCode();
    //    System.out.println("My NAME IS @@@" + name);
        File dirctory = new File(tempDirectoryPath);

        File[] files = dirctory.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (name.equals(NameCheckMaze(files[i].getName()))) {
                return files[i].getName();
            }
        }
        return "false";
    }




    /**
     * @param path name of file
     * @return true if he exsit
     */
    private Solution ReturnExistSolution(String path) {
//        String name = maze.toString();
        // String sol = "name-" + maze.toString()+maze.hashCode();
        String mypath = tempDirectoryPath + path;

        try {
            File file = new File(mypath);
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

    // make id synchronized to each file

    /**
     * @return index for name of file
     */
    private static synchronized int getFileId() {
        int serialID = count;
        count++;
        return serialID;
    }


    /**
     * @return which solver to use
     */
    private ASearchingAlgorithm SolverAlgorithem() {
        //open the properties file:
        Configurations cf= Configurations.getInstance();
        Properties properties;
        properties=cf.getProperties();
        String algo_prop = properties.getProperty("mazeSearchingAlgorithm");

        if(algo_prop.equals("BestFirstSearch"))
            return new BestFirstSearch();

        if(algo_prop.equals(BestFirstSearch.class))
            return new BreadthFirstSearch();

        if(algo_prop.equals(DepthFirstSearch.class))
            return new DepthFirstSearch();

        return new BestFirstSearch();
    }


}