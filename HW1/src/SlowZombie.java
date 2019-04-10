/**
 *  SlowZombie extends abstract Zombie
 *  This objects has one constructor with name and position
 *  SlowZombie can move, change direction
 *  SlowZombie can follow soldier and kill
 *  @see Zombie
 */
public class SlowZombie extends Zombie{

    /**
     * Constructor
     * @param name name of the SlowZombie
     * @param position position of the SlowZombie
     */
    public SlowZombie(String name, Position position) { // DO NOT CHANGE PARAMETERS

        super(name,position,2.0,1.0,40.0,ZombieType.SLOW,ZombieState.WANDERING);

    }

    /**
     *Calculate the euclidean distance
     * This function is behaviour of SlowZombie in one step in following conditions:
     * If the distance is shorter than or equal to the sum of collision distance of zombie and soldier
     * (distance <= collision_distance zombie + collision_distance soldier ), remove the soldier from the
     * simulation and return
     * If the state is wandering calculate the euclidean distance to the closest soldier.
     * If the distance is shorter than or equal to the detection range of the zombie, change state to
     * FOLLOWING and return.
     * If not calculate the next position of the zombie.
     * If the position is out of bounds, change direction to random value.
     * If the position is not out of bounds, change zombie position to the new_position.
     * If the state is following calculate the euclidean distance to the closest soldier.
     * If the distance is shorter than or equal to the detection range of the zombie, change direction
     * to soldier.
     * Calculate the next position of the zombie .
     * If the position is out of bounds, change direction to random value.
     * If the position is not out of bounds, change zombie position to the new_position.
     * Use the calculated distance to the closest soldier in the first step. If the distance is shorter
     * than or equal to the detection range of the zombie, change state to WANDERING.
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
            System.out.println(getName()+" killed " + closestSoldier.getName());
            return;
        }
        if(zombieState.equals(ZombieState.WANDERING)){

            if(this.detectionRange >= this.getPosition().distance(closestSoldier.getPosition())){
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
            boolean detected=false;

            if(this.detectionRange >= this.getPosition().distance(closestSoldier.getPosition())){
                changeDirectionToTarget(closestSoldier.getPosition());
                detected = true;
                System.out.println(getName()+" changed direction to "+getDirection()+".");
            }
            Position newPosition = getPosition().add(getDirection().mult(getSpeed()));
            if(!controller.isWithinBound(newPosition)){
                this.setDirection(Position.generateRandomDirection(true));
                System.out.println(getName()+" changed direction to "+getDirection()+".");
            }else{
                setPosition(newPosition);
                System.out.println(getName()+" moved to "+getPosition()+".");
            }
            if(detected){
                zombieState = ZombieState.WANDERING;
                System.out.println(getName()+" changed state to WANDERING.");
            }

        }
    }


}
