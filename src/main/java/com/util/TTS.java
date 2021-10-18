package com.util;

import java.io.IOException;
import java.util.ArrayDeque;

import com.MainApp;
import com.enums.Language;
import com.models.Word;

import javafx.concurrent.Task;

/**
 * Handles all TTS for the Kemu Kupu Application
 */
public class TTS {
    private ArrayDeque<ProcessBuilder> festivalQueue;
    private boolean speaking;

    //// Private (Helper) Methods ////

    /**
     * Pull the next word from the queue and read it.
     */
    private void __speakNext() {
        //If currently speaking, do not begin again
        if (speaking || festivalQueue.peek() == null)
            return;
        speaking = true;

        //Create speech as new task - goverened by javafx
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                try {
                    Process p = festivalQueue.poll().start();
                    p.waitFor();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                speaking = false;
                __speakNext();
                return null;
            }
        };

        //Push speech onto new thread to avoid blocking.
        new Thread(task).start();
    }

    //// Public Methods ////

    public TTS() {
        this.festivalQueue = new ArrayDeque<>();
        this.speaking = false;
    }

    /**
     * Reads a word to a user utilising festival
     * 
     * @param word     The word or phrase to be read to the user
     * @param repeats  The number of times to repeat the word or phrase
     * @param language The language to which words are read out using
     * @throws IllegalArgumentException if paramters are out of range or invalid
     */
    public void readWord(Word word, int repeats, Language language) throws IllegalArgumentException {
        //Validate word
        if (word == null)
            throw new IllegalArgumentException("param `word` may not be null");
        if (language == null)
            throw new IllegalArgumentException("param `language` may not be null");
        if (repeats < 1 || repeats > 20)
            throw new IllegalArgumentException("repeats should be between 1 and 20 (inclusive)");

        //Construct the command
        String speedCommand = "\"(Parameter.set 'Duration_Stretch " + MainApp.getSetting().getDurationFactor() + ")\"";
        String langCommand = (language == Language.MAORI) ? "voice_akl_mi_pk06_cg" : "voice_akl_nz_cw_cg_cg";
        String wordRaw = (language == Language.MAORI) ? word.getMaori() : word.getEnglish();
        if (wordRaw == null)
            throw new IllegalArgumentException("Attempted to read " + ((language == Language.MAORI) ? "maori" : "english") + " word, which was null!");
        String wordCommand = "\"(SayText \\\"" + wordRaw + "\\\")\"";

        //Generate and store process builders
        for (int i = 0; i < Integer.max(repeats, 1); i++) {
            ProcessBuilder c = new ProcessBuilder("/bin/bash", "-c",
                    "echo \"(" + langCommand + ")\" " + speedCommand + " " + wordCommand + " | festival");
            festivalQueue.add(c);
        }

        //Begin speaking
        __speakNext();
    }

    public void stopSpeech() {
        festivalQueue.clear();
    }
}