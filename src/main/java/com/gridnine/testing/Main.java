package com.gridnine.testing;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.FlightBuilder;
import com.gridnine.testing.service.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        List<Flight> flights = FlightBuilder.createFlights();

        // Фильтр, который исключает вылеты до текущего момента времени
        FlightFilterService flightFilter1 = new FlightFilterService();
        flightFilter1.addFilter(new DepartureBeforeNowFilter());
        List<Flight> filteredFlights1 = flightFilter1.filter(flights);
        System.out.println("Будущие вылеты:");
        System.out.println(filteredFlights1);

        System.out.println("================================");

        // Фильтр, который исключает вылеты с сегментами, у которых дата прилета раньше даты вылета
        FlightFilterService flightFilter2 = new FlightFilterService();
        flightFilter2.addFilter(new ArrivalBeforeDepartureFilter());
        List<Flight> filteredFlights2 = flightFilter2.filter(flights);
        System.out.println("Вылеты с верной датой вылета и прилета:");
        System.out.println(filteredFlights2);

        System.out.println("================================");

        // Фильтр, который исключает вылеты с общим временем на земле более 2-х часов
        FlightFilterService flightFilter3 = new FlightFilterService();
        flightFilter3.addFilter(new GroundTimeExceedsTwoHoursFilter());
        List<Flight> filteredFlights3 = flightFilter3.filter(flights);
        System.out.println("Вылеты с пересадкой менее 2-х часов:");
        System.out.println(filteredFlights3);

        System.out.println("================================");

        // Одновременное использование нескольких фильтров
        FlightFilterService flightFilter = new FlightFilterService();
        flightFilter.addFilter(new DepartureBeforeNowFilter());
        flightFilter.addFilter(new ArrivalBeforeDepartureFilter());
        flightFilter.addFilter(new GroundTimeExceedsTwoHoursFilter());
        List<Flight> filteredFlights = flightFilter.filter(flights);
        System.out.println("Список вылетов при одновременном использовании нескольких фильтров:");
        System.out.println(filteredFlights);

        System.out.println("================================");

        // Пример создания фильтров через анонимные классы на основе DepartureBeforeNowFilter и ArrivalBeforeDepartureFilter
        flightFilter3.addFilter(new FlightFilter() {
            @Override
            public List<Flight> filter(List<Flight> flights) {
                LocalDateTime now = LocalDateTime.now();
                return flights.stream()
                        .filter(flight -> flight.getSegments().stream()
                                .allMatch(segment -> segment.getDepartureDate().isAfter(now)))
                        .collect(Collectors.toList());
            }
        });

        flightFilter3.addFilter(flightList -> flightList.stream()
                .filter(flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate())))
                .collect(Collectors.toList()));
        List<Flight> filteredFlights4 = flightFilter3.filter(filteredFlights3);
        System.out.println("Список вылетов с использованием анонимных классов:");
        System.out.println(filteredFlights4);
    }
}
