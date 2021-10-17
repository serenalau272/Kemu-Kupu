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
import com.enums.View;
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
    User currentUser;

    @FXML Label numLabel;
    @FXML ImageView backButton;
    @FXML ScrollPane scroll;
    @FXML GridPane grid;
    
    private List<AchievementItem> types = new ArrayList<>();

    // mock backend data:
    private ArrayList<String> data = new ArrayList<>();

    private void configureMockData() {
        data.add("EXPLORER_1");
        data.add("EXPLORER_2");
        data.add("STUDENT_5");
        data.add("ACHIEVER_2");
        data.add("SPEEDY_1");
        data.add("POCKETS_3");
    }

    private List<AchievementItem> getData() {
        List<AchievementItem> types = new ArrayList<>();
        Achievement achievementType;
        List<Integer> explorerLevels = new ArrayList<>();
        List<Integer> studentLevels = new ArrayList<>();
        List<Integer> achieverLevels = new ArrayList<>();
        List<Integer> speedyLevels = new ArrayList<>();
        List<Integer> pocketLevels = new ArrayList<>();
        List<Integer> stylishLevels = new ArrayList<>();
        
        AchievementItem item;
        configureMockData();

        for (String achievementLabel : data) {
            String[] components = achievementLabel.split("_");
            achievementType = Achievement.fromString(components[0]);
            Integer levelNum = Integer.parseInt(components[1]);

            switch (achievementType){
                case EXPLORER:
                    explorerLevels.add(levelNum);
                    continue;
                case STUDENT:
                    studentLevels.add(levelNum);
                    continue;
                case ACHIEVER:
                    achieverLevels.add(levelNum);
                    continue;
                case SPEEDY:
                    speedyLevels.add(levelNum);
                    continue;
                case POCKETS:
                    pocketLevels.add(levelNum);
                    continue;
                case STYLISH:
                    stylishLevels.add(levelNum);
                    continue;
            }
        }

        item = new AchievementItem(Achievement.EXPLORER, explorerLevels);
        types.add(0, item);
        item = new AchievementItem(Achievement.STUDENT, studentLevels);
        types.add(0, item);
        item = new AchievementItem(Achievement.ACHIEVER, achieverLevels);
        types.add(0, item);
        item = new AchievementItem(Achievement.SPEEDY, speedyLevels);
        types.add(3, item);
        item = new AchievementItem(Achievement.POCKETS, pocketLevels);
        types.add(4, item);
        item = new AchievementItem(Achievement.STYLISH, stylishLevels);
        types.add(5, item);

        return types;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize();
        currentUser = MainApp.getUser();

        numLabel.setText(Integer.toString(currentUser.getNumAchievements()) + "/18");

        backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> MainApp.setRoot(View.PROFILE));

        types.addAll(getData());

        int row = 1;
        try {
            for (int i=0; i<types.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(MainApp.class.getResource("/fxmlComponents/achievementType.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                AchievementType achievementTypeController = fxmlLoader.getController();
                achievementTypeController.setData(types.get(i));

                grid.add(anchorPane, 1, row);
                row++;
                
                grid.setMinWidth(1100);
                grid.setPrefWidth(1100); 
                grid.setMaxWidth(1100); 

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
