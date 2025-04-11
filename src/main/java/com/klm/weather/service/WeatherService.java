package com.klm.weather.service;

import com.klm.weather.dto.WeatherDto;
import com.klm.weather.model.Weather;
import com.klm.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.klm.weather.exception.ResourceNotFoundException;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WeatherService {
    private static final String WEATHER_NOT_FOUND = "Weather with ID %d not found";
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
                .orElseThrow(() -> new ResourceNotFoundException(String.format(WEATHER_NOT_FOUND, id)));
    }

    public List<Weather> getAllWeather(String date, String city, String sort) {
        List<Weather> records = weatherRepository.findAll();

        //get records with same date
        if (date != null && !date.isBlank()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            records = records
                    .stream()
                    .filter(weather -> sdf.format(weather.getDate()).equals(date))
                    .toList();
        }

        //get records with same city
        if (city != null && !city.isBlank()) {
            Set<String> citySet = Arrays.stream(city.split(","))
                    .map(String::toLowerCase)
                    .collect(Collectors.toSet());

            records = records.stream()
                    .filter(weather -> citySet.contains(weather.getCity().toLowerCase()))
                    .toList();
        }

        if (sort != null) {
            if ("date".equals(sort)) {
                records.sort(Comparator.comparing(Weather::getDate).thenComparing(Weather::getId));
            } else if ("-date".equals(sort)) {
                records.sort(Comparator.comparing(Weather::getDate).reversed().thenComparing(Weather::getId));
            }
        }

        return records;
    }
}
