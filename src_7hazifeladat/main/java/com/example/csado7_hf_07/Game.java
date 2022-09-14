package com.example.csado7_hf_07;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

//import static jdk.jfr.internal.tool.Command.addEventFilter;

public class Game extends Pane {

    private int[][] gameFields;
    private int[] level;
    private int turn;

    boolean gameNotEnded;

    Stage stage;

    protected boolean paused;

    protected final KeyCode[] keys = { KeyCode.NUMPAD1, KeyCode.NUMPAD2,
            KeyCode.NUMPAD3, KeyCode.NUMPAD4, KeyCode.NUMPAD5,
            KeyCode.NUMPAD6,KeyCode.ENTER};

    protected final KeyCode[] keys2 = { KeyCode.DIGIT1, KeyCode.DIGIT2,
            KeyCode.DIGIT3, KeyCode.DIGIT4, KeyCode.DIGIT5, KeyCode.DIGIT6};

    /**
     * init() inicializálja az értékeket Game() Pane meghívásakor.
     */
    public void init(){

        int[][] gameFields_ = new int[6][6];
        gameFields = gameFields_;

        int[] level_ = new int[6];
        level =level_;

        turn =1;
        gameNotEnded =true;
        paused=false;
        //1 -> kék 2->piros, mindig kék kezd.

    }

    /**
     *
     * @param stage_
     * @throws Exception
     *
     *  start metúdus új szállként futtatva beveszi a gombok lenyomását, Animation()-t hív és a gombok lenyomásának megfelelő Action-re
     *  új metódust hajt végre
     */
    public void start(Stage stage_) throws Exception {


        Stage newPane2 = new Animations();
        stage =newPane2;

        stage.addEventFilter(MouseEvent.ANY, event -> stage.requestFocus());
        stage.addEventHandler(KeyEvent.KEY_PRESSED,this::keyPressed);

        stage.setOnCloseRequest(event -> System.exit(0));

    }

    /**
     *
     * @param ev event, lenyomott gombra reagál:
     *           1-6 v (numlock 1-6) gombok Animation-be addAnimation()-t meghívja, és megfelelő akciókkal végrehajtja, míg a resolve nem teljesül.
     */
    protected void keyPressed(KeyEvent ev) {
       if(gameNotEnded) {

           if (ev.getCode() == keys[6]) {

               ((Animations)stage).pause();

               if(paused){
                    paused =false;
                } else {
                    paused = true;
                }
           }

           if (!paused) {
               for (int i = 0; i < 6; i++) {

                   if (ev.getCode() == keys[i] || ev.getCode() == keys2[i]) {

                       if (level[i] <= 5) {
                           gameFields[i][level[i]] = turn;

                           if (turn == 1) {
                               turn += 1;
                           } else {
                               turn -= 1;
                           }

                           if (resolve(i, level[i])) {
                               gameNotEnded = false;
                           }

                           level[i] += 1;


                           /**
                            * eseményeket kezelő kiírat mátrix, piros =1, BLUEVIOLET=2;
                            */
                    /*
                    for (int j = 0; j < gameFields.length; j++) {
                        String temp="";
                        for (int k = 0; k < gameFields.length; k++) {
                            temp +=gameFields[j][k]+" ";

                        }
                        System.out.println(temp);
                    }
*/

                           if (turn == 1) {
                               ((Animations) stage).addAnimation(8 + i * 57, 285 - (level[i] - 1) * 52, Color.RED, gameNotEnded);
                           } else {
                               ((Animations) stage).addAnimation(8 + i * 57, 285 - (level[i] - 1) * 52, Color.BLUEVIOLET, gameNotEnded);
                           }

                       }

                   }
               }
           }
       }
    }


    /**  rekurzív fuggvény mely megmondja, hogy emgnyerte e már valaki a játékot
     *
     * @param x gameFields megoldás mátrix x koordinátája
     * @param y gameFields megoldás mátrix y koordinátája
     * @return igaz, ha vége a játéknak, az nyert aki lépésen volt, hamis ha még nincs vége a játéknak
     */
    protected boolean resolve(int x, int y) {
        int plays = gameFields[x][y];


        if(x-1>=0 && y-1>=0 && gameFields[x-1][y-1]==plays){
            if(this.resolve(x-1,y-1)){
                return true;
            }
        }
        if(x-1>=0 && gameFields[x-1][y]==plays) {

            if( this.resolve(x-1,y)){
                return true;
            }


        }
        if(x-1>=0 && y+1<=5 && gameFields[x-1][y+1]==plays) {


            if(  this.resolve(x-1,y+1)){
                return true;
            }

        }

        //int score[] = new int[5];
        int score1 =1;
        int score2 =1;
        int score3 =1;
        int score4 =1;
        int score5 =1;

        for (int i = 1; i < 4; i++) {
                if(x+i<=5 && gameFields[x+i][y]==plays){
                    score1+=1;
                }
            if(x+i<=5 && y+i <=5 &&gameFields[x+i][y+i]==plays){
                score2+=1;
            }
            if(y-i>=0 && x+i<=5 && gameFields[x+i][y-i]==plays){
                score3+=1;
            }
            if(x-i>=0 && gameFields[x-i][y]==plays){
                score4+=1;
            }
            //------
            if(y-i>=0 && gameFields[x][y-i]==plays){
                score5+=1;
            }

        }


        return score4 >= 4 || score3 >= 4 || score2 >= 4 || score1 >= 4 || score5 >=4;
    }

    /**
     * bezárja az ablakot ha game_menü t is bezárjuk
     */
    protected void close(){
        Platform.exit();
    }

}
