package com.greasecake.kooshots.bot.processors.location;

import com.greasecake.kooshots.bot.processors.AbstractUpdateHandler;
import com.greasecake.kooshots.entity.Place;
import com.greasecake.kooshots.model.PlacesRequest;
import com.greasecake.kooshots.model.callback.LocationCallback;
import com.greasecake.kooshots.service.PlaceService;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public abstract class LocationHandler extends AbstractUpdateHandler {
    @Autowired
    PlaceService placeService;
    public final int PAGE_SIZE = 3;

    protected String composeMessage(Place place) {
        Map<String, String> map = Map.of(
            "name", place.getName(),
            "address", place.getAddress(),
            "description", place.getDescription(),
            "distance", round(place.getDistance(), 1).toString(),
            "map_links", buildMapLinks(place));
        return new StringSubstitutor(map).replace(messageUtils.getMessage("message.place"));
    }

    private Double round(double value, @SuppressWarnings("SameParameterValue") int places) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private String buildMapLinks(Place place) {
        return Map.of(
                        "Яндекс", Optional.ofNullable(place.getMapLink().getYandex()),
                        "Google", Optional.ofNullable(place.getMapLink().getGoogle()),
                        "2ГИС", Optional.ofNullable(place.getMapLink().getGis()))
                .entrySet().stream()
                .filter(e -> e.getValue().isPresent())
                .map(e -> String.format("[%s](%s)", e.getKey(), e.getValue().get())).collect(Collectors.joining(" | "));
    }

    protected InlineKeyboardMarkup buildNextButton(LocationCallback callback) {
        InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> buttonRow = new ArrayList<>();
        List<InlineKeyboardButton> buttonColumn = new ArrayList<>();
        InlineKeyboardButton nextButton = new InlineKeyboardButton();

        nextButton.setText(messageUtils.getMessage("button.next_page"));
        callback.setPageIndex(callback.getPageIndex() + 1);
        nextButton.setCallbackData(callback.toString());

        buttonColumn.add(nextButton);
        buttonRow.add(buttonColumn);
        keyboardMarkup.setKeyboard(buttonRow);
        return keyboardMarkup;
    }

    protected void sendPlaces(AbsSender sender, long chatId, double latitude, double longitude, int pageIndex) {
        // TODO: add better location validation
        if (latitude > 60.050466 || latitude < 59.805652 ||
                longitude > 30.565505 || longitude < 30.030409) {
            senderUtils.send(sender,
                    chatId,
                    messageUtils.getMessage("message.location_not_supported"));
            return;
        }

        InlineKeyboardMarkup nextButton = null;
        Page<Place> places = placeService.findByLocation(new PlacesRequest(latitude, longitude, pageIndex, PAGE_SIZE));
        int i = 1;
        for (Place place : places.getContent()) {
            if (i == PAGE_SIZE && places.getTotalPages() > pageIndex) {
                nextButton = buildNextButton(new LocationCallback(latitude, longitude, pageIndex));
            }
            i++;
            senderUtils.send(sender,
                    chatId,
                    composeMessage(place),
                    nextButton,
                    place.getPhotoUrl());
        }
    }
}
