package com.components;

import java.util.Random;

import com.MainApp;
import com.controllers.views.Quiz;
import com.enums.Status;
import com.models.Word;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class InputField extends TextField{
    //configured fields
    private static StackPane root;
    private static ImageView submit;
    private static Quiz controller;

    //word specific fields
    private static TextField[] inputItems;
    private static Label[] hintItems;
    private static int wordSize;
    private static Word currentWord;
    private static int cursor;
    private static boolean isInputEmpty;

    //fields for styling
    private static int offset = 10;
    private static int inputTileWidth = 70;
    private static int leftMargin = 397;
    private static int bottomMargin = 50;
    private static int totalWidth = 908;

    public static void configureInputField(Word word, Quiz quiz, ImageView submit_button){
        submit = submit_button;
        controller = quiz;
        root = MainApp.getStackPane();
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> submit());

        reconfigureInputField(word);
    }

    public static void reconfigureInputField(Word word){
        currentWord = word;
        wordSize = currentWord.getMaori().length();
        isInputEmpty = true;       //assumes empty initially and checks if actually not empty in getInput()
        
        removeAll(inputItems);
        removeAll(hintItems);
        
        
        inputItems = new TextField[wordSize];
        hintItems = new Label[wordSize];

        alterCentre();
        createHintItems();
        createInputFields();
        
        addAll(inputItems);
        addAll(hintItems);

        cursor = getNextValidIndex(-1, true);
        recursor(); 
    }
    
    public static void recursor(){
        inputItems[cursor].requestFocus();
    }

     /**
     * insert macron to textfield
     */
    public static void insertMacron(String macron){
        inputItems[cursor].setText(macron);
        insertCharacter(inputItems[cursor]);
    }

    private static void submit(){
        Word input = new Word();
        input.setMaori(getInput());

        controller.onEnter(input, isInputEmpty);

        if (currentWord.getStatus() == Status.FAILED) onFailed(Paint.valueOf("FF6F74"));
    }

    private static void alterCentre(){
        int widthOfField = (wordSize * inputTileWidth) + ((wordSize - 1) * offset);
        int additionalOffset = (totalWidth - widthOfField) / 2;
        leftMargin = 397 - additionalOffset;
    }

    private static void createInputFields(){
        for (int i = 0; i < inputItems.length; i++){
            TextField n = null;
            if (!getCharacter(i).equals(" ")){
                if (hintItems[i] != null){
                    //has hint
                    n = createInputItem(i, false);
                } else {
                    //is inputtable
                    n = createInputItem(i, true);
                }
            }
            inputItems[i] = n;
        }
    }

    private static void createHintItems(){
        if (currentWord.getStatus() != Status.FAULTED) return;

        int numOfHints = (int) Math.ceil(0.2 * wordSize);

        for (int i = 0; i < numOfHints; i++){
            int trialIndex = new Random().nextInt(wordSize);

            while (hintItems[trialIndex] != null){
                trialIndex++;
                if (trialIndex >= wordSize) trialIndex-=wordSize;
            }
  
            Label hintItem = new Label(getCharacter(trialIndex));
            hintItem.setFont(Font.font("System", FontWeight.BOLD, 30));
            hintItem.setTextFill(Color.WHITE);
            setPositioning(hintItem, trialIndex);
            hintItems[trialIndex] = hintItem;
        }
    }

    private static void setPositioning (Node node, int num){
        node.setTranslateX(((inputTileWidth + offset) * num) - leftMargin);
        node.setTranslateY(-1 * bottomMargin);
    }

    private static TextField createInputItem(int num, boolean isInputtable){
        TextField inputItem = new TextField();
        inputItem.setMaxSize(inputTileWidth, inputTileWidth - 5);
        inputItem.setPrefSize(inputTileWidth, inputTileWidth - 5);
        setPositioning(inputItem, num);

        if (isInputtable){
            addHandler(inputItem, num);
        } else {
            inputItem.setStyle("-fx-background-color: #5F7E79");
            inputItem.setEditable(false);
        }
        
        return inputItem;
    }

    private static String getInput(){
        String input = "";
        for (int index = 0; index < inputItems.length; index++){
            if (inputItems[index] != null && hintItems[index] == null){
                input += inputItems[index].getText();

                if (!inputItems[index].getText().isEmpty()) isInputEmpty = false;

            } else if (hintItems[index] != null){
                input += hintItems[index].getText();
            } else {
                input += " ";
            }
        }
        return input;
    }

    private static int getNextValidIndex(int num, boolean isForward){
        int promptIndex = num;
        boolean foundIndex = false;

        while (!foundIndex){
            promptIndex = (isForward) ? promptIndex + 1 : promptIndex - 1;

            if (promptIndex == wordSize || promptIndex == -1){
                foundIndex = true;
            } else if (inputItems[promptIndex] != null && hintItems[promptIndex] == null){
                foundIndex = true;
            }
        }

        return promptIndex;
    }

    private static void addHandler(TextField inputItem, int num){
        inputItem.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.ENTER){
                submit();
            }  else if (event.getCode() == KeyCode.BACK_SPACE) {
                removeCharacter(inputItem);
            } else  {
                insertCharacter(inputItem);
            }
        });
        inputItem.setOnMouseClicked(e -> {
            cursor = num;
            recursor();
        });
    }
    
    private static void insertCharacter(TextField inputItem){
        String in = inputItem.getText();
        if (in.length() != 0){
            String c = Character.toString(in.charAt(in.length() - 1));
            inputItem.clear();
            inputItem.setText(c);
            inputItem.positionCaret(1);
        }

        if (!inputItem.getText().equals("")) {
            int followingIndex = getNextValidIndex(cursor, true);
            if (followingIndex < wordSize) {
                inputItems[followingIndex].requestFocus();
                inputItems[followingIndex].positionCaret(1);
                cursor = followingIndex;
            }
        }
    }

    private static void removeCharacter(TextField inputItem){
        if (inputItem.getText().equals("")) {
            int previousIndex = getNextValidIndex(cursor, false);
            if (previousIndex >= 0) {
                inputItems[previousIndex].clear();
                inputItems[previousIndex].requestFocus();
                inputItems[previousIndex].positionCaret(1);
                cursor = previousIndex;
            }
        }
    }

    private static void onFailed(Paint colour){
        if (inputItems == null ) return;
        for (int ind = 0; ind < inputItems.length; ind++){
            if (inputItems[ind] != null){
                inputItems[ind].setStyle("-fx-background-color: #"+colour.toString().substring(2));
                inputItems[ind].setText(getCharacter(ind));
                inputItems[ind].setEditable(false);
            }
        }
    }

    public static void setEditability(boolean isEditable){
        if (inputItems == null ) return;
        for (TextField n : inputItems){
            if (n != null){
                n.setEditable(isEditable);
            }    
        }
    }

    private static String getCharacter(int index){
        return Character.toString(currentWord.getMaori().charAt(index));
    }


    private static void removeAll(Node[] items){
        if (items == null ) return;
        for (Node n : items){
            if (n != null){
                root.getChildren().remove(n);
            }
        }     
    }

    private static void addAll(Node[] items){
        for (Node n : items){
            if (n != null){
                root.getChildren().add(n);
            }   
        }
    }

}
