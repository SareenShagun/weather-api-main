package com.klm.weather.controller;

import com.klm.weather.dto.WeatherDto;
import com.klm.weather.model.Weather;
import com.klm.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/weather")
public class WeatherApiRestController {
    private final WeatherService weatherService;

    @Autowired
    public WeatherApiRestController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping
    public ResponseEntity<Weather> createWeather(@RequestBody WeatherDto weatherDto) {
        Weather response = weatherService.createWeather(weatherDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Weather> getWeatherById(@PathVariable Integer id) {
        Weather weather = weatherService.getWeatherById(id);
        return ResponseEntity.ok(weather);
    }

    @GetMapping
    public ResponseEntity<List<Weather>> getWeather(@RequestParam(required = false) String date,
                                                    @RequestParam(required = false) String city,
                                                    @RequestParam(required = false) String sort) {
        List<Weather> records = weatherService.getAllWeather(date, city, sort);
        return ResponseEntity.ok(records);
    }
}
