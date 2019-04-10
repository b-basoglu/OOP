import java.util.Scanner;

/**
 *  Sniper extends abstract Soldier
 *  This objects has one constructor with name and position
 *  Sniper can move, change direction
 *  Sniper can shoot bullet towards zombie to kill
 *  @see Soldier
 *
 */
public class Sniper extends Soldier{


    /**
     * Constructor
     * @param name name of the Sniper
     * @param position position of the Sniper
     */
    public Sniper(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name,position,10.0,5,40,SoldierState.SEARCHING,SoldierType.SNIPER,100.0);
    }

    /**
     * This function is behaviour of Sniper in one step in following conditions:
     * If Sniper in searching state;
     * Calculate the next position of the soldier with formula:
     * new_position = position + direction ∗ speed
     * If the position is out of bounds, change direction to random value
     * If the position is not out of bounds, change soldier position to the new_position.
     * Calculate the euclidean distance (3.1.2) to the closest zombie.
     * If the distance is shorter than or equal to the shooting range of the soldier, change state to
     * AIMING.
     * If Sniper in aiming state;
     * Calculate the euclidean distance to the closest zombie.
     * If the distance is shorter than or equal to the shooting range of the soldier, change soldier
     * direction to zombie and change state to SHOOTING.Normalize the direction.
     * If not, change state to SEARCHING
     * @param controller creates bullet and has zombie list
     * If Sniper in shooting state create a bullet.
     * Calculate the euclidean distance to the closest zombie.
     * If the distance is shorter than or equal to the shooting range of the soldier, change state to
     * AIMING.
     * If not, randomly change soldier direction and change state to SEARCHING.
     */
    @Override
    public void step(SimulationController controller) {
        int x = 0;

        if(soldierState.equals(SoldierState.SEARCHING)){
            Position newPosition = getPosition().add(getDirection().mult(getSpeed()));
            if(!controller.isWithinBound(newPosition)){
                this.setDirection(Position.generateRandomDirection(true));
                System.out.println(getName()+" changed direction to "+getDirection()+".");
            }else{
                setPosition(newPosition);
                System.out.println(getName()+" moved to "+getPosition()+".");
            }
            soldierState = SoldierState.AIMING;
            System.out.println(getName()+" changed state to AIMING.");



        }else if(soldierState.equals(SoldierState.AIMING)){
            SimulationObject closestZombie = controller.closestZombie(this);
            if(closestZombie == null){
                return;
            }
            if(this.shootingRange >= this.getPosition().distance(closestZombie.getPosition())){

                changeDirectionToTarget(closestZombie.getPosition());
                System.out.println(getName()+" changed direction to "+getDirection()+".");
                soldierState = SoldierState.SHOOTING;
                System.out.println(getName()+" changed state to SHOOTING.");
            }else{
                soldierState = SoldierState.SEARCHING;
                System.out.println(getName()+" changed state to SEARCHING.");
            }

        }else if(soldierState.equals(SoldierState.SHOOTING)){
            controller.createBullet(this,bulletSpeed);
            SimulationObject closestZombie = controller.closestZombie(this);
            if(closestZombie == null){
                return;
            }
            if(this.shootingRange >= this.getPosition().distance(closestZombie.getPosition())){
                soldierState = SoldierState.AIMING;
                System.out.println(getName()+" changed state to AIMING.");
            }else{
                this.setDirection(Position.generateRandomDirection(true));
                System.out.println(getName()+" changed direction to "+getDirection()+".");
                soldierState = SoldierState.SEARCHING;
                System.out.println(getName()+" changed state to SEARCHING.");
            }

        }
    }





}
