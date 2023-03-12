package com.greasecake.kooshots.bot;

import com.greasecake.kooshots.utils.MessageUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class SenderUtils {
    final MessageUtils messageUtils;

    public SenderUtils(MessageUtils messageUtils) {
        this.messageUtils = messageUtils;
    }

    public void send(AbsSender sender, Long chatId, String text, ReplyKeyboardMarkup markup) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(markup)
                .parseMode(ParseMode.MARKDOWN)
                .build();
        try {
            sender.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void send(AbsSender sender, Long chatId, String text) {
        send(sender, chatId, text, buildDefaultReplyKeyboard());
    }

    public void send(AbsSender sender, Long chatId, String text, InlineKeyboardMarkup markup, String photoUrl) {
        SendPhoto sendPhoto = SendPhoto.builder()
                .chatId(chatId)
                .caption(text)
                .replyMarkup(markup)
                .photo(new InputFile(photoUrl))
                .parseMode(ParseMode.MARKDOWN)
                .build();
        try {
            sender.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public ReplyKeyboardMarkup buildDefaultReplyKeyboard() {
        List<KeyboardRow> buttonRows = new ArrayList<>();
        KeyboardRow buttonRow1 = new KeyboardRow();
        KeyboardRow buttonRow2 = new KeyboardRow();

        KeyboardButton buttonR1C1 = new KeyboardButton();
        buttonR1C1.setText(messageUtils.getMessage("button.send_location"));
        buttonR1C1.setRequestLocation(true);
        buttonRow1.add(buttonR1C1);
        buttonRows.add(buttonRow1);

        KeyboardButton buttonR2C1 = new KeyboardButton();
        buttonR2C1.setText(messageUtils.getMessage("button.info"));
        buttonRow2.add(buttonR2C1);

        KeyboardButton buttonR2C2 = new KeyboardButton();
        buttonR2C2.setText(messageUtils.getMessage("button.feedback"));
        buttonRow2.add(buttonR2C2);

        buttonRows.add(buttonRow2);

        ReplyKeyboardMarkup replyKeyboard = new ReplyKeyboardMarkup();
        replyKeyboard.setKeyboard(buttonRows);
        replyKeyboard.setResizeKeyboard(true);
        return replyKeyboard;
    }
}
