import java.util.ArrayList;

/**
 *  This is an abstract class which extended by any object on simulation which
 *  are bullets,soldiers and zombies.
 *  This objects has one constructor takes name position and speed
 *  which sets direction randomly and makes object active
 *
 * @see Zombie
 * @see Soldier
 * @see Bullet
 */
public abstract class SimulationObject {
    private final String name;
    private Position position;
    private Position direction;
    private final double speed;
    private boolean active;
    public SimulationObject(String name, Position position, double speed) {
        this.name = name;
        this.position = position;
        this.speed = speed;
        this.direction = Position.generateRandomDirection(true);
        this.active = true;
    }

    /**
     * This function change direction of simulation objects to their target
     * @param target targeted simulation object
     */
    public void changeDirectionToTarget(Position target ){
        Position newDirection = new Position(target.getX()-this.getPosition().getX(),target.getY()-this.getPosition().getY());
        newDirection.normalize();
        setDirection(newDirection);
    }
    /**
     * This abstract function adds simulation objects to their corresponding list on simulation controller.
     * @param simulationController has lists of simulation objects
     */
    public abstract void addToList(SimulationController simulationController);

    /**
     * This abstract function is used to deactivate simulation objects to be removed later on simulation objects.
     * @param simulationController has lists of simulation objects
     */
    public abstract void removeFromList(SimulationController simulationController);

    /**
     * This abstract function is used to step any simulation object
     * @param controller has lists and counts of simulation objects
     */
    public abstract void step(SimulationController controller);

    /**
     * This abstract function is used to get collusion range of ant simulation object.
     * @return collusion range (except bullet which does not have collusion range)
     */
    public abstract double getAnyCollusionRange();

    /**
     * Getter function of name of simulation object
     * @return name of simulation object
     */
    public String getName() {
        return name;
    }
    /**
     * Getter function of position of simulation object
     * @return position of simulation object
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Setter function of position of object
     * @param position new position of object
     */
    public void setPosition(Position position) {
        this.position = position;
    }
    /**
     * Getter function of direction of simulation object
     * @return direction of simulation object
     */
    public Position getDirection() {
        return direction;
    }

    /**
     * Setter function of direction of object
     * @param direction new direction of object
     */
    public void setDirection(Position direction) {
        this.direction = direction;
    }
    /**
     * Getter function of speed of simulation object
     * @return speed of simulation object
     */
    public double getSpeed() {
        return speed;
    }
    /**
     * Returns whether object is active or not
     * @return whether object is active or not
     */
    public boolean isActive() {
        return active;
    }

    /**
     * This function activate or deactivate objects
     * @param active active or not
     */
    public void setActive(boolean active) {
        this.active = active;
    }




}
