package com.example.nagyhf_v1;

import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.io.*;
import java.util.Scanner;
import java.util.Stack;

public class GameConsole {

    /**
     *  a 'steps' a megtett lépés számoakt tartalmazza. ha txt-ből olvasunk be lehet előre megadott érték, különben nullárol kezdünk.
     *  'gameInConsole' egy GameFlow típus, melyen végezzük a játékmátrixon változtatásokat és a nagyobb számolásokat.
     *  isSolverOn egy igaz/hamis érték ,mely megadja, hogy a Solver be van-e kapcsolva. Hamisként inicializáljuk.
     */
    private GameFlow  gameInConsole;
    private int steps =0;
    private boolean isSolverOn =false;


    /**
     * Objekutumunk konstruktora. A játékot el lehet kezdeni 'start' beírásával és ki lehet lépni 'exit'-tel más parancsra 'Érvénytelen parancs.'-t ír ki
     * és egy újabb input-ot vár.
     * Majd ha megkapja a 'start'-t this->initialize()- metodus meghívódik, és break;-el kilép a végtelen ciklusból.
     */
    public GameConsole(){
        while(true){
            Scanner line =new Scanner(System.in);
            String input = line.next();
            if(input.equals("start")) {
                System.out.println("Új játékot kezdtél.");

                break;
            } else if(input.equals("exit")){
                System.out.println("Játék vége.");
                System.exit(0);
            } else {
                System.out.println("Non-valid input.");
            }
        }
        initialise();
    }


    /**
     * A metóduson belül inicializáljuk a mátrixot, emgadjuk a méretét és ez alapján állítunk elő egy new GameFlow()-t , amin dolgozni fogunk, ha a 'New_Game' kerül beírásra.
     * Ha 'Load_Game' az input, a projekt mappához csatolt 'game01.txt' fájlból beolvassuk az adatokat, kivéve az időt inicializáló értékeket, azok nem kellenek console-os megoldáshoz.
     * 'exit' paranccsal ki lehet lépni a játékból. Más parancsra 'Érvénytelen parancs.'-al tér vissza. Ha a gameInConsole iniciaizálva lett, meghívjuk a this->run() metúdust.
     *
     */
    public void initialise(){
        System.out.println("Ha új játékot kezdenél írd be: New_Game " +
                "\n" + "Ha megkezdett játékot töltenél be írd be: Load_Game " );


        while(true) {
            Scanner line =new Scanner(System.in);
            String input = line.nextLine();


            if(input.equals("New_Game")) {
                System.out.println("New Game");
                System.out.println("Adja meg a paletta méretét, melyre teljesül: 2<=[paletta méret] <=20 ");
                while(true){
                    Scanner line_size =new Scanner(System.in);

                    if(!line_size.hasNextInt()) {
                        System.out.println("Érvénytelen parancs.");

                    } else {
                        int temp = line_size.nextInt();

                        if(temp>=2 && temp<=20) {
                            System.out.println("Méret beolvasva.");
                            gameInConsole= new GameFlow(temp);
                            break;
                        } else {
                            System.out.println("Nem megfelelő formátum, vagy nem megfelelő méret.");
                        }
                    }

                }

                break;
            } else if(input.equals("Load_Game")){
                System.out.println("Load Game");
                this.LoadGame();
                break;

            } else if(input.equals("exit")) {
                System.out.println("Játék vége.");
                System.exit(0);
            } else {
                System.out.println("Érvénytelen parancs.");
            }
        }

        this.run();
    }


    /**
     * x,y koordinátákat kell megadni, egyáb parancsok:
     * 'solve': bekapcsolja a solver-t, minden kooridnáta beírás előtt print-el egy listát, ami tartalmazza a még megnyomandó gombokat.
     * 'exit': kilép a játékból.
     * 'stop': meghívja a this->stop() metódust.
     * 'save': meghívja a this->save() metódust.
     *  Ha a mátrixon minden egy színű lépésenként elelnőrizzuk a GameFlow típushoz tartozó GameOver() paranccsal, és kiirjuk hány lépésből ment végbe a játék.
     *  Ez után a játékos kilpéhet 'exit'-tel vagy 'start'-tal meghívhatja újra a this->initialize() metódust.
     *
     *  Egyéb parancsra 'Érvénytelen parancs.' -ot írunk ki és várunk egy értelmes input-ot.
     */
    public void run(){
        System.out.println("A lépéseket koordinátába add meg előszőr x, majd y koordinátát space-cel elválasztva: x y");



        while(true) {

            if(isSolverOn) {
                System.out.println("Következő mezőket kell még megnyomnod:");
                System.out.println(gameInConsole.printSolutionForConsole());
            }



            System.out.println(steps +". lépés utáni állás: ");
            gameInConsole.print();

            Scanner readLine = new Scanner(System.in);

            int x= 0;
            int y= 0;

            if(readLine.hasNextInt()){
                x = readLine.nextInt();

                if(x<1 || x> gameInConsole.size) {
                    System.out.println( "nem megfelelő érték x-nek. ( 1<=[érték]<=" + gameInConsole.size +")");
                    continue;
                }
                if(readLine.hasNextInt()) {
                    y = readLine.nextInt();
                    if(y<1 || y> gameInConsole.size) {
                        System.out.println( "nem megfelelő érték y-nak. ( 1<=[érték]<=" + gameInConsole.size+ ")");
                        continue;
                    }
                } else {
                    System.out.println("Beolvasott érték nem szám.");
                    continue;
                }
            } else {
                String str =readLine.nextLine();



                if(str.equals("exit")){
                    System.out.println("Játék vége.");
                    System.exit(0);

                } else if(str.equals("save")) {
                    this.SaveGame();
                } else if(str.equals("stop")) {
                        this.stop();
                } else if(str.equals("solve")) {
                    if(gameInConsole.size%2==0) {
                        gameInConsole.SolverEven();
                    }

                    isSolverOn =true;
                    continue;
                }
                System.out.println("Beolvasott érték nem szám vagy nem megfelelő fomrátumba lettek a koordináták megadva.");
                continue;
            }

            steps+=1;
            gameInConsole.FieldPressed(x-1,y-1);

            if(gameInConsole.getTop().getKey()==x-1 &&  gameInConsole.getTop().getValue() == y-1) {
                gameInConsole.popPath();
            } else {
                gameInConsole.setStackConsole(new Pair<>(x-1,y-1));
            }


            if(gameInConsole.GameOver()) {
                System.out.println("Gratulálok! Megnyerted a játékot " + steps + " lépésből. Ha új játékot szeretnél kezdeni, írd be hogy \"start\" .");
                while(true) {
                    Scanner startNewGame = new Scanner(System.in);

                    String temp = startNewGame.nextLine();
                    if(temp.equals("start")) {
                        steps=0;
                        isSolverOn =false;
                        this.initialise();

                    } else if(temp.equals("exit")) {
                        System.out.println("Játék vége.");
                        System.exit(0);

                    } else {
                        System.out.println("Érvénytelen parancs.");
                    }

                }
            }

        }

    }


    /**
     *  'game01.txt' fájlból betöltjük a mátrixot, majd inicializáljuk a gameInConsole()-t.
     */
    public void LoadGame(){

        Scanner scanner = null;

        try {
            scanner = new Scanner(new File("game01.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int size_ =scanner.nextInt();
        int[][] temp = new int[size_][size_];

        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp.length; j++) {
                temp[j][i] = scanner.nextInt();
            }
        }
        steps = scanner.nextInt();

        gameInConsole = new GameFlow(temp);
        System.out.println("Game loaded:");
        gameInConsole.print();

        scanner.close();
    }

    /**
     * 'game01.txt'-be kíírjuk a mátrixot az időt nullára mentjük le.
     */
    private void SaveGame() {
        try {

            PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter("game01.txt",false)));
            int[][] temp= gameInConsole.getGameMatrix();

            file.println(gameInConsole.getSize());

            String line="";
            for (int i = 0; i <temp.length; i++) {
                for (int j = 0; j < temp.length; j++) {
                    System.out.println(temp[j][i]);
                    line +=temp[j][i]+" ";
                }
                file.println(line);
                line="";
            }

            file.println(steps);

            //format miatt kell megadni, így egy Console-on játszott játék betölthető GUI-ra is és visa versa
            file.println(0);
            file.println(0);
            file.println(0);

            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     *  Meghívása után új játékot lehet kezdeni 'start', vagy kilépni 'exit'.
     *  Nem ismert parancsra 'Érvénytelen parancs.' ad vissza és vár egy értelmeset.
     */
    public void stop(){
        System.out.println("Stop beírásával megállítottad a játékot. Ha kiakarsz lépni írd be, hogy \"exit\" , ha új játékot kezdenél, írd be, hogy \"start\".");
        Scanner input = new Scanner(System.in);

        while(true) {
            String temp = input.nextLine();

            if(temp.equals("start")) {
                steps=0;
                isSolverOn =false;
                this.initialise();

            } else if(temp.equals("exit")) {
                System.out.println("Játék vége.");
                System.exit(0);

            } else {
                System.out.println("Érvénytelen parancs.");
            }

        }
    }


}
