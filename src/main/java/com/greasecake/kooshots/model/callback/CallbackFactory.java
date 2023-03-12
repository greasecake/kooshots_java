package com.greasecake.kooshots.model.callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class CallbackFactory {
    @Autowired
    List<Callback> callbacks;

    public Callback getCallback(String callbackString) {
        String[] callbackPair = callbackString.split(";");
        Callback callback = callbacks.stream()
                .filter(c -> c.getType().name().toLowerCase(Locale.ROOT).equals(callbackPair[0].toLowerCase(Locale.ROOT)))
                .findFirst().orElseThrow();
        callback.setFields(callbackPair[1]);
        return callback;
    }
}
