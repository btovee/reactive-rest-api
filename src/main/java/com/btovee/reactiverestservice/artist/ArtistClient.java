package com.btovee.reactiverestservice.artist;

import com.btovee.reactiverestservice.artist.dto.ArtistClientDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class ArtistClient {

    @Value("${ticketmaster.data.artists}")
    private String artistDataEndpoint;

    private final WebClient webClient;

    public Mono<ArtistClientDto[]> getArtists() {
        log.info("Retrieving Artists Data");
        return webClient
                .get()
                .uri(artistDataEndpoint)
                .retrieve()
                .bodyToMono(ArtistClientDto[].class);
    }

}
