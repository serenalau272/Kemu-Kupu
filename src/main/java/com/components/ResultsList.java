package com.components;

import com.MainApp;
import com.enums.Status;
import com.models.Word;
import com.util.Sounds;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/**
 * ResultsList is used to dynamically generating the results list for the
 * results screen
 */
public class ResultsList extends TextField {
    private static Node[][] inputs = new Node[5][4]; // an array of size 5 for 5 results, each comprised of 4 elements
    private static StackPane root;

    /**
     * Create entries
     */
    public static void configureEntries() {
        root = MainApp.getStackPane();
        removeAll(); // remove all current entries if they do exist

        int index = 1;
        for (Word word : MainApp.getGameState().getWords()) {
            createElement(index, word); // create element
            index++;
        }

        addTile(0); // add the first tile to set off the animation queue
    }

    /**
     * Create element
     * 
     * @param index the index of the word to be created
     * @param word  the word to create the element for
     */
    private static void createElement(int index, Word word) {
        // create result item with the four elements
        Node[] elements = new Node[4];

        // add background
        ImageView bg = createBg(index, word);
        elements[0] = bg;

        // add icon (correct, incorrect, skipped)
        ImageView icon = createIcon(index, word);
        elements[1] = icon;

        // add word label
        elements[2] = createWordLabel(index, word);

        // add user input label (only if incorrect)
        elements[3] = createInputLabel(index, word);

        // add result item to inputs
        inputs[index - 1] = elements;
    }

    /**
     * create icon element (correct, incorrect, skipped)
     * 
     * @param index
     * @param word
     */
    private static ImageView createIcon(int index, Word word) {
        ImageView img;

        switch (word.getStatus()) {
        case MASTERED:
            img = new ImageView(new Image(MainApp.class.getResource("/assets/Results/tick.png").toString(), true));
            break;
        case FAILED:
            img = new ImageView(new Image(MainApp.class.getResource("/assets/Results/cross.png").toString(), true));
            break;
        default:
            img = new ImageView(new Image(MainApp.class.getResource("/assets/Results/skip.png").toString(), true));
            break;
        }

        // configure position
        img.setFitHeight(66);
        img.setFitWidth(74);
        img.setTranslateX(400);
        img.setTranslateY(index * 85 - 230);

        return img;
    }

    /**
     * create background
     * 
     * @param index
     * @param word
     */
    private static ImageView createBg(int index, Word word) {
        ImageView img;

        switch (word.getStatus()) {
        case MASTERED:
            img = new ImageView(new Image(MainApp.class.getResource("/assets/Results/green.png").toString(), true));
            break;
        case FAILED:
            img = new ImageView(new Image(MainApp.class.getResource("/assets/Results/red.png").toString(), true));
            break;
        default:
            img = new ImageView(new Image(MainApp.class.getResource("/assets/Results/brown.png").toString(), true));
            break;
        }

        // configure position
        img.setFitHeight(74);
        img.setFitWidth(902);
        img.setTranslateY(index * 85 - 230);

        return img;
    }

    /**
     * create word element
     * 
     * @param index
     * @param word
     */
    private static Label createWordLabel(int index, Word word) {
        Label l = new Label(capitaliseFirst(word.getMaori()));

        // configure position
        l.setTranslateY(index * 85 - 230);
        l.setTranslateX(20);
        l.setTextAlignment(TextAlignment.LEFT);

        // configure font
        l.setPrefSize(900, 47.0);
        l.setTextFill(Color.WHITE);
        Font font = Font.loadFont(MainApp.class.getResource("/styles/fonts/Poppins-Bold.ttf").toExternalForm(), 42);
        l.setFont(font);

        return l;
    }

    /**
     * create input label if failed
     * 
     * @param index
     * @param word
     */
    private static Label createInputLabel(int index, Word word) {
        String txt = (word.getStatus() == Status.FAILED) ? capitaliseFirst(word.getResponse().getMaori()) : "";
        Label l = new Label(txt);

        // set position
        l.setAlignment(Pos.CENTER_RIGHT);
        l.setTranslateY(index * 85 - 230);
        l.setTranslateX(200);

        // configure font
        l.setPrefSize(300, 47.0);
        l.setTextFill(Color.WHITE);
        Font font = Font.loadFont(MainApp.class.getResource("/styles/fonts/Poppins-Regular.ttf").toExternalForm(), 42);
        l.setFont(font);

        return l;
    }

    /**
     * remove all elements if there are elements currently present
     */
    private static void removeAll() {
        if (inputs == null)
            return;
        for (Node[] els : inputs) {
            for (Node e : els) {
                if (e != null) {
                    root.getChildren().remove(e);
                }
            }
        }
    }

    /**
     * add the input with index num. this calls the next tile if it does exist after
     * 0.5 seconds
     * 
     * @param num
     */
    private static void addTile(int num) {
        Sounds.playSoundEffect("drip");
        Node[] els = inputs[num];

        // add elements of this tile
        for (Node e : els) {
            if (e != null) {
                root.getChildren().add(e);
            }
        }

        // endif there's no more tiles to add
        if (num + 1 >= 5)
            return;

        // wait and add next tile
        PauseTransition pause = new PauseTransition(Duration.millis(500));
        pause.setOnFinished(e -> addTile(num + 1));
        pause.play();
    }

    /**
     * capitalise the first letter of a given string
     * 
     * @param s
     * @return
     */
    public static String capitaliseFirst(String s) {
        String s1 = s.substring(0, 1).toUpperCase(); // capitalize first letter
        return s1 + s.substring(1);
    }

}
