
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * This class runs simulation
 * This class has main
 */
public class SimulationRunner {

    public static void main(String[] args) {
        SimulationController simulation = new SimulationController(50, 50);


        simulation.addSimulationObject(new RegularSoldier("RegularSoldier1", new Position(20, 20)));
        simulation.addSimulationObject(new RegularZombie("RegularZombie1", new Position(40, 40)));
        simulation.addSimulationObject(new RegularSoldier("RegularSoldier2", new Position(10, 10)));
        simulation.addSimulationObject(new RegularZombie("RegularZombie2", new Position(30, 30)));
        simulation.addSimulationObject(new Sniper("Sniper1", new Position(25, 25)));
        simulation.addSimulationObject(new Commando("Commando1", new Position(11, 1)));
        simulation.addSimulationObject(new FastZombie("FastZombie1", new Position(22, 44)));
        simulation.addSimulationObject(new SlowZombie("SlowZombie1", new Position(15 , 20)));


        while (!simulation.isFinished()) {
            simulation.stepAll();

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(SimulationRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
