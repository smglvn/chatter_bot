package com.poleena.app.bot;

import com.poleena.app.bot.Telegram.BotConfig;
import com.poleena.app.bot.Telegram.IProxyConfig;
import com.poleena.app.bot.Telegram.ProxyConfig;
import com.poleena.app.bot.Telegram.TelegramCommunicator;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import com.poleena.app.bot.FSM.Bot;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Scanner;

public class Main {

    private static Bot bot = new Bot();
    private static IProxyConfig pc;

    public static void main(String[] args) {
        pc = new ProxyConfig();
        ApiContextInitializer.init();
        TelegramBotsApi botsApi = new TelegramBotsApi();

        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
        botOptions.setProxyHost(pc.getProxyHost());
        botOptions.setProxyPort(pc.getProxyPort());
        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);

        Authenticator.setDefault(new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(pc.getProxyUser(), pc.getProxyPassword().toCharArray());
            }
        });

        Scanner sc = new Scanner(System.in);
        System.out.println("type something");

        try {
            BotConfig cf = new BotConfig();
            TelegramCommunicator tg = new TelegramCommunicator(botOptions, bot, cf);
            botsApi.registerBot(tg);

        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

//        while (true) {
//            String input = sc.nextLine().toLowerCase();
//            if (input.equals("exit")) {
//                break;
//            }
//            System.out.println(bot.process(input));
//        }
    }
}
