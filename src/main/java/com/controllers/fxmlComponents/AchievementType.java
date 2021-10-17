package com.controllers.fxmlComponents;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.components.AchievementItem;
import com.controllers.ApplicationController;
import com.enums.Achievement;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * This class is the controller for the achievement type.
 */
public class AchievementType extends ApplicationController {
    @FXML private ImageView level1;
    @FXML private ImageView level2;
    @FXML private ImageView level3;
    @FXML private ImageView level4;
    @FXML private ImageView level5;
    @FXML private Label label1;
    @FXML private Label label2;
    @FXML private Label label3;
    @FXML private Label label4;
    @FXML private Label label5;
    @FXML private Label typeLabel;
    @FXML private GridPane grid;
    @FXML private AnchorPane anchorPane;

    private List<Node> circles;
    private List<Node> labels;
    private AchievementItem achievementItem;

    private void setLists() {
        String[] level = {"level"};
        circles = findNodesByID(anchorPane, level);

        String[] label = {"label"};
        labels = findNodesByID(anchorPane, label);
    }

    public void setData(AchievementItem achievementItem) {
        this.achievementItem = achievementItem;
        String typeName = achievementItem.getTypeName();
        typeLabel.setText(typeName);

        setLists();
        for (int i=0; i<achievementItem.getMax(); i++) {
            int level = i+1;
            ImageView circle = (ImageView) circles.get(i);
            circle.setVisible(true);
            Label label = (Label) labels.get(i);
            label.setText(achievementItem.getAchievement().getAchievementLabel(i+1));
            label.getStyleClass().add("not-achieved-text");
            
            if (achievementItem.getLevels().contains(level)) {
                label.getStyleClass().remove("not-achieved-text");
                label.getStyleClass().add("achieved-text");
                try {
                    setImage(achievementItem.getTypeName()+Integer.toString(level), circle);
                } catch (FileNotFoundException e) {
                    System.err.println("File for badge not found.");
                    e.printStackTrace();
                }
            }
        }
    

    }
}
