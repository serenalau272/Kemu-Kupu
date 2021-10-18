package com.enums;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ViewsTest {
    @Test
    public void testGetFileName() {
        List<Views> views = Arrays.asList(Views.values());
        for (Views view : views) {
            String fileName = view.getFileName();
            if (view == Views.MENU) {
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
        List<Views> views = Arrays.asList(Views.values());
        for (Views view : views) {
            String windowName = view.getWindowName();
            if (view == Views.MENU) {
                if (windowName != "Kemu Kupu") {
                    fail("View.MENU returns incorrect value!");
                }
            } else {
                assertNotEquals("Kemu Kupu", windowName);
            }
        }
    }
}
