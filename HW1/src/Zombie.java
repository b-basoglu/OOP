import java.util.ArrayList;

/**
 *  Zombie extends abstract SimulationObject
 *  This objects has one constructor
 *  There are 3 type of zombie which are fast zombie, regular zombie and slow zombie
 *  @see SimulationObject
 *  @see SlowZombie
 *  @see RegularZombie
 *  @see FastZombie
 */
public abstract class Zombie extends SimulationObject {
    protected double collusionRange;
    protected double detectionRange;
    protected ZombieType zombieType;
    protected ZombieState zombieState;

    public Zombie(String name, Position position, double speed,double collusionRange,double detectionRange,ZombieType zombieType,ZombieState zombieState) {
        super(name, position, speed);
        this.collusionRange = collusionRange;
        this.detectionRange = detectionRange;
        this.zombieType = zombieType;
        this.zombieState = zombieState;

    }
    /**
     * This function adds this zombie to simulation
     * @param simulationController has zombies list and count
     */
    @Override
    public void addToList(SimulationController simulationController){
        simulationController.getZombies().add(this);
        simulationController.setZombieCount(simulationController.getZombieCount()+1);
    }

    /**
     * This function set inactive zombie to be removed later
     * @param simulationController has zombie count
     */

    @Override
    public void removeFromList(SimulationController simulationController){
        this.setActive(false);
        simulationController.setZombieCount(simulationController.getZombieCount()-1);
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
