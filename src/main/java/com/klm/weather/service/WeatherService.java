package com.klm.weather.service;

import com.klm.weather.repository.WeatherRepository;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }
}
