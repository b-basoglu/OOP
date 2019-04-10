import java.util.ArrayList;

/**
 * This class has height and width which bounds map.
 * This class stores soldiers,bullets and zombies with their counts.
 * This class steps all simulation objects.
 * This can find closest zombies to specific soldier and specific bullet.
 * This can find closest soldier to specific zombie.
 * Decide whether object is within bound or not.
 */
public class SimulationController {
    private final double height;
    private final double width;
    private ArrayList<Soldier> soldiers = new ArrayList<Soldier>();
    private ArrayList<Zombie> zombies = new ArrayList<Zombie>();
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private int soldierCount = 0;
    private int zombieCount = 0;
    private int bulletCount = 0;

    /**
     * This function creates bullet, add it to the list and increment bulletcount
     * Bullet’s position and direction should be same as soldiers
     * Speed depends on the soldier.
     * @param soldier who fired bullet
     * @param speed bulletspeed which depends on soldier
     */
    public void createBullet(SimulationObject soldier,double speed){
        Bullet bullet = new Bullet("Bullet"+bulletCount,soldier.getPosition(),speed);
        bullet.setDirection(soldier.getDirection());
        bullets.add(bullet);
        bulletCount+=1;
        System.out.println(soldier.getName()+" fired "+bullet.getName()+" to direction "+bullet.getDirection()+".");

    }

    /**
     * Constructor with bounds of map
     * @param width horizontal limit
     * @param height vertical limit
     */
    public SimulationController(double width, double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Finds and returns closest zombie to soldier
     * @param soldier is searching for closest zombie
     * @return closest zombie
     */
    public SimulationObject closestZombie(SimulationObject soldier){
        SimulationObject closestZombie = null;
        double closestDistance = Double.MAX_VALUE;
        double newDistance;
        for(Zombie temp : zombies){
            if(!temp.isActive()){
                continue;
            }
            newDistance = soldier.getPosition().distance(temp.getPosition());
            if(closestDistance>newDistance){
                closestDistance = newDistance;
                closestZombie = temp;
            }
        }

        return closestZombie;
    }

    /**
     * Finds and returns closest soldier to zombie
     * @param zombie is searching for closest soldier
     * @return closest soldier
     */
    public SimulationObject closestSoldier(SimulationObject zombie){
        SimulationObject closestSoldier = null;
        double closestDistance = Double.MAX_VALUE;
        double newDistance;
        for(Soldier temp : soldiers){
            if(!temp.isActive()){
                continue;
            }
            newDistance = zombie.getPosition().distance(temp.getPosition());
            if(closestDistance>newDistance){
                closestDistance = newDistance;
                closestSoldier = temp;
            }
        }

        return closestSoldier;
    }

    /**
     * Getter function of height
     * @return vertical limit
     */
    public double getHeight() {
        return height;
    }

    /**
     * Getter function of windth
     * @return horizontal limit
     */
    public double getWidth() {
        return width;
    }

    /**
     * Checks whether position is within bounds or not
     * @param position is checking whether within bounds
     * @return returns boolean within bounds or not
     */
    public boolean isWithinBound(Position position){
        if(position.getX() < 0 || position.getY() < 0){
            return false;
        }else if(position.getX() > this.getWidth() || position.getY() > this.getHeight()){
            return false;
        }else{
            return true;
        }
    }

    /**
     * This functions calls step function of all simulation objects in this controller
     * respectively bullets,soldiers,zombies
     * All simulation object types has their lists
     * If any of objects became inactive to be killed,kill all inactive objects
     */
    public void stepAll() {

        for(Bullet bullet : bullets){
            bullet.step(this);
        }
        if(this.isFinished())
            return;

        for(Soldier soldier : soldiers){
            if(soldier.isActive()){
                soldier.step(this);
            }
        }

        for(Zombie zombie : zombies){
            if(zombie.isActive()){
                zombie.step(this);
            }

        }

        if(this.isFinished())
            return;

        deleteNotActive();
    }

    /**
     * This function deletes all in active objects
     */
    public void deleteNotActive() {

        for(int i =0 ; i<bullets.size();i++){
            if(!(bullets.get(i).isActive())) {
                bullets.remove(i);
                i--;
            }
        }
        for(int i =0 ; i<soldiers.size();i++){
            if(!(soldiers.get(i).isActive())) {
                soldiers.remove(i);
                i--;
            }
        }

        for(int i =0 ; i<zombies.size();i++){
            if(!(zombies.get(i).isActive())) {
                zombies.remove(i);
                i--;
            }
        }

    }

    /**
     * Adds simulation object to be simulated
     * @param obj to be simulated
     */
    public void addSimulationObject(SimulationObject obj) {
        obj.addToList(this);
    }

    /**
     * remove simulation object from simulation list
     * @param obj remove from list
     */
    public void removeSimulationObject(SimulationObject obj) {
        obj.removeFromList(this);
    }

    /**
     * Check whether there all soldiers are killed or all zombies are killed
     * @return boolean soldiers are killed or all zombies are killed
     */
    public boolean isFinished() {
        if(soldierCount==0 || zombieCount == 0){
            return true;
        }
        return false;
    }

    /**
     * Getter of regualar soldier list
     * @return regular soldier list
     */
    public ArrayList<Soldier> getSoldiers() {
        return soldiers;
    }



    /**
     * Getter of regular zombie list
     * @return regular zombie list
     */
    public ArrayList<Zombie> getZombies() {
        return zombies;
    }



    /**
     * Getter of number of soldiers
     * @return number of soldiers
     */
    public int getSoldierCount() {
        return soldierCount;
    }

    /**
     * Setter of number of soldiers
     * @param soldierCount number of soldiers
     */
    public void setSoldierCount(int soldierCount) {
        this.soldierCount = soldierCount;
    }
    /**
     * Getter of number of zombies
     * @return number of zombies
     */
    public int getZombieCount() {
        return zombieCount;
    }

    /**
     * Setter of number of zombies
     * @param zombieCount number of zombies
     */

    public void setZombieCount(int zombieCount) {
        this.zombieCount = zombieCount;
    }
    /**
     * Getter of bullet list
     * @return bullet list
     */
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

}
