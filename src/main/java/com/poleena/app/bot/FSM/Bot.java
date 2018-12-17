package com.poleena.app.bot.FSM;

import com.google.gson.Gson;
import com.poleena.app.bot.Models.TestManager;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Bot {
    private Map<Long, FSMBot> fsmBots = new HashMap<>();
    private String testManagerJSON;
    private String fsmBotJSON;
    private Gson gson;

    public Bot() {
        fsmBotJSON = readFromJARFile("/newStructure.json");
        testManagerJSON = readFromJARFile("/tests.json");
        gson = new Gson();
    }

    public String process(String phrase) {
        return process(phrase, (long) 0);
    }

    public String process(String phrase, Long chatId) {
        if (phrase == null) {
            return "Не понял";
        }

        FSMBot fsmBot = fsmBots.get(chatId);
        if (fsmBot != null) {
            return fsmBot.process(phrase);
        } else {
            TestManager testManager = gson.fromJson(testManagerJSON, TestManager.class);
            FSMBot newBot = gson.fromJson(fsmBotJSON, FSMBot.class);
            testManager.init();
            newBot.initStates(testManager);
            fsmBots.put(chatId, newBot);

            return newBot.process(phrase);
        }
    }

    private String readFromJARFile(String filename) {
        String string = "";
        try {
            string = new String(Files.readAllBytes(Paths.get(new File(Bot.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent() + filename)), "utf8");
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        return string;
    }
}
