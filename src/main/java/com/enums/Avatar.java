package com.enums;

/**
 * Represents an avatar to be shown to the user.
 */
public enum Avatar {
    DEFAULT, WIZARD, SAILOR, CHEF, MAGICIAN, QUEEN, ALIEN, FAIRY, NINJA, PROFESSOR;

    /**
     * Convert a string into an avatar, this is useful for parsing messages from the
     * api into avatars.
     * @param s the string to parse to the avatar
     * @return an avatar fro the user
     */
    public static Avatar fromString(String s) {
        switch (s.strip().toLowerCase()) {
        case "default":
            return DEFAULT;
        case "sailor":
            return SAILOR;
        case "magician":
            return MAGICIAN;
        case "wizard":
            return WIZARD;
        case "ninja":
            return NINJA;
        case "queen":
            return QUEEN;
        case "fairy":
            return FAIRY;
        case "professor":
            return PROFESSOR;
        case "alien":
            return ALIEN;
        case "chef":
            return CHEF;
        default:
            System.err.println("Recieved unknown avatar request from string to avatar! Affected avatar string " + s);
            return DEFAULT;
        }
    }

    /**
     * Converts an avatar into a string, this is useful for communication with the
     * api.
     * @return a string representing this avatar
     */
    public String toString() {
        switch (this) {
        case DEFAULT:
            return "default";
        case SAILOR:
            return "sailor";
        case MAGICIAN:
            return "magician";
        case WIZARD:
            return "wizard";
        case NINJA:
            return "ninja";
        case QUEEN:
            return "queen";
        case FAIRY:
            return "fairy";
        case PROFESSOR:
            return "professor";
        case ALIEN:
            return "alien";
        case CHEF:
            return "chef";
        default:
            System.err.println("Tried to convert unknown avatar into a string!");
            return "default";
        }
    }

    /**
     * Get the name of an avatar, for display purposes to the user.
     * @return a string which represents the display name of the avatar.
     */
    public String getAvatarName() {
        switch (this) {
        case DEFAULT:
            return "B";
        case SAILOR:
            return "Sailor Bee";
        case MAGICIAN:
            return "Magician Bee";
        case WIZARD:
            return "Wizard Bee";
        case NINJA:
            return "Ninja Bee";
        case QUEEN:
            return "Queen Bee";
        case FAIRY:
            return "Fairy Bee";
        case PROFESSOR:
            return "Professor Bee";
        case ALIEN:
            return "Alien Bee";
        case CHEF:
            return "Chef Bee";
        default:
            System.err.println("Tried to convert unknown avatar into a string!");
            return "B";
        }
    }

    /**
     * Get the speech lines for a given avatar, which should be spoken to the user during quizzes.
     * returns null if no lines exist for the avatar. 
     * @return a string array with the users lines.
     */
    public String[] getSpeechLines() {
        switch (this) {
        case DEFAULT:
            return null;
        case SAILOR: {
            String[] arr = { "Arrrr. Attit ma boi!", "When the waves get tough, we sail right through them!" };
            return arr;
        }
        case MAGICIAN: {
            String[] arr = { "Now that’s what I call magic!",
                    "Don’t worry, sometimes it takes a while for the magic to appear ;)" };
            return arr;
        }
        case WIZARD: {
            String[] arr = { "I cast spells. You can spell. We’re a perfect fit :)",
                    "Fear not, spell-ing is what I do. We got this!" };
            return arr;
        }
        case NINJA: {
            String[] arr = { "Agile and accurate. You’ll be a ninja in no time!",
                    "All good! Let’s jump back into it!" };
            return arr;
        }
        case QUEEN: {
            String[] arr = { "With spelling like that, you’re fit for royalty!", "A few small mistakes? Let it go!" };
            return arr;
        }
        case FAIRY: {
            String[] arr = { "Keep on sparkling!", "A little pixie dust might just do the trick" };
            return arr;
        }
        case PROFESSOR: {
            String[] arr = { "That’s an A++ from me :)",
                    "Embrace uncertainty, and use it to flourish your creativity." };
            return arr;
        }
        case ALIEN: {
            String[] arr = { "Wowzers! Your spelling is out of this world!",
                    "No sweat. I still make mistakes - and I have 3 eyes!" };
            return arr;
        }
        case CHEF: {
            String[] arr = { "You’re cooking up a storm!",
                    "When you’ve split your mayo, you leave it and start afresh!" };
            return arr;
        }
        default:
            System.err.println("Tried to convert unknown avatar into a string!");
            return null;
        }
    }
}
