package com.btovee.reactiverestservice.venue;

import com.btovee.reactiverestservice.venue.dto.VenueClientDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class VenueClient {

    @Value("${ticketmaster.data.venues}")
    private String venuesDataEndpoint;

    private final WebClient webClient;

    public Mono<VenueClientDto[]> getVenues() {
        log.info("Retrieving Venues Data");
        return webClient
                .get()
                .uri(venuesDataEndpoint)
                .retrieve()
                .bodyToMono(VenueClientDto[].class);
    }
}
