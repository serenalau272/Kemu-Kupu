package com.se206.g11.components;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InputTile extends TextField{
    private TextField input;
    
    public InputTile(int x, int y){
        input = new TextField();
        input.setLayoutX(x);
        input.setLayoutX(y);
        
    }
}
