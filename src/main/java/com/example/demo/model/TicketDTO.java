package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TicketDTO(@JsonProperty("ticketId") int ticketId,
                        @JsonProperty("preis") int preis,
                        @JsonProperty("sitzplatz") String sitzplatz,
                        @JsonProperty("vId") int vId,
                        @JsonProperty("email") String email) {
}
