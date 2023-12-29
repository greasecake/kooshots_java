package com.greasecake.kooshots.bot.handlers.location;

import com.greasecake.kooshots.entity.Place;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Order(1)
public class BasicLocationHandler extends LocationHandler {
    List<Place> sentPlaces;

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
        sentPlaces = sendPlaces(sender, update.getMessage().getChatId(), latitude, longitude, 0);
    }

//    @Override
    public void log(Update update) {
        boolean isForward = update.getMessage().getForwardFrom() != null;
        boolean isReply = update.getMessage().isReply();
        List<String> places = sentPlaces.stream().map(Place::getId).collect(Collectors.toList());
    }
}
