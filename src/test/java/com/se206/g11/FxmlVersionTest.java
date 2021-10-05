package com.se206.g11;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class FxmlVersionTest {
    
    /**
     * This test checks that the fxml version is correct for all fxml files in the game.
     */
    @Test
    public void checkFxmlVersion11() {
        String path = "./src/main/resources/fxml/";
        File f = new File(path);
        Arrays.asList(f.list()).forEach(x -> {
            try {
                String content = Files.readString(Paths.get(path, x), StandardCharsets.UTF_8);
                if (content.contains("http://javafx.com/javafx/16")) {
                    fail("fxml file " + x + " is set to version 16, please change it to version 11!");
                }
            } catch (IOException e) {
                fail("Failed to read fxml file at " + path + "/" + x + "\nError " + e.toString());
            }
        });
    }
}
