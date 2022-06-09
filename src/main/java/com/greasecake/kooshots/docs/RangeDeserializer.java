package com.greasecake.kooshots.docs;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.base.CaseFormat;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class RangeDeserializer extends StdDeserializer<ResponseRows> {
    public RangeDeserializer() {this(null);}
    public RangeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ResponseRows deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        ArrayNode values = (ArrayNode) p.getCodec().readTree(p).get("values");
        ArrayNode headers = (ArrayNode) values.get(0);
        List<ResponseRow> rows = new ArrayList<>();
        for (int rowIndex = 1; rowIndex < values.size(); rowIndex++) {
            if (values.get(rowIndex).size() != headers.size()) continue;
            ResponseRow row = new ResponseRow();
            for (int colIndex = 0; colIndex < headers.size(); colIndex++) {
                String headerName = headers.get(colIndex).asText();
                String value = values.get(rowIndex).get(colIndex).asText();
                try {
                    Field field = row.getClass().getDeclaredField(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, headerName));
                    field.setAccessible(true);
                    field.set(row, value);
                    field.setAccessible(false);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            rows.add(row);
        }
        ResponseRows range = new ResponseRows();
        range.setValues(rows);
        return range;
    }
}
