/**
 *  FastZombie extends abstract Zombie
 *  This objects has one constructor with name and position
 *  FastZombie can move, change direction
 *  FastZombie can follow soldier and kill
 *  @see Zombie
 */
public class FastZombie extends Zombie{
    /**
     * Constructor
     * @param name name of the FastZombie
     * @param position position of the FastZombie
     */
    public FastZombie(String name, Position position) { // DO NOT CHANGE PARAMETERS

        super(name,position,20.0,2.0,20.0,ZombieType.FAST,ZombieState.WANDERING);



    }

    /**
     * This function is behaviour of FastZombie in one step in following conditions:
     * Calculate the euclidean distance
     * If the distance is shorter than or equal to the sum of collision distance of zombie and soldier
     * (distance <= collision_distance zombie + collision_distance soldier ), remove the soldier from the
     * simulation and return
     * If the state is wandering;
     * Calculate the next position of the zombie .
     * If the position is out of bounds, change direction to random value.
     * If the position is not out of bounds, change zombie position to the new_position.
     * Calculate the euclidean distance  to the closest soldier.
     * If the distance is shorter than or equal to the detection range of the zombie, change state to
     * FOLLOWING.
     * If the state is following;
     * Calculate the next position of the zombie.
     * If the position is out of bounds, change direction to random value.
     * If the position is not out of bounds, change zombie position to the new_position.
     * Count the number of step zombie has been in FOLLOWING state.
     * If the count is 4, change state to WANDERING
     *
     * @param controller has soldiers lists
     */
    @Override
    public void step(SimulationController controller) {

        SimulationObject closestSoldier = controller.closestSoldier(this);
        if(closestSoldier == null){
            return;
        }
        if(closestSoldier.getPosition().distance(this.getPosition()) <= this.getAnyCollusionRange() + closestSoldier.getAnyCollusionRange()){
            closestSoldier.removeFromList(controller);
            System.out.println(getName()+" killed " + closestSoldier.getName()+".");
            return;
        }
        if(zombieState.equals(ZombieState.WANDERING)){

            if(this.detectionRange >= this.getPosition().distance(closestSoldier.getPosition())){
                changeDirectionToTarget(closestSoldier.getPosition());
                zombieState = ZombieState.FOLLOWING;
                System.out.println(getName()+" changed state to FOLLOWING.");
                return;
            }
            Position newPosition = getPosition().add(getDirection().mult(getSpeed()));
            if(!controller.isWithinBound(newPosition)){
                this.setDirection(Position.generateRandomDirection(true));
                System.out.println(getName()+" changed direction to "+getDirection()+".");
            }else{
                setPosition(newPosition);
                System.out.println(getName()+" moved to "+getPosition()+".");
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
            zombieState = ZombieState.WANDERING;
            System.out.println(getName()+" changed state to WANDERING.");

        }
    }


}
