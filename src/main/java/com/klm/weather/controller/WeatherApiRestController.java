package com.klm.weather.controller;

import com.klm.weather.dto.WeatherDto;
import com.klm.weather.service.WeatherService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherApiRestController {
    private final WeatherService weatherService;

    public WeatherApiRestController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }
}
