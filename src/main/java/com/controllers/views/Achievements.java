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
import com.util.User;

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
    
    User currentUser;

    @FXML Label numLabel;
    @FXML ImageView backButton;
    @FXML ScrollPane scroll;
    @FXML GridPane grid;
    
    private List<AchievementItem> types = new ArrayList<>();
    private List<String> data = new ArrayList<>();

    private List<AchievementItem> configureData() {
        currentUser = MainApp.getUser();
        data = currentUser.getAchievements();
        
        List<AchievementItem> types = new ArrayList<>();
        List<ArrayList<Integer>> levels = new ArrayList<ArrayList<Integer>>();
        for (int i=0; i<=5; i++) {
            levels.add(new ArrayList<>());
        }

        AchievementItem item;

        for (String achievementLabel : data) {
            Integer levelNum = Achievement.getLevelFromString(achievementLabel);

            switch (Achievement.getAchievementTypeFromString(achievementLabel)){
                case EXPLORER:
                    levels.get(0).add(levelNum);
                    continue;
                case STUDENT:
                    levels.get(1).add(levelNum);
                    continue;
                case ACHIEVER:
                    levels.get(2).add(levelNum);
                    continue;
                case SPEEDY:
                    levels.get(3).add(levelNum);
                    continue;
                case POCKETS:
                    levels.get(4).add(levelNum);
                    continue;
                case STYLISH:
                    levels.get(5).add(levelNum);
                    continue;
            }
        }

        Achievement[] allTypes = {Achievement.EXPLORER, Achievement.STUDENT, Achievement.ACHIEVER, Achievement.SPEEDY, Achievement.POCKETS, Achievement.STYLISH};
        int counter = 0;
        for (ArrayList<Integer> typeLevels : levels) {
            item = new AchievementItem(allTypes[counter], typeLevels);
            types.add(item);
            counter++;
        }
        return types;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize();
        scroll.setFitToWidth(true);
        currentUser = MainApp.getUser();

        numLabel.setText(Integer.toString(currentUser.getNumAchievements()) + "/20");

        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> MainApp.setRoot(Views.PROFILE));

        types.addAll(configureData());

        int row = 1;
        try {
            for (int i=0; i<types.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(MainApp.class.getResource("/fxmlComponents/achievementType.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                AchievementType achievementTypeController = fxmlLoader.getController();
                achievementTypeController.setData(types.get(i));

                grid.add(anchorPane, 0, row);
                row++;
                
                // grid.setMinWidth(1100);
                // grid.setPrefWidth(1100); 
                // grid.setMaxWidth(1100); 

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
