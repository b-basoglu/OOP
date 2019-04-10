/**
 *  RegularZombie extends abstract Zombie
 *  This objects has one constructor with name and position
 *  RegularZombie can move, change direction
 *  RegularZombie can follow soldier and kill
 *  @see Zombie
 */
public class RegularZombie extends Zombie{

    private int countFollowing;

    /**
     * Constructor
     * @param name name of the RegularZombie
     * @param position position of the RegularZombie
     */
    public RegularZombie(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name,position,5.0,2.0,20.0,ZombieType.REGULAR,ZombieState.WANDERING);
        countFollowing = 0;
    }

    /**
     * This function is behaviour of RegularZombie in one step in following conditions:
     * Calculate the euclidean distance
     * If the distance is shorter than or equal to the sum of collision distance of zombie and soldier
     * (distance <= collision_distance zombie + collision_distance soldier ), remove the soldier from the
     * simulation and return
     * If the state is wandering;
     * Calculate the next position of the zombie .
     * If the position is out of bounds, change direction to random value.
     * If the position is not out of bounds, change zombie position to the new_position.
     * Calculate the euclidean distance to the closest soldier.
     * If the distance is shorter than or equal to the detection range of the zombie, change state to
     * FOLLOWING.
     * If the state is following
     * Calculate the next position of the zombie.
     * If the position is out of bounds, change direction to random value.
     * If the position is not out of bounds, change zombie position to the new_position.
     * Count the number of step zombie has been in FOLLOWING state.
     * If the count is 4, change state to WANDERING.;
     * @param controller has soldiers lists
     */
    @Override
    public void step(SimulationController controller) {

        SimulationObject closestSoldier = controller.closestSoldier(this);
        if(closestSoldier == null){
            return;
        }
        if(getPosition().distance(closestSoldier.getPosition()) <= this.getAnyCollusionRange() + closestSoldier.getAnyCollusionRange()){
            closestSoldier.removeFromList(controller);
            System.out.println(getName()+" killed " + closestSoldier.getName()+".");
            return;
        }
        if(zombieState.equals(ZombieState.WANDERING)){
            Position newPosition = getPosition().add(getDirection().mult(getSpeed()));
            if(!controller.isWithinBound(newPosition)){
                this.setDirection(Position.generateRandomDirection(true));
                System.out.println(getName()+" changed direction to "+getDirection()+".");
            }else{
                setPosition(newPosition);
                System.out.println(getName()+" moved to "+getPosition()+".");
            }

            if(this.detectionRange >= this.getPosition().distance(closestSoldier.getPosition())){
                zombieState = ZombieState.FOLLOWING;
                System.out.println(getName()+" changed state to FOLLOWING.");
            }
        }else if(zombieState.equals(ZombieState.FOLLOWING)){
            Position newPosition = getPosition().add(getDirection().mult(getSpeed()));
            if(!controller.isWithinBound(newPosition)){
                this.setDirection(Position.generateRandomDirection(true));
                System.out.println(getName()+" changed direction to "+getDirection()+".");
            }else{
                setPosition(newPosition);
                System.out.println(getName()+" moved to "+getPosition()+".");
            }
            countFollowing+=1;
            if(countFollowing==4){
                zombieState = ZombieState.WANDERING;
                System.out.println(getName()+" changed state to WANDERING.");
                countFollowing = 0;
            }
        }
    }

}
