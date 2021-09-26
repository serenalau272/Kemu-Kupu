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
    //List of imorted topics
    private ArrayList<SpellingTopic> TOPICS;

    @FXML private ImageView babies_button;
    @FXML private ImageView compassPoints_button;
    @FXML private ImageView colours_button;
    @FXML private ImageView daysOfWeekLoanWords_button;
    @FXML private ImageView engineering_button;
    @FXML private ImageView monthsOfYearLoanWords_button;
    @FXML private ImageView software_button;
    @FXML private ImageView uniLife_button;
    @FXML private ImageView weather_button;
    @FXML private ImageView work_button;

    //// Private Methods ////

    private void __initiliseSelectableTopic(ImageView id){
        String listName = id.getId().replace("_button", "");

        id.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            SpellingTopic topic = new SpellingTopic(listName, "./words/" + listName);
            if (TOPICS.contains(topic)){
                try {
                    MainApp.setTopic(topic);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                SystemInterface.playSound("pop");
                MainApp.setRoot("GameScreen", "Kemu Kupu - Let's Play!");
            } else {
                System.err.println("Could not select topic with id: " + listName);
            }
        });
    }

    //// Public Methods ////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        super.initialize();
        try {
            TOPICS = (ArrayList<SpellingTopic>) SystemInterface.getTopics();
        } catch (IOException e){
            System.err.println("Unable to retrieve spelling word topics " + e);
        }
        //Pssst, could we use `List<ImageView> imgs = findElms(anchorPane, ImageView.class);` with a loop here?
        __initiliseSelectableTopic(babies_button);
        __initiliseSelectableTopic(compassPoints_button);
        __initiliseSelectableTopic(colours_button);
        __initiliseSelectableTopic(daysOfWeekLoanWords_button);
        __initiliseSelectableTopic(engineering_button);
        __initiliseSelectableTopic(monthsOfYearLoanWords_button);
        __initiliseSelectableTopic(software_button);
        __initiliseSelectableTopic(uniLife_button);
        __initiliseSelectableTopic(weather_button);
        __initiliseSelectableTopic(work_button);
    }
}
