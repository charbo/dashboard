package java8.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.time.Duration;
import java.time.LocalDate;

public class WeightAppointment {
    private String category;
    private String status;
    private LocalDate date;
    private Duration duration;

    private WeightAppointment(String category, String status, LocalDate date, Duration duration) {
        this.category = category;
        this.status = status;
        this.date = date;
        this.duration = duration;
    }

    public static final WeightAppointment of(String category, String status, LocalDate date, Duration duration) {
        return new WeightAppointment(category, status, date, duration);
    }

    public int weight() {
        int result = 0;
        if ("PRIO".equals(status)) {
            result = 1;
        }
        return result;
    }
}
