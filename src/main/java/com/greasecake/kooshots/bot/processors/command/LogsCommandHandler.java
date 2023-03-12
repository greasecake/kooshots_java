package com.greasecake.kooshots.bot.processors.command;

import com.greasecake.kooshots.configuration.TelegramConfig;
import com.greasecake.kooshots.entity.Log;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import javax.annotation.PostConstruct;
import java.util.stream.Collectors;

@Component
@Order(11)
public class LogsCommandHandler extends CommandHandler {
    @PostConstruct
    public void init() {
        this.commandName = "/logs";
    }

    @Override
    public void handle(AbsSender sender, Update update) {
        if (new TelegramConfig().getAdminId().equals(update.getMessage().getChatId())) {
            // TODO: log attempt
            return;
        }

        Page<Log> logEntries = logService.getLastLogEntries(10);
        if (logEntries.hasContent()) {
            senderUtils.send(sender,
                    update.getChannelPost().getChatId(),
                    logEntries.getContent().stream().map(Object::toString).collect(Collectors.joining("\n")));
        }
    }
}
