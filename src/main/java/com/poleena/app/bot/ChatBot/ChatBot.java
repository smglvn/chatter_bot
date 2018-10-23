package com.poleena.app.bot.ChatBot;

import com.google.gson.Gson;
import com.poleena.app.bot.Models.BotModel;
import com.poleena.app.bot.Models.DialogModel;
import com.poleena.app.bot.Models.UserModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

public class ChatBot {
    private static BotModel bot;
    private static UserModel user;

    public ChatBot() {
        File jsonFile = new File("src/main/dataBase.json");
        String jsonString = "";
        try {
            jsonString = new String(Files.readAllBytes(Paths.get(jsonFile.getAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        DialogModel dialog = gson.fromJson(jsonString, DialogModel.class);
        bot = dialog.bot;
        user = dialog.user;
    }

    public boolean inputHandler(String input) {

        switch (classifyStatement(input)) {
            case "user_greeting": {
                int index = ThreadLocalRandom.current().nextInt(bot.greetings.size());
                System.out.println(bot.greetings.get(index));
                return false;
            }

            case "help": {
                System.out.println(bot.description);
                return false;
            }

            case "exit": {
                return true;
            }

            case "unknown command": {
                System.out.println(bot.unknownCommand);
                return false;
            }

            default: {
                System.out.println(bot.description);
                return false;
            }
        }
    }

    private static String classifyStatement(String input) {
        if (user.greetings.contains(input)) {
            return "user_greeting";
        } else if (input.equals("help")) {
            return "help";
        } else if (input.equals("exit")) {
            return "exit";
        } else {
            return "unknown command";
        }
    }
}
