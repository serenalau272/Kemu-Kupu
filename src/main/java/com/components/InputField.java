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

/**
 * InputField is used to dynamically generate the custom input text field for
 * use in Quiz
 */
public class InputField extends TextField {
    // configured fields
    private static StackPane root;
    private static ImageView submit;
    private static Quiz controller;

    // word specific fields
    private static TextField[] inputItems;
    private static Label[] hintItems;
    private static int wordSize;
    private static Word currentWord;
    private static int cursor;
    private static boolean isInputEmpty;

    // fields for styling
    private static int offset = 10;
    private static int inputTileWidth = 73;
    private static int leftMargin = 397;
    private static int bottomMargin = 50;
    private static int totalWidth = 908;

    /**
     * configures the input field with a given word, quiz controller, and submit
     * button called on first intialisation
     * @param word
     * @param quiz
     * @param submit_button
     */
    public static void configureInputField(Word word, Quiz quiz, ImageView submit_button) {
        submit = submit_button;
        controller = quiz;
        root = MainApp.getStackPane();
        submit.addEventHandler(MouseEvent.MOUSE_CLICKED, _event -> submit()); // adds event handler to submit button

        reconfigureInputField(word);
    }

    /**
     * reconfigures the input field with a given word
     * @param word
     */
    public static void reconfigureInputField(Word word) {
        currentWord = word;
        wordSize = currentWord.getMaori().length();
        isInputEmpty = true; // assumes empty initially and checks if actually not empty in getInput()

        // remove all elements
        removeAll(inputItems);
        removeAll(hintItems);

        inputItems = new TextField[wordSize];
        hintItems = new Label[wordSize];

        // ensures centreing and populated input and hint items
        alterCentre();
        createHintItems();
        createInputFields();

        // adds all elements
        addAll(inputItems);
        addAll(hintItems);

        // sets cursor in correct position
        cursor = getNextValidIndex(-1, true);
        recursor();
    }

    
    /**
     * re-request focus at current cursor position
     */
    public static void recursor() {
        inputItems[cursor].requestFocus();
    }

    /**
     * insert macron to textfield
     * @param macron
     */
    public static void insertMacron(String macron) {
        inputItems[cursor].setText(macron);
        insertCharacter(inputItems[cursor]);
    }

    /**
     * submit the word
     */
    private static void submit() {
        // construct word from input
        Word input = new Word();
        input.setMaori(getInput());

        // call onEnter() method from Quiz controller
        controller.onEnter(input, isInputEmpty);

        // change GUI if word failed
        if (currentWord.getStatus() == Status.FAILED)
            onFailed(Paint.valueOf("FF6F74"));
    }

    /**
     * alter the leftMargin field to ensure centreing of the input field
     */
    private static void alterCentre() {
        int widthOfField = (wordSize * inputTileWidth) + ((wordSize - 1) * offset);
        int additionalOffset = (totalWidth - widthOfField) / 2;
        leftMargin = 397 - additionalOffset;
    }

    /**
     * create the input fields
     */
    private static void createInputFields() {
        for (int i = 0; i < inputItems.length; i++) {
            TextField n = null;
            if (!getCharacter(i).equals(" ")) { // if the character at index i is not a space
                if (hintItems[i] != null) {
                    // has hint
                    n = createInputItem(i, false);
                } else {
                    // is inputtable
                    n = createInputItem(i, true);
                }
            }
            inputItems[i] = n;
        }
    }

    /**
     * create the hint items
     */
    private static void createHintItems() {
        // only create hint iterms if status is faulted
        if (currentWord.getStatus() != Status.FAULTED)
            return;

        int numOfHints = (int) Math.ceil(0.2 * wordSize); // get a certain proportion of hints

        for (int i = 0; i < numOfHints; i++) {
            // obtain trialIndex which is an index to which a hint can be added at
            int trialIndex = new Random().nextInt(wordSize);

            while (hintItems[trialIndex] != null) {
                trialIndex++;
                if (trialIndex >= wordSize)
                    trialIndex -= wordSize;
            }

            // create hintItem
            Label hintItem = new Label(getCharacter(trialIndex));

            // configure entry
            hintItem.setFont(Font.font("System", FontWeight.BOLD, 30));
            hintItem.setTextFill(Color.WHITE);
            setPositioning(hintItem, trialIndex);

            // add hint item
            hintItems[trialIndex] = hintItem;
        }
    }

    /**
     * set the positioning for the tile based upon its index
     * @param node
     * @param num
     */
    private static void setPositioning(Node node, int num) {
        node.setTranslateX(((inputTileWidth + offset) * num) - leftMargin);
        node.setTranslateY(-1 * bottomMargin);
    }

    /**
     * create and input item
     * @param num
     * @param isInputtable
     * @return
     */
    private static TextField createInputItem(int num, boolean isInputtable) {
        TextField inputItem = new TextField();

        // configure input
        inputItem.setMaxSize(inputTileWidth, inputTileWidth - 5);
        inputItem.setPrefSize(inputTileWidth, inputTileWidth - 5);
        setPositioning(inputItem, num);

        if (isInputtable) {
            // regular text field
            addHandler(inputItem, num);
        } else {
            // hint item
            inputItem.setStyle("-fx-background-color: #5F7E79");
            inputItem.setEditable(false);
        }

        return inputItem;
    }

    /**
     * obtain the input from the custom input field
     * @return
     */
    private static String getInput() {
        String input = "";
        for (int index = 0; index < inputItems.length; index++) {
            if (inputItems[index] != null && hintItems[index] == null) {
                // add text
                input += inputItems[index].getText();

                // if any inputtable textfield is not empty, set isInputEmpty to false
                if (!inputItems[index].getText().isEmpty())
                    isInputEmpty = false;

            } else if (hintItems[index] != null) {
                // add hint letter
                input += hintItems[index].getText();
            } else {
                // add space
                input += " ";
            }
        }
        return input;
    }

    /**
     * retrieves the next valid index in a given direction from num
     * @param num
     * @param isForward
     * @return
     */
    private static int getNextValidIndex(int num, boolean isForward) {
        int promptIndex = num;
        boolean foundIndex = false;

        while (!foundIndex) {
            promptIndex = (isForward) ? promptIndex + 1 : promptIndex - 1; // prompt index

            if (promptIndex == wordSize || promptIndex == -1) {
                // if bounds of word have been reached, we exit
                foundIndex = true;
            } else if (inputItems[promptIndex] != null && hintItems[promptIndex] == null) {
                // if we reach an index which corresponds to an inputtable text field
                foundIndex = true;
            }
        }

        return promptIndex;
    }

    /**
     * add handler to input item
     * 
     * @param inputItem
     * @param num
     */
    private static void addHandler(TextField inputItem, int num) {
        inputItem.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // on enter, submit
                submit();
            } else if (event.getCode() != KeyCode.BACK_SPACE) {
                // else and given it is not backspace, insert a character
                insertCharacter(inputItem);
            }
        });

        inputItem.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.BACK_SPACE) {
                // on backspace, remove
                removeCharacter(inputItem);
            }
        });

        inputItem.setOnMouseClicked(e -> {
            // set the cursor and focus on click
            cursor = num;
            recursor();
        });
    }

    /**
     * insert a character in a given input item
     * 
     * @param inputItem
     */
    private static void insertCharacter(TextField inputItem) {
        String in = inputItem.getText();

        // code to ensure that there is only one character in the input field. error
        // tolerance in case people type fast. always takes last character and replaces
        // the rest
        if (in.length() != 0) {
            String c = Character.toString(in.charAt(in.length() - 1));
            inputItem.clear();
            inputItem.setText(c);
            inputItem.positionCaret(1);
        }

        // if there's no input in the textfield, we progress to next textfield
        if (!inputItem.getText().equals("")) {
            int followingIndex = getNextValidIndex(cursor, true);
            if (followingIndex < wordSize) {
                inputItems[followingIndex].requestFocus();
                inputItems[followingIndex].positionCaret(1);
                cursor = followingIndex;
            }
        }
    }

    /**
     * remove character
     * 
     * @param inputItem
     */
    private static void removeCharacter(TextField inputItem) {
        // if textfield is empty, we progress to preceding textfield
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

    /**
     * if word is failed, we alter the GUI to reflect this
     * 
     * @param colour
     */
    private static void onFailed(Paint colour) {
        // clear hint items
        removeAll(hintItems);

        if (inputItems == null)
            return;

        // add correct spelling with red background
        for (int ind = 0; ind < inputItems.length; ind++) {
            if (inputItems[ind] != null) {
                inputItems[ind].setStyle("-fx-background-color: #" + colour.toString().substring(2));
                inputItems[ind].setText(getCharacter(ind));
                inputItems[ind].setEditable(false);
            }
        }
    }

    /**
     * method to disable input items
     * 
     * @param isDisable
     */
    public static void setDisableInputs(boolean isDisable) {
        if (inputItems == null)
            return;
        for (TextField n : inputItems) {
            if (n != null) {
                n.setDisable(isDisable);
                n.setEditable(false);
            }
        }
    }

    /**
     * method to retrieve a certain character from the given word
     * 
     * @param index
     * @return
     */
    private static String getCharacter(int index) {
        return Character.toString(currentWord.getMaori().charAt(index));
    }

    /**
     * remove all elements of a certain array of nodes
     * 
     * @param items
     */
    private static void removeAll(Node[] items) {
        if (items == null)
            return;
        for (Node n : items) {
            if (n != null) {
                root.getChildren().remove(n);
            }
        }
    }

    /**
     * add all elements of a certain node array
     * 
     * @param items
     */
    private static void addAll(Node[] items) {
        for (Node n : items) {
            if (n != null) {
                root.getChildren().add(n);
            }
        }
    }

}
