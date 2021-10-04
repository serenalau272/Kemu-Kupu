package com.se206.g11.util;

import java.net.URISyntaxException;

import com.se206.g11.MainApp;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * This class handles all sound effects and music for the game.
 */
public class Sounds {    

    //TODO implement music here!

    /**
     * Play a sound from the resources/sound folder.
     * SAFTEY: As this is a built-in javafx method execution is ended automatically upon termination of the main thread.
     * WARNING: Will fail if an unknown sound is provided (full thread crash).
     * @param sound the name of the sound to play
     */
    public static void playSoundEffect(String sound) {
        if (!MainApp.getSettings().getMusic()) return;
        try {
            String path = MainApp.class.getResource("/sound/" + sound + ".wav").toURI().toString();
            //Play sound
            new MediaPlayer(new Media(path)).play();
        } catch (URISyntaxException exception){
            System.err.println("Unable to load sound file: " + sound);
        }
    }
}
