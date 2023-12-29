package com.greasecake.kooshots.bot.handlers;

import com.greasecake.kooshots.bot.SenderUtils;
import com.greasecake.kooshots.service.ConversationStateService;
import com.greasecake.kooshots.service.LogService;
import com.greasecake.kooshots.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.transaction.Transactional;

@Component
public abstract class AbstractUpdateHandler implements UpdateHandler {
    Logger logger = LoggerFactory.getLogger(UpdateHandler.class);
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
    @Transactional
    public void updateConversationState(Update update) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        conversationStateService.setState(message.getChatId(), finishState);
    }
}
