package com.poleena.app.bot.Telegram;

import com.poleena.app.bot.FSM.Bot;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramCommunicator extends TelegramLongPollingBot {
    private static Bot chatbot;
    private static IBotConfig config;

    public TelegramCommunicator(DefaultBotOptions botOptions, Bot chatbot, IBotConfig cf) {

        super(botOptions);

        TelegramCommunicator.chatbot = chatbot;
        config = cf;
    }

    @Override
    public String getBotUsername() {
        return config.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            Message message = update.getMessage();
            SendMessage sendMessage = new SendMessage(message.getChatId(), chatbot.process(message.getText(), message.getChatId()));
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void SendMessage(int id, String text) {
        try {
            SendMessage sendMessage = new SendMessage(Integer.toString(id), text);
            sendMessage.setReplyMarkup(new ReplyKeyboardRemove());
            execute(sendMessage);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}