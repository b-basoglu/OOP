/**
 *  Commando extends abstract Soldier
 *  This objects has one constructor
 *  Commando can move, change direction
 *  Commando can shoot bullet towards zombie to kill
 *  @see Soldier
 */
public class Commando extends Soldier{

    /**
     * Constructor
     * @param name  name of the Commando
     * @param position position of the Commando
     */
    public Commando(String name, Position position) { // DO NOT CHANGE PARAMETERS
        super(name,position,10.0,2,10,SoldierState.SEARCHING,SoldierType.COMMANDO,40);

    }

    /**
     * This function is behaviour of Commando in one step in following conditions:
     * If Commando in searching state Calculate the euclidean distance  to the closest zombie.
     * If the distance is shorter than or equal to the shooting range of the soldier; change soldier
     * direction to zombie, change state to SHOOTING and return.
     * Calculate the next position of the soldier
     * If the position is out of bounds, change direction to random value.
     * If the position is not out of bounds, change soldier position to the new_position.
     * Calculate the euclidean distance to the closest zombie.
     * If the distance is shorter than or equal to the shooting range of the soldier; change soldier
     * direction to zombie, change state to SHOOTING.
     * If Commando in shooting state create a bullet.
     * Calculate the euclidean distance to the closest zombie.
     * If the distance is shorter than or equal to the shooting range of the soldier, change soldier
     * direction to zombie.
     * If not, randomly change soldier direction and change state to SEARCHING.
     *
     * @param controller creates bullet and has zombie list
     */
    @Override
    public void step(SimulationController controller) {

        if(soldierState.equals(SoldierState.SEARCHING)){
            SimulationObject closestZombie = controller.closestZombie(this);
            if(closestZombie == null){
                return;
            }
            if(this.shootingRange >= this.getPosition().distance(closestZombie.getPosition())){
                changeDirectionToTarget(closestZombie.getPosition());
                System.out.println(getName()+" changed direction to "+getDirection()+".");
                soldierState = SoldierState.SHOOTING;
                System.out.println(getName()+" changed state to SHOOTING.");
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
            closestZombie = controller.closestZombie(this);
            if(this.shootingRange >= this.getPosition().distance(closestZombie.getPosition())){
                changeDirectionToTarget(closestZombie.getPosition());
                System.out.println(getName()+" changed direction to "+getDirection()+".");
                soldierState = SoldierState.SHOOTING;
                System.out.println(getName()+" changed state to SHOOTING.");
            }

        }else if(soldierState.equals(SoldierState.SHOOTING)){
            controller.createBullet(this,bulletSpeed);
            SimulationObject closestZombie = controller.closestZombie(this);
            if(closestZombie == null){
                return;
            }
            if(this.shootingRange >= this.getPosition().distance(closestZombie.getPosition())){
                changeDirectionToTarget(closestZombie.getPosition());
                System.out.println(getName()+" changed direction to "+getDirection()+".");
            }else{
                this.setDirection(Position.generateRandomDirection(true));
                System.out.println(getName()+" changed direction to "+getDirection()+".");
                soldierState = SoldierState.SEARCHING;
                System.out.println(getName()+" changed state to SEARCHING.");
            }

        }
    }

}
