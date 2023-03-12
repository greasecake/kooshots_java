package com.greasecake.kooshots.bot.handlers.command;

import com.greasecake.kooshots.bot.SenderUtils;
import com.greasecake.kooshots.bot.handlers.AbstractUpdateHandler;
import com.greasecake.kooshots.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

public abstract class CommandHandler extends AbstractUpdateHandler {
    @Autowired
    SenderUtils senderUtils;
    @Autowired
    MessageUtils messageUtils;
    protected String commandName;

    @Override
    public boolean check(Update update) {
        return Optional.ofNullable(update)
                .map(Update::getMessage)
                .map(Message::getText)
                .map(s -> s.equals(commandName))
                .orElse(false);
    }
}
