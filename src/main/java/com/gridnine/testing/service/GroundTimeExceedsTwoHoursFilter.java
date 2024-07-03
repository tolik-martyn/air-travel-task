package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.model.Segment;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Фильтр, который исключает полеты с пересадкой более 2-х часов
 */
public class GroundTimeExceedsTwoHoursFilter implements FlightFilter{
    @Override
    public List<Flight> filter(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> {
                    List<Segment> segments = flight.getSegments();
                    long totalGroundTimeMinutes = 0;

                    for (int i = 0; i < segments.size() - 1; i++) {
                        Segment currentSegment = segments.get(i);
                        Segment nextSegment = segments.get(i + 1);
                        Duration groundTime = Duration.between(currentSegment.getArrivalDate(), nextSegment.getDepartureDate());
                        totalGroundTimeMinutes += groundTime.toMinutes();
                    }

                    return totalGroundTimeMinutes <= 120; // 2 часа = 120 минут
                })
                .collect(Collectors.toList());
    }
}
