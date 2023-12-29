package com.greasecake.kooshots.entity.log;

import com.greasecake.kooshots.entity.AbstractEntity;
import org.springframework.scheduling.annotation.Async;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@Table
@Entity(name = "logs")
public class Log extends AbstractEntity {
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp requestDatetime;
    private Long chatId;
    private LogRequestData requestData;
    private String requestType;
    private Timestamp responseDatetime;
    private LogResponseData responseData;

    public class LogResponseData {

    }
}
