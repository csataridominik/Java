package com.example.nagyhf_v1;

import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.Random;
import java.util.Stack;

public class GameFlow {

    Random random = new Random();

    //matrix
    protected int[][] GameMatrix;
    protected int size;
    protected Color Team1=Color.BLUE;
    protected Color Team2 =Color.RED;

    protected Stack<Pair<Integer,Integer> > stack = new Stack<>();

    protected int[][] occurrenceMatrix;


    /** konstruktor, ahol átveszünk egy int[][]-et
     *
     * @param matrix
     */
    public GameFlow(int[][] matrix) {
        GameMatrix = matrix;
        size = matrix.length;

    }

    /** Konstruktor, ahol size-zal hívjuk meg
     *
     * @param size ez lesz a GameMatrix[][] mérete.
     *             Ha páros legenerálunk egy teljesen random felállást, ami még nem végeredmény.
     *             Ha páratlan meghívjuk az initialize() metúdust.
     */
    public GameFlow(int size){
        this.size =size;
        GameMatrix = new int[size][size];

        if(size%2 ==0) {
            do {
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        //(maximum – minimum) + 1) + minimum
                        GameMatrix[i][j] = random.nextInt(2);
                    }
                }
            } while (GameOver());

        } else {
            this.initialize();
        }

    }

    /**
     *  páratlan méretű mátrix esetén meghívjuk, és random számú lépést teszünk meg az adott intervallumon belül, amivel feltöltjük a this->stack-et.
     *  Ha ez végállapot rekurzívan újra hívjuk.
     */
    public void initialize() {

        //(max - min) + 1)
        System.out.println("Páratlan.");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                GameMatrix[j][i] =0;
            }
        }

        int r =-1;
        while(!(r<=(int)Math.pow((double)size,1.5) && r>= (int)size/2)){
            r = (int)Math.pow((double)size,1.5)-(int)size/2+1;
        }
        int random_steps= random.nextInt(r);

        for (int i = 0; i < random_steps; i++) {
            Pair<Integer,Integer> temp = new Pair<>(random.nextInt(size),random.nextInt(size));

            stack.add(temp);
            FieldPressed(temp.getKey(),temp.getValue());

        }

        if(GameOver()) {
            this.initialize();
        }

    }

    /**
     *
     * @return GameMátrixot visszaadja
     */
    public int[][] getGameMatrix() {
        return GameMatrix;
    }

    /**
     *
     * @return Color1 színe
     */
    public Color getTeam1() {
        return Team1;
    }

    /**
     *
     * @return Color2 színe
     */
    public Color getTeam2() {
        return Team2;
    }

    /**
     *
     * @return GameMatrix mérete
     */
    public int getSize() {
        return size;
    }

    /**
     *
     * @param team1 első szín beálíltása
     */
    public void setTeam1(Color team1) {
        Team1 = team1;
    }

    /**
     *
     * @param team2 Második szín beállítása
     */
    public void setTeam2(Color team2) {
        Team2 = team2;
    }


    /** Lenyomott gomb mezők változtatása
     *
     * @param x sorindex koordinátája
     * @param y oszlopindex koordinátája
     *
     */
    public void FieldPressed(int x, int y){

        swap(x,y);
        int FieldChanged = GameMatrix[x][y];

        for (int i = 0; i < 4; i++) {
            int temp_x = x;
            int temp_y = y;

            while(temp_x<size && temp_x>=0 && temp_y<size && temp_y>=0){

                if(i==0) {
                    //balra
                    swap(temp_x,temp_y);
                    temp_x -=1;
                } else if(i==1){
                    //jobbra
                    swap(temp_x,temp_y);
                    temp_x +=1;
                } else if(i==2){
                    //fel
                    swap(temp_x,temp_y);
                    temp_y +=1;
                } else if(i==3) {
                    //le
                    swap(temp_x,temp_y);
                    temp_y -=1;
                }
            }

            GameMatrix[x][y] = FieldChanged;


        }
        //print();
    }

    /** (x,y) helyen álló érték megváltoztatása
     *
     * @param x sorindex koordinátája
     * @param y oszlopindex koordinátája
     */
    public void swap(int x, int y) {
        if(GameMatrix[x][y] ==0){
            GameMatrix[x][y] =1;
        } else {
            GameMatrix[x][y] =0;
        }
    }

    /**
     * kiír
     */
    public void print(){
        for (int j = 0; j < GameMatrix.length; j++) {
            String temp="";
            for (int k = 0; k < GameMatrix.length; k++) {
                temp +=GameMatrix[k][j]+" ";

            }
            System.out.println(temp);
        }

        System.out.println("");
    }

    /**
     *
     * @param matrix adott mátrixt kiír
     */
    public void print_Matrix(int[][] matrix){
        for (int j = 0; j < matrix.length; j++) {
            String temp="";
            for (int k = 0; k < matrix.length; k++) {
                temp +=matrix[k][j]+" ";

            }
            System.out.println(temp);
        }

        System.out.println("");
    }

    /**
     *
     * @param matrix GameMatrix beállítása
     */
    public void setGameMatrix(int[][] matrix){
        GameMatrix =matrix;
    }

    public void setStack(Pair<Integer, Integer> p) {
            stack.add(p);
    }


    /**
     *
     * @param p Console implemtálásához this->stack változtatása
     */
    public void setStackConsole(Pair<Integer, Integer> p) {
        if(stack.contains(p)) {
            stack.remove(p);
        } else {
            stack.add(p);
        }


    }

    /**
     *
     * @return top elemet leszedi a stack-ről és azzal visszatér
     */
    public Pair<Integer,Integer> popPath(){
        if(!stack.isEmpty()){
            return stack.pop();
        }else {
            return new Pair<>(-1,-1);
        }
    }

    /**
     *
     * @return stack top elemével visszatér
     */
    public Pair<Integer,Integer> getTop() {
        if(!stack.isEmpty()){
            return stack.peek();
        } else {
            return new Pair<>(-1,-1);
        }

    }

    /**
     *
     * @return igaz/hamis:
     * igaz ha minden mező egy színű
     * hami ha ez nem teljesül.
     */
    public boolean GameOver(){
        int count1=0;
        int count2=0;

        for (int i = 0; i < GameMatrix.length; i++) {
            for (int j = 0; j < GameMatrix.length; j++) {
                if(GameMatrix[j][i] ==0) {
                    count1 +=1;
                } else {
                    count2 +=1;
                }
            }
        }

        return count1 == size * size || count2 == size * size;
    }


    /**
     * Páros méretű mátrixra path() megpakolása.
     */
    public void SolverEven(){
        Stack<Pair<Integer, Integer> >count1= new Stack<>();
        Stack<Pair<Integer, Integer> >count2= new Stack<>();

        occurrenceMatrix = new int[GameMatrix.length][GameMatrix.length];


        for (int i = 0; i < occurrenceMatrix.length; i++) {
            for (int j = 0; j < occurrenceMatrix.length; j++) {
                occurrenceMatrix[j][i]=0;
            }
        }

        for (int i = 0; i < GameMatrix.length; i++) {
            for (int j = 0; j < GameMatrix.length; j++) {
                if(GameMatrix[j][i] ==0) {
                    count1.add(new Pair<>(j,i));
                } else {
                    count2.add(new Pair<>(j,i));
                }
            }
        }



        if(count1.size() >= count2.size()) {

              while(!count2.isEmpty()){
                    Pair<Integer, Integer> temp = count2.pop();
                    MakePathEven_v2(temp.getKey(), temp.getValue());
            }
        } else {
            while (!count1.isEmpty()){
                Pair<Integer, Integer> temp =count1.pop();
                MakePathEven_v2(temp.getKey(), temp.getValue());
            }
        }

        for (int i = 0; i < occurrenceMatrix.length; i++) {
            for (int j = 0; j < occurrenceMatrix.length; j++) {
                if(occurrenceMatrix[j][i] ==0 || occurrenceMatrix[j][i]%2==0) {
                    occurrenceMatrix[j][i] =0;
                } else {
                    occurrenceMatrix[j][i] =1;
                }
            }
        }

        for (int i = 0; i < occurrenceMatrix.length; i++) {
            for (int j = 0; j < occurrenceMatrix.length; j++) {
                if(occurrenceMatrix[j][i] ==1) {
                    stack.add(new Pair<>(j,i));
                }

            }
        }

    }


    //2n+1 lépést hajtunk végre
    public void MakePathEven_v2(int x, int y ){

        occurrenceMatrix[x][y] +=1;

        for (int i = 0; i < size; i++) {
            occurrenceMatrix[x][i] +=1;
            occurrenceMatrix[i][y] +=1;
        }

    }

    /**
     *
     * @return stack tartalma String formátumba.
     */
    public String printSolutionForConsole(){
        String str ="";

        for (Pair<Integer,Integer> e : stack) {

            str += "[" + (e.getKey()+1)+ " " + (e.getValue()+1)+"]\n";

        }
        return str;
    }

}
