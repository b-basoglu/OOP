public class Smelter extends Agent  {
    private int capacity;
    private IgnotType ignotType;
    private int totalIgnot;
    private int currentIgnotCount = 0;
    private int producedIgnotCount =0;
    public Smelter(int id, int interval, int capacity, IgnotType ignotType, int totalIgnot) {
        super(id, interval);
        this.capacity = capacity;
        this.ignotType = ignotType;
        this.totalIgnot = totalIgnot;
    }

    public int getCurrentIgnotCount() {
        return currentIgnotCount;
    }

    public void setCurrentIgnotCount(int currentIgnotCount) {
        this.currentIgnotCount = currentIgnotCount;
    }


    public void run() {

        HW2Logger.WriteOutput(this.getAgentId(), 0, 0, Action.SMELTER_CREATED);
        while(this.isAgentAlive()){
            try {
                Simulator.simulation.waitSmelterEmpty(this.getAgentId());

                HW2Logger.WriteOutput(this.getAgentId(), 0, 0, Action.SMELTER_STARTED);

                sleepInterval(this.getInterval());

                Simulator.simulation.waitSmelterMutex(this.getAgentId());

                currentIgnotCount++;
                totalIgnot=totalIgnot-1;
                if(totalIgnot<=0){
                    this.setAlive(false);
                }
                Simulator.simulation.postSmelterMutex(this.getAgentId());

                Simulator.simulation.postSmelterFull(this.getAgentId());
                HW2Logger.WriteOutput(this.getAgentId(), 0, 0, Action.SMELTER_FINISHED);

                sleepInterval(this.getInterval());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //SmelterStopped ()
        //Simulator.simulation.postSmelterEmpty(this.getAgentId());
        for(Transporter transporter: Simulator.simulation.getTransporters() ){
            if(transporter.getSmelterID() == this.getAgentId()){
                Simulator.simulation.postSmelterFull(this.getAgentId());
            }
        }

        HW2Logger.WriteOutput(this.getAgentId(), 0, 0, Action.SMELTER_STOPPED);
    }
}
