package com.example.csado7_hf_07;

import java.util.TimerTask;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;



public class Animations extends Stage {

        /**
         * már leérkezett golyók tárolása
         */
        List<Circle> circles =new ArrayList<>();

        private int seconds =0;

        private boolean paused= false;

        java.util.Timer tr = new java.util.Timer() ;
        Label label = new Label("Timer: 0");

        /**
         *  konstruktor, hol meghívja a mátrixot, kiírja ki kezd, inicializálja a Pane()-t.
         *  Elindítja a Timer()-t 0-ról.
         */
        public Animations(){

                Pane canvas = new Pane();
                Scene scene = new Scene(canvas, 500, 500);

                Image image = new Image("file:src/game_matrix.png",true); // Töltse be a háttérben
                //"C:\\Users\\csata\\IdeaProjects\\csado7_hf_07\\src\\game_matrix.png"
                canvas.getChildren().add(new ImageView(image));

                label.relocate(400,450);


                label.setStyle("-fx-font-weight: bold");
                canvas.getChildren().add(label);



                tr.scheduleAtFixedRate(new TimerTask(){
                        //override run method

                        @Override
                        public void run(){
                                if(!paused){
                                        seconds++;
                                        System.out.println(seconds);
                                        Platform.runLater(() -> {
                                                label.setText("Timer:  "+String.valueOf(seconds));
                                        });
                                }
                        }

                }, 0, 2000);

                setTitle("Kék van lépésen.");
                setScene(scene);
                show();

        }

        /**
         *
         * @param x koordináta amelyik oszlopba esik
         * @param y y koordináta ahova esik
         * @param color szín, amelyik lépésen van
         * @param end ha ez volt az utolsó lépés title-be kiírja kinyert, és paused=true ra állítja
         */
        public void addAnimation(int x,int y, Color color, boolean end){

                Pane canvas = new Pane();
                Scene scene = new Scene(canvas, 500, 500);

                Circle ball = new Circle(18, color);

                ball.relocate(x, 0);

                //Image image = new Image("C:\\Users\\csata\\IdeaProjects\\csado7_hf_07\\src\\game_matrix.png"); // Töltse be a háttérben
                Image image = new Image("file:src/game_matrix.png",true);
                canvas.getChildren().add(new ImageView(image));

                canvas.getChildren().add(ball);

                label.relocate(400,450);
                canvas.getChildren().add(label);


                for (Circle it:circles) {
                                canvas.getChildren().add(it);
                }

                if(end) {

                        if(color.equals(Color.RED)) {
                                setTitle("Kék van lépésen.");
                        } else {
                                setTitle("Piros van lépésen.");
                        }
                } else {
                                this.pause();

                        if(color.equals(Color.RED)){
                                setTitle("Piros nyert. ");
                        } else {
                                setTitle("Kék nyert.");
                        }
                }


                setScene(scene);
                show();

                Bounds bounds = canvas.getBoundsInLocal();
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                        new KeyValue(ball.layoutYProperty(), y)));

                timeline.play();


                circles.add(ball);


                if(end) {

                     /*
                        try {
                                synchronized (this){
                                        wait(2500);
                                }
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        }
                        if(color.equals(Color.RED)){
                                try {
                                        this.End(color,"Piros");
                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                        } else {
                                try {
                                        this.End(color,"Kék");
                                } catch (InterruptedException e) {
                                        e.printStackTrace();
                                }
                        }*/
                }


        }

        /**
         *  paused-ot átállítja ellenkezőjére, ami volt
         */
        protected void pause(){
                if(paused) {
                        paused =false;
                } else {
                        paused =true;
                }
        }
}