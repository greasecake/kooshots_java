package com.greasecake.kooshots.bot;

import com.greasecake.kooshots.controller.KooshotsController;
import com.greasecake.kooshots.entity.Log;
import com.greasecake.kooshots.entity.Place;
import com.greasecake.kooshots.model.PlaceCallback;
import com.greasecake.kooshots.model.PlacesRequest;
import com.greasecake.kooshots.repository.LogRepository;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class KooshotsBot extends TelegramLongPollingBot {
    @Autowired
    KooshotsController controller;

    @Autowired
    LogRepository logRepository;

    @Value("${telegram.bot-token}")
    private String botToken;
    @Value("${telegram.bot-name}")
    private String botUserName;
    @Value("${telegram.admin-id}")
    private String adminId;

    @Override
    public String getBotUsername() {
        return botUserName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        String result = "";
        if (update.hasMessage() || update.hasCallbackQuery()) {
            Message message = new Message();
            Double userLatitude = null;
            Double userLongitude = null;
            int pageNum = 0;

            if (update.hasMessage()) {
                message = update.getMessage();
                if (message.getLocation() == null) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(message.getChatId().toString());
                    if (message.getText().equals("/logs") && isAdmin(message)) {
                        Pageable pageable = PageRequest.of(0, 10, Sort.by("datetime").descending());
                        Page<Log> logEntries = logRepository.findAll(pageable);
                        if (logEntries.hasContent()) {
                            sendMessage.setText(logEntries.getContent().stream().map(Object::toString).collect(Collectors.joining("\n")));
                        }
                    } else if (message.getText().equals("Как отправить другое местоположение?")) {
                        sendMessage.setText("Нажмите на скрепочку рядом с полем ввода сообщения и укажите местоположение вручную");
                        result = "sent text";
                    } else {
                        sendMessage.setText("Чтобы получить список ближайших мест, поделитесь вашим местоположением");
                        result = "sent text";
                    }

                    List<KeyboardRow> buttonRows = new ArrayList<>();
                    KeyboardRow buttonRow1 = new KeyboardRow();
                    KeyboardRow buttonRow2 = new KeyboardRow();

                    KeyboardButton locationButton = new KeyboardButton();
                    locationButton.setText("📍 Отправить мое местоположение");
                    locationButton.setRequestLocation(true);
                    buttonRow1.add(locationButton);
                    buttonRows.add(buttonRow1);

                    KeyboardButton helpButton = new KeyboardButton();
                    helpButton.setText("Как отправить другое местоположение?");
                    buttonRow2.add(helpButton);
                    buttonRows.add(buttonRow2);


                    ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
                    replyKeyboard.setKeyboard(buttonRows);
                    replyKeyboard.setResizeKeyboard(true);
                    sendMessage.setReplyMarkup(replyKeyboard);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                } else {
                    userLatitude = update.getMessage().getLocation().getLatitude();
                    userLongitude = update.getMessage().getLocation().getLongitude();
                }
            } else if (update.hasCallbackQuery()) {
                message = update.getCallbackQuery().getMessage();
                PlaceCallback callback = new PlaceCallback(update.getCallbackQuery().getData());
                userLatitude = callback.getLatitude();
                userLongitude = callback.getLongitude();
                pageNum = callback.getPageIndex();
            }

            Page<Place> places = Page.empty();
            if (userLatitude != null && userLongitude != null) {
                if (userLatitude > 60.050466 || userLatitude < 59.805652 ||
                        userLongitude > 30.565505 || userLongitude < 30.030409) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(message.getChatId().toString());
                    sendMessage.setText("Пока можем показать только заведения в пределах Санкт-Петербурга 😕");
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                PlacesRequest request = new PlacesRequest();
                request.setLatitude(userLatitude);
                request.setLongitude(userLongitude);
                request.setDistance(300);
                places = controller.findByLocation(request, pageNum);
            }

            int index = 0;
            for (Place place : places.getContent()) {
                InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();

                List<List<InlineKeyboardButton>> buttonRow = new ArrayList<>();
                List<InlineKeyboardButton> buttonColumn = new ArrayList<>();
                if (index + 1 == places.getNumberOfElements()) {
                    PlaceCallback callback = new PlaceCallback();
                    callback.setLatitude(userLatitude);
                    callback.setLongitude(userLongitude);

                    if (places.hasPrevious()) {
                        InlineKeyboardButton prevButton = new InlineKeyboardButton();
                        prevButton.setText("Предыдущие 3");
                        callback.setPageIndex(places.getPageable().getPageNumber() - 1);
                        prevButton.setCallbackData(callback.toString());
                        buttonColumn.add(prevButton);
                    }

                    if (places.hasNext()) {
                        InlineKeyboardButton nextButton = new InlineKeyboardButton();
                        nextButton.setText("Следующие 3");
                        callback.setPageIndex(places.getPageable().getPageNumber() + 1);
                        nextButton.setCallbackData(callback.toString());
                        buttonColumn.add(nextButton);
                    }
                }
                buttonRow.add(buttonColumn);
                keyboardMarkup.setKeyboard(buttonRow);

                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setPhoto(new InputFile(place.getPhotoUrl()));
                sendPhoto.setChatId(message.getChatId().toString());
                sendPhoto.setCaption(composeMessage(place, userLatitude, userLongitude));
                sendPhoto.setParseMode(ParseMode.MARKDOWN);
                if (keyboardMarkup.getKeyboard() != null) sendPhoto.setReplyMarkup(keyboardMarkup);
                try {
                    execute(sendPhoto);
                    result = "sent places";
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                index++;
            }
            if (!result.isEmpty()) {
                Log logEntry = new Log();
                logEntry.setChatId(message.getChatId());
                logEntry.setDatetime(new Timestamp(System.currentTimeMillis()));
                logEntry.setUsername(message.getChat().getUserName());
                logEntry.setLatitude(userLatitude);
                logEntry.setLongitude(userLongitude);
                logEntry.setResult(result);
                logRepository.save(logEntry);
            }
        }
    }

    private String composeMessage(Place place, Double userLatitude, Double userLongitude) {
        Double placeLatitude = place.getLatitude();
        Double placeLongitude = place.getLongitude();
        BigDecimal distance = BigDecimal.valueOf(Math.sqrt(Math.pow(placeLatitude - userLatitude, 2) + Math.pow(placeLongitude - userLongitude, 2)) * 110.574).setScale(1, RoundingMode.HALF_UP);

        StringBuilder links = new StringBuilder();
        String yandexLink = place.getMapLink().getYandex();
        String googleLink = place.getMapLink().getGoogle();
        if (!yandexLink.isBlank()) {
            links.append("[Яндекс](" + place.getMapLink().getYandex() + ")");
            if (!googleLink.isBlank()) links.append(" | ");
        }
        if (!googleLink.isBlank()) {
            links.append("[Google](" + place.getMapLink().getGoogle() + ")");
        }

        return ("*" + place.getName() + "*\n\n" +
                "\uD83D\uDCCF Находится в " + distance + " км\n" +
                "\uD83D\uDCCD " + place.getAddress()) + "\n" +
                "\uD83D\uDDFA " + links + "\n\n" +
                place.getDescription() + "\n";
    }

    private boolean isAdmin(Message message) {
        return message.getChatId().equals(Long.valueOf(adminId));
    }
}
