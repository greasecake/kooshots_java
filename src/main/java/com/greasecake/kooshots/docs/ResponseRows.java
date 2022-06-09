package com.greasecake.kooshots.docs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = RangeDeserializer.class)
public class ResponseRows {
    public List<ResponseRow> values;

    public List<ResponseRow> getValues() {
        return values;
    }

    public void setValues(List<ResponseRow> values) {
        this.values = values;
    }
}
