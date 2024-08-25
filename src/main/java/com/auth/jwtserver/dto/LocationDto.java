package com.auth.jwtserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDto {
    private String countryName;
    private String currency;
    private String currencyCode;
    private String flag;
}
