package com.poleena.app.bot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import org.json.*;

public class Main {
    private static List<String> userGreetings = new ArrayList<>();
    private static List<String> botsGreetings = new ArrayList<>();
    private static String botDescription = "";
    private static String botUnknownCommand = "";
    private static boolean exit = false;

    public static void main(String[] args) {
        File jsonFile = new File("src/main/dataBase.json");
        String jsonString = "";
        try {
            jsonString = new String(Files.readAllBytes(Paths.get(jsonFile.getAbsolutePath())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray userPhrases = jsonObject.getJSONObject("userPhrases").getJSONArray("greetings");
        JSONArray botsPhrases = jsonObject.getJSONObject("botsPhrases").getJSONArray("greetings");
        botDescription = jsonObject.getJSONObject("botsPhrases").getString("description");
        botUnknownCommand = jsonObject.getJSONObject("botsPhrases").getString("unknownCommand");

        for (int i = 0; i < userPhrases.length(); i++) {
            userGreetings.add(userPhrases.getString(i));
        }

        for (int i = 0; i < botsPhrases.length(); i++) {
            botsGreetings.add(botsPhrases.getString(i));
        }

        while (!exit) {
            Scanner input = new Scanner(System.in);
            String strInput = input.nextLine().toLowerCase();
            inputHandler(strInput);
        }

    }

    private static void inputHandler(String input) {

        switch (classifyStatement(input)) {
            case "user_greeting": {
                int index = ThreadLocalRandom.current().nextInt(botsGreetings.size());
                System.out.println(botsGreetings.get(index));
                return;
            }

            case "help": {
                System.out.println(botDescription);
                return;
            }

            case "exit": {
                exit = true;
                return;
            }

            case "unknown command": {
                System.out.println(botUnknownCommand);
                return;
            }

            default: {
                System.out.println(botDescription);
            }
        }
    }

    private static String classifyStatement(String input) {
        if (userGreetings.contains(input)) {
            return "user_greeting";
        }

        if (input.equals("help")) {
            return "help";
        }

        if (input.equals("exit")) {
            return "exit";
        }

        return "unknown command";
    }
}
