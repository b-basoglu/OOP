/**
 *  Soldier extends abstract SimulationObject
 *  This objects has one constructor
 *  There are three type of soldiers which are regular soldier, commando and sniper
 *  @see SimulationObject
 *  @see Sniper
 *  @see Commando
 *  @see RegularSoldier
 */
public abstract class Soldier extends SimulationObject{
    protected double collusionRange;
    protected double shootingRange;
    protected SoldierState soldierState;
    protected SoldierType soldierType;
    protected double bulletSpeed;
    protected Soldier(String name, Position position, double speed,double collusionRange,double shootingRange,SoldierState soldierState,SoldierType soldierType,double bulletSpeed) {
        super(name, position, speed);
        this.collusionRange=collusionRange;
        this.shootingRange=shootingRange;
        this.soldierState=soldierState;
        this.soldierType=soldierType;
        this.bulletSpeed=bulletSpeed;
    }
    /**
     * This function adds this soldier to simulation
     * @param simulationController has soldiers list and count
     */
    @Override
    public void addToList(SimulationController simulationController){
        simulationController.getSoldiers().add(this);
        simulationController.setSoldierCount(simulationController.getSoldierCount()+1);
    }

    /**
     * This function makes this soldier inactive to be deleted later
     * and decrements soldier count
     * @param simulationController has soldier counts
     */
    @Override
    public void removeFromList(SimulationController simulationController){
        this.setActive(false);
        simulationController.setSoldierCount(simulationController.getSoldierCount()-1);
    }
    /**
     * This abstract function is to get any collusion range of objects which extends simulation object
     * @return collusionRange
     */
    @Override
    public double getAnyCollusionRange() {
        return collusionRange;
    }


}
