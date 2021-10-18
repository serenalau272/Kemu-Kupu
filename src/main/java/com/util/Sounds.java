package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.net.URISyntaxException;
import java.util.Arrays;

import com.MainApp;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * This class handles all sound effects and music for the game.
 */
public class Sounds {    
    private static MediaPlayer musicPlayer;

    /**
     * Loads information from the underlying OS, acquiring the exact linux distribution we are running on.
     * Modified from: https://stackoverflow.com/questions/15018474/getting-linux-distro-from-java
     * 
     * HACK: Ubuntu does not support playing of .mp3s! Is an imcompatability with javafx 8 This 
     * is unlikely to be fixed in the future, and only updating to a newer version of javafx
     * will fix it. This function is needed to fix this issue.
     * 
     * See https://github.com/javafxports/openjdk-jfx/issues/331 for more information.
     * @return the distribution
     */
    private static String getOSInformation() {
        //lists all the files ending with -release in the etc folder
        File dir = new File("/etc/");
        File fileList[] = new File[0];
        if(dir.exists()){
            fileList =  dir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String filename) {
                    return filename.endsWith("-release");
                }
            });
        }
        //looks for the version file (not all linux distros)
        File fileVersion = new File("/proc/version");
        if(fileVersion.exists()){
            fileList = Arrays.copyOf(fileList,fileList.length+1);
            fileList[fileList.length-1] = fileVersion;
        }       
        //prints all the version-related files
        for (File f : fileList) {
            try {
                BufferedReader myReader = new BufferedReader(new FileReader(f));
                String strLine = null;
                while ((strLine = myReader.readLine()) != null) {
                    if (strLine.contains("PRETTY_NAME=")) {
                        return strLine.substring(12, strLine.length()-1).toLowerCase();
                    }

                }
                myReader.close();
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
        return "";
    }

    public static void playMusic(String music) {
        //HACK: Ubuntu does not support playing of .mp3s! Is an imcompatability with javafx 8 This 
        //is unlikely to be fixed in the future, and only updating to a newer version of javafx
        //will fix it.
        //See https://github.com/javafxports/openjdk-jfx/issues/331 for more information.
        // if (getOSInformation().contains("ubuntu")) return; 

        if (musicPlayer != null) {
            musicPlayer.stop();
        }
        
        if (!MainApp.getSetting().getMusic()) return;        

        try {
            String path = MainApp.class.getResource("/sound/" + music + ".mp3").toURI().toString();
            musicPlayer = new MediaPlayer(new Media(path));

            if (!music.equals("menu")){
                if (music.equals("game")){
                    musicPlayer.setVolume(0.08);
                } else {
                    musicPlayer.setVolume(0.15);
                }
            } 

            musicPlayer.setOnEndOfMedia(new Runnable() {
                public void run() {
                    musicPlayer.seek(Duration.ZERO);
                }
            });
            musicPlayer.play();
        } catch (URISyntaxException exception){
            System.err.println("Unable to load music file: " + music);
        }
    }

    /**
     * Play a sound from the resources/sound folder.
     * SAFTEY: As this is a built-in javafx method execution is ended automatically upon termination of the main thread.
     * WARNING: Will fail if an unknown sound is provided (full thread crash).
     * @param sound the name of the sound to play
     */
    public static void playSoundEffect(String sound) {
        if (!MainApp.getSetting().getSounds()) return;
        try {
            String path = MainApp.class.getResource("/sound/" + sound + ".wav").toURI().toString();
            //Play sound
            new MediaPlayer(new Media(path)).play();
        } catch (URISyntaxException exception){
            System.err.println("Unable to load sound file: " + sound);
        }
    }
}
