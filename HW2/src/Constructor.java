
public class Constructor extends Agent{
    private int capacity;
    private IgnotType ignotType;
    private boolean isalive = true;

    public Constructor(int id, int interval, int capacity, IgnotType ignotType) {
        super(id, interval);
        this.capacity = capacity;
        this.ignotType = ignotType;
    }


    public void run() {
        HW2Logger.WriteOutput(0, 0, this.getAgentId(), Action.CONSTRUCTOR_CREATED);
        while(true){
            //WaitIngots()or timeout after 3 seconds else break

            if(!Simulator.simulation.waitConstructorEmpty(this.getAgentId(),this.ignotType)){
                //Simulator.simulation.postConstructorFull(this.getAgentId());
                //Simulator.simulation.postConstructorEmpty(this.getAgentId());

                break;
            }

            HW2Logger.WriteOutput(this.getAgentId(), 0, this.getAgentId(), Action.CONSTRUCTOR_STARTED);
            try {
                sleepInterval(this.getInterval());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(this.ignotType ==IgnotType.IRON){
                Simulator.simulation.postConstructorFull(this.getAgentId());
                Simulator.simulation.postConstructorFull(this.getAgentId());
            }else{
                Simulator.simulation.postConstructorFull(this.getAgentId());
                Simulator.simulation.postConstructorFull(this.getAgentId());
                Simulator.simulation.postConstructorFull(this.getAgentId());
            }

            HW2Logger.WriteOutput(this.getAgentId(), 0, this.getAgentId(), Action.CONSTRUCTOR_FINISHED);

        }

        this.setAlive(false);
        HW2Logger.WriteOutput(0, 0, this.getAgentId(), Action.CONSTRUCTOR_STOPPED);

    }
}
