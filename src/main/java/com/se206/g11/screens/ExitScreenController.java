package com.se206.g11.screens;
import java.net.URL;
import java.util.ResourceBundle;

import com.se206.g11.ApplicationController;

import javafx.fxml.Initializable;
import javafx.application.Platform;

public class ExitScreenController extends ApplicationController implements Initializable {
    
    public ExitScreenController(){
        System.out.println("In constructor!!!");
        // try {
        //     Thread.sleep(5000);
        // } catch (InterruptedException exception){

        // }

        // Platform.exit();
    }

    public class exitPlatform implements Runnable {
        public void run() {
			Platform.exit();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("hi");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {

        }
        Thread exit = new Thread(() -> {
            Platform.exit();
        });

        exit.start();
    }    
    
}
