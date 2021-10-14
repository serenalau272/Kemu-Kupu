package com.components;

import com.MainApp;
import com.enums.Status;
import com.models.Word;
import com.util.Sounds;

import javafx.animation.PauseTransition;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class ResultsList extends TextField{
    private static Node[][] inputs = new Node[5][4];
    private static StackPane root;

    public static void configureEntries(){
        root = MainApp.getStackPane();
        removeAll();
        
        int index = 1;
        for (Word word : MainApp.getGameState().getWords()){
            createElement(index, word);            
            index++;
        }

        addTile(0);
    }

    private static void createElement(int index, Word word){
        Node[] elements = new Node[4];
        
        ImageView bg = createBg(index, word);
        elements[0] = bg;

        ImageView icon = createIcon(index, word);
        elements[1] = icon;

        Label wordLabel = createWordLabel(index, word);
        elements[2] = wordLabel;

        Label inputLabel = createInputLabel(index, word);
        elements[3] = inputLabel;

        inputs[index-1] = elements;
    }

    private static ImageView createIcon(int index, Word word){
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
        
        img.setFitHeight(66);
        img.setFitWidth(74);

        img.setTranslateX(400);
        img.setTranslateY(index * 85 - 230);
        return img;
    }

    private static ImageView createBg(int index, Word word){
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
        
        img.setFitHeight(74);
        img.setFitWidth(902);
        img.setTranslateY(index * 85 - 230);
        return img;
    }

    private static Label createWordLabel(int index, Word word){
        Label l = new Label(capitaliseFirst(word.getMaori()));
        l.setTranslateY(index * 85 - 230);
        l.setTranslateX(-290);
        l.setPrefSize(264.0, 47.0);
        l.setTextFill(Color.WHITE);
        l.setFont(Font.font("System", FontWeight.BOLD, 50));

        return l;
    }

    private static Label createInputLabel(int index, Word word){
        String txt = (word.getStatus() == Status.FAILED) ? capitaliseFirst(word.getResponse().getMaori()) : "";
        Label l = new Label(txt);
        l.setTranslateY(index * 85 - 230);
        l.setTranslateX(200);
        l.setPrefSize(264.0, 47.0);
        l.setTextFill(Color.WHITE);
        l.setFont(new Font(50));
        l.setTextAlignment(TextAlignment.RIGHT);
        

        return l;
    }

    private static void removeAll(){
        if (inputs == null ) return;
        for (Node[] els : inputs){
            for (Node e : els){
                if (e != null){
                    root.getChildren().remove(e);
                }
            }
        }     
    }

    private static void addTile(int num){
        Sounds.playSoundEffect("pop");
        Node[] els = inputs[num];

        for (Node e: els){
            if (e != null){
                root.getChildren().add(e);         
            }
        }

        if (num + 1 >= 5) return;

        PauseTransition pause = new PauseTransition(Duration.millis(500));
        pause.setOnFinished(e -> addTile(num + 1));
        pause.play();
    }

    public static String capitaliseFirst(String s) {
        String s1 = s.substring(0, 1).toUpperCase(); //capatalize first letter
        return s1 + s.substring(1);
    }

}
