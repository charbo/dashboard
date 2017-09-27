package java8;

import com.fasterxml.jackson.databind.ObjectMapper;
import java8.beans.entity.Appointment;
import java8.domain.WeightAppointment;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class StreamTestCompleted {
  private static final ObjectMapper MAPPER = new ObjectMapper();

  private Appointment[] getAppointments()  {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("appointments.json").getFile());
    try {
      return MAPPER.readValue(file, Appointment[].class);
    } catch (IOException e) {
      return null;
    }
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


  @Test public void
  display_only_appointment_with_duration_more_than_2_hours() {
    Appointment[] appointments = getAppointments();
    Arrays.stream(appointments).filter(appointment -> appointment.getDuration().compareTo(Duration.ofHours(2))  > 0).forEach(System.out::println);
  }

  @Test public void
  sort_appointment_by_date() {
    Appointment[] appointments = getAppointments();
    Arrays.stream(appointments).sorted(Comparator.comparing(Appointment::getDate)).forEach(System.out::println);
    Arrays.stream(appointments).sorted(Comparator.comparing(Appointment::getDate).reversed()).forEach(System.out::println);
  }


  @Test  public void
  appointment_grouped_by_date() {
    Appointment[] appointments = getAppointments();
    Map<LocalDate, List<Appointment>> collect = Arrays.stream(appointments).collect(Collectors.groupingBy(appointment -> appointment.getDate()));

    System.out.println(collect);
  }


  @Test  public void
  appointment_with_max_date() {
    Optional<Appointment> max = Arrays.stream(getAppointments()).max(Comparator.comparing(Appointment::getDate));
    System.out.println(max.get());

  }

  @Test public void
  dates_grouped_by_category() {
    Map<String, List<LocalDate>> collect = Arrays.stream(getAppointments()).collect(
            Collectors.groupingBy(
                    appointment -> appointment.getCategory(),
                    Collectors.mapping(a -> a.getDate(), Collectors.toList())
            )
    );

    System.out.println(collect);
  }

  @Test public void
  max_date_by_category() {
    Map<String, LocalDate> collect = Arrays.stream(getAppointments()).collect(
            Collectors.groupingBy(
                    appointment -> appointment.getCategory(),
                    Collectors.collectingAndThen(
                            Collectors.maxBy(Comparator.comparing(Appointment::getDate)),
                            appointment -> appointment.get().getDate()
                    )
            )
    );

    System.out.println(collect);
  }

  @Test public void
  get_the_priororest_weightappointment_by_category() {
    Map<String, WeightAppointment> collect = Arrays.stream(getAppointments()).collect(
            Collectors.groupingBy(
                    appointment -> appointment.getCategory(),
                    Collectors.mapping(
                            appointment -> WeightAppointment.of(appointment.getCategory(), appointment.getStatus(), appointment.getDate(), appointment.getDuration())
                            ,
                            Collectors.collectingAndThen(
                                    Collectors.maxBy(Comparator.comparing(WeightAppointment::weight)),
                                    wappointment -> wappointment.get()
                            )
                    )
            )
    );

    System.out.println(collect);
  }

}
