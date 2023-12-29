package com.greasecake.kooshots.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TelegramConfig {
    @Value("${telegram.webhook-path}")
    private String botWebhookPath;
    @Value("${telegram.bot-token}")
    private String botToken;
    @Value("${telegram.bot-name}")
    private String botUserName;
    @Value("${telegram.admin-id}")
    private Long adminId;
    @Value("-100${telegram.feedback-channel-id}")
    private Long feedbackChannelId;

    public String getBotWebhookPath() {
        return botWebhookPath;
    }

    public String getBotToken() {
        return botToken;
    }

    public String getBotUserName() {
        return botUserName;
    }

    public Long getAdminId() {
        return adminId;
    }

    public Long getFeedbackChannelId() {
        return feedbackChannelId;
    }

    public void setFeedbackChannelId(Long feedbackChannelId) {
        this.feedbackChannelId = feedbackChannelId;
    }
}
