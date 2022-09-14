import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.Random;

/**
 * Iranyok: N - eszak, S - del, E - kelet, W - nyugat
 */
enum Direction{N,S,E,W,NULL};

/**
 * Auto osztaly, amely Thread-ből örököl
 * Lehet plusz fuggvenyeket, eljarasokat, mezoket hozzaadni
 * Aminek biztosan futnia kell az Crossing_2_way_stop osztalynak (ami szinten modosithato) és a setID, setFrom, setTo
 * fuggvenyeknek
 */
public class Car extends Thread{
    private LinkedList<Car> cars=new LinkedList<>();
    //private LinkedList<Queue< Car> > traffic;


    private Random random = new Random();
    private long startTime;                                     // a program futtatasanak kezdete
    private int id;                                             // auto ID
    private Direction from, to;                                 // iranyok: honnet, hova megy az auto

    private final Crossing_2_way_stop.Crossing crossing;


    /**
     * Konstruktor
     * General maganak random honnet (N,S,E,W), hova iranyt (N,S,E,W). A honnet es hova irany nem lehet azonos
     * General maganak ID-t (1-999 erteket vehet fel)
     * !!! Parameterlista bovitheto !!!
     * @param startTime a program futtatasanak kezdete ms-ban
     */
    Car(long startTime, Crossing_2_way_stop.Crossing crossing){

        int rand = this.random.nextInt(4);

        this.startTime =startTime;
        if(rand ==0){
          from = Direction.N;
          crossing.carsN.add(this);
        } else if(rand ==1){
          from = Direction.S;
          crossing.carsS.add(this);
        } else if(rand ==2){
            from = Direction.E;
            crossing.carsE.add(this);
        } else if(rand == 3){
            from = Direction.W;
            crossing.carsW.add(this);
        }

        int rand2 = rand;
        while (rand == rand2) {
                rand2=(int) (Math.random() * 4);
        }

        if(rand2 == 0){
            to = Direction.N;
        } else if(rand2 ==1){
            to = Direction.S;
        } else if(rand2 ==2){
            to = Direction.E;
        } else if(rand2 ==3){
            to = Direction.W;
        }

        this.id = this.random.nextInt(999);

        this.crossing =crossing;
        //System.out.println("from: " + from+"  to:  "+to      +"            _" +id);


    }

    /**
     * Szal altal futtatott programresz
     * Logoljon! Pl.: 1 - A(z) 181 rendszamu auto megerkezett a keresztezodés ele (S-->N)
     */
    @Override
    public void run() {

        //synchronized (Car.this){
        synchronized (this.crossing){
            System.out.println( (int)((System.currentTimeMillis()-startTime)/1000F) + " -  A(z) "+ this.id +" rendszámú autó megérkezett a kereszteződés elé. (" +from+""+to+")");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }




        if(from.equals(Direction.W) && to.equals(Direction.N)) {
            //WN irányba akkor mehet az autó, ha nincs EW, EN irányú jármű
         if(crossing.carsE.isEmpty() || ((!crossing.carsE.peek().getTo().equals(Direction.W) && !Objects.requireNonNull(crossing.carsE.peek()).getTo().equals(Direction.N)))) {
             this.log();

             //mehet
         } else {
             while(!crossing.carsE.isEmpty() || ((crossing.carsE.peek().getTo().equals(Direction.W) || Objects.requireNonNull(crossing.carsE.peek()).getTo().equals(Direction.N)))) {
                 try {

                     crossing.wait();

                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }

             this.log();


         }


        } else if(from.equals(Direction.W) && (to.equals(Direction.S) || to.equals(Direction.E))){
                //WS és WE irányban az autók szabadon mehetnek
                this.log();


        } else if(from.equals(Direction.E) && (to.equals(Direction.N) || to.equals(Direction.W))){
            //EW és EN irányban az autók szabadon mehetnek
            this.log();


        } else if(from.equals(Direction.E) && to.equals(Direction.S)) {
            //ES irányba akkor mehet az autó, ha nincs WE, WS irányú jármű
            if(crossing.carsW.isEmpty() || ((!crossing.carsW.peek().getTo().equals(Direction.E) && !Objects.requireNonNull(crossing.carsW.peek()).getTo().equals(Direction.S)))) {
                //mehet
                this.log();



            } else {
                while(!crossing.carsW.isEmpty() || ((crossing.carsW.peek().getTo().equals(Direction.E) || Objects.requireNonNull(crossing.carsW.peek()).getTo().equals(Direction.S)))) {
                    try {

                        crossing.wait();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                this.log();



            }



        } else if(from.equals(Direction.S) && to.equals(Direction.N)){
            //SN irányba akkor mehet az autó, ha nincs ()WE, ()EW, ()WN, ()EN, ES irányú jármű
            if((crossing.carsW.isEmpty() && crossing.carsE.isEmpty() )
                    || ((Objects.requireNonNull(!crossing.carsW.peek().getTo().equals(Direction.E))
                    && Objects.requireNonNull(crossing.carsW.peek()).getTo().equals(Direction.N))
                    && ((Objects.requireNonNull(!crossing.carsE.peek().getTo().equals(Direction.W))
                    && Objects.requireNonNull(crossing.carsE.peek()).getTo().equals(Direction.N)))
                    && !(Objects.requireNonNull(crossing.carsE.peek()).getTo().equals(Direction.S)))) {
                //mehet

                this.log();


            } else {
                while(!(crossing.carsW.isEmpty() && crossing.carsE.isEmpty() )
                        || ((crossing.carsW.peek().getTo().equals(Direction.E)
                        && Objects.requireNonNull(crossing.carsW.peek()).getTo().equals(Direction.N)))
                        && ((crossing.carsE.peek().getTo().equals(Direction.W)
                        && Objects.requireNonNull(crossing.carsE.peek()).getTo().equals(Direction.N)))
                        && Objects.requireNonNull(crossing.carsE.peek()).getTo().equals(Direction.S)) {
                    try {

                        crossing.wait();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                this.log();



            }



        } else if(from.equals(Direction.S) && to.equals(Direction.E)) {
            //SE irányba akkor mehet az autó, ha nincs WE irányú jármű

            if(crossing.carsW.isEmpty() || ((!crossing.carsW.peek().getTo().equals(Direction.E) ))) {
                //mehet
                this.log();


            } else {
                while(!crossing.carsW.isEmpty() || ((crossing.carsW.peek().getTo().equals(Direction.E) ))) {
                    try {

                        crossing.wait();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                this.log();

            }


        } else if(from.equals(Direction.S) && to.equals(Direction.W)) {
            //SW irányba akkor mehet az autó, ha nincs WE, WN, EW, ES, NS, NW irányú jármű

            if((crossing.carsW.isEmpty() && crossing.carsE.isEmpty() && crossing.carsN.isEmpty() )
                    || (Objects.requireNonNull(!crossing.carsW.peek().getTo().equals(Direction.E))
                    && Objects.requireNonNull(crossing.carsW.peek()).getTo().equals(Direction.N)
                    && ((!crossing.carsE.peek().getTo().equals(Direction.W)
                    && !Objects.requireNonNull(crossing.carsE.peek()).getTo().equals(Direction.S)))
                    && !(Objects.requireNonNull(crossing.carsN.peek()).getTo().equals(Direction.S))
                    && !(Objects.requireNonNull(crossing.carsN.peek()).getTo().equals(Direction.W)))) {
                //mehet
                this.log();


            } else {
                while(!(crossing.carsW.isEmpty() && crossing.carsE.isEmpty() && crossing.carsN.isEmpty())
                        || ((crossing.carsW.peek().getTo().equals(Direction.E)
                        && Objects.requireNonNull(crossing.carsW.peek()).getTo().equals(Direction.N)))
                        && ((crossing.carsE.peek().getTo().equals(Direction.W)
                        && Objects.requireNonNull(crossing.carsE.peek()).getTo().equals(Direction.S)))
                        && (Objects.requireNonNull(crossing.carsN.peek()).getTo().equals(Direction.S))
                        && (Objects.requireNonNull(crossing.carsN.peek()).getTo().equals(Direction.W))) {
                    try {

                        crossing.wait();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                this.log();



            }



        } else if(from.equals(Direction.N) && to.equals(Direction.S)) {
            //NS irányba akkor mehet az autó, ha nincs .WE, .EW, .WN, WS, .ES irányú jármű

            //hozzá tettem, hogy ne legyenek üres sorok
            if((crossing.carsW.isEmpty() && crossing.carsE.isEmpty() )
                    || (!crossing.carsW.isEmpty() && (!crossing.carsW.peek().getTo().equals(Direction.E)
                    && Objects.requireNonNull(crossing.carsW.peek()).getTo().equals(Direction.N)))
                    && !crossing.carsE.isEmpty() && ((!crossing.carsE.peek().getTo().equals(Direction.W)
                    && Objects.requireNonNull(crossing.carsE.peek()).getTo().equals(Direction.S))
                    && !(Objects.requireNonNull(crossing.carsW.peek()).getTo().equals(Direction.S)))) {
                //mehet
                this.log();



            } else {
                while(!(crossing.carsW.isEmpty() && crossing.carsE.isEmpty() )
                        || ((crossing.carsW.peek().getTo().equals(Direction.E)
                        && Objects.requireNonNull(crossing.carsW.peek()).getTo().equals(Direction.N)))
                        && ((crossing.carsE.peek().getTo().equals(Direction.W)
                        && Objects.requireNonNull(crossing.carsE.peek()).getTo().equals(Direction.S))
                        && (Objects.requireNonNull(crossing.carsW.peek()).getTo().equals(Direction.S)))) {
                    try {

                        crossing.wait();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                this.log();



            }


        } else if(from.equals(Direction.N) && to.equals(Direction.W)) {
            //NW irányba akkor mehet az autó, ha nincs EW irányú jármű

            if(crossing.carsE.isEmpty() || ((!crossing.carsE.peek().getTo().equals(Direction.W) ))) {
                //mehet
                this.log();



            } else {
                while(!crossing.carsE.isEmpty() || ((crossing.carsE.peek().getTo().equals(Direction.W) ))) {
                    try {

                        crossing.wait();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                this.log();

            }



        } else if(from.equals(Direction.N) && to.equals(Direction.E)) {
            //NE irányba akkor mehet az autó, ha nincs WE, WN, EW, ES, SN, SE irányú jármű

            if((crossing.carsW.isEmpty() && crossing.carsE.isEmpty() && crossing.carsS.isEmpty() )
                    || (!crossing.carsW.isEmpty() && (!crossing.carsW.peek().getTo().equals(Direction.E)
                    && Objects.requireNonNull(crossing.carsW.peek()).getTo().equals(Direction.N)))
                    && !crossing.carsE.isEmpty() && ((!crossing.carsE.peek().getTo().equals(Direction.W)
                    && Objects.requireNonNull(crossing.carsE.peek()).getTo().equals(Direction.S)))
                    && !(Objects.requireNonNull(crossing.carsS.peek()).getTo().equals(Direction.N))
                    && !(Objects.requireNonNull(crossing.carsS.peek()).getTo().equals(Direction.E))) {
                //mehet
                this.log();


            } else {
                while(!(crossing.carsW.isEmpty() && crossing.carsE.isEmpty() && crossing.carsS.isEmpty() )
                        || ((crossing.carsW.peek().getTo().equals(Direction.E)
                        && Objects.requireNonNull(crossing.carsW.peek()).getTo().equals(Direction.N)))
                        && ((crossing.carsE.peek().getTo().equals(Direction.W)
                        && Objects.requireNonNull(crossing.carsE.peek()).getTo().equals(Direction.S)))
                        && (Objects.requireNonNull(crossing.carsS.peek()).getTo().equals(Direction.N))
                        && (Objects.requireNonNull(crossing.carsS.peek()).getTo().equals(Direction.E))) {
                    try {

                        crossing.wait();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


                this.log();



            }





        }
    } // sync

}

    /**
     * Getter: honnet megy az auto
     * @return honnet megy az auto
     */
    public Direction getFrom() {
        return from;
    }


    /**
     * Getter: hova megy az auto
     * @return hova megy az auto
     */
    public Direction getTo() {
        return to;
    }


    /**
     * Setter (felulirjuk a random ID-t)
     * @param id auto ID
     */
    public void setId(int id) {this.id = id;}

    /**
     * Setter (felulirjuk a random honnet iranyt)
     * @param from honnet megy az auto
     */
    public void setFrom(Direction from) {this.from = from;}

    /**
     * Setter (felulirjuk a random hova iranyt)
     * @param to hova megy az auto
     */
    public void setTo(Direction to) {this.to = to;}

    public void log(){
        synchronized (crossing){
            System.out.println( (int)((System.currentTimeMillis()-startTime)/1000F) + " -  A(z) "+ this.id +" rendszámú autó behajt a kereszteződésbe (" +from+""+to+")");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println((int)( (System.currentTimeMillis()-startTime)/1000F) +" -  A(z) "+ this.id +" rendszámú autó elhagyta a kereszteződést  (" +from+""+to+")");
            if(from.equals(Direction.W)) {

                crossing.carsW.remove();
            } else if(from.equals(Direction.E)) {

                crossing.carsE.remove();
            } else if(from.equals(Direction.N)) {

                crossing.carsN.remove();
            } else if(from.equals(Direction.S)) {

                crossing.carsS.remove();
            }
            this.setFrom(Direction.NULL);

        }
        }




}
