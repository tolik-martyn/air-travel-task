package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для класса FlightFilterService")
class FlightFilterServiceTest {

    private FlightFilterService service;
    private ArrivalBeforeDepartureFilter arrivalFilter;
    private DepartureBeforeNowFilter departureFilter;

    @BeforeEach
    public void setUp() {
        service = new FlightFilterService();
    }

    @Test
    @DisplayName("Тестирование метода addFilter()")
    void addFilter() {
        ArrivalBeforeDepartureFilter arrivalFilter = new ArrivalBeforeDepartureFilter();
        DepartureBeforeNowFilter departureFilter = new DepartureBeforeNowFilter();

        service.addFilter(arrivalFilter);
        service.addFilter(departureFilter);

        assertEquals(2, service.getFilters().size());
        assertEquals(arrivalFilter, service.getFilters().get(0));
        assertEquals(departureFilter, service.getFilters().get(1));
    }

    @Test
    @DisplayName("Тестирование метода filter()")
    void filter() {
        LocalDateTime now = LocalDateTime.now();
        Segment validSegment = new Segment(now.plusHours(1), now.plusHours(2));
        Segment invalidSegment = new Segment(now.plusHours(2), now.plusHours(1));
        Segment futureSegment = new Segment(now.plusHours(3), now.plusHours(4));

        Flight validFlight = new Flight(Collections.singletonList(validSegment));
        Flight invalidFlight = new Flight(Collections.singletonList(invalidSegment));
        Flight futureFlight = new Flight(Collections.singletonList(futureSegment));

        List<Flight> flights = Arrays.asList(validFlight, invalidFlight, futureFlight);

        ArrivalBeforeDepartureFilter arrivalFilter = new ArrivalBeforeDepartureFilter();
        DepartureBeforeNowFilter departureFilter = new DepartureBeforeNowFilter();

        service.addFilter(arrivalFilter);
        service.addFilter(departureFilter);

        List<Flight> result = service.filter(flights);

        assertEquals(2, result.size());
        assertEquals(validFlight, result.get(0));
        assertEquals(futureFlight, result.get(1));
    }
}
