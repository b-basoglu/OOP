import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Maze extends UnicastRemoteObject implements IMaze, Serializable  {
    private int height;
    private int width;
    private int agentID=0;
    private Map<Position,MazeObject> mazeMap = new HashMap<Position,MazeObject>();

    protected Maze() throws RemoteException {
    }


    @Override
    public void create(int height, int width) throws RemoteException {
        this.height = height;
        this.width = width;
    }

    @Override
    public MazeObject getObject(Position position) throws RemoteException {

        return mazeMap.get(position);
    }

    @Override
    public boolean createObject(Position position, MazeObjectType type) throws RemoteException {
        if(!checkEdges(position.getX(),position.getY())){
            return false;
        }
        if(type == MazeObjectType.AGENT){
            Agent agent = new Agent(position,agentID++);
            return mazeMap.putIfAbsent(position, agent) == null;

        }else{
            MazeObject mazeObject = new MazeObject(position,type);
            return mazeMap.putIfAbsent(position, mazeObject) == null;
        }
    }

    @Override
    public boolean deleteObject(Position position) throws RemoteException {
        if(mazeMap.containsKey(position)){
            mazeMap.remove(position);
            return true;
        }
        return false;
    }

    @Override
    public Agent[] getAgents() throws RemoteException {
        ArrayList<Agent> agents = new ArrayList<Agent>();

        for (Map.Entry<Position, MazeObject> pair: mazeMap.entrySet()) {
            if(pair.getValue().getType() == MazeObjectType.AGENT){
                agents.add((Agent)pair.getValue());
            }
        }

        if(agents.size()>0){
            Object[] arr = agents.toArray();
            Agent[] agentArray = new Agent[arr.length];
            for(int i =0 ; i< arr.length;i++){
                agentArray[i] = (Agent) arr[i];
            }
            return agentArray;
        }
        return new Agent[0];
    }
    private boolean checkEdges(int X,int Y){
        return Y < height && X < width && X>=0 && Y>=0;
    }
    private Position findAgentPosition(Agent[] agents,int id){
        for(Agent agent:agents){
            if(agent.getId()==id){
                return agent.getPosition();
            }
        }
        return null;
    }
    private void incrementGold(Agent agent){
        agent.setCollectedGold(agent.getCollectedGold()+1);
    }
    @Override
    public boolean moveAgent(int id, Position position) throws RemoteException {
        MazeObject mazeObject = mazeMap.get(position);
        Position positionGiven = findAgentPosition(getAgents(),id);
        if(positionGiven == null){
            return false;
        }
        if(positionGiven.distance(position)!=1){
            return false;
        }
        if(!checkEdges(position.getX(),position.getY())){
            return false;
        }
        if(mazeObject == null && checkEdges(position.getX(),position.getY())){
            mazeMap.putIfAbsent(position,getObject(positionGiven));
            mazeMap.get(position).setPosition(position);
            mazeMap.remove(positionGiven);
            return true;
        }else if(mazeObject == null){
            return false;
        }else if(mazeObject.getType()==MazeObjectType.HOLE){
            deleteObject(positionGiven);
            return true;
        }else if(mazeObject.getType()==MazeObjectType.WALL || mazeObject.getType() == MazeObjectType.AGENT){
            return false;
        }else if(mazeObject.getType()== MazeObjectType.GOLD){
            incrementGold((Agent)getObject(positionGiven));
            mazeMap.remove(position);
            mazeMap.putIfAbsent(position,getObject(positionGiven));
            mazeMap.get(position).setPosition(position);
            mazeMap.remove(positionGiven);
            return true;
        }
        return false;
    }

    @Override
    public String print() throws RemoteException {
        StringBuilder s = new StringBuilder();
        for(int j=0;j<height+2;j++){
            for(int i =0 ;i<width+2;i++){

                if((i==0 && j==0) || (i==width+1 && j==0) || (i==0 && j==height+1) || ( (i==width+1) && (j ==height+1)) ){
                    s.append("+");
                }else if(i==0 || i ==width+1){
                    s.append("|");
                }else if(j==0 || j == height+1){
                    s.append("-");
                }else{

                    if(getObject(new Position(i-1,j-1)) == null){
                        s.append(" ");
                    }else if(getObject(new Position(i-1,j-1)).getType()==MazeObjectType.WALL){
                        s.append("X");
                    }else if(getObject(new Position(i-1,j-1)).getType()==MazeObjectType.HOLE){
                        s.append("O");
                    }else if(getObject(new Position(i-1,j-1)).getType()==MazeObjectType.AGENT){
                        s.append("A");
                    }else if(getObject(new Position(i-1,j-1)).getType()==MazeObjectType.GOLD){
                        s.append("G");
                    }else{
                        s.append(" ");
                    }
                }
            }
            s.append("\n");
        }
        return s.toString();
    }
}
