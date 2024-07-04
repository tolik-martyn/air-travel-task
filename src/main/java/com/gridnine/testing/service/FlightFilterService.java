package com.gridnine.testing.service;

import com.gridnine.testing.model.Flight;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис для последовательного применения нескольких фильтров к списку полетов.
 */
public class FlightFilterService {
    private final List<FlightFilter> filters = new ArrayList<>();

    /**
     * Добавляет фильтр к списку фильтров.
     *
     * @param filter фильтр для добавления
     */
    public void addFilter(FlightFilter filter) {
        filters.add(filter);
    }

    /**
     * Применяет все добавленные фильтры к списку полетов.
     *
     * @param flights список полетов для фильтрации
     * @return отфильтрованный список полетов
     */
    public List<Flight> filter(List<Flight> flights) {
        List<Flight> filteredFlights = flights;
        for (FlightFilter filter : filters) {
            filteredFlights = filter.filter(filteredFlights);
        }
        return filteredFlights;
    }

    public List<FlightFilter> getFilters() {
        return filters;
    }
}
