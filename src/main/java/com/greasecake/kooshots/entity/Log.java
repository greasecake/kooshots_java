package com.greasecake.kooshots.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Table
@Entity(name = "logs")
public class Log extends AbstractEntity {
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp datetime;
    private String username;
    private Long chatId;
    private Double latitude;
    private Double longitude;
    private String result;

    @Column(name = "datetime")
    public Timestamp getDatetime() {
        return datetime;
    }

    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    @Column(name = "chat_id")
    public Long getChatId() {
        return chatId;
    }

    @Column(name = "latitude")
    public Double getLatitude() {
        return latitude;
    }

    @Column(name = "longitude")
    public Double getLongitude() {
        return longitude;
    }

    @Column(name = "result")
    public String getResult() {
        return result;
    }


    public void setResult(String result) {
        this.result = result;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    @Override
    public String toString() {
        return new SimpleDateFormat("dd-MM-yy HH:mm").format(datetime) +
                ", " + username +
                ", " + result;
    }
}
