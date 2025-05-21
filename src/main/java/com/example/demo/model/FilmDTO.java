package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FilmDTO(@JsonProperty("filmId") int filmId,
                        @JsonProperty("titel") String titel,
                        @JsonProperty("genre") String genre,
                        @JsonProperty("fsk") int fsk,
                        @JsonProperty("dauer") int dauer) {}