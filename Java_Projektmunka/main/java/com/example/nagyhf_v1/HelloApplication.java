package com.example.nagyhf_v1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class HelloApplication extends Application {
    static String[] args_;

    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        boolean console =true;

        if(!(args_.length == 0) && (args_[0].equals("-nogui") || args_[0].equals("-console"))) {

            System.out.println("-nogui started.");
            GameConsole gc = new GameConsole();
        } else {
            Game g1 =new Game();
        }
    }

    public static void main(String[] args) {
        args_ =args;
        launch();
    }

}