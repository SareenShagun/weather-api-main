package com.klm.weather.dto;

import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WeatherDto {
    private Date date;
    private Float lat;
    private Float lon;
    private String city;
    private String state;
    private List<Double> temperatures;
}
