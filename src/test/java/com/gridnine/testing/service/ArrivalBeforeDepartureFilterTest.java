package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для класса ArrivalBeforeDepartureFilter")
class ArrivalBeforeDepartureFilterTest {

    @Test
    @DisplayName("Тестирование метода filter()")
    void filter() {
        ArrivalBeforeDepartureFilter filter = new ArrivalBeforeDepartureFilter();

        LocalDateTime now = LocalDateTime.now();
        Segment validSegment = new Segment(now.plusHours(1), now.plusHours(2));
        Segment invalidSegment = new Segment(now.plusHours(2), now.plusHours(1));

        Flight validFlight = new Flight(Collections.singletonList(validSegment));
        Flight invalidFlight = new Flight(Collections.singletonList(invalidSegment));

        List<Flight> flights = Arrays.asList(validFlight, invalidFlight);

        List<Flight> result = filter.filter(flights);

        assertEquals(1, result.size());
        assertEquals(validFlight, result.getFirst());
    }
}
