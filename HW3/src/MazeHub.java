import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class MazeHub extends UnicastRemoteObject implements IMazeHub , Serializable {
    ArrayList<Maze> mazes = new ArrayList<Maze>();

    public MazeHub() throws RemoteException {
    }

    @Override
    public void createMaze(int width, int height) throws RemoteException {
        if(width<1 || height<1){
            return;
        }
        Maze maze = new Maze();
        maze.create(height,width);
        mazes.add(maze);
    }

    @Override
    public IMaze getMaze(int index) throws RemoteException {
        if(index>=mazes.size() || index < 0){
            return null;
        }
        return mazes.get(index);
    }

    @Override
    public boolean removeMaze(int index) throws RemoteException {
        if(index>=mazes.size() || index <0 ){
            return false;
        }
        mazes.remove(index);
        return true;
    }
}
