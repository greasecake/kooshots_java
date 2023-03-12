package com.greasecake.kooshots.bot.processors.location;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Optional;

@Component
@Order(1)
public class BasicLocationHandler extends LocationHandler {
    @Override
    public boolean check(Update update) {
        return Optional.ofNullable(update)
                .map(Update::getMessage)
                .map(Message::hasLocation)
                .orElse(false);
    }

    @Override
    public void handle(AbsSender sender, Update update) {
        Location location = update.getMessage().getLocation();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        sendPlaces(sender, update.getMessage().getChatId(), latitude, longitude, 0);
//        TODO: log
//        boolean isForward = update.getMessage().getForwardDate() != null;
//        boolean isReply = update.getMessage().isReply();
    }
}
