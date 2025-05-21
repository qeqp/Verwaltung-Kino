package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KinosaalDTO(@JsonProperty("saalname") String saalname,
                        @JsonProperty("kapazitaet") int kapazitaet) {}
