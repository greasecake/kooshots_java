package com.greasecake.kooshots.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MessageUtils {
    @Autowired
    private MessageSource messageSource;
    @Autowired
    ApplicationContext context;
    MessageSourceAccessor accessor;

    @PostConstruct
    private void init() {
        this.accessor = new MessageSourceAccessor(messageSource);
    }

    public String getMessage(String path) {
        return accessor.getMessage(path);
    }
}
