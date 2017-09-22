package org.dashboard.java8;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dashboard.bean.Dashboard;
import org.dashboard.generator.JSONParser;
import org.dashboard.java8.beans.Appointment;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamTestToComplete {
    private static final  ObjectMapper MAPPER = new ObjectMapper();

    private Appointment[] getAppointments() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("appointments.json").getFile());
        return MAPPER.readValue(file, Appointment[].class);
    }

    @Test public void
    aSimpleForEach() throws IOException {
        Appointment[] appointments = getAppointments();

        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }

        Arrays.stream(appointments).forEach(System.out::println);
    }

    @Test public void
    getListOfCategories() throws IOException {
        Appointment[] appointments = getAppointments();

        List<String> categories = Arrays.stream(appointments).map(a -> a.getCategory()).collect(Collectors.toList());
        categories.stream().forEach(System.out::println);

        //TODO collector set

        //TODO collector string
    }


    @Test public void
    getAppointmentsGroupedByCategory() throws IOException {
        Appointment[] appointments = getAppointments();

        Map<String, List<Appointment>> appointmensByCategory = Arrays.stream(appointments)
                .collect(Collectors.groupingBy(
                        Appointment::getCategory
                ));


        System.out.println(appointmensByCategory);


        appointmensByCategory.entrySet().stream().flatMap(entry -> entry.getValue().stream()).forEach(System.out::println);

    }





}
