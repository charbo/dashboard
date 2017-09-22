package org.dashboard.java8;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.dashboard.java8.beans.Appointment;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StreamTestToCompleted {
    private static final  ObjectMapper MAPPER = new ObjectMapper();

    private Appointment[] getAppointments() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("appointments.json").getFile());
        return MAPPER.readValue(file, Appointment[].class);
    }

    @Test public void
    unSimpleForEach() throws IOException {
        Appointment[] appointments = getAppointments();

        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }

        Arrays.stream(appointments).forEach(System.out::println);
    }

    @Test public void
    recupererLaListeDesCategories() throws IOException {
        Appointment[] appointments = getAppointments();

        List<String> categories = Arrays.stream(appointments).map(a -> a.getCategory()).collect(Collectors.toList());
        categories.stream().forEach(System.out::println);

        System.out.println("***********");

        Set<String> categoriesSet = Arrays.stream(appointments).map(a -> a.getCategory()).collect(Collectors.toSet());
        categoriesSet.stream().forEach(System.out::println);

        System.out.println("***********");

        categories = Arrays.stream(appointments).map(a -> a.getCategory()).distinct().collect(Collectors.toList());
        categories.stream().forEach(System.out::println);


    }



}
