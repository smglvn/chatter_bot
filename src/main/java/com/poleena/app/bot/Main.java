package com.poleena.app.bot;

import com.poleena.app.bot.ChatBot.ChatBot;

import java.util.Scanner;


public class Main {

    private static ChatBot chatBot = new ChatBot();

    public static void main(String[] args) {
        boolean exit = false;
        boolean start = true;
        Scanner input = new Scanner(System.in);

        while (!exit) {
            if (start) {
                start = false;
                System.out.println(ChatBot.bot.startDialog);
            }
            String strInput = input.nextLine().toLowerCase();
            exit = chatBot.inputHandler(strInput);
        }
    }
}
