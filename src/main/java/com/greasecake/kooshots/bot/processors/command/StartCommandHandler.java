package com.greasecake.kooshots.bot.processors.command;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import javax.annotation.PostConstruct;

@Component
@Order(10)
public class StartCommandHandler extends CommandHandler {
    @PostConstruct
    public void init() {
        this.commandName = "/start";
    }

    public void handle(AbsSender sender, Update update) {
        senderUtils.send(sender,
                update.getMessage().getChatId(),
                messageUtils.getMessage("message.intro"));
    }
}
