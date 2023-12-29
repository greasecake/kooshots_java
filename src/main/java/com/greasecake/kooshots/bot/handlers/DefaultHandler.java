package com.greasecake.kooshots.bot.handlers;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
@Order(100)
public class DefaultHandler extends AbstractUpdateHandler {
    @Override
    public boolean check(Update update) {
        return false;
    }

    @Override
    public void handle(AbsSender sender, Update update) {}
}
