package com.se206.g11.screens;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.se206.g11.models.SpellingTopic;
import com.se206.g11.ApplicationController;
import com.se206.g11.SystemInterface;
import com.se206.g11.MainApp;

import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

public class TopicScreenController extends ApplicationController implements Initializable {
    ArrayList<SpellingTopic> TOPICS;

    @FXML
    private ImageView babies;
    @FXML
    private ImageView compassPoints;
    @FXML
    private ImageView colours;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // try {
        //     TOPICS = (ArrayList<SpellingTopic>) SystemInterface.getTopics();
        // } catch (IOException exception){
        //     System.out.println("Unable to retrieve spelling word topics");
        // }

        // for (SpellingTopic t : TOPICS){
        //     System.out.println("Name: "+ t.getName());
        //     System.out.println("Path: "+t.getPath());
        // }

        initiliseSelectableTopic(babies);
    }

    private void initiliseSelectableTopic(ImageView id){
        id.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SpellingTopic topic = new SpellingTopic(id.getId(), "./words/" + id.getId());
            chosenTopic = topic;

            MainApp.setRoot("GameScreen", "Kemu Kupu - Let's Play!");

            // if (TOPICS.contains(topic)){
            //     chosenTopic = topic;
            //     System.out.println("Entering Game! with topic: " + topic);

            //     // MainApp.setRoot("GameScreen", "Kemu Kupu - Let's Play!");
            // } else {
            //     System.err.println("Could not initialise listview with id: " + id.toString());
            // }
        });
    }

}
