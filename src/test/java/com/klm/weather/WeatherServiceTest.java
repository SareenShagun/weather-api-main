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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Test
    void shouldReturnWeatherDetailsById() {
        when(weatherRepository.findById(1)).thenReturn(Optional.ofNullable(weather));

        Weather responseWeather = weatherService.getWeatherById(1);

        assertNotNull(responseWeather);
        assertEquals(weather.getId(), responseWeather.getId());
        assertEquals(weather.getCity(), responseWeather.getCity());
        verify(weatherRepository, times(1)).findById(1);
    }

    @Test
    void shouldReturnWeatherDetailsByDate() {

        when(weatherRepository.findAll()).thenReturn(List.of(weather));
        String targetDate = new SimpleDateFormat("yyyy-MM-dd").format(weather.getDate());

        List<Weather> response = weatherService.getAllWeather(targetDate, null, null);
        assertEquals(1, response.size());
        assertEquals(weather.getCity(), response.get(0).getCity());
    }
}
