package com.se206.g11;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")

public class ApplicationController {
    @FXML
    private Pane anchorPane;

    /// Function using generics to find elements of a certain type
    protected <T> List<T> findElms(Pane p, Class<T> t) {
        List<T> elm = new ArrayList<T>();
        p.getChildren().forEach(c -> {
            if (c instanceof Pane) elm.addAll(findElms((Pane) c, t));
            else if (t.isAssignableFrom(c.getClass())) elm.add((T) c);
        });
        return elm;
    }

    public void initialize() {
        // Add resizing to all buttons on the page
        List<ImageView> imgs = findElms(anchorPane, ImageView.class);
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