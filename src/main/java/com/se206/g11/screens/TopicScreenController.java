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
    private ImageView babies_button;
    @FXML
    private ImageView compassPoints_button;
    @FXML
    private ImageView colours_button;
    @FXML
    private ImageView daysOfWeek_button;
    @FXML
    private ImageView daysOfWeekLoanWords_button;
    @FXML
    private ImageView engineering_button;
    @FXML
    private ImageView feelings_button;
    @FXML
    private ImageView monthsOfYear_button;
    @FXML
    private ImageView monthsOfYearLoanWords_button;
    @FXML
    private ImageView software_button;
    @FXML
    private ImageView uniLife_button;
    @FXML
    private ImageView weather_button;
    @FXML
    private ImageView work_button;

    private void initiliseSelectableTopic(ImageView id){
        String listName = id.getId().replace("_button", "");

        id.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SpellingTopic topic = new SpellingTopic(listName, "./words/" + listName);
            System.out.println("Topic " + topic.getName());
            if (TOPICS.contains(topic)){
                try {
                    MainApp.setTopic(topic);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("Entering Game! with topic: " + topic.getName());
                MainApp.setRoot("GameScreen", "Kemu Kupu - Let's Play!");
            } else {
                System.err.println("Could not select topic with id: " + listName);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.stageInit();
        try {
            TOPICS = (ArrayList<SpellingTopic>) SystemInterface.getTopics();
        } catch (IOException e){
            System.err.println("Unable to retrieve spelling word topics " + e);
        }
        //Pssst, could we use `List<ImageView> imgs = findElms(anchorPane, ImageView.class);` with a loop here?
        initiliseSelectableTopic(babies_button);
        initiliseSelectableTopic(compassPoints_button);
        initiliseSelectableTopic(colours_button);
        initiliseSelectableTopic(daysOfWeek_button);
        initiliseSelectableTopic(daysOfWeekLoanWords_button);
        initiliseSelectableTopic(engineering_button);
        initiliseSelectableTopic(feelings_button);
        initiliseSelectableTopic(monthsOfYear_button);
        initiliseSelectableTopic(monthsOfYearLoanWords_button);
        initiliseSelectableTopic(software_button);
        initiliseSelectableTopic(uniLife_button);
        initiliseSelectableTopic(weather_button);
        initiliseSelectableTopic(work_button);
    }
}
