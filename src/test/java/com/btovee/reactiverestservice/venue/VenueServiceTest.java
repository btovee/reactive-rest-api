package com.btovee.reactiverestservice.venue;

import com.btovee.reactiverestservice.event.EventsClient;
import com.btovee.reactiverestservice.event.dto.EventClientDto;
import com.btovee.reactiverestservice.event.dto.EventWithVenueDto;
import com.btovee.reactiverestservice.event.dto.IdDto;
import com.btovee.reactiverestservice.venue.dto.VenueClientDto;
import com.btovee.reactiverestservice.venue.dto.VenueWithEventDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VenueServiceTest {

    @Mock
    private EventsClient eventsClient;

    @Mock
    private VenueClient venueClient;

    private VenueService service;

    @BeforeEach
    void setup() {
        service = new VenueService(eventsClient, venueClient);
    }

    @Test
    void getVenue_withVenueId_withAllDataPresent() {
        when(eventsClient.getEvents()).thenReturn(getEventClientDtos());
        when(venueClient.getVenues()).thenReturn(getVenueClientDtos());

        EventWithVenueDto eventWithVenueDto = EventWithVenueDto.builder()
                .title("Fusion Prog")
                .dateStatus("singleDate")
                .hiddenFromSearch(false)
                .timeZone("Europe/London")
                .startDate(new Date(2009, Calendar.JANUARY, 11))
                .build();

        VenueWithEventDto expected = VenueWithEventDto.builder()
                .name("O2 Academy Sheffield")
                .url("/o2-academy-sheffield-tickets-sheffield/venue/41")
                .city("Sheffield")
                .events(new EventWithVenueDto[]{eventWithVenueDto})
                .build();

        StepVerifier.create(service.getVenue("41"))
                .expectNext(expected)
                .verifyComplete();
    }

    @Test
    void getVenue_withVenueId_venueNotPresent() {
        when(eventsClient.getEvents()).thenReturn(getEventClientDtos());
        when(venueClient.getVenues()).thenReturn(getVenueClientDtos());

        VenueWithEventDto expected = VenueWithEventDto.builder()
                .build();

        StepVerifier.create(service.getVenue("10"))
                .expectNext(expected)
                .verifyComplete();
    }

    @Test
    void getVenue_withArtistId_NoEventData() {
        when(eventsClient.getEvents()).thenReturn(getEventClientDtos());
        when(venueClient.getVenues()).thenReturn(getVenueClientDtos());

        VenueWithEventDto expected = VenueWithEventDto.builder()
                .name("O2 Academy Brixton")
                .url("/o2-academy-brixton/venue/45")
                .city("London")
                .events(new EventWithVenueDto[]{})
                .build();

        StepVerifier.create(service.getVenue("45"))
                .expectNext(expected)
                .verifyComplete();
    }


    private Mono<VenueClientDto[]> getVenueClientDtos() {
        return Mono.just(new VenueClientDto[]{
                VenueClientDto.builder()
                        .name("O2 Academy Sheffield")
                        .url("/o2-academy-sheffield-tickets-sheffield/venue/41")
                        .city("Sheffield")
                        .id("41")
                        .build(),
                VenueClientDto.builder()
                        .name("O2 Institute2 Birmingham")
                        .url("/o2-institute2-birmingham-tickets-birmingham/venue/42")
                        .city("Birmingham")
                        .id("42")
                        .build(),
                VenueClientDto.builder()
                        .name("O2 Forum Kentish Town")
                        .url("/o2-forum-kentish-town-tickets-london/venue/43")
                        .city("London")
                        .id("43")
                        .build(),
                VenueClientDto.builder()
                        .name("The O2")
                        .url("/o2/venue/44")
                        .city("London")
                        .id("44")
                        .build(),
                VenueClientDto.builder()
                        .name("O2 Academy Brixton")
                        .url("/o2-academy-brixton/venue/45")
                        .city("London")
                        .id("45")
                        .build()
        });
    }

    private Mono<EventClientDto[]> getEventClientDtos() {
        return Mono.just(new EventClientDto[]{
                EventClientDto.builder()
                        .title("Fusion Prog")
                        .id("1")
                        .dateStatus("singleDate")
                        .timeZone("Europe/London")
                        .startDate(new Date(2009, Calendar.JANUARY, 11))
                        .artists(new IdDto[]{new IdDto("21"), new IdDto("23"), new IdDto("26")})
                        .venue(new IdDto("41"))
                        .hiddenFromSearch(false)
                        .build(),

                EventClientDto.builder()
                        .title("Blues In Space")
                        .id("2")
                        .dateStatus("singleDate")
                        .timeZone("Europe/London")
                        .startDate(new Date(2009, Calendar.JANUARY, 11))
                        .artists(new IdDto[]{new IdDto("22"), new IdDto("25"), new IdDto("27")})
                        .venue(new IdDto("42"))
                        .hiddenFromSearch(false)
                        .build(),

                EventClientDto.builder()
                        .title("Jazz Live")
                        .id("3")
                        .dateStatus("singleDate")
                        .timeZone("Europe/London")
                        .startDate(new Date(2009, Calendar.JANUARY, 11))
                        .artists(new IdDto[]{new IdDto("24"), new IdDto("28"), new IdDto("29")})
                        .venue(new IdDto("43"))
                        .hiddenFromSearch(true)
                        .build()
        });
    }
}