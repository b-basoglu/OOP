import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;



public class RMIClient {
    public static void printAgents(Agent[] agents){
        Arrays.sort(agents);
        for(Agent agent:agents){
            System.out.print("Agent"+agent.getId()+" at "+agent.getPosition()+". Gold collected: "+agent.getCollectedGold()+".\n");
        }
    }
    public static void main(String[] args) throws RemoteException, NotBoundException, MalformedURLException {
        IMazeHub mazeHub = (IMazeHub) Naming.lookup("rmi://127.0.0.1:8000/bnymn");
        Scanner scanner = new Scanner(System.in);
        ParsedInput parsedInput = null;
        IMaze selectedMaze = null;
        String input;
        while( true ) {
            input = scanner.nextLine();
            try {
                parsedInput = ParsedInput.parse(input);
            }
            catch (Exception ex) {
                parsedInput = null;
            }
            if ( parsedInput == null ) {
                System.out.println("Wrong input format. Try again.");
                continue;
            }
            switch(parsedInput.getType()) {
                case CREATE_MAZE:
                    mazeHub.createMaze((int)parsedInput.getArgs()[0],(int)parsedInput.getArgs()[1]);
                    break;
                case DELETE_MAZE:
                    boolean removal = mazeHub.removeMaze((int)parsedInput.getArgs()[0]);
                    selectedMaze = null;
                    if(removal){
                        System.out.println("Operation Success.");
                    }else{
                        System.out.println("Operation Failed.");
                    }
                    break;
                case SELECT_MAZE:
                    selectedMaze = mazeHub.getMaze((int)parsedInput.getArgs()[0]);
                    if(selectedMaze==null){
                        System.out.println("Operation Failed.");
                    }else{
                        System.out.println("Operation Success.");
                    }
                    break;
                case PRINT_MAZE:
                    if(selectedMaze==null){
                        System.out.println("Operation Failed.");
                    }else{
                        System.out.print(selectedMaze.print());
                        //System.out.println("Operation Success.");
                    }

                    break;
                case CREATE_OBJECT:
                    if(selectedMaze==null){
                        System.out.println("Operation Failed.");
                    }else{
                        boolean created = selectedMaze.createObject(new Position((int)parsedInput.getArgs()[0],(int)parsedInput.getArgs()[1]),(MazeObjectType) parsedInput.getArgs()[2]);
                        if(created){

                            System.out.println("Operation Success.");
                        }else{
                            System.out.println("Operation Failed.");
                        }
                    }
                    break;
                case DELETE_OBJECT:
                    if(selectedMaze==null){
                        System.out.println("Operation Failed.");
                    }else{
                        boolean deleted = selectedMaze.deleteObject(new Position((int)parsedInput.getArgs()[0],(int)parsedInput.getArgs()[1]));
                        if(deleted){
                            System.out.println("Operation Success.");
                        }else{
                            System.out.println("Operation Failed.");
                        }
                    }
                    break;
                case LIST_AGENTS:
                    if(selectedMaze==null){
                        System.out.println("Operation Failed.");
                    }else{
                        printAgents(selectedMaze.getAgents());
                    }

                    break;
                case MOVE_AGENT:
                    if(selectedMaze==null){
                        System.out.println("Operation Failed.");
                    }else{
                        boolean moved = selectedMaze.moveAgent((int)parsedInput.getArgs()[0],new Position((int)parsedInput.getArgs()[1],(int)parsedInput.getArgs()[2]));
                        if(moved){
                            System.out.println("Operation Success.");
                        }else{
                            System.out.println("Operation Failed.");
                        }
                    }
                    break;
                case QUIT:
                    System.exit(1);
                    break;
            }
        }
    }

}
