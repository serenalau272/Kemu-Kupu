package com;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import com.enums.Modals;
import com.enums.View;

import org.junit.jupiter.api.Test;

public class FxmlTests {
    
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
                if (!content.contains("http://javafx.com/javafx/11")) {
                    fail("fxml file " + x + " is not set to version 11!");
                }
            } catch (IOException e) {
                fail("Failed to read fxml file at " + path + "/" + x + "\nError " + e.toString());
            }
        });
    }
    
    /**
     * Check that all fxml is available in the Views or Modals
     */
    // @Test
    // public void checkAllFxmlAccessible() {
    //     String path = "./src/main/resources/fxml";

    //     List<View> views = Arrays.asList(View.values());
    //     List<Modals> modals = Arrays.asList(Modals.values());

    //     List<String> fileNames = new ArrayList<>();
    //     views.forEach(x -> fileNames.add(x.getFileName() + ".fxml"));
    //     modals.forEach(x -> fileNames.add(x.getFileName() + ".fxml"));

    //     //Check there's no duplicates
    //     HashMap<String, Integer> map = new HashMap<>();
    //     fileNames.forEach(x -> {
    //         if (map.containsKey(x)) {
    //             fail("fxml file " + x + " is listed as both a modal and a screen! This is an error.");
    //         }
    //         map.put(x, 1);
    //     });

    //     //Check that 
    //     File f = new File(path);
    //     Arrays.asList(f.list()).forEach(x -> {
    //         if (!fileNames.contains(x)) {
    //             fail("fxml file " + x + " is not accessible as a modal or a view! If it is a component it must go under the components folder!\nTo fix this issue, place it in the modal or view enum.");
    //         }
    //     });
    // }
}
