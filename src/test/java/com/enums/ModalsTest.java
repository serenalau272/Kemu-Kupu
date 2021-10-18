package com.enums;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ModalsTest {
    @Test
    public void testGetFileName() {
        List<Modals> modals = Arrays.asList(Modals.values());
        for (Modals modal : modals) {
            assertNotEquals("", modal.getFileName());
        }
    }

    @Test
    public void testGetWindowName() {
        List<Views> modals = Arrays.asList(Views.values());
        for (Views modal : modals) {
            assertNotEquals("", modal.getWindowName());
        }
    }
}
