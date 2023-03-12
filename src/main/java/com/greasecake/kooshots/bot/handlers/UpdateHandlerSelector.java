package com.greasecake.kooshots.bot.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;

@Component
public class UpdateHandlerSelector {
    @Autowired
    List<UpdateHandler> handlers;
    @Autowired
    DefaultHandler defaultHandler;

    public void getHandler(AbsSender sender, Update update) {
        UpdateHandler handler = handlers.stream()
                .filter(p -> p.check(update))
                .findFirst()
                .orElse(defaultHandler);
        handler.handle(sender, update);
        handler.updateConversationState(update);
    }
}
