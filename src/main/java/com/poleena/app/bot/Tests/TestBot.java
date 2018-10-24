package com.poleena.app.bot.Tests;

import static org.junit.Assert.*;

import com.poleena.app.bot.ChatBot.ChatBot;
import org.junit.Test;

import java.util.ArrayList;

public class TestBot {
    private static ChatBot chatBot = new ChatBot();

    @Test
    public void testClassifyStatement() {
        String statement = chatBot.classifyStatement("Здравствуйте".toLowerCase());
        assertEquals("user_greeting", statement);
    }

    @Test
    public void testFindFrequentAns() {
        ArrayList<String> answers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            answers.add("а");
        }
        for (int i = 0; i < 3; i++) {
            answers.add("б");
        }
        String res = chatBot.findFrequentAns(answers);
        assertEquals("а", res);

    }

    @Test
    public void testInputHandler() {
        boolean res = chatBot.inputHandler("exit");
        assertTrue(res);
    }
}
