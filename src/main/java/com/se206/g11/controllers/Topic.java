package com.se206.g11.controllers;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.se206.g11.models.Game;
import com.se206.g11.models.SpellingTopic;
import com.se206.g11.util.Sounds;
import com.se206.g11.util.SystemIO;
import com.se206.g11.ApplicationController;
import com.se206.g11.MainApp;
import com.se206.g11.enums.Gamemode;
import com.se206.g11.enums.View;

import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

/**
 * This class is the controller for the topic screen
 */
public class Topic extends ApplicationController implements Initializable {
    //List of imported topics
    private ArrayList<SpellingTopic> TOPICS;

    @FXML private ImageView babies_button;
    @FXML private ImageView compassPoints_button;
    @FXML private ImageView colours_button;
    @FXML private ImageView daysOfWeek_button;
    @FXML private ImageView monthsOfYear_button;
    @FXML private ImageView software_button;
    @FXML private ImageView weather_button;
    @FXML private ImageView work_button;
    @FXML private ImageView feelings_button;

    //// Private Methods ////

    private void __initialiseSelectableTopic(ImageView id){
        //get topic name from button
        String listName = id.getId().replace("_button", "");

        //add handler
        id.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SpellingTopic topic = new SpellingTopic(listName, "./words/" + listName);
            if (TOPICS.contains(topic)){
                //if it is a valid topic
                try {
                    Game game = new Game(Gamemode.RANKED, topic); //TODO - ranked vs practise selection
                    MainApp.setGameState(game);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Sounds.playSoundEffect("pop");
                MainApp.setRoot(View.QUIZ);
            } else {
                System.err.println("Could not select topic with id: " + listName);
            }
        });
    }

    //// Public Methods ////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Inital setup & loading of data
        super.initialize();
        try {
            TOPICS = (ArrayList<SpellingTopic>) SystemIO.getTopics();
        } catch (IOException e){
            System.err.println("Unable to retrieve spelling word topics " + e);
        }

        //add handlers
        __initialiseSelectableTopic(babies_button);
        __initialiseSelectableTopic(compassPoints_button);
        __initialiseSelectableTopic(colours_button);
        __initialiseSelectableTopic(daysOfWeek_button);
        __initialiseSelectableTopic(monthsOfYear_button);
        __initialiseSelectableTopic(software_button);
        __initialiseSelectableTopic(uniLife_button);
        __initialiseSelectableTopic(weather_button);
        __initialiseSelectableTopic(work_button);
        __initialiseSelectableTopic(feelings_button);
    }
}
