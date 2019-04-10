/**
 * Bullet extends abstract Simulation Object
 * Bullet has directions , position and moves iteratively towards zombie to kill
 * Bullet removed after 1 step
 * Bullet can kill zombie
 * @see SimulationObject
 */
public class Bullet extends SimulationObject{
    /**
     * Constructor
     * @param name
     * @param position
     * @param speed
     */
    public Bullet(String name, Position position, double speed) {
        super(name, position, speed);
    }

    /**
     * This function adds Bullet to simulation
     * @param simulationController has Bullet list
     */
    @Override
    public void addToList(SimulationController simulationController) {
        simulationController.getBullets().add(this);
    }

    /**
     * This function makes Bullet inactive to be deleted later
     * @param simulationController has Bullet list
     */
    @Override
    public void removeFromList(SimulationController simulationController) {
        this.setActive(false);
    }

    /**
     * This function behaviour of Bullet in one step
     * Bullet runs N (where N = getSpeed) small step and check following conditions:
     * If there is a zombie in collusion range hit, kill and remove bullet
     * Else if it goes out bounds just remove bullet
     * If nothing happened it drops to ground and remove bullet
     * @param controller has zombies
     */
    @Override
    public void step(SimulationController controller) {
        boolean remove = true;
        for(int i =0 ; i <getSpeed();i++ ){

            SimulationObject closestZombie = controller.closestZombie(this);
            if(closestZombie == null){
                return;
            }

            if( this.getPosition().distance(closestZombie.getPosition()) <= closestZombie.getAnyCollusionRange()){
                closestZombie.removeFromList(controller);
                this.removeFromList(controller);
                System.out.println(getName()+" hit "+closestZombie.getName()+".");
                remove = false;
                break;
            }
            this.setPosition(this.getPosition().add(this.getDirection()));
            if(!controller.isWithinBound(getPosition())){
                this.removeFromList(controller);
                System.out.println(getName()+" moved out of bounds.");
                remove = false;
                break;
            }
        }
        if(remove){
            this.removeFromList(controller);
            System.out.println(getName()+" dropped to the ground at "+getPosition());
        }

    }

    /**
     * Abstract function which needs to be implemented in all SimulationObjects to get collusion range
     * @return nothing
     */
    @Override
    public double getAnyCollusionRange() {
        return 0;
    }
}
