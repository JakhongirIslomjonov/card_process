package uz.dev.cardprocess.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CurrencyRateDTO(@JsonProperty("Rate") double rate) {}
