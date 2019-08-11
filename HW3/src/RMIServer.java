import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
    public static void main(String[] args) throws RemoteException, MalformedURLException {
        MazeHub mazeHub = new MazeHub();
        LocateRegistry.createRegistry(8000);
        Naming.rebind("rmi://127.0.0.1:8000/bnymn", mazeHub);
    }
}
