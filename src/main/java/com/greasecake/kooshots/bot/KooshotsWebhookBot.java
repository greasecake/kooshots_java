package com.greasecake.kooshots.bot;

import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiValidationException;

import java.io.Serializable;

public class KooshotsWebhookBot extends TelegramWebhookBot {
    @Value("${telegram.webhook-path}")
    private String botWebhookPath;
    @Value("${telegram.bot-token}")
    private String botToken;
    @Value("${telegram.bot-name}")
    private String botName;

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        if (update.hasMessage()) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(update.getMessage().getChatId().toString());
            sendMessage.setText(update.getMessage().getText());
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        return new BotApiMethod<Serializable>() {
            @Override
            public String getMethod() {
                return null;
            }

            @Override
            public Serializable deserializeResponse(String answer) throws TelegramApiRequestException {
                return null;
            }

            @Override
            public void validate() throws TelegramApiValidationException {

            }
        };
    }

    @Override
    public String getBotPath() {
        return botWebhookPath;
    }
}
