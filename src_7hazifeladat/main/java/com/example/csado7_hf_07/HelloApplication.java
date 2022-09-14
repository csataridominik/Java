/**
 *  Készítette: Csatári Dominik
 *  2022.04.18
 *  Sajnos a feltöltött programhoz képest vannak eltérések, melyet nemt udtam futtatni a gépemen.
 *  Ezért például a megoldás mátrixom is 6x6-os nem 7x6-os, de erre jól működik.
 */

package com.example.csado7_hf_07;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {


    private boolean givenLabel =false;

    boolean ok=true;


    Game game = new Game();

    String description =( "Játék leírása:\n " +
            "A játék enterrel megállítható, a timer is megáll, ugyan ezzel a gombbal folytatható\n" +
            "felső [Title] sávba írja ki, ki van lépésen és a győztest.\n" +
            "köröket elhelyezni, 1-6 billentyűkkel lehet, attól függően, melyik sávba szeretnél rakni.");

    Label label1 = new Label("");



    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        //----
        final ToggleGroup group = new ToggleGroup();

        ToggleButton tb1 = new ToggleButton("New Game");
        tb1.setToggleGroup(group);
        tb1.setSelected(true);

        ToggleButton tb2 = new ToggleButton("Description");
        tb2.setToggleGroup(group);

        ToggleButton tb3 = new ToggleButton("Exit");
        tb3.setToggleGroup(group);
        //---
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        VBox root = new VBox();
        root.setSpacing(10);
        root.getChildren().addAll(tb1,tb2,tb3);
        Scene scene = new Scene(root, 500, 500);



        tb1.setOnAction(event -> {
            if(ok)
                try {
                    game.init();
                    //game.start(stage);
                    game.start(new Stage());
                    ok =false;
                } catch (Exception e) {
                    e.printStackTrace();
                }

        });

        root.getChildren().add(label1);
        tb2.setOnAction(event -> {
            if(!givenLabel){
                label1.setText(description);

                givenLabel =true;
            } else {
                label1.setText("");
                givenLabel=false;
            }


        });

        tb3.setOnAction(actionEvent -> {
            Platform.exit();
            game.close();
        });

        stage.setTitle("4 in a line");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}