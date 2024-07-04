package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для класса DepartureBeforeNowFilter")
class DepartureBeforeNowFilterTest {

    @Test
    @DisplayName("Тестирование метода filter()")
    void filter() {
        DepartureBeforeNowFilter filter = new DepartureBeforeNowFilter();

        LocalDateTime now = LocalDateTime.now();

        Segment futureSegment = new Segment(now.plusHours(1), now.plusHours(2));
        Segment pastSegment = new Segment(now.minusHours(1), now.minusHours(1));

        Flight futureFlight = new Flight(Collections.singletonList(futureSegment));
        Flight pastFlight = new Flight(Collections.singletonList(pastSegment));

        List<Flight> flights = Arrays.asList(futureFlight, pastFlight);

        List<Flight> result = filter.filter(flights);

        assertEquals(1, result.size());
        assertEquals(futureFlight, result.getFirst());
    }
}