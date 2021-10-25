package com.controllers.fxmlComponents;

import java.io.FileNotFoundException;
import java.util.List;

import com.components.AchievementItem;
import com.controllers.ApplicationController;

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
    @FXML
    private ImageView level1;
    @FXML
    private ImageView level2;
    @FXML
    private ImageView level3;
    @FXML
    private ImageView level4;
    @FXML
    private ImageView level5;
    @FXML
    private Label message1;
    @FXML
    private Label message2;
    @FXML
    private Label message3;
    @FXML
    private Label message4;
    @FXML
    private Label message5;
    @FXML
    private Label type;
    @FXML
    private GridPane grid;
    @FXML
    private AnchorPane anchorPane;

    //// Private Methods ////

    private List<Node> circles;
    private List<Node> instructions;

    /**
     * Set up two lists to store the ImageView components of the badge circles and the badge labels,
     * for later modifications
     */
    private void setLists() {
        String[] level = { "level" };
        // fetch and store all the ImageView circles (that represent the badge and level colour) on this AnchorPane
        this.circles = findNodesByID(anchorPane, level);

        // fetch and store all the labels on this AnchorPane that describe each achievement badge's instructions
        String[] label = { "message" };
        this.instructions = findNodesByID(anchorPane, label);
    }

    //// Public Methods ////
    /**
     * Retrieve information from the input AchievementItem object and modify this AchievementType component
     * accordingly. This component will then be dynamically added to the Achievements screen.
     * @param achievementItem
     */
    public void setData(AchievementItem achievementItem) {
        String typeName = achievementItem.getTypeName();
        // Set the header label to be the achievement type name
        this.type.setText(typeName);

        setLists();
        // loop through each achievement level, from 1 to the maximum number of levels for this type
        for (int i = 0; i < achievementItem.getMax(); i++) {
            // levels count starting from 1
            int level = i + 1;
            ImageView circle = (ImageView) circles.get(i);
            // display the blank achievement circle
            circle.setVisible(true);
            Label label = (Label) this.instructions.get(i);
            // set the achievement instruction label with the font style for not being achieved
            label.setText(achievementItem.getAchievement().getAchievementLabel(i + 1));
            label.getStyleClass().add("not-achieved-text");
            
            // retrieve the List of levels that has been achieved for this achievement type and set the badge circle
            // colour and label font style accordingly
            if (achievementItem.getLevels().contains(level)) {
                label.getStyleClass().remove("not-achieved-text");
                label.getStyleClass().add("achieved-text");
                try {
                    setImage(achievementItem.getTypeName() + Integer.toString(level), circle);
                } catch (FileNotFoundException e) {
                    System.err.println("File for badge not found.");
                    e.printStackTrace();
                }
            }
        }

    }
}
