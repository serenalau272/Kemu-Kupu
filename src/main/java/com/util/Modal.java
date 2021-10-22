package com.util;

import com.MainApp;
import com.components.InputField;
import com.components.animations.Clock;
import com.controllers.modals.Confirmation;
import com.controllers.modals.Error;
import com.enums.ConfirmModal;
import com.enums.ErrorModal;
import com.enums.Modals;
import com.enums.Views;
import com.models.GeneralModal;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.StackPane;

public class Modal {
    private static Clock clock;

    /**
     * Set clock
     * @param clock
     * 
     */
    public static void setClock(Clock c){
        clock = c;
    }

    /**
     * Load and show a modal to the user
     * @param m the modal to load
     */
    public static void showModal(Modals m) {
        try {
            //Duplicate code should be refactored at some point
            if (MainApp.getBaseView() == Views.QUIZ) clock.stop();

            disableScreenNodes(true);
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/" + m.getFileName() + ".fxml"));
            Node modal = (Node) fxmlLoader.load();
            MainApp.getStackPane().getChildren().add(modal);
            addBlur();
        } catch (Exception e) {
            System.err.println("Unable to load modal " + m.getFileName() + " due to error " + e.toString()); 
            return;
        }
    }

    public static void showGeneralModal(GeneralModal type) {
        FXMLLoader fxmlLoader;
        Node modal;
        String message;
        String modalType = type.getClass().getName().replace("com.enums.", "");
        try {
            disableScreenNodes(true);
            switch (modalType) {
                case "ConfirmModal":
                    fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/Confirmation.fxml"));
                    modal = (Node) fxmlLoader.load();
                    MainApp.getStackPane().getChildren().add(modal);
                    addBlur();
        
                    Confirmation confirmationController = fxmlLoader.getController();
                    confirmationController.setConfirmType((ConfirmModal) type);
                    message = type.getMessage();
                    confirmationController.setMessage(message);
                    break;
                case "ErrorModal": 
                    fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/Error.fxml"));
                    modal = (Node) fxmlLoader.load();
                    MainApp.getStackPane().getChildren().add(modal);
                    addBlur();

                    Error errorController = fxmlLoader.getController();
                    errorController.setErrorType((ErrorModal) type);
                    message = type.getMessage();
                    errorController.setMessage(message);
                    break;
                default:
                    System.err.println("ERROR: General modal type not implemented.");
                    return;
            }
        } catch (Exception e) {
            System.err.println("Unable to load confirmation modal due to error " + e.toString()); 
            return;
        }
    }

    /**
     * closes modal
     */
    public static void closeModal() {
        // Sounds.playSoundEffect("pop");
        int size = MainApp.getStackPane().getChildren().size();
        MainApp.getStackPane().getChildren().remove(size-1);
        removeBlur();
        disableScreenNodes(false);

        if (MainApp.getBaseView() == Views.QUIZ) {
            clock.resume();
            InputField.recursor();
        }
    }

    /**
     * Disable all screen nodes
     */
    public static void disableScreenNodes(boolean isDisable) {
        StackPane stackpane = MainApp.getStackPane();

        stackpane.getChildren().get(0).setDisable(isDisable);
        int size = stackpane.getChildren().size();
        if (size >= 3) {
            for (int i = 1; i < stackpane.getChildren().size() - 2; i++) {
                stackpane.getChildren().get(i).setDisable(isDisable);
            }
        }
    }

    /**
     * adds blur from background pane
     */
    public static void addBlur() {
        BoxBlur blur = new BoxBlur();
        blur.setIterations(2);
        MainApp.getStackPane().getChildren().get(0).setEffect(blur);
    }

    /**
     * removes blur from background pane
     */
    public static void removeBlur() {
        MainApp.getStackPane().getChildren().get(0).setEffect(null);
    }

}
