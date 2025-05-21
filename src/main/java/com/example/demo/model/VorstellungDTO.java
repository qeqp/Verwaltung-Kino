package com.example.demo.model;

import java.sql.Date;
import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VorstellungDTO(@JsonProperty("vId") int vId,
                        @JsonProperty("datum") Date datum,
                        @JsonProperty("uhrzeit") Time uhrzeit,
                        @JsonProperty("saalname") String saalname,
                        @JsonProperty("filmId") int filmId) {}
