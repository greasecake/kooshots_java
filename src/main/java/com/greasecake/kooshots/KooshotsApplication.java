package com.greasecake.kooshots;

import com.greasecake.kooshots.bot.KooshotsBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class KooshotsApplication {

    public static void main(String[] args) throws TelegramApiException {
        SpringApplication.run(KooshotsApplication.class, args);

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new KooshotsBot());
    }

}
