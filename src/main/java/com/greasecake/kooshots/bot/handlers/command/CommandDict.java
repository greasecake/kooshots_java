package com.greasecake.kooshots.bot.handlers.command;

import com.greasecake.kooshots.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class CommandDict {
    @Autowired
    MessageUtils messageUtils;

    @Autowired
    public CommandDict(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    private static Map<CommandType, String> commands;

    @PostConstruct
    private void init() {
        commands = Map.of(
                CommandType.START, "/start",
                CommandType.LOGS, "/logs",
                CommandType.INFO, messageUtils.getMessage("button.info"),
                CommandType.FEEDBACK_INIT, messageUtils.getMessage("button.feedback")
        );
    }

    public static Map<CommandType, String> getCommands() {
        return commands;
    }

    public static String getCommandByType(CommandType type) {
        return commands.get(type);
    }
}
