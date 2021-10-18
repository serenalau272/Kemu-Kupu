package com.enums;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ViewTest {
    @Test
    public void testGetFileName() {
        List<View> views = Arrays.asList(View.values());
        for (View view : views) {
            String fileName = view.getFileName();
            if (view == View.MENU) {
                if (fileName != "Menu") {
                    fail("View.MENU returns incorrect value!");
                }
            } else {
                assertNotEquals("Menu", fileName);
            }
        }
    }

    @Test
    public void testGetWindowName() {
        List<View> views = Arrays.asList(View.values());
        for (View view : views) {
            String windowName = view.getWindowName();
            if (view == View.MENU) {
                if (windowName != "Kemu Kupu") {
                    fail("View.MENU returns incorrect value!");
                }
            } else {
                assertNotEquals("Kemu Kupu", windowName);
            }
        }
    }
}
