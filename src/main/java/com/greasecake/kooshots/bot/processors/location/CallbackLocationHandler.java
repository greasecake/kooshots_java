package com.greasecake.kooshots.bot.processors.location;

import com.greasecake.kooshots.model.callback.CallbackFactory;
import com.greasecake.kooshots.model.callback.LocationCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.*;

@Component
@Order(2)
public class CallbackLocationHandler extends LocationHandler {
    @Autowired
    CallbackFactory callbackFactory;

    @Override
    public boolean check(Update update) {
        return Optional.ofNullable(update)
                .map(Update::getCallbackQuery)
                .map(CallbackQuery::getData)
                .map(LocationCallback::isLocationCallback)
                .orElse(false);
    }

    @Override
    public void handle(AbsSender sender, Update update) {
        LocationCallback locationCallback = (LocationCallback) (callbackFactory.getCallback(update.getCallbackQuery().getData()));
        assert locationCallback != null;
        sendPlaces(sender,
                update.getCallbackQuery().getMessage().getChatId(),
                locationCallback.getLatitude(),
                locationCallback.getLongitude(),
                locationCallback.getPageIndex());
    }

}
