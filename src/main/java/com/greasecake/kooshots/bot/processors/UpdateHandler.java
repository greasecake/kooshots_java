package com.greasecake.kooshots.bot.processors;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

public interface UpdateHandler {
    boolean check(Update update);
    void handle(AbsSender sender, Update update);
    void updateConversationState(Update update);
}
