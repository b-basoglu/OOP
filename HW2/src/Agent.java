import java.util.Random;
import java.util.stream.DoubleStream;

public abstract class Agent extends Thread{
    private int id;
    private int interval;
    private boolean isAlive = true;

    protected Agent(int id, int interval) {
        this.id = id;
        this.interval = interval;
    }

    public void sleepInterval(int interval) throws InterruptedException {

        Random random = new Random(System.currentTimeMillis());
        DoubleStream stream;
        stream = random.doubles(1, interval-interval*0.01, interval+interval*0.02);
        Thread.sleep((long) stream.findFirst().getAsDouble());

    }

    public int getAgentId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public boolean isAgentAlive() {
        synchronized (this){
            return isAlive;
        }

    }

    public void setAlive(boolean alive) {
        synchronized (this){
            isAlive = alive;
        }

    }
}
