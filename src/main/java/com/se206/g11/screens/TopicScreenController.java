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
    @FXML
    private ImageView daysOfWeek;
    @FXML
    private ImageView daysOfWeekLoanWords;
    @FXML
    private ImageView engineering;
    @FXML
    private ImageView feelings;
    @FXML
    private ImageView monthsOfYear;
    @FXML
    private ImageView monthsOfYearLoanWords;
    @FXML
    private ImageView software;
    @FXML
    private ImageView uniLife;
    @FXML
    private ImageView weather;
    @FXML
    private ImageView work;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            TOPICS = (ArrayList<SpellingTopic>) SystemInterface.getTopics();
        } catch (IOException exception){
            System.err.println("Unable to retrieve spelling word topics");
        }

        initiliseSelectableTopic(babies);
        initiliseSelectableTopic(compassPoints);
        initiliseSelectableTopic(colours);
        initiliseSelectableTopic(daysOfWeek);
        initiliseSelectableTopic(daysOfWeekLoanWords);
        initiliseSelectableTopic(engineering);
        initiliseSelectableTopic(feelings);
        initiliseSelectableTopic(monthsOfYear);
        initiliseSelectableTopic(monthsOfYearLoanWords);
        initiliseSelectableTopic(software);
        initiliseSelectableTopic(uniLife);
        initiliseSelectableTopic(weather);
        initiliseSelectableTopic(work);
    }

    private void initiliseSelectableTopic(ImageView id){
        id.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SpellingTopic topic = new SpellingTopic(id.getId(), "./words/" + id.getId());

            if (TOPICS.contains(topic)){
                chosenTopic = topic;
                System.out.println("Entering Game! with topic: " + topic.getName());
                // MainApp.setRoot("GameScreen", "Kemu Kupu - Let's Play!");
            } else {
                System.err.println("Could not select topic with id: " + id.getId());
            }
        });
    }

}
