package com.se206.g11;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javafx.stage.Stage;

@SuppressWarnings("unchecked")

public class ApplicationController {
    @FXML
    protected Pane stackPane;

    //The stage of this window.
    private Stage stage = null; 

    //Offsets, used when dragging an undecorated window
    private double yOffset = 0;
    private double xOffset = 0;

    //// Private (Helper) Functions ////

    //// Public API (to be used by child classes internally) ////

    /**
     * Update an imageview with another imageview in the same folder (makes things much simpler)
     * Note that this can fail if you provide a filename which doesn't exist!
     * @param s the name of the image to switch to
     * @param view the view to attach the image on
     * @throws FileNotFoundException fails when s is an invalid image
     */
    private void __setImage(String s, ImageView view) throws FileNotFoundException {
        if (s.contains(".png")) System.err.println("Warning! Path included `.png`, this is already added " + s);
        //Parse the current location and set the new value
        String[] ex = view.getImage().getUrl().split("/");
        ex[ex.length-1] = s + ".png";

        //Check the file exists
        URI uri = URI.create(String.join("/", ex));
        if (!new File(uri).isFile()) throw new FileNotFoundException("Index was set out of bounds! Unable to find image file " + uri);

        //Set the image on the frontend
        view.setImage(new Image(String.join("/", ex)));
    }

    /// Function using generics to find elements of a certain type
    protected <T> List<T> findElms(Pane p, Class<T> t) {
        List<T> elm = new ArrayList<T>();
        p.getChildren().forEach(c -> {
            if (c instanceof Pane) elm.addAll(findElms((Pane) c, t));
            else if (t.isAssignableFrom(c.getClass())) elm.add((T) c);
        });
        return elm;
    }

    /**
     * Update an imageview with another imageview in the same folder (i.e. /some/path/1.png -> /some/path/2.png)
     * I'd recommend placing a call to this behind a calling function which will throw an error in the event i is out of bounds
     * (or clamp it to a certain value)
     * @param i an integer indicating which image we need to load
     * @param view the view to attach the image on
     * @throws FileNotFoundException
     */
    protected void setImage(Integer i, ImageView view) throws FileNotFoundException {     
        __setImage(i.toString(), view);
    }

    /**
     * Update an imageview with another imageview in the same folder
     * Note that this can fail if you provide a filename which doesn't exist!
     * @param s the name of the image to switch to
     * @param view the view to attach the image on
     * @throws FileNotFoundException
     */
    protected void setImage(String s, ImageView view) throws FileNotFoundException {
        __setImage(s, view);
    }

    /**
     * Initalize a modal - Should only be called from MainApp, do not call from internal function
     * @param s
     */
    // protected final void modalInit(Stage s) {
    //     this.stage = s;
    //     //Allow the modal to be clicked and dragged
    //     this.stage.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
    //         this.xOffset = this.stage.getX() - event.getScreenX();
    //         this.yOffset = this.stage.getY() - event.getScreenY();
    //     });
    //     this.stage.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
    //         this.stage.setX(event.getScreenX() + this.xOffset);
    //         this.stage.setY(event.getScreenY() + this.yOffset);
    //     });
    //     //Close the modal on esc
    //     this.stage.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
    //         if (event.getCode() == KeyCode.ESCAPE) this.stage.close();
    //     });
    // }

    /**
     * Initalize a regular stage
     */
    protected void initialize() {
        // Add resizing to all buttons on the page
        List<ImageView> imgs = findElms(stackPane, ImageView.class);
        imgs.forEach(i -> {
            if (i.getId() != null && i.getId().contains("_button")) {
                i.addEventHandler(MouseEvent.MOUSE_ENTERED, _e -> {
                    i.setScaleX(1.1);
                    i.setScaleY(1.1);
                });
                i.addEventHandler(MouseEvent.MOUSE_EXITED, _e -> {
                    i.setScaleX(1);
                    i.setScaleY(1);
                });
            }
        });
    }
}