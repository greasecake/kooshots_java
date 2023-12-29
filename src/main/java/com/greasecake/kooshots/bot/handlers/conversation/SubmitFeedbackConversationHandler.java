package com.greasecake.kooshots.bot.handlers.conversation;

import com.greasecake.kooshots.bot.handlers.AbstractUpdateHandler;
import com.greasecake.kooshots.bot.handlers.command.CommandDict;
import com.greasecake.kooshots.configuration.TelegramConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
@Order(3)
public class SubmitFeedbackConversationHandler extends AbstractUpdateHandler {
    @Autowired
    TelegramConfig telegramConfig;

    @Override
    public boolean check(Update update) {
        return update.hasMessage() &&
                conversationStateService.isState(update.getMessage().getChatId(), "feedback.init") && (
                        !update.getMessage().hasText() ||
                        !CommandDict.getCommands().containsValue(update.getMessage().getText()));
    }

    @Override
    public void handle(AbsSender sender, Update update) {
        senderUtils.send(sender,
                update.getMessage().getChatId(),
                messageUtils.getMessage("message.feedback.received"));
        senderUtils.forward(sender,
                telegramConfig.getFeedbackChannelId(),
                update.getMessage().getChatId(),
                update.getMessage().getMessageId());
    }
}
