package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;

import java.util.List;

/**
 * Интерфейс для фильтров полетов.
 */
public interface FlightFilter {
    /**
     * Метод фильтрует список полетов.
     *
     * @param flights список полетов для фильтрации
     * @return отфильтрованный список полетов
     */
    List<Flight> filter(List<Flight> flights);
}
