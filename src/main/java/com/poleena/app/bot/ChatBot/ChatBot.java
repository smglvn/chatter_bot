package com.poleena.app.bot.ChatBot;

import com.google.gson.Gson;
import com.poleena.app.bot.Models.BotModel;
import com.poleena.app.bot.Models.DialogModel;
import com.poleena.app.bot.Models.TestModel;
import com.poleena.app.bot.Models.UserModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ChatBot {
    public static BotModel bot;
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

            case "test": {
                TestModel test = getTest();
                ArrayList<String> answers = new ArrayList<>();
                Scanner answer = new Scanner(System.in);

                for (int i = 0; i < test.questions.size(); i++) {
                    System.out.println(test.questions.get(i));
                    String strAnswer = answer.nextLine().toLowerCase();

                    if (user.answers.contains(strAnswer)) {
                        answers.add(strAnswer);
                    } else if (strAnswer.equals("help")) {
                        System.out.println(bot.description);
                        return false;
                    } else if (strAnswer.equals("exit")) {
                        return true;
                    } else {
                        System.out.println(bot.unknownTestAnswer);
                        i--;
                    }
                }
                String res = findFrequentAns(answers);
                System.out.println(test.results.get(res));

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

    public String classifyStatement(String input) {
        if (user.greetings.contains(input)) {
            return "user_greeting";
        } else if (input.equals("тест")) {
            return "test";
        } else if (input.equals("help")) {
            return "help";
        } else if (input.equals("exit")) {
            return "exit";
        } else {
            return "unknown command";
        }
    }

    public String findFrequentAns(ArrayList<String> answers) {
        Map<String, Integer> ansCounter = new HashMap<>();
        for (String ans : answers) {
            Integer count = ansCounter.get(ans);
            ansCounter.put(ans, count == null ? 1 : count + 1);
        }
        Integer maxCount = 0;
        String res = "";
        for (String ans : answers) {
            if (ansCounter.get(ans) > maxCount) {
                maxCount = ansCounter.get(ans);
                res = ans;
            }
        }
        return res;
    }

    private TestModel getTest() {
        int index = ThreadLocalRandom.current().nextInt(bot.tests.size());
        if (bot.tests.get(index).equals("tempTest")) {
            return bot.tempTest;
        } else {
            return bot.petsTest;
        }
    }
}
