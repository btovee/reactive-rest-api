package com.btovee.reactiverestservice.artist;

import com.btovee.reactiverestservice.artist.dto.ArtistClientDto;
import com.btovee.reactiverestservice.artist.dto.ArtistDto;
import com.btovee.reactiverestservice.event.EventsClient;
import com.btovee.reactiverestservice.event.dto.EventClientDto;
import com.btovee.reactiverestservice.event.dto.EventDto;
import com.btovee.reactiverestservice.event.dto.IdDto;
import com.btovee.reactiverestservice.venue.VenueClient;
import com.btovee.reactiverestservice.venue.dto.VenueClientDto;
import com.btovee.reactiverestservice.venue.dto.VenueDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {

    @Mock
    private ArtistClient artistClient;

    @Mock
    private EventsClient eventsClient;

    @Mock
    private VenueClient venueClient;

    private ArtistService service;

    @BeforeEach
    void setup() {
        service = new ArtistService(artistClient, eventsClient, venueClient);
    }

    @Test
    void getArtist_withArtistId_withAllDataPresent() {
        when(eventsClient.getEvents()).thenReturn(getEventClientDtos());
        when(artistClient.getArtists()).thenReturn(getArtistClientDto());
        when(venueClient.getVenues()).thenReturn(getVenueClientDtos());

        VenueDto venueDto = VenueDto.builder()
                .name("O2 Academy Sheffield")
                .url("/o2-academy-sheffield-tickets-sheffield/venue/41")
                .city("Sheffield")
                .build();

        EventDto eventDto = EventDto.builder()
                .title("Fusion Prog")
                .dateStatus("singleDate")
                .timeZone("Europe/London")
                .startDate(new Date(2009, Calendar.JANUARY, 11))
                .hiddenFromSearch(false)
                .venue(venueDto)
                .build();

        ArtistDto expected = ArtistDto.builder()
                .name("HRH Prog")
                .url("/hrh-prog-tickets/artist/21")
                .imgSrc("//some-base-url/hrh-prog.jpg")
                .rank(1)
                .eventDto(new EventDto[]{eventDto})
                .build();

        StepVerifier.create(service.getArtist("21"))
                .expectNext(expected)
                .verifyComplete();
    }

    @Test
    void getArtist_withArtistId_ArtistNotPresent() {
        when(eventsClient.getEvents()).thenReturn(getEventClientDtos());
        when(artistClient.getArtists()).thenReturn(getArtistClientDto());
        when(venueClient.getVenues()).thenReturn(getVenueClientDtos());

        ArtistDto expected = ArtistDto.builder()
                .build();

        StepVerifier.create(service.getArtist("1"))
                .expectNext(expected)
                .verifyComplete();
    }

    @Test
    void getArtist_withArtistId_NoEventDataPresent() {
        when(eventsClient.getEvents()).thenReturn(getEventClientDtos());
        when(artistClient.getArtists()).thenReturn(getArtistClientDto());
        when(venueClient.getVenues()).thenReturn(getVenueClientDtos());

        ArtistDto expected = ArtistDto.builder()
                .name("Mostly Autumn")
                .imgSrc("//some-base-url/mostly-autumn.jpg")
                .url("/mostly-autumn-tickets/artist/23")
                .rank(3)
                .eventDto(new EventDto[]{})
                .build();

        StepVerifier.create(service.getArtist("30"))
                .expectNext(expected)
                .verifyComplete();
    }

    @Test
    void getArtist_withArtistId_NoVenueDataPresent() {
        when(eventsClient.getEvents()).thenReturn(getEventClientDtos());
        when(artistClient.getArtists()).thenReturn(getArtistClientDto());
        when(venueClient.getVenues()).thenReturn(getVenueClientDtos());

        EventDto eventDto = EventDto.builder()
                .title("Jazz Live")
                .dateStatus("singleDate")
                .timeZone("Europe/London")
                .startDate(new Date(2009, Calendar.JANUARY, 11))
                .hiddenFromSearch(true)
                .venue(VenueDto.builder().build())
                .build();

        ArtistDto expected = ArtistDto.builder()
                .name("Colosseum")
                .imgSrc("//some-base-url/colosseum.jpg")
                .url("/colosseum-tickets/artist/22")
                .rank(2)
                .eventDto(new EventDto[]{eventDto})
                .build();

        StepVerifier.create(service.getArtist("22"))
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
                        .artists(new IdDto[]{new IdDto("20"), new IdDto("25"), new IdDto("27")})
                        .venue(new IdDto("42"))
                        .hiddenFromSearch(false)
                        .build(),

                EventClientDto.builder()
                        .title("Jazz Live")
                        .id("3")
                        .dateStatus("singleDate")
                        .timeZone("Europe/London")
                        .startDate(new Date(2009, Calendar.JANUARY, 11))
                        .artists(new IdDto[]{new IdDto("22"), new IdDto("28"), new IdDto("29")})
                        .venue(new IdDto("49"))
                        .hiddenFromSearch(true)
                        .build()
        });
    }

    private Mono<ArtistClientDto[]> getArtistClientDto() {
        return Mono.just(new ArtistClientDto[]{
                ArtistClientDto.builder()
                        .name("HRH Prog")
                        .id("21")
                        .imgSrc("//some-base-url/hrh-prog.jpg")
                        .url("/hrh-prog-tickets/artist/21")
                        .rank(1)
                        .build(),

                ArtistClientDto.builder()
                        .name("Colosseum")
                        .id("22")
                        .imgSrc("//some-base-url/colosseum.jpg")
                        .url("/colosseum-tickets/artist/22")
                        .rank(2)
                        .build(),

                ArtistClientDto.builder()
                        .name("Mostly Autumn")
                        .id("30")
                        .imgSrc("//some-base-url/mostly-autumn.jpg")
                        .url("/mostly-autumn-tickets/artist/23")
                        .rank(3)
                        .build()
        });
    }
}