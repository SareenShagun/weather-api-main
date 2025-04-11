package com.klm.weather.service;

import com.klm.weather.dto.WeatherDto;
import com.klm.weather.model.Weather;
import com.klm.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.klm.weather.exception.ResourceNotFoundException;

@Service
public class WeatherService {
    private final WeatherRepository weatherRepository;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public Weather createWeather(WeatherDto weatherDto) {
        Weather weather = convertDtoToWeather(weatherDto);
        return weatherRepository.save(weather);
    }

    private Weather convertDtoToWeather(WeatherDto weatherDto) {
        return new Weather(weatherDto.getDate(),
                weatherDto.getLat(),
                weatherDto.getLon(),
                weatherDto.getCity(),
                weatherDto.getState(),
                weatherDto.getTemperatures());
    }

    public Weather getWeatherById(Integer id) {
        return weatherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Weather with ID " + id + " not found"));
    }
}
