package com.example.nagyhf_v1;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import java.util.TimerTask;


public class Game extends Stage {

    //Timer
    java.util.Timer tr = new java.util.Timer() ;
    Label timerLabel = new Label("");
    protected int seconds =0;
    protected int minutes =0;
    protected int hours =0;
    protected boolean TimerStart =false;
    protected int steps=0;


    //az ajánlott mező amit fehérre színezünk ha solver on
    protected Pair<Integer,Integer> recommended;


    protected MenuBar menuBar;
    protected Menu mGame, mSolver, mExtra;

    //mGame
    protected MenuItem mExit,mNewGame,mSave,mLoad;
    //newGame is already in action
    protected boolean isGameOver =false;



    //mSolver
    protected MenuItem mOn,mOff;
    protected boolean isSolverOn=false;
    //mExtra
    protected MenuItem mRedBlack, mGreenOrange, mBlueRed,mColour4, mColour5;
    protected boolean ColourCanBeChanged=false;

    //Textfield for creating new game
    protected TextField text;
    protected Label label1 = new Label("");

    private VBox vbox;
    GridPane root = new GridPane();


    //Játék mátrix mérete
    protected int size=0;

    //Reprezental rectangle
    protected Rectangle[][] allRect =null;

    protected GameFlow GameEngine;
    protected boolean PressedField=false;


    //solver on/off
    protected boolean solverState=false;

    /**
     *  konstruktor, ahol GUI-s elemeket menubar-t inicializáljuk, action-re meghívjuk az adott fv-eket.
     */
    public Game() {
        setTitle("Game");

        menuBar = new MenuBar();
        vbox = new VBox(menuBar, root);

        mGame = new Menu("Game");
        mSolver = new Menu("Solver");
        mExtra = new Menu("Change Colour");
        menuBar.getMenus().addAll(mGame, mSolver, mExtra);

        mNewGame = new MenuItem("New Game");
        mLoad = new MenuItem("Load Game");
        mSave = new MenuItem("Save Game");
        mExit = new MenuItem("Exit");
        mGame.getItems().addAll(mNewGame, mLoad,mSave,mExit);

        mOn = new MenuItem("On");
        mOff = new MenuItem("Off");
        mSolver.getItems().addAll(mOff, mOn);

        //Colour change
        mBlueRed = new MenuItem("Blue - Red");
        mExtra.getItems().addAll(mBlueRed);

        mGreenOrange = new MenuItem("Brown - Yellow Green");
        mExtra.getItems().addAll(mGreenOrange);

        mRedBlack = new MenuItem("Red - Black");
        mExtra.getItems().addAll(mRedBlack);

        mColour4 = new MenuItem("Maroon - Deep Pink");
        mExtra.getItems().addAll(mColour4);


        mColour5 = new MenuItem("Dark Green - Light Salmon");
        mExtra.getItems().addAll(mColour5);

        //called action
        mExit.setOnAction(event -> System.exit(0));


        mNewGame.setOnAction(event -> {
            seconds =0;
            hours =0;
            minutes =0;

            if(this.allRect != null) {
                for (int i = 0; i < allRect.length; i++) {
                    for (int j = 0; j < allRect.length; j++) {
                        //allRect[j][i].setFill(Color.WHITE);
                        root.getChildren().remove(allRect[j][i]);
                    }
                }
            }


                text = new TextField("Write here!");
                root.add(text, 0, 0);
                text.setOnAction(this::rememberText);

                isGameOver = true;

        });

        mLoad.setOnAction(event -> {
            if(this.allRect != null) {
                for (int i = 0; i < allRect.length; i++) {
                    for (int j = 0; j < allRect.length; j++) {
                        root.getChildren().remove(allRect[j][i]);
                    }
                }
            }
            LoadGame();
        });

        mSave.setOnAction(event ->SaveGame());


        //Colour change Actions
        mRedBlack.setOnAction(event -> {
            if(ColourCanBeChanged)
            ChangeColour(Color.RED,Color.BLACK);
        });

        mGreenOrange.setOnAction(event -> {
            if(ColourCanBeChanged)
                ChangeColour(Color.BROWN,Color.YELLOWGREEN);
        });

        mBlueRed.setOnAction(event -> {
            if(ColourCanBeChanged)
                ChangeColour(Color.BLUE,Color.RED);
        });

        mColour4.setOnAction(event -> {
            if(ColourCanBeChanged)
                ChangeColour(Color.MAROON,Color.DEEPPINK);
        });

        mColour5.setOnAction(event -> {
            if(ColourCanBeChanged)
                ChangeColour(Color.DARKGREEN,Color.LIGHTSALMON);
        });

        //Négyzetek nyomkodása
        root.setOnMouseClicked(mouseEvent -> {
            if(!isGameOver) {


                int Field_x = (int) (mouseEvent.getX() / 35);
                int Field_y = (int) (mouseEvent.getY() / 35);

                if (Field_x < size && Field_y < size) {
                    steps+=1;



                    if (isSolverOn && (Field_x != recommended.getKey() || Field_y != recommended.getValue())) {
                        GameEngine.setStack(new Pair<>(Field_x, Field_y));

                    } else if (!isSolverOn) {
                        GameEngine.setStack(new Pair<>(Field_x, Field_y));

                    } else if (isSolverOn && Field_x == recommended.getKey() && Field_y == recommended.getValue()) {
                        recommended = GameEngine.popPath();
                    }

                    GameEngine.FieldPressed(Field_x, Field_y);
                    this.setGame(size, false);

                }
            }
        });

        mOn.setOnAction(event -> {
            if(!isSolverOn && solverState){
                isSolverOn =true;
                Solver();
            }
        });

        mOff.setOnAction(event -> {
            if(isSolverOn && solverState) {
                isSolverOn = false;
                GameEngine.setStack(recommended);
                setGame(size,false);
                //Solver();
            }
        });

        tr.scheduleAtFixedRate(new TimerTask(){
            //override run method

            @Override
            public void run(){
                if(!isGameOver && TimerStart){
                    seconds++;
                    if(seconds ==60){
                        seconds =0;
                        minutes+=1;

                        if(minutes==60){
                            minutes=0;
                            hours+=1;
                        }
                    }
                    System.out.println(seconds);
                    Platform.runLater(() -> {

                        timerLabel.setText("Timer:  "+String.valueOf(hours) +" : "+String.valueOf(minutes) +" : "+String.valueOf(seconds));
                    });
                }
            }
        }, 0, 2000);

        vbox.getChildren().add(timerLabel);


            //set scene
            Scene scene = new Scene(vbox, 800, 800);
            setScene(scene);

            show();
        }


    /**
     *
     * @param size ebben a méretben adjuk meg, hány kockát kell kirajzolni
     * @param setup Ha bool igaz, akkor inicializáló blokk fut le, ha hamis akkor újra betölti a megváltoztatott Rectangle-ekkel.
     *              Ha vége a játéknak a játékost a gyözelemről és a lpések számáról Title mezőbe értesíti.
     */
    public void setGame(int size,boolean setup){

        int[][] temp = GameEngine.getGameMatrix();

        if(setup){

            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {
                    Rectangle rect = new Rectangle(35,35);

                    if(temp[j][i] == 1){
                        rect.setFill(GameEngine.getTeam1());
                    } else {
                        rect.setFill(GameEngine.getTeam2());
                    }

                    allRect[j][i] =rect;

                    root.add(rect,j,i);
                }
                TimerStart =true;

            }
        } else {
            boolean BeforeChange=true;

            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {

                    if(temp[j][i] == 1){
                        allRect[j][i].setFill(GameEngine.getTeam1());
                    } else {
                        allRect[j][i].setFill(GameEngine.getTeam2());
                    }


                    if(BeforeChange && isSolverOn && j==recommended.getKey() && i==recommended.getValue()) {
                        if(recommended.getKey() ==-1) {
                            break;
                        }

                        BeforeChange =false;
                        allRect[j][i].setFill(Color.WHITE);
                    }

                }
            }

            if(GameEngine.GameOver()) {
                setTitle("Vége a játéknak, gratulálok!    Lépések száma: "+steps+ "     ( Time:  "+hours+":"+minutes+":"+seconds+")");


                isGameOver =true;
                TimerStart =false;
                isSolverOn=false;
                solverState =false;
            }
        }

    }


    /**
     *
     * @param ev TextField-action jét ellenőrzi, hogy szám-e és, ha szám 2 és 20 közé eső érték e. Ha igen GameFlow típusú GameEngine-t hozza létre.
     */
    private void rememberText (ActionEvent ev) {
        String s = text.getText();
        if(s.matches("-?(0|[1-9]\\d*)")){
            if(Integer.parseInt(s)>20 || Integer.parseInt(s)<2) {
                label1.setText("A megadott érték string vagy nem esik bele az intervallumba!\nÉrték intervallum: 2<[érték]<20\nMegadott értéked: " + s);
            } else {
                size = Integer.parseInt(s);
                allRect = new Rectangle[size][size];

                root.getChildren().remove(text);
                root.getChildren().remove(label1);
                isGameOver =false;
                setTitle("New Game");

                GameEngine = new GameFlow(size); //GameFlow declare, itt mert setGame rekurzív
                ColourCanBeChanged =true;

                solverState=true;
                isSolverOn =false;


                this.setGame(size,true);

            }
        } else {
            label1.setText("A megadott érték string vagy nem esik bele az intervallumba!\nÉrték intervallum: 2<[érték]<20\nMegadott értéked: " + s);
            root.add(label1,0,1);
        }
        steps =0;
    }

    /**
     * 'game01.txt'-ből betölti a mátrixot.
     */
    private void LoadGame(){

        Scanner scanner = null;
        try {

            scanner = new Scanner(new File("game01.txt"));
        //scanner = new Scanner(new File("C:\\Users\\csata\\Desktop\\Egyetem\\Java\\HW_JAVA\\projekt\\csado7_projekt\\src\\main\\java\\com\\example\\nagyhf_v1\\game01.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int size_ = Objects.requireNonNull(scanner).nextInt();

        int[][] temp = new int[size_][size_];

        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp.length; j++) {
                    temp[j][i] = scanner.nextInt();
                }
            }

        steps = scanner.nextInt();
        hours = scanner.nextInt();
        minutes = scanner.nextInt();
        seconds = scanner.nextInt();

        GameEngine = new GameFlow(temp);
        ColourCanBeChanged =true;
        isGameOver =false;
        solverState=true;

        size= GameEngine.getSize();
        allRect = new Rectangle[size][size];

        setGame(size,true);

        setTitle("New Game");

        scanner.close();
    }

    /**
     * 'game01.txt'-be menti el a játékot.
     */
    private void SaveGame() {
        try {

            PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter("game01.txt",false)));
            int[][] temp= GameEngine.getGameMatrix();
            file.println(GameEngine.getSize());


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
            file.println(hours);
            file.println(minutes);
            file.println(seconds);



            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * Megváltoztatja a színeket.
     *
     * @param c1 Menubar ba kiválasztott színkombináció első színe.
     * @param c2 Menubar ba kiválasztott színkombináció második színe.
     */
    public void ChangeColour(Color c1, Color c2){
        GameEngine.setTeam1(c1);
        GameEngine.setTeam2(c2);
        setGame(size,false);
    }


    /**
     *  Mátrix-ra solver-t hívunk, attól függően, hogy páros/páratlan a mérete, más GaameFlow metódus fut le.
     */
    public void Solver(){
        if(GameEngine.getGameMatrix().length %2 ==0){
            GameEngine.SolverEven();
        }

        recommended=GameEngine.popPath();

        setGame(size,false);
    }
}
