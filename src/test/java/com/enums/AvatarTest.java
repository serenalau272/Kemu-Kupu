package com.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class AvatarTest {
    @Test
    public void testConversionMethods() {
        List<Avatar> avatars = Arrays.asList(Avatar.values());
        for (Avatar avatar : avatars) {
            String s = avatar.toString();
            Avatar f = Avatar.fromString(s);
            assertEquals(avatar, f);
        }
    }
}
