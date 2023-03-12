package com.greasecake.kooshots.entity;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "conversation_state")
public class ConversationState extends AbstractEntity {
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Timestamp datetime;
    private Long chatId;
    // TODO: convert to enum
    private String state;

    @Column(name = "chat_id", unique = true, nullable = false)
    public Long getChatId() {
        return chatId;
    }

    @Column(name = "state")
    public String getState() {
        return state;
    }

    @Column
    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setState(String state) {
        this.state = state;
    }
}
