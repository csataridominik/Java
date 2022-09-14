import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Crossing_2_way_stop {
    public static class Crossing {
        Queue<Car> carsW=new LinkedList<>();
        Queue<Car> carsN=new LinkedList<>();
        Queue<Car> carsE=new LinkedList<>();
        Queue<Car> carsS=new LinkedList<>();
    }

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();
        Crossing tempCrossing = new Crossing();


        Car temp;
        int num_of_cars = 10;
        // 10 autot indit a keresztezodesben 0 vagy 1 sec szunetekkel
        for (int i=0;i<num_of_cars;i++) {
            try {
                Thread.sleep(new Random().nextInt(2)*10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Car parameterlistaja bovitheto!!!
            temp = new Car(startTime,tempCrossing);
            temp.start();



        }
/*
                System.out.println(tempCrossing.carsE.size() +"E  "+tempCrossing.carsW.size() +"W  "+
                        tempCrossing.carsS.size() +"S  "+tempCrossing.carsN.size() +"N  ");

*/

    }
}
