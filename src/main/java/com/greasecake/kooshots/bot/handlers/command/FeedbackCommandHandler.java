package com.greasecake.kooshots.bot.handlers.command;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import javax.annotation.PostConstruct;

@Component
@Order(13)
public class FeedbackCommandHandler extends CommandHandler {
    @PostConstruct
    public void init() {
        this.commandName = messageUtils.getMessage("button.feedback");
        this.finishState = "feedback.init";
    }

    @Override
    public void handle(AbsSender sender, Update update) {
        senderUtils.send(sender,
                update.getMessage().getChatId(),
                messageUtils.getMessage("message.feedback.ready"));
    }
}
