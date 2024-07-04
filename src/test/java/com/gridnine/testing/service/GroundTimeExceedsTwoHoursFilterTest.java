package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Тесты для класса GroundTimeExceedsTwoHoursFilter")
class GroundTimeExceedsTwoHoursFilterTest {

    @Test
    @DisplayName("Тестирование метода filter()")
    void filter() {
        GroundTimeExceedsTwoHoursFilter filter = new GroundTimeExceedsTwoHoursFilter();

        LocalDateTime now = LocalDateTime.now();
        Segment firstSegment = new Segment(now, now.plusHours(1));
        Segment secondSegmentWithinTwoHours = new Segment(now.plusHours(2), now.plusHours(3));
        Segment secondSegmentExceedsTwoHours = new Segment(now.plusHours(4), now.plusHours(5));

        Flight validFlight = new Flight(Arrays.asList(firstSegment, secondSegmentWithinTwoHours));
        Flight invalidFlight = new Flight(Arrays.asList(firstSegment, secondSegmentExceedsTwoHours));

        List<Flight> flights = Arrays.asList(validFlight, invalidFlight);

        List<Flight> result = filter.filter(flights);

        assertEquals(1, result.size());
        assertEquals(validFlight, result.getFirst());
    }
}
