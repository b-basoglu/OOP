import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Simulator {
    public static  SimulationController simulation;
    public static void main(String[] args) {
        simulation = new SimulationController();

        int I,C,T,R,S;
        int N_t,N_c,N_s;



        Scanner myObj = new Scanner(new InputStreamReader(System.in));
        N_s = myObj.nextInt();

        for(int i=0; i<N_s; i++)
        {
            I = myObj.nextInt();
            C = myObj.nextInt();
            T = myObj.nextInt();
            R = myObj.nextInt();
            simulation.setSmelterEmpty(C);
            simulation.setSmelterMutex();
            simulation.setSmelterFull();
            if(T==0){
                simulation.addSmelter(new Smelter(i+1,I,C,IgnotType.IRON,R));
            }else{
                simulation.addSmelter(new Smelter(i+1,I,C,IgnotType.COPPER,R));
            }
        }

        N_c = myObj.nextInt();

        for(int i=0; i<N_c; i++)
        {
            I = myObj.nextInt();
            C = myObj.nextInt();
            T = myObj.nextInt();
            simulation.setConstructorFull(C);
            simulation.setConstructorMutex();
            simulation.setConstructorEmpty();


            if(T==0){
                simulation.addConstructor(new Constructor(i+1,I,C,IgnotType.IRON));
            }else{
                simulation.addConstructor(new Constructor(i+1,I,C,IgnotType.COPPER));
            }
        }

        N_t = myObj.nextInt();

        for(int i=0; i<N_t; i++)
        {
            I = myObj.nextInt();
            S = myObj.nextInt();
            C = myObj.nextInt();

            simulation.addTransporter(new Transporter(i+1,I,S,C));

        }
        HW2Logger.InitWriteOutput();

        for(Smelter smelter:simulation.getSmelters()){
            smelter.start();
        }
        for(Transporter transporter:simulation.getTransporters()){
            transporter.start();
        }
        for(Constructor constructor:simulation.getConstructors()){
            constructor.start();
        }


    }

}
