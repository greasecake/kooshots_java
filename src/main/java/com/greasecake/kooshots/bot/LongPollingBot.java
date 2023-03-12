package com.greasecake.kooshots.bot;

import com.greasecake.kooshots.bot.processors.UpdateHandlerSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class LongPollingBot extends TelegramLongPollingBot {
    @Value("${telegram.bot-token}")
    private String botToken;
    @Value("${telegram.bot-name}")
    private String botUserName;
    @Autowired
    UpdateHandlerSelector processorSelector;

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        processorSelector.getHandler(this, update);
    }
}
