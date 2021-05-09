package com.btovee.reactiverestservice.venue;

import com.btovee.reactiverestservice.venue.dto.VenueClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class VenueClient {

    @Value("${ticketmaster.data.venues}")
    private String venuesDataEndpoint;

    private final WebClient webClient;

    public Mono<VenueClientDto[]> getVenues() {
        return webClient
                .get()
                .uri(venuesDataEndpoint)
                .retrieve()
                .bodyToMono(VenueClientDto[].class);
    }
}
