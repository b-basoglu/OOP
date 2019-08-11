import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SimulationController {
    private ArrayList<Smelter> smelters = new ArrayList<Smelter>();
    private ArrayList<Constructor> constructors = new ArrayList<Constructor>();
    private ArrayList<Transporter> transporters = new ArrayList<Transporter>();
    private ArrayList<Semaphore> smelterEmpty = new ArrayList<>();
    private ArrayList<Semaphore> smelterFull= new ArrayList<>();
    private ArrayList<Semaphore> smelterMutex = new ArrayList<>();
    private ArrayList<Semaphore> constructorEmpty = new ArrayList<>();
    private ArrayList<Semaphore> constructorFull = new ArrayList<>();
    private ArrayList<Semaphore> constructorMutex = new ArrayList<>();
    private ArrayList<Semaphore> transporterCounter = new ArrayList<>();

    public void setConstructorEmpty(){
        constructorEmpty.add(new Semaphore(0));
    }
    public void setConstructorFull(int capacity){
        constructorFull.add(new Semaphore(capacity));
    }
    public void setConstructorMutex(){
        constructorMutex.add(new Semaphore(1));
    }

    public void postConstructorMutex(int constructorID){
        constructorMutex.get(constructorID-1).release();
    }
    public void postConstructorEmpty(int constructorID){
        constructorEmpty.get(constructorID-1).release();
    }
    public void postConstructorFull(int constructorID){
        constructorFull.get(constructorID-1).release();
    }

    public boolean waitConstructorEmpty(int constructorID,IgnotType type)  {

        try {
            if (type == IgnotType.IRON) {
                if(constructorEmpty.get(constructorID - 1).tryAcquire(2, 3, TimeUnit.SECONDS)){
                    return true;
                }else{
                    return false;
                }
            } else {
                if(constructorEmpty.get(constructorID - 1).tryAcquire(3, 3, TimeUnit.SECONDS)){
                    return true;
                }else{
                    return false;
                }
            }
        }catch (InterruptedException e) {
            return false;
        }

    }
    public void waitConstructorMutex(int constructorID) throws InterruptedException {
        constructorMutex.get(constructorID-1).acquire();
    }
    public void waitConstructorFull(int constructorID) throws InterruptedException {
        constructorFull.get(constructorID-1).acquire();
    }


    public void setSmelterEmpty(int capacity){
        smelterEmpty.add(new Semaphore(capacity));
    }
    public void setSmelterFull(){
        smelterFull.add(new Semaphore(0));
    }
    public void setSmelterMutex(){
        smelterMutex.add(new Semaphore(1));
    }

    public void postSmelterMutex(int smelterID){
        smelterMutex.get(smelterID-1).release();
    }
    public void postSmelterEmpty(int smelterID){
        smelterEmpty.get(smelterID-1).release();
    }
    public void postSmelterFull(int smelterID){
        smelterFull.get(smelterID-1).release();
    }

    public void waitSmelterEmpty(int smelterID) throws InterruptedException {
        smelterEmpty.get(smelterID-1).acquire();
    }
    public void waitSmelterMutex(int smelterID) throws InterruptedException {
        smelterMutex.get(smelterID-1).acquire();
    }
    public void waitSmelterFull(int smelterID) throws InterruptedException {
        smelterFull.get(smelterID-1).acquire();
    }

    public ArrayList<Smelter> getSmelters() {

        return smelters;
    }

    public void setSmelters(ArrayList<Smelter> smelters) {
        this.smelters = smelters;
    }

    public ArrayList<Constructor> getConstructors() {
        return constructors;
    }

    public void setConstructors(ArrayList<Constructor> constructors) {
        this.constructors = constructors;
    }

    public ArrayList<Transporter> getTransporters() {
        return transporters;
    }

    public void setTransporters(ArrayList<Transporter> transporters) {
        this.transporters = transporters;
    }

    public void addSmelter(Smelter smelter) {
        smelters.add(smelter);
    }
    public void addConstructor(Constructor constructor) {
        constructors.add(constructor);
    }
    public void addTransporter(Transporter transporter) {
        transporters.add(transporter);
    }


    public boolean smelterAlive(int ID){
        return smelters.get(ID-1).isAgentAlive();
    }
    public boolean smelterHasIgnot(int ID){

        try {
            Simulator.simulation.waitSmelterMutex(ID);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(smelters.get(ID-1).getCurrentIgnotCount()>0){
            Simulator.simulation.postSmelterMutex(ID);
            return true;
        }
        Simulator.simulation.postSmelterMutex(ID);
        return false;
    }
    public boolean constructorAlive(int ID){
        return constructors.get(ID-1).isAgentAlive();
    }
    public boolean transporterAlive(int ID){
        return transporters.get(ID-1).isAgentAlive();
    }

    public boolean transporterContinue(int smelterID,int constructorID){
        return smelterAlive(smelterID) || smelterHasIgnot(smelterID) && constructorAlive(constructorID);
    }
}
