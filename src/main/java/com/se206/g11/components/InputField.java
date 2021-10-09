package com.se206.g11.components;

import com.se206.g11.MainApp;
import com.se206.g11.controllers.Quiz;
import com.se206.g11.enums.Status;
import com.se206.g11.models.Word;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class InputField extends TextField{
    private static TextField[] inputs;
    private static StackPane root;
    private static int offset = 10;
    private static int inputTileWidth = 70;
    private static int leftMargin = 397;
    private static int bottomMargin = 50;
    private static int totalWidth = 908;
    private static Quiz controller;
    private static ImageView submit;
    private static int wordSize;
    private static Word currentWord;
    

    public static void configureInputField(Word word, Quiz quiz, ImageView submit_button){
        submit = submit_button;
        controller = quiz;
        root = MainApp.getStackPane();
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> submit());

        reconfigureInputField(word);
    }

    public static void reconfigureInputField(Word word){
        currentWord = word;
        removeAll();
        
        wordSize = currentWord.getMaori().length();
        inputs = new TextField[wordSize];

        alterCentre();
        createInputFields();

        addAll();
        reset();
    }
    
     /**
     * insert macron to textfield
     */
    public static void insertMacron(String macron){
        System.out.println("Should enter macron");
        // String newInput = inputTextField.getText() + macron;
        // inputTextField.setText(newInput);
        // inputTextField.positionCaret(newInput.length());
    }

    private static void submit(){
        Word input = new Word();
        input.setMaori(getInput());

        controller.onEnter(input);

        if (currentWord.getStatus() == Status.FAILED) onFailed(Paint.valueOf("FF6F74"));
    }

    private static void alterCentre(){
        int widthOfField = (wordSize * inputTileWidth) + ((wordSize - 1) * offset);
        int additionalOffset = (totalWidth - widthOfField) / 2;
        leftMargin = 397 - additionalOffset;
    }

    private static void createInputFields(){
        for (int i = 0; i < inputs.length; i++){
            TextField n = null;
            if (!getCharacter(i).equals(" ")){
                n = createInputItem(i);
            }
            inputs[i] = n;
        }
    }

    private static TextField createInputItem(int num){
        TextField inputItem = new TextField();
        inputItem.setMaxSize(inputTileWidth, inputTileWidth - 5);
        inputItem.setPrefSize(inputTileWidth, inputTileWidth - 5);
        inputItem.setTranslateX(((inputTileWidth + offset) * num) - leftMargin);
        inputItem.setTranslateY(-1 * bottomMargin);

        addHandler(inputItem, num);
        
        return inputItem;
    }

    private static String getInput(){
        String input = "";
        for (TextField t : inputs){
            if (t != null){
                input += t.getText();
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
            } else if (inputs[promptIndex] != null){
                foundIndex = true;
            }
        }

        return promptIndex;
    }

    private static void addHandler(TextField inputItem, int num){
        inputItem.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            int previousIndex = getNextValidIndex(num, false);
            int followingIndex = getNextValidIndex(num, true);

            if (event.getCode() == KeyCode.ENTER){
                submit();
            } else if (event.getCode().isLetterKey() || event.getCode().isDigitKey() || event.getCode().isWhitespaceKey()) {
                String in = inputItem.getText();
                if (in.length() != 0){
                    String c = Character.toString(in.charAt(0));
                    inputItem.clear();
                    inputItem.setText(c);
                    inputItem.positionCaret(1);
                }

                if (!inputItem.getText().equals("")) {
                    if (followingIndex < wordSize) {
                        inputs[followingIndex].requestFocus();
                    }
                }
            } else if (event.getCode() == KeyCode.BACK_SPACE) {
                if (inputItem.getText().equals("")) {
                    if (previousIndex >= 0) {
                        inputs[previousIndex].clear();
                        inputs[previousIndex].requestFocus();
                        inputs[previousIndex].positionCaret(1);
                    }
                }
            } else if (!event.getCode().isNavigationKey()) {
                inputItem.clear();
            }
        });
    }
    

    private static void removeAll(){
        if (inputs == null ) return;
        for (Node n : inputs){
            if (n != null){
                root.getChildren().remove(n);
            }
        }     
    }

    public static void reset(){
        if (inputs == null ) return;
        for (TextField n : inputs){
            if (n != null){
                n.clear();
            }
        }
        int ind = getNextValidIndex(-1, true);
        inputs[ind].requestFocus();
    }

    private static void onFailed(Paint colour){
        if (inputs == null ) return;
        for (int ind = 0; ind < inputs.length; ind++){
            if (inputs[ind] != null){
                inputs[ind].setStyle("-fx-background-color: #"+colour.toString().substring(2));
                inputs[ind].setText(getCharacter(ind));
            }
        }
    }

    public static void setEditability(boolean isEditable){
        if (inputs == null ) return;
        for (TextField n : inputs){
            if (n != null){
                n.setEditable(isEditable);
            }    
        }
    }

    private static String getCharacter(int index){
        return Character.toString(currentWord.getMaori().charAt(index));
    }

    private static void addAll(){
        for (Node n : inputs){
            if (n != null){
                root.getChildren().add(n);
            }   
        }
    }

}
