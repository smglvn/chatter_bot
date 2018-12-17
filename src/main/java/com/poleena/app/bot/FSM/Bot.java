package com.poleena.app.bot.FSM;

import com.google.gson.Gson;
import com.poleena.app.bot.Models.TestManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Bot
{
    private FSMBot fsmBot;
    private TestManager tester;

    public Bot() {
        File db = new File("src/main/newStructure.json");
        File tests = new File("src/main/tests.json");
        String testsString = "";
        String dbString = "";
        Gson gson = new Gson();
        try {
            dbString = new String(Files.readAllBytes(Paths.get(db.getAbsolutePath())));
            testsString = new String(Files.readAllBytes(Paths.get(tests.getAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        tester = gson.fromJson(testsString, TestManager.class);
        fsmBot = gson.fromJson(dbString, FSMBot.class);
    }

    public void init() {
        tester.init();
        fsmBot.init(tester);
        tester.setFsmBot(fsmBot);
    }

    public String process(String phrase) {
        return fsmBot.process(phrase.toLowerCase());
    }
}
