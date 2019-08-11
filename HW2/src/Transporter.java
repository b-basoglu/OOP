public class Transporter extends Agent {
    private int smelterID;
    private int constructorID;
    public Transporter(int id, int interval, int smelterID, int constructorID) {
        super(id, interval);

        this.smelterID = smelterID;
        this.constructorID = constructorID;
    }

    public int getSmelterID() {
        return smelterID;
    }

    public void run() {
        HW2Logger.WriteOutput(0, this.getAgentId(), 0, Action.TRANSPORTER_CREATED);
        while(Simulator.simulation.transporterContinue(smelterID,constructorID)){
            try {
                Simulator.simulation.waitSmelterFull(smelterID);


                HW2Logger.WriteOutput(smelterID, this.getAgentId(), 0, Action.TRANSPORTER_TRAVEL);

                sleepInterval(this.getInterval());

                Simulator.simulation.waitSmelterMutex(smelterID);
                Simulator.simulation.getSmelters().get(smelterID-1).setCurrentIgnotCount(Simulator.simulation.getSmelters().get(smelterID-1).getCurrentIgnotCount()-1);
                HW2Logger.WriteOutput(smelterID, this.getAgentId(), 0, Action.TRANSPORTER_TAKE_INGOT);
                Simulator.simulation.postSmelterMutex(smelterID);
                sleepInterval(this.getInterval());

                Simulator.simulation.postSmelterEmpty(smelterID);


                Simulator.simulation.waitConstructorFull(constructorID);

                HW2Logger.WriteOutput(0, this.getAgentId(), constructorID, Action.TRANSPORTER_TRAVEL);

                sleepInterval(this.getInterval());

                HW2Logger.WriteOutput(0, this.getAgentId(), constructorID, Action.TRANSPORTER_DROP_INGOT);

                sleepInterval(this.getInterval());

                Simulator.simulation.postConstructorEmpty(constructorID);


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.setAlive(false);

        HW2Logger.WriteOutput(0, this.getAgentId(), 0, Action.TRANSPORTER_STOPPED);

    }
}
