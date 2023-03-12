package com.greasecake.kooshots.bot.processors;

import com.greasecake.kooshots.bot.SenderUtils;
import com.greasecake.kooshots.service.ConversationStateService;
import com.greasecake.kooshots.service.LogService;
import com.greasecake.kooshots.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import javax.transaction.Transactional;

@Component
public abstract class AbstractUpdateHandler implements UpdateHandler {
    protected String finishState;
    @Autowired
    protected ConversationStateService conversationStateService;
    @Autowired
    protected LogService logService;
    @Autowired
    protected SenderUtils senderUtils;
    @Autowired
    protected MessageUtils messageUtils;

    @Override
    public void handle(AbsSender sender, Update update) {
        // TODO: log`
    }

    @Override
    @Transactional
    public void updateConversationState(Update update) {
        conversationStateService.setState(update.getMessage().getChatId(), finishState);
    }
}
