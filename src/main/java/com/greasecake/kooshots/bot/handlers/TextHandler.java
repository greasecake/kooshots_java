package com.greasecake.kooshots.bot.handlers;

import com.greasecake.kooshots.bot.handlers.command.CommandDict;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Optional;

@Component
@Order(99)
public class TextHandler extends AbstractUpdateHandler {
    @Override
    public boolean check(Update update) {
        return Optional.ofNullable(update)
                .map(Update::getMessage)
                .map(Message::getText)
                .map(s -> !CommandDict.getCommands().containsValue(s))
                .orElse(false) &&
                !conversationStateService.hasState(update.getMessage().getChatId());
    }

    @Override
    public void handle(AbsSender sender, Update update) {
        senderUtils.send(sender, update.getMessage().getChatId(), "text");
    }
}
