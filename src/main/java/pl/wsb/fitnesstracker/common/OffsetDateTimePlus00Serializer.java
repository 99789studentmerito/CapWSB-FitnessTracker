package pl.wsb.fitnesstracker.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimePlus00Serializer extends StdSerializer<OffsetDateTime> {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    public OffsetDateTimePlus00Serializer() {
        super(OffsetDateTime.class);
    }

    @Override
    public void serialize(OffsetDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        // Always format with +00:00, never Z
        String formatted = value.withOffsetSameInstant(java.time.ZoneOffset.UTC).format(FORMATTER);
        // Replace Z with +00:00 if present (defensive)
        formatted = formatted.replace("Z", "+00:00");
        gen.writeString(formatted);
    }
}