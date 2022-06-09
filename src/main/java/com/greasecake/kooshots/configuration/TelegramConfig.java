package com.greasecake.kooshots.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramConfig {
    @Value("${telegram.webhook-path}")
    private String botWebhookPath;
    @Value("${telegram.bot-token}")
    private String botToken;
    @Value("${telegram.bot-name}")
    private String botUserName;

    public String getBotWebhookPath() {
        return botWebhookPath;
    }

    public String getBotToken() {
        return botToken;
    }

    public String getBotUserName() {
        return botUserName;
    }
}
