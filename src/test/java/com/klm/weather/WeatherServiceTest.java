package com.klm.weather;

import com.klm.weather.dto.WeatherDto;
import com.klm.weather.model.Weather;
import com.klm.weather.repository.WeatherRepository;
import com.klm.weather.service.WeatherService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class WeatherServiceTest {

    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    private WeatherService weatherService;

    private AutoCloseable autoCloseable;

    private Weather weather;

    private WeatherDto weatherDto;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        weatherDto = new WeatherDto(new Date(),
                11.87f,
                75.59f,
                "Bengaluru",
                "Karnataka",
                Arrays.asList(22.3, 23.5));

        weather = new Weather(
                1,
                weatherDto.getDate(),
                weatherDto.getLat(),
                weatherDto.getLon(),
                weatherDto.getCity(),
                weatherDto.getState(),
                weatherDto.getTemperatures()
        );
    }

    @AfterEach
    void close() throws Exception {
        autoCloseable.close();
    }

    @Test
    void shouldCreateWeatherSuccessfully() {
        when(weatherRepository.save(any(Weather.class))).thenReturn(weather);

        Weather createdWeather = weatherService.createWeather(weatherDto);

        assertNotNull(createdWeather);
        assertEquals(weather.getCity(), createdWeather.getCity());
        assertEquals(weather.getId(), createdWeather.getId());
        assertEquals(weather.getState(), createdWeather.getState());
        verify(weatherRepository, times(1)).save(any(Weather.class));
    }
}
