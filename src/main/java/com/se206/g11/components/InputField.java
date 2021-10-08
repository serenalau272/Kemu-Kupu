package com.se206.g11.components;

import com.se206.g11.MainApp;
import com.se206.g11.models.Word;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class InputField extends TextField{
    private static TextField[] inputs;
    private static StackPane root;
    private static int offset = 10;
    private static int inputTileWidth = 60;
    private static int leftMargin = 397;
    private static int bottomMargin = 50;
    private static int totalWidth = 908;

    private static int wordSize;
    

    public static void configureInputField(Word word){
        root = MainApp.getStackPane();
        removeAll();
        
        wordSize = word.getMaori().length();
        inputs = new TextField[wordSize];

        alterCentre();
        createInputFields(word);

        addAll();
    }

    private static void alterCentre(){
        int widthOfField = (wordSize * inputTileWidth) + ((wordSize - 1) * offset);
        int additionalOffset = (totalWidth - widthOfField) / 2;
        leftMargin = 397 - additionalOffset;
    }

    private static void createInputFields(Word word){
        for (int i = 0; i < inputs.length; i++){
            TextField n = null;
            if (!Character.toString(word.getMaori().charAt(i)).equals(" ")){
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
            input += t.getText();
        }
        return input;
    }

    private static void addHandler(TextField inputItem, int num){
        inputItem.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.ENTER){
                System.out.println(getInput());
            } else if (event.getCode().isLetterKey() || event.getCode().isDigitKey() || event.getCode().isWhitespaceKey()) {
                String in = inputItem.getText();
                if (in.length() != 0){
                    String c = Character.toString(in.charAt(0));
                    inputItem.clear();
                    inputItem.setText(c);
                    inputItem.positionCaret(1);
                }

                if (!inputItem.getText().equals("")) {
                    if (num + 1 < wordSize) {
                        inputs[num + 1].requestFocus();
                    }
                }
            } else if (event.getCode() == KeyCode.BACK_SPACE) {
                if (inputItem.getText().equals("")) {
                    if (num - 1 >= 0) {
                        inputs[num - 1].clear();
                        inputs[num - 1].requestFocus();
                        inputs[num - 1].positionCaret(1);
                    }
                }
            } else {
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

    private static void addAll(){
        for (Node n : inputs){
            if (n != null){
                root.getChildren().add(n);
            } else {
                System.err.println("Null element to print in input field.");
            }       
        }
    }

}
