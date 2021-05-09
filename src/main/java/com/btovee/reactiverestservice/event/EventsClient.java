package com.btovee.reactiverestservice.event;

import com.btovee.reactiverestservice.event.dto.EventClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class EventsClient {

    @Value("${ticketmaster.data.events}")
    private String eventsDataEndpoint;

    private final WebClient webClient;

    public Mono<EventClientDto[]> getEvents() {
        return webClient
                .get()
                .uri(eventsDataEndpoint)
                .retrieve()
                .bodyToMono(EventClientDto[].class);
    }
}
