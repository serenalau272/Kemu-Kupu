package com.se206.g11.screens;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.se206.g11.models.SpellingTopic;
import com.se206.g11.ApplicationController;
import com.se206.g11.SystemInterface;

import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

public class TopicScreenController extends ApplicationController implements Initializable {
    ArrayList<SpellingTopic> TOPICS;

    @FXML
    private ImageView babies;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            TOPICS = (ArrayList<SpellingTopic>) SystemInterface.getTopics();
        } catch (IOException exception){
            System.out.println("Unable to retrieve spelling word topics");
        }

        initiliseSelectableTopic(babies);
    }

    private void initiliseSelectableTopic(ImageView id){
        id.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SpellingTopic topic = new SpellingTopic(id.getId(), "./words/" + id.getId());
            if (TOPICS.contains(topic)){
                chosenTopic = topic;
                System.out.println("Entering Game!");
                transitScene("game", event);
            } else {
                System.err.println("Could not initialise listview with id: " + id.toString());
            }
        });
    }

}
