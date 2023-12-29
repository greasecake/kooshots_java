package com.greasecake.kooshots.entity.log;

public class LogRequestData {
    String text;
    Boolean command;
    Boolean replyKeyboard;
    Boolean thread;
    String photoId;
    String voiceId;
    LogLocationData location;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getCommand() {
        return command;
    }

    public void setCommand(Boolean command) {
        this.command = command;
    }

    public Boolean getReplyKeyboard() {
        return replyKeyboard;
    }

    public void setReplyKeyboard(Boolean replyKeyboard) {
        this.replyKeyboard = replyKeyboard;
    }

    public Boolean getThread() {
        return thread;
    }

    public void setThread(Boolean thread) {
        this.thread = thread;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getVoiceId() {
        return voiceId;
    }

    public void setVoiceId(String voiceId) {
        this.voiceId = voiceId;
    }

    public LogLocationData getLocation() {
        return location;
    }

    public void setLocation(LogLocationData location) {
        this.location = location;
    }
}
