package com.controllers;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.MainApp;
import com.enums.Modals;
import com.util.Modal;
import com.util.Sounds;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

@SuppressWarnings("unchecked")

public class ApplicationController {
    @FXML
    protected Pane anchorPane;

    //// Private (Helper) Functions ////

    //// Public API (to be used by child classes internally) ////

    /**
     * Update an imageview with another imageview in the same folder (makes things
     * much simpler) Note that this can fail if you provide a filename which doesn't
     * exist!
     * 
     * @param s    the name of the image to switch to
     * @param view the view to attach the image on
     * @throws FileNotFoundException fails when s is an invalid image
     */
    private void __setImage(String s, ImageView view) throws FileNotFoundException {
        if (s.contains(".png"))
            System.err.println("Warning! Path included `.png`, this is already added " + s);
        // Parse the current location and set the new value
        String[] ex = view.getImage().getUrl().split("/");
        ex[ex.length - 1] = s + ".png";

        // Set the image on the frontend
        view.setImage(new Image(String.join("/", ex)));
    }

    /// Function using generics to find elements of a certain type
    protected <T> List<T> findElms(Pane p, Class<T> t) {
        List<T> elm = new ArrayList<T>();
        p.getChildren().forEach(c -> {
            if (c instanceof Pane)
                elm.addAll(findElms((Pane) c, t));
            else if (t.isAssignableFrom(c.getClass()))
                elm.add((T) c);
        });
        return elm;
    }

    /**
     * Find all nodes which made a given search term.
     * 
     * @param pane The pane to search for
     * @param fxid the fxid to match against
     * @return a list of nodes, empty if none are found.
     */
    protected List<Node> findNodesByID(Pane pane, String[] fxid) {
        List<Node> nodes = new ArrayList<Node>();
        List<Node> specificNodes = new ArrayList<Node>();
        nodes = findElms(pane, Node.class);
        nodes.forEach(i -> {
            if (i.getId() != null && Arrays.stream(fxid).anyMatch(i.getId()::contains)) {
                specificNodes.add(i);
            }
        });
        return specificNodes;
    }

    /**
     * Find a node on a pane via it's fxid.
     * 
     * @param pane the pane to search
     * @param id   the fxid to match against
     * @return a node if found, null otherwise.
     */
    protected Node findNodeByID(Pane pane, String fxid) {
        String[] str = { fxid };
        return this.findNodesByID(pane, str).get(0);
    }

    /**
     * Update an imageview with another imageview in the same folder (i.e.
     * /some/path/1.png -> /some/path/2.png) I'd recommend placing a call to this
     * behind a calling function which will throw an error in the event i is out of
     * bounds (or clamp it to a certain value)
     * 
     * @param i    an integer indicating which image we need to load
     * @param view the view to attach the image on
     * @throws FileNotFoundException
     */
    protected void setImage(Integer i, ImageView view) throws FileNotFoundException {
        __setImage(i.toString(), view);
    }

    /**
     * Update an imageview with another imageview in the same folder Note that this
     * can fail if you provide a filename which doesn't exist!
     * 
     * @param s    the name of the image to switch to
     * @param view the view to attach the image on
     * @throws FileNotFoundException
     */
    protected void setImage(String s, ImageView view) throws FileNotFoundException {
        __setImage(s, view);
    }

    protected void setAvatarImage(ImageView view) {
        try {
            setImage(MainApp.getUser().getSelectedAvatar().toString(), view);
        } catch (FileNotFoundException e) {
            System.err.println("Unable to load avatar");
        }
    }

    /**
     * Handler for the settings button
     */
    public void settingsClick() {
        Modal.showModal(Modals.SETTING);
    }

    /**
     * Runs after initialisation
     */
    protected void start() {
    }

    /**
     * Initalize a regular stage
     */
    protected void initialize() {
        // Add resizing to all buttons on the page
        List<ImageView> imgs = findElms(anchorPane, ImageView.class);
        imgs.forEach(i -> {
            if (i.getId() != null && i.getId().contains("Button")) {
                i.addEventHandler(MouseEvent.MOUSE_ENTERED, _e -> {
                    i.setScaleX(1.1);
                    i.setScaleY(1.1);
                });
                i.addEventHandler(MouseEvent.MOUSE_EXITED, _e -> {
                    i.setScaleX(1);
                    i.setScaleY(1);
                });

                i.addEventHandler(MouseEvent.MOUSE_CLICKED, _e -> Sounds.playSoundEffect("pop"));
            }
        });

        // run start method after initialisation
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                start();
            }
        });
    }
}