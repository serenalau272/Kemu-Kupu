package com.controllers.views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.models.SpellingTopic;
import com.util.SystemIO;
import com.MainApp;
import com.controllers.ApplicationController;
import com.enums.Views;

import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

/**
 * This class is the controller for the topic screen
 */
public class Topic extends ApplicationController implements Initializable {
    // List of imported topics
    private ArrayList<SpellingTopic> TOPICS;

    @FXML
    private ImageView babiesButton;
    @FXML
    private ImageView compassPointsButton;
    @FXML
    private ImageView coloursButton;
    @FXML
    private ImageView daysOfWeekButton;
    @FXML
    private ImageView monthsOfYearButton;
    @FXML
    private ImageView softwareButton;
    @FXML
    private ImageView weatherButton;
    @FXML
    private ImageView workButton;
    @FXML
    private ImageView feelingsButton;
    @FXML
    private ImageView backButton;
    //// Private Methods ////

    private void __initialiseSelectableTopic(ImageView id) {
        // get topic name from button
        String listName = id.getId().replace("Button", "");

        // add handler
        id.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SpellingTopic topic = new SpellingTopic(listName, "./words/" + listName);
            if (TOPICS.contains(topic)) {
                // if it is a valid topic
                try {
                    MainApp.getGameState().setTopic(topic);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                MainApp.setRoot(Views.QUIZ);
            } else {
                System.err.println("Could not select topic with id: " + listName);
            }
        });
    }

    //// Public Methods ////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inital setup & loading of data
        super.initialize();
        try {
            TOPICS = (ArrayList<SpellingTopic>) SystemIO.getTopics();
        } catch (IOException e) {
            System.err.println("Unable to retrieve spelling word topics " + e);
        }

        // add handlers
        __initialiseSelectableTopic(babiesButton);
        __initialiseSelectableTopic(compassPointsButton);
        __initialiseSelectableTopic(coloursButton);
        __initialiseSelectableTopic(daysOfWeekButton);
        __initialiseSelectableTopic(monthsOfYearButton);
        __initialiseSelectableTopic(softwareButton);
        __initialiseSelectableTopic(weatherButton);
        __initialiseSelectableTopic(workButton);
        __initialiseSelectableTopic(feelingsButton);

        this.backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> MainApp.setRoot(Views.GAMEMODE));
    }
}
