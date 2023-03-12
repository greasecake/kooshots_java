package com.greasecake.kooshots.model.callback;

public interface Callback {
    CallbackType getType();
    void setFields(String joinedData);
}
