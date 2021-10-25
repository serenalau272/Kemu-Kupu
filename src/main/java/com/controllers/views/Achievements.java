package com.controllers.views;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.MainApp;
import com.components.AchievementItem;
import com.controllers.ApplicationController;
import com.controllers.fxmlComponents.AchievementType;
import com.enums.Achievement;
import com.enums.Views;
import com.models.User;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.fxml.Initializable;

/**
 * This class is the controller for the achievements modal.
 */
public class Achievements extends ApplicationController implements Initializable {

    //// Properties ////

    private User currentUser;
    @FXML
    private Label numMessage;
    @FXML
    private ImageView backButton;
    @FXML
    private ScrollPane scroll;
    @FXML
    private GridPane grid;

    private List<AchievementItem> types = new ArrayList<>();
    private List<String> achievementStrings = new ArrayList<>();

    //// Private (helper) Methods ////
    /**
     * Retrieve information from the data stored for the current user and dynamically generate the achievement types
     * accordingly.
     * @return
     */
    private List<AchievementItem> __configureData() {
        //Load data from current user
        this.currentUser = MainApp.getUser();
        this.achievementStrings = this.currentUser.getAchievements();

        // set up lists for storing the achievement types and levels unlocked
        List<AchievementItem> types = new ArrayList<>();
        List<ArrayList<Integer>> levels = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i <= 5; i++) {
            // each ArrayList will correspond to one achievements type
            levels.add(new ArrayList<>());
        }

        AchievementItem item;

        // Each 'data string' is stored in the format TYPE_LEVEL. Loop through these strings and process accordingly.
        for (String achievement : achievementStrings) {
            // Retrieve just the level number from the data string
            Integer levelNum = Achievement.getLevelFromString(achievement);
            // Add the level number to the list that corresponds to the achievement type
            switch (Achievement.getAchievementTypeFromString(achievement)) {
                case EXPLORER: {
                    levels.get(0).add(levelNum);
                    continue;
                }
                case STUDENT: {
                    levels.get(1).add(levelNum);
                    continue;
                }
                case ACHIEVER: {
                    levels.get(2).add(levelNum);
                    continue;
                }
                case SPEEDY: {
                    levels.get(3).add(levelNum);
                    continue;
                }
                case POCKETS: {
                    levels.get(4).add(levelNum);
                    continue;
                }
                case STYLISH: {
                    levels.get(5).add(levelNum);
                    continue;
                }
            }
        }

        // Loop through the levels list to process each one individually that corresponds to one achievement type
        Achievement[] allTypes = Achievement.values();        
        int counter = 0;
        for (ArrayList<Integer> typeLevels : levels) {
            // Create a new AchievementItem with a corresponding enum (from looping through the allTypes array) and 
            // the corresponding levels list
            item = new AchievementItem(allTypes[counter], typeLevels);
            // Add the item to the types list
            types.add(item);
            counter++;
        }
        return types;
    }

    //// Public Methods ////

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize();
        scroll.setFitToWidth(true);
        currentUser = MainApp.getUser();

        numMessage.setText(Integer.toString(currentUser.getNumAchievements()) + "/20");

        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> MainApp.setRoot(Views.PROFILE));

        types.addAll(this.__configureData());

        int row = 1;
        try {
            for (int i = 0; i < types.size(); i++) {
                // Load the AchievementType AnchorPane to retrieve its controller, modify its information and dynamically add to
                // the Achievements screen
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(MainApp.class.getResource("/fxmlComponents/achievementType.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                AchievementType achievementTypeController = fxmlLoader.getController();
                // use the AchievementType controller to generate an achievement type component using the retrieved information
                // and add to the Achievements screen GridPane
                achievementTypeController.setData(types.get(i));

                grid.add(anchorPane, 0, row);
                row++;

                // Set grid size and alignment
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                grid.setAlignment(Pos.CENTER);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


/**
 * Attributions:
 * Dynamic GridPane design inspired by Mahmoud Hamwi's implementation
 * GitHub repo: https://github.com/mahmoudhamwi/Fruits-Market
 */
